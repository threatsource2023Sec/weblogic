package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;

public class KeyDescriptorImpl extends AbstractSAMLObject implements KeyDescriptor {
   private UsageType keyUseType;
   private KeyInfo keyInfo;
   private final XMLObjectChildrenList encryptionMethods = new XMLObjectChildrenList(this);

   protected KeyDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.keyUseType = UsageType.UNSPECIFIED;
   }

   public UsageType getUse() {
      return this.keyUseType;
   }

   public void setUse(UsageType newType) {
      if (newType != null) {
         this.keyUseType = (UsageType)this.prepareForAssignment(this.keyUseType, newType);
      } else {
         this.keyUseType = (UsageType)this.prepareForAssignment(this.keyUseType, UsageType.UNSPECIFIED);
      }

   }

   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(KeyInfo newKeyInfo) {
      this.keyInfo = (KeyInfo)this.prepareForAssignment(this.keyInfo, newKeyInfo);
   }

   public List getEncryptionMethods() {
      return this.encryptionMethods;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.keyInfo);
      children.addAll(this.encryptionMethods);
      return Collections.unmodifiableList(children);
   }
}
