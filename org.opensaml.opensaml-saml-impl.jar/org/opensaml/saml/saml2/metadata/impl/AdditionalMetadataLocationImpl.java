package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;

public class AdditionalMetadataLocationImpl extends AbstractSAMLObject implements AdditionalMetadataLocation {
   private String location;
   private String namespace;

   protected AdditionalMetadataLocationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getLocationURI() {
      return this.location;
   }

   public void setLocationURI(String locationURI) {
      this.location = this.prepareForAssignment(this.location, locationURI);
   }

   public String getNamespaceURI() {
      return this.namespace;
   }

   public void setNamespaceURI(String namespaceURI) {
      this.namespace = this.prepareForAssignment(this.namespace, namespaceURI);
   }

   public List getOrderedChildren() {
      return null;
   }
}
