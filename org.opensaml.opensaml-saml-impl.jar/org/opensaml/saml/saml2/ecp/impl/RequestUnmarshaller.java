package org.opensaml.saml.saml2.ecp.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.ecp.Request;
import org.w3c.dom.Attr;

public class RequestUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Request request = (Request)samlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (Request.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         request.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (Request.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         request.setSOAP11Actor(attribute.getValue());
      } else if ("IsPassive".equals(attribute.getLocalName())) {
         request.setPassive(XSBooleanValue.valueOf(attribute.getValue()));
      } else if ("ProviderName".equals(attribute.getLocalName())) {
         request.setProviderName(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Request request = (Request)parentSAMLObject;
      if (childSAMLObject instanceof Issuer) {
         request.setIssuer((Issuer)childSAMLObject);
      } else if (childSAMLObject instanceof IDPList) {
         request.setIDPList((IDPList)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
