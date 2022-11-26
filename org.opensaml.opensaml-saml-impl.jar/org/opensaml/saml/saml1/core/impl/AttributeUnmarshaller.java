package org.opensaml.saml.saml1.core.impl;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.Attribute;

public class AttributeUnmarshaller extends AttributeDesignatorUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Attribute attribute = (Attribute)parentSAMLObject;
      QName childQName = childSAMLObject.getElementQName();
      if ("AttributeValue".equals(childQName.getLocalPart()) && childQName.getNamespaceURI().equals("urn:oasis:names:tc:SAML:1.0:assertion")) {
         attribute.getAttributeValues().add(childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
