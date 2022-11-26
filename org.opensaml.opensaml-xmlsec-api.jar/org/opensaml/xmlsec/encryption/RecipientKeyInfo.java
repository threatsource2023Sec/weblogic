package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface RecipientKeyInfo extends KeyInfo {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RecipientKeyInfo";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "RecipientKeyInfo", "xenc");
}
