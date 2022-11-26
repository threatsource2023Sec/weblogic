package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.ResponseAbstractType;

public abstract class ResponseAbstractTypeImpl extends AbstractSignableSAMLObject implements ResponseAbstractType {
   private String id;
   private SAMLVersion version;
   private String inResponseTo;
   private DateTime issueInstant;
   private String recipient;

   protected ResponseAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.version = SAMLVersion.VERSION_11;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
   }

   public String getInResponseTo() {
      return this.inResponseTo;
   }

   public void setInResponseTo(String to) {
      this.inResponseTo = this.prepareForAssignment(this.inResponseTo, to);
   }

   public SAMLVersion getVersion() {
      return this.version;
   }

   public void setVersion(SAMLVersion newVersion) {
      this.version = (SAMLVersion)this.prepareForAssignment(this.version, newVersion);
   }

   public DateTime getIssueInstant() {
      return this.issueInstant;
   }

   public void setIssueInstant(DateTime date) {
      this.issueInstant = this.prepareForAssignment(this.issueInstant, date);
   }

   public String getRecipient() {
      return this.recipient;
   }

   public void setRecipient(String recip) {
      this.recipient = this.prepareForAssignment(this.recipient, recip);
   }

   public String getSignatureReferenceID() {
      return this.id;
   }

   public List getOrderedChildren() {
      List children = new ArrayList();
      if (this.getSignature() != null) {
         children.add(this.getSignature());
      }

      return Collections.unmodifiableList(children);
   }
}
