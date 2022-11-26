package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface X509IssuerSerial extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "X509IssuerSerial";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial", "ds");
   String TYPE_LOCAL_NAME = "X509IssuerSerialType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerialType", "ds");

   @Nullable
   X509IssuerName getX509IssuerName();

   void setX509IssuerName(@Nullable X509IssuerName var1);

   @Nullable
   X509SerialNumber getX509SerialNumber();

   void setX509SerialNumber(@Nullable X509SerialNumber var1);
}
