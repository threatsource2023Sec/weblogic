package org.opensaml.soap.wssecurity.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.soap.wssecurity.EncryptedHeader;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.w3c.dom.Attr;

public class EncryptedHeaderUnmarshaller extends AbstractWSSecurityObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      EncryptedHeader eh = (EncryptedHeader)xmlObject;
      QName attrName = QNameSupport.getNodeQName(attribute);
      if (EncryptedHeader.WSU_ID_ATTR_NAME.equals(attrName)) {
         eh.setWSUId(attribute.getValue());
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      } else if (EncryptedHeader.SOAP11_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         eh.setSOAP11MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (EncryptedHeader.SOAP11_ACTOR_ATTR_NAME.equals(attrName)) {
         eh.setSOAP11Actor(attribute.getValue());
      } else if (EncryptedHeader.SOAP12_MUST_UNDERSTAND_ATTR_NAME.equals(attrName)) {
         eh.setSOAP12MustUnderstand(XSBooleanValue.valueOf(attribute.getValue()));
      } else if (EncryptedHeader.SOAP12_ROLE_ATTR_NAME.equals(attrName)) {
         eh.setSOAP12Role(attribute.getValue());
      } else if (EncryptedHeader.SOAP12_RELAY_ATTR_NAME.equals(attrName)) {
         eh.setSOAP12Relay(XSBooleanValue.valueOf(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EncryptedHeader eh = (EncryptedHeader)parentXMLObject;
      if (childXMLObject instanceof EncryptedData) {
         eh.setEncryptedData((EncryptedData)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
