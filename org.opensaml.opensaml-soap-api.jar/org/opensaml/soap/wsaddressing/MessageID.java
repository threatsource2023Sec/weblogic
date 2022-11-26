package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface MessageID extends AttributedURI {
   String ELEMENT_LOCAL_NAME = "MessageID";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "MessageID", "wsa");
}
