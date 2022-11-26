package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface CipherValue extends XSBase64Binary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "CipherValue";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherValue", "xenc");
}
