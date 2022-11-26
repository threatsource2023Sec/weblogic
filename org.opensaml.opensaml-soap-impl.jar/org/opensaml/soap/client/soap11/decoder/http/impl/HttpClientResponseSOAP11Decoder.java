package org.opensaml.soap.client.soap11.decoder.http.impl;

import java.io.IOException;
import java.util.List;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.decoder.httpclient.BaseHttpClientResponseXMLMessageDecoder;
import org.opensaml.messaging.handler.MessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.common.SOAP11FaultDecodingException;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientResponseSOAP11Decoder extends BaseHttpClientResponseXMLMessageDecoder {
   private final Logger log = LoggerFactory.getLogger(HttpClientResponseSOAP11Decoder.class);
   private MessageHandler bodyHandler;

   public MessageHandler getBodyHandler() {
      return this.bodyHandler;
   }

   public void setBodyHandler(MessageHandler newBodyHandler) {
      this.bodyHandler = newBodyHandler;
   }

   protected void doDecode() throws MessageDecodingException {
      MessageContext messageContext = new MessageContext();
      HttpResponse response = this.getHttpResponse();
      this.log.debug("Unmarshalling SOAP message");

      try {
         int responseStatusCode = response.getStatusLine().getStatusCode();
         switch (responseStatusCode) {
            case 200:
               SOAP11Context soapContext = (SOAP11Context)messageContext.getSubcontext(SOAP11Context.class, true);
               this.processSuccessResponse(response, soapContext);
               break;
            case 500:
               throw this.buildFaultException(response);
            default:
               throw new MessageDecodingException("Received non-success HTTP response status code from SOAP call: " + responseStatusCode);
         }
      } catch (IOException var14) {
         this.log.error("Unable to obtain input stream from HttpResponse", var14);
         throw new MessageDecodingException("Unable to obtain input stream from HttpResponse", var14);
      } finally {
         if (response instanceof CloseableHttpResponse) {
            try {
               ((CloseableHttpResponse)response).close();
            } catch (IOException var12) {
               this.log.warn("Error closing HttpResponse", var12);
            }
         }

      }

      try {
         this.getBodyHandler().invoke(messageContext);
      } catch (MessageHandlerException var13) {
         this.log.error("Error processing SOAP Envelope body", var13);
         throw new MessageDecodingException("Error processing SOAP Envelope body", var13);
      }

      if (messageContext.getMessage() == null) {
         this.log.warn("Body handler did not properly populate the message in message context");
         throw new MessageDecodingException("Body handler did not properly populate the message in message context");
      } else {
         this.setMessageContext(messageContext);
      }
   }

   protected void processSuccessResponse(HttpResponse httpResponse, SOAP11Context soapContext) throws MessageDecodingException, IOException {
      if (httpResponse.getEntity() == null) {
         throw new MessageDecodingException("No response body from server");
      } else {
         Envelope soapMessage = (Envelope)this.unmarshallMessage(httpResponse.getEntity().getContent());
         Fault fault = this.getFault(soapMessage);
         if (fault != null) {
            throw new SOAP11FaultDecodingException(fault);
         } else {
            soapContext.setEnvelope(soapMessage);
            soapContext.setHTTPResponseStatus(httpResponse.getStatusLine().getStatusCode());
         }
      }
   }

   protected MessageDecodingException buildFaultException(HttpResponse response) throws MessageDecodingException, IOException {
      if (response.getEntity() == null) {
         throw new MessageDecodingException("No response body from server");
      } else {
         Envelope soapMessage = (Envelope)this.unmarshallMessage(response.getEntity().getContent());
         Fault fault = this.getFault(soapMessage);
         if (fault == null) {
            throw new MessageDecodingException("HTTP status code was 500 but SOAP response did not contain a Fault");
         } else {
            QName code = null;
            if (fault.getCode() != null) {
               code = fault.getCode().getValue();
            }

            String msg = null;
            if (fault.getMessage() != null) {
               msg = fault.getMessage().getValue();
            }

            this.log.debug("SOAP fault code '{}' with message '{}'", code != null ? code.toString() : "(not set)", msg);
            return new SOAP11FaultDecodingException(fault);
         }
      }
   }

   protected Fault getFault(Envelope soapMessage) {
      if (soapMessage.getBody() != null) {
         List faults = soapMessage.getBody().getUnknownXMLObjects(Fault.DEFAULT_ELEMENT_NAME);
         return !faults.isEmpty() ? (Fault)faults.get(0) : null;
      } else {
         return null;
      }
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.getBodyHandler() == null) {
         throw new ComponentInitializationException("Body handler MessageHandler cannot be null");
      }
   }

   protected XMLObject getMessageToLog() {
      return ((SOAP11Context)this.getMessageContext().getSubcontext(SOAP11Context.class, true)).getEnvelope();
   }
}
