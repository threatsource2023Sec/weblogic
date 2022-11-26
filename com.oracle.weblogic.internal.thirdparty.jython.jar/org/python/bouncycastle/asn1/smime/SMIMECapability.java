package org.python.bouncycastle.asn1.smime;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class SMIMECapability extends ASN1Object {
   public static final ASN1ObjectIdentifier preferSignedData;
   public static final ASN1ObjectIdentifier canNotDecryptAny;
   public static final ASN1ObjectIdentifier sMIMECapabilitiesVersions;
   public static final ASN1ObjectIdentifier dES_CBC;
   public static final ASN1ObjectIdentifier dES_EDE3_CBC;
   public static final ASN1ObjectIdentifier rC2_CBC;
   public static final ASN1ObjectIdentifier aES128_CBC;
   public static final ASN1ObjectIdentifier aES192_CBC;
   public static final ASN1ObjectIdentifier aES256_CBC;
   private ASN1ObjectIdentifier capabilityID;
   private ASN1Encodable parameters;

   public SMIMECapability(ASN1Sequence var1) {
      this.capabilityID = (ASN1ObjectIdentifier)var1.getObjectAt(0);
      if (var1.size() > 1) {
         this.parameters = (ASN1Primitive)var1.getObjectAt(1);
      }

   }

   public SMIMECapability(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.capabilityID = var1;
      this.parameters = var2;
   }

   public static SMIMECapability getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof SMIMECapability)) {
         if (var0 instanceof ASN1Sequence) {
            return new SMIMECapability((ASN1Sequence)var0);
         } else {
            throw new IllegalArgumentException("Invalid SMIMECapability");
         }
      } else {
         return (SMIMECapability)var0;
      }
   }

   public ASN1ObjectIdentifier getCapabilityID() {
      return this.capabilityID;
   }

   public ASN1Encodable getParameters() {
      return this.parameters;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.capabilityID);
      if (this.parameters != null) {
         var1.add(this.parameters);
      }

      return new DERSequence(var1);
   }

   static {
      preferSignedData = PKCSObjectIdentifiers.preferSignedData;
      canNotDecryptAny = PKCSObjectIdentifiers.canNotDecryptAny;
      sMIMECapabilitiesVersions = PKCSObjectIdentifiers.sMIMECapabilitiesVersions;
      dES_CBC = new ASN1ObjectIdentifier("1.3.14.3.2.7");
      dES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC;
      rC2_CBC = PKCSObjectIdentifiers.RC2_CBC;
      aES128_CBC = NISTObjectIdentifiers.id_aes128_CBC;
      aES192_CBC = NISTObjectIdentifiers.id_aes192_CBC;
      aES256_CBC = NISTObjectIdentifiers.id_aes256_CBC;
   }
}
