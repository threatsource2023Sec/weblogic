package org.python.bouncycastle.pqc.jcajce.provider.newhope;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.crypto.newhope.NHPrivateKeyParameters;
import org.python.bouncycastle.pqc.jcajce.interfaces.NHPrivateKey;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Pack;

public class BCNHPrivateKey implements NHPrivateKey {
   private static final long serialVersionUID = 1L;
   private final NHPrivateKeyParameters params;

   public BCNHPrivateKey(NHPrivateKeyParameters var1) {
      this.params = var1;
   }

   public BCNHPrivateKey(PrivateKeyInfo var1) throws IOException {
      this.params = new NHPrivateKeyParameters(convert(ASN1OctetString.getInstance(var1.parsePrivateKey()).getOctets()));
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BCNHPrivateKey) {
         BCNHPrivateKey var2 = (BCNHPrivateKey)var1;
         return Arrays.areEqual(this.params.getSecData(), var2.params.getSecData());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.params.getSecData());
   }

   public final String getAlgorithm() {
      return "NH";
   }

   public byte[] getEncoded() {
      try {
         AlgorithmIdentifier var1 = new AlgorithmIdentifier(PQCObjectIdentifiers.newHope);
         short[] var2 = this.params.getSecData();
         byte[] var3 = new byte[var2.length * 2];

         for(int var4 = 0; var4 != var2.length; ++var4) {
            Pack.shortToLittleEndian(var2[var4], var3, var4 * 2);
         }

         PrivateKeyInfo var5 = new PrivateKeyInfo(var1, new DEROctetString(var3));
         return var5.getEncoded();
      } catch (IOException var6) {
         return null;
      }
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public short[] getSecretData() {
      return this.params.getSecData();
   }

   CipherParameters getKeyParams() {
      return this.params;
   }

   private static short[] convert(byte[] var0) {
      short[] var1 = new short[var0.length / 2];

      for(int var2 = 0; var2 != var1.length; ++var2) {
         var1[var2] = Pack.littleEndianToShort(var0, var2 * 2);
      }

      return var1;
   }
}
