package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.Assertion;
import org.w3c.dom.Element;

public class AssertionMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      Assertion assertion = (Assertion)samlObject;
      if (assertion.getVersion() != null) {
         domElement.setAttributeNS((String)null, "Version", assertion.getVersion().toString());
      }

      if (assertion.getIssueInstant() != null) {
         String issueInstantStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(assertion.getIssueInstant());
         domElement.setAttributeNS((String)null, "IssueInstant", issueInstantStr);
      }

      if (assertion.getID() != null) {
         domElement.setAttributeNS((String)null, "ID", assertion.getID());
         domElement.setIdAttributeNS((String)null, "ID", true);
      }

   }
}
