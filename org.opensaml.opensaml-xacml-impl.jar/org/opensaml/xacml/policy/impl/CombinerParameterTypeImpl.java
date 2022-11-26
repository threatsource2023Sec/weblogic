package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.AttributeValueType;
import org.opensaml.xacml.policy.CombinerParameterType;

public class CombinerParameterTypeImpl extends AbstractXACMLObject implements CombinerParameterType {
   private String name;
   private AttributeValueType value;

   protected CombinerParameterTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public AttributeValueType getAttributeValue() {
      return this.value;
   }

   public String getParameterName() {
      return this.name;
   }

   public void setAttributeValue(AttributeValueType newValue) {
      this.value = (AttributeValueType)this.prepareForAssignment(this.value, newValue);
   }

   public void setParameterName(String newName) {
      this.name = this.prepareForAssignment(this.name, newName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.value != null) {
         children.add(this.value);
      }

      return Collections.unmodifiableList(children);
   }
}
