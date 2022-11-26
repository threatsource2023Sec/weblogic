package org.python.bouncycastle.asn1.pkcs;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.BERSequence;
import org.python.bouncycastle.asn1.DLSequence;

public class AuthenticatedSafe extends ASN1Object {
   private ContentInfo[] info;
   private boolean isBer = true;

   private AuthenticatedSafe(ASN1Sequence var1) {
      this.info = new ContentInfo[var1.size()];

      for(int var2 = 0; var2 != this.info.length; ++var2) {
         this.info[var2] = ContentInfo.getInstance(var1.getObjectAt(var2));
      }

      this.isBer = var1 instanceof BERSequence;
   }

   public static AuthenticatedSafe getInstance(Object var0) {
      if (var0 instanceof AuthenticatedSafe) {
         return (AuthenticatedSafe)var0;
      } else {
         return var0 != null ? new AuthenticatedSafe(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public AuthenticatedSafe(ContentInfo[] var1) {
      this.info = var1;
   }

   public ContentInfo[] getContentInfo() {
      return this.info;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();

      for(int var2 = 0; var2 != this.info.length; ++var2) {
         var1.add(this.info[var2]);
      }

      return (ASN1Primitive)(this.isBer ? new BERSequence(var1) : new DLSequence(var1));
   }
}
