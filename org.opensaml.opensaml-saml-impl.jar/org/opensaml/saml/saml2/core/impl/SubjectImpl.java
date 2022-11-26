package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Subject;

public class SubjectImpl extends AbstractSAMLObject implements Subject {
   private BaseID baseID;
   private NameID nameID;
   private EncryptedID encryptedID;
   private final XMLObjectChildrenList subjectConfirmations = new XMLObjectChildrenList(this);

   protected SubjectImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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

   public List getSubjectConfirmations() {
      return this.subjectConfirmations;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.baseID != null) {
         children.add(this.baseID);
      }

      if (this.nameID != null) {
         children.add(this.nameID);
      }

      if (this.encryptedID != null) {
         children.add(this.encryptedID);
      }

      children.addAll(this.subjectConfirmations);
      return Collections.unmodifiableList(children);
   }
}
