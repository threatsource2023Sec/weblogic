package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.ecp.Response;
import org.w3c.dom.Element;

public class ResponseMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Response response = (Response)xmlObject;
      if (response.getAssertionConsumerServiceURL() != null) {
         domElement.setAttributeNS((String)null, "AssertionConsumerServiceURL", response.getAssertionConsumerServiceURL());
      }

      if (response.isSOAP11MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(Response.SOAP11_MUST_UNDERSTAND_ATTR_NAME, response.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (response.getSOAP11Actor() != null) {
         XMLObjectSupport.marshallAttribute(Response.SOAP11_ACTOR_ATTR_NAME, response.getSOAP11Actor(), domElement, false);
      }

   }
}
