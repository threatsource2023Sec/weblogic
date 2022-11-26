package org.opensaml.saml.ext.saml2delrestrict.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;

public class DelegateImpl extends AbstractSAMLObject implements Delegate {
   private BaseID baseID;
   private NameID nameID;
   private EncryptedID encryptedID;
   private DateTime delegationInstant;
   private String confirmationMethod;

   protected DelegateImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public BaseID getBaseID() {
      return this.baseID;
   }

   public String getConfirmationMethod() {
      return this.confirmationMethod;
   }

   public DateTime getDelegationInstant() {
      return this.delegationInstant;
   }

   public EncryptedID getEncryptedID() {
      return this.encryptedID;
   }

   public NameID getNameID() {
      return this.nameID;
   }

   public void setBaseID(BaseID newBaseID) {
      this.baseID = (BaseID)this.prepareForAssignment(this.baseID, newBaseID);
   }

   public void setConfirmationMethod(String newMethod) {
      this.confirmationMethod = this.prepareForAssignment(this.confirmationMethod, newMethod);
   }

   public void setDelegationInstant(DateTime newInstant) {
      this.delegationInstant = this.prepareForAssignment(this.delegationInstant, newInstant);
   }

   public void setEncryptedID(EncryptedID newEncryptedID) {
      this.encryptedID = (EncryptedID)this.prepareForAssignment(this.encryptedID, newEncryptedID);
   }

   public void setNameID(NameID newNameID) {
      this.nameID = (NameID)this.prepareForAssignment(this.nameID, newNameID);
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

      return Collections.unmodifiableList(children);
   }
}
