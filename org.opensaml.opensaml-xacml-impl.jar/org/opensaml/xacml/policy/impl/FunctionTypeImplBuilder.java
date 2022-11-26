package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.FunctionType;

public class FunctionTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public FunctionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new FunctionTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public FunctionType buildObject() {
      return (FunctionType)this.buildObject(FunctionType.DEFAULT_ELEMENT_NAME);
   }
}
