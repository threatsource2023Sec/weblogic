package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml.saml2.core.NameIDPolicy;

public class NameIDMappingRequestImpl extends RequestAbstractTypeImpl implements NameIDMappingRequest {
   private BaseID baseID;
   private NameID nameID;
   private EncryptedID encryptedID;
   private NameIDPolicy nameIDPolicy;

   protected NameIDMappingRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public BaseID getBaseID() {
      return this.baseID;
   }

   public void setBaseID(BaseID newBaseID) {
      this.baseID = (BaseID)this.prepareForAssignment(this.baseID, newBaseID);
   }

   public NameID getNameID() {
      return this.nameID;
   }

   public void setNameID(NameID newNameID) {
      this.nameID = (NameID)this.prepareForAssignment(this.nameID, newNameID);
   }

   public EncryptedID getEncryptedID() {
      return this.encryptedID;
   }

   public void setEncryptedID(EncryptedID newEncryptedID) {
      this.encryptedID = (EncryptedID)this.prepareForAssignment(this.encryptedID, newEncryptedID);
   }

   public NameIDPolicy getNameIDPolicy() {
      return this.nameIDPolicy;
   }

   public void setNameIDPolicy(NameIDPolicy newNameIDPolicy) {
      this.nameIDPolicy = (NameIDPolicy)this.prepareForAssignment(this.nameIDPolicy, newNameIDPolicy);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      if (this.baseID != null) {
         children.add(this.baseID);
      }

      if (this.nameID != null) {
         children.add(this.nameID);
      }

      if (this.encryptedID != null) {
         children.add(this.encryptedID);
      }

      if (this.nameIDPolicy != null) {
         children.add(this.nameIDPolicy);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
