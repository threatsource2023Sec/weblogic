package org.opensaml.saml.saml2.binding.encoding.impl;

import java.net.URI;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.messaging.encoder.servlet.BaseHttpServletResponseXMLMessageEncoder;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.encoding.SAMLMessageEncoder;

public abstract class BaseSAML2MessageEncoder extends BaseHttpServletResponseXMLMessageEncoder implements SAMLMessageEncoder {
   protected URI getEndpointURL(MessageContext messageContext) throws MessageEncodingException {
      try {
         return SAMLBindingSupport.getEndpointURL(messageContext);
      } catch (BindingException var3) {
         throw new MessageEncodingException("Could not obtain message endpoint URL", var3);
      }
   }
}
