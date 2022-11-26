package org.python.bouncycastle.jcajce.provider.asymmetric.dsa;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DSAParameters;
import org.python.bouncycastle.crypto.params.DSAPrivateKeyParameters;

public class DSAUtil {
   public static final ASN1ObjectIdentifier[] dsaOids;

   public static boolean isDsaOid(ASN1ObjectIdentifier var0) {
      for(int var1 = 0; var1 != dsaOids.length; ++var1) {
         if (var0.equals(dsaOids[var1])) {
            return true;
         }
      }

      return false;
   }

   static DSAParameters toDSAParameters(DSAParams var0) {
      return var0 != null ? new DSAParameters(var0.getP(), var0.getQ(), var0.getG()) : null;
   }

   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      if (var0 instanceof BCDSAPublicKey) {
         return ((BCDSAPublicKey)var0).engineGetKeyParameters();
      } else if (var0 instanceof DSAPublicKey) {
         return (new BCDSAPublicKey((DSAPublicKey)var0)).engineGetKeyParameters();
      } else {
         try {
            byte[] var1 = var0.getEncoded();
            BCDSAPublicKey var2 = new BCDSAPublicKey(SubjectPublicKeyInfo.getInstance(var1));
            return var2.engineGetKeyParameters();
         } catch (Exception var3) {
            throw new InvalidKeyException("can't identify DSA public key: " + var0.getClass().getName());
         }
      }
   }

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if (var0 instanceof DSAPrivateKey) {
         DSAPrivateKey var1 = (DSAPrivateKey)var0;
         return new DSAPrivateKeyParameters(var1.getX(), new DSAParameters(var1.getParams().getP(), var1.getParams().getQ(), var1.getParams().getG()));
      } else {
         throw new InvalidKeyException("can't identify DSA private key.");
      }
   }

   static {
      dsaOids = new ASN1ObjectIdentifier[]{X9ObjectIdentifiers.id_dsa, OIWObjectIdentifiers.dsaWithSHA1};
   }
}
