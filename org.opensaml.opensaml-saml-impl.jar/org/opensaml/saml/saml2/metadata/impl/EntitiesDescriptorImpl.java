package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;

public class EntitiesDescriptorImpl extends AbstractSignableSAMLObject implements EntitiesDescriptor {
   private String name;
   private String id;
   private DateTime validUntil;
   private Long cacheDuration;
   private Extensions extensions;
   private final IndexedXMLObjectChildrenList orderedDescriptors = new IndexedXMLObjectChildrenList(this);

   protected EntitiesDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String newName) {
      this.name = this.prepareForAssignment(this.name, newName);
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

   public void setValidUntil(DateTime newValidUntil) {
      this.validUntil = this.prepareForAssignment(this.validUntil, newValidUntil);
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

   public void setExtensions(Extensions newExtensions) {
      this.extensions = (Extensions)this.prepareForAssignment(this.extensions, newExtensions);
   }

   public List getEntitiesDescriptors() {
      return this.orderedDescriptors.subList(EntitiesDescriptor.ELEMENT_QNAME);
   }

   public List getEntityDescriptors() {
      return this.orderedDescriptors.subList(EntityDescriptor.ELEMENT_QNAME);
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
      children.addAll(this.orderedDescriptors);
      return Collections.unmodifiableList(children);
   }
}
