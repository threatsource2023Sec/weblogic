package org.python.bouncycastle.asn1.eac;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERApplicationSpecific;
import org.python.bouncycastle.asn1.DEROctetString;

public class CertificateBody extends ASN1Object {
   ASN1InputStream seq;
   private DERApplicationSpecific certificateProfileIdentifier;
   private DERApplicationSpecific certificationAuthorityReference;
   private PublicKeyDataObject publicKey;
   private DERApplicationSpecific certificateHolderReference;
   private CertificateHolderAuthorization certificateHolderAuthorization;
   private DERApplicationSpecific certificateEffectiveDate;
   private DERApplicationSpecific certificateExpirationDate;
   private int certificateType = 0;
   private static final int CPI = 1;
   private static final int CAR = 2;
   private static final int PK = 4;
   private static final int CHR = 8;
   private static final int CHA = 16;
   private static final int CEfD = 32;
   private static final int CExD = 64;
   public static final int profileType = 127;
   public static final int requestType = 13;

   private void setIso7816CertificateBody(ASN1ApplicationSpecific var1) throws IOException {
      if (var1.getApplicationTag() == 78) {
         byte[] var2 = var1.getContents();
         ASN1InputStream var3 = new ASN1InputStream(var2);

         ASN1Primitive var4;
         while((var4 = var3.readObject()) != null) {
            if (!(var4 instanceof DERApplicationSpecific)) {
               throw new IOException("Not a valid iso7816 content : not a DERApplicationSpecific Object :" + EACTags.encodeTag(var1) + var4.getClass());
            }

            DERApplicationSpecific var5 = (DERApplicationSpecific)var4;
            switch (var5.getApplicationTag()) {
               case 2:
                  this.setCertificationAuthorityReference(var5);
                  break;
               case 32:
                  this.setCertificateHolderReference(var5);
                  break;
               case 36:
                  this.setCertificateExpirationDate(var5);
                  break;
               case 37:
                  this.setCertificateEffectiveDate(var5);
                  break;
               case 41:
                  this.setCertificateProfileIdentifier(var5);
                  break;
               case 73:
                  this.setPublicKey(PublicKeyDataObject.getInstance(var5.getObject(16)));
                  break;
               case 76:
                  this.setCertificateHolderAuthorization(new CertificateHolderAuthorization(var5));
                  break;
               default:
                  this.certificateType = 0;
                  throw new IOException("Not a valid iso7816 DERApplicationSpecific tag " + var5.getApplicationTag());
            }
         }

         var3.close();
      } else {
         throw new IOException("Bad tag : not an iso7816 CERTIFICATE_CONTENT_TEMPLATE");
      }
   }

   public CertificateBody(DERApplicationSpecific var1, CertificationAuthorityReference var2, PublicKeyDataObject var3, CertificateHolderReference var4, CertificateHolderAuthorization var5, PackedDate var6, PackedDate var7) {
      this.setCertificateProfileIdentifier(var1);
      this.setCertificationAuthorityReference(new DERApplicationSpecific(2, var2.getEncoded()));
      this.setPublicKey(var3);
      this.setCertificateHolderReference(new DERApplicationSpecific(32, var4.getEncoded()));
      this.setCertificateHolderAuthorization(var5);

      try {
         this.setCertificateEffectiveDate(new DERApplicationSpecific(false, 37, new DEROctetString(var6.getEncoding())));
         this.setCertificateExpirationDate(new DERApplicationSpecific(false, 36, new DEROctetString(var7.getEncoding())));
      } catch (IOException var9) {
         throw new IllegalArgumentException("unable to encode dates: " + var9.getMessage());
      }
   }

   private CertificateBody(ASN1ApplicationSpecific var1) throws IOException {
      this.setIso7816CertificateBody(var1);
   }

   private ASN1Primitive profileToASN1Object() throws IOException {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.certificateProfileIdentifier);
      var1.add(this.certificationAuthorityReference);
      var1.add(new DERApplicationSpecific(false, 73, this.publicKey));
      var1.add(this.certificateHolderReference);
      var1.add(this.certificateHolderAuthorization);
      var1.add(this.certificateEffectiveDate);
      var1.add(this.certificateExpirationDate);
      return new DERApplicationSpecific(78, var1);
   }

   private void setCertificateProfileIdentifier(DERApplicationSpecific var1) throws IllegalArgumentException {
      if (var1.getApplicationTag() == 41) {
         this.certificateProfileIdentifier = var1;
         this.certificateType |= 1;
      } else {
         throw new IllegalArgumentException("Not an Iso7816Tags.INTERCHANGE_PROFILE tag :" + EACTags.encodeTag(var1));
      }
   }

   private void setCertificateHolderReference(DERApplicationSpecific var1) throws IllegalArgumentException {
      if (var1.getApplicationTag() == 32) {
         this.certificateHolderReference = var1;
         this.certificateType |= 8;
      } else {
         throw new IllegalArgumentException("Not an Iso7816Tags.CARDHOLDER_NAME tag");
      }
   }

   private void setCertificationAuthorityReference(DERApplicationSpecific var1) throws IllegalArgumentException {
      if (var1.getApplicationTag() == 2) {
         this.certificationAuthorityReference = var1;
         this.certificateType |= 2;
      } else {
         throw new IllegalArgumentException("Not an Iso7816Tags.ISSUER_IDENTIFICATION_NUMBER tag");
      }
   }

   private void setPublicKey(PublicKeyDataObject var1) {
      this.publicKey = PublicKeyDataObject.getInstance(var1);
      this.certificateType |= 4;
   }

   private ASN1Primitive requestToASN1Object() throws IOException {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.certificateProfileIdentifier);
      var1.add(new DERApplicationSpecific(false, 73, this.publicKey));
      var1.add(this.certificateHolderReference);
      return new DERApplicationSpecific(78, var1);
   }

   public ASN1Primitive toASN1Primitive() {
      try {
         if (this.certificateType == 127) {
            return this.profileToASN1Object();
         } else {
            return this.certificateType == 13 ? this.requestToASN1Object() : null;
         }
      } catch (IOException var2) {
         return null;
      }
   }

   public int getCertificateType() {
      return this.certificateType;
   }

   public static CertificateBody getInstance(Object var0) throws IOException {
      if (var0 instanceof CertificateBody) {
         return (CertificateBody)var0;
      } else {
         return var0 != null ? new CertificateBody(ASN1ApplicationSpecific.getInstance(var0)) : null;
      }
   }

   public PackedDate getCertificateEffectiveDate() {
      return (this.certificateType & 32) == 32 ? new PackedDate(this.certificateEffectiveDate.getContents()) : null;
   }

   private void setCertificateEffectiveDate(DERApplicationSpecific var1) throws IllegalArgumentException {
      if (var1.getApplicationTag() == 37) {
         this.certificateEffectiveDate = var1;
         this.certificateType |= 32;
      } else {
         throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EFFECTIVE_DATE tag :" + EACTags.encodeTag(var1));
      }
   }

   public PackedDate getCertificateExpirationDate() throws IOException {
      if ((this.certificateType & 64) == 64) {
         return new PackedDate(this.certificateExpirationDate.getContents());
      } else {
         throw new IOException("certificate Expiration Date not set");
      }
   }

   private void setCertificateExpirationDate(DERApplicationSpecific var1) throws IllegalArgumentException {
      if (var1.getApplicationTag() == 36) {
         this.certificateExpirationDate = var1;
         this.certificateType |= 64;
      } else {
         throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EXPIRATION_DATE tag");
      }
   }

   public CertificateHolderAuthorization getCertificateHolderAuthorization() throws IOException {
      if ((this.certificateType & 16) == 16) {
         return this.certificateHolderAuthorization;
      } else {
         throw new IOException("Certificate Holder Authorisation not set");
      }
   }

   private void setCertificateHolderAuthorization(CertificateHolderAuthorization var1) {
      this.certificateHolderAuthorization = var1;
      this.certificateType |= 16;
   }

   public CertificateHolderReference getCertificateHolderReference() {
      return new CertificateHolderReference(this.certificateHolderReference.getContents());
   }

   public DERApplicationSpecific getCertificateProfileIdentifier() {
      return this.certificateProfileIdentifier;
   }

   public CertificationAuthorityReference getCertificationAuthorityReference() throws IOException {
      if ((this.certificateType & 2) == 2) {
         return new CertificationAuthorityReference(this.certificationAuthorityReference.getContents());
      } else {
         throw new IOException("Certification authority reference not set");
      }
   }

   public PublicKeyDataObject getPublicKey() {
      return this.publicKey;
   }
}
