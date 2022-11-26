package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.w3c.dom.Element;

public class AuthnStatementMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      AuthnStatement authnStatement = (AuthnStatement)samlObject;
      String sessionNotOnOrAfterStr;
      if (authnStatement.getAuthnInstant() != null) {
         sessionNotOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(authnStatement.getAuthnInstant());
         domElement.setAttributeNS((String)null, "AuthnInstant", sessionNotOnOrAfterStr);
      }

      if (authnStatement.getSessionIndex() != null) {
         domElement.setAttributeNS((String)null, "SessionIndex", authnStatement.getSessionIndex());
      }

      if (authnStatement.getSessionNotOnOrAfter() != null) {
         sessionNotOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(authnStatement.getSessionNotOnOrAfter());
         domElement.setAttributeNS((String)null, "SessionNotOnOrAfter", sessionNotOnOrAfterStr);
      }

   }
}
