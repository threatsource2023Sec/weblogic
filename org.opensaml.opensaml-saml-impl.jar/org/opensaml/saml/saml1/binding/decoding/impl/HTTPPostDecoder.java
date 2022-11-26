package org.opensaml.saml.saml1.binding.decoding.impl;

import java.io.ByteArrayInputStream;
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
import org.opensaml.saml.saml1.core.ResponseAbstractType;
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
      return "urn:oasis:names:tc:SAML:1.0:profiles:browser-post";
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
         String relayState = request.getParameter("TARGET");
         this.log.debug("Decoded SAML relay state (TARGET parameter) of: {}", relayState);
         SAMLBindingSupport.setRelayState(messageContext, relayState);
         String base64Message = request.getParameter("SAMLResponse");
         byte[] decodedBytes = Base64Support.decode(base64Message);
         if (decodedBytes == null) {
            this.log.error("Unable to Base64 decode SAML message");
            throw new MessageDecodingException("Unable to Base64 decode SAML message");
         } else {
            SAMLObject inboundMessage = (SAMLObject)this.unmarshallMessage(new ByteArrayInputStream(decodedBytes));
            messageContext.setMessage(inboundMessage);
            this.log.debug("Decoded SAML message");
            this.populateBindingContext(messageContext);
            this.setMessageContext(messageContext);
         }
      }
   }

   protected void populateBindingContext(MessageContext messageContext) {
      SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true);
      bindingContext.setBindingUri(this.getBindingURI());
      bindingContext.setBindingDescriptor(this.bindingDescriptor);
      bindingContext.setHasBindingSignature(false);
      bindingContext.setIntendedDestinationEndpointURIRequired(messageContext.getMessage() instanceof ResponseAbstractType);
   }
}
