package org.opensaml.soap.wspolicy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wspolicy.Policy;
import org.opensaml.soap.wssecurity.IdBearing;
import org.w3c.dom.Element;

public class PolicyMarshaller extends OperatorContentTypeMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Policy policy = (Policy)xmlObject;
      if (policy.getName() != null) {
         domElement.setAttributeNS((String)null, "Name", policy.getName());
      }

      if (policy.getWSUId() != null) {
         XMLObjectSupport.marshallAttribute(IdBearing.WSU_ID_ATTR_NAME, policy.getWSUId(), domElement, true);
      }

      XMLObjectSupport.marshallAttributeMap(policy.getUnknownAttributes(), domElement);
   }
}
