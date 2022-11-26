package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface EncryptedKey extends EncryptedType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedKey";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey", "xenc");
   String TYPE_LOCAL_NAME = "EncryptedKeyType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKeyType", "xenc");
   String RECIPIENT_ATTRIB_NAME = "Recipient";

   @Nullable
   String getRecipient();

   void setRecipient(@Nullable String var1);

   @Nullable
   ReferenceList getReferenceList();

   void setReferenceList(@Nullable ReferenceList var1);

   @Nullable
   CarriedKeyName getCarriedKeyName();

   void setCarriedKeyName(@Nullable CarriedKeyName var1);
}
