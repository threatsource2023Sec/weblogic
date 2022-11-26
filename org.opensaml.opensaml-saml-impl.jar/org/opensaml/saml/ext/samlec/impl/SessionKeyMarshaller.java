package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.ext.samlec.SessionKey;
import org.w3c.dom.Element;

public class SessionKeyMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      SessionKey key = (SessionKey)samlObject;
      if (key.isSOAP11MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(SessionKey.SOAP11_MUST_UNDERSTAND_ATTR_NAME, key.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (key.getSOAP11Actor() != null) {
         XMLObjectSupport.marshallAttribute(SessionKey.SOAP11_ACTOR_ATTR_NAME, key.getSOAP11Actor(), domElement, false);
      }

      if (key.getAlgorithm() != null) {
         domElement.setAttributeNS((String)null, "Algorithm", key.getAlgorithm());
      }

   }
}
