package ru.netology.transfermoneyservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.transfermoneyservice.Request.ConfirmOperationRequest;
import ru.netology.transfermoneyservice.Request.TransferRequest;
import ru.netology.transfermoneyservice.service.TransferMoneyService;

@RestController
public class TransferMoneyController {

    private final TransferMoneyService transferMoneyService;


    public TransferMoneyController(TransferMoneyService transferMoneyService) {
        this.transferMoneyService = transferMoneyService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Object> transfer(@RequestBody TransferRequest transferRequest) {
        return transferMoneyService.transfer(transferRequest);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<Object> confirmOperation(@RequestBody ConfirmOperationRequest confirmOperationRequest) {
        return transferMoneyService.confirmOperation(confirmOperationRequest);
    }
}
