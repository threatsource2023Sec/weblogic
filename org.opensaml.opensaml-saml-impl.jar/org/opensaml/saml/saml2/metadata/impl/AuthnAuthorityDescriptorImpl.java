package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.AuthnQueryService;

public class AuthnAuthorityDescriptorImpl extends RoleDescriptorImpl implements AuthnAuthorityDescriptor {
   private final XMLObjectChildrenList authnQueryServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList assertionIDRequestServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList nameIDFormats = new XMLObjectChildrenList(this);

   protected AuthnAuthorityDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getAuthnQueryServices() {
      return this.authnQueryServices;
   }

   public List getAssertionIDRequestServices() {
      return this.assertionIDRequestServices;
   }

   public List getNameIDFormats() {
      return this.nameIDFormats;
   }

   public List getEndpoints() {
      List endpoints = new ArrayList();
      endpoints.addAll(this.authnQueryServices);
      endpoints.addAll(this.assertionIDRequestServices);
      return Collections.unmodifiableList(endpoints);
   }

   public List getEndpoints(QName type) {
      if (type.equals(AuthnQueryService.DEFAULT_ELEMENT_NAME)) {
         return Collections.unmodifiableList(new ArrayList(this.authnQueryServices));
      } else {
         return type.equals(AssertionIDRequestService.DEFAULT_ELEMENT_NAME) ? Collections.unmodifiableList(new ArrayList(this.assertionIDRequestServices)) : null;
      }
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.authnQueryServices);
      children.addAll(this.assertionIDRequestServices);
      children.addAll(this.nameIDFormats);
      return Collections.unmodifiableList(children);
   }
}
