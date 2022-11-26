package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.w3c.dom.Element;

public class AuthzDecisionStatementMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      AuthzDecisionStatement authzDS = (AuthzDecisionStatement)samlObject;
      if (authzDS.getResource() != null) {
         domElement.setAttributeNS((String)null, "Resource", authzDS.getResource());
      }

      if (authzDS.getDecision() != null) {
         domElement.setAttributeNS((String)null, "Decision", authzDS.getDecision().toString());
      }

   }
}
