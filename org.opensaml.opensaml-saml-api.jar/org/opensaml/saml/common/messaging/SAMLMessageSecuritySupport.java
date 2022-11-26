package org.opensaml.saml.common.messaging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.security.SecurityException;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.signature.SignableXMLObject;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignatureSupport;

public final class SAMLMessageSecuritySupport {
   private SAMLMessageSecuritySupport() {
   }

   public static void signMessage(@Nonnull MessageContext messageContext) throws SecurityException, MarshallingException, SignatureException {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      SAMLObject outboundSAML = (SAMLObject)messageContext.getMessage();
      SignatureSigningParameters parameters = getContextSigningParameters(messageContext);
      if (outboundSAML instanceof SignableXMLObject && parameters != null) {
         SignatureSupport.signObject((SignableXMLObject)outboundSAML, parameters);
      }

   }

   @Nullable
   public static SignatureSigningParameters getContextSigningParameters(@Nonnull MessageContext messageContext) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      SecurityParametersContext context = (SecurityParametersContext)messageContext.getSubcontext(SecurityParametersContext.class);
      return context != null ? context.getSignatureSigningParameters() : null;
   }

   public static boolean checkURLScheme(@Nonnull @NotEmpty String scheme) {
      String normalized = StringSupport.trimOrNull(scheme);
      return normalized == null ? false : SAMLConfigurationSupport.getAllowedBindingURLSchemes().contains(normalized.toLowerCase());
   }
}
