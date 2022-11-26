package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface Salt extends XSBase64Binary, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "Salt";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "Salt", "wsse11");
}
