package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface X509SKI extends XSBase64Binary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "X509SKI";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SKI", "ds");
}
