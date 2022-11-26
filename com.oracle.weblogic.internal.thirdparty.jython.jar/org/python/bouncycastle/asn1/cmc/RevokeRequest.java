package org.python.bouncycastle.asn1.cmc;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.CRLReason;
import org.python.bouncycastle.util.Arrays;

public class RevokeRequest extends ASN1Object {
   private final X500Name name;
   private final ASN1Integer serialNumber;
   private final CRLReason reason;
   private ASN1GeneralizedTime invalidityDate;
   private ASN1OctetString passphrase;
   private DERUTF8String comment;

   public RevokeRequest(X500Name var1, ASN1Integer var2, CRLReason var3, ASN1GeneralizedTime var4, ASN1OctetString var5, DERUTF8String var6) {
      this.name = var1;
      this.serialNumber = var2;
      this.reason = var3;
      this.invalidityDate = var4;
      this.passphrase = var5;
      this.comment = var6;
   }

   private RevokeRequest(ASN1Sequence var1) {
      if (var1.size() >= 3 && var1.size() <= 6) {
         this.name = X500Name.getInstance(var1.getObjectAt(0));
         this.serialNumber = ASN1Integer.getInstance(var1.getObjectAt(1));
         this.reason = CRLReason.getInstance(var1.getObjectAt(2));
         int var2 = 3;
         if (var1.size() > var2 && var1.getObjectAt(var2).toASN1Primitive() instanceof ASN1GeneralizedTime) {
            this.invalidityDate = ASN1GeneralizedTime.getInstance(var1.getObjectAt(var2++));
         }

         if (var1.size() > var2 && var1.getObjectAt(var2).toASN1Primitive() instanceof ASN1OctetString) {
            this.passphrase = ASN1OctetString.getInstance(var1.getObjectAt(var2++));
         }

         if (var1.size() > var2 && var1.getObjectAt(var2).toASN1Primitive() instanceof DERUTF8String) {
            this.comment = DERUTF8String.getInstance(var1.getObjectAt(var2));
         }

      } else {
         throw new IllegalArgumentException("incorrect sequence size");
      }
   }

   public static RevokeRequest getInstance(Object var0) {
      if (var0 instanceof RevokeRequest) {
         return (RevokeRequest)var0;
      } else {
         return var0 != null ? new RevokeRequest(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public X500Name getName() {
      return this.name;
   }

   public BigInteger getSerialNumber() {
      return this.serialNumber.getValue();
   }

   public CRLReason getReason() {
      return this.reason;
   }

   public ASN1GeneralizedTime getInvalidityDate() {
      return this.invalidityDate;
   }

   public void setInvalidityDate(ASN1GeneralizedTime var1) {
      this.invalidityDate = var1;
   }

   public ASN1OctetString getPassphrase() {
      return this.passphrase;
   }

   public void setPassphrase(ASN1OctetString var1) {
      this.passphrase = var1;
   }

   public DERUTF8String getComment() {
      return this.comment;
   }

   public void setComment(DERUTF8String var1) {
      this.comment = var1;
   }

   public byte[] getPassPhrase() {
      return this.passphrase != null ? Arrays.clone(this.passphrase.getOctets()) : null;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.name);
      var1.add(this.serialNumber);
      var1.add(this.reason);
      if (this.invalidityDate != null) {
         var1.add(this.invalidityDate);
      }

      if (this.passphrase != null) {
         var1.add(this.passphrase);
      }

      if (this.comment != null) {
         var1.add(this.comment);
      }

      return new DERSequence(var1);
   }
}
