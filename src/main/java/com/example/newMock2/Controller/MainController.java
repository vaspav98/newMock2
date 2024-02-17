package com.example.newMock2.Controller;

import com.example.newMock2.Model.RequestDTO;
import com.example.newMock2.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.Random;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String currency;
            Random random = new Random();
            BigDecimal balance;

            if (firstDigit == '8') {
                balance = new BigDecimal(random.nextInt(2000));
                maxLimit = new BigDecimal(2000);
                currency = "US";
            } else if (firstDigit == '9') {
                balance = new BigDecimal(random.nextInt(1000));
                maxLimit = new BigDecimal(1000);
                currency = "EU";
            } else {
                balance = new BigDecimal(random.nextInt(10000));
                maxLimit = new BigDecimal(10000);
                currency = "RUB";
            }

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(requestDTO.getClientId());
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("********** RequestDTO **********" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("********** ResponseDTO **********" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
