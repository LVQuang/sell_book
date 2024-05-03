package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.request.TransactionRequest;
import dev.lvpq.sell_book.entity.Transaction;
import dev.lvpq.sell_book.dto.response.TransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction convertEntity(TransactionRequest request);
    TransactionResponse toResponse(Transaction transaction);
}
