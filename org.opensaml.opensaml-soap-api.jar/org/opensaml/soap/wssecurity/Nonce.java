package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface Nonce extends EncodedString {
   String ELEMENT_LOCAL_NAME = "Nonce";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Nonce", "wsse");
}
