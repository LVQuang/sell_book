package dev.lvpq.sell_book.service;

import dev.lvpq.sell_book.entity.Contact;
import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.mapper.ContactMapper;
import dev.lvpq.sell_book.repository.ContactRepository;
import dev.lvpq.sell_book.repository.UserRepository;
import dev.lvpq.sell_book.dto.request.ContactRequest;
import dev.lvpq.sell_book.dto.response.ContactResponse;
import dev.lvpq.sell_book.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ContactService {
    ContactRepository contactRepository;
    UserRepository userRepository;
    ContactMapper contactMapper;
    UserService userService;

    @PreAuthorize("hasRole('USER')")
    public ContactResponse create(ContactRequest request, String userId) {
        var contact = contactMapper.convertEntity(request);
        var senderName = SecurityContextHolder.getContext().getAuthentication().getName();
        var sender = userRepository.findByName(senderName).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        var receiver = userRepository.findById(userId).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        contact.setContactDate(LocalDate.now());
        contact.setSender(sender.getName());
        contact.setReceiver(receiver.getName());

        contactRepository.save(contact);

        sender.getContacts().add(contact);
        receiver.getContacts().add(contact);

        userRepository.saveAll(List.of(sender, receiver));

        return contactMapper.toResponse(contact);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ContactResponse> getAll() {
        var contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contactMapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Contact> getAllMyContacts() {
        var sender = userService.getCurrentUser().getName();
        return contactRepository.findBySender(sender);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void delete(String id) {
        var contact = contactRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        var sender = userRepository.findById(contact.getSender()).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        var receiver = userRepository.findById(contact.getReceiver()).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        sender.getContacts().remove(contact);
        receiver.getContacts().remove(contact);

        userRepository.saveAll(List.of(sender, receiver));

        contactRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<Contact> getAllContactsPage(int page) {
        var result = contactRepository.findAll();
        return getAllContactsPageImpl(page, result);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<Contact> getAllContactsUserPage(int page) {
        var sender = userService.getCurrentUser().getName();
        var result = contactRepository.findBySender(sender);
        return getAllContactsPageImpl(page, result);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    private Page<Contact> getAllContactsPageImpl(int page, List<Contact> result) {
        int pageSize = 10;

        if(result.size() < pageSize)
            pageSize = result.size() ;

        Pageable pageable = PageRequest.of(page, pageSize);

        int start =(int) pageable.getOffset();
        int end = Math.min( (start + pageable.getPageSize()) , result.size());

        var content = result.subList(start, end);
        return new PageImpl<>(content, pageable, result.size());
    }
}
