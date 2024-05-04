package dev.lvpq.sell_book.mapper;

import dev.lvpq.sell_book.dto.response.BillResponse;
import dev.lvpq.sell_book.entity.Bill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillMapper {
    BillResponse toBillResponse(Bill bill);
}
