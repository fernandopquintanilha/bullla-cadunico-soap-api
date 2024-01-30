package com.bullla.cadunico.soap.util;

import com.bullla.cadunico.soap.dto.FaultResult;
import com.bullla.cadunico.soap.dto.fault;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.soap.client.SoapFaultClientException;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class ExceptionUtil {
    private static final Logger LOGGER = LogManager.getLogger(ExceptionUtil.class);
    public static fault getFaultResult(SoapFaultClientException e){
        FaultResult resultado = null;
        try{
            final TransformerFactory factory = TransformerFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            StringWriter writer = new StringWriter();
            Transformer transformer = factory.newTransformer();
            StreamResult result = new StreamResult(writer);
            transformer.transform(e.getSoapFault().getSource(), result);
            StringBuffer sb = writer.getBuffer();
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            resultado = xmlMapper.readValue(sb.toString(), FaultResult.class);
        } catch (TransformerConfigurationException ex) {
            LOGGER.error("Erro na configuração de transformar xml ", ex);
            throw new RuntimeException(ex);
        } catch (JsonMappingException ex) {
            LOGGER.error("Erro no mapeamento do JSON ", ex);
            throw new RuntimeException(ex);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Erro ao processar o JSON ", ex);
            throw new RuntimeException(ex);
        } catch (TransformerException ex) {
            LOGGER.error("Erro ao transformar o xml ", ex);
            throw new RuntimeException(ex);
        }
        return resultado.getDetail().getFault();
    }
}
