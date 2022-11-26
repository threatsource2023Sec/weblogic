package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AttributeDesignator;

public class AttributeDesignatorImpl extends AbstractSAMLObject implements AttributeDesignator {
   private String attributeName;
   private String attributeNamespace;

   protected AttributeDesignatorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAttributeName() {
      return this.attributeName;
   }

   public void setAttributeName(String name) {
      this.attributeName = this.prepareForAssignment(this.attributeName, name);
   }

   public String getAttributeNamespace() {
      return this.attributeNamespace;
   }

   public void setAttributeNamespace(String ns) {
      this.attributeNamespace = this.prepareForAssignment(this.attributeNamespace, ns);
   }

   public List getOrderedChildren() {
      return null;
   }
}
