package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.RequestSecurityToken;
import org.w3c.dom.Element;

public class RequestSecurityTokenMarshaller extends AbstractWSTrustObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      RequestSecurityToken rst = (RequestSecurityToken)xmlObject;
      if (rst.getContext() != null) {
         domElement.setAttributeNS((String)null, "Context", rst.getContext());
      }

      XMLObjectSupport.marshallAttributeMap(rst.getUnknownAttributes(), domElement);
   }
}
