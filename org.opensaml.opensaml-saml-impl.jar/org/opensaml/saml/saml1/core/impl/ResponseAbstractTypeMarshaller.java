package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.w3c.dom.Element;

public abstract class ResponseAbstractTypeMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      ResponseAbstractType response = (ResponseAbstractType)samlElement;
      if (response.getID() != null) {
         domElement.setAttributeNS((String)null, "ResponseID", response.getID());
         if (response.getVersion() != SAMLVersion.VERSION_10) {
            domElement.setIdAttributeNS((String)null, "ResponseID", true);
         }
      }

      if (response.getInResponseTo() != null) {
         domElement.setAttributeNS((String)null, "InResponseTo", response.getInResponseTo());
      }

      if (response.getIssueInstant() != null) {
         String date = SAMLConfigurationSupport.getSAMLDateFormatter().print(response.getIssueInstant());
         domElement.setAttributeNS((String)null, "IssueInstant", date);
      }

      domElement.setAttributeNS((String)null, "MajorVersion", Integer.toString(response.getVersion().getMajorVersion()));
      domElement.setAttributeNS((String)null, "MinorVersion", Integer.toString(response.getVersion().getMinorVersion()));
      if (response.getRecipient() != null) {
         domElement.setAttributeNS((String)null, "Recipient", response.getRecipient());
      }

   }
}
