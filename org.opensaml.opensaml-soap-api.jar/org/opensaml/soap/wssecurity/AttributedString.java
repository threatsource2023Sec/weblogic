package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSString;

public interface AttributedString extends XSString, IdBearing, AttributeExtensibleXMLObject, WSSecurityObject {
   String TYPE_LOCAL_NAME = "AttributedString";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "AttributedString", "wsse");
}
