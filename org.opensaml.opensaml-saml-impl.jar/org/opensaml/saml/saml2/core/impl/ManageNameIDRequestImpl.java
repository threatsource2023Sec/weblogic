package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.saml.saml2.core.Terminate;

public class ManageNameIDRequestImpl extends RequestAbstractTypeImpl implements ManageNameIDRequest {
   private NameID nameID;
   private EncryptedID encryptedID;
   private NewID newID;
   private NewEncryptedID newEncryptedID;
   private Terminate terminate;

   protected ManageNameIDRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
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

   public void setEncryptedID(EncryptedID newEncID) {
      this.encryptedID = (EncryptedID)this.prepareForAssignment(this.encryptedID, newEncID);
   }

   public NewID getNewID() {
      return this.newID;
   }

   public void setNewID(NewID newNewID) {
      this.newID = (NewID)this.prepareForAssignment(this.newID, newNewID);
   }

   public NewEncryptedID getNewEncryptedID() {
      return this.newEncryptedID;
   }

   public void setNewEncryptedID(NewEncryptedID newNewEncryptedID) {
      this.newEncryptedID = (NewEncryptedID)this.prepareForAssignment(this.newEncryptedID, newNewEncryptedID);
   }

   public Terminate getTerminate() {
      return this.terminate;
   }

   public void setTerminate(Terminate newTerminate) {
      this.terminate = (Terminate)this.prepareForAssignment(this.terminate, newTerminate);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      if (this.nameID != null) {
         children.add(this.nameID);
      }

      if (this.encryptedID != null) {
         children.add(this.encryptedID);
      }

      if (this.newID != null) {
         children.add(this.newID);
      }

      if (this.newEncryptedID != null) {
         children.add(this.newEncryptedID);
      }

      if (this.terminate != null) {
         children.add(this.terminate);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
