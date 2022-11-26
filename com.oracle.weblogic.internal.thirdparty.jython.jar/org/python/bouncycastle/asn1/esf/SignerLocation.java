package org.python.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.x500.DirectoryString;

public class SignerLocation extends ASN1Object {
   private DERUTF8String countryName;
   private DERUTF8String localityName;
   private ASN1Sequence postalAddress;

   private SignerLocation(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var2.nextElement();
         switch (var3.getTagNo()) {
            case 0:
               DirectoryString var4 = DirectoryString.getInstance(var3, true);
               this.countryName = new DERUTF8String(var4.getString());
               break;
            case 1:
               DirectoryString var5 = DirectoryString.getInstance(var3, true);
               this.localityName = new DERUTF8String(var5.getString());
               break;
            case 2:
               if (var3.isExplicit()) {
                  this.postalAddress = ASN1Sequence.getInstance(var3, true);
               } else {
                  this.postalAddress = ASN1Sequence.getInstance(var3, false);
               }

               if (this.postalAddress != null && this.postalAddress.size() > 6) {
                  throw new IllegalArgumentException("postal address must contain less than 6 strings");
               }
               break;
            default:
               throw new IllegalArgumentException("illegal tag");
         }
      }

   }

   public SignerLocation(DERUTF8String var1, DERUTF8String var2, ASN1Sequence var3) {
      if (var3 != null && var3.size() > 6) {
         throw new IllegalArgumentException("postal address must contain less than 6 strings");
      } else {
         if (var1 != null) {
            this.countryName = DERUTF8String.getInstance(var1.toASN1Primitive());
         }

         if (var2 != null) {
            this.localityName = DERUTF8String.getInstance(var2.toASN1Primitive());
         }

         if (var3 != null) {
            this.postalAddress = ASN1Sequence.getInstance(var3.toASN1Primitive());
         }

      }
   }

   public static SignerLocation getInstance(Object var0) {
      return var0 != null && !(var0 instanceof SignerLocation) ? new SignerLocation(ASN1Sequence.getInstance(var0)) : (SignerLocation)var0;
   }

   public DERUTF8String getCountryName() {
      return this.countryName;
   }

   public DERUTF8String getLocalityName() {
      return this.localityName;
   }

   public ASN1Sequence getPostalAddress() {
      return this.postalAddress;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if (this.countryName != null) {
         var1.add(new DERTaggedObject(true, 0, this.countryName));
      }

      if (this.localityName != null) {
         var1.add(new DERTaggedObject(true, 1, this.localityName));
      }

      if (this.postalAddress != null) {
         var1.add(new DERTaggedObject(true, 2, this.postalAddress));
      }

      return new DERSequence(var1);
   }
}
