package org.python.bouncycastle.asn1.x9;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;

/** @deprecated */
public class DHValidationParms extends ASN1Object {
   private DERBitString seed;
   private ASN1Integer pgenCounter;

   public static DHValidationParms getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static DHValidationParms getInstance(Object var0) {
      if (var0 instanceof DHValidationParms) {
         return (DHValidationParms)var0;
      } else {
         return var0 != null ? new DHValidationParms(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public DHValidationParms(DERBitString var1, ASN1Integer var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("'seed' cannot be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("'pgenCounter' cannot be null");
      } else {
         this.seed = var1;
         this.pgenCounter = var2;
      }
   }

   private DHValidationParms(ASN1Sequence var1) {
      if (var1.size() != 2) {
         throw new IllegalArgumentException("Bad sequence size: " + var1.size());
      } else {
         this.seed = DERBitString.getInstance(var1.getObjectAt(0));
         this.pgenCounter = ASN1Integer.getInstance(var1.getObjectAt(1));
      }
   }

   public DERBitString getSeed() {
      return this.seed;
   }

   public ASN1Integer getPgenCounter() {
      return this.pgenCounter;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.seed);
      var1.add(this.pgenCounter);
      return new DERSequence(var1);
   }
}
