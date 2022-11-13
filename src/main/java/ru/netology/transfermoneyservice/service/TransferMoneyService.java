package ru.netology.transfermoneyservice.service;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.transfermoneyservice.Request.ConfirmOperationRequest;
import ru.netology.transfermoneyservice.Request.TransferRequest;
import ru.netology.transfermoneyservice.Response.FailTransferResponse;
import ru.netology.transfermoneyservice.Response.OkTransferResponse;
import ru.netology.transfermoneyservice.Transfer;
import ru.netology.transfermoneyservice.repository.TransferMoneyRepository;

@Service
@Log
public class TransferMoneyService {

    private final String INVALID_INPUT = "Неверные данные";
    private final String INVALID_TRANSACTION = "Ошибка транзакции";


    private final TransferMoneyRepository transferMoneyRepository;

    public TransferMoneyService(TransferMoneyRepository transferMoneyRepository) {
        this.transferMoneyRepository = transferMoneyRepository;
    }

    public ResponseEntity<Object> transfer(TransferRequest transferRequest) {

        if (transferRequest == null)
            return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(INVALID_INPUT).build());

        String operationId = transferMoneyRepository.add(Transfer.builder().transferRequest(transferRequest).build());
        log.info("Новая транзакция: " + transferMoneyRepository.get(operationId).orElseThrow().getLog());
        return ResponseEntity.ok(OkTransferResponse.builder().operationId(operationId).build());
    }

    public ResponseEntity<Object> confirmOperation(ConfirmOperationRequest confirmOperationRequest) {
        if (confirmOperationRequest == null)
            return ResponseEntity.badRequest().body(FailTransferResponse.builder().message(INVALID_INPUT).build());

        String operationId = confirmOperationRequest.getOperationId();

        if (transferMoneyRepository.contains(operationId)) {
            transferMoneyRepository.remove(operationId);
            log.info("Операция - " + operationId + " подтверждена, код = " + confirmOperationRequest.getCode());
            return ResponseEntity.ok(OkTransferResponse.builder().operationId(operationId).build());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FailTransferResponse.builder().message(INVALID_TRANSACTION));
        }
    }
}
