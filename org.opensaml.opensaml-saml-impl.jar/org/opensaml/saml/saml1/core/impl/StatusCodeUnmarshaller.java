package org.opensaml.saml.saml1.core.impl;

import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.StatusCode;
import org.w3c.dom.Attr;

public class StatusCodeUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      StatusCode statusCode = (StatusCode)parentSAMLObject;
      if (childSAMLObject instanceof StatusCode) {
         statusCode.setStatusCode((StatusCode)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      StatusCode statusCode = (StatusCode)samlObject;
      if (attribute.getName().equals("Value") && attribute.getNamespaceURI() == null) {
         statusCode.setValue(AttributeSupport.getAttributeValueAsQName(attribute));
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
