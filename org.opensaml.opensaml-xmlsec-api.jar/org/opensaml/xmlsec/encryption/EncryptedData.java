package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;

public interface EncryptedData extends EncryptedType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedData";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedData", "xenc");
   String TYPE_LOCAL_NAME = "EncryptedDataType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedDataType", "xenc");
}
