package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;

public interface DataReference extends ReferenceType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DataReference";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "DataReference", "xenc");
}
