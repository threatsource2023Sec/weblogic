package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSInteger;

public interface Iteration extends XSInteger, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "Iteration";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Iteration", "wsse11");
   String TYPE_LOCAL_NAME = "unsignedInt";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt", "xsd");
}
