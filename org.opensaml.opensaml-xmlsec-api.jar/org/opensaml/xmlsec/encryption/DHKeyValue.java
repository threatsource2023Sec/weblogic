package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface DHKeyValue extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DHKeyValue";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "DHKeyValue", "xenc");
   String TYPE_LOCAL_NAME = "DHKeyValueType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "DHKeyValueType", "xenc");

   @Nullable
   P getP();

   void setP(@Nullable P var1);

   @Nullable
   Q getQ();

   void setQ(@Nullable Q var1);

   @Nullable
   Generator getGenerator();

   void setGenerator(@Nullable Generator var1);

   @Nullable
   Public getPublic();

   void setPublic(@Nullable Public var1);

   @Nullable
   Seed getSeed();

   void setSeed(@Nullable Seed var1);

   @Nullable
   PgenCounter getPgenCounter();

   void setPgenCounter(@Nullable PgenCounter var1);
}
