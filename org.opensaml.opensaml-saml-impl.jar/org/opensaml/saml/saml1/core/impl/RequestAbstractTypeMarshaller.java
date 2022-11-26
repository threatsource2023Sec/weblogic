package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml1.core.RequestAbstractType;
import org.w3c.dom.Element;

public class RequestAbstractTypeMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      RequestAbstractType request = (RequestAbstractType)samlElement;
      if (request.getID() != null) {
         domElement.setAttributeNS((String)null, "RequestID", request.getID());
         if (request.getVersion() != SAMLVersion.VERSION_10) {
            domElement.setIdAttributeNS((String)null, "RequestID", true);
         }
      }

      if (request.getIssueInstant() != null) {
         String date = SAMLConfigurationSupport.getSAMLDateFormatter().print(request.getIssueInstant());
         domElement.setAttributeNS((String)null, "IssueInstant", date);
      }

      domElement.setAttributeNS((String)null, "MajorVersion", Integer.toString(request.getVersion().getMajorVersion()));
      domElement.setAttributeNS((String)null, "MinorVersion", Integer.toString(request.getVersion().getMinorVersion()));
   }
}
