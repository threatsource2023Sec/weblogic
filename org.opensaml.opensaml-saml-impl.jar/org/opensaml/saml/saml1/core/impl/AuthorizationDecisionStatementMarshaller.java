package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.w3c.dom.Element;

public class AuthorizationDecisionStatementMarshaller extends SubjectStatementMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement)samlElement;
      if (authorizationDecisionStatement.getResource() != null) {
         domElement.setAttributeNS((String)null, "Resource", authorizationDecisionStatement.getResource());
      }

      if (authorizationDecisionStatement.getDecision() != null) {
         domElement.setAttributeNS((String)null, "Decision", authorizationDecisionStatement.getDecision().toString());
      }

   }
}
