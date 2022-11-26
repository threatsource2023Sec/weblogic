package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.EmailAddress;
import org.w3c.dom.Element;

public class EmailAddressMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      EmailAddress address = (EmailAddress)samlObject;
      if (address.getAddress() != null) {
         ElementSupport.appendTextContent(domElement, address.getAddress());
      }

   }
}
