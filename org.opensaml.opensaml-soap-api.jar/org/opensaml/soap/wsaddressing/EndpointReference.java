package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;

public interface EndpointReference extends EndpointReferenceType {
   String ELEMENT_LOCAL_NAME = "EndpointReference";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "EndpointReference", "wsa");
}
