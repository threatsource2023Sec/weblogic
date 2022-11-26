package org.opensaml.soap.wsfed.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.soap.wsfed.AppliesTo;
import org.opensaml.soap.wsfed.RequestSecurityTokenResponse;
import org.opensaml.soap.wsfed.RequestedSecurityToken;
import org.w3c.dom.Attr;

public class RequestSecurityTokenResponseUnmarshaller extends AbstractXMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) {
      RequestSecurityTokenResponse response = (RequestSecurityTokenResponse)parentSAMLObject;
      if (childSAMLObject instanceof RequestedSecurityToken) {
         response.getRequestedSecurityToken().add((RequestedSecurityToken)childSAMLObject);
      } else if (childSAMLObject instanceof AppliesTo) {
         response.setAppliesTo((AppliesTo)childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject fedObject, Attr attribute) {
   }

   protected void processElementContent(XMLObject fedObject, String content) {
   }
}
