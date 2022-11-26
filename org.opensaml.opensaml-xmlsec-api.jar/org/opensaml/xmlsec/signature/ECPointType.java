package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;

public interface ECPointType extends CryptoBinary {
   String TYPE_LOCAL_NAME = "ECPointType";
   QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "ECPointType", "ds11");
}
