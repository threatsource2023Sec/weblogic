package org.opensaml.saml.saml2.binding.security.impl;

import com.google.common.base.Strings;
import java.io.UnsupportedEncodingException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.net.URISupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.binding.security.impl.BaseSAMLSimpleSignatureSecurityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAML2HTTPRedirectDeflateSignatureSecurityHandler extends BaseSAMLSimpleSignatureSecurityHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAML2HTTPRedirectDeflateSignatureSecurityHandler.class);

   protected boolean ruleHandles(@Nonnull MessageContext messgaeContext) throws MessageHandlerException {
      return "GET".equals(this.getHttpServletRequest().getMethod());
   }

   @Nullable
   protected byte[] getSignedContent() throws MessageHandlerException {
      String queryString = this.getHttpServletRequest().getQueryString();
      this.log.debug("Constructing signed content string from URL query string {}", queryString);
      String constructed = this.buildSignedContentString(queryString);
      if (Strings.isNullOrEmpty(constructed)) {
         this.log.warn("Could not extract signed content string from query string");
         return null;
      } else {
         this.log.debug("Constructed signed content string for HTTP-Redirect DEFLATE {}", constructed);

         try {
            return constructed.getBytes("UTF-8");
         } catch (UnsupportedEncodingException var4) {
            return null;
         }
      }
   }

   @Nonnull
   @NotEmpty
   private String buildSignedContentString(String queryString) throws MessageHandlerException {
      StringBuilder builder = new StringBuilder();
      if (!this.appendParameter(builder, queryString, "SAMLRequest") && !this.appendParameter(builder, queryString, "SAMLResponse")) {
         this.log.warn("Could not extract either a SAMLRequest or a SAMLResponse from the query string");
         throw new MessageHandlerException("Extract of SAMLRequest or SAMLResponse from query string failed");
      } else {
         this.appendParameter(builder, queryString, "RelayState");
         this.appendParameter(builder, queryString, "SigAlg");
         return builder.toString();
      }
   }

   private boolean appendParameter(@Nonnull StringBuilder builder, @Nullable String queryString, @Nullable String paramName) {
      String rawParam = URISupport.getRawQueryStringParameter(queryString, paramName);
      if (rawParam == null) {
         return false;
      } else {
         if (builder.length() > 0) {
            builder.append('&');
         }

         builder.append(rawParam);
         return true;
      }
   }
}
