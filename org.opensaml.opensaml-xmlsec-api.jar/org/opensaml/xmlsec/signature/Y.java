package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;

public interface Y extends CryptoBinary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Y";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Y", "ds");
}
