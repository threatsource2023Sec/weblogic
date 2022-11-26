package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;

public interface ExactlyOne extends OperatorContentType {
   String ELEMENT_LOCAL_NAME = "ExactlyOne";
   QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "ExactlyOne", "wsp");
}
