package org.opensaml.saml.saml2.binding.decoding.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.BindingDescriptor;
import org.opensaml.saml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.saml.common.binding.impl.SAMLSOAPDecoderBodyHandler;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPSOAP11Decoder extends org.opensaml.soap.soap11.decoder.http.impl.HTTPSOAP11Decoder implements SAMLMessageDecoder {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(HTTPSOAP11Decoder.class);
   @Nullable
   private BindingDescriptor bindingDescriptor;

   public HTTPSOAP11Decoder() {
      this.setBodyHandler(new SAMLSOAPDecoderBodyHandler());
   }

   @Nonnull
   @NotEmpty
   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:SOAP";
   }

   @Nullable
   public BindingDescriptor getBindingDescriptor() {
      return this.bindingDescriptor;
   }

   public void setBindingDescriptor(@Nullable BindingDescriptor descriptor) {
      this.bindingDescriptor = descriptor;
   }

   protected void doDecode() throws MessageDecodingException {
      super.doDecode();
      this.populateBindingContext(this.getMessageContext());
      SAMLObject samlMessage = (SAMLObject)this.getMessageContext().getMessage();
      this.log.debug("Decoded SOAP messaged which included SAML message of type {}", samlMessage.getElementQName());
   }

   protected void populateBindingContext(MessageContext messageContext) {
      SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true);
      bindingContext.setBindingUri(this.getBindingURI());
      bindingContext.setBindingDescriptor(this.bindingDescriptor);
      bindingContext.setHasBindingSignature(false);
      bindingContext.setIntendedDestinationEndpointURIRequired(false);
   }
}
