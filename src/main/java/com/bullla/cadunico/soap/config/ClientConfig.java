package com.bullla.cadunico.soap.config;

import com.bullla.cadunico.soap.integration.InfobankClient;
import com.bullla.cadunico.soap.interception.LogHttpHeaderClientInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class ClientConfig {

    @Value("${client.IB_ServicesLT.uri}")
    private String defaultUri;

    @Bean
    Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("br.com.autbank.literal");
        return jaxb2Marshaller;
    }

    @Bean
    public InfobankClient infobankClient() {
        InfobankClient client = new InfobankClient();
        client.setMarshaller(jaxb2Marshaller());
        client.setUnmarshaller(jaxb2Marshaller());
        client.setDefaultUri(defaultUri);
        ClientInterceptor[] interceptors =
                new ClientInterceptor[] {new LogHttpHeaderClientInterceptor()};
        client.setInterceptors(interceptors);
        return client;
    }
}
