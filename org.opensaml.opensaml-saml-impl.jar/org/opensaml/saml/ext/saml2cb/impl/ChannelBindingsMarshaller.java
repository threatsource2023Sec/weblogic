package org.opensaml.saml.ext.saml2cb.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.impl.XSBase64BinaryMarshaller;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.w3c.dom.Element;

public class ChannelBindingsMarshaller extends XSBase64BinaryMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ChannelBindings cb = (ChannelBindings)xmlObject;
      if (cb.getType() != null) {
         domElement.setAttributeNS((String)null, "Type", cb.getType());
      }

      if (cb.isSOAP11MustUnderstandXSBoolean() != null) {
         XMLObjectSupport.marshallAttribute(ChannelBindings.SOAP11_MUST_UNDERSTAND_ATTR_NAME, cb.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
      }

      if (cb.getSOAP11Actor() != null) {
         XMLObjectSupport.marshallAttribute(ChannelBindings.SOAP11_ACTOR_ATTR_NAME, cb.getSOAP11Actor(), domElement, false);
      }

   }
}
