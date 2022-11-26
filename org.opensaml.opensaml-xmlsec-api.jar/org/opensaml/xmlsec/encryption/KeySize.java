package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSInteger;

public interface KeySize extends XSInteger {
   String DEFAULT_ELEMENT_LOCAL_NAME = "KeySize";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "KeySize", "xenc");
   String TYPE_LOCAL_NAME = "KeySizeType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "KeySizeType", "xenc");
}
