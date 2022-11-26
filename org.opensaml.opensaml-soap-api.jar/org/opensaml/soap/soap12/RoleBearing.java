package org.opensaml.soap.soap12;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface RoleBearing {
   String SOAP12_ROLE_ATTR_LOCAL_NAME = "role";
   QName SOAP12_ROLE_ATTR_NAME = new QName("http://www.w3.org/2003/05/soap-envelope", "role", "soap12");

   @Nullable
   String getSOAP12Role();

   void setSOAP12Role(@Nullable String var1);
}
