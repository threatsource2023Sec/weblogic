package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.RequestAbstractType;

public abstract class RequestAbstractTypeImpl extends AbstractSignableSAMLObject implements RequestAbstractType {
   private String id;
   private DateTime issueInstant;
   private SAMLVersion version;
   private final XMLObjectChildrenList respondWiths;

   protected RequestAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.version = SAMLVersion.VERSION_11;
      this.respondWiths = new XMLObjectChildrenList(this);
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
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

   public void setIssueInstant(DateTime instant) {
      this.issueInstant = this.prepareForAssignment(this.issueInstant, instant);
   }

   public List getRespondWiths() {
      return this.respondWiths;
   }

   public String getSignatureReferenceID() {
      return this.id;
   }

   public List getOrderedChildren() {
      List children = new ArrayList();
      children.addAll(this.respondWiths);
      if (this.getSignature() != null) {
         children.add(this.getSignature());
      }

      return Collections.unmodifiableList(children);
   }
}
