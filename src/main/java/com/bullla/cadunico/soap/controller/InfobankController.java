package com.bullla.cadunico.soap.controller;

import com.bullla.cadunico.soap.dto.IncluirClienteDTO;
import com.bullla.cadunico.soap.integration.InfobankClient;
import com.bullla.cadunico.soap.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.soap.client.SoapFaultClientException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/infobank")
public class InfobankController {

    @Autowired
    private final InfobankClient infobankClient;

    public InfobankController(InfobankClient infobankClient) {
        this.infobankClient = infobankClient;
    }

    @PostMapping("/incluirCliente7")
    public ResponseEntity<Object> incluirCliente7(@RequestBody IncluirClienteDTO incluirClienteDTO) {
        try {
            return new ResponseEntity<>(infobankClient.incluirCliente7(incluirClienteDTO), HttpStatus.OK);
        } catch (SoapFaultClientException e) {
            return new ResponseEntity<>(ExceptionUtil.getFaultResult(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
