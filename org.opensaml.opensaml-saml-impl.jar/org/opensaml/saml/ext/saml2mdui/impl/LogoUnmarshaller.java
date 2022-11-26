package org.opensaml.saml.ext.saml2mdui.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.saml2mdui.Logo;
import org.w3c.dom.Attr;

public class LogoUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      Logo logo = (Logo)samlObject;
      logo.setURL(elementContent);
   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Logo logo = (Logo)samlObject;
      if (attribute.getLocalName().equals("lang") && "http://www.w3.org/XML/1998/namespace".equals(attribute.getNamespaceURI())) {
         logo.setXMLLang(attribute.getValue());
      } else if (attribute.getLocalName().equals("height") && attribute.getNamespaceURI() == null) {
         logo.setHeight(Integer.valueOf(attribute.getValue()));
      } else if (attribute.getLocalName().equals("width") && attribute.getNamespaceURI() == null) {
         logo.setWidth(Integer.valueOf(attribute.getValue()));
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
