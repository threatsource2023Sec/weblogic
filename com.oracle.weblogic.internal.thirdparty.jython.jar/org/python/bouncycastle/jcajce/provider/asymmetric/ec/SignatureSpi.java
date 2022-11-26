package org.python.bouncycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.NullDigest;
import org.python.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.signers.ECDSASigner;
import org.python.bouncycastle.crypto.signers.ECNRSigner;
import org.python.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.python.bouncycastle.crypto.util.DigestFactory;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.DSABase;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.DSAEncoder;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.util.Arrays;

public class SignatureSpi extends DSABase {
   SignatureSpi(Digest var1, DSA var2, DSAEncoder var3) {
      super(var1, var2, var3);
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      AsymmetricKeyParameter var2 = ECUtils.generatePublicKeyParameter(var1);
      this.digest.reset();
      this.signer.init(false, var2);
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      AsymmetricKeyParameter var2 = ECUtil.generatePrivateKeyParameter(var1);
      this.digest.reset();
      if (this.appRandom != null) {
         this.signer.init(true, new ParametersWithRandom(var2, this.appRandom));
      } else {
         this.signer.init(true, var2);
      }

   }

   private static class PlainDSAEncoder implements DSAEncoder {
      private PlainDSAEncoder() {
      }

      public byte[] encode(BigInteger var1, BigInteger var2) throws IOException {
         byte[] var3 = this.makeUnsigned(var1);
         byte[] var4 = this.makeUnsigned(var2);
         byte[] var5;
         if (var3.length > var4.length) {
            var5 = new byte[var3.length * 2];
         } else {
            var5 = new byte[var4.length * 2];
         }

         System.arraycopy(var3, 0, var5, var5.length / 2 - var3.length, var3.length);
         System.arraycopy(var4, 0, var5, var5.length - var4.length, var4.length);
         return var5;
      }

      private byte[] makeUnsigned(BigInteger var1) {
         byte[] var2 = var1.toByteArray();
         if (var2[0] == 0) {
            byte[] var3 = new byte[var2.length - 1];
            System.arraycopy(var2, 1, var3, 0, var3.length);
            return var3;
         } else {
            return var2;
         }
      }

      public BigInteger[] decode(byte[] var1) throws IOException {
         BigInteger[] var2 = new BigInteger[2];
         byte[] var3 = new byte[var1.length / 2];
         byte[] var4 = new byte[var1.length / 2];
         System.arraycopy(var1, 0, var3, 0, var3.length);
         System.arraycopy(var1, var3.length, var4, 0, var4.length);
         var2[0] = new BigInteger(1, var3);
         var2[1] = new BigInteger(1, var4);
         return var2;
      }

      // $FF: synthetic method
      PlainDSAEncoder(Object var1) {
         this();
      }
   }

   private static class StdDSAEncoder implements DSAEncoder {
      private StdDSAEncoder() {
      }

      public byte[] encode(BigInteger var1, BigInteger var2) throws IOException {
         ASN1EncodableVector var3 = new ASN1EncodableVector();
         var3.add(new ASN1Integer(var1));
         var3.add(new ASN1Integer(var2));
         return (new DERSequence(var3)).getEncoded("DER");
      }

      public BigInteger[] decode(byte[] var1) throws IOException {
         ASN1Sequence var2 = (ASN1Sequence)ASN1Primitive.fromByteArray(var1);
         if (var2.size() != 2) {
            throw new IOException("malformed signature");
         } else if (!Arrays.areEqual(var1, var2.getEncoded("DER"))) {
            throw new IOException("malformed signature");
         } else {
            BigInteger[] var3 = new BigInteger[]{ASN1Integer.getInstance(var2.getObjectAt(0)).getValue(), ASN1Integer.getInstance(var2.getObjectAt(1)).getValue()};
            return var3;
         }
      }

      // $FF: synthetic method
      StdDSAEncoder(Object var1) {
         this();
      }
   }

   public static class ecCVCDSA extends SignatureSpi {
      public ecCVCDSA() {
         super(DigestFactory.createSHA1(), new ECDSASigner(), new PlainDSAEncoder());
      }
   }

   public static class ecCVCDSA224 extends SignatureSpi {
      public ecCVCDSA224() {
         super(DigestFactory.createSHA224(), new ECDSASigner(), new PlainDSAEncoder());
      }
   }

   public static class ecCVCDSA256 extends SignatureSpi {
      public ecCVCDSA256() {
         super(DigestFactory.createSHA256(), new ECDSASigner(), new PlainDSAEncoder());
      }
   }

   public static class ecCVCDSA384 extends SignatureSpi {
      public ecCVCDSA384() {
         super(DigestFactory.createSHA384(), new ECDSASigner(), new PlainDSAEncoder());
      }
   }

   public static class ecCVCDSA512 extends SignatureSpi {
      public ecCVCDSA512() {
         super(DigestFactory.createSHA512(), new ECDSASigner(), new PlainDSAEncoder());
      }
   }

   public static class ecDSA extends SignatureSpi {
      public ecDSA() {
         super(DigestFactory.createSHA1(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSA224 extends SignatureSpi {
      public ecDSA224() {
         super(DigestFactory.createSHA224(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSA256 extends SignatureSpi {
      public ecDSA256() {
         super(DigestFactory.createSHA256(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSA384 extends SignatureSpi {
      public ecDSA384() {
         super(DigestFactory.createSHA384(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSA512 extends SignatureSpi {
      public ecDSA512() {
         super(DigestFactory.createSHA512(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSARipeMD160 extends SignatureSpi {
      public ecDSARipeMD160() {
         super(new RIPEMD160Digest(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSASha3_224 extends SignatureSpi {
      public ecDSASha3_224() {
         super(DigestFactory.createSHA3_224(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSASha3_256 extends SignatureSpi {
      public ecDSASha3_256() {
         super(DigestFactory.createSHA3_256(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSASha3_384 extends SignatureSpi {
      public ecDSASha3_384() {
         super(DigestFactory.createSHA3_384(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSASha3_512 extends SignatureSpi {
      public ecDSASha3_512() {
         super(DigestFactory.createSHA3_512(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDSAnone extends SignatureSpi {
      public ecDSAnone() {
         super(new NullDigest(), new ECDSASigner(), new StdDSAEncoder());
      }
   }

   public static class ecDetDSA extends SignatureSpi {
      public ecDetDSA() {
         super(DigestFactory.createSHA1(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA1())), new StdDSAEncoder());
      }
   }

   public static class ecDetDSA224 extends SignatureSpi {
      public ecDetDSA224() {
         super(DigestFactory.createSHA224(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA224())), new StdDSAEncoder());
      }
   }

   public static class ecDetDSA256 extends SignatureSpi {
      public ecDetDSA256() {
         super(DigestFactory.createSHA256(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA256())), new StdDSAEncoder());
      }
   }

   public static class ecDetDSA384 extends SignatureSpi {
      public ecDetDSA384() {
         super(DigestFactory.createSHA384(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA384())), new StdDSAEncoder());
      }
   }

   public static class ecDetDSA512 extends SignatureSpi {
      public ecDetDSA512() {
         super(DigestFactory.createSHA512(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA512())), new StdDSAEncoder());
      }
   }

   public static class ecDetDSASha3_224 extends SignatureSpi {
      public ecDetDSASha3_224() {
         super(DigestFactory.createSHA3_224(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_224())), new StdDSAEncoder());
      }
   }

   public static class ecDetDSASha3_256 extends SignatureSpi {
      public ecDetDSASha3_256() {
         super(DigestFactory.createSHA3_256(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_256())), new StdDSAEncoder());
      }
   }

   public static class ecDetDSASha3_384 extends SignatureSpi {
      public ecDetDSASha3_384() {
         super(DigestFactory.createSHA3_384(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_384())), new StdDSAEncoder());
      }
   }

   public static class ecDetDSASha3_512 extends SignatureSpi {
      public ecDetDSASha3_512() {
         super(DigestFactory.createSHA3_512(), new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_512())), new StdDSAEncoder());
      }
   }

   public static class ecNR extends SignatureSpi {
      public ecNR() {
         super(DigestFactory.createSHA1(), new ECNRSigner(), new StdDSAEncoder());
      }
   }

   public static class ecNR224 extends SignatureSpi {
      public ecNR224() {
         super(DigestFactory.createSHA224(), new ECNRSigner(), new StdDSAEncoder());
      }
   }

   public static class ecNR256 extends SignatureSpi {
      public ecNR256() {
         super(DigestFactory.createSHA256(), new ECNRSigner(), new StdDSAEncoder());
      }
   }

   public static class ecNR384 extends SignatureSpi {
      public ecNR384() {
         super(DigestFactory.createSHA384(), new ECNRSigner(), new StdDSAEncoder());
      }
   }

   public static class ecNR512 extends SignatureSpi {
      public ecNR512() {
         super(DigestFactory.createSHA512(), new ECNRSigner(), new StdDSAEncoder());
      }
   }

   public static class ecPlainDSARP160 extends SignatureSpi {
      public ecPlainDSARP160() {
         super(new RIPEMD160Digest(), new ECDSASigner(), new PlainDSAEncoder());
      }
   }
}
