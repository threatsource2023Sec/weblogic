package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class StatusCodeTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public StatusCodeType buildObject() {
      return (StatusCodeType)this.buildObject(StatusCodeType.DEFAULT_ELEMENT_NAME);
   }

   public StatusCodeType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusCodeTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
