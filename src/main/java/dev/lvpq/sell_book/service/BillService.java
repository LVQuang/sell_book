package dev.lvpq.sell_book.service;

import dev.lvpq.sell_book.dto.response.BillResponse;
import dev.lvpq.sell_book.entity.Bill;
import dev.lvpq.sell_book.enums.ErrorCode;
import dev.lvpq.sell_book.exception.AppException;
import dev.lvpq.sell_book.mapper.BillMapper;
import dev.lvpq.sell_book.repository.BillRepository;
import dev.lvpq.sell_book.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class BillService {
    UserService userService;
    BillMapper billMapper;
    BillRepository billRepository;

    public List<BillResponse> getAllUserBill() {
        var user = userService.getCurrentUser();
        var bills = billRepository.findByUser(user);
        return bills.stream().map(billMapper::toBillResponse).toList();
    }

    public void checkBill(String id) {
        var bill = billRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        bill.setBillState("Received");
        billRepository.save(bill);
    }

    public List<BillResponse> getAllBills() {
        return billRepository.findAll().stream().map(billMapper::toBillResponse).toList();
    }
}
