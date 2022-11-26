package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.EnvironmentsType;

public class EnvironmentsTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public EnvironmentsType buildObject() {
      return (EnvironmentsType)this.buildObject(EnvironmentsType.DEFAULT_ELEMENT_NAME);
   }

   public EnvironmentsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EnvironmentsTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
