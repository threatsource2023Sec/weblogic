package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface OriginatorKeyInfo extends KeyInfo {
   String DEFAULT_ELEMENT_LOCAL_NAME = "OriginatorKeyInfo";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "OriginatorKeyInfo", "xenc");
}
