package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;

class CMSAuthEnvelopedGenerator {
   public static final String AES128_CCM;
   public static final String AES192_CCM;
   public static final String AES256_CCM;
   public static final String AES128_GCM;
   public static final String AES192_GCM;
   public static final String AES256_GCM;

   static {
      AES128_CCM = NISTObjectIdentifiers.id_aes128_CCM.getId();
      AES192_CCM = NISTObjectIdentifiers.id_aes192_CCM.getId();
      AES256_CCM = NISTObjectIdentifiers.id_aes256_CCM.getId();
      AES128_GCM = NISTObjectIdentifiers.id_aes128_GCM.getId();
      AES192_GCM = NISTObjectIdentifiers.id_aes192_GCM.getId();
      AES256_GCM = NISTObjectIdentifiers.id_aes256_GCM.getId();
   }
}
