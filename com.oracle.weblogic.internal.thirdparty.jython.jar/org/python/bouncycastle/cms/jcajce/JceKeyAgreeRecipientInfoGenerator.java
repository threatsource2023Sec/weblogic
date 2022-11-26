package org.python.bouncycastle.cms.jcajce;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.cms.KeyAgreeRecipientIdentifier;
import org.python.bouncycastle.asn1.cms.OriginatorPublicKey;
import org.python.bouncycastle.asn1.cms.RecipientEncryptedKey;
import org.python.bouncycastle.asn1.cms.RecipientKeyIdentifier;
import org.python.bouncycastle.asn1.cms.ecc.MQVuserKeyingMaterial;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.KeyAgreeRecipientInfoGenerator;
import org.python.bouncycastle.jcajce.spec.MQVParameterSpec;
import org.python.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.python.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.SecretKeySizeProvider;
import org.python.bouncycastle.util.Arrays;

public class JceKeyAgreeRecipientInfoGenerator extends KeyAgreeRecipientInfoGenerator {
   private SecretKeySizeProvider keySizeProvider = new DefaultSecretKeySizeProvider();
   private List recipientIDs = new ArrayList();
   private List recipientKeys = new ArrayList();
   private PublicKey senderPublicKey;
   private PrivateKey senderPrivateKey;
   private EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
   private SecureRandom random;
   private KeyPair ephemeralKP;
   private byte[] userKeyingMaterial;
   private static KeyMaterialGenerator ecc_cms_Generator = new RFC5753KeyMaterialGenerator();

   public JceKeyAgreeRecipientInfoGenerator(ASN1ObjectIdentifier var1, PrivateKey var2, PublicKey var3, ASN1ObjectIdentifier var4) {
      super(var1, SubjectPublicKeyInfo.getInstance(var3.getEncoded()), var4);
      this.senderPublicKey = var3;
      this.senderPrivateKey = var2;
   }

   public JceKeyAgreeRecipientInfoGenerator setUserKeyingMaterial(byte[] var1) {
      this.userKeyingMaterial = Arrays.clone(var1);
      return this;
   }

   public JceKeyAgreeRecipientInfoGenerator setProvider(Provider var1) {
      this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(var1));
      return this;
   }

   public JceKeyAgreeRecipientInfoGenerator setProvider(String var1) {
      this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(var1));
      return this;
   }

   public JceKeyAgreeRecipientInfoGenerator setSecureRandom(SecureRandom var1) {
      this.random = var1;
      return this;
   }

   public JceKeyAgreeRecipientInfoGenerator addRecipient(X509Certificate var1) throws CertificateEncodingException {
      this.recipientIDs.add(new KeyAgreeRecipientIdentifier(CMSUtils.getIssuerAndSerialNumber(var1)));
      this.recipientKeys.add(var1.getPublicKey());
      return this;
   }

   public JceKeyAgreeRecipientInfoGenerator addRecipient(byte[] var1, PublicKey var2) throws CertificateEncodingException {
      this.recipientIDs.add(new KeyAgreeRecipientIdentifier(new RecipientKeyIdentifier(var1)));
      this.recipientKeys.add(var2);
      return this;
   }

   public ASN1Sequence generateRecipientEncryptedKeys(AlgorithmIdentifier var1, AlgorithmIdentifier var2, GenericKey var3) throws CMSException {
      if (this.recipientIDs.isEmpty()) {
         throw new CMSException("No recipients associated with generator - use addRecipient()");
      } else {
         this.init(var1.getAlgorithm());
         PrivateKey var4 = this.senderPrivateKey;
         ASN1ObjectIdentifier var5 = var1.getAlgorithm();
         ASN1EncodableVector var6 = new ASN1EncodableVector();

         for(int var7 = 0; var7 != this.recipientIDs.size(); ++var7) {
            PublicKey var8 = (PublicKey)this.recipientKeys.get(var7);
            KeyAgreeRecipientIdentifier var9 = (KeyAgreeRecipientIdentifier)this.recipientIDs.get(var7);

            try {
               Object var10;
               if (CMSUtils.isMQV(var5)) {
                  var10 = new MQVParameterSpec(this.ephemeralKP, var8, this.userKeyingMaterial);
               } else if (CMSUtils.isEC(var5)) {
                  byte[] var11 = ecc_cms_Generator.generateKDFMaterial(var2, this.keySizeProvider.getKeySize(var2.getAlgorithm()), this.userKeyingMaterial);
                  var10 = new UserKeyingMaterialSpec(var11);
               } else {
                  if (!CMSUtils.isRFC2631(var5)) {
                     throw new CMSException("Unknown key agreement algorithm: " + var5);
                  }

                  if (this.userKeyingMaterial != null) {
                     var10 = new UserKeyingMaterialSpec(this.userKeyingMaterial);
                  } else {
                     if (var5.equals(PKCSObjectIdentifiers.id_alg_SSDH)) {
                        throw new CMSException("User keying material must be set for static keys.");
                     }

                     var10 = null;
                  }
               }

               KeyAgreement var17 = this.helper.createKeyAgreement(var5);
               var17.init(var4, (AlgorithmParameterSpec)var10, this.random);
               var17.doPhase(var8, true);
               SecretKey var12 = var17.generateSecret(var2.getAlgorithm().getId());
               Cipher var13 = this.helper.createCipher(var2.getAlgorithm());
               var13.init(3, var12, this.random);
               byte[] var14 = var13.wrap(this.helper.getJceKey(var3));
               DEROctetString var15 = new DEROctetString(var14);
               var6.add(new RecipientEncryptedKey(var9, var15));
            } catch (GeneralSecurityException var16) {
               throw new CMSException("Cannot perform agreement step: " + var16.getMessage(), var16);
            }
         }

         return new DERSequence(var6);
      }
   }

   protected byte[] getUserKeyingMaterial(AlgorithmIdentifier var1) throws CMSException {
      this.init(var1.getAlgorithm());
      if (this.ephemeralKP != null) {
         OriginatorPublicKey var2 = this.createOriginatorPublicKey(SubjectPublicKeyInfo.getInstance(this.ephemeralKP.getPublic().getEncoded()));

         try {
            return this.userKeyingMaterial != null ? (new MQVuserKeyingMaterial(var2, new DEROctetString(this.userKeyingMaterial))).getEncoded() : (new MQVuserKeyingMaterial(var2, (ASN1OctetString)null)).getEncoded();
         } catch (IOException var4) {
            throw new CMSException("unable to encode user keying material: " + var4.getMessage(), var4);
         }
      } else {
         return this.userKeyingMaterial;
      }
   }

   private void init(ASN1ObjectIdentifier var1) throws CMSException {
      if (this.random == null) {
         this.random = new SecureRandom();
      }

      if (CMSUtils.isMQV(var1) && this.ephemeralKP == null) {
         try {
            SubjectPublicKeyInfo var2 = SubjectPublicKeyInfo.getInstance(this.senderPublicKey.getEncoded());
            AlgorithmParameters var3 = this.helper.createAlgorithmParameters(var1);
            var3.init(var2.getAlgorithm().getParameters().toASN1Primitive().getEncoded());
            KeyPairGenerator var4 = this.helper.createKeyPairGenerator(var1);
            var4.initialize(var3.getParameterSpec(AlgorithmParameterSpec.class), this.random);
            this.ephemeralKP = var4.generateKeyPair();
         } catch (Exception var5) {
            throw new CMSException("cannot determine MQV ephemeral key pair parameters from public key: " + var5, var5);
         }
      }

   }
}
