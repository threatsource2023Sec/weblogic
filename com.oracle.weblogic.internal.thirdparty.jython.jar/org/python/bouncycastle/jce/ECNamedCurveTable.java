package org.python.bouncycastle.jce;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.crypto.ec.CustomNamedCurves;
import org.python.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

public class ECNamedCurveTable {
   public static ECNamedCurveParameterSpec getParameterSpec(String var0) {
      X9ECParameters var1 = CustomNamedCurves.getByName(var0);
      if (var1 == null) {
         try {
            var1 = CustomNamedCurves.getByOID(new ASN1ObjectIdentifier(var0));
         } catch (IllegalArgumentException var4) {
         }

         if (var1 == null) {
            var1 = org.python.bouncycastle.asn1.x9.ECNamedCurveTable.getByName(var0);
            if (var1 == null) {
               try {
                  var1 = org.python.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(new ASN1ObjectIdentifier(var0));
               } catch (IllegalArgumentException var3) {
               }
            }
         }
      }

      return var1 == null ? null : new ECNamedCurveParameterSpec(var0, var1.getCurve(), var1.getG(), var1.getN(), var1.getH(), var1.getSeed());
   }

   public static Enumeration getNames() {
      return org.python.bouncycastle.asn1.x9.ECNamedCurveTable.getNames();
   }
}
