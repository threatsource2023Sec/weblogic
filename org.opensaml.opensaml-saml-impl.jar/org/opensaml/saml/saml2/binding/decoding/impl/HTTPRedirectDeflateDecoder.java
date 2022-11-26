package org.opensaml.saml.saml2.binding.decoding.impl;

import com.google.common.base.Strings;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
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

public class HTTPRedirectDeflateDecoder extends BaseHttpServletRequestXMLMessageDecoder implements SAMLMessageDecoder {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(HTTPRedirectDeflateDecoder.class);
   @Nullable
   private BindingDescriptor bindingDescriptor;

   @Nonnull
   @NotEmpty
   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
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
      if (!"GET".equalsIgnoreCase(request.getMethod())) {
         throw new MessageDecodingException("This message decoder only supports the HTTP GET method");
      } else {
         String samlEncoding = StringSupport.trimOrNull(request.getParameter("SAMLEncoding"));
         if (samlEncoding != null && !"urn:oasis:names:tc:SAML:2.0:bindings:URL-Encoding:DEFLATE".equals(samlEncoding)) {
            throw new MessageDecodingException("Request indicated an unsupported SAMLEncoding: " + samlEncoding);
         } else {
            String relayState = request.getParameter("RelayState");
            this.log.debug("Decoded RelayState: {}", relayState);
            SAMLBindingSupport.setRelayState(messageContext, relayState);
            InputStream samlMessageIns;
            if (!Strings.isNullOrEmpty(request.getParameter("SAMLRequest"))) {
               samlMessageIns = this.decodeMessage(request.getParameter("SAMLRequest"));
            } else {
               if (Strings.isNullOrEmpty(request.getParameter("SAMLResponse"))) {
                  throw new MessageDecodingException("No SAMLRequest or SAMLResponse query path parameter, invalid SAML 2 HTTP Redirect message");
               }

               samlMessageIns = this.decodeMessage(request.getParameter("SAMLResponse"));
            }

            SAMLObject samlMessage = (SAMLObject)this.unmarshallMessage(samlMessageIns);
            messageContext.setMessage(samlMessage);
            this.log.debug("Decoded SAML message");
            this.populateBindingContext(messageContext);
            this.setMessageContext(messageContext);
         }
      }
   }

   protected InputStream decodeMessage(String message) throws MessageDecodingException {
      this.log.debug("Base64 decoding and inflating SAML message");
      byte[] decodedBytes = Base64Support.decode(message);
      if (decodedBytes == null) {
         this.log.error("Unable to Base64 decode incoming message");
         throw new MessageDecodingException("Unable to Base64 decode incoming message");
      } else {
         try {
            ByteArrayInputStream bytesIn = new ByteArrayInputStream(decodedBytes);
            InflaterInputStream inflater = new InflaterInputStream(bytesIn, new Inflater(true));
            return inflater;
         } catch (Exception var5) {
            this.log.error("Unable to Base64 decode and inflate SAML message", var5);
            throw new MessageDecodingException("Unable to Base64 decode and inflate SAML message", var5);
         }
      }
   }

   protected void populateBindingContext(MessageContext messageContext) {
      SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true);
      bindingContext.setBindingUri(this.getBindingURI());
      bindingContext.setBindingDescriptor(this.bindingDescriptor);
      bindingContext.setHasBindingSignature(!Strings.isNullOrEmpty(this.getHttpServletRequest().getParameter("Signature")));
      bindingContext.setIntendedDestinationEndpointURIRequired(SAMLBindingSupport.isMessageSigned(messageContext));
   }
}
