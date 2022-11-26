package org.opensaml.soap.soap11.decoder.http.impl;

import com.google.common.collect.Sets;
import com.google.common.net.MediaType;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.net.HttpServletSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.decoder.servlet.BaseHttpServletRequestXMLMessageDecoder;
import org.opensaml.messaging.handler.MessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPSOAP11Decoder extends BaseHttpServletRequestXMLMessageDecoder {
   private static final Set SUPPORTED_MEDIA_TYPES = Sets.newHashSet(new MediaType[]{MediaType.create("text", "xml")});
   private final Logger log = LoggerFactory.getLogger(HTTPSOAP11Decoder.class);
   private MessageHandler bodyHandler;

   public MessageHandler getBodyHandler() {
      return this.bodyHandler;
   }

   public void setBodyHandler(MessageHandler newBodyHandler) {
      this.bodyHandler = newBodyHandler;
   }

   protected void doDecode() throws MessageDecodingException {
      MessageContext messageContext = new MessageContext();
      HttpServletRequest request = this.getHttpServletRequest();
      if (!"POST".equalsIgnoreCase(request.getMethod())) {
         throw new MessageDecodingException("This message decoder only supports the HTTP POST method");
      } else {
         this.log.debug("Unmarshalling SOAP message");

         try {
            Envelope soapMessage = (Envelope)this.unmarshallMessage(request.getInputStream());
            ((SOAP11Context)messageContext.getSubcontext(SOAP11Context.class, true)).setEnvelope(soapMessage);
         } catch (IOException var6) {
            this.log.error("Unable to obtain input stream from HttpServletRequest", var6);
            throw new MessageDecodingException("Unable to obtain input stream from HttpServletRequest", var6);
         }

         try {
            this.getBodyHandler().invoke(messageContext);
         } catch (MessageHandlerException var5) {
            this.log.error("Error processing SOAP Envelope body", var5);
            throw new MessageDecodingException("Error processing SOAP Envelope body", var5);
         }

         if (messageContext.getMessage() == null) {
            this.log.warn("Body handler did not properly populate the message in message context");
            throw new MessageDecodingException("Body handler did not properly populate the message in message context");
         } else {
            this.setMessageContext(messageContext);
         }
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

   protected void validateHttpRequest(HttpServletRequest request) throws MessageDecodingException {
      super.validateHttpRequest(request);
      if (!HttpServletSupport.validateContentType(request, SUPPORTED_MEDIA_TYPES, false, false)) {
         this.log.warn("Saw unsupported request Content-Type: {}", request.getContentType());
         throw new MessageDecodingException(String.format("Content-Type '%s' was not a supported media type", request.getContentType()));
      }
   }
}
