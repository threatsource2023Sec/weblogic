package org.python.bouncycastle.asn1.cmc;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERUTF8String;

public class CMCStatusInfo extends ASN1Object {
   private final CMCStatus cMCStatus;
   private final ASN1Sequence bodyList;
   private final DERUTF8String statusString;
   private final OtherInfo otherInfo;

   CMCStatusInfo(CMCStatus var1, ASN1Sequence var2, DERUTF8String var3, OtherInfo var4) {
      this.cMCStatus = var1;
      this.bodyList = var2;
      this.statusString = var3;
      this.otherInfo = var4;
   }

   private CMCStatusInfo(ASN1Sequence var1) {
      if (var1.size() >= 2 && var1.size() <= 4) {
         this.cMCStatus = CMCStatus.getInstance(var1.getObjectAt(0));
         this.bodyList = ASN1Sequence.getInstance(var1.getObjectAt(1));
         if (var1.size() > 3) {
            this.statusString = DERUTF8String.getInstance(var1.getObjectAt(2));
            this.otherInfo = CMCStatusInfo.OtherInfo.getInstance(var1.getObjectAt(3));
         } else if (var1.size() > 2) {
            if (var1.getObjectAt(2) instanceof DERUTF8String) {
               this.statusString = DERUTF8String.getInstance(var1.getObjectAt(2));
               this.otherInfo = null;
            } else {
               this.statusString = null;
               this.otherInfo = CMCStatusInfo.OtherInfo.getInstance(var1.getObjectAt(2));
            }
         } else {
            this.statusString = null;
            this.otherInfo = null;
         }

      } else {
         throw new IllegalArgumentException("incorrect sequence size");
      }
   }

   public static CMCStatusInfo getInstance(Object var0) {
      if (var0 instanceof CMCStatusInfo) {
         return (CMCStatusInfo)var0;
      } else {
         return var0 != null ? new CMCStatusInfo(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.cMCStatus);
      var1.add(this.bodyList);
      if (this.statusString != null) {
         var1.add(this.statusString);
      }

      if (this.otherInfo != null) {
         var1.add(this.otherInfo);
      }

      return new DERSequence(var1);
   }

   public CMCStatus getCMCStatus() {
      return this.cMCStatus;
   }

   public BodyPartID[] getBodyList() {
      return Utils.toBodyPartIDArray(this.bodyList);
   }

   public DERUTF8String getStatusString() {
      return this.statusString;
   }

   public boolean hasOtherInfo() {
      return this.otherInfo != null;
   }

   public OtherInfo getOtherInfo() {
      return this.otherInfo;
   }

   public static class OtherInfo extends ASN1Object implements ASN1Choice {
      private final CMCFailInfo failInfo;
      private final PendInfo pendInfo;

      private static OtherInfo getInstance(Object var0) {
         if (var0 instanceof OtherInfo) {
            return (OtherInfo)var0;
         } else {
            if (var0 instanceof ASN1Encodable) {
               ASN1Primitive var1 = ((ASN1Encodable)var0).toASN1Primitive();
               if (var1 instanceof ASN1Integer) {
                  return new OtherInfo(CMCFailInfo.getInstance(var1));
               }

               if (var1 instanceof ASN1Sequence) {
                  return new OtherInfo(PendInfo.getInstance(var1));
               }
            }

            throw new IllegalArgumentException("unknown object in getInstance(): " + var0.getClass().getName());
         }
      }

      OtherInfo(CMCFailInfo var1) {
         this(var1, (PendInfo)null);
      }

      OtherInfo(PendInfo var1) {
         this((CMCFailInfo)null, var1);
      }

      private OtherInfo(CMCFailInfo var1, PendInfo var2) {
         this.failInfo = var1;
         this.pendInfo = var2;
      }

      public boolean isFailInfo() {
         return this.failInfo != null;
      }

      public ASN1Primitive toASN1Primitive() {
         return this.pendInfo != null ? this.pendInfo.toASN1Primitive() : this.failInfo.toASN1Primitive();
      }
   }
}
