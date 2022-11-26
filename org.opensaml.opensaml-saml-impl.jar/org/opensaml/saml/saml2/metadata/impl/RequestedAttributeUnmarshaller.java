package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.impl.AttributeUnmarshaller;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;
import org.w3c.dom.Attr;

public class RequestedAttributeUnmarshaller extends AttributeUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      RequestedAttribute requestedAttribute = (RequestedAttribute)samlObject;
      if (attribute.getLocalName().equals("isRequired") && attribute.getNamespaceURI() == null) {
         requestedAttribute.setIsRequired(XSBooleanValue.valueOf(attribute.getValue()));
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
