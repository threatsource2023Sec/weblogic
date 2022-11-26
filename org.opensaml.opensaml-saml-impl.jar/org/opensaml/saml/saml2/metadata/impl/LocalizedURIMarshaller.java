package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.LocalizedURI;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class LocalizedURIMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      LocalizedURI name = (LocalizedURI)samlObject;
      if (name.getXMLLang() != null) {
         Attr attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(), "http://www.w3.org/XML/1998/namespace", "lang", "xml");
         attribute.setValue(name.getXMLLang());
         domElement.setAttributeNodeNS(attribute);
      }

   }

   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      LocalizedURI name = (LocalizedURI)samlObject;
      if (name.getValue() != null) {
         ElementSupport.appendTextContent(domElement, name.getValue());
      }

   }
}
