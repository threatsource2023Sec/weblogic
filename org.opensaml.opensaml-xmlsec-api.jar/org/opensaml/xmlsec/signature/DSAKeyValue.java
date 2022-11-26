package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface DSAKeyValue extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DSAKeyValue";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue", "ds");
   String TYPE_LOCAL_NAME = "DSAKeyValueType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValueType", "ds");

   @Nullable
   P getP();

   void setP(@Nullable P var1);

   @Nullable
   Q getQ();

   void setQ(@Nullable Q var1);

   @Nullable
   G getG();

   void setG(@Nullable G var1);

   @Nullable
   Y getY();

   void setY(@Nullable Y var1);

   @Nullable
   J getJ();

   void setJ(@Nullable J var1);

   @Nullable
   Seed getSeed();

   void setSeed(@Nullable Seed var1);

   @Nullable
   PgenCounter getPgenCounter();

   void setPgenCounter(@Nullable PgenCounter var1);
}
