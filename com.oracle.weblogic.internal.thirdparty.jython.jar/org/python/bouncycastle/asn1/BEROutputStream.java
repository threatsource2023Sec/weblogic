package org.python.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

public class BEROutputStream extends DEROutputStream {
   public BEROutputStream(OutputStream var1) {
      super(var1);
   }

   public void writeObject(Object var1) throws IOException {
      if (var1 == null) {
         this.writeNull();
      } else if (var1 instanceof ASN1Primitive) {
         ((ASN1Primitive)var1).encode(this);
      } else {
         if (!(var1 instanceof ASN1Encodable)) {
            throw new IOException("object not BEREncodable");
         }

         ((ASN1Encodable)var1).toASN1Primitive().encode(this);
      }

   }
}
