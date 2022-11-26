package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AuthzService;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;

public class PDPDescriptorImpl extends RoleDescriptorImpl implements PDPDescriptor {
   private final XMLObjectChildrenList authzServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList assertionIDRequestServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList nameIDFormats = new XMLObjectChildrenList(this);

   protected PDPDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAuthzServices() {
      return this.authzServices;
   }

   public List getAssertionIDRequestServices() {
      return this.assertionIDRequestServices;
   }

   public List getNameIDFormats() {
      return this.nameIDFormats;
   }

   public List getEndpoints() {
      List endpoints = new ArrayList();
      endpoints.addAll(this.authzServices);
      endpoints.addAll(this.assertionIDRequestServices);
      return Collections.unmodifiableList(endpoints);
   }

   public List getEndpoints(QName type) {
      if (type.equals(AuthzService.DEFAULT_ELEMENT_NAME)) {
         return Collections.unmodifiableList(new ArrayList(this.authzServices));
      } else {
         return type.equals(AssertionIDRequestService.DEFAULT_ELEMENT_NAME) ? Collections.unmodifiableList(new ArrayList(this.assertionIDRequestServices)) : null;
      }
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.authzServices);
      children.addAll(this.assertionIDRequestServices);
      children.addAll(this.nameIDFormats);
      return children;
   }
}
