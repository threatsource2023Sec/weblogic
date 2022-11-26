package org.python.bouncycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.math.ec.ECCurve;

class ECUtils {
   static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      return (AsymmetricKeyParameter)(var0 instanceof BCECPublicKey ? ((BCECPublicKey)var0).engineGetKeyParameters() : ECUtil.generatePublicKeyParameter(var0));
   }

   static X9ECParameters getDomainParametersFromGenSpec(ECGenParameterSpec var0) {
      return getDomainParametersFromName(var0.getName());
   }

   static X9ECParameters getDomainParametersFromName(String var0) {
      X9ECParameters var2;
      try {
         if (var0.charAt(0) >= '0' && var0.charAt(0) <= '2') {
            ASN1ObjectIdentifier var1 = new ASN1ObjectIdentifier(var0);
            var2 = ECUtil.getNamedCurveByOid(var1);
         } else if (var0.indexOf(32) > 0) {
            var0 = var0.substring(var0.indexOf(32) + 1);
            var2 = ECUtil.getNamedCurveByName(var0);
         } else {
            var2 = ECUtil.getNamedCurveByName(var0);
         }
      } catch (IllegalArgumentException var3) {
         var2 = ECUtil.getNamedCurveByName(var0);
      }

      return var2;
   }

   static X962Parameters getDomainParametersFromName(ECParameterSpec var0, boolean var1) {
      X962Parameters var3;
      if (var0 instanceof ECNamedCurveSpec) {
         ASN1ObjectIdentifier var2 = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)var0).getName());
         if (var2 == null) {
            var2 = new ASN1ObjectIdentifier(((ECNamedCurveSpec)var0).getName());
         }

         var3 = new X962Parameters(var2);
      } else if (var0 == null) {
         var3 = new X962Parameters(DERNull.INSTANCE);
      } else {
         ECCurve var5 = EC5Util.convertCurve(var0.getCurve());
         X9ECParameters var4 = new X9ECParameters(var5, EC5Util.convertPoint(var5, var0.getGenerator(), var1), var0.getOrder(), BigInteger.valueOf((long)var0.getCofactor()), var0.getCurve().getSeed());
         var3 = new X962Parameters(var4);
      }

      return var3;
   }
}
