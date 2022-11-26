package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface CipherData extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "CipherData";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc");
   String TYPE_LOCAL_NAME = "CipherDataType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherDataType", "xenc");

   @Nullable
   CipherValue getCipherValue();

   void setCipherValue(@Nullable CipherValue var1);

   @Nullable
   CipherReference getCipherReference();

   void setCipherReference(@Nullable CipherReference var1);
}
