package org.python.bouncycastle.pqc.crypto.xmss;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DefaultXMSSMTOid implements XMSSOid {
   private static final Map oidLookupTable;
   private final int oid;
   private final String stringRepresentation;

   private DefaultXMSSMTOid(int var1, String var2) {
      this.oid = var1;
      this.stringRepresentation = var2;
   }

   public static DefaultXMSSMTOid lookup(String var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         throw new NullPointerException("algorithmName == null");
      } else {
         return (DefaultXMSSMTOid)oidLookupTable.get(createKey(var0, var1, var2, var3, var4, var5));
      }
   }

   private static String createKey(String var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         throw new NullPointerException("algorithmName == null");
      } else {
         return var0 + "-" + var1 + "-" + var2 + "-" + var3 + "-" + var4 + "-" + var5;
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
      var0.put(createKey("SHA-256", 32, 16, 67, 20, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-256_W16_H20_D2"));
      var0.put(createKey("SHA-256", 32, 16, 67, 20, 4), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-256_W16_H20_D4"));
      var0.put(createKey("SHA-256", 32, 16, 67, 40, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-256_W16_H40_D2"));
      var0.put(createKey("SHA-256", 32, 16, 67, 40, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-256_W16_H40_D4"));
      var0.put(createKey("SHA-256", 32, 16, 67, 40, 4), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-256_W16_H40_D8"));
      var0.put(createKey("SHA-256", 32, 16, 67, 60, 8), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-256_W16_H60_D3"));
      var0.put(createKey("SHA-256", 32, 16, 67, 60, 6), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-256_W16_H60_D6"));
      var0.put(createKey("SHA-256", 32, 16, 67, 60, 12), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-256_W16_H60_D12"));
      var0.put(createKey("SHA2-512", 64, 16, 131, 20, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-512_W16_H20_D2"));
      var0.put(createKey("SHA2-512", 64, 16, 131, 20, 4), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-512_W16_H20_D4"));
      var0.put(createKey("SHA2-512", 64, 16, 131, 40, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-512_W16_H40_D2"));
      var0.put(createKey("SHA2-512", 64, 16, 131, 40, 4), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-512_W16_H40_D4"));
      var0.put(createKey("SHA2-512", 64, 16, 131, 40, 8), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-512_W16_H40_D8"));
      var0.put(createKey("SHA2-512", 64, 16, 131, 60, 3), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-512_W16_H60_D3"));
      var0.put(createKey("SHA2-512", 64, 16, 131, 60, 6), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-512_W16_H60_D6"));
      var0.put(createKey("SHA2-512", 64, 16, 131, 60, 12), new DefaultXMSSMTOid(16777217, "XMSSMT_SHA2-512_W16_H60_D12"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 20, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE128_W16_H20_D2"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 20, 4), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE128_W16_H20_D4"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 40, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE128_W16_H40_D2"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 40, 4), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE128_W16_H40_D4"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 40, 8), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE128_W16_H40_D8"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 60, 3), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE128_W16_H60_D3"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 60, 6), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE128_W16_H60_D6"));
      var0.put(createKey("SHAKE128", 32, 16, 67, 60, 12), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE128_W16_H60_D12"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 20, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE256_W16_H20_D2"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 20, 4), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE256_W16_H20_D4"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 40, 2), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE256_W16_H40_D2"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 40, 4), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE256_W16_H40_D4"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 40, 8), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE256_W16_H40_D8"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 60, 3), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE256_W16_H60_D3"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 60, 6), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE256_W16_H60_D6"));
      var0.put(createKey("SHAKE256", 64, 16, 131, 60, 12), new DefaultXMSSMTOid(16777217, "XMSSMT_SHAKE256_W16_H60_D12"));
      oidLookupTable = Collections.unmodifiableMap(var0);
   }
}
