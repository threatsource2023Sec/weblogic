package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class StatusTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public StatusType buildObject() {
      return (StatusType)this.buildObject(StatusType.DEFAULT_ELEMENT_NAME);
   }

   public StatusType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
