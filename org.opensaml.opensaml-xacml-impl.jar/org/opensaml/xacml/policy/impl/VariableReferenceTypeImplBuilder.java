package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.VariableReferenceType;

public class VariableReferenceTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public VariableReferenceType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new VariableReferenceTypeImpl(namespaceURI, localName, namespacePrefix);
   }

   public VariableReferenceType buildObject() {
      return (VariableReferenceType)this.buildObject(VariableReferenceType.DEFAULT_ELEMENT_NAME_XACML20);
   }
}
