package org.python.bouncycastle.pqc.crypto.xmss;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DefaultXMSSOid implements XMSSOid {
   private static final Map oidLookupTable;
   private final int oid;
   private final String stringRepresentation;

   private DefaultXMSSOid(int var1, String var2) {
      this.oid = var1;
      this.stringRepresentation = var2;
   }

   public static DefaultXMSSOid lookup(String var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         throw new NullPointerException("algorithmName == null");
      } else {
         return (DefaultXMSSOid)oidLookupTable.get(createKey(var0, var1, var2, var3, var4));
      }
   }

   private static String createKey(String var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         throw new NullPointerException("algorithmName == null");
      } else {
         return var0 + "-" + var1 + "-" + var2 + "-" + var3 + "-" + var4;
      }
   }

   public int getOid() {
      return this.oid;
   }

   public String toString() {
      return this.stringRepresentation;
   }

   static {
      HashMap var0 = new HashMap();
      var0.put(createKey("SHA-256", 32, 16, 67, 10), new DefaultXMSSOid(16777217, "XMSS_SHA2-256_W16_H10"));
      var0.put(createKey("SHA-256", 32, 16, 67, 16), new DefaultXMSSOid(33554434, "XMSS_SHA2-256_W16_H16"));
      var0.put(createKey("SHA-256", 32, 16, 67, 20), new DefaultXMSSOid(50331651, "XMSS_SHA2-256_W16_H20"));
      var0.put(createKey("SHA-512", 64, 16, 131, 10), new DefaultXMSSOid(67108868, "XMSS_SHA2-512_W16_H10"));
      var0.put(createKey("SHA-512", 64, 16, 131, 16), new DefaultXMSSOid(83886085, "XMSS_SHA2-512_W16_H16"));
      var0.put(createKey("SHA-512", 64, 16, 131, 20), new DefaultXMSSOid(100663302, "XMSS_SHA2-512_W16_H20"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 10), new DefaultXMSSOid(117440519, "XMSS_SHAKE128_W16_H10"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 16), new DefaultXMSSOid(134217736, "XMSS_SHAKE128_W16_H16"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 20), new DefaultXMSSOid(150994953, "XMSS_SHAKE128_W16_H20"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 10), new DefaultXMSSOid(167772170, "XMSS_SHAKE256_W16_H10"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 16), new DefaultXMSSOid(184549387, "XMSS_SHAKE256_W16_H16"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 20), new DefaultXMSSOid(201326604, "XMSS_SHAKE256_W16_H20"));
      oidLookupTable = Collections.unmodifiableMap(var0);
   }
}
