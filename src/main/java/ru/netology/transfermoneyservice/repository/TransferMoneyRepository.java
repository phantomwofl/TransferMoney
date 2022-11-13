package ru.netology.transfermoneyservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.transfermoneyservice.Transfer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransferMoneyRepository {
    private Map<String, Transfer> transfers;

    public TransferMoneyRepository() {
        this.transfers = new ConcurrentHashMap<>();
    }

    public String add(Transfer transfer) {
        String operationId = transfer.getOperationId();
        transfers.put(operationId, transfer);
        return operationId;
    }

    public boolean contains(String operationId) {
        return transfers.containsKey(operationId);
    }

    public Optional<Transfer> get(String operationId) {
        return Optional.ofNullable(transfers.get(operationId));
    }

    public void remove(String operationId) {
        transfers.remove(operationId);
    }
}
