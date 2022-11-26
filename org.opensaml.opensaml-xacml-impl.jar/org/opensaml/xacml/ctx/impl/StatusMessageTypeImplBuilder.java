package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class StatusMessageTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public StatusMessageType buildObject() {
      return this.buildObject("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusMessage", "xacml-context");
   }

   public StatusMessageType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusMessageTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
