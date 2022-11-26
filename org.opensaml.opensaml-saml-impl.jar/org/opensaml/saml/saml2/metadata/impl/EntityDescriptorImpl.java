package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;

public class EntityDescriptorImpl extends AbstractSignableSAMLObject implements EntityDescriptor {
   private String entityID;
   private String id;
   private DateTime validUntil;
   private Long cacheDuration;
   private Extensions extensions;
   private final IndexedXMLObjectChildrenList roleDescriptors = new IndexedXMLObjectChildrenList(this);
   private AffiliationDescriptor affiliationDescriptor;
   private Organization organization;
   private final XMLObjectChildrenList contactPersons = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList additionalMetadata = new XMLObjectChildrenList(this);
   private final AttributeMap unknownAttributes = new AttributeMap(this);

   protected EntityDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getEntityID() {
      return this.entityID;
   }

   public void setEntityID(String newId) {
      if (newId != null && newId.length() > 1024) {
         throw new IllegalArgumentException("Entity ID can not exceed 1024 characters in length");
      } else {
         this.entityID = this.prepareForAssignment(this.entityID, newId);
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

   public List getRoleDescriptors() {
      return this.roleDescriptors;
   }

   public List getRoleDescriptors(QName typeOrName) {
      return this.roleDescriptors.subList(typeOrName);
   }

   public List getRoleDescriptors(QName type, String supportedProtocol) {
      ArrayList supportingRoleDescriptors = new ArrayList();
      Iterator var4 = this.roleDescriptors.subList(type).iterator();

      while(var4.hasNext()) {
         RoleDescriptor descriptor = (RoleDescriptor)var4.next();
         if (descriptor.isSupportedProtocol(supportedProtocol)) {
            supportingRoleDescriptors.add(descriptor);
         }
      }

      return supportingRoleDescriptors;
   }

   public IDPSSODescriptor getIDPSSODescriptor(String supportedProtocol) {
      List descriptors = this.getRoleDescriptors(IDPSSODescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
      return descriptors.size() > 0 ? (IDPSSODescriptor)descriptors.get(0) : null;
   }

   public SPSSODescriptor getSPSSODescriptor(String supportedProtocol) {
      List descriptors = this.getRoleDescriptors(SPSSODescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
      return descriptors.size() > 0 ? (SPSSODescriptor)descriptors.get(0) : null;
   }

   public AuthnAuthorityDescriptor getAuthnAuthorityDescriptor(String supportedProtocol) {
      List descriptors = this.getRoleDescriptors(AuthnAuthorityDescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
      return descriptors.size() > 0 ? (AuthnAuthorityDescriptor)descriptors.get(0) : null;
   }

   public AttributeAuthorityDescriptor getAttributeAuthorityDescriptor(String supportedProtocol) {
      List descriptors = this.getRoleDescriptors(AttributeAuthorityDescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
      return descriptors.size() > 0 ? (AttributeAuthorityDescriptor)descriptors.get(0) : null;
   }

   public PDPDescriptor getPDPDescriptor(String supportedProtocol) {
      List descriptors = this.getRoleDescriptors(PDPDescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
      return descriptors.size() > 0 ? (PDPDescriptor)descriptors.get(0) : null;
   }

   public AffiliationDescriptor getAffiliationDescriptor() {
      return this.affiliationDescriptor;
   }

   public void setAffiliationDescriptor(AffiliationDescriptor descriptor) {
      this.affiliationDescriptor = (AffiliationDescriptor)this.prepareForAssignment(this.affiliationDescriptor, descriptor);
   }

   public Organization getOrganization() {
      return this.organization;
   }

   public void setOrganization(Organization newOrganization) {
      this.organization = (Organization)this.prepareForAssignment(this.organization, newOrganization);
   }

   public List getContactPersons() {
      return this.contactPersons;
   }

   public List getAdditionalMetadataLocations() {
      return this.additionalMetadata;
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
      children.addAll(this.roleDescriptors);
      children.add(this.getAffiliationDescriptor());
      children.add(this.getOrganization());
      children.addAll(this.contactPersons);
      children.addAll(this.additionalMetadata);
      return Collections.unmodifiableList(children);
   }
}
