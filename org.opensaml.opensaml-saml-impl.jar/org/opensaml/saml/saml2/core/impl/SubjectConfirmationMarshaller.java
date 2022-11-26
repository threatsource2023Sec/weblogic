package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.w3c.dom.Element;

public class SubjectConfirmationMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      SubjectConfirmation subjectConfirmation = (SubjectConfirmation)samlObject;
      if (subjectConfirmation.getMethod() != null) {
         domElement.setAttributeNS((String)null, "Method", subjectConfirmation.getMethod());
      }

   }
}
