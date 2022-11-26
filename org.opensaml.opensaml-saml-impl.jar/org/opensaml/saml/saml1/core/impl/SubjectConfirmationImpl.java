package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.SubjectConfirmation;
import org.opensaml.xmlsec.signature.KeyInfo;

public class SubjectConfirmationImpl extends AbstractSAMLObject implements SubjectConfirmation {
   private final XMLObjectChildrenList confirmationMethods = new XMLObjectChildrenList(this);
   private XMLObject subjectConfirmationData;
   private KeyInfo keyInfo;

   protected SubjectConfirmationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getConfirmationMethods() {
      return this.confirmationMethods;
   }

   public void setSubjectConfirmationData(XMLObject data) {
      this.subjectConfirmationData = this.prepareForAssignment(this.subjectConfirmationData, data);
   }

   public XMLObject getSubjectConfirmationData() {
      return this.subjectConfirmationData;
   }

   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(KeyInfo info) {
      this.keyInfo = (KeyInfo)this.prepareForAssignment(this.keyInfo, info);
   }

   public List getOrderedChildren() {
      List list = new ArrayList(this.confirmationMethods.size() + 1);
      list.addAll(this.confirmationMethods);
      if (this.subjectConfirmationData != null) {
         list.add(this.subjectConfirmationData);
      }

      if (this.keyInfo != null) {
         list.add(this.keyInfo);
      }

      return list.size() == 0 ? null : Collections.unmodifiableList(list);
   }
}
