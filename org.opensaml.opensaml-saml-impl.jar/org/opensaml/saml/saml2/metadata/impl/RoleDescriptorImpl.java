package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public abstract class RoleDescriptorImpl extends AbstractSignableSAMLObject implements RoleDescriptor {
   private String id;
   private DateTime validUntil;
   private Long cacheDuration;
   private final List supportedProtocols = new LazyList();
   private String errorURL;
   private Extensions extensions;
   private Organization organization;
   private final AttributeMap unknownAttributes = new AttributeMap(this);
   private final XMLObjectChildrenList contactPersons = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList keyDescriptors = new XMLObjectChildrenList(this);

   protected RoleDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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

   public void setValidUntil(DateTime dt) {
      this.validUntil = this.prepareForAssignment(this.validUntil, dt);
   }

   public Long getCacheDuration() {
      return this.cacheDuration;
   }

   public void setCacheDuration(Long duration) {
      this.cacheDuration = (Long)this.prepareForAssignment(this.cacheDuration, duration);
   }

   public List getSupportedProtocols() {
      return Collections.unmodifiableList(this.supportedProtocols);
   }

   public boolean isSupportedProtocol(String protocol) {
      return this.supportedProtocols.contains(protocol);
   }

   public void addSupportedProtocol(String protocol) {
      String trimmed = StringSupport.trimOrNull(protocol);
      if (trimmed != null && !this.supportedProtocols.contains(trimmed)) {
         this.releaseThisandParentDOM();
         this.supportedProtocols.add(trimmed);
      }

   }

   public void removeSupportedProtocol(String protocol) {
      String trimmed = StringSupport.trimOrNull(protocol);
      if (trimmed != null && this.supportedProtocols.contains(trimmed)) {
         this.releaseThisandParentDOM();
         this.supportedProtocols.remove(trimmed);
      }

   }

   public void removeSupportedProtocols(Collection protocols) {
      Iterator var2 = protocols.iterator();

      while(var2.hasNext()) {
         String protocol = (String)var2.next();
         this.removeSupportedProtocol(protocol);
      }

   }

   public void removeAllSupportedProtocols() {
      this.supportedProtocols.clear();
   }

   public String getErrorURL() {
      return this.errorURL;
   }

   public void setErrorURL(String url) {
      this.errorURL = this.prepareForAssignment(this.errorURL, url);
   }

   public Extensions getExtensions() {
      return this.extensions;
   }

   public void setExtensions(Extensions ext) {
      this.extensions = (Extensions)this.prepareForAssignment(this.extensions, ext);
   }

   public Organization getOrganization() {
      return this.organization;
   }

   public void setOrganization(Organization org) {
      this.organization = (Organization)this.prepareForAssignment(this.organization, org);
   }

   public List getContactPersons() {
      return this.contactPersons;
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

      if (this.extensions != null) {
         children.add(this.getExtensions());
      }

      children.addAll(this.getKeyDescriptors());
      if (this.organization != null) {
         children.add(this.getOrganization());
      }

      children.addAll(this.getContactPersons());
      return Collections.unmodifiableList(children);
   }
}
