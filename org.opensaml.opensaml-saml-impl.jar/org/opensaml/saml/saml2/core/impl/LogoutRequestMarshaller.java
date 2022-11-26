package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.w3c.dom.Element;

public class LogoutRequestMarshaller extends RequestAbstractTypeMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      LogoutRequest req = (LogoutRequest)samlObject;
      if (req.getReason() != null) {
         domElement.setAttributeNS((String)null, "Reason", req.getReason());
      }

      if (req.getNotOnOrAfter() != null) {
         String noaStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(req.getNotOnOrAfter());
         domElement.setAttributeNS((String)null, "NotOnOrAfter", noaStr);
      }

      super.marshallAttributes(samlObject, domElement);
   }
}
