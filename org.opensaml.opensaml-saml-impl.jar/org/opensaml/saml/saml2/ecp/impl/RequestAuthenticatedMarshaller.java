package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.ecp.RequestAuthenticated;
import org.w3c.dom.Element;

public class RequestAuthenticatedMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RequestAuthenticated ra = (RequestAuthenticated)xmlObject;
      if (ra.isSOAP11MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(RequestAuthenticated.SOAP11_MUST_UNDERSTAND_ATTR_NAME, ra.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (ra.getSOAP11Actor() != null) {
         XMLObjectSupport.marshallAttribute(RequestAuthenticated.SOAP11_ACTOR_ATTR_NAME, ra.getSOAP11Actor(), domElement, false);
      }

   }
}
