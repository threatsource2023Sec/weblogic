package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.crmf.CertReqMessages;
import org.python.bouncycastle.asn1.pkcs.CertificationRequest;

public class PKIBody extends ASN1Object implements ASN1Choice {
   public static final int TYPE_INIT_REQ = 0;
   public static final int TYPE_INIT_REP = 1;
   public static final int TYPE_CERT_REQ = 2;
   public static final int TYPE_CERT_REP = 3;
   public static final int TYPE_P10_CERT_REQ = 4;
   public static final int TYPE_POPO_CHALL = 5;
   public static final int TYPE_POPO_REP = 6;
   public static final int TYPE_KEY_UPDATE_REQ = 7;
   public static final int TYPE_KEY_UPDATE_REP = 8;
   public static final int TYPE_KEY_RECOVERY_REQ = 9;
   public static final int TYPE_KEY_RECOVERY_REP = 10;
   public static final int TYPE_REVOCATION_REQ = 11;
   public static final int TYPE_REVOCATION_REP = 12;
   public static final int TYPE_CROSS_CERT_REQ = 13;
   public static final int TYPE_CROSS_CERT_REP = 14;
   public static final int TYPE_CA_KEY_UPDATE_ANN = 15;
   public static final int TYPE_CERT_ANN = 16;
   public static final int TYPE_REVOCATION_ANN = 17;
   public static final int TYPE_CRL_ANN = 18;
   public static final int TYPE_CONFIRM = 19;
   public static final int TYPE_NESTED = 20;
   public static final int TYPE_GEN_MSG = 21;
   public static final int TYPE_GEN_REP = 22;
   public static final int TYPE_ERROR = 23;
   public static final int TYPE_CERT_CONFIRM = 24;
   public static final int TYPE_POLL_REQ = 25;
   public static final int TYPE_POLL_REP = 26;
   private int tagNo;
   private ASN1Encodable body;

   public static PKIBody getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof PKIBody)) {
         if (var0 instanceof ASN1TaggedObject) {
            return new PKIBody((ASN1TaggedObject)var0);
         } else {
            throw new IllegalArgumentException("Invalid object: " + var0.getClass().getName());
         }
      } else {
         return (PKIBody)var0;
      }
   }

   private PKIBody(ASN1TaggedObject var1) {
      this.tagNo = var1.getTagNo();
      this.body = getBodyForType(this.tagNo, var1.getObject());
   }

   public PKIBody(int var1, ASN1Encodable var2) {
      this.tagNo = var1;
      this.body = getBodyForType(var1, var2);
   }

   private static ASN1Encodable getBodyForType(int var0, ASN1Encodable var1) {
      switch (var0) {
         case 0:
            return CertReqMessages.getInstance(var1);
         case 1:
            return CertRepMessage.getInstance(var1);
         case 2:
            return CertReqMessages.getInstance(var1);
         case 3:
            return CertRepMessage.getInstance(var1);
         case 4:
            return CertificationRequest.getInstance(var1);
         case 5:
            return POPODecKeyChallContent.getInstance(var1);
         case 6:
            return POPODecKeyRespContent.getInstance(var1);
         case 7:
            return CertReqMessages.getInstance(var1);
         case 8:
            return CertRepMessage.getInstance(var1);
         case 9:
            return CertReqMessages.getInstance(var1);
         case 10:
            return KeyRecRepContent.getInstance(var1);
         case 11:
            return RevReqContent.getInstance(var1);
         case 12:
            return RevRepContent.getInstance(var1);
         case 13:
            return CertReqMessages.getInstance(var1);
         case 14:
            return CertRepMessage.getInstance(var1);
         case 15:
            return CAKeyUpdAnnContent.getInstance(var1);
         case 16:
            return CMPCertificate.getInstance(var1);
         case 17:
            return RevAnnContent.getInstance(var1);
         case 18:
            return CRLAnnContent.getInstance(var1);
         case 19:
            return PKIConfirmContent.getInstance(var1);
         case 20:
            return PKIMessages.getInstance(var1);
         case 21:
            return GenMsgContent.getInstance(var1);
         case 22:
            return GenRepContent.getInstance(var1);
         case 23:
            return ErrorMsgContent.getInstance(var1);
         case 24:
            return CertConfirmContent.getInstance(var1);
         case 25:
            return PollReqContent.getInstance(var1);
         case 26:
            return PollRepContent.getInstance(var1);
         default:
            throw new IllegalArgumentException("unknown tag number: " + var0);
      }
   }

   public int getType() {
      return this.tagNo;
   }

   public ASN1Encodable getContent() {
      return this.body;
   }

   public ASN1Primitive toASN1Primitive() {
      return new DERTaggedObject(true, this.tagNo, this.body);
   }
}
