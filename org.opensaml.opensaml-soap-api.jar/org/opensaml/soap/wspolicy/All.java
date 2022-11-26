package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;

public interface All extends OperatorContentType {
   String ELEMENT_LOCAL_NAME = "All";
   QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "All", "wsp");
}
