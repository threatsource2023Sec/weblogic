package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.AffiliateMember;

public class AffiliateMemberImpl extends AbstractSAMLObject implements AffiliateMember {
   private String id;

   protected AffiliateMemberImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      if (newID != null && newID.length() > 1024) {
         throw new IllegalArgumentException("Member ID can not exceed 1024 characters in length");
      } else {
         this.id = this.prepareForAssignment(this.id, newID);
      }
   }

   public List getOrderedChildren() {
      return null;
   }
}
