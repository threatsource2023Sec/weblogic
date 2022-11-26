package org.python.bouncycastle.jcajce.provider.keystore.pkcs12;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.BEROctetString;
import org.python.bouncycastle.asn1.BEROutputStream;
import org.python.bouncycastle.asn1.DERBMPString;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DEROutputStream;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.cryptopro.GOST28147Parameters;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.AuthenticatedSafe;
import org.python.bouncycastle.asn1.pkcs.CertBag;
import org.python.bouncycastle.asn1.pkcs.ContentInfo;
import org.python.bouncycastle.asn1.pkcs.EncryptedData;
import org.python.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.python.bouncycastle.asn1.pkcs.MacData;
import org.python.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.python.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.python.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.Pfx;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.pkcs.SafeBag;
import org.python.bouncycastle.asn1.util.ASN1Dump;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.python.bouncycastle.asn1.x509.DigestInfo;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.util.DigestFactory;
import org.python.bouncycastle.jcajce.PKCS12Key;
import org.python.bouncycastle.jcajce.PKCS12StoreParameter;
import org.python.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.python.bouncycastle.jcajce.spec.PBKDF2KeySpec;
import org.python.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jce.interfaces.BCKeyStore;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.provider.JDKPKCS12StoreParameter;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Integers;
import org.python.bouncycastle.util.Strings;
import org.python.bouncycastle.util.encoders.Hex;

public class PKCS12KeyStoreSpi extends KeyStoreSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore {
   private final JcaJceHelper helper = new BCJcaJceHelper();
   private static final int SALT_SIZE = 20;
   private static final int MIN_ITERATIONS = 1024;
   private static final DefaultSecretKeyProvider keySizeProvider = new DefaultSecretKeyProvider();
   private IgnoresCaseHashtable keys = new IgnoresCaseHashtable();
   private Hashtable localIds = new Hashtable();
   private IgnoresCaseHashtable certs = new IgnoresCaseHashtable();
   private Hashtable chainCerts = new Hashtable();
   private Hashtable keyCerts = new Hashtable();
   static final int NULL = 0;
   static final int CERTIFICATE = 1;
   static final int KEY = 2;
   static final int SECRET = 3;
   static final int SEALED = 4;
   static final int KEY_PRIVATE = 0;
   static final int KEY_PUBLIC = 1;
   static final int KEY_SECRET = 2;
   protected SecureRandom random = new SecureRandom();
   private CertificateFactory certFact;
   private ASN1ObjectIdentifier keyAlgorithm;
   private ASN1ObjectIdentifier certAlgorithm;

   public PKCS12KeyStoreSpi(Provider var1, ASN1ObjectIdentifier var2, ASN1ObjectIdentifier var3) {
      this.keyAlgorithm = var2;
      this.certAlgorithm = var3;

      try {
         if (var1 != null) {
            this.certFact = CertificateFactory.getInstance("X.509", var1);
         } else {
            this.certFact = CertificateFactory.getInstance("X.509");
         }

      } catch (Exception var5) {
         throw new IllegalArgumentException("can't create cert factory - " + var5.toString());
      }
   }

   private SubjectKeyIdentifier createSubjectKeyId(PublicKey var1) {
      try {
         SubjectPublicKeyInfo var2 = SubjectPublicKeyInfo.getInstance(var1.getEncoded());
         return new SubjectKeyIdentifier(getDigest(var2));
      } catch (Exception var3) {
         throw new RuntimeException("error creating key");
      }
   }

   private static byte[] getDigest(SubjectPublicKeyInfo var0) {
      Digest var1 = DigestFactory.createSHA1();
      byte[] var2 = new byte[var1.getDigestSize()];
      byte[] var3 = var0.getPublicKeyData().getBytes();
      var1.update(var3, 0, var3.length);
      var1.doFinal(var2, 0);
      return var2;
   }

   public void setRandom(SecureRandom var1) {
      this.random = var1;
   }

   public Enumeration engineAliases() {
      Hashtable var1 = new Hashtable();
      Enumeration var2 = this.certs.keys();

      while(var2.hasMoreElements()) {
         var1.put(var2.nextElement(), "cert");
      }

      var2 = this.keys.keys();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         if (var1.get(var3) == null) {
            var1.put(var3, "key");
         }
      }

      return var1.keys();
   }

   public boolean engineContainsAlias(String var1) {
      return this.certs.get(var1) != null || this.keys.get(var1) != null;
   }

   public void engineDeleteEntry(String var1) throws KeyStoreException {
      Key var2 = (Key)this.keys.remove(var1);
      Certificate var3 = (Certificate)this.certs.remove(var1);
      if (var3 != null) {
         this.chainCerts.remove(new CertId(var3.getPublicKey()));
      }

      if (var2 != null) {
         String var4 = (String)this.localIds.remove(var1);
         if (var4 != null) {
            var3 = (Certificate)this.keyCerts.remove(var4);
         }

         if (var3 != null) {
            this.chainCerts.remove(new CertId(var3.getPublicKey()));
         }
      }

   }

   public Certificate engineGetCertificate(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("null alias passed to getCertificate.");
      } else {
         Certificate var2 = (Certificate)this.certs.get(var1);
         if (var2 == null) {
            String var3 = (String)this.localIds.get(var1);
            if (var3 != null) {
               var2 = (Certificate)this.keyCerts.get(var3);
            } else {
               var2 = (Certificate)this.keyCerts.get(var1);
            }
         }

         return var2;
      }
   }

   public String engineGetCertificateAlias(Certificate var1) {
      Enumeration var2 = this.certs.elements();
      Enumeration var3 = this.certs.keys();

      Certificate var4;
      String var5;
      do {
         if (!var2.hasMoreElements()) {
            var2 = this.keyCerts.elements();
            var3 = this.keyCerts.keys();

            do {
               if (!var2.hasMoreElements()) {
                  return null;
               }

               var4 = (Certificate)var2.nextElement();
               var5 = (String)var3.nextElement();
            } while(!var4.equals(var1));

            return var5;
         }

         var4 = (Certificate)var2.nextElement();
         var5 = (String)var3.nextElement();
      } while(!var4.equals(var1));

      return var5;
   }

   public Certificate[] engineGetCertificateChain(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("null alias passed to getCertificateChain.");
      } else if (!this.engineIsKeyEntry(var1)) {
         return null;
      } else {
         Object var2 = this.engineGetCertificate(var1);
         if (var2 == null) {
            return null;
         } else {
            Vector var3 = new Vector();

            while(var2 != null) {
               X509Certificate var4 = (X509Certificate)var2;
               Object var5 = null;
               byte[] var6 = var4.getExtensionValue(Extension.authorityKeyIdentifier.getId());
               if (var6 != null) {
                  try {
                     ASN1InputStream var7 = new ASN1InputStream(var6);
                     byte[] var8 = ((ASN1OctetString)var7.readObject()).getOctets();
                     var7 = new ASN1InputStream(var8);
                     AuthorityKeyIdentifier var9 = AuthorityKeyIdentifier.getInstance(var7.readObject());
                     if (var9.getKeyIdentifier() != null) {
                        var5 = (Certificate)this.chainCerts.get(new CertId(var9.getKeyIdentifier()));
                     }
                  } catch (IOException var13) {
                     throw new RuntimeException(var13.toString());
                  }
               }

               if (var5 == null) {
                  Principal var17 = var4.getIssuerDN();
                  Principal var18 = var4.getSubjectDN();
                  if (!var17.equals(var18)) {
                     Enumeration var19 = this.chainCerts.keys();

                     label68:
                     while(true) {
                        X509Certificate var10;
                        Principal var11;
                        do {
                           if (!var19.hasMoreElements()) {
                              break label68;
                           }

                           var10 = (X509Certificate)this.chainCerts.get(var19.nextElement());
                           var11 = var10.getSubjectDN();
                        } while(!var11.equals(var17));

                        try {
                           var4.verify(var10.getPublicKey());
                           var5 = var10;
                           break;
                        } catch (Exception var14) {
                        }
                     }
                  }
               }

               if (var3.contains(var2)) {
                  var2 = null;
               } else {
                  var3.addElement(var2);
                  if (var5 != var2) {
                     var2 = var5;
                  } else {
                     var2 = null;
                  }
               }
            }

            Certificate[] var15 = new Certificate[var3.size()];

            for(int var16 = 0; var16 != var15.length; ++var16) {
               var15[var16] = (Certificate)var3.elementAt(var16);
            }

            return var15;
         }
      }
   }

   public Date engineGetCreationDate(String var1) {
      if (var1 == null) {
         throw new NullPointerException("alias == null");
      } else {
         return this.keys.get(var1) == null && this.certs.get(var1) == null ? null : new Date();
      }
   }

   public Key engineGetKey(String var1, char[] var2) throws NoSuchAlgorithmException, UnrecoverableKeyException {
      if (var1 == null) {
         throw new IllegalArgumentException("null alias passed to getKey.");
      } else {
         return (Key)this.keys.get(var1);
      }
   }

   public boolean engineIsCertificateEntry(String var1) {
      return this.certs.get(var1) != null && this.keys.get(var1) == null;
   }

   public boolean engineIsKeyEntry(String var1) {
      return this.keys.get(var1) != null;
   }

   public void engineSetCertificateEntry(String var1, Certificate var2) throws KeyStoreException {
      if (this.keys.get(var1) != null) {
         throw new KeyStoreException("There is a key entry with the name " + var1 + ".");
      } else {
         this.certs.put(var1, var2);
         this.chainCerts.put(new CertId(var2.getPublicKey()), var2);
      }
   }

   public void engineSetKeyEntry(String var1, byte[] var2, Certificate[] var3) throws KeyStoreException {
      throw new RuntimeException("operation not supported");
   }

   public void engineSetKeyEntry(String var1, Key var2, char[] var3, Certificate[] var4) throws KeyStoreException {
      if (!(var2 instanceof PrivateKey)) {
         throw new KeyStoreException("PKCS12 does not support non-PrivateKeys");
      } else if (var2 instanceof PrivateKey && var4 == null) {
         throw new KeyStoreException("no certificate chain for private key");
      } else {
         if (this.keys.get(var1) != null) {
            this.engineDeleteEntry(var1);
         }

         this.keys.put(var1, var2);
         if (var4 != null) {
            this.certs.put(var1, var4[0]);

            for(int var5 = 0; var5 != var4.length; ++var5) {
               this.chainCerts.put(new CertId(var4[var5].getPublicKey()), var4[var5]);
            }
         }

      }
   }

   public int engineSize() {
      Hashtable var1 = new Hashtable();
      Enumeration var2 = this.certs.keys();

      while(var2.hasMoreElements()) {
         var1.put(var2.nextElement(), "cert");
      }

      var2 = this.keys.keys();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         if (var1.get(var3) == null) {
            var1.put(var3, "key");
         }
      }

      return var1.size();
   }

   protected PrivateKey unwrapKey(AlgorithmIdentifier var1, byte[] var2, char[] var3, boolean var4) throws IOException {
      ASN1ObjectIdentifier var5 = var1.getAlgorithm();

      try {
         if (var5.on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
            PKCS12PBEParams var11 = PKCS12PBEParams.getInstance(var1.getParameters());
            PBEParameterSpec var7 = new PBEParameterSpec(var11.getIV(), var11.getIterations().intValue());
            Cipher var8 = this.helper.createCipher(var5.getId());
            PKCS12Key var9 = new PKCS12Key(var3, var4);
            var8.init(4, var9, var7);
            return (PrivateKey)var8.unwrap(var2, "", 2);
         }

         if (var5.equals(PKCSObjectIdentifiers.id_PBES2)) {
            Cipher var6 = this.createCipher(4, var3, var1);
            return (PrivateKey)var6.unwrap(var2, "", 2);
         }
      } catch (Exception var10) {
         throw new IOException("exception unwrapping private key - " + var10.toString());
      }

      throw new IOException("exception unwrapping private key - cannot recognise: " + var5);
   }

   protected byte[] wrapKey(String var1, Key var2, PKCS12PBEParams var3, char[] var4) throws IOException {
      PBEKeySpec var5 = new PBEKeySpec(var4);

      try {
         SecretKeyFactory var6 = this.helper.createSecretKeyFactory(var1);
         PBEParameterSpec var7 = new PBEParameterSpec(var3.getIV(), var3.getIterations().intValue());
         Cipher var8 = this.helper.createCipher(var1);
         var8.init(3, var6.generateSecret(var5), var7);
         byte[] var9 = var8.wrap(var2);
         return var9;
      } catch (Exception var10) {
         throw new IOException("exception encrypting data - " + var10.toString());
      }
   }

   protected byte[] cryptData(boolean var1, AlgorithmIdentifier var2, char[] var3, boolean var4, byte[] var5) throws IOException {
      ASN1ObjectIdentifier var6 = var2.getAlgorithm();
      int var7 = var1 ? 1 : 2;
      if (var6.on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
         PKCS12PBEParams var15 = PKCS12PBEParams.getInstance(var2.getParameters());
         new PBEKeySpec(var3);

         try {
            PBEParameterSpec var10 = new PBEParameterSpec(var15.getIV(), var15.getIterations().intValue());
            PKCS12Key var11 = new PKCS12Key(var3, var4);
            Cipher var12 = this.helper.createCipher(var6.getId());
            var12.init(var7, var11, var10);
            return var12.doFinal(var5);
         } catch (Exception var13) {
            throw new IOException("exception decrypting data - " + var13.toString());
         }
      } else if (var6.equals(PKCSObjectIdentifiers.id_PBES2)) {
         try {
            Cipher var8 = this.createCipher(var7, var3, var2);
            return var8.doFinal(var5);
         } catch (Exception var14) {
            throw new IOException("exception decrypting data - " + var14.toString());
         }
      } else {
         throw new IOException("unknown PBE algorithm: " + var6);
      }
   }

   private Cipher createCipher(int var1, char[] var2, AlgorithmIdentifier var3) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException {
      PBES2Parameters var4 = PBES2Parameters.getInstance(var3.getParameters());
      PBKDF2Params var5 = PBKDF2Params.getInstance(var4.getKeyDerivationFunc().getParameters());
      AlgorithmIdentifier var6 = AlgorithmIdentifier.getInstance(var4.getEncryptionScheme());
      SecretKeyFactory var7 = this.helper.createSecretKeyFactory(var4.getKeyDerivationFunc().getAlgorithm().getId());
      SecretKey var8;
      if (var5.isDefaultPrf()) {
         var8 = var7.generateSecret(new PBEKeySpec(var2, var5.getSalt(), var5.getIterationCount().intValue(), keySizeProvider.getKeySize(var6)));
      } else {
         var8 = var7.generateSecret(new PBKDF2KeySpec(var2, var5.getSalt(), var5.getIterationCount().intValue(), keySizeProvider.getKeySize(var6), var5.getPrf()));
      }

      Cipher var9 = Cipher.getInstance(var4.getEncryptionScheme().getAlgorithm().getId());
      AlgorithmIdentifier var10 = AlgorithmIdentifier.getInstance(var4.getEncryptionScheme());
      ASN1Encodable var11 = var4.getEncryptionScheme().getParameters();
      if (var11 instanceof ASN1OctetString) {
         var9.init(var1, var8, new IvParameterSpec(ASN1OctetString.getInstance(var11).getOctets()));
      } else {
         GOST28147Parameters var12 = GOST28147Parameters.getInstance(var11);
         var9.init(var1, var8, new GOST28147ParameterSpec(var12.getEncryptionParamSet(), var12.getIV()));
      }

      return var9;
   }

   public void engineLoad(InputStream var1, char[] var2) throws IOException {
      if (var1 != null) {
         if (var2 == null) {
            throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
         } else {
            BufferedInputStream var3 = new BufferedInputStream(var1);
            var3.mark(10);
            int var4 = var3.read();
            if (var4 != 48) {
               throw new IOException("stream does not represent a PKCS12 key store");
            } else {
               var3.reset();
               ASN1InputStream var5 = new ASN1InputStream(var3);
               ASN1Sequence var6 = (ASN1Sequence)var5.readObject();
               Pfx var7 = Pfx.getInstance(var6);
               ContentInfo var8 = var7.getAuthSafe();
               Vector var9 = new Vector();
               boolean var10 = false;
               boolean var11 = false;
               if (var7.getMacData() != null) {
                  MacData var12 = var7.getMacData();
                  DigestInfo var13 = var12.getMac();
                  AlgorithmIdentifier var14 = var13.getAlgorithmId();
                  byte[] var15 = var12.getSalt();
                  int var16 = var12.getIterationCount().intValue();
                  byte[] var17 = ((ASN1OctetString)var8.getContent()).getOctets();

                  try {
                     byte[] var18 = this.calculatePbeMac(var14.getAlgorithm(), var15, var16, var2, false, var17);
                     byte[] var19 = var13.getDigest();
                     if (!Arrays.constantTimeAreEqual(var18, var19)) {
                        if (var2.length > 0) {
                           throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                        }

                        var18 = this.calculatePbeMac(var14.getAlgorithm(), var15, var16, var2, true, var17);
                        if (!Arrays.constantTimeAreEqual(var18, var19)) {
                           throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                        }

                        var11 = true;
                     }
                  } catch (IOException var32) {
                     throw var32;
                  } catch (Exception var33) {
                     throw new IOException("error constructing MAC: " + var33.toString());
                  }
               }

               this.keys = new IgnoresCaseHashtable();
               this.localIds = new Hashtable();
               String var23;
               if (var8.getContentType().equals(data)) {
                  var5 = new ASN1InputStream(((ASN1OctetString)var8.getContent()).getOctets());
                  AuthenticatedSafe var34 = AuthenticatedSafe.getInstance(var5.readObject());
                  ContentInfo[] var36 = var34.getContentInfo();

                  for(int var38 = 0; var38 != var36.length; ++var38) {
                     if (var36[var38].getContentType().equals(data)) {
                        ASN1InputStream var41 = new ASN1InputStream(((ASN1OctetString)var36[var38].getContent()).getOctets());
                        ASN1Sequence var44 = (ASN1Sequence)var41.readObject();

                        for(int var48 = 0; var48 != var44.size(); ++var48) {
                           SafeBag var51 = SafeBag.getInstance(var44.getObjectAt(var48));
                           if (!var51.getBagId().equals(pkcs8ShroudedKeyBag)) {
                              if (var51.getBagId().equals(certBag)) {
                                 var9.addElement(var51);
                              } else {
                                 System.out.println("extra in data " + var51.getBagId());
                                 System.out.println(ASN1Dump.dumpAsString(var51));
                              }
                           } else {
                              EncryptedPrivateKeyInfo var54 = EncryptedPrivateKeyInfo.getInstance(var51.getBagValue());
                              PrivateKey var58 = this.unwrapKey(var54.getEncryptionAlgorithm(), var54.getEncryptedData(), var2, var11);
                              PKCS12BagAttributeCarrier var60 = (PKCS12BagAttributeCarrier)var58;
                              String var62 = null;
                              ASN1OctetString var64 = null;
                              if (var51.getBagAttributes() != null) {
                                 Enumeration var65 = var51.getBagAttributes().getObjects();

                                 while(var65.hasMoreElements()) {
                                    ASN1Sequence var68 = (ASN1Sequence)var65.nextElement();
                                    ASN1ObjectIdentifier var71 = (ASN1ObjectIdentifier)var68.getObjectAt(0);
                                    ASN1Set var72 = (ASN1Set)var68.getObjectAt(1);
                                    ASN1Primitive var73 = null;
                                    if (var72.size() > 0) {
                                       var73 = (ASN1Primitive)var72.getObjectAt(0);
                                       ASN1Encodable var74 = var60.getBagAttribute(var71);
                                       if (var74 != null) {
                                          if (!var74.toASN1Primitive().equals(var73)) {
                                             throw new IOException("attempt to add existing attribute with different value");
                                          }
                                       } else {
                                          var60.setBagAttribute(var71, var73);
                                       }
                                    }

                                    if (var71.equals(pkcs_9_at_friendlyName)) {
                                       var62 = ((DERBMPString)var73).getString();
                                       this.keys.put(var62, var58);
                                    } else if (var71.equals(pkcs_9_at_localKeyId)) {
                                       var64 = (ASN1OctetString)var73;
                                    }
                                 }
                              }

                              if (var64 != null) {
                                 String var67 = new String(Hex.encode(var64.getOctets()));
                                 if (var62 == null) {
                                    this.keys.put(var67, var58);
                                 } else {
                                    this.localIds.put(var62, var67);
                                 }
                              } else {
                                 var10 = true;
                                 this.keys.put("unmarked", var58);
                              }
                           }
                        }
                     } else if (!var36[var38].getContentType().equals(encryptedData)) {
                        System.out.println("extra " + var36[var38].getContentType().getId());
                        System.out.println("extra " + ASN1Dump.dumpAsString(var36[var38].getContent()));
                     } else {
                        EncryptedData var40 = EncryptedData.getInstance(var36[var38].getContent());
                        byte[] var43 = this.cryptData(false, var40.getEncryptionAlgorithm(), var2, var11, var40.getContent().getOctets());
                        ASN1Sequence var46 = (ASN1Sequence)ASN1Primitive.fromByteArray(var43);

                        for(int var50 = 0; var50 != var46.size(); ++var50) {
                           SafeBag var52 = SafeBag.getInstance(var46.getObjectAt(var50));
                           if (var52.getBagId().equals(certBag)) {
                              var9.addElement(var52);
                           } else {
                              PrivateKey var21;
                              PKCS12BagAttributeCarrier var22;
                              ASN1OctetString var24;
                              Enumeration var25;
                              ASN1Sequence var26;
                              ASN1ObjectIdentifier var27;
                              ASN1Set var28;
                              ASN1Primitive var29;
                              ASN1Encodable var30;
                              String var70;
                              if (var52.getBagId().equals(pkcs8ShroudedKeyBag)) {
                                 EncryptedPrivateKeyInfo var57 = EncryptedPrivateKeyInfo.getInstance(var52.getBagValue());
                                 var21 = this.unwrapKey(var57.getEncryptionAlgorithm(), var57.getEncryptedData(), var2, var11);
                                 var22 = (PKCS12BagAttributeCarrier)var21;
                                 var23 = null;
                                 var24 = null;
                                 var25 = var52.getBagAttributes().getObjects();

                                 while(var25.hasMoreElements()) {
                                    var26 = (ASN1Sequence)var25.nextElement();
                                    var27 = (ASN1ObjectIdentifier)var26.getObjectAt(0);
                                    var28 = (ASN1Set)var26.getObjectAt(1);
                                    var29 = null;
                                    if (var28.size() > 0) {
                                       var29 = (ASN1Primitive)var28.getObjectAt(0);
                                       var30 = var22.getBagAttribute(var27);
                                       if (var30 != null) {
                                          if (!var30.toASN1Primitive().equals(var29)) {
                                             throw new IOException("attempt to add existing attribute with different value");
                                          }
                                       } else {
                                          var22.setBagAttribute(var27, var29);
                                       }
                                    }

                                    if (var27.equals(pkcs_9_at_friendlyName)) {
                                       var23 = ((DERBMPString)var29).getString();
                                       this.keys.put(var23, var21);
                                    } else if (var27.equals(pkcs_9_at_localKeyId)) {
                                       var24 = (ASN1OctetString)var29;
                                    }
                                 }

                                 var70 = new String(Hex.encode(var24.getOctets()));
                                 if (var23 == null) {
                                    this.keys.put(var70, var21);
                                 } else {
                                    this.localIds.put(var23, var70);
                                 }
                              } else if (!var52.getBagId().equals(keyBag)) {
                                 System.out.println("extra in encryptedData " + var52.getBagId());
                                 System.out.println(ASN1Dump.dumpAsString(var52));
                              } else {
                                 PrivateKeyInfo var20 = PrivateKeyInfo.getInstance(var52.getBagValue());
                                 var21 = BouncyCastleProvider.getPrivateKey(var20);
                                 var22 = (PKCS12BagAttributeCarrier)var21;
                                 var23 = null;
                                 var24 = null;
                                 var25 = var52.getBagAttributes().getObjects();

                                 while(var25.hasMoreElements()) {
                                    var26 = ASN1Sequence.getInstance(var25.nextElement());
                                    var27 = ASN1ObjectIdentifier.getInstance(var26.getObjectAt(0));
                                    var28 = ASN1Set.getInstance(var26.getObjectAt(1));
                                    var29 = null;
                                    if (var28.size() > 0) {
                                       var29 = (ASN1Primitive)var28.getObjectAt(0);
                                       var30 = var22.getBagAttribute(var27);
                                       if (var30 != null) {
                                          if (!var30.toASN1Primitive().equals(var29)) {
                                             throw new IOException("attempt to add existing attribute with different value");
                                          }
                                       } else {
                                          var22.setBagAttribute(var27, var29);
                                       }

                                       if (var27.equals(pkcs_9_at_friendlyName)) {
                                          var23 = ((DERBMPString)var29).getString();
                                          this.keys.put(var23, var21);
                                       } else if (var27.equals(pkcs_9_at_localKeyId)) {
                                          var24 = (ASN1OctetString)var29;
                                       }
                                    }
                                 }

                                 var70 = new String(Hex.encode(var24.getOctets()));
                                 if (var23 == null) {
                                    this.keys.put(var70, var21);
                                 } else {
                                    this.localIds.put(var23, var70);
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               this.certs = new IgnoresCaseHashtable();
               this.chainCerts = new Hashtable();
               this.keyCerts = new Hashtable();

               for(int var35 = 0; var35 != var9.size(); ++var35) {
                  SafeBag var37 = (SafeBag)var9.elementAt(var35);
                  CertBag var39 = CertBag.getInstance(var37.getBagValue());
                  if (!var39.getCertId().equals(x509Certificate)) {
                     throw new RuntimeException("Unsupported certificate type: " + var39.getCertId());
                  }

                  Certificate var42;
                  try {
                     ByteArrayInputStream var45 = new ByteArrayInputStream(((ASN1OctetString)var39.getCertValue()).getOctets());
                     var42 = this.certFact.generateCertificate(var45);
                  } catch (Exception var31) {
                     throw new RuntimeException(var31.toString());
                  }

                  ASN1OctetString var47 = null;
                  String var49 = null;
                  if (var37.getBagAttributes() != null) {
                     Enumeration var53 = var37.getBagAttributes().getObjects();

                     while(var53.hasMoreElements()) {
                        ASN1Sequence var55 = ASN1Sequence.getInstance(var53.nextElement());
                        ASN1ObjectIdentifier var59 = ASN1ObjectIdentifier.getInstance(var55.getObjectAt(0));
                        ASN1Set var61 = ASN1Set.getInstance(var55.getObjectAt(1));
                        if (var61.size() > 0) {
                           ASN1Primitive var63 = (ASN1Primitive)var61.getObjectAt(0);
                           var23 = null;
                           if (var42 instanceof PKCS12BagAttributeCarrier) {
                              PKCS12BagAttributeCarrier var66 = (PKCS12BagAttributeCarrier)var42;
                              ASN1Encodable var69 = var66.getBagAttribute(var59);
                              if (var69 != null) {
                                 if (!var69.toASN1Primitive().equals(var63)) {
                                    throw new IOException("attempt to add existing attribute with different value");
                                 }
                              } else {
                                 var66.setBagAttribute(var59, var63);
                              }
                           }

                           if (var59.equals(pkcs_9_at_friendlyName)) {
                              var49 = ((DERBMPString)var63).getString();
                           } else if (var59.equals(pkcs_9_at_localKeyId)) {
                              var47 = (ASN1OctetString)var63;
                           }
                        }
                     }
                  }

                  this.chainCerts.put(new CertId(var42.getPublicKey()), var42);
                  String var56;
                  if (var10) {
                     if (this.keyCerts.isEmpty()) {
                        var56 = new String(Hex.encode(this.createSubjectKeyId(var42.getPublicKey()).getKeyIdentifier()));
                        this.keyCerts.put(var56, var42);
                        this.keys.put(var56, this.keys.remove("unmarked"));
                     }
                  } else {
                     if (var47 != null) {
                        var56 = new String(Hex.encode(var47.getOctets()));
                        this.keyCerts.put(var56, var42);
                     }

                     if (var49 != null) {
                        this.certs.put(var49, var42);
                     }
                  }
               }

            }
         }
      }
   }

   public void engineStore(KeyStore.LoadStoreParameter var1) throws IOException, NoSuchAlgorithmException, CertificateException {
      if (var1 == null) {
         throw new IllegalArgumentException("'param' arg cannot be null");
      } else if (!(var1 instanceof PKCS12StoreParameter) && !(var1 instanceof JDKPKCS12StoreParameter)) {
         throw new IllegalArgumentException("No support for 'param' of type " + var1.getClass().getName());
      } else {
         PKCS12StoreParameter var2;
         if (var1 instanceof PKCS12StoreParameter) {
            var2 = (PKCS12StoreParameter)var1;
         } else {
            var2 = new PKCS12StoreParameter(((JDKPKCS12StoreParameter)var1).getOutputStream(), var1.getProtectionParameter(), ((JDKPKCS12StoreParameter)var1).isUseDEREncoding());
         }

         KeyStore.ProtectionParameter var3 = var1.getProtectionParameter();
         char[] var4;
         if (var3 == null) {
            var4 = null;
         } else {
            if (!(var3 instanceof KeyStore.PasswordProtection)) {
               throw new IllegalArgumentException("No support for protection parameter of type " + var3.getClass().getName());
            }

            var4 = ((KeyStore.PasswordProtection)var3).getPassword();
         }

         this.doStore(var2.getOutputStream(), var4, var2.isForDEREncoding());
      }
   }

   public void engineStore(OutputStream var1, char[] var2) throws IOException {
      this.doStore(var1, var2, false);
   }

   private void doStore(OutputStream var1, char[] var2, boolean var3) throws IOException {
      if (var2 == null) {
         throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
      } else {
         ASN1EncodableVector var4 = new ASN1EncodableVector();
         Enumeration var5 = this.keys.keys();

         byte[] var6;
         AlgorithmIdentifier var11;
         ASN1EncodableVector var19;
         Certificate var46;
         while(var5.hasMoreElements()) {
            var6 = new byte[20];
            this.random.nextBytes(var6);
            String var7 = (String)var5.nextElement();
            PrivateKey var8 = (PrivateKey)this.keys.get(var7);
            PKCS12PBEParams var9 = new PKCS12PBEParams(var6, 1024);
            byte[] var10 = this.wrapKey(this.keyAlgorithm.getId(), var8, var9, var2);
            var11 = new AlgorithmIdentifier(this.keyAlgorithm, var9.toASN1Primitive());
            EncryptedPrivateKeyInfo var12 = new EncryptedPrivateKeyInfo(var11, var10);
            boolean var13 = false;
            ASN1EncodableVector var14 = new ASN1EncodableVector();
            if (var8 instanceof PKCS12BagAttributeCarrier) {
               PKCS12BagAttributeCarrier var15 = (PKCS12BagAttributeCarrier)var8;
               DERBMPString var16 = (DERBMPString)var15.getBagAttribute(pkcs_9_at_friendlyName);
               if (var16 == null || !var16.getString().equals(var7)) {
                  var15.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(var7));
               }

               if (var15.getBagAttribute(pkcs_9_at_localKeyId) == null) {
                  Certificate var17 = this.engineGetCertificate(var7);
                  var15.setBagAttribute(pkcs_9_at_localKeyId, this.createSubjectKeyId(var17.getPublicKey()));
               }

               Enumeration var49 = var15.getBagAttributeKeys();

               while(var49.hasMoreElements()) {
                  ASN1ObjectIdentifier var18 = (ASN1ObjectIdentifier)var49.nextElement();
                  var19 = new ASN1EncodableVector();
                  var19.add(var18);
                  var19.add(new DERSet(var15.getBagAttribute(var18)));
                  var13 = true;
                  var14.add(new DERSequence(var19));
               }
            }

            if (!var13) {
               ASN1EncodableVector var43 = new ASN1EncodableVector();
               var46 = this.engineGetCertificate(var7);
               var43.add(pkcs_9_at_localKeyId);
               var43.add(new DERSet(this.createSubjectKeyId(var46.getPublicKey())));
               var14.add(new DERSequence(var43));
               var43 = new ASN1EncodableVector();
               var43.add(pkcs_9_at_friendlyName);
               var43.add(new DERSet(new DERBMPString(var7)));
               var14.add(new DERSequence(var43));
            }

            SafeBag var44 = new SafeBag(pkcs8ShroudedKeyBag, var12.toASN1Primitive(), new DERSet(var14));
            var4.add(var44);
         }

         var6 = (new DERSequence(var4)).getEncoded("DER");
         BEROctetString var35 = new BEROctetString(var6);
         byte[] var36 = new byte[20];
         this.random.nextBytes(var36);
         ASN1EncodableVector var37 = new ASN1EncodableVector();
         PKCS12PBEParams var38 = new PKCS12PBEParams(var36, 1024);
         var11 = new AlgorithmIdentifier(this.certAlgorithm, var38.toASN1Primitive());
         Hashtable var39 = new Hashtable();
         Enumeration var40 = this.keys.keys();

         DERBMPString var20;
         Enumeration var21;
         ASN1ObjectIdentifier var22;
         ASN1EncodableVector var23;
         String var41;
         Certificate var45;
         boolean var47;
         CertBag var51;
         ASN1EncodableVector var52;
         PKCS12BagAttributeCarrier var55;
         SafeBag var57;
         while(var40.hasMoreElements()) {
            try {
               var41 = (String)var40.nextElement();
               var45 = this.engineGetCertificate(var41);
               var47 = false;
               var51 = new CertBag(x509Certificate, new DEROctetString(var45.getEncoded()));
               var52 = new ASN1EncodableVector();
               if (var45 instanceof PKCS12BagAttributeCarrier) {
                  var55 = (PKCS12BagAttributeCarrier)var45;
                  var20 = (DERBMPString)var55.getBagAttribute(pkcs_9_at_friendlyName);
                  if (var20 == null || !var20.getString().equals(var41)) {
                     var55.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(var41));
                  }

                  if (var55.getBagAttribute(pkcs_9_at_localKeyId) == null) {
                     var55.setBagAttribute(pkcs_9_at_localKeyId, this.createSubjectKeyId(var45.getPublicKey()));
                  }

                  for(var21 = var55.getBagAttributeKeys(); var21.hasMoreElements(); var47 = true) {
                     var22 = (ASN1ObjectIdentifier)var21.nextElement();
                     var23 = new ASN1EncodableVector();
                     var23.add(var22);
                     var23.add(new DERSet(var55.getBagAttribute(var22)));
                     var52.add(new DERSequence(var23));
                  }
               }

               if (!var47) {
                  var19 = new ASN1EncodableVector();
                  var19.add(pkcs_9_at_localKeyId);
                  var19.add(new DERSet(this.createSubjectKeyId(var45.getPublicKey())));
                  var52.add(new DERSequence(var19));
                  var19 = new ASN1EncodableVector();
                  var19.add(pkcs_9_at_friendlyName);
                  var19.add(new DERSet(new DERBMPString(var41)));
                  var52.add(new DERSequence(var19));
               }

               var57 = new SafeBag(certBag, var51.toASN1Primitive(), new DERSet(var52));
               var37.add(var57);
               var39.put(var45, var45);
            } catch (CertificateEncodingException var34) {
               throw new IOException("Error encoding certificate: " + var34.toString());
            }
         }

         var40 = this.certs.keys();

         while(var40.hasMoreElements()) {
            try {
               var41 = (String)var40.nextElement();
               var45 = (Certificate)this.certs.get(var41);
               var47 = false;
               if (this.keys.get(var41) == null) {
                  var51 = new CertBag(x509Certificate, new DEROctetString(var45.getEncoded()));
                  var52 = new ASN1EncodableVector();
                  if (var45 instanceof PKCS12BagAttributeCarrier) {
                     var55 = (PKCS12BagAttributeCarrier)var45;
                     var20 = (DERBMPString)var55.getBagAttribute(pkcs_9_at_friendlyName);
                     if (var20 == null || !var20.getString().equals(var41)) {
                        var55.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(var41));
                     }

                     var21 = var55.getBagAttributeKeys();

                     while(var21.hasMoreElements()) {
                        var22 = (ASN1ObjectIdentifier)var21.nextElement();
                        if (!var22.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                           var23 = new ASN1EncodableVector();
                           var23.add(var22);
                           var23.add(new DERSet(var55.getBagAttribute(var22)));
                           var52.add(new DERSequence(var23));
                           var47 = true;
                        }
                     }
                  }

                  if (!var47) {
                     var19 = new ASN1EncodableVector();
                     var19.add(pkcs_9_at_friendlyName);
                     var19.add(new DERSet(new DERBMPString(var41)));
                     var52.add(new DERSequence(var19));
                  }

                  var57 = new SafeBag(certBag, var51.toASN1Primitive(), new DERSet(var52));
                  var37.add(var57);
                  var39.put(var45, var45);
               }
            } catch (CertificateEncodingException var33) {
               throw new IOException("Error encoding certificate: " + var33.toString());
            }
         }

         Set var42 = this.getUsedCertificateSet();
         var40 = this.chainCerts.keys();

         while(var40.hasMoreElements()) {
            try {
               CertId var48 = (CertId)var40.nextElement();
               var46 = (Certificate)this.chainCerts.get(var48);
               if (var42.contains(var46) && var39.get(var46) == null) {
                  var51 = new CertBag(x509Certificate, new DEROctetString(var46.getEncoded()));
                  var52 = new ASN1EncodableVector();
                  if (var46 instanceof PKCS12BagAttributeCarrier) {
                     var55 = (PKCS12BagAttributeCarrier)var46;
                     Enumeration var58 = var55.getBagAttributeKeys();

                     while(var58.hasMoreElements()) {
                        ASN1ObjectIdentifier var60 = (ASN1ObjectIdentifier)var58.nextElement();
                        if (!var60.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                           ASN1EncodableVector var63 = new ASN1EncodableVector();
                           var63.add(var60);
                           var63.add(new DERSet(var55.getBagAttribute(var60)));
                           var52.add(new DERSequence(var63));
                        }
                     }
                  }

                  var57 = new SafeBag(certBag, var51.toASN1Primitive(), new DERSet(var52));
                  var37.add(var57);
               }
            } catch (CertificateEncodingException var32) {
               throw new IOException("Error encoding certificate: " + var32.toString());
            }
         }

         byte[] var50 = (new DERSequence(var37)).getEncoded("DER");
         byte[] var53 = this.cryptData(true, var11, var2, false, var50);
         EncryptedData var54 = new EncryptedData(data, var11, new BEROctetString(var53));
         ContentInfo[] var56 = new ContentInfo[]{new ContentInfo(data, var35), new ContentInfo(encryptedData, var54.toASN1Primitive())};
         AuthenticatedSafe var62 = new AuthenticatedSafe(var56);
         ByteArrayOutputStream var59 = new ByteArrayOutputStream();
         Object var61;
         if (var3) {
            var61 = new DEROutputStream(var59);
         } else {
            var61 = new BEROutputStream(var59);
         }

         ((DEROutputStream)var61).writeObject(var62);
         byte[] var64 = var59.toByteArray();
         ContentInfo var65 = new ContentInfo(data, new BEROctetString(var64));
         byte[] var24 = new byte[20];
         short var25 = 1024;
         this.random.nextBytes(var24);
         byte[] var26 = ((ASN1OctetString)var65.getContent()).getOctets();

         MacData var30;
         try {
            byte[] var27 = this.calculatePbeMac(id_SHA1, var24, var25, var2, false, var26);
            AlgorithmIdentifier var28 = new AlgorithmIdentifier(id_SHA1, DERNull.INSTANCE);
            DigestInfo var29 = new DigestInfo(var28, var27);
            var30 = new MacData(var29, var24, var25);
         } catch (Exception var31) {
            throw new IOException("error constructing MAC: " + var31.toString());
         }

         Pfx var66 = new Pfx(var65, var30);
         if (var3) {
            var61 = new DEROutputStream(var1);
         } else {
            var61 = new BEROutputStream(var1);
         }

         ((DEROutputStream)var61).writeObject(var66);
      }
   }

   private Set getUsedCertificateSet() {
      HashSet var1 = new HashSet();
      Enumeration var2 = this.keys.keys();

      String var3;
      while(var2.hasMoreElements()) {
         var3 = (String)var2.nextElement();
         Certificate[] var4 = this.engineGetCertificateChain(var3);

         for(int var5 = 0; var5 != var4.length; ++var5) {
            var1.add(var4[var5]);
         }
      }

      var2 = this.certs.keys();

      while(var2.hasMoreElements()) {
         var3 = (String)var2.nextElement();
         Certificate var6 = this.engineGetCertificate(var3);
         var1.add(var6);
      }

      return var1;
   }

   private byte[] calculatePbeMac(ASN1ObjectIdentifier var1, byte[] var2, int var3, char[] var4, boolean var5, byte[] var6) throws Exception {
      PBEParameterSpec var7 = new PBEParameterSpec(var2, var3);
      Mac var8 = this.helper.createMac(var1.getId());
      var8.init(new PKCS12Key(var4, var5), var7);
      var8.update(var6);
      return var8.doFinal();
   }

   public static class BCPKCS12KeyStore extends PKCS12KeyStoreSpi {
      public BCPKCS12KeyStore() {
         super(new BouncyCastleProvider(), pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
      }
   }

   public static class BCPKCS12KeyStore3DES extends PKCS12KeyStoreSpi {
      public BCPKCS12KeyStore3DES() {
         super(new BouncyCastleProvider(), pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
      }
   }

   private class CertId {
      byte[] id;

      CertId(PublicKey var2) {
         this.id = PKCS12KeyStoreSpi.this.createSubjectKeyId(var2).getKeyIdentifier();
      }

      CertId(byte[] var2) {
         this.id = var2;
      }

      public int hashCode() {
         return Arrays.hashCode(this.id);
      }

      public boolean equals(Object var1) {
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof CertId)) {
            return false;
         } else {
            CertId var2 = (CertId)var1;
            return Arrays.areEqual(this.id, var2.id);
         }
      }
   }

   public static class DefPKCS12KeyStore extends PKCS12KeyStoreSpi {
      public DefPKCS12KeyStore() {
         super((Provider)null, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
      }
   }

   public static class DefPKCS12KeyStore3DES extends PKCS12KeyStoreSpi {
      public DefPKCS12KeyStore3DES() {
         super((Provider)null, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
      }
   }

   private static class DefaultSecretKeyProvider {
      private final Map KEY_SIZES;

      DefaultSecretKeyProvider() {
         HashMap var1 = new HashMap();
         var1.put(new ASN1ObjectIdentifier("1.2.840.113533.7.66.10"), Integers.valueOf(128));
         var1.put(PKCSObjectIdentifiers.des_EDE3_CBC, Integers.valueOf(192));
         var1.put(NISTObjectIdentifiers.id_aes128_CBC, Integers.valueOf(128));
         var1.put(NISTObjectIdentifiers.id_aes192_CBC, Integers.valueOf(192));
         var1.put(NISTObjectIdentifiers.id_aes256_CBC, Integers.valueOf(256));
         var1.put(NTTObjectIdentifiers.id_camellia128_cbc, Integers.valueOf(128));
         var1.put(NTTObjectIdentifiers.id_camellia192_cbc, Integers.valueOf(192));
         var1.put(NTTObjectIdentifiers.id_camellia256_cbc, Integers.valueOf(256));
         var1.put(CryptoProObjectIdentifiers.gostR28147_gcfb, Integers.valueOf(256));
         this.KEY_SIZES = Collections.unmodifiableMap(var1);
      }

      public int getKeySize(AlgorithmIdentifier var1) {
         Integer var2 = (Integer)this.KEY_SIZES.get(var1.getAlgorithm());
         return var2 != null ? var2 : -1;
      }
   }

   private static class IgnoresCaseHashtable {
      private Hashtable orig;
      private Hashtable keys;

      private IgnoresCaseHashtable() {
         this.orig = new Hashtable();
         this.keys = new Hashtable();
      }

      public void put(String var1, Object var2) {
         String var3 = var1 == null ? null : Strings.toLowerCase(var1);
         String var4 = (String)this.keys.get(var3);
         if (var4 != null) {
            this.orig.remove(var4);
         }

         this.keys.put(var3, var1);
         this.orig.put(var1, var2);
      }

      public Enumeration keys() {
         return this.orig.keys();
      }

      public Object remove(String var1) {
         String var2 = (String)this.keys.remove(var1 == null ? null : Strings.toLowerCase(var1));
         return var2 == null ? null : this.orig.remove(var2);
      }

      public Object get(String var1) {
         String var2 = (String)this.keys.get(var1 == null ? null : Strings.toLowerCase(var1));
         return var2 == null ? null : this.orig.get(var2);
      }

      public Enumeration elements() {
         return this.orig.elements();
      }

      // $FF: synthetic method
      IgnoresCaseHashtable(Object var1) {
         this();
      }
   }
}
