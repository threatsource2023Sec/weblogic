package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.w3c.dom.Attr;

public class StatusCodeTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      StatusCodeType statusCode = (StatusCodeType)xmlObject;
      if (attribute.getLocalName().equals("Value")) {
         statusCode.setValue(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      StatusCodeType statuscode = (StatusCodeType)parentObject;
      if (childObject instanceof StatusCodeType) {
         statuscode.setStatusCode((StatusCodeType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
