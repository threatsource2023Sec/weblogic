package org.python.bouncycastle.math.ec.tools;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeSet;
import org.python.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.crypto.ec.CustomNamedCurves;
import org.python.bouncycastle.math.ec.ECAlgorithms;
import org.python.bouncycastle.math.ec.ECFieldElement;

public class F2mSqrtOptimizer {
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
            implPrintRootZ(var4);
         }
      }

   }

   public static void printRootZ(X9ECParameters var0) {
      if (!ECAlgorithms.isF2mCurve(var0.getCurve())) {
         throw new IllegalArgumentException("Sqrt optimization only defined over characteristic-2 fields");
      } else {
         implPrintRootZ(var0);
      }
   }

   private static void implPrintRootZ(X9ECParameters var0) {
      ECFieldElement var1 = var0.getCurve().fromBigInteger(BigInteger.valueOf(2L));
      ECFieldElement var2 = var1.sqrt();
      System.out.println(var2.toBigInteger().toString(16).toUpperCase());
      if (!var2.square().equals(var1)) {
         throw new IllegalStateException("Optimized-sqrt sanity check failed");
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
