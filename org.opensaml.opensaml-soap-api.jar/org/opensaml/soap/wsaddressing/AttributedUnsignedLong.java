package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;

public interface AttributedUnsignedLong extends AttributeExtensibleXMLObject, WSAddressingObject {
   String TYPE_LOCAL_NAME = "AttributedUnsignedLongType";
   QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "AttributedUnsignedLongType", "wsa");

   Long getValue();

   void setValue(Long var1);
}
