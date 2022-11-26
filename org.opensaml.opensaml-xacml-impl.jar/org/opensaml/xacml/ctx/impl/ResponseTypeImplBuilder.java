package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ResponseType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ResponseTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ResponseType buildObject() {
      return (ResponseType)this.buildObject(ResponseType.DEFAULT_ELEMENT_NAME);
   }

   public ResponseType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ResponseTypeImpl(namespaceURI, localName, "xacml-context");
   }
}
