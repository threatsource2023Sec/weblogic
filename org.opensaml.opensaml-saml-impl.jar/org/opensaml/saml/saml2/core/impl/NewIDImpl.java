package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.NewID;

public class NewIDImpl extends AbstractSAMLObject implements NewID {
   private String newID;

   protected NewIDImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getNewID() {
      return this.newID;
   }

   public void setNewID(String newNewID) {
      this.newID = this.prepareForAssignment(this.newID, newNewID);
   }

   public List getOrderedChildren() {
      return null;
   }
}
