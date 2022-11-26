package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.opensaml.xmlsec.encryption.KANonce;
import org.opensaml.xmlsec.encryption.OriginatorKeyInfo;
import org.opensaml.xmlsec.encryption.RecipientKeyInfo;

public class AgreementMethodImpl extends AbstractXMLObject implements AgreementMethod {
   private String algorithm;
   private KANonce kaNonce;
   private OriginatorKeyInfo originatorKeyInfo;
   private RecipientKeyInfo recipientKeyInfo;
   private IndexedXMLObjectChildrenList xmlChildren = new IndexedXMLObjectChildrenList(this);

   protected AgreementMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String newAlgorithm) {
      this.algorithm = this.prepareForAssignment(this.algorithm, newAlgorithm);
   }

   public KANonce getKANonce() {
      return this.kaNonce;
   }

   public void setKANonce(KANonce newKANonce) {
      this.kaNonce = (KANonce)this.prepareForAssignment(this.kaNonce, newKANonce);
   }

   public OriginatorKeyInfo getOriginatorKeyInfo() {
      return this.originatorKeyInfo;
   }

   public void setOriginatorKeyInfo(OriginatorKeyInfo newOriginatorKeyInfo) {
      this.originatorKeyInfo = (OriginatorKeyInfo)this.prepareForAssignment(this.originatorKeyInfo, newOriginatorKeyInfo);
   }

   public RecipientKeyInfo getRecipientKeyInfo() {
      return this.recipientKeyInfo;
   }

   public void setRecipientKeyInfo(RecipientKeyInfo newRecipientKeyInfo) {
      this.recipientKeyInfo = (RecipientKeyInfo)this.prepareForAssignment(this.recipientKeyInfo, newRecipientKeyInfo);
   }

   public List getUnknownXMLObjects() {
      return this.xmlChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.xmlChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.kaNonce != null) {
         children.add(this.kaNonce);
      }

      children.addAll(this.xmlChildren);
      if (this.originatorKeyInfo != null) {
         children.add(this.originatorKeyInfo);
      }

      if (this.recipientKeyInfo != null) {
         children.add(this.recipientKeyInfo);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
