package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.MgmtData;
import org.opensaml.xmlsec.signature.PGPData;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.opensaml.xmlsec.signature.SPKIData;
import org.opensaml.xmlsec.signature.X509Data;

public class KeyInfoImpl extends AbstractXMLObject implements KeyInfo {
   private final IndexedXMLObjectChildrenList indexedChildren = new IndexedXMLObjectChildrenList(this);
   private String id;

   protected KeyInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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

   public List getXMLObjects() {
      return this.indexedChildren;
   }

   public List getXMLObjects(QName typeOrName) {
      return this.indexedChildren.subList(typeOrName);
   }

   public List getKeyNames() {
      return this.indexedChildren.subList(KeyName.DEFAULT_ELEMENT_NAME);
   }

   public List getKeyValues() {
      return this.indexedChildren.subList(KeyValue.DEFAULT_ELEMENT_NAME);
   }

   public List getDEREncodedKeyValues() {
      return this.indexedChildren.subList(DEREncodedKeyValue.DEFAULT_ELEMENT_NAME);
   }

   public List getRetrievalMethods() {
      return this.indexedChildren.subList(RetrievalMethod.DEFAULT_ELEMENT_NAME);
   }

   public List getKeyInfoReferences() {
      return this.indexedChildren.subList(KeyInfoReference.DEFAULT_ELEMENT_NAME);
   }

   public List getX509Datas() {
      return this.indexedChildren.subList(X509Data.DEFAULT_ELEMENT_NAME);
   }

   public List getPGPDatas() {
      return this.indexedChildren.subList(PGPData.DEFAULT_ELEMENT_NAME);
   }

   public List getSPKIDatas() {
      return this.indexedChildren.subList(SPKIData.DEFAULT_ELEMENT_NAME);
   }

   public List getMgmtDatas() {
      return this.indexedChildren.subList(MgmtData.DEFAULT_ELEMENT_NAME);
   }

   public List getAgreementMethods() {
      return this.indexedChildren.subList(AgreementMethod.DEFAULT_ELEMENT_NAME);
   }

   public List getEncryptedKeys() {
      return this.indexedChildren.subList(EncryptedKey.DEFAULT_ELEMENT_NAME);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.indexedChildren);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
