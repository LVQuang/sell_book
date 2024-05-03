package dev.lvpq.sell_book.api;

import dev.lvpq.sell_book.dto.request.PostRequest;
import dev.lvpq.sell_book.dto.response.ApiResponse;
import dev.lvpq.sell_book.dto.response.PostDetailResponse;
import dev.lvpq.sell_book.dto.response.PostListingResponse;
import dev.lvpq.sell_book.entity.Post;
import dev.lvpq.sell_book.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostAPI {
    PostService postService;

    @GetMapping("/{post}")
    ApiResponse<PostDetailResponse> getById(@PathVariable String post) {
        return ApiResponse.<PostDetailResponse>builder()
                .result(postService.getById(post))
                .build();
    }

    @GetMapping
    ApiResponse<List<PostListingResponse>> getAll() {
        return ApiResponse.<List<PostListingResponse>>builder()
                .result(postService.getAll())
                .build();
    }

    @DeleteMapping("/{post}")
    ApiResponse<Void> delete(@PathVariable String post) {
        postService.delete(post);
        return ApiResponse.<Void>builder()
                .code(2045)
                .message("Item Deleted")
                .build();
    }

    @PostMapping
    ApiResponse<PostDetailResponse> create(@RequestBody PostRequest request) {
        return ApiResponse.<PostDetailResponse>builder()
                .result(postService.create(request))
                .build();
    }

    @PutMapping("/{post}")
    ApiResponse<PostDetailResponse> update(@RequestBody PostRequest request, @PathVariable String post) {
        return ApiResponse.<PostDetailResponse>builder()
                .result(postService.update(request, post))
                .build();
    }
}
