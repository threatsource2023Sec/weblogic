package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.StatusDetailType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class StatusDetailTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public StatusDetailType buildObject() {
      return (StatusDetailType)this.buildObject(StatusDetailType.DEFAULT_ELEMENT_NAME);
   }

   public StatusDetailType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusDetailTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
