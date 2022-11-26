package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface Username extends AttributedString {
   String ELEMENT_LOCAL_NAME = "Username";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Username", "wsse");
}
