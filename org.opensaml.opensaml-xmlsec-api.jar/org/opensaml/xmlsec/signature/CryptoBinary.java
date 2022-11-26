package org.opensaml.xmlsec.signature;

import java.math.BigInteger;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface CryptoBinary extends XSBase64Binary {
   String TYPE_LOCAL_NAME = "CryptoBinary";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "CryptoBinary", "ds");

   @Nullable
   BigInteger getValueBigInt();

   void setValueBigInt(@Nullable BigInteger var1);
}
