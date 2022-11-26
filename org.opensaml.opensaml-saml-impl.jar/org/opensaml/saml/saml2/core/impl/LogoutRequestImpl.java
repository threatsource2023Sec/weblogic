package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.NameID;

public class LogoutRequestImpl extends RequestAbstractTypeImpl implements LogoutRequest {
   private String reason;
   private DateTime notOnOrAfter;
   private BaseID baseID;
   private NameID nameID;
   private EncryptedID encryptedID;
   private final XMLObjectChildrenList sessionIndexes = new XMLObjectChildrenList(this);

   protected LogoutRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getReason() {
      return this.reason;
   }

   public void setReason(String newReason) {
      this.reason = this.prepareForAssignment(this.reason, newReason);
   }

   public DateTime getNotOnOrAfter() {
      return this.notOnOrAfter;
   }

   public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
      this.notOnOrAfter = this.prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter);
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

   public List getSessionIndexes() {
      return this.sessionIndexes;
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

      children.addAll(this.sessionIndexes);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
