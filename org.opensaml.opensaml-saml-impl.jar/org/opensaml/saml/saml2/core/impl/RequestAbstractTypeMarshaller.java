package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.w3c.dom.Element;

public abstract class RequestAbstractTypeMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      RequestAbstractType req = (RequestAbstractType)samlObject;
      if (req.getVersion() != null) {
         domElement.setAttributeNS((String)null, "Version", req.getVersion().toString());
      }

      if (req.getID() != null) {
         domElement.setAttributeNS((String)null, "ID", req.getID());
         domElement.setIdAttributeNS((String)null, "ID", true);
      }

      if (req.getVersion() != null) {
         domElement.setAttributeNS((String)null, "Version", req.getVersion().toString());
      }

      if (req.getIssueInstant() != null) {
         String iiStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(req.getIssueInstant());
         domElement.setAttributeNS((String)null, "IssueInstant", iiStr);
      }

      if (req.getDestination() != null) {
         domElement.setAttributeNS((String)null, "Destination", req.getDestination());
      }

      if (req.getConsent() != null) {
         domElement.setAttributeNS((String)null, "Consent", req.getConsent());
      }

   }
}
