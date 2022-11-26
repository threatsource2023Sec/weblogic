package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.EncryptedType;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.signature.KeyInfo;

public abstract class EncryptedTypeImpl extends AbstractXMLObject implements EncryptedType {
   private String id;
   private String type;
   private String mimeType;
   private String encoding;
   private EncryptionMethod encryptionMethod;
   private KeyInfo keyInfo;
   private CipherData cipherData;
   private EncryptionProperties encryptionProperties;

   protected EncryptedTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
   }

   public String getType() {
      return this.type;
   }

   public void setType(String newType) {
      this.type = this.prepareForAssignment(this.type, newType);
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public void setMimeType(String newMimeType) {
      this.mimeType = this.prepareForAssignment(this.mimeType, newMimeType);
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String newEncoding) {
      this.encoding = this.prepareForAssignment(this.encoding, newEncoding);
   }

   public EncryptionMethod getEncryptionMethod() {
      return this.encryptionMethod;
   }

   public void setEncryptionMethod(EncryptionMethod newEncryptionMethod) {
      this.encryptionMethod = (EncryptionMethod)this.prepareForAssignment(this.encryptionMethod, newEncryptionMethod);
   }

   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(KeyInfo newKeyInfo) {
      this.keyInfo = (KeyInfo)this.prepareForAssignment(this.keyInfo, newKeyInfo);
   }

   public CipherData getCipherData() {
      return this.cipherData;
   }

   public void setCipherData(CipherData newCipherData) {
      this.cipherData = (CipherData)this.prepareForAssignment(this.cipherData, newCipherData);
   }

   public EncryptionProperties getEncryptionProperties() {
      return this.encryptionProperties;
   }

   public void setEncryptionProperties(EncryptionProperties newEncryptionProperties) {
      this.encryptionProperties = (EncryptionProperties)this.prepareForAssignment(this.encryptionProperties, newEncryptionProperties);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.encryptionMethod != null) {
         children.add(this.encryptionMethod);
      }

      if (this.keyInfo != null) {
         children.add(this.keyInfo);
      }

      if (this.cipherData != null) {
         children.add(this.cipherData);
      }

      if (this.encryptionProperties != null) {
         children.add(this.encryptionProperties);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
