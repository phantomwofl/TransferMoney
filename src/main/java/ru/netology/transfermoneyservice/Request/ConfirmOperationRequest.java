package ru.netology.transfermoneyservice.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConfirmOperationRequest {
    private String operationId;
    private String code;
}
