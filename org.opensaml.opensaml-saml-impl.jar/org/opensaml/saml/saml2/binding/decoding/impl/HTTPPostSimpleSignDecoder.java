package org.opensaml.saml.saml2.binding.decoding.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;

public class HTTPPostSimpleSignDecoder extends HTTPPostDecoder {
   @Nonnull
   @NotEmpty
   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST-SimpleSign";
   }

   protected void populateBindingContext(MessageContext messageContext) {
      SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true);
      bindingContext.setBindingUri(this.getBindingURI());
      bindingContext.setBindingDescriptor(this.getBindingDescriptor());
      bindingContext.setHasBindingSignature(!Strings.isNullOrEmpty(this.getHttpServletRequest().getParameter("Signature")));
      bindingContext.setIntendedDestinationEndpointURIRequired(SAMLBindingSupport.isMessageSigned(messageContext));
   }
}
