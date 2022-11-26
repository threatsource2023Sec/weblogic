package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface RSAKeyValue extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RSAKeyValue";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue", "ds");
   String TYPE_LOCAL_NAME = "RSAKeyValueType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValueType", "ds");

   @Nullable
   Modulus getModulus();

   void setModulus(@Nullable Modulus var1);

   @Nullable
   Exponent getExponent();

   void setExponent(@Nullable Exponent var1);
}
