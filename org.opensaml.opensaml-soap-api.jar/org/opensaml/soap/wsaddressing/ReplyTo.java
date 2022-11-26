package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface ReplyTo extends EndpointReferenceType {
   String ELEMENT_LOCAL_NAME = "ReplyTo";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "ReplyTo", "wsa");
}
