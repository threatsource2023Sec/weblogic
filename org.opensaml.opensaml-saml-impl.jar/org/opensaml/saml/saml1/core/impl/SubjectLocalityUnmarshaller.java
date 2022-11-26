package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.SubjectLocality;
import org.w3c.dom.Attr;

public class SubjectLocalityUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      SubjectLocality subjectLocality = (SubjectLocality)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("DNSAddress".equals(attribute.getLocalName())) {
            subjectLocality.setDNSAddress(attribute.getValue());
         } else if ("IPAddress".equals(attribute.getLocalName())) {
            subjectLocality.setIPAddress(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
