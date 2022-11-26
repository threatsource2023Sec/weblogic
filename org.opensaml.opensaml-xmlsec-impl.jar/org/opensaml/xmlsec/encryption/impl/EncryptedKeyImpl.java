package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.xmlsec.encryption.CarriedKeyName;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.ReferenceList;

public class EncryptedKeyImpl extends EncryptedTypeImpl implements EncryptedKey {
   private String recipient;
   private CarriedKeyName carriedKeyName;
   private ReferenceList referenceList;

   protected EncryptedKeyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getRecipient() {
      return this.recipient;
   }

   public void setRecipient(String newRecipient) {
      this.recipient = this.prepareForAssignment(this.recipient, newRecipient);
   }

   public ReferenceList getReferenceList() {
      return this.referenceList;
   }

   public void setReferenceList(ReferenceList newReferenceList) {
      this.referenceList = (ReferenceList)this.prepareForAssignment(this.referenceList, newReferenceList);
   }

   public CarriedKeyName getCarriedKeyName() {
      return this.carriedKeyName;
   }

   public void setCarriedKeyName(CarriedKeyName newCarriedKeyName) {
      this.carriedKeyName = (CarriedKeyName)this.prepareForAssignment(this.carriedKeyName, newCarriedKeyName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      if (this.referenceList != null) {
         children.add(this.referenceList);
      }

      if (this.carriedKeyName != null) {
         children.add(this.carriedKeyName);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
