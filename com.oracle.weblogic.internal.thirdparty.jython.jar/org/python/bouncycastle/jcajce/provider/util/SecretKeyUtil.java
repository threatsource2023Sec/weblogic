package org.python.bouncycastle.jcajce.provider.util;

import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.util.Integers;

public class SecretKeyUtil {
   private static Map keySizes = new HashMap();

   public static int getKeySize(ASN1ObjectIdentifier var0) {
      Integer var1 = (Integer)keySizes.get(var0);
      return var1 != null ? var1 : -1;
   }

   static {
      keySizes.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), Integers.valueOf(192));
      keySizes.put(NISTObjectIdentifiers.id_aes128_CBC, Integers.valueOf(128));
      keySizes.put(NISTObjectIdentifiers.id_aes192_CBC, Integers.valueOf(192));
      keySizes.put(NISTObjectIdentifiers.id_aes256_CBC, Integers.valueOf(256));
      keySizes.put(NTTObjectIdentifiers.id_camellia128_cbc, Integers.valueOf(128));
      keySizes.put(NTTObjectIdentifiers.id_camellia192_cbc, Integers.valueOf(192));
      keySizes.put(NTTObjectIdentifiers.id_camellia256_cbc, Integers.valueOf(256));
   }
}
