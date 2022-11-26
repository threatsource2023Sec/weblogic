package org.python.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSAUtil {
   public static final ASN1ObjectIdentifier[] rsaOids;

   public static boolean isRsaOid(ASN1ObjectIdentifier var0) {
      for(int var1 = 0; var1 != rsaOids.length; ++var1) {
         if (var0.equals(rsaOids[var1])) {
            return true;
         }
      }

      return false;
   }

   static RSAKeyParameters generatePublicKeyParameter(RSAPublicKey var0) {
      return new RSAKeyParameters(false, var0.getModulus(), var0.getPublicExponent());
   }

   static RSAKeyParameters generatePrivateKeyParameter(RSAPrivateKey var0) {
      if (var0 instanceof RSAPrivateCrtKey) {
         RSAPrivateCrtKey var1 = (RSAPrivateCrtKey)var0;
         return new RSAPrivateCrtKeyParameters(var1.getModulus(), var1.getPublicExponent(), var1.getPrivateExponent(), var1.getPrimeP(), var1.getPrimeQ(), var1.getPrimeExponentP(), var1.getPrimeExponentQ(), var1.getCrtCoefficient());
      } else {
         return new RSAKeyParameters(true, var0.getModulus(), var0.getPrivateExponent());
      }
   }

   static {
      rsaOids = new ASN1ObjectIdentifier[]{PKCSObjectIdentifiers.rsaEncryption, X509ObjectIdentifiers.id_ea_rsa, PKCSObjectIdentifiers.id_RSAES_OAEP, PKCSObjectIdentifiers.id_RSASSA_PSS};
   }
}
