package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface DEREncodedKeyValue extends XSBase64Binary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DEREncodedKeyValue";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "DEREncodedKeyValue", "ds11");
   String TYPE_LOCAL_NAME = "DEREncodedKeyValueType";
   QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "DEREncodedKeyValueType", "ds11");
   String ID_ATTRIB_NAME = "Id";

   @Nullable
   String getID();

   void setID(@Nullable String var1);
}
