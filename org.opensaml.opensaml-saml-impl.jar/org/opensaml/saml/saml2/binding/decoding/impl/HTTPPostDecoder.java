package org.opensaml.saml.saml2.binding.decoding.impl;

import com.google.common.base.Strings;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.decoder.servlet.BaseHttpServletRequestXMLMessageDecoder;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.BindingDescriptor;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPPostDecoder extends BaseHttpServletRequestXMLMessageDecoder implements SAMLMessageDecoder {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(HTTPPostDecoder.class);
   @Nullable
   private BindingDescriptor bindingDescriptor;

   @Nonnull
   @NotEmpty
   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST";
   }

   @Nullable
   public BindingDescriptor getBindingDescriptor() {
      return this.bindingDescriptor;
   }

   public void setBindingDescriptor(@Nullable BindingDescriptor descriptor) {
      this.bindingDescriptor = descriptor;
   }

   protected void doDecode() throws MessageDecodingException {
      MessageContext messageContext = new MessageContext();
      HttpServletRequest request = this.getHttpServletRequest();
      if (!"POST".equalsIgnoreCase(request.getMethod())) {
         throw new MessageDecodingException("This message decoder only supports the HTTP POST method");
      } else {
         String relayState = request.getParameter("RelayState");
         this.log.debug("Decoded SAML relay state of: {}", relayState);
         SAMLBindingSupport.setRelayState(messageContext, relayState);
         InputStream base64DecodedMessage = this.getBase64DecodedMessage(request);
         SAMLObject inboundMessage = (SAMLObject)this.unmarshallMessage(base64DecodedMessage);
         messageContext.setMessage(inboundMessage);
         this.log.debug("Decoded SAML message");
         this.populateBindingContext(messageContext);
         this.setMessageContext(messageContext);
      }
   }

   protected InputStream getBase64DecodedMessage(HttpServletRequest request) throws MessageDecodingException {
      this.log.debug("Getting Base64 encoded message from request");
      String encodedMessage = request.getParameter("SAMLRequest");
      if (Strings.isNullOrEmpty(encodedMessage)) {
         encodedMessage = request.getParameter("SAMLResponse");
      }

      if (Strings.isNullOrEmpty(encodedMessage)) {
         this.log.error("Request did not contain either a SAMLRequest or SAMLResponse paramter.  Invalid request for SAML 2 HTTP POST binding.");
         throw new MessageDecodingException("No SAML message present in request");
      } else {
         this.log.trace("Base64 decoding SAML message:\n{}", encodedMessage);
         byte[] decodedBytes = Base64Support.decode(encodedMessage);
         if (decodedBytes == null) {
            this.log.error("Unable to Base64 decode SAML message");
            throw new MessageDecodingException("Unable to Base64 decode SAML message");
         } else {
            this.log.trace("Decoded SAML message:\n{}", new String(decodedBytes));
            return new ByteArrayInputStream(decodedBytes);
         }
      }
   }

   protected void populateBindingContext(MessageContext messageContext) {
      SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true);
      bindingContext.setBindingUri(this.getBindingURI());
      bindingContext.setBindingDescriptor(this.bindingDescriptor);
      bindingContext.setHasBindingSignature(false);
      bindingContext.setIntendedDestinationEndpointURIRequired(SAMLBindingSupport.isMessageSigned(messageContext));
   }
}
