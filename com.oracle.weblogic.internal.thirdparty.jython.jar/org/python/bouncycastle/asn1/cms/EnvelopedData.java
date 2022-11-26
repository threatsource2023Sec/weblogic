package org.python.bouncycastle.asn1.cms;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.BERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;

public class EnvelopedData extends ASN1Object {
   private ASN1Integer version;
   private OriginatorInfo originatorInfo;
   private ASN1Set recipientInfos;
   private EncryptedContentInfo encryptedContentInfo;
   private ASN1Set unprotectedAttrs;

   public EnvelopedData(OriginatorInfo var1, ASN1Set var2, EncryptedContentInfo var3, ASN1Set var4) {
      this.version = new ASN1Integer((long)calculateVersion(var1, var2, var4));
      this.originatorInfo = var1;
      this.recipientInfos = var2;
      this.encryptedContentInfo = var3;
      this.unprotectedAttrs = var4;
   }

   public EnvelopedData(OriginatorInfo var1, ASN1Set var2, EncryptedContentInfo var3, Attributes var4) {
      this.version = new ASN1Integer((long)calculateVersion(var1, var2, ASN1Set.getInstance(var4)));
      this.originatorInfo = var1;
      this.recipientInfos = var2;
      this.encryptedContentInfo = var3;
      this.unprotectedAttrs = ASN1Set.getInstance(var4);
   }

   /** @deprecated */
   public EnvelopedData(ASN1Sequence var1) {
      int var2 = 0;
      this.version = (ASN1Integer)var1.getObjectAt(var2++);
      ASN1Encodable var3 = var1.getObjectAt(var2++);
      if (var3 instanceof ASN1TaggedObject) {
         this.originatorInfo = OriginatorInfo.getInstance((ASN1TaggedObject)var3, false);
         var3 = var1.getObjectAt(var2++);
      }

      this.recipientInfos = ASN1Set.getInstance(var3);
      this.encryptedContentInfo = EncryptedContentInfo.getInstance(var1.getObjectAt(var2++));
      if (var1.size() > var2) {
         this.unprotectedAttrs = ASN1Set.getInstance((ASN1TaggedObject)var1.getObjectAt(var2), false);
      }

   }

   public static EnvelopedData getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static EnvelopedData getInstance(Object var0) {
      if (var0 instanceof EnvelopedData) {
         return (EnvelopedData)var0;
      } else {
         return var0 != null ? new EnvelopedData(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1Integer getVersion() {
      return this.version;
   }

   public OriginatorInfo getOriginatorInfo() {
      return this.originatorInfo;
   }

   public ASN1Set getRecipientInfos() {
      return this.recipientInfos;
   }

   public EncryptedContentInfo getEncryptedContentInfo() {
      return this.encryptedContentInfo;
   }

   public ASN1Set getUnprotectedAttrs() {
      return this.unprotectedAttrs;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.version);
      if (this.originatorInfo != null) {
         var1.add(new DERTaggedObject(false, 0, this.originatorInfo));
      }

      var1.add(this.recipientInfos);
      var1.add(this.encryptedContentInfo);
      if (this.unprotectedAttrs != null) {
         var1.add(new DERTaggedObject(false, 1, this.unprotectedAttrs));
      }

      return new BERSequence(var1);
   }

   public static int calculateVersion(OriginatorInfo var0, ASN1Set var1, ASN1Set var2) {
      byte var3;
      if (var0 == null && var2 == null) {
         var3 = 0;
         Enumeration var4 = var1.getObjects();

         while(var4.hasMoreElements()) {
            RecipientInfo var5 = RecipientInfo.getInstance(var4.nextElement());
            if (var5.getVersion().getValue().intValue() != var3) {
               var3 = 2;
               break;
            }
         }
      } else {
         var3 = 2;
      }

      return var3;
   }
}
