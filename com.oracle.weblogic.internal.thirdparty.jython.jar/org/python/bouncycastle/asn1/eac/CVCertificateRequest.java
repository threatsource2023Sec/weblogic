package org.python.bouncycastle.asn1.eac;

import java.io.IOException;
import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ParsingException;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERApplicationSpecific;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.util.Arrays;

public class CVCertificateRequest extends ASN1Object {
   private final ASN1ApplicationSpecific original;
   private CertificateBody certificateBody;
   private byte[] innerSignature = null;
   private byte[] outerSignature = null;
   private static final int bodyValid = 1;
   private static final int signValid = 2;

   private CVCertificateRequest(ASN1ApplicationSpecific var1) throws IOException {
      this.original = var1;
      if (var1.isConstructed() && var1.getApplicationTag() == 7) {
         ASN1Sequence var2 = ASN1Sequence.getInstance(var1.getObject(16));
         this.initCertBody(ASN1ApplicationSpecific.getInstance(var2.getObjectAt(0)));
         this.outerSignature = ASN1ApplicationSpecific.getInstance(var2.getObjectAt(var2.size() - 1)).getContents();
      } else {
         this.initCertBody(var1);
      }

   }

   private void initCertBody(ASN1ApplicationSpecific var1) throws IOException {
      if (var1.getApplicationTag() == 33) {
         int var2 = 0;
         ASN1Sequence var3 = ASN1Sequence.getInstance(var1.getObject(16));
         Enumeration var4 = var3.getObjects();

         while(var4.hasMoreElements()) {
            ASN1ApplicationSpecific var5 = ASN1ApplicationSpecific.getInstance(var4.nextElement());
            switch (var5.getApplicationTag()) {
               case 55:
                  this.innerSignature = var5.getContents();
                  var2 |= 2;
                  break;
               case 78:
                  this.certificateBody = CertificateBody.getInstance(var5);
                  var2 |= 1;
                  break;
               default:
                  throw new IOException("Invalid tag, not an CV Certificate Request element:" + var5.getApplicationTag());
            }
         }

         if ((var2 & 3) == 0) {
            throw new IOException("Invalid CARDHOLDER_CERTIFICATE in request:" + var1.getApplicationTag());
         }
      } else {
         throw new IOException("not a CARDHOLDER_CERTIFICATE in request:" + var1.getApplicationTag());
      }
   }

   public static CVCertificateRequest getInstance(Object var0) {
      if (var0 instanceof CVCertificateRequest) {
         return (CVCertificateRequest)var0;
      } else if (var0 != null) {
         try {
            return new CVCertificateRequest(ASN1ApplicationSpecific.getInstance(var0));
         } catch (IOException var2) {
            throw new ASN1ParsingException("unable to parse data: " + var2.getMessage(), var2);
         }
      } else {
         return null;
      }
   }

   public CertificateBody getCertificateBody() {
      return this.certificateBody;
   }

   public PublicKeyDataObject getPublicKey() {
      return this.certificateBody.getPublicKey();
   }

   public byte[] getInnerSignature() {
      return Arrays.clone(this.innerSignature);
   }

   public byte[] getOuterSignature() {
      return Arrays.clone(this.outerSignature);
   }

   public boolean hasOuterSignature() {
      return this.outerSignature != null;
   }

   public ASN1Primitive toASN1Primitive() {
      if (this.original != null) {
         return this.original;
      } else {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         var1.add(this.certificateBody);

         try {
            var1.add(new DERApplicationSpecific(false, 55, new DEROctetString(this.innerSignature)));
         } catch (IOException var3) {
            throw new IllegalStateException("unable to convert signature!");
         }

         return new DERApplicationSpecific(33, var1);
      }
   }
}
