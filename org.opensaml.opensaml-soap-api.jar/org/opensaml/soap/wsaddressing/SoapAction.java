package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;

public interface SoapAction extends XSURI, WSAddressingObject {
   String ELEMENT_LOCAL_NAME = "SoapAction";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "SoapAction", "wsa");
}
