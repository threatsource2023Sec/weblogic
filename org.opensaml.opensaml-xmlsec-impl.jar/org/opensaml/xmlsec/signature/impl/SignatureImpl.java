package org.opensaml.xmlsec.signature.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;

public class SignatureImpl extends AbstractXMLObject implements Signature {
   private String canonicalizationAlgorithm;
   private String signatureAlgorithm;
   private Integer hmacOutputLength;
   private Credential signingCredential;
   private KeyInfo keyInfo;
   private List contentReferences = new LinkedList();
   private XMLSignature xmlSignature;

   protected SignatureImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getCanonicalizationAlgorithm() {
      return this.canonicalizationAlgorithm;
   }

   public void setCanonicalizationAlgorithm(String newAlgorithm) {
      this.canonicalizationAlgorithm = this.prepareForAssignment(this.canonicalizationAlgorithm, newAlgorithm);
   }

   public String getSignatureAlgorithm() {
      return this.signatureAlgorithm;
   }

   public void setSignatureAlgorithm(String newAlgorithm) {
      this.signatureAlgorithm = this.prepareForAssignment(this.signatureAlgorithm, newAlgorithm);
   }

   public Integer getHMACOutputLength() {
      return this.hmacOutputLength;
   }

   public void setHMACOutputLength(Integer length) {
      this.hmacOutputLength = (Integer)this.prepareForAssignment(this.hmacOutputLength, length);
   }

   public Credential getSigningCredential() {
      return this.signingCredential;
   }

   public void setSigningCredential(Credential newCredential) {
      this.signingCredential = (Credential)this.prepareForAssignment(this.signingCredential, newCredential);
   }

   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(KeyInfo newKeyInfo) {
      this.keyInfo = (KeyInfo)this.prepareForAssignment(this.keyInfo, newKeyInfo);
   }

   public List getContentReferences() {
      return this.contentReferences;
   }

   public List getOrderedChildren() {
      return Collections.EMPTY_LIST;
   }

   public void releaseDOM() {
      super.releaseDOM();
      this.xmlSignature = null;
      if (this.keyInfo != null) {
         this.keyInfo.releaseChildrenDOM(true);
         this.keyInfo.releaseDOM();
      }

   }

   public XMLSignature getXMLSignature() {
      return this.xmlSignature;
   }

   public void setXMLSignature(XMLSignature signature) {
      this.xmlSignature = (XMLSignature)this.prepareForAssignment(this.xmlSignature, signature);
   }
}
