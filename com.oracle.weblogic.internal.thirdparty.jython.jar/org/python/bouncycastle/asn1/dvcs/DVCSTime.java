package org.python.bouncycastle.asn1.dvcs;

import java.util.Date;
import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.cms.ContentInfo;

public class DVCSTime extends ASN1Object implements ASN1Choice {
   private final ASN1GeneralizedTime genTime;
   private final ContentInfo timeStampToken;

   public DVCSTime(Date var1) {
      this(new ASN1GeneralizedTime(var1));
   }

   public DVCSTime(ASN1GeneralizedTime var1) {
      this.genTime = var1;
      this.timeStampToken = null;
   }

   public DVCSTime(ContentInfo var1) {
      this.genTime = null;
      this.timeStampToken = var1;
   }

   public static DVCSTime getInstance(Object var0) {
      if (var0 instanceof DVCSTime) {
         return (DVCSTime)var0;
      } else if (var0 instanceof ASN1GeneralizedTime) {
         return new DVCSTime(ASN1GeneralizedTime.getInstance(var0));
      } else {
         return var0 != null ? new DVCSTime(ContentInfo.getInstance(var0)) : null;
      }
   }

   public static DVCSTime getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public ASN1GeneralizedTime getGenTime() {
      return this.genTime;
   }

   public ContentInfo getTimeStampToken() {
      return this.timeStampToken;
   }

   public ASN1Primitive toASN1Primitive() {
      return (ASN1Primitive)(this.genTime != null ? this.genTime : this.timeStampToken.toASN1Primitive());
   }

   public String toString() {
      return this.genTime != null ? this.genTime.toString() : this.timeStampToken.toString();
   }
}
