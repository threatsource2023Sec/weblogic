package org.opensaml.xmlsec.signature;

import java.math.BigInteger;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface X509SerialNumber extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "X509SerialNumber";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber", "ds");
   String TYPE_LOCAL_NAME = "integer";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "integer", "xsd");

   @Nullable
   BigInteger getValue();

   void setValue(@Nullable BigInteger var1);
}
