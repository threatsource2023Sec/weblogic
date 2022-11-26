package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.GetComplete;

public class GetCompleteImpl extends AbstractSAMLObject implements GetComplete {
   private String getComplete;

   protected GetCompleteImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getGetComplete() {
      return this.getComplete;
   }

   public void setGetComplete(String newGetComplete) {
      this.getComplete = this.prepareForAssignment(this.getComplete, newGetComplete);
   }

   public List getOrderedChildren() {
      return null;
   }
}
