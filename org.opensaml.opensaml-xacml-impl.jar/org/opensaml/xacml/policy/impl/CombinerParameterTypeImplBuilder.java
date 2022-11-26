package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.CombinerParameterType;

public class CombinerParameterTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public CombinerParameterType buildObject() {
      return (CombinerParameterType)this.buildObject(CombinerParameterType.DEFAULT_ELEMENT_NAME);
   }

   public CombinerParameterType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CombinerParameterTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
