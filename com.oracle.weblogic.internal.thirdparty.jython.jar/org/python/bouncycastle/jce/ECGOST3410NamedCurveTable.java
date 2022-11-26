package org.python.bouncycastle.jce;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

public class ECGOST3410NamedCurveTable {
   public static ECNamedCurveParameterSpec getParameterSpec(String var0) {
      ECDomainParameters var1 = ECGOST3410NamedCurves.getByName(var0);
      if (var1 == null) {
         try {
            var1 = ECGOST3410NamedCurves.getByOID(new ASN1ObjectIdentifier(var0));
         } catch (IllegalArgumentException var3) {
            return null;
         }
      }

      return var1 == null ? null : new ECNamedCurveParameterSpec(var0, var1.getCurve(), var1.getG(), var1.getN(), var1.getH(), var1.getSeed());
   }

   public static Enumeration getNames() {
      return ECGOST3410NamedCurves.getNames();
   }
}
