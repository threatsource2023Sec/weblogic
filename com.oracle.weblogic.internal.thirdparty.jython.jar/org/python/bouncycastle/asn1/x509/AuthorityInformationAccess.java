package org.python.bouncycastle.asn1.x509;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class AuthorityInformationAccess extends ASN1Object {
   private AccessDescription[] descriptions;

   public static AuthorityInformationAccess getInstance(Object var0) {
      if (var0 instanceof AuthorityInformationAccess) {
         return (AuthorityInformationAccess)var0;
      } else {
         return var0 != null ? new AuthorityInformationAccess(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public static AuthorityInformationAccess fromExtensions(Extensions var0) {
      return getInstance(var0.getExtensionParsedValue(Extension.authorityInfoAccess));
   }

   private AuthorityInformationAccess(ASN1Sequence var1) {
      if (var1.size() < 1) {
         throw new IllegalArgumentException("sequence may not be empty");
      } else {
         this.descriptions = new AccessDescription[var1.size()];

         for(int var2 = 0; var2 != var1.size(); ++var2) {
            this.descriptions[var2] = AccessDescription.getInstance(var1.getObjectAt(var2));
         }

      }
   }

   public AuthorityInformationAccess(AccessDescription var1) {
      this(new AccessDescription[]{var1});
   }

   public AuthorityInformationAccess(AccessDescription[] var1) {
      this.descriptions = new AccessDescription[var1.length];
      System.arraycopy(var1, 0, this.descriptions, 0, var1.length);
   }

   public AuthorityInformationAccess(ASN1ObjectIdentifier var1, GeneralName var2) {
      this(new AccessDescription(var1, var2));
   }

   public AccessDescription[] getAccessDescriptions() {
      return this.descriptions;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();

      for(int var2 = 0; var2 != this.descriptions.length; ++var2) {
         var1.add(this.descriptions[var2]);
      }

      return new DERSequence(var1);
   }

   public String toString() {
      return "AuthorityInformationAccess: Oid(" + this.descriptions[0].getAccessMethod().getId() + ")";
   }
}
