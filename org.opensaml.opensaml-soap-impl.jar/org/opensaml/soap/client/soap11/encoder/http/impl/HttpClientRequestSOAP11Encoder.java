package org.opensaml.soap.client.soap11.encoder.http.impl;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.messaging.encoder.httpclient.BaseHttpClientRequestXMLMessageEncoder;
import org.opensaml.soap.common.SOAPObjectBuilder;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Body;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Header;
import org.opensaml.soap.wsaddressing.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientRequestSOAP11Encoder extends BaseHttpClientRequestXMLMessageEncoder {
   private final Logger log = LoggerFactory.getLogger(HttpClientRequestSOAP11Encoder.class);
   private SOAPObjectBuilder envBuilder;
   private SOAPObjectBuilder bodyBuilder;

   public HttpClientRequestSOAP11Encoder() {
      XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
      this.envBuilder = (SOAPObjectBuilder)builderFactory.getBuilder(Envelope.DEFAULT_ELEMENT_NAME);
      this.bodyBuilder = (SOAPObjectBuilder)builderFactory.getBuilder(Body.DEFAULT_ELEMENT_NAME);
      Constraint.isNotNull(this.envBuilder, "Envelope Builder cannot be null");
      Constraint.isNotNull(this.bodyBuilder, "Body Builder cannot be null");
   }

   @Nullable
   public HttpPost getHttpRequest() {
      return (HttpPost)super.getHttpRequest();
   }

   public synchronized void setHttpRequest(HttpRequest httpRequest) {
      if (!(httpRequest instanceof HttpPost)) {
         throw new IllegalArgumentException("HttpClient SOAP message encoder only operates on HttpPost");
      } else {
         super.setHttpRequest(httpRequest);
      }
   }

   public void prepareContext() throws MessageEncodingException {
      MessageContext messageContext = this.getMessageContext();
      XMLObject message = (XMLObject)messageContext.getMessage();
      if (message == null) {
         throw new MessageEncodingException("No outbound message contained in message context");
      } else {
         if (message instanceof Envelope) {
            this.storeSOAPEnvelope((Envelope)message);
         } else {
            this.buildAndStoreSOAPMessage(message);
         }

      }
   }

   protected void doEncode() throws MessageEncodingException {
      Envelope envelope = this.getSOAPEnvelope();
      this.prepareHttpRequest();
      this.getHttpRequest().setEntity(this.createRequestEntity(envelope, Charset.forName("UTF-8")));
   }

   protected HttpEntity createRequestEntity(@Nonnull Envelope message, @Nullable Charset charset) throws MessageEncodingException {
      try {
         ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
         SerializeSupport.writeNode(XMLObjectSupport.marshall(message), arrayOut);
         return new ByteArrayEntity(arrayOut.toByteArray(), ContentType.create("text/xml", charset));
      } catch (MarshallingException var4) {
         throw new MessageEncodingException("Unable to marshall SOAP envelope", var4);
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

   protected void prepareHttpRequest() throws MessageEncodingException {
      String soapAction = this.getSOAPAction();
      if (soapAction != null) {
         this.getHttpRequest().setHeader("SOAPAction", soapAction);
      } else {
         this.getHttpRequest().setHeader("SOAPAction", "");
      }

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

   protected XMLObject getMessageToLog() {
      return ((SOAP11Context)this.getMessageContext().getSubcontext(SOAP11Context.class, true)).getEnvelope();
   }
}
