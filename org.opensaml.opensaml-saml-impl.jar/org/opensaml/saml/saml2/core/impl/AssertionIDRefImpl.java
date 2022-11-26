package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AssertionIDRef;

public class AssertionIDRefImpl extends AbstractSAMLObject implements AssertionIDRef {
   private String assertionID;

   protected AssertionIDRefImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAssertionID() {
      return this.assertionID;
   }

   public void setAssertionID(String newIDRef) {
      this.assertionID = this.prepareForAssignment(this.assertionID, newIDRef);
   }

   public List getOrderedChildren() {
      return null;
   }
}
