package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.AuthnQuery;
import org.w3c.dom.Element;

public class AuthnQueryMarshaller extends SubjectQueryMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      AuthnQuery query = (AuthnQuery)samlObject;
      if (query.getSessionIndex() != null) {
         domElement.setAttributeNS((String)null, "SessionIndex", query.getSessionIndex());
      }

      super.marshallAttributes(samlObject, domElement);
   }
}
