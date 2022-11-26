package org.python.bouncycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.Digest;

public abstract class DSABase extends SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
   protected Digest digest;
   protected DSA signer;
   protected DSAEncoder encoder;

   protected DSABase(Digest var1, DSA var2, DSAEncoder var3) {
      this.digest = var1;
      this.signer = var2;
      this.encoder = var3;
   }

   protected void engineUpdate(byte var1) throws SignatureException {
      this.digest.update(var1);
   }

   protected void engineUpdate(byte[] var1, int var2, int var3) throws SignatureException {
      this.digest.update(var1, var2, var3);
   }

   protected byte[] engineSign() throws SignatureException {
      byte[] var1 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var1, 0);

      try {
         BigInteger[] var2 = this.signer.generateSignature(var1);
         return this.encoder.encode(var2[0], var2[1]);
      } catch (Exception var3) {
         throw new SignatureException(var3.toString());
      }
   }

   protected boolean engineVerify(byte[] var1) throws SignatureException {
      byte[] var2 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var2, 0);

      BigInteger[] var3;
      try {
         var3 = this.encoder.decode(var1);
      } catch (Exception var5) {
         throw new SignatureException("error decoding signature bytes.");
      }

      return this.signer.verifySignature(var2, var3[0], var3[1]);
   }

   protected void engineSetParameter(AlgorithmParameterSpec var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   /** @deprecated */
   protected void engineSetParameter(String var1, Object var2) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   /** @deprecated */
   protected Object engineGetParameter(String var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }
}
