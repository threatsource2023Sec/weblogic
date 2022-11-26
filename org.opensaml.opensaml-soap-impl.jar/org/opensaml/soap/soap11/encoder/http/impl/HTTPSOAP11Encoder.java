package org.opensaml.soap.soap11.encoder.http.impl;

import java.io.IOException;
import java.util.List;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.HttpServletSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.messaging.encoder.servlet.BaseHttpServletResponseXMLMessageEncoder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Body;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.soap11.Header;
import org.opensaml.soap.wsaddressing.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class HTTPSOAP11Encoder extends BaseHttpServletResponseXMLMessageEncoder {
   private final Logger log = LoggerFactory.getLogger(HTTPSOAP11Encoder.class);
   private SOAPObjectBuilder envBuilder;
   private SOAPObjectBuilder bodyBuilder;

   public HTTPSOAP11Encoder() {
      XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
      this.envBuilder = (SOAPObjectBuilder)builderFactory.getBuilder(Envelope.DEFAULT_ELEMENT_NAME);
      this.bodyBuilder = (SOAPObjectBuilder)builderFactory.getBuilder(Body.DEFAULT_ELEMENT_NAME);
      Constraint.isNotNull(this.envBuilder, "Envelope Builder cannot be null");
      Constraint.isNotNull(this.bodyBuilder, "Body Builder cannot be null");
   }

   public void prepareContext() throws MessageEncodingException {
      MessageContext messageContext = this.getMessageContext();
      XMLObject payload = null;
      Fault fault = SOAPMessagingSupport.getSOAP11Fault(messageContext);
      if (fault != null) {
         this.log.debug("Saw SOAP 1.1 Fault payload with fault code, replacing any existing context message: {}", fault.getCode() != null ? fault.getCode().getValue() : null);
         payload = fault;
         messageContext.setMessage((Object)null);
      } else {
         payload = (XMLObject)messageContext.getMessage();
      }

      if (payload == null) {
         throw new MessageEncodingException("No outbound message or Fault contained in message context");
      } else {
         if (payload instanceof Envelope) {
            this.storeSOAPEnvelope((Envelope)payload);
         } else {
            this.buildAndStoreSOAPMessage((XMLObject)payload);
         }

      }
   }

   protected void doEncode() throws MessageEncodingException {
      Envelope envelope = this.getSOAPEnvelope();
      Element envelopeElem = this.marshallMessage(envelope);
      this.prepareHttpServletResponse();

      try {
         SerializeSupport.writeNode(envelopeElem, this.getHttpServletResponse().getOutputStream());
      } catch (IOException var4) {
         throw new MessageEncodingException("Problem writing SOAP envelope to servlet output stream", var4);
      }
   }

   protected void storeSOAPEnvelope(Envelope envelope) {
      ((SOAP11Context)this.getMessageContext().getSubcontext(SOAP11Context.class, true)).setEnvelope(envelope);
   }

   protected Envelope getSOAPEnvelope() {
      return ((SOAP11Context)this.getMessageContext().getSubcontext(SOAP11Context.class, true)).getEnvelope();
   }

   protected void buildAndStoreSOAPMessage(@Nonnull XMLObject payload) {
      Envelope envelope = this.getSOAPEnvelope();
      if (envelope == null) {
         envelope = (Envelope)this.envBuilder.buildObject();
         this.storeSOAPEnvelope(envelope);
      }

      Body body = envelope.getBody();
      if (body == null) {
         body = (Body)this.bodyBuilder.buildObject();
         envelope.setBody(body);
      }

      if (!body.getUnknownXMLObjects().isEmpty()) {
         this.log.warn("Existing SOAP Envelope Body already contained children");
      }

      body.getUnknownXMLObjects().add(payload);
   }

   protected void prepareHttpServletResponse() throws MessageEncodingException {
      HttpServletResponse response = this.getHttpServletResponse();
      HttpServletSupport.addNoCacheHeaders(response);
      HttpServletSupport.setUTF8Encoding(response);
      HttpServletSupport.setContentType(response, "text/xml");
      String soapAction = this.getSOAPAction();
      if (soapAction != null) {
         response.setHeader("SOAPAction", soapAction);
      } else {
         response.setHeader("SOAPAction", "");
      }

      response.setStatus(this.getHTTPResponseStatusCode());
   }

   protected String getSOAPAction() {
      Envelope env = this.getSOAPEnvelope();
      Header header = env.getHeader();
      if (header == null) {
         return null;
      } else {
         List objList = header.getUnknownXMLObjects(Action.ELEMENT_NAME);
         return objList != null && !objList.isEmpty() ? ((Action)objList.get(0)).getValue() : null;
      }
   }

   protected int getHTTPResponseStatusCode() {
      Integer contextStatus = ((SOAP11Context)this.getMessageContext().getSubcontext(SOAP11Context.class, true)).getHTTPResponseStatus();
      if (contextStatus != null) {
         return contextStatus;
      } else {
         Envelope envelope = this.getSOAPEnvelope();
         if (envelope != null && envelope.getBody() != null) {
            Body body = envelope.getBody();
            List faults = body.getUnknownXMLObjects(Fault.DEFAULT_ELEMENT_NAME);
            if (!faults.isEmpty()) {
               return 500;
            }
         }

         return 200;
      }
   }

   protected XMLObject getMessageToLog() {
      return ((SOAP11Context)this.getMessageContext().getSubcontext(SOAP11Context.class, true)).getEnvelope();
   }
}
