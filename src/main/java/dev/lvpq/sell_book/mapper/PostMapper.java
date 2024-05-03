package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.AddPostRequest;
import dev.lvpq.sell_book.dto.request.PostRequest;
import dev.lvpq.sell_book.entity.Post;
import dev.lvpq.sell_book.dto.response.PostDetailResponse;
import dev.lvpq.sell_book.dto.response.PostListingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "transactions", ignore = true)
    Post convertEntity(PostRequest request);
    PostDetailResponse toResponse(Post post);
    PostListingResponse toListResponse(Post post);
    @Mapping(target = "transactions", ignore = true)
    void update(@MappingTarget Post post, PostRequest request);
    PostRequest toAddPostRequest(AddPostRequest request);
}
