package org.python.bouncycastle.jcajce.provider.keystore.bcfks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.bc.EncryptedObjectStoreData;
import org.python.bouncycastle.asn1.bc.EncryptedPrivateKeyData;
import org.python.bouncycastle.asn1.bc.EncryptedSecretKeyData;
import org.python.bouncycastle.asn1.bc.ObjectData;
import org.python.bouncycastle.asn1.bc.ObjectDataSequence;
import org.python.bouncycastle.asn1.bc.ObjectStore;
import org.python.bouncycastle.asn1.bc.ObjectStoreData;
import org.python.bouncycastle.asn1.bc.ObjectStoreIntegrityCheck;
import org.python.bouncycastle.asn1.bc.PbkdMacIntegrityCheck;
import org.python.bouncycastle.asn1.bc.SecretKeyData;
import org.python.bouncycastle.asn1.cms.CCMParameters;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.python.bouncycastle.asn1.pkcs.EncryptionScheme;
import org.python.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import org.python.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.python.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.python.bouncycastle.crypto.PBEParametersGenerator;
import org.python.bouncycastle.crypto.digests.SHA512Digest;
import org.python.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Strings;

class BcFKSKeyStoreSpi extends KeyStoreSpi {
   private static final Map oidMap = new HashMap();
   private static final Map publicAlgMap = new HashMap();
   private static final BigInteger CERTIFICATE;
   private static final BigInteger PRIVATE_KEY;
   private static final BigInteger SECRET_KEY;
   private static final BigInteger PROTECTED_PRIVATE_KEY;
   private static final BigInteger PROTECTED_SECRET_KEY;
   private final BouncyCastleProvider provider;
   private final Map entries = new HashMap();
   private final Map privateKeyCache = new HashMap();
   private AlgorithmIdentifier hmacAlgorithm;
   private KeyDerivationFunc hmacPkbdAlgorithm;
   private Date creationDate;
   private Date lastModifiedDate;

   private static String getPublicKeyAlg(ASN1ObjectIdentifier var0) {
      String var1 = (String)publicAlgMap.get(var0);
      return var1 != null ? var1 : var0.getId();
   }

   BcFKSKeyStoreSpi(BouncyCastleProvider var1) {
      this.provider = var1;
   }

   public Key engineGetKey(String var1, char[] var2) throws NoSuchAlgorithmException, UnrecoverableKeyException {
      ObjectData var3 = (ObjectData)this.entries.get(var1);
      if (var3 != null) {
         if (!var3.getType().equals(PRIVATE_KEY) && !var3.getType().equals(PROTECTED_PRIVATE_KEY)) {
            if (!var3.getType().equals(SECRET_KEY) && !var3.getType().equals(PROTECTED_SECRET_KEY)) {
               throw new UnrecoverableKeyException("BCFKS KeyStore unable to recover secret key (" + var1 + "): type not recognized");
            } else {
               EncryptedSecretKeyData var12 = EncryptedSecretKeyData.getInstance(var3.getData());

               try {
                  SecretKeyData var13 = SecretKeyData.getInstance(this.decryptData("SECRET_KEY_ENCRYPTION", var12.getKeyEncryptionAlgorithm(), var2, var12.getEncryptedKeyData()));
                  SecretKeyFactory var14;
                  if (this.provider != null) {
                     var14 = SecretKeyFactory.getInstance(var13.getKeyAlgorithm().getId(), this.provider);
                  } else {
                     var14 = SecretKeyFactory.getInstance(var13.getKeyAlgorithm().getId());
                  }

                  return var14.generateSecret(new SecretKeySpec(var13.getKeyBytes(), var13.getKeyAlgorithm().getId()));
               } catch (Exception var10) {
                  throw new UnrecoverableKeyException("BCFKS KeyStore unable to recover secret key (" + var1 + "): " + var10.getMessage());
               }
            }
         } else {
            PrivateKey var4 = (PrivateKey)this.privateKeyCache.get(var1);
            if (var4 != null) {
               return var4;
            } else {
               EncryptedPrivateKeyData var5 = EncryptedPrivateKeyData.getInstance(var3.getData());
               EncryptedPrivateKeyInfo var6 = EncryptedPrivateKeyInfo.getInstance(var5.getEncryptedPrivateKeyInfo());

               try {
                  PrivateKeyInfo var7 = PrivateKeyInfo.getInstance(this.decryptData("PRIVATE_KEY_ENCRYPTION", var6.getEncryptionAlgorithm(), var2, var6.getEncryptedData()));
                  KeyFactory var8;
                  if (this.provider != null) {
                     var8 = KeyFactory.getInstance(var7.getPrivateKeyAlgorithm().getAlgorithm().getId(), this.provider);
                  } else {
                     var8 = KeyFactory.getInstance(getPublicKeyAlg(var7.getPrivateKeyAlgorithm().getAlgorithm()));
                  }

                  PrivateKey var9 = var8.generatePrivate(new PKCS8EncodedKeySpec(var7.getEncoded()));
                  this.privateKeyCache.put(var1, var9);
                  return var9;
               } catch (Exception var11) {
                  throw new UnrecoverableKeyException("BCFKS KeyStore unable to recover private key (" + var1 + "): " + var11.getMessage());
               }
            }
         }
      } else {
         return null;
      }
   }

   public Certificate[] engineGetCertificateChain(String var1) {
      ObjectData var2 = (ObjectData)this.entries.get(var1);
      if (var2 == null || !var2.getType().equals(PRIVATE_KEY) && !var2.getType().equals(PROTECTED_PRIVATE_KEY)) {
         return null;
      } else {
         EncryptedPrivateKeyData var3 = EncryptedPrivateKeyData.getInstance(var2.getData());
         org.python.bouncycastle.asn1.x509.Certificate[] var4 = var3.getCertificateChain();
         X509Certificate[] var5 = new X509Certificate[var4.length];

         for(int var6 = 0; var6 != var5.length; ++var6) {
            var5[var6] = this.decodeCertificate(var4[var6]);
         }

         return var5;
      }
   }

   public Certificate engineGetCertificate(String var1) {
      ObjectData var2 = (ObjectData)this.entries.get(var1);
      if (var2 != null) {
         if (var2.getType().equals(PRIVATE_KEY) || var2.getType().equals(PROTECTED_PRIVATE_KEY)) {
            EncryptedPrivateKeyData var3 = EncryptedPrivateKeyData.getInstance(var2.getData());
            org.python.bouncycastle.asn1.x509.Certificate[] var4 = var3.getCertificateChain();
            return this.decodeCertificate(var4[0]);
         }

         if (var2.getType().equals(CERTIFICATE)) {
            return this.decodeCertificate(var2.getData());
         }
      }

      return null;
   }

   private Certificate decodeCertificate(Object var1) {
      CertificateFactory var2;
      if (this.provider != null) {
         try {
            var2 = CertificateFactory.getInstance("X.509", this.provider);
            return var2.generateCertificate(new ByteArrayInputStream(org.python.bouncycastle.asn1.x509.Certificate.getInstance(var1).getEncoded()));
         } catch (Exception var3) {
            return null;
         }
      } else {
         try {
            var2 = CertificateFactory.getInstance("X.509");
            return var2.generateCertificate(new ByteArrayInputStream(org.python.bouncycastle.asn1.x509.Certificate.getInstance(var1).getEncoded()));
         } catch (Exception var4) {
            return null;
         }
      }
   }

   public Date engineGetCreationDate(String var1) {
      ObjectData var2 = (ObjectData)this.entries.get(var1);
      if (var2 != null) {
         try {
            return var2.getLastModifiedDate().getDate();
         } catch (ParseException var4) {
            return new Date();
         }
      } else {
         return null;
      }
   }

   public void engineSetKeyEntry(String var1, Key var2, char[] var3, Certificate[] var4) throws KeyStoreException {
      Date var5 = new Date();
      Date var6 = var5;
      ObjectData var7 = (ObjectData)this.entries.get(var1);
      if (var7 != null) {
         var5 = this.extractCreationDate(var7, var5);
      }

      this.privateKeyCache.remove(var1);
      byte[] var8;
      KeyDerivationFunc var9;
      byte[] var10;
      Cipher var11;
      if (var2 instanceof PrivateKey) {
         if (var4 == null) {
            throw new KeyStoreException("BCFKS KeyStore requires a certificate chain for private key storage.");
         }

         try {
            var8 = var2.getEncoded();
            var9 = this.generatePkbdAlgorithmIdentifier(32);
            var10 = this.generateKey(var9, "PRIVATE_KEY_ENCRYPTION", var3 != null ? var3 : new char[0]);
            if (this.provider == null) {
               var11 = Cipher.getInstance("AES/CCM/NoPadding");
            } else {
               var11 = Cipher.getInstance("AES/CCM/NoPadding", this.provider);
            }

            var11.init(1, new SecretKeySpec(var10, "AES"));
            byte[] var12 = var11.doFinal(var8);
            AlgorithmParameters var13 = var11.getParameters();
            PBES2Parameters var14 = new PBES2Parameters(var9, new EncryptionScheme(NISTObjectIdentifiers.id_aes256_CCM, CCMParameters.getInstance(var13.getEncoded())));
            EncryptedPrivateKeyInfo var15 = new EncryptedPrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2, var14), var12);
            EncryptedPrivateKeyData var16 = this.createPrivateKeySequence(var15, var4);
            this.entries.put(var1, new ObjectData(PRIVATE_KEY, var1, var5, var6, var16.getEncoded(), (String)null));
         } catch (Exception var17) {
            throw new ExtKeyStoreException("BCFKS KeyStore exception storing private key: " + var17.toString(), var17);
         }
      } else {
         if (!(var2 instanceof SecretKey)) {
            throw new KeyStoreException("BCFKS KeyStore unable to recognize key.");
         }

         if (var4 != null) {
            throw new KeyStoreException("BCFKS KeyStore cannot store certificate chain with secret key.");
         }

         try {
            var8 = var2.getEncoded();
            var9 = this.generatePkbdAlgorithmIdentifier(32);
            var10 = this.generateKey(var9, "SECRET_KEY_ENCRYPTION", var3 != null ? var3 : new char[0]);
            if (this.provider == null) {
               var11 = Cipher.getInstance("AES/CCM/NoPadding");
            } else {
               var11 = Cipher.getInstance("AES/CCM/NoPadding", this.provider);
            }

            var11.init(1, new SecretKeySpec(var10, "AES"));
            String var19 = Strings.toUpperCase(var2.getAlgorithm());
            byte[] var20;
            if (var19.indexOf("AES") > -1) {
               var20 = var11.doFinal((new SecretKeyData(NISTObjectIdentifiers.aes, var8)).getEncoded());
            } else {
               ASN1ObjectIdentifier var21 = (ASN1ObjectIdentifier)oidMap.get(var19);
               if (var21 == null) {
                  throw new KeyStoreException("BCFKS KeyStore cannot recognize secret key (" + var19 + ") for storage.");
               }

               var20 = var11.doFinal((new SecretKeyData(var21, var8)).getEncoded());
            }

            AlgorithmParameters var22 = var11.getParameters();
            PBES2Parameters var23 = new PBES2Parameters(var9, new EncryptionScheme(NISTObjectIdentifiers.id_aes256_CCM, CCMParameters.getInstance(var22.getEncoded())));
            EncryptedSecretKeyData var24 = new EncryptedSecretKeyData(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2, var23), var20);
            this.entries.put(var1, new ObjectData(SECRET_KEY, var1, var5, var6, var24.getEncoded(), (String)null));
         } catch (Exception var18) {
            throw new ExtKeyStoreException("BCFKS KeyStore exception storing private key: " + var18.toString(), var18);
         }
      }

      this.lastModifiedDate = var6;
   }

   private SecureRandom getDefaultSecureRandom() {
      return new SecureRandom();
   }

   private EncryptedPrivateKeyData createPrivateKeySequence(EncryptedPrivateKeyInfo var1, Certificate[] var2) throws CertificateEncodingException {
      org.python.bouncycastle.asn1.x509.Certificate[] var3 = new org.python.bouncycastle.asn1.x509.Certificate[var2.length];

      for(int var4 = 0; var4 != var2.length; ++var4) {
         var3[var4] = org.python.bouncycastle.asn1.x509.Certificate.getInstance(var2[var4].getEncoded());
      }

      return new EncryptedPrivateKeyData(var1, var3);
   }

   public void engineSetKeyEntry(String var1, byte[] var2, Certificate[] var3) throws KeyStoreException {
      Date var4 = new Date();
      Date var5 = var4;
      ObjectData var6 = (ObjectData)this.entries.get(var1);
      if (var6 != null) {
         var4 = this.extractCreationDate(var6, var4);
      }

      if (var3 != null) {
         EncryptedPrivateKeyInfo var7;
         try {
            var7 = EncryptedPrivateKeyInfo.getInstance(var2);
         } catch (Exception var11) {
            throw new ExtKeyStoreException("BCFKS KeyStore private key encoding must be an EncryptedPrivateKeyInfo.", var11);
         }

         try {
            this.privateKeyCache.remove(var1);
            this.entries.put(var1, new ObjectData(PROTECTED_PRIVATE_KEY, var1, var4, var5, this.createPrivateKeySequence(var7, var3).getEncoded(), (String)null));
         } catch (Exception var10) {
            throw new ExtKeyStoreException("BCFKS KeyStore exception storing protected private key: " + var10.toString(), var10);
         }
      } else {
         try {
            this.entries.put(var1, new ObjectData(PROTECTED_SECRET_KEY, var1, var4, var5, var2, (String)null));
         } catch (Exception var9) {
            throw new ExtKeyStoreException("BCFKS KeyStore exception storing protected private key: " + var9.toString(), var9);
         }
      }

      this.lastModifiedDate = var5;
   }

   public void engineSetCertificateEntry(String var1, Certificate var2) throws KeyStoreException {
      ObjectData var3 = (ObjectData)this.entries.get(var1);
      Date var4 = new Date();
      Date var5 = var4;
      if (var3 != null) {
         if (!var3.getType().equals(CERTIFICATE)) {
            throw new KeyStoreException("BCFKS KeyStore already has a key entry with alias " + var1);
         }

         var4 = this.extractCreationDate(var3, var4);
      }

      try {
         this.entries.put(var1, new ObjectData(CERTIFICATE, var1, var4, var5, var2.getEncoded(), (String)null));
      } catch (CertificateEncodingException var7) {
         throw new ExtKeyStoreException("BCFKS KeyStore unable to handle certificate: " + var7.getMessage(), var7);
      }

      this.lastModifiedDate = var5;
   }

   private Date extractCreationDate(ObjectData var1, Date var2) {
      try {
         var2 = var1.getCreationDate().getDate();
      } catch (ParseException var4) {
      }

      return var2;
   }

   public void engineDeleteEntry(String var1) throws KeyStoreException {
      ObjectData var2 = (ObjectData)this.entries.get(var1);
      if (var2 != null) {
         this.privateKeyCache.remove(var1);
         this.entries.remove(var1);
         this.lastModifiedDate = new Date();
      }
   }

   public Enumeration engineAliases() {
      final Iterator var1 = (new HashSet(this.entries.keySet())).iterator();
      return new Enumeration() {
         public boolean hasMoreElements() {
            return var1.hasNext();
         }

         public Object nextElement() {
            return var1.next();
         }
      };
   }

   public boolean engineContainsAlias(String var1) {
      if (var1 == null) {
         throw new NullPointerException("alias value is null");
      } else {
         return this.entries.containsKey(var1);
      }
   }

   public int engineSize() {
      return this.entries.size();
   }

   public boolean engineIsKeyEntry(String var1) {
      ObjectData var2 = (ObjectData)this.entries.get(var1);
      if (var2 == null) {
         return false;
      } else {
         BigInteger var3 = var2.getType();
         return var3.equals(PRIVATE_KEY) || var3.equals(SECRET_KEY) || var3.equals(PROTECTED_PRIVATE_KEY) || var3.equals(PROTECTED_SECRET_KEY);
      }
   }

   public boolean engineIsCertificateEntry(String var1) {
      ObjectData var2 = (ObjectData)this.entries.get(var1);
      return var2 != null ? var2.getType().equals(CERTIFICATE) : false;
   }

   public String engineGetCertificateAlias(Certificate var1) {
      if (var1 == null) {
         return null;
      } else {
         byte[] var2;
         try {
            var2 = var1.getEncoded();
         } catch (CertificateEncodingException var8) {
            return null;
         }

         Iterator var3 = this.entries.keySet().iterator();

         String var4;
         ObjectData var5;
         label43:
         do {
            while(var3.hasNext()) {
               var4 = (String)var3.next();
               var5 = (ObjectData)this.entries.get(var4);
               if (var5.getType().equals(CERTIFICATE)) {
                  continue label43;
               }

               if (var5.getType().equals(PRIVATE_KEY) || var5.getType().equals(PROTECTED_PRIVATE_KEY)) {
                  try {
                     EncryptedPrivateKeyData var6 = EncryptedPrivateKeyData.getInstance(var5.getData());
                     if (Arrays.areEqual(var6.getCertificateChain()[0].toASN1Primitive().getEncoded(), var2)) {
                        return var4;
                     }
                  } catch (IOException var7) {
                  }
               }
            }

            return null;
         } while(!Arrays.areEqual(var5.getData(), var2));

         return var4;
      }
   }

   private byte[] generateKey(KeyDerivationFunc var1, String var2, char[] var3) throws IOException {
      byte[] var4 = PBEParametersGenerator.PKCS12PasswordToBytes(var3);
      byte[] var5 = PBEParametersGenerator.PKCS12PasswordToBytes(var2.toCharArray());
      PKCS5S2ParametersGenerator var6 = new PKCS5S2ParametersGenerator(new SHA512Digest());
      if (var1.getAlgorithm().equals(PKCSObjectIdentifiers.id_PBKDF2)) {
         PBKDF2Params var7 = PBKDF2Params.getInstance(var1.getParameters());
         if (var7.getPrf().getAlgorithm().equals(PKCSObjectIdentifiers.id_hmacWithSHA512)) {
            var6.init(Arrays.concatenate(var4, var5), var7.getSalt(), var7.getIterationCount().intValue());
            int var8 = var7.getKeyLength().intValue();
            return ((KeyParameter)var6.generateDerivedParameters(var8 * 8)).getKey();
         } else {
            throw new IOException("BCFKS KeyStore: unrecognized MAC PBKD PRF.");
         }
      } else {
         throw new IOException("BCFKS KeyStore: unrecognized MAC PBKD.");
      }
   }

   private void verifyMac(byte[] var1, PbkdMacIntegrityCheck var2, char[] var3) throws NoSuchAlgorithmException, IOException {
      byte[] var4 = this.calculateMac(var1, var2.getMacAlgorithm(), var2.getPbkdAlgorithm(), var3);
      if (!Arrays.constantTimeAreEqual(var4, var2.getMac())) {
         throw new IOException("BCFKS KeyStore corrupted: MAC calculation failed.");
      }
   }

   private byte[] calculateMac(byte[] var1, AlgorithmIdentifier var2, KeyDerivationFunc var3, char[] var4) throws NoSuchAlgorithmException, IOException {
      String var5 = var2.getAlgorithm().getId();
      Mac var6;
      if (this.provider != null) {
         var6 = Mac.getInstance(var5, this.provider);
      } else {
         var6 = Mac.getInstance(var5);
      }

      try {
         var6.init(new SecretKeySpec(this.generateKey(var3, "INTEGRITY_CHECK", var4 != null ? var4 : new char[0]), var5));
      } catch (InvalidKeyException var8) {
         throw new IOException("Cannot set up MAC calculation: " + var8.getMessage());
      }

      return var6.doFinal(var1);
   }

   public void engineStore(OutputStream var1, char[] var2) throws IOException, NoSuchAlgorithmException, CertificateException {
      ObjectData[] var3 = (ObjectData[])((ObjectData[])this.entries.values().toArray(new ObjectData[this.entries.size()]));
      KeyDerivationFunc var4 = this.generatePkbdAlgorithmIdentifier(32);
      byte[] var5 = this.generateKey(var4, "STORE_ENCRYPTION", var2 != null ? var2 : new char[0]);
      ObjectStoreData var6 = new ObjectStoreData(this.hmacAlgorithm, this.creationDate, this.lastModifiedDate, new ObjectDataSequence(var3), (String)null);

      byte[] var8;
      EncryptedObjectStoreData var11;
      try {
         Cipher var7;
         if (this.provider == null) {
            var7 = Cipher.getInstance("AES/CCM/NoPadding");
         } else {
            var7 = Cipher.getInstance("AES/CCM/NoPadding", this.provider);
         }

         var7.init(1, new SecretKeySpec(var5, "AES"));
         var8 = var7.doFinal(var6.getEncoded());
         AlgorithmParameters var9 = var7.getParameters();
         PBES2Parameters var10 = new PBES2Parameters(var4, new EncryptionScheme(NISTObjectIdentifiers.id_aes256_CCM, CCMParameters.getInstance(var9.getEncoded())));
         var11 = new EncryptedObjectStoreData(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2, var10), var8);
      } catch (NoSuchPaddingException var12) {
         throw new NoSuchAlgorithmException(var12.toString());
      } catch (BadPaddingException var13) {
         throw new IOException(var13.toString());
      } catch (IllegalBlockSizeException var14) {
         throw new IOException(var14.toString());
      } catch (InvalidKeyException var15) {
         throw new IOException(var15.toString());
      }

      PBKDF2Params var17 = PBKDF2Params.getInstance(this.hmacPkbdAlgorithm.getParameters());
      var8 = new byte[var17.getSalt().length];
      this.getDefaultSecureRandom().nextBytes(var8);
      this.hmacPkbdAlgorithm = new KeyDerivationFunc(this.hmacPkbdAlgorithm.getAlgorithm(), new PBKDF2Params(var8, var17.getIterationCount().intValue(), var17.getKeyLength().intValue(), var17.getPrf()));
      byte[] var18 = this.calculateMac(var11.getEncoded(), this.hmacAlgorithm, this.hmacPkbdAlgorithm, var2);
      ObjectStore var16 = new ObjectStore(var11, new ObjectStoreIntegrityCheck(new PbkdMacIntegrityCheck(this.hmacAlgorithm, this.hmacPkbdAlgorithm, var18)));
      var1.write(var16.getEncoded());
      var1.flush();
   }

   public void engineLoad(InputStream var1, char[] var2) throws IOException, NoSuchAlgorithmException, CertificateException {
      this.entries.clear();
      this.privateKeyCache.clear();
      this.lastModifiedDate = this.creationDate = null;
      this.hmacAlgorithm = null;
      if (var1 == null) {
         this.lastModifiedDate = this.creationDate = new Date();
         this.hmacAlgorithm = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA512, DERNull.INSTANCE);
         this.hmacPkbdAlgorithm = this.generatePkbdAlgorithmIdentifier(64);
      } else {
         ASN1InputStream var3 = new ASN1InputStream(var1);
         ObjectStore var4 = ObjectStore.getInstance(var3.readObject());
         ObjectStoreIntegrityCheck var5 = var4.getIntegrityCheck();
         if (var5.getType() != 0) {
            throw new IOException("BCFKS KeyStore unable to recognize integrity check.");
         } else {
            PbkdMacIntegrityCheck var6 = PbkdMacIntegrityCheck.getInstance(var5.getIntegrityCheck());
            this.hmacAlgorithm = var6.getMacAlgorithm();
            this.hmacPkbdAlgorithm = var6.getPbkdAlgorithm();
            this.verifyMac(var4.getStoreData().toASN1Primitive().getEncoded(), var6, var2);
            ASN1Encodable var11 = var4.getStoreData();
            ObjectStoreData var9;
            if (var11 instanceof EncryptedObjectStoreData) {
               EncryptedObjectStoreData var7 = (EncryptedObjectStoreData)var11;
               AlgorithmIdentifier var8 = var7.getEncryptionAlgorithm();
               var9 = ObjectStoreData.getInstance(this.decryptData("STORE_ENCRYPTION", var8, var2, var7.getEncryptedContent().getOctets()));
            } else {
               var9 = ObjectStoreData.getInstance(var11);
            }

            try {
               this.creationDate = var9.getCreationDate().getDate();
               this.lastModifiedDate = var9.getLastModifiedDate().getDate();
            } catch (ParseException var10) {
               throw new IOException("BCFKS KeyStore unable to parse store data information.");
            }

            if (!var9.getIntegrityAlgorithm().equals(this.hmacAlgorithm)) {
               throw new IOException("BCFKS KeyStore storeData integrity algorithm does not match store integrity algorithm.");
            } else {
               Iterator var12 = var9.getObjectDataSequence().iterator();

               while(var12.hasNext()) {
                  ObjectData var13 = ObjectData.getInstance(var12.next());
                  this.entries.put(var13.getIdentifier(), var13);
               }

            }
         }
      }
   }

   private byte[] decryptData(String var1, AlgorithmIdentifier var2, char[] var3, byte[] var4) throws IOException {
      if (!var2.getAlgorithm().equals(PKCSObjectIdentifiers.id_PBES2)) {
         throw new IOException("BCFKS KeyStore cannot recognize protection algorithm.");
      } else {
         PBES2Parameters var5 = PBES2Parameters.getInstance(var2.getParameters());
         EncryptionScheme var6 = var5.getEncryptionScheme();
         if (!var6.getAlgorithm().equals(NISTObjectIdentifiers.id_aes256_CCM)) {
            throw new IOException("BCFKS KeyStore cannot recognize protection encryption algorithm.");
         } else {
            try {
               CCMParameters var7 = CCMParameters.getInstance(var6.getParameters());
               Cipher var8;
               AlgorithmParameters var9;
               if (this.provider == null) {
                  var8 = Cipher.getInstance("AES/CCM/NoPadding");
                  var9 = AlgorithmParameters.getInstance("CCM");
               } else {
                  var8 = Cipher.getInstance("AES/CCM/NoPadding", this.provider);
                  var9 = AlgorithmParameters.getInstance("CCM", this.provider);
               }

               var9.init(var7.getEncoded());
               byte[] var10 = this.generateKey(var5.getKeyDerivationFunc(), var1, var3 != null ? var3 : new char[0]);
               var8.init(2, new SecretKeySpec(var10, "AES"), var9);
               byte[] var11 = var8.doFinal(var4);
               return var11;
            } catch (Exception var12) {
               throw new IOException(var12.toString());
            }
         }
      }
   }

   private KeyDerivationFunc generatePkbdAlgorithmIdentifier(int var1) {
      byte[] var2 = new byte[64];
      this.getDefaultSecureRandom().nextBytes(var2);
      return new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(var2, 1024, var1, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA512, DERNull.INSTANCE)));
   }

   static {
      oidMap.put("DESEDE", OIWObjectIdentifiers.desEDE);
      oidMap.put("TRIPLEDES", OIWObjectIdentifiers.desEDE);
      oidMap.put("TDEA", OIWObjectIdentifiers.desEDE);
      oidMap.put("HMACSHA1", PKCSObjectIdentifiers.id_hmacWithSHA1);
      oidMap.put("HMACSHA224", PKCSObjectIdentifiers.id_hmacWithSHA224);
      oidMap.put("HMACSHA256", PKCSObjectIdentifiers.id_hmacWithSHA256);
      oidMap.put("HMACSHA384", PKCSObjectIdentifiers.id_hmacWithSHA384);
      oidMap.put("HMACSHA512", PKCSObjectIdentifiers.id_hmacWithSHA512);
      publicAlgMap.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
      publicAlgMap.put(X9ObjectIdentifiers.id_ecPublicKey, "EC");
      publicAlgMap.put(OIWObjectIdentifiers.elGamalAlgorithm, "DH");
      publicAlgMap.put(PKCSObjectIdentifiers.dhKeyAgreement, "DH");
      publicAlgMap.put(X9ObjectIdentifiers.id_dsa, "DSA");
      CERTIFICATE = BigInteger.valueOf(0L);
      PRIVATE_KEY = BigInteger.valueOf(1L);
      SECRET_KEY = BigInteger.valueOf(2L);
      PROTECTED_PRIVATE_KEY = BigInteger.valueOf(3L);
      PROTECTED_SECRET_KEY = BigInteger.valueOf(4L);
   }

   public static class Def extends BcFKSKeyStoreSpi {
      public Def() {
         super((BouncyCastleProvider)null);
      }
   }

   private static class ExtKeyStoreException extends KeyStoreException {
      private final Throwable cause;

      ExtKeyStoreException(String var1, Throwable var2) {
         super(var1);
         this.cause = var2;
      }

      public Throwable getCause() {
         return this.cause;
      }
   }

   public static class Std extends BcFKSKeyStoreSpi {
      public Std() {
         super(new BouncyCastleProvider());
      }
   }
}
