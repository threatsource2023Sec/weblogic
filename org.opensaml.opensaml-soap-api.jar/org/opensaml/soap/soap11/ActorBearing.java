package org.opensaml.soap.soap11;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface ActorBearing {
   String SOAP11_ACTOR_ATTR_LOCAL_NAME = "actor";
   QName SOAP11_ACTOR_ATTR_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "actor", "soap11");
   String SOAP11_ACTOR_NEXT = "http://schemas.xmlsoap.org/soap/actor/next";

   @Nullable
   String getSOAP11Actor();

   void setSOAP11Actor(@Nullable String var1);
}
