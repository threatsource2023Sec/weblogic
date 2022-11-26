package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;

public interface MGF extends AlgorithmIdentifierType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "MGF";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmlenc11#", "MGF", "xenc11");
   String TYPE_LOCAL_NAME = "MGFType";
   QName TYPE_NAME = new QName("http://www.w3.org/2009/xmlenc11#", "MGFType", "xenc11");
}
