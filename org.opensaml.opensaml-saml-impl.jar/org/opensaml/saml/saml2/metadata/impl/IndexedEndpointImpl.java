package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;

public abstract class IndexedEndpointImpl extends EndpointImpl implements IndexedEndpoint {
   private Integer index;
   private XSBooleanValue isDefault;

   protected IndexedEndpointImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Integer getIndex() {
      return this.index;
   }

   public void setIndex(Integer theIndex) {
      this.index = (Integer)this.prepareForAssignment(this.index, theIndex);
   }

   public Boolean isDefault() {
      return this.isDefault == null ? Boolean.FALSE : this.isDefault.getValue();
   }

   public XSBooleanValue isDefaultXSBoolean() {
      return this.isDefault;
   }

   public void setIsDefault(Boolean newIsDefault) {
      if (newIsDefault != null) {
         this.isDefault = (XSBooleanValue)this.prepareForAssignment(this.isDefault, new XSBooleanValue(newIsDefault, false));
      } else {
         this.isDefault = (XSBooleanValue)this.prepareForAssignment(this.isDefault, (Object)null);
      }

   }

   public void setIsDefault(XSBooleanValue theIsDefault) {
      this.isDefault = (XSBooleanValue)this.prepareForAssignment(this.isDefault, theIsDefault);
   }
}
