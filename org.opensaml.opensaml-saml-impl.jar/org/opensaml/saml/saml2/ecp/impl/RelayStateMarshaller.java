package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSStringMarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.ecp.RelayState;
import org.w3c.dom.Element;

public class RelayStateMarshaller extends XSStringMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RelayState relayState = (RelayState)xmlObject;
      if (relayState.isSOAP11MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(RelayState.SOAP11_MUST_UNDERSTAND_ATTR_NAME, relayState.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (relayState.getSOAP11Actor() != null) {
         XMLObjectSupport.marshallAttribute(RelayState.SOAP11_ACTOR_ATTR_NAME, relayState.getSOAP11Actor(), domElement, false);
      }

   }
}
