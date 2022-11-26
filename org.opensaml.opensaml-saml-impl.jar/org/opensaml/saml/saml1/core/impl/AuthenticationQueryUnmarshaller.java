package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.AuthenticationQuery;
import org.w3c.dom.Attr;

public class AuthenticationQueryUnmarshaller extends SubjectQueryUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthenticationQuery authenticationQuery = (AuthenticationQuery)samlObject;
      if ("AuthenticationMethod".equals(attribute.getLocalName()) && attribute.getNamespaceURI() == null) {
         authenticationQuery.setAuthenticationMethod(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
