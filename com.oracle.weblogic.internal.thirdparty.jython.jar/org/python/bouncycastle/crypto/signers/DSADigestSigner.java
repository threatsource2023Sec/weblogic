package org.python.bouncycastle.crypto.signers;

import java.io.IOException;
import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;

public class DSADigestSigner implements Signer {
   private final Digest digest;
   private final DSA dsaSigner;
   private boolean forSigning;

   public DSADigestSigner(DSA var1, Digest var2) {
      this.digest = var2;
      this.dsaSigner = var1;
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forSigning = var1;
      AsymmetricKeyParameter var3;
      if (var2 instanceof ParametersWithRandom) {
         var3 = (AsymmetricKeyParameter)((ParametersWithRandom)var2).getParameters();
      } else {
         var3 = (AsymmetricKeyParameter)var2;
      }

      if (var1 && !var3.isPrivate()) {
         throw new IllegalArgumentException("Signing Requires Private Key.");
      } else if (!var1 && var3.isPrivate()) {
         throw new IllegalArgumentException("Verification Requires Public Key.");
      } else {
         this.reset();
         this.dsaSigner.init(var1, var2);
      }
   }

   public void update(byte var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public byte[] generateSignature() {
      if (!this.forSigning) {
         throw new IllegalStateException("DSADigestSigner not initialised for signature generation.");
      } else {
         byte[] var1 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var1, 0);
         BigInteger[] var2 = this.dsaSigner.generateSignature(var1);

         try {
            return this.derEncode(var2[0], var2[1]);
         } catch (IOException var4) {
            throw new IllegalStateException("unable to encode signature");
         }
      }
   }

   public boolean verifySignature(byte[] var1) {
      if (this.forSigning) {
         throw new IllegalStateException("DSADigestSigner not initialised for verification");
      } else {
         byte[] var2 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var2, 0);

         try {
            BigInteger[] var3 = this.derDecode(var1);
            return this.dsaSigner.verifySignature(var2, var3[0], var3[1]);
         } catch (IOException var4) {
            return false;
         }
      }
   }

   public void reset() {
      this.digest.reset();
   }

   private byte[] derEncode(BigInteger var1, BigInteger var2) throws IOException {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(new ASN1Integer(var1));
      var3.add(new ASN1Integer(var2));
      return (new DERSequence(var3)).getEncoded("DER");
   }

   private BigInteger[] derDecode(byte[] var1) throws IOException {
      ASN1Sequence var2 = (ASN1Sequence)ASN1Primitive.fromByteArray(var1);
      return new BigInteger[]{((ASN1Integer)var2.getObjectAt(0)).getValue(), ((ASN1Integer)var2.getObjectAt(1)).getValue()};
   }
}
