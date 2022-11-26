package org.python.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.GOST3411Digest;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.signers.DSTU4145Signer;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jce.interfaces.ECKey;

public class SignatureSpi extends java.security.SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
   private Digest digest;
   private DSA signer = new DSTU4145Signer();
   private static byte[] DEFAULT_SBOX = new byte[]{10, 9, 13, 6, 14, 11, 4, 5, 15, 1, 3, 12, 7, 0, 8, 2, 8, 0, 12, 4, 9, 6, 7, 11, 2, 3, 1, 15, 5, 14, 10, 13, 15, 6, 5, 8, 14, 11, 10, 4, 12, 0, 3, 7, 2, 9, 1, 13, 3, 8, 13, 9, 6, 11, 15, 0, 2, 5, 12, 10, 4, 14, 1, 7, 15, 8, 14, 9, 7, 2, 0, 13, 12, 6, 1, 5, 11, 4, 3, 10, 2, 8, 9, 7, 5, 15, 0, 11, 12, 1, 13, 14, 10, 3, 6, 4, 3, 8, 11, 5, 6, 4, 14, 10, 2, 12, 1, 7, 9, 15, 13, 0, 1, 2, 3, 14, 6, 13, 11, 8, 15, 10, 12, 5, 7, 9, 0, 4};

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      Object var2;
      if (var1 instanceof BCDSTU4145PublicKey) {
         var2 = ((BCDSTU4145PublicKey)var1).engineGetKeyParameters();
      } else {
         var2 = ECUtil.generatePublicKeyParameter(var1);
      }

      this.digest = new GOST3411Digest(this.expandSbox(((BCDSTU4145PublicKey)var1).getSbox()));
      this.signer.init(false, (CipherParameters)var2);
   }

   byte[] expandSbox(byte[] var1) {
      byte[] var2 = new byte[128];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3 * 2] = (byte)(var1[var3] >> 4 & 15);
         var2[var3 * 2 + 1] = (byte)(var1[var3] & 15);
      }

      return var2;
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      AsymmetricKeyParameter var2 = null;
      if (var1 instanceof ECKey) {
         var2 = ECUtil.generatePrivateKeyParameter(var1);
      }

      this.digest = new GOST3411Digest(DEFAULT_SBOX);
      if (this.appRandom != null) {
         this.signer.init(true, new ParametersWithRandom(var2, this.appRandom));
      } else {
         this.signer.init(true, var2);
      }

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
         byte[] var3 = var2[0].toByteArray();
         byte[] var4 = var2[1].toByteArray();
         byte[] var5 = new byte[var3.length > var4.length ? var3.length * 2 : var4.length * 2];
         System.arraycopy(var4, 0, var5, var5.length / 2 - var4.length, var4.length);
         System.arraycopy(var3, 0, var5, var5.length - var3.length, var3.length);
         return (new DEROctetString(var5)).getEncoded();
      } catch (Exception var6) {
         throw new SignatureException(var6.toString());
      }
   }

   protected boolean engineVerify(byte[] var1) throws SignatureException {
      byte[] var2 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var2, 0);

      BigInteger[] var6;
      try {
         byte[] var3 = ((ASN1OctetString)ASN1OctetString.fromByteArray(var1)).getOctets();
         byte[] var4 = new byte[var3.length / 2];
         byte[] var5 = new byte[var3.length / 2];
         System.arraycopy(var3, 0, var5, 0, var3.length / 2);
         System.arraycopy(var3, var3.length / 2, var4, 0, var3.length / 2);
         var6 = new BigInteger[]{new BigInteger(1, var4), new BigInteger(1, var5)};
      } catch (Exception var7) {
         throw new SignatureException("error decoding signature bytes.");
      }

      return this.signer.verifySignature(var2, var6[0], var6[1]);
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
