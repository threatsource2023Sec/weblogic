package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface KeyIdentifier extends EncodedString {
   String ELEMENT_LOCAL_NAME = "KeyIdentifier";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifier", "wsse");
   String TYPE_LOCAL_NAME = "KeyIdentifierType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifierType", "wsse");
   String VALUE_TYPE_ATTRIB_NAME = "ValueType";

   String getValueType();

   void setValueType(String var1);
}
