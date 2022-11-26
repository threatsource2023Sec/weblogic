package org.opensaml.saml.saml1.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.w3c.dom.Element;

public class NameIdentifierMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      NameIdentifier nameIdentifier = (NameIdentifier)samlElement;
      if (nameIdentifier.getNameQualifier() != null) {
         domElement.setAttributeNS((String)null, "NameQualifier", nameIdentifier.getNameQualifier());
      }

      if (nameIdentifier.getFormat() != null) {
         domElement.setAttributeNS((String)null, "Format", nameIdentifier.getFormat());
      }

   }

   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      NameIdentifier nameIdentifier = (NameIdentifier)samlObject;
      if (nameIdentifier.getValue() != null) {
         ElementSupport.appendTextContent(domElement, nameIdentifier.getValue());
      }

   }
}
