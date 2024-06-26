package dev.lvpq.sell_book.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.lvpq.sell_book.dto.request.*;
import dev.lvpq.sell_book.dto.response.AuthenticationResponse;
import dev.lvpq.sell_book.dto.response.RegisterResponse;
import dev.lvpq.sell_book.entity.InvalidatedToken;
import dev.lvpq.sell_book.entity.User;
import dev.lvpq.sell_book.enums.ErrorCode;
import dev.lvpq.sell_book.enums.RoleName;
import dev.lvpq.sell_book.enums.UserGender;
import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.exception.WebException;
import dev.lvpq.sell_book.mapper.RegisterMapper;
import dev.lvpq.sell_book.repository.InvalidatedTokenRepository;
import dev.lvpq.sell_book.repository.RoleRepository;
import dev.lvpq.sell_book.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    RegisterMapper registerMapper;
    PasswordEncoder passwordEncoder;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.ITEM_EXISTS);

        var user = registerMapper.convertEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var role = roleRepository.findById(RoleName.USER.getName()).orElse(null);
        user.setRoles(Collections.singleton(role));

        user.setGender(UserGender.valueOf(request.getGender()));

        log.info(user.getGender().name());
        return registerMapper.toResponse(userRepository.save(user));
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request, boolean web) {
        var user = userRepository.findByName(request.getName()).orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        boolean authenticated = passwordEncoder
                .matches(request.getPass(), user.getPassword());
        var token = generateToken(user);
        if(!authenticated || token == null) {
            if(web)
                return AuthenticationResponse.builder()
                        .token(null)
                        .authenticated(false)
                        .build();
            else
                throw new AppException(ErrorCode.INCORRECTPASSWORD);
        }


        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public boolean checkNameExist(UserRegisterRequest user){
        return userRepository.existsByName(user.getName());
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);


        JWTClaimsSet jwtClaimsSet =
                new JWTClaimsSet.Builder()
                        .subject(user.getName())
                        .issuer("lvpq.com")
                        .issueTime(new Date())
                        .expirationTime(new Date(
                                Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                        ))
                        .jwtID(UUID.randomUUID().toString())
                        .claim("scope", buildScope(user))
                        .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (Exception e) {
            return null;
        }
    }

    public AuthenticationResponse refreshToken (RefreshRequest request)
            throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken());

        var jid = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var invalidatedToken =
                InvalidatedToken.builder()
                        .id(jid)
                        .expiryTime(expiryTime)
                        .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByName(username).orElseThrow(()
                -> new WebException(ErrorCode.INVALIDATEDTOKEN));

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public boolean logout(LogoutRequest request)
            throws ParseException, JOSEException {
        var signedToken = verifyToken(request.getToken());

        if (signedToken == null)
            return false;

        String jid = signedToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();

        var invalidatedToken =
                InvalidatedToken.builder()
                        .id(jid)
                        .expiryTime(expiryTime)
                        .build();

        invalidatedTokenRepository.save(invalidatedToken);

        log.info("Invalidated id: " + invalidatedToken.getId());
        return true;
    }

    private SignedJWT verifyToken(String token)
            throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if( !(verified && expityTime.after(new Date())) )
            return null;

        if(invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            return null;
        }
        return signedJWT;
    }

    private String buildScope(User user) {
        var stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        return stringJoiner.toString();
    }
}
