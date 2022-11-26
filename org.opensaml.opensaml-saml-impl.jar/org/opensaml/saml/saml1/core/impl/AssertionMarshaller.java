package org.opensaml.saml.saml1.core.impl;

import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.Assertion;
import org.w3c.dom.Element;

public class AssertionMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      Assertion assertion = (Assertion)samlElement;
      if (assertion.getID() != null) {
         domElement.setAttributeNS((String)null, "AssertionID", assertion.getID());
         if (assertion.getMinorVersion() != 0) {
            domElement.setIdAttributeNS((String)null, "AssertionID", true);
         }
      }

      if (assertion.getIssuer() != null) {
         domElement.setAttributeNS((String)null, "Issuer", assertion.getIssuer());
      }

      if (assertion.getIssueInstant() != null) {
         String date = ISODateTimeFormat.dateTime().print(assertion.getIssueInstant());
         domElement.setAttributeNS((String)null, "IssueInstant", date);
      }

      domElement.setAttributeNS((String)null, "MajorVersion", "1");
      if (assertion.getMinorVersion() == 0) {
         domElement.setAttributeNS((String)null, "MinorVersion", "0");
      } else {
         domElement.setAttributeNS((String)null, "MinorVersion", "1");
      }

   }
}
