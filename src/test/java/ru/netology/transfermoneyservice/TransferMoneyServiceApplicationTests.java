package ru.netology.transfermoneyservice;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.testcontainers.containers.GenericContainer;
import ru.netology.transfermoneyservice.Request.ConfirmOperationRequest;
import ru.netology.transfermoneyservice.Request.TransferRequest;
import ru.netology.transfermoneyservice.Response.OkTransferResponse;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferMoneyServiceApplicationTests {

    private static final int PORT = 5500;
    private static final String HOST = "http://localhost:";

    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> transferMoneyService = new GenericContainer<>("transfermoney").withExposedPorts(PORT);

    @BeforeAll
    public static void setUp() {
        transferMoneyService.start();
    }




    @Test
    void goodTransfer() {
        TransferRequest transferRequest = Mockito.mock(TransferRequest.class);
        Mockito.when(transferRequest.getCardFromNumber()).thenReturn("3700 0000 0000 002");
        Mockito.when(transferRequest.getCardToNumber()).thenReturn("3700 0000 0100 018");
        Mockito.when(transferRequest.getAmount()).thenReturn(TransferRequest.Amount.builder().value(100).build());

        ResponseEntity<Object> transferResponse = restTemplate.postForEntity(HOST + transferMoneyService.getMappedPort(PORT) + "/transfer", transferRequest, Object.class);
        Assert.assertEquals(HttpStatus.OK, transferResponse.getStatusCode());

        Object transferBody = transferResponse.getBody();
        Assert.assertTrue((transferBody instanceof OkTransferResponse));
        OkTransferResponse okTransferResponse = (OkTransferResponse) transferBody;
        String operationId = okTransferResponse.getOperationId();

        ConfirmOperationRequest confirmOperationRequest = Mockito.mock(ConfirmOperationRequest.class);
        Mockito.when(confirmOperationRequest.getOperationId()).thenReturn(operationId);
        ResponseEntity<Object> objectResponseEntity = restTemplate.postForEntity(HOST + transferMoneyService.getMappedPort(PORT) + "/confirmOperation", confirmOperationRequest, Object.class);
        Assert.assertEquals(HttpStatus.OK, objectResponseEntity.getStatusCode());
    }

}
