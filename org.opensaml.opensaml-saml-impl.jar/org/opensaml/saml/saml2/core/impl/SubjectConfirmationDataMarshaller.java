package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.w3c.dom.Element;

public class SubjectConfirmationDataMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      SubjectConfirmationData subjectCD = (SubjectConfirmationData)samlObject;
      String notOnOrAfterStr;
      if (subjectCD.getNotBefore() != null) {
         notOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(subjectCD.getNotBefore());
         domElement.setAttributeNS((String)null, "NotBefore", notOnOrAfterStr);
      }

      if (subjectCD.getNotOnOrAfter() != null) {
         notOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(subjectCD.getNotOnOrAfter());
         domElement.setAttributeNS((String)null, "NotOnOrAfter", notOnOrAfterStr);
      }

      if (subjectCD.getRecipient() != null) {
         domElement.setAttributeNS((String)null, "Recipient", subjectCD.getRecipient());
      }

      if (subjectCD.getInResponseTo() != null) {
         domElement.setAttributeNS((String)null, "InResponseTo", subjectCD.getInResponseTo());
      }

      if (subjectCD.getAddress() != null) {
         domElement.setAttributeNS((String)null, "Address", subjectCD.getAddress());
      }

      this.marshallUnknownAttributes(subjectCD, domElement);
   }
}
