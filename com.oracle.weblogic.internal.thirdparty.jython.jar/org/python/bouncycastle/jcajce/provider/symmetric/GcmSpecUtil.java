package org.python.bouncycastle.jcajce.provider.symmetric;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.cms.GCMParameters;
import org.python.bouncycastle.util.Integers;

class GcmSpecUtil {
   static final Class gcmSpecClass = lookup("javax.crypto.spec.GCMParameterSpec");

   static boolean gcmSpecExists() {
      return gcmSpecClass != null;
   }

   static boolean isGcmSpec(AlgorithmParameterSpec var0) {
      return gcmSpecClass != null && gcmSpecClass.isInstance(var0);
   }

   static boolean isGcmSpec(Class var0) {
      return gcmSpecClass == var0;
   }

   static AlgorithmParameterSpec extractGcmSpec(ASN1Primitive var0) throws InvalidParameterSpecException {
      try {
         GCMParameters var1 = GCMParameters.getInstance(var0);
         Constructor var2 = gcmSpecClass.getConstructor(Integer.TYPE, byte[].class);
         return (AlgorithmParameterSpec)var2.newInstance(Integers.valueOf(var1.getIcvLen() * 8), var1.getNonce());
      } catch (NoSuchMethodException var3) {
         throw new InvalidParameterSpecException("No constructor found!");
      } catch (Exception var4) {
         throw new InvalidParameterSpecException("Construction failed: " + var4.getMessage());
      }
   }

   static GCMParameters extractGcmParameters(AlgorithmParameterSpec var0) throws InvalidParameterSpecException {
      try {
         Method var1 = gcmSpecClass.getDeclaredMethod("getTLen");
         Method var2 = gcmSpecClass.getDeclaredMethod("getIV");
         return new GCMParameters((byte[])((byte[])var2.invoke(var0)), (Integer)var1.invoke(var0) / 8);
      } catch (Exception var3) {
         throw new InvalidParameterSpecException("Cannot process GCMParameterSpec");
      }
   }

   private static Class lookup(String var0) {
      try {
         return GcmSpecUtil.class.getClassLoader().loadClass(var0);
      } catch (Exception var2) {
         return null;
      }
   }
}
