package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface Expires extends AttributedDateTime {
   String ELEMENT_LOCAL_NAME = "Expires";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Expires", "wsu");
}
