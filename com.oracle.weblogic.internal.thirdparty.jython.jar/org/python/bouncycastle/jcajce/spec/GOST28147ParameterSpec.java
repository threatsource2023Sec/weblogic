package org.python.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.crypto.engines.GOST28147Engine;
import org.python.bouncycastle.util.Arrays;

public class GOST28147ParameterSpec implements AlgorithmParameterSpec {
   private byte[] iv;
   private byte[] sBox;
   private static Map oidMappings = new HashMap();

   public GOST28147ParameterSpec(byte[] var1) {
      this.iv = null;
      this.sBox = null;
      this.sBox = new byte[var1.length];
      System.arraycopy(var1, 0, this.sBox, 0, var1.length);
   }

   public GOST28147ParameterSpec(byte[] var1, byte[] var2) {
      this(var1);
      this.iv = new byte[var2.length];
      System.arraycopy(var2, 0, this.iv, 0, var2.length);
   }

   public GOST28147ParameterSpec(String var1) {
      this.iv = null;
      this.sBox = null;
      this.sBox = GOST28147Engine.getSBox(var1);
   }

   public GOST28147ParameterSpec(String var1, byte[] var2) {
      this(var1);
      this.iv = new byte[var2.length];
      System.arraycopy(var2, 0, this.iv, 0, var2.length);
   }

   public GOST28147ParameterSpec(ASN1ObjectIdentifier var1, byte[] var2) {
      this(getName(var1));
      this.iv = Arrays.clone(var2);
   }

   public byte[] getSbox() {
      return Arrays.clone(this.sBox);
   }

   public byte[] getIV() {
      return Arrays.clone(this.iv);
   }

   private static String getName(ASN1ObjectIdentifier var0) {
      String var1 = (String)oidMappings.get(var0);
      if (var1 == null) {
         throw new IllegalArgumentException("unknown OID: " + var0);
      } else {
         return var1;
      }
   }

   static {
      oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_A_ParamSet, "E-A");
      oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_B_ParamSet, "E-B");
      oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_C_ParamSet, "E-C");
      oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_D_ParamSet, "E-D");
   }
}
