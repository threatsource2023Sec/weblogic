package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;
import org.w3c.dom.Element;

public class TelephoneNumberMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      TelephoneNumber number = (TelephoneNumber)samlObject;
      if (number.getNumber() != null) {
         ElementSupport.appendTextContent(domElement, number.getNumber());
      }

   }
}
