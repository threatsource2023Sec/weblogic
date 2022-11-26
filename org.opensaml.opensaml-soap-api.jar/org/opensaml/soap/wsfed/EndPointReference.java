package org.opensaml.soap.wsfed;

import javax.xml.namespace.QName;

public interface EndPointReference extends WSFedObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EndPointReference";
   QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "EndPointReference", "wsa");
   String TYPE_LOCAL_NAME = "EndPointReferenceType";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "EndPointReferenceType", "wsa");

   Address getAddress();

   void setAddress(Address var1);
}
