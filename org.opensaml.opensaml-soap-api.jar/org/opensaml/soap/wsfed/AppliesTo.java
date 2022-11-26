package org.opensaml.soap.wsfed;

import javax.xml.namespace.QName;

public interface AppliesTo extends WSFedObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AppliesTo";
   QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "AppliesTo", "wsp");
   String TYPE_LOCAL_NAME = "AppliesToType";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "AppliesToType", "wsp");

   EndPointReference getEndPointReference();

   void setEndPointReference(EndPointReference var1);
}
