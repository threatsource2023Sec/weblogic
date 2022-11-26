package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.Organization;

public class OrganizationImpl extends AbstractSAMLObject implements Organization {
   private Extensions extensions;
   private final XMLObjectChildrenList names = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList displayNames = new XMLObjectChildrenList(this);
   private final XMLObjectChildrenList urls = new XMLObjectChildrenList(this);
   private final AttributeMap unknownAttributes = new AttributeMap(this);

   protected OrganizationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Extensions getExtensions() {
      return this.extensions;
   }

   public void setExtensions(Extensions newExtensions) {
      this.extensions = (Extensions)this.prepareForAssignment(this.extensions, newExtensions);
   }

   public List getOrganizationNames() {
      return this.names;
   }

   public List getDisplayNames() {
      return this.displayNames;
   }

   public List getURLs() {
      return this.urls;
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.extensions);
      children.addAll(this.names);
      children.addAll(this.displayNames);
      children.addAll(this.urls);
      return children;
   }
}
