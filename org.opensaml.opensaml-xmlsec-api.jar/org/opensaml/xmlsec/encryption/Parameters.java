package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface Parameters extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Parameters";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmlenc11#", "Parameters", "xenc11");
}
