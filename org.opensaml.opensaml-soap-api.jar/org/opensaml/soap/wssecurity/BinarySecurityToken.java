package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface BinarySecurityToken extends EncodedString {
   String ELEMENT_LOCAL_NAME = "BinarySecurityToken";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken", "wsse");
   String TYPE_LOCAL_NAME = "BinarySecurityTokenType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityTokenType", "wsse");
   String VALUE_TYPE_ATTRIB_NAME = "ValueType";

   String getValueType();

   void setValueType(String var1);
}
