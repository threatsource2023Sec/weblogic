package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.CombinerParametersType;

public class CombinerParametersTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public CombinerParametersType buildObject() {
      return (CombinerParametersType)this.buildObject(CombinerParametersType.DEFAULT_ELEMENT_NAME);
   }

   public CombinerParametersType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CombinerParametersTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
