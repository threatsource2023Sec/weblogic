package org.python.bouncycastle.crypto.util;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class DEROtherInfo {
   private final DERSequence sequence;

   private DEROtherInfo(DERSequence var1) {
      this.sequence = var1;
   }

   public byte[] getEncoded() throws IOException {
      return this.sequence.getEncoded();
   }

   // $FF: synthetic method
   DEROtherInfo(DERSequence var1, Object var2) {
      this(var1);
   }

   public static final class Builder {
      private final AlgorithmIdentifier algorithmID;
      private final ASN1OctetString partyUVInfo;
      private final ASN1OctetString partyVInfo;
      private ASN1TaggedObject suppPubInfo;
      private ASN1TaggedObject suppPrivInfo;

      public Builder(AlgorithmIdentifier var1, byte[] var2, byte[] var3) {
         this.algorithmID = var1;
         this.partyUVInfo = DerUtil.getOctetString(var2);
         this.partyVInfo = DerUtil.getOctetString(var3);
      }

      public Builder withSuppPubInfo(byte[] var1) {
         this.suppPubInfo = new DERTaggedObject(false, 0, DerUtil.getOctetString(var1));
         return this;
      }

      public Builder withSuppPrivInfo(byte[] var1) {
         this.suppPrivInfo = new DERTaggedObject(false, 1, DerUtil.getOctetString(var1));
         return this;
      }

      public DEROtherInfo build() {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         var1.add(this.algorithmID);
         var1.add(this.partyUVInfo);
         var1.add(this.partyVInfo);
         if (this.suppPubInfo != null) {
            var1.add(this.suppPubInfo);
         }

         if (this.suppPrivInfo != null) {
            var1.add(this.suppPrivInfo);
         }

         return new DEROtherInfo(new DERSequence(var1));
      }
   }
}
