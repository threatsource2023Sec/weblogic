package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.AssertionURIRef;

public class AssertionURIRefImpl extends AbstractSAMLObject implements AssertionURIRef {
   private String assertionURI;

   protected AssertionURIRefImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAssertionURI() {
      return this.assertionURI;
   }

   public void setAssertionURI(String newAssertionURI) {
      this.assertionURI = this.prepareForAssignment(this.assertionURI, newAssertionURI);
   }

   public List getOrderedChildren() {
      return null;
   }
}
