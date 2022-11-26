package org.python.bouncycastle.asn1.misc;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.util.Arrays;

public class IDEACBCPar extends ASN1Object {
   ASN1OctetString iv;

   public static IDEACBCPar getInstance(Object var0) {
      if (var0 instanceof IDEACBCPar) {
         return (IDEACBCPar)var0;
      } else {
         return var0 != null ? new IDEACBCPar(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public IDEACBCPar(byte[] var1) {
      this.iv = new DEROctetString(var1);
   }

   public IDEACBCPar(ASN1Sequence var1) {
      if (var1.size() == 1) {
         this.iv = (ASN1OctetString)var1.getObjectAt(0);
      } else {
         this.iv = null;
      }

   }

   public byte[] getIV() {
      return this.iv != null ? Arrays.clone(this.iv.getOctets()) : null;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.iv != null) {
         var1.add(this.iv);
      }

      return new DERSequence(var1);
   }
}
