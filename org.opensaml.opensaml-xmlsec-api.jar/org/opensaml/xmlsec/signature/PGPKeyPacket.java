package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface PGPKeyPacket extends XSBase64Binary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PGPKeyPacket";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPKeyPacket", "ds");
}
