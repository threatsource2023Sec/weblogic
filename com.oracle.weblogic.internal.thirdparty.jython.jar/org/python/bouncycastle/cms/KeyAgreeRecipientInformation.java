package org.python.bouncycastle.cms;

import java.io.IOException;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.python.bouncycastle.asn1.cms.KeyAgreeRecipientIdentifier;
import org.python.bouncycastle.asn1.cms.KeyAgreeRecipientInfo;
import org.python.bouncycastle.asn1.cms.OriginatorIdentifierOrKey;
import org.python.bouncycastle.asn1.cms.OriginatorPublicKey;
import org.python.bouncycastle.asn1.cms.RecipientEncryptedKey;
import org.python.bouncycastle.asn1.cms.RecipientKeyIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class KeyAgreeRecipientInformation extends RecipientInformation {
   private KeyAgreeRecipientInfo info;
   private ASN1OctetString encryptedKey;

   static void readRecipientInfo(List var0, KeyAgreeRecipientInfo var1, AlgorithmIdentifier var2, CMSSecureReadable var3, AuthAttributesProvider var4) {
      ASN1Sequence var5 = var1.getRecipientEncryptedKeys();

      for(int var6 = 0; var6 < var5.size(); ++var6) {
         RecipientEncryptedKey var7 = RecipientEncryptedKey.getInstance(var5.getObjectAt(var6));
         KeyAgreeRecipientIdentifier var8 = var7.getIdentifier();
         IssuerAndSerialNumber var9 = var8.getIssuerAndSerialNumber();
         KeyAgreeRecipientId var10;
         if (var9 != null) {
            var10 = new KeyAgreeRecipientId(var9.getName(), var9.getSerialNumber().getValue());
         } else {
            RecipientKeyIdentifier var11 = var8.getRKeyID();
            var10 = new KeyAgreeRecipientId(var11.getSubjectKeyIdentifier().getOctets());
         }

         var0.add(new KeyAgreeRecipientInformation(var1, var10, var7.getEncryptedKey(), var2, var3, var4));
      }

   }

   KeyAgreeRecipientInformation(KeyAgreeRecipientInfo var1, RecipientId var2, ASN1OctetString var3, AlgorithmIdentifier var4, CMSSecureReadable var5, AuthAttributesProvider var6) {
      super(var1.getKeyEncryptionAlgorithm(), var4, var5, var6);
      this.info = var1;
      this.rid = var2;
      this.encryptedKey = var3;
   }

   private SubjectPublicKeyInfo getSenderPublicKeyInfo(AlgorithmIdentifier var1, OriginatorIdentifierOrKey var2) throws CMSException, IOException {
      OriginatorPublicKey var3 = var2.getOriginatorKey();
      if (var3 != null) {
         return this.getPublicKeyInfoFromOriginatorPublicKey(var1, var3);
      } else {
         IssuerAndSerialNumber var4 = var2.getIssuerAndSerialNumber();
         OriginatorId var5;
         if (var4 != null) {
            var5 = new OriginatorId(var4.getName(), var4.getSerialNumber().getValue());
         } else {
            SubjectKeyIdentifier var6 = var2.getSubjectKeyIdentifier();
            var5 = new OriginatorId(var6.getKeyIdentifier());
         }

         return this.getPublicKeyInfoFromOriginatorId(var5);
      }
   }

   private SubjectPublicKeyInfo getPublicKeyInfoFromOriginatorPublicKey(AlgorithmIdentifier var1, OriginatorPublicKey var2) {
      SubjectPublicKeyInfo var3 = new SubjectPublicKeyInfo(var1, var2.getPublicKey().getBytes());
      return var3;
   }

   private SubjectPublicKeyInfo getPublicKeyInfoFromOriginatorId(OriginatorId var1) throws CMSException {
      throw new CMSException("No support for 'originator' as IssuerAndSerialNumber or SubjectKeyIdentifier");
   }

   protected RecipientOperator getRecipientOperator(Recipient var1) throws CMSException, IOException {
      KeyAgreeRecipient var2 = (KeyAgreeRecipient)var1;
      AlgorithmIdentifier var3 = var2.getPrivateKeyAlgorithmIdentifier();
      return ((KeyAgreeRecipient)var1).getRecipientOperator(this.keyEncAlg, this.messageAlgorithm, this.getSenderPublicKeyInfo(var3, this.info.getOriginator()), this.info.getUserKeyingMaterial(), this.encryptedKey.getOctets());
   }
}
