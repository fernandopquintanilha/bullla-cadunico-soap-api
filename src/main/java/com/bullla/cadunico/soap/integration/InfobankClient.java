package com.bullla.cadunico.soap.integration;

import br.com.autbank.literal.IncluirCliente7;
import br.com.autbank.literal.IncluirCliente7Response;
import com.bullla.cadunico.soap.dto.IncluirClienteDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class InfobankClient extends WebServiceGatewaySupport {
    private static final Logger LOGGER = LogManager.getLogger(InfobankClient.class);
    @Value("${client.IB_ServicesLT.uri}")
    private String defaultUri;

    public IncluirCliente7Response incluirCliente7(IncluirClienteDTO incluirCliente) throws SoapFaultClientException {
        ModelMapper mapper = new ModelMapper();
        IncluirCliente7Response incluirCliente7Response = null;
        var incluirCliente7 = mapper.map(incluirCliente, IncluirCliente7.class);
        incluirCliente7Response = (IncluirCliente7Response) getWebServiceTemplate().marshalSendAndReceive(defaultUri, incluirCliente7,
               new SoapActionCallback("http://literal.autbank.com.br"));
        LOGGER.info("Call incluir Clinete 7, request [{}], retorno [{}]", incluirCliente7.toString(), incluirCliente7Response.toString());
        return incluirCliente7Response;
    }
}
