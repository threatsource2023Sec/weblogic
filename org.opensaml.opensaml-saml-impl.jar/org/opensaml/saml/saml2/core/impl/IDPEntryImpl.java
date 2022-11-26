package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.IDPEntry;

public class IDPEntryImpl extends AbstractSAMLObject implements IDPEntry {
   private String providerID;
   private String name;
   private String loc;

   protected IDPEntryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getProviderID() {
      return this.providerID;
   }

   public void setProviderID(String newProviderID) {
      this.providerID = this.prepareForAssignment(this.providerID, newProviderID);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String newName) {
      this.name = this.prepareForAssignment(this.name, newName);
   }

   public String getLoc() {
      return this.loc;
   }

   public void setLoc(String newLoc) {
      this.loc = this.prepareForAssignment(this.loc, newLoc);
   }

   public List getOrderedChildren() {
      return null;
   }
}
