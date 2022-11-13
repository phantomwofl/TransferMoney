package ru.netology.transfermoneyservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.netology.transfermoneyservice.Request.TransferRequest;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transfer {

    private String operationId;
    private TransferRequest transferRequest;

    public String getOperationId() {
        if (operationId == null || operationId.isEmpty())
            operationId = UUID.randomUUID().toString();
        return operationId;
    }

    public String getLog() {
        return String.format("Номер транзакции: %s, С карты: %s, На карту: %s, Валюта: %s, Сумма перевода: %s", operationId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferRequest.getAmount().getCurrency(), transferRequest.getAmount().getValue());
    }
}
