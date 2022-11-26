package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.w3c.dom.Element;

public class AuthorizationDecisionQueryMarshaller extends SubjectQueryMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      AuthorizationDecisionQuery authorizationDecisionQuery = (AuthorizationDecisionQuery)samlElement;
      if (authorizationDecisionQuery.getResource() != null) {
         domElement.setAttributeNS((String)null, "Resource", authorizationDecisionQuery.getResource());
      }

   }
}
