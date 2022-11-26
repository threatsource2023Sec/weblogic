package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.w3c.dom.Element;

public class ContactPersonMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      ContactPerson person = (ContactPerson)samlObject;
      if (person.getType() != null) {
         domElement.setAttributeNS((String)null, "contactType", person.getType().toString());
      }

      this.marshallUnknownAttributes(person, domElement);
   }
}
