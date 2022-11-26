package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml1.core.AuthenticationQuery;
import org.w3c.dom.Element;

public class AuthenticationQueryMarshaller extends SubjectQueryMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      AuthenticationQuery authenticationQuery = (AuthenticationQuery)samlObject;
      if (authenticationQuery.getAuthenticationMethod() != null) {
         domElement.setAttributeNS((String)null, "AuthenticationMethod", authenticationQuery.getAuthenticationMethod());
      }

   }
}
