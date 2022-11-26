package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface TokenTypeBearing {
   String WSSE11_TOKEN_TYPE_ATTR_LOCAL_NAME = "TokenType";
   QName WSSE11_TOKEN_TYPE_ATTR_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "TokenType", "wsse11");

   String getWSSE11TokenType();

   void setWSSE11TokenType(String var1);
}
