package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSString;

public interface BinaryExchange extends XSString, AttributeExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "BinaryExchange";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinaryExchange", "wst");
   String TYPE_LOCAL_NAME = "BinaryExchangeType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinaryExchangeType", "wst");
   String VALUE_TYPE_ATTRIB_NAME = "ValueType";
   String ENCODING_TYPE_ATTRIB_NAME = "EncodingType";

   String getValueType();

   void setValueType(String var1);

   String getEncodingType();

   void setEncodingType(String var1);
}
