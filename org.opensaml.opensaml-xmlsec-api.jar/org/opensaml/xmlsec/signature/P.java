package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;

public interface P extends CryptoBinary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "P";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "P", "ds");
}
