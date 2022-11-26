package org.python.bouncycastle.asn1.crmf;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedValue extends ASN1Object {
   private AlgorithmIdentifier intendedAlg;
   private AlgorithmIdentifier symmAlg;
   private DERBitString encSymmKey;
   private AlgorithmIdentifier keyAlg;
   private ASN1OctetString valueHint;
   private DERBitString encValue;

   private EncryptedValue(ASN1Sequence var1) {
      int var2;
      for(var2 = 0; var1.getObjectAt(var2) instanceof ASN1TaggedObject; ++var2) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var1.getObjectAt(var2);
         switch (var3.getTagNo()) {
            case 0:
               this.intendedAlg = AlgorithmIdentifier.getInstance(var3, false);
               break;
            case 1:
               this.symmAlg = AlgorithmIdentifier.getInstance(var3, false);
               break;
            case 2:
               this.encSymmKey = DERBitString.getInstance(var3, false);
               break;
            case 3:
               this.keyAlg = AlgorithmIdentifier.getInstance(var3, false);
               break;
            case 4:
               this.valueHint = ASN1OctetString.getInstance(var3, false);
               break;
            default:
               throw new IllegalArgumentException("Unknown tag encountered: " + var3.getTagNo());
         }
      }

      this.encValue = DERBitString.getInstance(var1.getObjectAt(var2));
   }

   public static EncryptedValue getInstance(Object var0) {
      if (var0 instanceof EncryptedValue) {
         return (EncryptedValue)var0;
      } else {
         return var0 != null ? new EncryptedValue(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public EncryptedValue(AlgorithmIdentifier var1, AlgorithmIdentifier var2, DERBitString var3, AlgorithmIdentifier var4, ASN1OctetString var5, DERBitString var6) {
      if (var6 == null) {
         throw new IllegalArgumentException("'encValue' cannot be null");
      } else {
         this.intendedAlg = var1;
         this.symmAlg = var2;
         this.encSymmKey = var3;
         this.keyAlg = var4;
         this.valueHint = var5;
         this.encValue = var6;
      }
   }

   public AlgorithmIdentifier getIntendedAlg() {
      return this.intendedAlg;
   }

   public AlgorithmIdentifier getSymmAlg() {
      return this.symmAlg;
   }

   public DERBitString getEncSymmKey() {
      return this.encSymmKey;
   }

   public AlgorithmIdentifier getKeyAlg() {
      return this.keyAlg;
   }

   public ASN1OctetString getValueHint() {
      return this.valueHint;
   }

   public DERBitString getEncValue() {
      return this.encValue;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      this.addOptional(var1, 0, this.intendedAlg);
      this.addOptional(var1, 1, this.symmAlg);
      this.addOptional(var1, 2, this.encSymmKey);
      this.addOptional(var1, 3, this.keyAlg);
      this.addOptional(var1, 4, this.valueHint);
      var1.add(this.encValue);
      return new DERSequence(var1);
   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if (var3 != null) {
         var1.add(new DERTaggedObject(false, var2, var3));
      }

   }
}
