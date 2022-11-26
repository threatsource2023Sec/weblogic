package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface SecurityTokenReference extends IdBearing, UsageBearing, AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "SecurityTokenReference";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenReference", "wsse");
   String TYPE_LOCAL_NAME = "SecurityTokenReferenceType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenReferenceType", "wsse");
}
