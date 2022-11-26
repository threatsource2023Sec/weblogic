package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ResultType;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ResultTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public ResultType buildObject() {
      return (ResultType)this.buildObject(ResultType.DEFAULT_ELEMENT_NAME);
   }

   public ResultType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ResultTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
