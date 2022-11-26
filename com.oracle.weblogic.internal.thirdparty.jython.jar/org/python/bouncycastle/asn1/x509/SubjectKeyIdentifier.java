package org.python.bouncycastle.asn1.x509;

import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.util.Arrays;

public class SubjectKeyIdentifier extends ASN1Object {
   private byte[] keyidentifier;

   public static SubjectKeyIdentifier getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1OctetString.getInstance(var0, var1));
   }

   public static SubjectKeyIdentifier getInstance(Object var0) {
      if (var0 instanceof SubjectKeyIdentifier) {
         return (SubjectKeyIdentifier)var0;
      } else {
         return var0 != null ? new SubjectKeyIdentifier(ASN1OctetString.getInstance(var0)) : null;
      }
   }

   public static SubjectKeyIdentifier fromExtensions(Extensions var0) {
      return getInstance(var0.getExtensionParsedValue(Extension.subjectKeyIdentifier));
   }

   public SubjectKeyIdentifier(byte[] var1) {
      this.keyidentifier = Arrays.clone(var1);
   }

   protected SubjectKeyIdentifier(ASN1OctetString var1) {
      this(var1.getOctets());
   }

   public byte[] getKeyIdentifier() {
      return Arrays.clone(this.keyidentifier);
   }

   public ASN1Primitive toASN1Primitive() {
      return new DEROctetString(this.getKeyIdentifier());
   }
}
