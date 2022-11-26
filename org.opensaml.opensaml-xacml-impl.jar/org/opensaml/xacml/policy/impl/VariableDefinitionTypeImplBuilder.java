package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.VariableDefinitionType;

public class VariableDefinitionTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public VariableDefinitionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new VariableDefinitionTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public VariableDefinitionType buildObject() {
      return (VariableDefinitionType)this.buildObject(VariableDefinitionType.DEFAULT_ELEMENT_NAME);
   }
}
