package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.StatusCode;
import org.w3c.dom.Element;

public class StatusCodeMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      StatusCode statusCode = (StatusCode)samlObject;
      if (statusCode.getValue() != null) {
         domElement.setAttributeNS((String)null, "Value", statusCode.getValue());
      }

   }
}
