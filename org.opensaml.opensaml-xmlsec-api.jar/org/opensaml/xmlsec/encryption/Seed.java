package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xmlsec.signature.CryptoBinary;

public interface Seed extends CryptoBinary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "seed";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "seed", "xenc");
}
