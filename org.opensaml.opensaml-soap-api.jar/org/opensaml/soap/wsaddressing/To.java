package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface To extends AttributedURI {
   String ELEMENT_LOCAL_NAME = "To";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "To", "wsa");
}
