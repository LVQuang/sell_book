package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.AddPostRequest;
import dev.lvpq.sell_book.dto.request.PostRequest;
import dev.lvpq.sell_book.dto.response.PostDetailResponse;
import dev.lvpq.sell_book.dto.response.PostListingResponse;
import dev.lvpq.sell_book.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post convertEntity(PostRequest request);
    PostDetailResponse toResponse(Post post);
    PostListingResponse toListResponse(Post post);
    void update(@MappingTarget Post post, PostRequest request);
    PostRequest toAddPostRequest(AddPostRequest request);
}
