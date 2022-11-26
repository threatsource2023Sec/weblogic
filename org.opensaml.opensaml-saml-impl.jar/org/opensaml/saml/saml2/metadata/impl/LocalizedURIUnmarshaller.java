package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.LocalizedURI;
import org.w3c.dom.Attr;

public class LocalizedURIUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      LocalizedURI name = (LocalizedURI)samlObject;
      name.setValue(elementContent);
   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("lang") && "http://www.w3.org/XML/1998/namespace".equals(attribute.getNamespaceURI())) {
         LocalizedURI name = (LocalizedURI)samlObject;
         name.setXMLLang(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
