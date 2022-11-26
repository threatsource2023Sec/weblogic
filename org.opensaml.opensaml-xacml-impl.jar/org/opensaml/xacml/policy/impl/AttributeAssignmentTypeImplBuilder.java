package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.AttributeAssignmentType;

public class AttributeAssignmentTypeImplBuilder extends AbstractXACMLObjectBuilder {
   public AttributeAssignmentType buildObject() {
      return (AttributeAssignmentType)this.buildObject(AttributeAssignmentType.DEFAULT_ELEMENT_NAME);
   }

   public AttributeAssignmentType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AttributeAssignmentTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
