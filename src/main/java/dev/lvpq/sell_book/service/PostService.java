package dev.lvpq.sell_book.service;

import dev.lvpq.sell_book.dto.request.PostRequest;
import dev.lvpq.sell_book.dto.response.PostDetailResponse;
import dev.lvpq.sell_book.dto.response.PostListingResponse;
import dev.lvpq.sell_book.entity.Post;
import dev.lvpq.sell_book.enums.ErrorCode;
import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.mapper.PostMapper;
import dev.lvpq.sell_book.repository.CartItemRepository;
import dev.lvpq.sell_book.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class PostService {
    PostRepository postRepository;
    CartItemRepository cartItemRepository;
    PostMapper postMapper;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PostDetailResponse getById(String id) {
        var post = postRepository.findById(id).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        log.info(post.getId());

        return postMapper.toResponse(post);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<PostListingResponse> getAll() {
        var posts = postRepository.findAll();
        return posts
                .stream()
                .map(postMapper::toListResponse)
                .toList();
    }

    public List<PostListingResponse> getAllWithKey(String key) {
        List<Post> posts;
        if (key != null)
            posts = postRepository.search(key);
        else
            posts = postRepository.findAll();
        return posts
                .stream()
                .map(postMapper::toListResponse)
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(String id) {
        postRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public PostDetailResponse create(PostRequest request) {
        var post = postMapper.convertEntity(request);
        return postMapper.toResponse(postRepository.save(post));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public PostDetailResponse update(PostRequest request, String id) {
        var post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS) );
        postMapper.update(post, request);

        var cartItem = post.getCartItem();
        if(cartItem != null) {
            cartItem.setPost(post);
            cartItemRepository.save(cartItem);
        }
        postRepository.save(post);
        return postMapper.toResponse(post);
    }
}
