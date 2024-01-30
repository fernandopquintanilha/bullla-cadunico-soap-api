package com.bullla.cadunico.soap.interception;

import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.soap.SOAPMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

@Configuration
public class LogHttpHeaderClientInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        HttpLoggingUtils.logMessage("Client Request Message", messageContext.getRequest());
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        HttpLoggingUtils.logMessage("Client Response Message", messageContext.getResponse());
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        HttpLoggingUtils.logMessage("Client Response Message", messageContext.getResponse());
        SaajSoapMessage soapResponse = (SaajSoapMessage) messageContext.getResponse();
        SOAPMessage soapMessage = soapResponse.getSaajMessage();
        SOAPBody body = null;
        try {
            body = soapMessage.getSOAPBody();
        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }
        SOAPFault fault = body.getFault();
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {
    }
}
