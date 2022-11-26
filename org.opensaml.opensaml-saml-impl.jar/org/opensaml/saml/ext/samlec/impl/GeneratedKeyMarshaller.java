package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryMarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.ext.samlec.GeneratedKey;
import org.w3c.dom.Element;

public class GeneratedKeyMarshaller extends XSBase64BinaryMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      GeneratedKey key = (GeneratedKey)samlObject;
      if (key.isSOAP11MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(GeneratedKey.SOAP11_MUST_UNDERSTAND_ATTR_NAME, key.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (key.getSOAP11Actor() != null) {
         XMLObjectSupport.marshallAttribute(GeneratedKey.SOAP11_ACTOR_ATTR_NAME, key.getSOAP11Actor(), domElement, false);
      }

   }
}
