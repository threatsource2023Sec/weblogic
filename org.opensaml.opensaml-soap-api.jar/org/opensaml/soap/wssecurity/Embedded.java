package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface Embedded extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "Embedded";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Embedded", "wsse");
   String TYPE_LOCAL_NAME = "EmbeddedType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "EmbeddedType", "wsse");
   String VALUE_TYPE_ATTRIB_NAME = "ValueType";

   String getValueType();

   void setValueType(String var1);
}
