package org.opensaml.saml.saml2.ecp.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.ecp.RequestAuthenticated;
import org.w3c.dom.Attr;

public class RequestAuthenticatedUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      RequestAuthenticated ra = (RequestAuthenticated)xmlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (RequestAuthenticated.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         ra.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (RequestAuthenticated.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         ra.setSOAP11Actor(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
