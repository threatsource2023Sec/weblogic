package org.python.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.cryptopro.GOST3410NamedParameters;
import org.python.bouncycastle.asn1.cryptopro.GOST3410ParamSetParameters;
import org.python.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.python.bouncycastle.jce.interfaces.GOST3410Params;

public class GOST3410ParameterSpec implements AlgorithmParameterSpec, GOST3410Params {
   private GOST3410PublicKeyParameterSetSpec keyParameters;
   private String keyParamSetOID;
   private String digestParamSetOID;
   private String encryptionParamSetOID;

   public GOST3410ParameterSpec(String var1, String var2, String var3) {
      GOST3410ParamSetParameters var4 = null;

      try {
         var4 = GOST3410NamedParameters.getByOID(new ASN1ObjectIdentifier(var1));
      } catch (IllegalArgumentException var7) {
         ASN1ObjectIdentifier var6 = GOST3410NamedParameters.getOID(var1);
         if (var6 != null) {
            var1 = var6.getId();
            var4 = GOST3410NamedParameters.getByOID(var6);
         }
      }

      if (var4 == null) {
         throw new IllegalArgumentException("no key parameter set for passed in name/OID.");
      } else {
         this.keyParameters = new GOST3410PublicKeyParameterSetSpec(var4.getP(), var4.getQ(), var4.getA());
         this.keyParamSetOID = var1;
         this.digestParamSetOID = var2;
         this.encryptionParamSetOID = var3;
      }
   }

   public GOST3410ParameterSpec(String var1, String var2) {
      this(var1, var2, (String)null);
   }

   public GOST3410ParameterSpec(String var1) {
      this(var1, CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet.getId(), (String)null);
   }

   public GOST3410ParameterSpec(GOST3410PublicKeyParameterSetSpec var1) {
      this.keyParameters = var1;
      this.digestParamSetOID = CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet.getId();
      this.encryptionParamSetOID = null;
   }

   public String getPublicKeyParamSetOID() {
      return this.keyParamSetOID;
   }

   public GOST3410PublicKeyParameterSetSpec getPublicKeyParameters() {
      return this.keyParameters;
   }

   public String getDigestParamSetOID() {
      return this.digestParamSetOID;
   }

   public String getEncryptionParamSetOID() {
      return this.encryptionParamSetOID;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof GOST3410ParameterSpec)) {
         return false;
      } else {
         GOST3410ParameterSpec var2 = (GOST3410ParameterSpec)var1;
         return this.keyParameters.equals(var2.keyParameters) && this.digestParamSetOID.equals(var2.digestParamSetOID) && (this.encryptionParamSetOID == var2.encryptionParamSetOID || this.encryptionParamSetOID != null && this.encryptionParamSetOID.equals(var2.encryptionParamSetOID));
      }
   }

   public int hashCode() {
      return this.keyParameters.hashCode() ^ this.digestParamSetOID.hashCode() ^ (this.encryptionParamSetOID != null ? this.encryptionParamSetOID.hashCode() : 0);
   }

   public static GOST3410ParameterSpec fromPublicKeyAlg(GOST3410PublicKeyAlgParameters var0) {
      return var0.getEncryptionParamSet() != null ? new GOST3410ParameterSpec(var0.getPublicKeyParamSet().getId(), var0.getDigestParamSet().getId(), var0.getEncryptionParamSet().getId()) : new GOST3410ParameterSpec(var0.getPublicKeyParamSet().getId(), var0.getDigestParamSet().getId());
   }
}
