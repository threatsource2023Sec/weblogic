package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface Action extends AttributedURI {
   String ELEMENT_LOCAL_NAME = "Action";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "Action", "wsa");
}
