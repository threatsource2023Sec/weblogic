package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;

public class SubjectConfirmationImpl extends AbstractSAMLObject implements SubjectConfirmation {
   private BaseID baseID;
   private NameID nameID;
   private EncryptedID encryptedID;
   private SubjectConfirmationData subjectConfirmationData;
   private String method;

   protected SubjectConfirmationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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

   public SubjectConfirmationData getSubjectConfirmationData() {
      return this.subjectConfirmationData;
   }

   public void setSubjectConfirmationData(SubjectConfirmationData newSubjectConfirmationData) {
      this.subjectConfirmationData = (SubjectConfirmationData)this.prepareForAssignment(this.subjectConfirmationData, newSubjectConfirmationData);
   }

   public String getMethod() {
      return this.method;
   }

   public void setMethod(String newMethod) {
      this.method = this.prepareForAssignment(this.method, newMethod);
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

      children.add(this.subjectConfirmationData);
      return Collections.unmodifiableList(children);
   }
}
