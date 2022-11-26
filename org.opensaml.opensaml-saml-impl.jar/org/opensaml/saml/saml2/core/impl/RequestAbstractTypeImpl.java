package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.RequestAbstractType;

public abstract class RequestAbstractTypeImpl extends AbstractSignableSAMLObject implements RequestAbstractType {
   private SAMLVersion version;
   private String id;
   private DateTime issueInstant;
   private String destination;
   private String consent;
   private Issuer issuer;
   private Extensions extensions;

   protected RequestAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.version = SAMLVersion.VERSION_20;
   }

   public SAMLVersion getVersion() {
      return this.version;
   }

   public void setVersion(SAMLVersion newVersion) {
      this.version = (SAMLVersion)this.prepareForAssignment(this.version, newVersion);
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
   }

   public DateTime getIssueInstant() {
      return this.issueInstant;
   }

   public void setIssueInstant(DateTime newIssueInstant) {
      this.issueInstant = this.prepareForAssignment(this.issueInstant, newIssueInstant);
   }

   public String getDestination() {
      return this.destination;
   }

   public void setDestination(String newDestination) {
      this.destination = this.prepareForAssignment(this.destination, newDestination);
   }

   public String getConsent() {
      return this.consent;
   }

   public void setConsent(String newConsent) {
      this.consent = this.prepareForAssignment(this.consent, newConsent);
   }

   public Issuer getIssuer() {
      return this.issuer;
   }

   public void setIssuer(Issuer newIssuer) {
      this.issuer = (Issuer)this.prepareForAssignment(this.issuer, newIssuer);
   }

   public Extensions getExtensions() {
      return this.extensions;
   }

   public void setExtensions(Extensions newExtensions) {
      this.extensions = (Extensions)this.prepareForAssignment(this.extensions, newExtensions);
   }

   public String getSignatureReferenceID() {
      return this.id;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.issuer != null) {
         children.add(this.issuer);
      }

      if (this.getSignature() != null) {
         children.add(this.getSignature());
      }

      if (this.extensions != null) {
         children.add(this.extensions);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
