package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.KeySize;
import org.opensaml.xmlsec.encryption.OAEPparams;

public class EncryptionMethodImpl extends AbstractXMLObject implements EncryptionMethod {
   private String algorithm;
   private KeySize keySize;
   private OAEPparams oaepParams;
   private final IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   protected EncryptionMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String newAlgorithm) {
      this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
   }

   public KeySize getKeySize() {
      return this.keySize;
   }

   public void setKeySize(KeySize newKeySize) {
      this.keySize = (KeySize)this.prepareForAssignment(this.keySize, newKeySize);
   }

   public OAEPparams getOAEPparams() {
      return this.oaepParams;
   }

   public void setOAEPparams(OAEPparams newOAEPparams) {
      this.oaepParams = (OAEPparams)this.prepareForAssignment(this.oaepParams, newOAEPparams);
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.keySize != null) {
         children.add(this.keySize);
      }

      if (this.oaepParams != null) {
         children.add(this.oaepParams);
      }

      children.addAll(this.unknownChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
