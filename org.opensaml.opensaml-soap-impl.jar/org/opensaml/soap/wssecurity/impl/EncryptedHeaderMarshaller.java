package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wssecurity.EncryptedHeader;
import org.w3c.dom.Element;

public class EncryptedHeaderMarshaller extends AbstractWSSecurityObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      EncryptedHeader eh = (EncryptedHeader)xmlObject;
      if (eh.getWSUId() != null) {
         XMLObjectSupport.marshallAttribute(EncryptedHeader.WSU_ID_ATTR_NAME, eh.getWSUId(), domElement, true);
      }

      if (eh.isSOAP11MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(EncryptedHeader.SOAP11_MUST_UNDERSTAND_ATTR_NAME, eh.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (eh.getSOAP11Actor() != null) {
         XMLObjectSupport.marshallAttribute(EncryptedHeader.SOAP11_ACTOR_ATTR_NAME, eh.getSOAP11Actor(), domElement, false);
      }

      if (eh.isSOAP12MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(EncryptedHeader.SOAP12_MUST_UNDERSTAND_ATTR_NAME, eh.isSOAP12MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (eh.getSOAP12Role() != null) {
         XMLObjectSupport.marshallAttribute(EncryptedHeader.SOAP12_ROLE_ATTR_NAME, eh.getSOAP12Role(), domElement, false);
      }

      if (eh.isSOAP12RelayXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(EncryptedHeader.SOAP12_RELAY_ATTR_NAME, eh.isSOAP12RelayXSBoolean().toString(), domElement, false);
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
