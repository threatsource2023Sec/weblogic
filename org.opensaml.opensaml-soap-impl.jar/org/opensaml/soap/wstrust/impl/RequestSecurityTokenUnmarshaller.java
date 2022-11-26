package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.soap.wstrust.RequestSecurityToken;
import org.w3c.dom.Attr;

public class RequestSecurityTokenUnmarshaller extends AbstractWSTrustObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      RequestSecurityToken rst = (RequestSecurityToken)xmlObject;
      if ("Context".equals(attribute.getLocalName())) {
         rst.setContext(attribute.getValue());
      } else {
         XMLObjectSupport.unmarshallToAttributeMap(rst.getUnknownAttributes(), attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RequestSecurityToken rst = (RequestSecurityToken)parentXMLObject;
      rst.getUnknownXMLObjects().add(childXMLObject);
   }
}
