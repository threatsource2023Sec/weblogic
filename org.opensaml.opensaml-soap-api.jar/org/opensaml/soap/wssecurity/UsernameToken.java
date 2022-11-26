package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface UsernameToken extends IdBearing, AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "UsernameToken";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken", "wsse");
   String TYPE_LOCAL_NAME = "UsernameTokenType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameTokenType", "wsse");

   Username getUsername();

   void setUsername(Username var1);
}
