package org.opensaml.soap.wsfed;

import javax.xml.namespace.QName;

public interface Address extends WSFedObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Address";
   QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "Address", "wsa");
   String TYPE_LOCAL_NAME = "AddressType";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "AddressType", "wsa");

   String getValue();

   void setValue(String var1);
}
