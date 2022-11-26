package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.impl.AttributeImpl;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;

public class RequestedAttributeImpl extends AttributeImpl implements RequestedAttribute {
   private XSBooleanValue isRequired;

   protected RequestedAttributeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Boolean isRequired() {
      return this.isRequired != null ? this.isRequired.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isRequiredXSBoolean() {
      return this.isRequired;
   }

   public void setIsRequired(Boolean newIsRequired) {
      if (newIsRequired != null) {
         this.isRequired = (XSBooleanValue)this.prepareForAssignment(this.isRequired, new XSBooleanValue(newIsRequired, false));
      } else {
         this.isRequired = (XSBooleanValue)this.prepareForAssignment(this.isRequired, (Object)null);
      }

   }

   public void setIsRequired(XSBooleanValue newIsRequired) {
      this.isRequired = (XSBooleanValue)this.prepareForAssignment(this.isRequired, newIsRequired);
   }
}
