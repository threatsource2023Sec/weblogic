package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.metadata.support.SAML2MetadataSupport;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.ManageNameIDService;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;

public abstract class SSODescriptorImpl extends RoleDescriptorImpl implements SSODescriptor {
   private final XMLObjectChildrenList artifactResolutionServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList singleLogoutServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList manageNameIDServices = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList nameIDFormats = new XMLObjectChildrenList(this);

   protected SSODescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getArtifactResolutionServices() {
      return this.artifactResolutionServices;
   }

   public ArtifactResolutionService getDefaultArtifactResolutionService() {
      return (ArtifactResolutionService)SAML2MetadataSupport.getDefaultIndexedEndpoint(this.artifactResolutionServices);
   }

   public List getSingleLogoutServices() {
      return this.singleLogoutServices;
   }

   public List getManageNameIDServices() {
      return this.manageNameIDServices;
   }

   public List getNameIDFormats() {
      return this.nameIDFormats;
   }

   public List getEndpoints() {
      List endpoints = new ArrayList();
      endpoints.addAll(this.artifactResolutionServices);
      endpoints.addAll(this.singleLogoutServices);
      endpoints.addAll(this.manageNameIDServices);
      return Collections.unmodifiableList(endpoints);
   }

   public List getEndpoints(QName type) {
      if (type.equals(ArtifactResolutionService.DEFAULT_ELEMENT_NAME)) {
         return Collections.unmodifiableList(new ArrayList(this.artifactResolutionServices));
      } else if (type.equals(SingleLogoutService.DEFAULT_ELEMENT_NAME)) {
         return Collections.unmodifiableList(new ArrayList(this.singleLogoutServices));
      } else {
         return type.equals(ManageNameIDService.DEFAULT_ELEMENT_NAME) ? Collections.unmodifiableList(new ArrayList(this.manageNameIDServices)) : Collections.EMPTY_LIST;
      }
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.artifactResolutionServices);
      children.addAll(this.singleLogoutServices);
      children.addAll(this.manageNameIDServices);
      children.addAll(this.nameIDFormats);
      return Collections.unmodifiableList(children);
   }
}
