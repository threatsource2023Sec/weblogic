package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.NameIDMappingService;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;

public class IDPSSODescriptorImpl extends SSODescriptorImpl implements IDPSSODescriptor {
   private XSBooleanValue wantAuthnRequestsSigned;
   private final XMLObjectChildrenList singleSignOnServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList nameIDMappingServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList assertionIDRequestServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList attributeProfiles = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList attributes = new XMLObjectChildrenList(this);

   protected IDPSSODescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Boolean getWantAuthnRequestsSigned() {
      return this.wantAuthnRequestsSigned != null ? this.wantAuthnRequestsSigned.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue getWantAuthnRequestsSignedXSBoolean() {
      return this.wantAuthnRequestsSigned;
   }

   public void setWantAuthnRequestsSigned(Boolean newWantSigned) {
      if (newWantSigned != null) {
         this.wantAuthnRequestsSigned = (XSBooleanValue)this.prepareForAssignment(this.wantAuthnRequestsSigned, new XSBooleanValue(newWantSigned, false));
      } else {
         this.wantAuthnRequestsSigned = (XSBooleanValue)this.prepareForAssignment(this.wantAuthnRequestsSigned, (Object)null);
      }

   }

   public void setWantAuthnRequestsSigned(XSBooleanValue wantSigned) {
      this.wantAuthnRequestsSigned = (XSBooleanValue)this.prepareForAssignment(this.wantAuthnRequestsSigned, wantSigned);
   }

   public List getSingleSignOnServices() {
      return this.singleSignOnServices;
   }

   public List getNameIDMappingServices() {
      return this.nameIDMappingServices;
   }

   public List getAssertionIDRequestServices() {
      return this.assertionIDRequestServices;
   }

   public List getAttributeProfiles() {
      return this.attributeProfiles;
   }

   public List getAttributes() {
      return this.attributes;
   }

   public List getEndpoints() {
      List endpoints = new ArrayList();
      endpoints.addAll(super.getEndpoints());
      endpoints.addAll(this.singleSignOnServices);
      endpoints.addAll(this.nameIDMappingServices);
      endpoints.addAll(this.assertionIDRequestServices);
      return Collections.unmodifiableList(endpoints);
   }

   public List getEndpoints(QName type) {
      if (type.equals(SingleSignOnService.DEFAULT_ELEMENT_NAME)) {
         return Collections.unmodifiableList(new ArrayList(this.singleSignOnServices));
      } else if (type.equals(NameIDMappingService.DEFAULT_ELEMENT_NAME)) {
         return Collections.unmodifiableList(new ArrayList(this.nameIDMappingServices));
      } else {
         return type.equals(AssertionIDRequestService.DEFAULT_ELEMENT_NAME) ? Collections.unmodifiableList(new ArrayList(this.assertionIDRequestServices)) : super.getEndpoints(type);
      }
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.singleSignOnServices);
      children.addAll(this.nameIDMappingServices);
      children.addAll(this.assertionIDRequestServices);
      children.addAll(this.attributeProfiles);
      children.addAll(this.attributes);
      return Collections.unmodifiableList(children);
   }
}
