package org.python.bouncycastle.math.ec.tools;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeSet;
import org.python.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.crypto.ec.CustomNamedCurves;
import org.python.bouncycastle.math.ec.ECAlgorithms;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.util.Integers;

public class TraceOptimizer {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final SecureRandom R = new SecureRandom();

   public static void main(String[] var0) {
      TreeSet var1 = new TreeSet(enumToList(ECNamedCurveTable.getNames()));
      var1.addAll(enumToList(CustomNamedCurves.getNames()));
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         X9ECParameters var4 = CustomNamedCurves.getByName(var3);
         if (var4 == null) {
            var4 = ECNamedCurveTable.getByName(var3);
         }

         if (var4 != null && ECAlgorithms.isF2mCurve(var4.getCurve())) {
            System.out.print(var3 + ":");
            implPrintNonZeroTraceBits(var4);
         }
      }

   }

   public static void printNonZeroTraceBits(X9ECParameters var0) {
      if (!ECAlgorithms.isF2mCurve(var0.getCurve())) {
         throw new IllegalArgumentException("Trace only defined over characteristic-2 fields");
      } else {
         implPrintNonZeroTraceBits(var0);
      }
   }

   public static void implPrintNonZeroTraceBits(X9ECParameters var0) {
      ECCurve var1 = var0.getCurve();
      int var2 = var1.getFieldSize();
      ArrayList var3 = new ArrayList();

      int var4;
      BigInteger var5;
      ECFieldElement var6;
      int var7;
      for(var4 = 0; var4 < var2; ++var4) {
         var5 = ONE.shiftLeft(var4);
         var6 = var1.fromBigInteger(var5);
         var7 = calculateTrace(var6);
         if (var7 != 0) {
            var3.add(Integers.valueOf(var4));
            System.out.print(" " + var4);
         }
      }

      System.out.println();

      for(var4 = 0; var4 < 1000; ++var4) {
         var5 = new BigInteger(var2, R);
         var6 = var1.fromBigInteger(var5);
         var7 = calculateTrace(var6);
         int var8 = 0;

         for(int var9 = 0; var9 < var3.size(); ++var9) {
            int var10 = (Integer)var3.get(var9);
            if (var5.testBit(var10)) {
               var8 ^= 1;
            }
         }

         if (var7 != var8) {
            throw new IllegalStateException("Optimized-trace sanity check failed");
         }
      }

   }

   private static int calculateTrace(ECFieldElement var0) {
      int var1 = var0.getFieldSize();
      ECFieldElement var2 = var0;

      for(int var3 = 1; var3 < var1; ++var3) {
         var0 = var0.square();
         var2 = var2.add(var0);
      }

      BigInteger var4 = var2.toBigInteger();
      if (var4.bitLength() > 1) {
         throw new IllegalStateException();
      } else {
         return var4.intValue();
      }
   }

   private static ArrayList enumToList(Enumeration var0) {
      ArrayList var1 = new ArrayList();

      while(var0.hasMoreElements()) {
         var1.add(var0.nextElement());
      }

      return var1;
   }
}
