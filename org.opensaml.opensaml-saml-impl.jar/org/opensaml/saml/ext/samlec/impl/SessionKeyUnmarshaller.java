package org.opensaml.saml.ext.samlec.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.ext.samlec.EncType;
import org.opensaml.saml.ext.samlec.SessionKey;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Attr;

public class SessionKeyUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      SessionKey key = (SessionKey)parentObject;
      if (childObject instanceof EncType) {
         key.getEncTypes().add((EncType)childObject);
      } else if (childObject instanceof KeyInfo) {
         key.setKeyInfo((KeyInfo)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      SessionKey key = (SessionKey)samlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (SessionKey.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         key.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (SessionKey.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         key.setSOAP11Actor(attribute.getValue());
      } else if (attribute.getLocalName().equals("Algorithm") && attribute.getNamespaceURI() == null) {
         key.setAlgorithm(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
