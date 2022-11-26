package org.python.bouncycastle.cms.jcajce;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.cms.ecc.ECCCMSSharedInfo;
import org.python.bouncycastle.asn1.cms.ecc.MQVuserKeyingMaterial;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.KeyAgreeRecipient;
import org.python.bouncycastle.jcajce.spec.MQVParameterSpec;
import org.python.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.python.bouncycastle.operator.DefaultSecretKeySizeProvider;
import org.python.bouncycastle.operator.SecretKeySizeProvider;
import org.python.bouncycastle.util.Pack;

public abstract class JceKeyAgreeRecipient implements KeyAgreeRecipient {
   private static final Set possibleOldMessages = new HashSet();
   private PrivateKey recipientKey;
   protected EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
   protected EnvelopedDataHelper contentHelper;
   private SecretKeySizeProvider keySizeProvider;
   private static KeyMaterialGenerator old_ecc_cms_Generator;
   private static KeyMaterialGenerator ecc_cms_Generator;

   public JceKeyAgreeRecipient(PrivateKey var1) {
      this.contentHelper = this.helper;
      this.keySizeProvider = new DefaultSecretKeySizeProvider();
      this.recipientKey = var1;
   }

   public JceKeyAgreeRecipient setProvider(Provider var1) {
      this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(var1));
      this.contentHelper = this.helper;
      return this;
   }

   public JceKeyAgreeRecipient setProvider(String var1) {
      this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(var1));
      this.contentHelper = this.helper;
      return this;
   }

   public JceKeyAgreeRecipient setContentProvider(Provider var1) {
      this.contentHelper = CMSUtils.createContentHelper(var1);
      return this;
   }

   public JceKeyAgreeRecipient setContentProvider(String var1) {
      this.contentHelper = CMSUtils.createContentHelper(var1);
      return this;
   }

   private SecretKey calculateAgreedWrapKey(AlgorithmIdentifier var1, AlgorithmIdentifier var2, PublicKey var3, ASN1OctetString var4, PrivateKey var5, KeyMaterialGenerator var6) throws CMSException, GeneralSecurityException, IOException {
      if (CMSUtils.isMQV(var1.getAlgorithm())) {
         MQVuserKeyingMaterial var14 = MQVuserKeyingMaterial.getInstance(var4.getOctets());
         SubjectPublicKeyInfo var15 = new SubjectPublicKeyInfo(this.getPrivateKeyAlgorithmIdentifier(), var14.getEphemeralPublicKey().getPublicKey().getBytes());
         X509EncodedKeySpec var16 = new X509EncodedKeySpec(var15.getEncoded());
         KeyFactory var10 = this.helper.createKeyFactory(var1.getAlgorithm());
         PublicKey var11 = var10.generatePublic(var16);
         KeyAgreement var12 = this.helper.createKeyAgreement(var1.getAlgorithm());
         byte[] var13 = var14.getAddedukm() != null ? var14.getAddedukm().getOctets() : null;
         if (var6 == old_ecc_cms_Generator) {
            var13 = old_ecc_cms_Generator.generateKDFMaterial(var2, this.keySizeProvider.getKeySize(var2), var13);
         }

         var12.init(var5, new MQVParameterSpec(var5, var11, var13));
         var12.doPhase(var3, true);
         return var12.generateSecret(var2.getAlgorithm().getId());
      } else {
         KeyAgreement var7 = this.helper.createKeyAgreement(var1.getAlgorithm());
         UserKeyingMaterialSpec var8 = null;
         if (CMSUtils.isEC(var1.getAlgorithm())) {
            byte[] var9;
            if (var4 != null) {
               var9 = var6.generateKDFMaterial(var2, this.keySizeProvider.getKeySize(var2), var4.getOctets());
               var8 = new UserKeyingMaterialSpec(var9);
            } else {
               var9 = var6.generateKDFMaterial(var2, this.keySizeProvider.getKeySize(var2), (byte[])null);
               var8 = new UserKeyingMaterialSpec(var9);
            }
         } else {
            if (!CMSUtils.isRFC2631(var1.getAlgorithm())) {
               throw new CMSException("Unknown key agreement algorithm: " + var1.getAlgorithm());
            }

            if (var4 != null) {
               var8 = new UserKeyingMaterialSpec(var4.getOctets());
            }
         }

         var7.init(var5, var8);
         var7.doPhase(var3, true);
         return var7.generateSecret(var2.getAlgorithm().getId());
      }
   }

   private Key unwrapSessionKey(ASN1ObjectIdentifier var1, SecretKey var2, ASN1ObjectIdentifier var3, byte[] var4) throws CMSException, InvalidKeyException, NoSuchAlgorithmException {
      Cipher var5 = this.helper.createCipher(var1);
      var5.init(4, var2);
      return var5.unwrap(var4, this.helper.getBaseCipherName(var3), 3);
   }

   protected Key extractSecretKey(AlgorithmIdentifier var1, AlgorithmIdentifier var2, SubjectPublicKeyInfo var3, ASN1OctetString var4, byte[] var5) throws CMSException {
      try {
         AlgorithmIdentifier var6 = AlgorithmIdentifier.getInstance(var1.getParameters());
         X509EncodedKeySpec var7 = new X509EncodedKeySpec(var3.getEncoded());
         KeyFactory var8 = this.helper.createKeyFactory(var3.getAlgorithm().getAlgorithm());
         PublicKey var9 = var8.generatePublic(var7);

         try {
            SecretKey var10 = this.calculateAgreedWrapKey(var1, var6, var9, var4, this.recipientKey, ecc_cms_Generator);
            return this.unwrapSessionKey(var6.getAlgorithm(), var10, var2.getAlgorithm(), var5);
         } catch (InvalidKeyException var12) {
            if (possibleOldMessages.contains(var1.getAlgorithm())) {
               SecretKey var11 = this.calculateAgreedWrapKey(var1, var6, var9, var4, this.recipientKey, old_ecc_cms_Generator);
               return this.unwrapSessionKey(var6.getAlgorithm(), var11, var2.getAlgorithm(), var5);
            } else {
               throw var12;
            }
         }
      } catch (NoSuchAlgorithmException var13) {
         throw new CMSException("can't find algorithm.", var13);
      } catch (InvalidKeyException var14) {
         throw new CMSException("key invalid in message.", var14);
      } catch (InvalidKeySpecException var15) {
         throw new CMSException("originator key spec invalid.", var15);
      } catch (NoSuchPaddingException var16) {
         throw new CMSException("required padding not supported.", var16);
      } catch (Exception var17) {
         throw new CMSException("originator key invalid.", var17);
      }
   }

   public AlgorithmIdentifier getPrivateKeyAlgorithmIdentifier() {
      return PrivateKeyInfo.getInstance(this.recipientKey.getEncoded()).getPrivateKeyAlgorithm();
   }

   static {
      possibleOldMessages.add(X9ObjectIdentifiers.dhSinglePass_stdDH_sha1kdf_scheme);
      possibleOldMessages.add(X9ObjectIdentifiers.mqvSinglePass_sha1kdf_scheme);
      old_ecc_cms_Generator = new KeyMaterialGenerator() {
         public byte[] generateKDFMaterial(AlgorithmIdentifier var1, int var2, byte[] var3) {
            ECCCMSSharedInfo var4 = new ECCCMSSharedInfo(new AlgorithmIdentifier(var1.getAlgorithm(), DERNull.INSTANCE), var3, Pack.intToBigEndian(var2));

            try {
               return var4.getEncoded("DER");
            } catch (IOException var6) {
               throw new IllegalStateException("Unable to create KDF material: " + var6);
            }
         }
      };
      ecc_cms_Generator = new RFC5753KeyMaterialGenerator();
   }
}
