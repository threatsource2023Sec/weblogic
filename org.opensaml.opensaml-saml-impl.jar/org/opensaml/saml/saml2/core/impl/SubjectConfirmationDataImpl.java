package org.opensaml.saml.saml2.core.impl;

import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;

public class SubjectConfirmationDataImpl extends AbstractSAMLObject implements SubjectConfirmationData {
   private DateTime notBefore;
   private DateTime notOnOrAfter;
   private String recipient;
   private String inResponseTo;
   private String address;
   private final AttributeMap unknownAttributes = new AttributeMap(this);
   private final IndexedXMLObjectChildrenList unknownChildren = new IndexedXMLObjectChildrenList(this);

   protected SubjectConfirmationDataImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public DateTime getNotBefore() {
      return this.notBefore;
   }

   public void setNotBefore(DateTime newNotBefore) {
      this.notBefore = this.prepareForAssignment(this.notBefore, newNotBefore);
   }

   public DateTime getNotOnOrAfter() {
      return this.notOnOrAfter;
   }

   public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
      this.notOnOrAfter = this.prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter);
   }

   public String getRecipient() {
      return this.recipient;
   }

   public void setRecipient(String newRecipient) {
      this.recipient = this.prepareForAssignment(this.recipient, newRecipient);
   }

   public String getInResponseTo() {
      return this.inResponseTo;
   }

   public void setInResponseTo(String newInResponseTo) {
      this.inResponseTo = this.prepareForAssignment(this.inResponseTo, newInResponseTo);
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String newAddress) {
      this.address = this.prepareForAssignment(this.address, newAddress);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getUnknownXMLObjects() {
      return this.unknownChildren;
   }

   public List getUnknownXMLObjects(QName typeOrName) {
      return this.unknownChildren.subList(typeOrName);
   }

   public List getOrderedChildren() {
      return Collections.unmodifiableList(this.unknownChildren);
   }
}
