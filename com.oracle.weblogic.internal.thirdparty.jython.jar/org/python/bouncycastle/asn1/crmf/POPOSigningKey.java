package org.python.bouncycastle.asn1.crmf;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class POPOSigningKey extends ASN1Object {
   private POPOSigningKeyInput poposkInput;
   private AlgorithmIdentifier algorithmIdentifier;
   private DERBitString signature;

   private POPOSigningKey(ASN1Sequence var1) {
      int var2 = 0;
      if (var1.getObjectAt(var2) instanceof ASN1TaggedObject) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var1.getObjectAt(var2++);
         if (var3.getTagNo() != 0) {
            throw new IllegalArgumentException("Unknown POPOSigningKeyInput tag: " + var3.getTagNo());
         }

         this.poposkInput = POPOSigningKeyInput.getInstance(var3.getObject());
      }

      this.algorithmIdentifier = AlgorithmIdentifier.getInstance(var1.getObjectAt(var2++));
      this.signature = DERBitString.getInstance(var1.getObjectAt(var2));
   }

   public static POPOSigningKey getInstance(Object var0) {
      if (var0 instanceof POPOSigningKey) {
         return (POPOSigningKey)var0;
      } else {
         return var0 != null ? new POPOSigningKey(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public static POPOSigningKey getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public POPOSigningKey(POPOSigningKeyInput var1, AlgorithmIdentifier var2, DERBitString var3) {
      this.poposkInput = var1;
      this.algorithmIdentifier = var2;
      this.signature = var3;
   }

   public POPOSigningKeyInput getPoposkInput() {
      return this.poposkInput;
   }

   public AlgorithmIdentifier getAlgorithmIdentifier() {
      return this.algorithmIdentifier;
   }

   public DERBitString getSignature() {
      return this.signature;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.poposkInput != null) {
         var1.add(new DERTaggedObject(false, 0, this.poposkInput));
      }

      var1.add(this.algorithmIdentifier);
      var1.add(this.signature);
      return new DERSequence(var1);
   }
}
