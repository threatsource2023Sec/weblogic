package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;

public interface Seed extends CryptoBinary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Seed";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Seed", "ds");
}
