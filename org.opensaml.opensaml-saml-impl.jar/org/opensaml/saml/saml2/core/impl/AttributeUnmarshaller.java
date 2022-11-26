package org.opensaml.saml.saml2.core.impl;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Attribute;
import org.w3c.dom.Attr;

public class AttributeUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Attribute attribute = (Attribute)parentSAMLObject;
      QName childQName = childSAMLObject.getElementQName();
      if ("AttributeValue".equals(childQName.getLocalPart()) && childQName.getNamespaceURI().equals("urn:oasis:names:tc:SAML:2.0:assertion")) {
         attribute.getAttributeValues().add(childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Attribute attrib = (Attribute)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Name")) {
            attrib.setName(attribute.getValue());
         } else if (attribute.getLocalName().equals("NameFormat")) {
            attrib.setNameFormat(attribute.getValue());
         } else if (attribute.getLocalName().equals("FriendlyName")) {
            attrib.setFriendlyName(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         this.processUnknownAttribute(attrib, attribute);
      }

   }
}
