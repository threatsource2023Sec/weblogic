package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.AttributeService;

public class AttributeAuthorityDescriptorImpl extends RoleDescriptorImpl implements AttributeAuthorityDescriptor {
   private final XMLObjectChildrenList attributeServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList assertionIDRequestServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList nameFormats = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList attributeProfiles = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList attributes = new XMLObjectChildrenList(this);

   protected AttributeAuthorityDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAttributeServices() {
      return this.attributeServices;
   }

   public List getAssertionIDRequestServices() {
      return this.assertionIDRequestServices;
   }

   public List getNameIDFormats() {
      return this.nameFormats;
   }

   public List getAttributeProfiles() {
      return this.attributeProfiles;
   }

   public List getAttributes() {
      return this.attributes;
   }

   public List getEndpoints() {
      List endpoints = new ArrayList();
      endpoints.addAll(this.attributeServices);
      endpoints.addAll(this.assertionIDRequestServices);
      return Collections.unmodifiableList(endpoints);
   }

   public List getEndpoints(QName type) {
      if (type.equals(AttributeService.DEFAULT_ELEMENT_NAME)) {
         return Collections.unmodifiableList(new ArrayList(this.attributeServices));
      } else {
         return type.equals(AssertionIDRequestService.DEFAULT_ELEMENT_NAME) ? Collections.unmodifiableList(new ArrayList(this.assertionIDRequestServices)) : null;
      }
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.attributeServices);
      children.addAll(this.assertionIDRequestServices);
      children.addAll(this.nameFormats);
      children.addAll(this.attributeProfiles);
      children.addAll(this.attributes);
      return Collections.unmodifiableList(children);
   }
}
