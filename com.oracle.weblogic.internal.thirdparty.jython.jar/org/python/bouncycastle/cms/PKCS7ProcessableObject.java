package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Sequence;

public class PKCS7ProcessableObject implements CMSTypedData {
   private final ASN1ObjectIdentifier type;
   private final ASN1Encodable structure;

   public PKCS7ProcessableObject(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.type = var1;
      this.structure = var2;
   }

   public ASN1ObjectIdentifier getContentType() {
      return this.type;
   }

   public void write(OutputStream var1) throws IOException, CMSException {
      if (this.structure instanceof ASN1Sequence) {
         ASN1Sequence var2 = ASN1Sequence.getInstance(this.structure);
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            ASN1Encodable var4 = (ASN1Encodable)var3.next();
            var1.write(var4.toASN1Primitive().getEncoded("DER"));
         }
      } else {
         byte[] var5 = this.structure.toASN1Primitive().getEncoded("DER");

         int var6;
         for(var6 = 1; (var5[var6] & 255) > 127; ++var6) {
         }

         ++var6;
         var1.write(var5, var6, var5.length - var6);
      }

   }

   public Object getContent() {
      return this.structure;
   }
}
