package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.metadata.support.AttributeConsumingServiceSelector;
import org.opensaml.saml.metadata.support.SAML2MetadataSupport;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;

public class SPSSODescriptorImpl extends SSODescriptorImpl implements SPSSODescriptor {
   private XSBooleanValue authnRequestSigned;
   private XSBooleanValue assertionSigned;
   private final XMLObjectChildrenList assertionConsumerServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList attributeConsumingServices = new XMLObjectChildrenList(this);

   protected SPSSODescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Boolean isAuthnRequestsSigned() {
      return this.authnRequestSigned == null ? Boolean.FALSE : this.authnRequestSigned.getValue();
   }

   public XSBooleanValue isAuthnRequestsSignedXSBoolean() {
      return this.authnRequestSigned;
   }

   public void setAuthnRequestsSigned(Boolean newIsSigned) {
      if (newIsSigned != null) {
         this.authnRequestSigned = (XSBooleanValue)this.prepareForAssignment(this.authnRequestSigned, new XSBooleanValue(newIsSigned, false));
      } else {
         this.authnRequestSigned = (XSBooleanValue)this.prepareForAssignment(this.authnRequestSigned, (Object)null);
      }

   }

   public void setAuthnRequestsSigned(XSBooleanValue isSigned) {
      this.authnRequestSigned = (XSBooleanValue)this.prepareForAssignment(this.authnRequestSigned, isSigned);
   }

   public Boolean getWantAssertionsSigned() {
      return this.assertionSigned == null ? Boolean.FALSE : this.assertionSigned.getValue();
   }

   public XSBooleanValue getWantAssertionsSignedXSBoolean() {
      return this.assertionSigned;
   }

   public void setWantAssertionsSigned(Boolean wantAssestionSigned) {
      if (wantAssestionSigned != null) {
         this.assertionSigned = (XSBooleanValue)this.prepareForAssignment(this.assertionSigned, new XSBooleanValue(wantAssestionSigned, false));
      } else {
         this.assertionSigned = (XSBooleanValue)this.prepareForAssignment(this.assertionSigned, (Object)null);
      }

   }

   public void setWantAssertionsSigned(XSBooleanValue wantAssestionSigned) {
      this.assertionSigned = (XSBooleanValue)this.prepareForAssignment(this.assertionSigned, wantAssestionSigned);
   }

   public List getAssertionConsumerServices() {
      return this.assertionConsumerServices;
   }

   public AssertionConsumerService getDefaultAssertionConsumerService() {
      return (AssertionConsumerService)SAML2MetadataSupport.getDefaultIndexedEndpoint(this.assertionConsumerServices);
   }

   public List getAttributeConsumingServices() {
      return this.attributeConsumingServices;
   }

   public AttributeConsumingService getDefaultAttributeConsumingService() {
      AttributeConsumingServiceSelector selector = new AttributeConsumingServiceSelector();
      selector.setRoleDescriptor(this);
      return selector.selectService();
   }

   public List getEndpoints() {
      List endpoints = new ArrayList();
      endpoints.addAll(super.getEndpoints());
      endpoints.addAll(this.assertionConsumerServices);
      return Collections.unmodifiableList(endpoints);
   }

   public List getEndpoints(QName type) {
      return type.equals(AssertionConsumerService.DEFAULT_ELEMENT_NAME) ? Collections.unmodifiableList(new ArrayList(this.assertionConsumerServices)) : super.getEndpoints(type);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.assertionConsumerServices);
      children.addAll(this.attributeConsumingServices);
      return Collections.unmodifiableList(children);
   }
}
