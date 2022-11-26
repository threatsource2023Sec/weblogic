package org.python.bouncycastle.operator.jcajce;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;

public class JcaAlgorithmParametersConverter {
   public AlgorithmIdentifier getAlgorithmIdentifier(ASN1ObjectIdentifier var1, AlgorithmParameters var2) throws InvalidAlgorithmParameterException {
      try {
         ASN1Primitive var3 = ASN1Primitive.fromByteArray(var2.getEncoded());
         return new AlgorithmIdentifier(var1, var3);
      } catch (IOException var4) {
         throw new InvalidAlgorithmParameterException("unable to encode parameters object: " + var4.getMessage());
      }
   }

   public AlgorithmIdentifier getAlgorithmIdentifier(ASN1ObjectIdentifier var1, AlgorithmParameterSpec var2) throws InvalidAlgorithmParameterException {
      if (var2 instanceof OAEPParameterSpec) {
         if (var2.equals(OAEPParameterSpec.DEFAULT)) {
            return new AlgorithmIdentifier(var1, new RSAESOAEPparams(RSAESOAEPparams.DEFAULT_HASH_ALGORITHM, RSAESOAEPparams.DEFAULT_MASK_GEN_FUNCTION, RSAESOAEPparams.DEFAULT_P_SOURCE_ALGORITHM));
         } else {
            OAEPParameterSpec var3 = (OAEPParameterSpec)var2;
            PSource var4 = var3.getPSource();
            if (!var3.getMGFAlgorithm().equals(OAEPParameterSpec.DEFAULT.getMGFAlgorithm())) {
               throw new InvalidAlgorithmParameterException("only " + OAEPParameterSpec.DEFAULT.getMGFAlgorithm() + " mask generator supported.");
            } else {
               AlgorithmIdentifier var5 = (new DefaultDigestAlgorithmIdentifierFinder()).find(var3.getDigestAlgorithm());
               AlgorithmIdentifier var6 = (new DefaultDigestAlgorithmIdentifierFinder()).find(((MGF1ParameterSpec)var3.getMGFParameters()).getDigestAlgorithm());
               return new AlgorithmIdentifier(var1, new RSAESOAEPparams(var5, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, var6), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(((PSource.PSpecified)var4).getValue()))));
            }
         }
      } else {
         throw new InvalidAlgorithmParameterException("unknown parameter spec passed.");
      }
   }
}
