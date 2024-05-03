package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.ContactRequest;
import dev.lvpq.sell_book.dto.response.ContactResponse;
import dev.lvpq.sell_book.entity.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    Contact convertEntity(ContactRequest request);
    ContactResponse toResponse(Contact contact);
}
