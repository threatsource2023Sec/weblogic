package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface Address extends AttributedURI {
   String ELEMENT_LOCAL_NAME = "Address";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "Address", "wsa");
   String ANONYMOUS = "http://www.w3.org/2005/08/addressing/anonymous";
   String NONE = "http://www.w3.org/2005/08/addressing/none";
}
