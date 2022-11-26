package org.python.bouncycastle.asn1.cmp;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;

public class CertStatus extends ASN1Object {
   private ASN1OctetString certHash;
   private ASN1Integer certReqId;
   private PKIStatusInfo statusInfo;

   private CertStatus(ASN1Sequence var1) {
      this.certHash = ASN1OctetString.getInstance(var1.getObjectAt(0));
      this.certReqId = ASN1Integer.getInstance(var1.getObjectAt(1));
      if (var1.size() > 2) {
         this.statusInfo = PKIStatusInfo.getInstance(var1.getObjectAt(2));
      }

   }

   public CertStatus(byte[] var1, BigInteger var2) {
      this.certHash = new DEROctetString(var1);
      this.certReqId = new ASN1Integer(var2);
   }

   public CertStatus(byte[] var1, BigInteger var2, PKIStatusInfo var3) {
      this.certHash = new DEROctetString(var1);
      this.certReqId = new ASN1Integer(var2);
      this.statusInfo = var3;
   }

   public static CertStatus getInstance(Object var0) {
      if (var0 instanceof CertStatus) {
         return (CertStatus)var0;
      } else {
         return var0 != null ? new CertStatus(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1OctetString getCertHash() {
      return this.certHash;
   }

   public ASN1Integer getCertReqId() {
      return this.certReqId;
   }

   public PKIStatusInfo getStatusInfo() {
      return this.statusInfo;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.certHash);
      var1.add(this.certReqId);
      if (this.statusInfo != null) {
         var1.add(this.statusInfo);
      }

      return new DERSequence(var1);
   }
}
