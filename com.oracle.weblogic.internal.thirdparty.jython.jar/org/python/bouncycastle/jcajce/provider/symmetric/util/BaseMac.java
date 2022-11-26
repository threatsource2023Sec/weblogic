package org.python.bouncycastle.jcajce.provider.symmetric.util;

import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.crypto.MacSpi;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.crypto.params.AEADParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.params.RC2Parameters;
import org.python.bouncycastle.crypto.params.SkeinParameters;
import org.python.bouncycastle.jcajce.PKCS12Key;
import org.python.bouncycastle.jcajce.spec.AEADParameterSpec;
import org.python.bouncycastle.jcajce.spec.SkeinParameterSpec;

public class BaseMac extends MacSpi implements PBE {
   private static final Class gcmSpecClass = lookup("javax.crypto.spec.GCMParameterSpec");
   private Mac macEngine;
   private int scheme = 2;
   private int pbeHash = 1;
   private int keySize = 160;

   protected BaseMac(Mac var1) {
      this.macEngine = var1;
   }

   protected BaseMac(Mac var1, int var2, int var3, int var4) {
      this.macEngine = var1;
      this.scheme = var2;
      this.pbeHash = var3;
      this.keySize = var4;
   }

   protected void engineInit(Key var1, AlgorithmParameterSpec var2) throws InvalidKeyException, InvalidAlgorithmParameterException {
      if (var1 == null) {
         throw new InvalidKeyException("key is null");
      } else {
         Object var7;
         if (var1 instanceof PKCS12Key) {
            SecretKey var3;
            try {
               var3 = (SecretKey)var1;
            } catch (Exception var11) {
               throw new InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
            }

            PBEParameterSpec var5;
            try {
               var5 = (PBEParameterSpec)var2;
            } catch (Exception var10) {
               throw new InvalidAlgorithmParameterException("PKCS12 requires a PBEParameterSpec");
            }

            if (var3 instanceof PBEKey && var5 == null) {
               var5 = new PBEParameterSpec(((PBEKey)var3).getSalt(), ((PBEKey)var3).getIterationCount());
            }

            byte var4 = 1;
            short var6 = 160;
            if (this.macEngine.getAlgorithmName().startsWith("GOST")) {
               var4 = 6;
               var6 = 256;
            } else if (this.macEngine instanceof HMac && !this.macEngine.getAlgorithmName().startsWith("SHA-1")) {
               if (this.macEngine.getAlgorithmName().startsWith("SHA-224")) {
                  var4 = 7;
                  var6 = 224;
               } else if (this.macEngine.getAlgorithmName().startsWith("SHA-256")) {
                  var4 = 4;
                  var6 = 256;
               } else if (this.macEngine.getAlgorithmName().startsWith("SHA-384")) {
                  var4 = 8;
                  var6 = 384;
               } else if (this.macEngine.getAlgorithmName().startsWith("SHA-512")) {
                  var4 = 9;
                  var6 = 512;
               } else {
                  if (!this.macEngine.getAlgorithmName().startsWith("RIPEMD160")) {
                     throw new InvalidAlgorithmParameterException("no PKCS12 mapping for HMAC: " + this.macEngine.getAlgorithmName());
                  }

                  var4 = 2;
                  var6 = 160;
               }
            }

            var7 = PBE.Util.makePBEMacParameters(var3, 2, var4, var6, var5);
         } else if (var1 instanceof BCPBEKey) {
            BCPBEKey var12 = (BCPBEKey)var1;
            if (var12.getParam() != null) {
               var7 = var12.getParam();
            } else {
               if (!(var2 instanceof PBEParameterSpec)) {
                  throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
               }

               var7 = PBE.Util.makePBEMacParameters(var12, var2);
            }
         } else {
            if (var2 instanceof PBEParameterSpec) {
               throw new InvalidAlgorithmParameterException("inappropriate parameter type: " + var2.getClass().getName());
            }

            var7 = new KeyParameter(var1.getEncoded());
         }

         KeyParameter var13;
         if (var7 instanceof ParametersWithIV) {
            var13 = (KeyParameter)((ParametersWithIV)var7).getParameters();
         } else {
            var13 = (KeyParameter)var7;
         }

         if (var2 instanceof AEADParameterSpec) {
            AEADParameterSpec var14 = (AEADParameterSpec)var2;
            var7 = new AEADParameters(var13, var14.getMacSizeInBits(), var14.getNonce(), var14.getAssociatedData());
         } else if (var2 instanceof IvParameterSpec) {
            var7 = new ParametersWithIV(var13, ((IvParameterSpec)var2).getIV());
         } else if (var2 instanceof RC2ParameterSpec) {
            var7 = new ParametersWithIV(new RC2Parameters(var13.getKey(), ((RC2ParameterSpec)var2).getEffectiveKeyBits()), ((RC2ParameterSpec)var2).getIV());
         } else if (var2 instanceof SkeinParameterSpec) {
            var7 = (new SkeinParameters.Builder(copyMap(((SkeinParameterSpec)var2).getParameters()))).setKey(var13.getKey()).build();
         } else if (var2 == null) {
            var7 = new KeyParameter(var1.getEncoded());
         } else if (gcmSpecClass != null && gcmSpecClass.isAssignableFrom(var2.getClass())) {
            try {
               Method var15 = gcmSpecClass.getDeclaredMethod("getTLen");
               Method var16 = gcmSpecClass.getDeclaredMethod("getIV");
               var7 = new AEADParameters(var13, (Integer)var15.invoke(var2), (byte[])((byte[])var16.invoke(var2)));
            } catch (Exception var9) {
               throw new InvalidAlgorithmParameterException("Cannot process GCMParameterSpec.");
            }
         } else if (!(var2 instanceof PBEParameterSpec)) {
            throw new InvalidAlgorithmParameterException("unknown parameter type: " + var2.getClass().getName());
         }

         try {
            this.macEngine.init((CipherParameters)var7);
         } catch (Exception var8) {
            throw new InvalidAlgorithmParameterException("cannot initialize MAC: " + var8.getMessage());
         }
      }
   }

   protected int engineGetMacLength() {
      return this.macEngine.getMacSize();
   }

   protected void engineReset() {
      this.macEngine.reset();
   }

   protected void engineUpdate(byte var1) {
      this.macEngine.update(var1);
   }

   protected void engineUpdate(byte[] var1, int var2, int var3) {
      this.macEngine.update(var1, var2, var3);
   }

   protected byte[] engineDoFinal() {
      byte[] var1 = new byte[this.engineGetMacLength()];
      this.macEngine.doFinal(var1, 0);
      return var1;
   }

   private static Hashtable copyMap(Map var0) {
      Hashtable var1 = new Hashtable();
      Iterator var2 = var0.keySet().iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var1.put(var3, var0.get(var3));
      }

      return var1;
   }

   private static Class lookup(String var0) {
      try {
         Class var1 = BaseBlockCipher.class.getClassLoader().loadClass(var0);
         return var1;
      } catch (Exception var2) {
         return null;
      }
   }
}
