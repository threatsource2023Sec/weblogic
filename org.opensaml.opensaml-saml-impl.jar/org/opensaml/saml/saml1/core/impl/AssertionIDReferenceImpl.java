package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AssertionIDReference;

public class AssertionIDReferenceImpl extends AbstractSAMLObject implements AssertionIDReference {
   private String ncName;

   protected AssertionIDReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getReference() {
      return this.ncName;
   }

   public void setReference(String name) {
      this.ncName = this.prepareForAssignment(this.ncName, name);
   }

   public List getOrderedChildren() {
      return null;
   }
}
