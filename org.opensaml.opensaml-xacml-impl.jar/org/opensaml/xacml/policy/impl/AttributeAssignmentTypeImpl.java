package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.policy.AttributeAssignmentType;

public class AttributeAssignmentTypeImpl extends AttributeValueTypeImpl implements AttributeAssignmentType {
   private String attributeId;

   protected AttributeAssignmentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAttributeId() {
      return this.attributeId;
   }

   public void setAttributeId(String newAttributeID) {
      this.attributeId = this.prepareForAssignment(this.attributeId, newAttributeID);
   }
}
