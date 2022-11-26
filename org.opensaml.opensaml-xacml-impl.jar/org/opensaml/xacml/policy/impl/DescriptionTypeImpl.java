package org.opensaml.xacml.policy.impl;

import java.util.List;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.DescriptionType;

public class DescriptionTypeImpl extends AbstractXACMLObject implements DescriptionType {
   private String description;

   protected DescriptionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValue() {
      return this.description;
   }

   public void setValue(String arg0) {
      this.description = this.prepareForAssignment(this.description, arg0);
   }

   public List getOrderedChildren() {
      return null;
   }
}
