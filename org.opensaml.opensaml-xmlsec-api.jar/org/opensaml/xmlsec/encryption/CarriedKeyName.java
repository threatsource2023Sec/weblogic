package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;

public interface CarriedKeyName extends XSString {
   String DEFAULT_ELEMENT_LOCAL_NAME = "CarriedKeyName";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName", "xenc");
}
