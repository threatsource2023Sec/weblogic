package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.RequestSecurityTokenResponse;
import org.w3c.dom.Element;

public class RequestSecurityTokenResponseMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RequestSecurityTokenResponse rstr = (RequestSecurityTokenResponse)xmlObject;
      if (rstr.getContext() != null) {
         domElement.setAttributeNS((String)null, "Context", rstr.getContext());
      }

      XMLObjectSupport.marshallAttributeMap(rstr.getUnknownAttributes(), domElement);
   }
}
