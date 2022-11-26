package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.GeneralName;

public class PKIHeaderBuilder {
   private ASN1Integer pvno;
   private GeneralName sender;
   private GeneralName recipient;
   private ASN1GeneralizedTime messageTime;
   private AlgorithmIdentifier protectionAlg;
   private ASN1OctetString senderKID;
   private ASN1OctetString recipKID;
   private ASN1OctetString transactionID;
   private ASN1OctetString senderNonce;
   private ASN1OctetString recipNonce;
   private PKIFreeText freeText;
   private ASN1Sequence generalInfo;

   public PKIHeaderBuilder(int var1, GeneralName var2, GeneralName var3) {
      this(new ASN1Integer((long)var1), var2, var3);
   }

   private PKIHeaderBuilder(ASN1Integer var1, GeneralName var2, GeneralName var3) {
      this.pvno = var1;
      this.sender = var2;
      this.recipient = var3;
   }

   public PKIHeaderBuilder setMessageTime(ASN1GeneralizedTime var1) {
      this.messageTime = var1;
      return this;
   }

   public PKIHeaderBuilder setProtectionAlg(AlgorithmIdentifier var1) {
      this.protectionAlg = var1;
      return this;
   }

   public PKIHeaderBuilder setSenderKID(byte[] var1) {
      return this.setSenderKID((ASN1OctetString)(var1 == null ? null : new DEROctetString(var1)));
   }

   public PKIHeaderBuilder setSenderKID(ASN1OctetString var1) {
      this.senderKID = var1;
      return this;
   }

   public PKIHeaderBuilder setRecipKID(byte[] var1) {
      return this.setRecipKID(var1 == null ? null : new DEROctetString(var1));
   }

   public PKIHeaderBuilder setRecipKID(DEROctetString var1) {
      this.recipKID = var1;
      return this;
   }

   public PKIHeaderBuilder setTransactionID(byte[] var1) {
      return this.setTransactionID((ASN1OctetString)(var1 == null ? null : new DEROctetString(var1)));
   }

   public PKIHeaderBuilder setTransactionID(ASN1OctetString var1) {
      this.transactionID = var1;
      return this;
   }

   public PKIHeaderBuilder setSenderNonce(byte[] var1) {
      return this.setSenderNonce((ASN1OctetString)(var1 == null ? null : new DEROctetString(var1)));
   }

   public PKIHeaderBuilder setSenderNonce(ASN1OctetString var1) {
      this.senderNonce = var1;
      return this;
   }

   public PKIHeaderBuilder setRecipNonce(byte[] var1) {
      return this.setRecipNonce((ASN1OctetString)(var1 == null ? null : new DEROctetString(var1)));
   }

   public PKIHeaderBuilder setRecipNonce(ASN1OctetString var1) {
      this.recipNonce = var1;
      return this;
   }

   public PKIHeaderBuilder setFreeText(PKIFreeText var1) {
      this.freeText = var1;
      return this;
   }

   public PKIHeaderBuilder setGeneralInfo(InfoTypeAndValue var1) {
      return this.setGeneralInfo(makeGeneralInfoSeq(var1));
   }

   public PKIHeaderBuilder setGeneralInfo(InfoTypeAndValue[] var1) {
      return this.setGeneralInfo(makeGeneralInfoSeq(var1));
   }

   public PKIHeaderBuilder setGeneralInfo(ASN1Sequence var1) {
      this.generalInfo = var1;
      return this;
   }

   private static ASN1Sequence makeGeneralInfoSeq(InfoTypeAndValue var0) {
      return new DERSequence(var0);
   }

   private static ASN1Sequence makeGeneralInfoSeq(InfoTypeAndValue[] var0) {
      DERSequence var1 = null;
      if (var0 != null) {
         ASN1EncodableVector var2 = new ASN1EncodableVector();

         for(int var3 = 0; var3 < var0.length; ++var3) {
            var2.add(var0[var3]);
         }

         var1 = new DERSequence(var2);
      }

      return var1;
   }

   public PKIHeader build() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.pvno);
      var1.add(this.sender);
      var1.add(this.recipient);
      this.addOptional(var1, 0, this.messageTime);
      this.addOptional(var1, 1, this.protectionAlg);
      this.addOptional(var1, 2, this.senderKID);
      this.addOptional(var1, 3, this.recipKID);
      this.addOptional(var1, 4, this.transactionID);
      this.addOptional(var1, 5, this.senderNonce);
      this.addOptional(var1, 6, this.recipNonce);
      this.addOptional(var1, 7, this.freeText);
      this.addOptional(var1, 8, this.generalInfo);
      this.messageTime = null;
      this.protectionAlg = null;
      this.senderKID = null;
      this.recipKID = null;
      this.transactionID = null;
      this.senderNonce = null;
      this.recipNonce = null;
      this.freeText = null;
      this.generalInfo = null;
      return PKIHeader.getInstance(new DERSequence(var1));
   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if (var3 != null) {
         var1.add(new DERTaggedObject(true, var2, var3));
      }

   }
}
