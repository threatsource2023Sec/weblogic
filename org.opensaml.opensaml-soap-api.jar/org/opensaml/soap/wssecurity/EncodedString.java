package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface EncodedString extends AttributedString {
   String TYPE_LOCAL_NAME = "EncodedString";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "EncodedString", "wsse");
   String ENCODING_TYPE_ATTRIB_NAME = "EncodingType";
   String ENCODING_TYPE_BASE64_BINARY = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";

   String getEncodingType();

   void setEncodingType(String var1);
}
