package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;

public class AffiliationDescriptorImpl extends AbstractSignableSAMLObject implements AffiliationDescriptor {
   private String ownerID;
   private String id;
   private DateTime validUntil;
   private Long cacheDuration;
   private Extensions extensions;
   private final AttributeMap unknownAttributes = new AttributeMap(this);
   private final XMLObjectChildrenList members = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList keyDescriptors = new XMLObjectChildrenList(this);

   protected AffiliationDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getOwnerID() {
      return this.ownerID;
   }

   public void setOwnerID(String newOwnerID) {
      if (newOwnerID != null && newOwnerID.length() > 1024) {
         throw new IllegalArgumentException("Owner ID can not exceed 1024 characters in length");
      } else {
         this.ownerID = this.prepareForAssignment(this.ownerID, newOwnerID);
      }
   }

   public String getID() {
      return this.id;
   }

   public void setID(String newID) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newID);
      this.registerOwnID(oldID, this.id);
   }

   public boolean isValid() {
      if (null == this.validUntil) {
         return true;
      } else {
         DateTime now = new DateTime();
         return now.isBefore(this.validUntil);
      }
   }

   public DateTime getValidUntil() {
      return this.validUntil;
   }

   public void setValidUntil(DateTime theValidUntil) {
      this.validUntil = this.prepareForAssignment(this.validUntil, theValidUntil);
   }

   public Long getCacheDuration() {
      return this.cacheDuration;
   }

   public void setCacheDuration(Long duration) {
      this.cacheDuration = (Long)this.prepareForAssignment(this.cacheDuration, duration);
   }

   public Extensions getExtensions() {
      return this.extensions;
   }

   public void setExtensions(Extensions theExtensions) {
      this.extensions = (Extensions)this.prepareForAssignment(this.extensions, theExtensions);
   }

   public List getMembers() {
      return this.members;
   }

   public List getKeyDescriptors() {
      return this.keyDescriptors;
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public String getSignatureReferenceID() {
      return this.id;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.getSignature() != null) {
         children.add(this.getSignature());
      }

      children.add(this.getExtensions());
      children.addAll(this.getMembers());
      children.addAll(this.getKeyDescriptors());
      return Collections.unmodifiableList(children);
   }
}
