package org.apache.xml.security.encryption;

import java.util.Iterator;

public interface EncryptionProperties {
   String getId();

   void setId(String var1);

   Iterator getEncryptionProperties();

   void addEncryptionProperty(EncryptionProperty var1);

   void removeEncryptionProperty(EncryptionProperty var1);
}
