package org.opensaml.saml.ext.saml2cb.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;

public class ChannelBindingsBuilder extends AbstractSAMLObjectBuilder {
   public ChannelBindings buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:protocol:ext:channel-binding", "ChannelBindings", "cb");
   }

   public ChannelBindings buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ChannelBindingsImpl(namespaceURI, localName, namespacePrefix);
   }
}
