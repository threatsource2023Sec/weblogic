package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;

public interface J extends CryptoBinary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "J";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "J", "ds");
}
