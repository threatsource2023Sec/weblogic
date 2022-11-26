package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface ECKeyValue extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ECKeyValue";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "ECKeyValue", "ds11");
   String TYPE_LOCAL_NAME = "ECKeyValueType";
   QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "ECKeyValueType", "ds11");
   String ID_ATTRIB_NAME = "Id";

   @Nullable
   String getID();

   void setID(@Nullable String var1);

   @Nullable
   XMLObject getECParameters();

   void setECParameters(@Nullable XMLObject var1);

   @Nullable
   NamedCurve getNamedCurve();

   void setNamedCurve(@Nullable NamedCurve var1);

   @Nullable
   PublicKey getPublicKey();

   void setPublicKey(@Nullable PublicKey var1);
}
