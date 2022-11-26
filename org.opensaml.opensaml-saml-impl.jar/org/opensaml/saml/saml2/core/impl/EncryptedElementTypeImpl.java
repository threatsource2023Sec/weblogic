package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.xmlsec.encryption.EncryptedData;

public class EncryptedElementTypeImpl extends AbstractSAMLObject implements EncryptedElementType {
   private EncryptedData encryptedData;
   private final XMLObjectChildrenList encryptedKeys = new XMLObjectChildrenList(this);

   protected EncryptedElementTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public EncryptedData getEncryptedData() {
      return this.encryptedData;
   }

   public void setEncryptedData(EncryptedData newEncryptedData) {
      this.encryptedData = (EncryptedData)this.prepareForAssignment(this.encryptedData, newEncryptedData);
   }

   public List getEncryptedKeys() {
      return this.encryptedKeys;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.encryptedData != null) {
         children.add(this.encryptedData);
      }

      children.addAll(this.encryptedKeys);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
