package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.Attribute;
import org.w3c.dom.Element;

public class AttributeMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      Attribute attribute = (Attribute)samlElement;
      if (attribute.getName() != null) {
         domElement.setAttributeNS((String)null, "Name", attribute.getName());
      }

      if (attribute.getNameFormat() != null) {
         domElement.setAttributeNS((String)null, "NameFormat", attribute.getNameFormat());
      }

      if (attribute.getFriendlyName() != null) {
         domElement.setAttributeNS((String)null, "FriendlyName", attribute.getFriendlyName());
      }

      this.marshallUnknownAttributes(attribute, domElement);
   }
}
