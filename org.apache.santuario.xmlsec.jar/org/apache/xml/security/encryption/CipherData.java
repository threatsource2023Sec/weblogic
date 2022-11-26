package org.apache.xml.security.encryption;

public interface CipherData {
   int VALUE_TYPE = 1;
   int REFERENCE_TYPE = 2;

   int getDataType();

   CipherValue getCipherValue();

   void setCipherValue(CipherValue var1) throws XMLEncryptionException;

   CipherReference getCipherReference();

   void setCipherReference(CipherReference var1) throws XMLEncryptionException;
}
