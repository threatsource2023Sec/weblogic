package org.python.bouncycastle.asn1.cmc;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.cms.ContentInfo;

public class TaggedContentInfo extends ASN1Object {
   private final BodyPartID bodyPartID;
   private final ContentInfo contentInfo;

   public TaggedContentInfo(BodyPartID var1, ContentInfo var2) {
      this.bodyPartID = var1;
      this.contentInfo = var2;
   }

   private TaggedContentInfo(ASN1Sequence var1) {
      if (var1.size() != 2) {
         throw new IllegalArgumentException("incorrect sequence size");
      } else {
         this.bodyPartID = BodyPartID.getInstance(var1.getObjectAt(0));
         this.contentInfo = ContentInfo.getInstance(var1.getObjectAt(1));
      }
   }

   public static TaggedContentInfo getInstance(Object var0) {
      if (var0 instanceof TaggedContentInfo) {
         return (TaggedContentInfo)var0;
      } else {
         return var0 != null ? new TaggedContentInfo(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public static TaggedContentInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.bodyPartID);
      var1.add(this.contentInfo);
      return new DERSequence(var1);
   }

   public BodyPartID getBodyPartID() {
      return this.bodyPartID;
   }

   public ContentInfo getContentInfo() {
      return this.contentInfo;
   }
}
