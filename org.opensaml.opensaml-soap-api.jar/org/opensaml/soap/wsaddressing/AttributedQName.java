package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSQName;

public interface AttributedQName extends XSQName, AttributeExtensibleXMLObject, WSAddressingObject {
   String TYPE_LOCAL_NAME = "AttributedQNameType";
   QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "AttributedQNameType", "wsa");
}
