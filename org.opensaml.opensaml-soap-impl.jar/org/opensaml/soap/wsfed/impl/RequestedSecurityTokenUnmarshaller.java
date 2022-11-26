package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.soap.wsfed.RequestedSecurityToken;
import org.w3c.dom.Attr;

public class RequestedSecurityTokenUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) {
      RequestedSecurityToken requestedSecurityToken = (RequestedSecurityToken)parentSAMLObject;
      requestedSecurityToken.getSecurityTokens().add(childSAMLObject);
   }

   protected void processAttribute(XMLObject fedObject, Attr attribute) {
   }

   protected void processElementContent(XMLObject fedObject, String content) {
   }
}
