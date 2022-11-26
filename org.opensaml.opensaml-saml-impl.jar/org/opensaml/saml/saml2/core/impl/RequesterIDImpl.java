package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.RequesterID;

public class RequesterIDImpl extends AbstractSAMLObject implements RequesterID {
   private String requesterID;

   protected RequesterIDImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getRequesterID() {
      return this.requesterID;
   }

   public void setRequesterID(String newRequesterID) {
      this.requesterID = this.prepareForAssignment(this.requesterID, newRequesterID);
   }

   public List getOrderedChildren() {
      return null;
   }
}
