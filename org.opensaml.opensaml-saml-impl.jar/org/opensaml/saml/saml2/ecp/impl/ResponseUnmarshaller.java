package org.opensaml.saml.saml2.ecp.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.ecp.Response;
import org.w3c.dom.Attr;

public class ResponseUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Response response = (Response)samlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (Response.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         response.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (Response.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         response.setSOAP11Actor(attribute.getValue());
      } else if ("AssertionConsumerServiceURL".equals(attribute.getLocalName())) {
         response.setAssertionConsumerServiceURL(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
