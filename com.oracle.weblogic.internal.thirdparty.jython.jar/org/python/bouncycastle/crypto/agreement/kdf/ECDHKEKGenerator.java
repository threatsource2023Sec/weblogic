package org.python.bouncycastle.crypto.agreement.kdf;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.DerivationParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.DigestDerivationFunction;
import org.python.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.python.bouncycastle.crypto.params.KDFParameters;
import org.python.bouncycastle.util.Pack;

public class ECDHKEKGenerator implements DigestDerivationFunction {
   private DigestDerivationFunction kdf;
   private ASN1ObjectIdentifier algorithm;
   private int keySize;
   private byte[] z;

   public ECDHKEKGenerator(Digest var1) {
      this.kdf = new KDF2BytesGenerator(var1);
   }

   public void init(DerivationParameters var1) {
      DHKDFParameters var2 = (DHKDFParameters)var1;
      this.algorithm = var2.getAlgorithm();
      this.keySize = var2.getKeySize();
      this.z = var2.getZ();
   }

   public Digest getDigest() {
      return this.kdf.getDigest();
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      var4.add(new AlgorithmIdentifier(this.algorithm, DERNull.INSTANCE));
      var4.add(new DERTaggedObject(true, 2, new DEROctetString(Pack.intToBigEndian(this.keySize))));

      try {
         this.kdf.init(new KDFParameters(this.z, (new DERSequence(var4)).getEncoded("DER")));
      } catch (IOException var6) {
         throw new IllegalArgumentException("unable to initialise kdf: " + var6.getMessage());
      }

      return this.kdf.generateBytes(var1, var2, var3);
   }
}
