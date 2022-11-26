package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface EncryptedType extends XMLObject {
   String TYPE_LOCAL_NAME = "EncryptedType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedType", "xenc");
   String ID_ATTRIB_NAME = "Id";
   String TYPE_ATTRIB_NAME = "Type";
   String MIMETYPE_ATTRIB_NAME = "MimeType";
   String ENCODING_ATTRIB_NAME = "Encoding";

   @Nullable
   String getID();

   void setID(@Nullable String var1);

   @Nullable
   String getType();

   void setType(@Nullable String var1);

   @Nullable
   String getMimeType();

   void setMimeType(@Nullable String var1);

   @Nullable
   String getEncoding();

   void setEncoding(@Nullable String var1);

   @Nullable
   EncryptionMethod getEncryptionMethod();

   void setEncryptionMethod(@Nullable EncryptionMethod var1);

   @Nullable
   KeyInfo getKeyInfo();

   void setKeyInfo(@Nullable KeyInfo var1);

   @Nullable
   CipherData getCipherData();

   void setCipherData(@Nullable CipherData var1);

   @Nullable
   EncryptionProperties getEncryptionProperties();

   void setEncryptionProperties(@Nullable EncryptionProperties var1);
}
