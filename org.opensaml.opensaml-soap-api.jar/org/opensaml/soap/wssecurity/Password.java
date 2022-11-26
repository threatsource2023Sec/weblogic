package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface Password extends AttributedString {
   String ELEMENT_LOCAL_NAME = "Password";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password", "wsse");
   String TYPE_ATTRIB_NAME = "Type";
   String TYPE_PASSWORD_TEXT = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#PasswordText";
   String TYPE_PASSWORD_DIGEST = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#PasswordDigest";

   String getType();

   void setType(String var1);
}
