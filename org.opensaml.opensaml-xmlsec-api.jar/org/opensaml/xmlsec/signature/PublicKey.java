package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;

public interface PublicKey extends ECPointType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PublicKey";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "PublicKey", "ds11");
}
