package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;

public interface XPath extends XSString {
   String DEFAULT_ELEMENT_LOCAL_NAME = "XPath";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "XPath", "ds");
}
