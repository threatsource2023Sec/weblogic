package org.python.bouncycastle.asn1.cms;

import org.python.bouncycastle.asn1.ASN1Boolean;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERUTF8String;

public class MetaData extends ASN1Object {
   private ASN1Boolean hashProtected;
   private DERUTF8String fileName;
   private DERIA5String mediaType;
   private Attributes otherMetaData;

   public MetaData(ASN1Boolean var1, DERUTF8String var2, DERIA5String var3, Attributes var4) {
      this.hashProtected = var1;
      this.fileName = var2;
      this.mediaType = var3;
      this.otherMetaData = var4;
   }

   private MetaData(ASN1Sequence var1) {
      this.hashProtected = ASN1Boolean.getInstance(var1.getObjectAt(0));
      int var2 = 1;
      if (var2 < var1.size() && var1.getObjectAt(var2) instanceof DERUTF8String) {
         this.fileName = DERUTF8String.getInstance(var1.getObjectAt(var2++));
      }

      if (var2 < var1.size() && var1.getObjectAt(var2) instanceof DERIA5String) {
         this.mediaType = DERIA5String.getInstance(var1.getObjectAt(var2++));
      }

      if (var2 < var1.size()) {
         this.otherMetaData = Attributes.getInstance(var1.getObjectAt(var2++));
      }

   }

   public static MetaData getInstance(Object var0) {
      if (var0 instanceof MetaData) {
         return (MetaData)var0;
      } else {
         return var0 != null ? new MetaData(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.hashProtected);
      if (this.fileName != null) {
         var1.add(this.fileName);
      }

      if (this.mediaType != null) {
         var1.add(this.mediaType);
      }

      if (this.otherMetaData != null) {
         var1.add(this.otherMetaData);
      }

      return new DERSequence(var1);
   }

   public boolean isHashProtected() {
      return this.hashProtected.isTrue();
   }

   public DERUTF8String getFileName() {
      return this.fileName;
   }

   public DERIA5String getMediaType() {
      return this.mediaType;
   }

   public Attributes getOtherMetaData() {
      return this.otherMetaData;
   }
}
