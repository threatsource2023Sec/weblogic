package com.rsa.certj.provider.db.pkcs11;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.db.DatabaseInterface;
import com.rsa.certj.x.h;
import com.rsa.jsafe.FIPS140Context;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_InvalidParameterException;
import com.rsa.jsafe.JSAFE_MessageDigest;
import com.rsa.jsafe.JSAFE_PKCS11SessionSpec;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_Session;
import com.rsa.jsafe.JSAFE_SessionSpec;
import com.rsa.jsafe.JSAFE_Signature;
import com.rsa.jsafe.JSAFE_UnimplementedException;
import com.rsa.jsafe.provider.HardwareIterator;
import com.rsa.jsafe.provider.HardwareStore;
import com.rsa.jsafe.provider.HardwareStoreException;
import com.rsa.jsafe.provider.JsafeJCE;
import com.rsa.jsafe.provider.JsafeJCEPKCS11;
import com.rsa.jsafe.provider.PKCS11CertIteratorParameters;
import com.rsa.jsafe.provider.PKCS11Key;
import com.rsa.jsafe.provider.PKCS11KeyIteratorParameters;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

/** @deprecated */
public final class PKCS11DB extends Provider {
   JsafeJCEPKCS11 p11Provider;
   HardwareStore store;
   private final java.security.Provider jsafeJCE = new JsafeJCE();
   private HardwareIterator certIterator;
   private HardwareIterator keyIterator;
   private JSAFE_Session session;
   private boolean sessionFlag;
   private static final String PASSED_IN_SESSION_IS_NULL = "Passed in session is null.";
   private static final String CANNOT_CREATE_PKCS_11_SESSION = "Cannot create PKCS#11 session.";
   private static final String CANNOT_CREATE_PROVIDER = "Cannot create provider.";

   /** @deprecated */
   public PKCS11DB(String var1, JSAFE_Session var2) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("Passed in session is null.");
      } else {
         this.session = var2;
         this.createProvider(var1, var2);
      }
   }

   /** @deprecated */
   public PKCS11DB(String var1, JSAFE_PKCS11SessionSpec var2) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("Spec is null.");
      } else {
         try {
            this.session = JSAFE_Session.getInstance(var2);
            this.sessionFlag = true;
            this.createProvider(var1, this.session);
         } catch (JSAFE_InvalidParameterException var4) {
            throw new InvalidParameterException("Cannot create PKCS#11 session.", var4);
         }
      }
   }

   /** @deprecated */
   public PKCS11DB(String var1, String var2, String var3, char[] var4, int var5, int var6) throws InvalidParameterException {
      super(1, var1);

      try {
         JSAFE_PKCS11SessionSpec var7 = new JSAFE_PKCS11SessionSpec(var2, var3, var4, var5, var6);
         this.session = JSAFE_Session.getInstance(var7);
         this.sessionFlag = true;
         this.createProvider(var1, this.session);
      } catch (JSAFE_InvalidParameterException var8) {
         throw new InvalidParameterException("Cannot create PKCS#11 session.", var8);
      }
   }

   private void createProvider(String var1, JSAFE_Session var2) throws InvalidParameterException {
      if (var2 == null) {
         throw new InvalidParameterException("Passed in session is null.");
      } else {
         JSAFE_SessionSpec var3 = var2.getSessionSpec();
         if (var3 != null && var3 instanceof JSAFE_PKCS11SessionSpec) {
            JSAFE_PKCS11SessionSpec var4 = (JSAFE_PKCS11SessionSpec)var3;

            try {
               this.p11Provider = this.createP11Provider(var1, var4);
            } catch (Exception var7) {
               this.p11Provider = null;
            }

            if (this.p11Provider == null) {
               throw new InvalidParameterException("Cannot create provider.");
            } else {
               try {
                  this.store = HardwareStore.getInstance("PKCS11", this.p11Provider);
               } catch (NoSuchAlgorithmException var6) {
                  throw new InvalidParameterException("Cannot create provider.");
               }
            }
         } else {
            throw new InvalidParameterException("Passed in session does not contain PKCS11 spec.");
         }
      }
   }

   private JsafeJCEPKCS11 createP11Provider(String var1, JSAFE_PKCS11SessionSpec var2) throws Exception {
      Properties var3 = new Properties();
      var3.setProperty("library", var2.getLibraryName());
      var3.setProperty("name", var1);
      var3.setProperty("tokenLabel", var2.getTokenLabel());
      ByteArrayOutputStream var4 = new ByteArrayOutputStream();
      var3.store(var4, (String)null);
      ByteArrayInputStream var5 = new ByteArrayInputStream(var4.toByteArray());
      JsafeJCEPKCS11 var6 = new JsafeJCEPKCS11(var5);
      var6.login(CertJUtils.byteArrayToCharArray(var2.getPassPhrase()));
      var5.close();
      var4.close();
      return var6;
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException("PKCS11DB.instantiate.", var3);
      }
   }

   /** @deprecated */
   public String toString() {
      return "PKCS11 database provider named: " + super.getName();
   }

   private int nativeInsertCertificate(byte[] var1, byte[] var2) {
      try {
         CertificateFactory var3 = CertificateFactory.getInstance("X509", this.jsafeJCE);
         ByteArrayInputStream var4 = new ByteArrayInputStream(var1);
         Certificate var5 = var3.generateCertificate(var4);
         var4.close();
         this.store.setCertificate(var2, (String)null, var5);
         return 0;
      } catch (Exception var6) {
         return 1;
      }
   }

   private int nativeInsertPrivateKey(String var1, byte[] var2, byte[] var3) {
      try {
         KeyFactory var4 = KeyFactory.getInstance(var1, this.jsafeJCE);
         PrivateKey var5 = var4.generatePrivate(new PKCS8EncodedKeySpec(var3));
         this.store.setKey(var2, (String)null, var5);
         return 0;
      } catch (Exception var6) {
         return 1;
      }
   }

   private byte[][] nativeSelectCertByIssuerSerial(byte[] var1, byte[] var2, java.security.Provider var3) {
      try {
         ArrayList var4 = new ArrayList();
         HardwareStore var5 = HardwareStore.getInstance("PKCS11", (JsafeJCEPKCS11)var3);
         PKCS11CertIteratorParameters var6 = new PKCS11CertIteratorParameters((byte[])null, (String)null);
         HardwareIterator var7 = var5.certificateIterator(var6);

         while(var7.hasNext()) {
            X509Certificate var8 = (X509Certificate)var7.next();
            byte[] var9 = var8.getIssuerX500Principal().getEncoded();
            if (CertJUtils.byteArraysEqual(var1, var9)) {
               byte[] var10 = var8.getSerialNumber().toByteArray();
               if (CertJUtils.byteArraysEqual(var2, var10)) {
                  var4.add(var8);
               }
            }
         }

         int var13 = var4.size();
         byte[][] var14 = new byte[var13][];

         for(int var12 = 0; var12 < var13; ++var12) {
            var14[var12] = ((X509Certificate)var4.get(var12)).getEncoded();
         }

         return var14;
      } catch (Exception var11) {
         return (byte[][])null;
      }
   }

   private byte[][] nativeSelectCertBySubject(byte[] var1, java.security.Provider var2) {
      try {
         ArrayList var3 = new ArrayList();
         HardwareStore var4 = HardwareStore.getInstance("PKCS11", (JsafeJCEPKCS11)var2);
         PKCS11CertIteratorParameters var5 = new PKCS11CertIteratorParameters((byte[])null, (String)null);
         HardwareIterator var6 = var4.certificateIterator(var5);

         while(var6.hasNext()) {
            X509Certificate var7 = (X509Certificate)var6.next();
            byte[] var8 = var7.getSubjectX500Principal().getEncoded();
            if (CertJUtils.byteArraysEqual(var1, var8)) {
               var3.add(var7);
            }
         }

         int var11 = var3.size();
         byte[][] var12 = new byte[var11][];

         for(int var9 = 0; var9 < var11; ++var9) {
            var12[var9] = ((X509Certificate)var3.get(var9)).getEncoded();
         }

         return var12;
      } catch (Exception var10) {
         return (byte[][])null;
      }
   }

   private byte[][] nativeSelectCertByExtensions(byte[] var1, byte[] var2, java.security.Provider var3) {
      try {
         ArrayList var4 = new ArrayList();
         HardwareStore var5 = HardwareStore.getInstance("PKCS11", (JsafeJCEPKCS11)var3);
         PKCS11CertIteratorParameters var6 = new PKCS11CertIteratorParameters((byte[])null, (String)null);
         HardwareIterator var7 = var5.certificateIterator(var6);

         while(var7.hasNext()) {
            X509Certificate var8 = (X509Certificate)var7.next();
            byte[] var9 = var8.getSubjectX500Principal().getEncoded();
            if (CertJUtils.byteArraysEqual(var1, var9)) {
               com.rsa.certj.cert.X509Certificate var10 = new com.rsa.certj.cert.X509Certificate(var8.getEncoded(), 0, 0);
               byte[] var11 = new byte[var10.getExtensions().getDERLen(0)];
               var10.getExtensions().getDEREncoding(var11, 0, 0);
               if (CertJUtils.byteArraysEqual(var2, var11)) {
                  var4.add(var8);
               }
            }
         }

         int var13 = var4.size();
         byte[][] var14 = new byte[var13][];

         for(int var15 = 0; var15 < var13; ++var15) {
            var14[var15] = ((X509Certificate)var4.get(var15)).getEncoded();
         }

         return var14;
      } catch (Exception var12) {
         return (byte[][])null;
      }
   }

   private byte[][] nativeSelectPrivateKey(String var1, byte[] var2, java.security.Provider var3) {
      try {
         HardwareIterator var5 = this.store.keyIterator(new PKCS11KeyIteratorParameters(var2, (String)null, var1));
         if (var5.hasNext()) {
            PKCS11Key var6 = (PKCS11Key)var5.next();
            byte[][] var4 = new byte[][]{var6.getManufacturerId(), var6.getKeyId()};
            return var4;
         } else {
            return (byte[][])null;
         }
      } catch (Exception var7) {
         return (byte[][])null;
      }
   }

   private byte[] nativeNextCertificate() {
      try {
         return ((Certificate)this.certIterator.next()).getEncoded();
      } catch (Exception var2) {
         return null;
      }
   }

   private int nativeDeleteCert(byte[] var1, byte[] var2, java.security.Provider var3) {
      try {
         HardwareStore var4 = HardwareStore.getInstance("PKCS11", (JsafeJCEPKCS11)var3);
         HardwareIterator var5 = var4.certificateIterator(new PKCS11CertIteratorParameters((byte[])null, (String)null));

         while(var5.hasNext()) {
            X509Certificate var6 = (X509Certificate)var5.next();
            byte[] var7 = var6.getIssuerX500Principal().getEncoded();
            if (CertJUtils.byteArraysEqual(var1, var7)) {
               byte[] var8 = var6.getSerialNumber().toByteArray();
               if (CertJUtils.byteArraysEqual(var2, var8)) {
                  var5.remove();
                  return 0;
               }
            }
         }

         return 1;
      } catch (Exception var9) {
         return 1;
      }
   }

   private byte[][] nativeNextPrivateKey(HardwareIterator var1) {
      PKCS11Key var2 = (PKCS11Key)var1.next();
      byte[][] var3 = new byte[][]{var2.getManufacturerId(), var2.getKeyId()};
      return var3;
   }

   private int nativeDeletePrivateKey(String var1, byte[] var2) {
      try {
         HardwareIterator var3 = this.store.keyIterator(new PKCS11KeyIteratorParameters(var2, (String)null, var1));

         boolean var4;
         for(var4 = false; var3.hasNext(); var4 = true) {
            var3.next();
            var3.remove();
         }

         return var4 ? 0 : 1;
      } catch (Exception var5) {
         return 1;
      }
   }

   private static String byteArrayToHexString(byte[] var0) {
      StringBuffer var1 = new StringBuffer();
      int var2 = var0.length;

      for(int var3 = 0; var2 > 0; ++var3) {
         int var4 = var0[var3] & 255;
         String var5 = Integer.toHexString(var4);
         if (var5.length() == 1) {
            var1 = var1.append("0");
         }

         var1 = var1.append(var5);
         --var2;
      }

      return var1.toString();
   }

   private void nativeFinalizeSession() {
      this.p11Provider.logout();
   }

   static {
      System.loadLibrary("ncm");
   }

   private final class a extends ProviderImplementation implements DatabaseInterface {
      private final Object b;
      private final Object c;
      private final Object d;
      private final Object e;
      private static final String f = "PKCS11DBProvider.insertCertificate: ";
      private static final String g = "PKCS11DBProvider.insertPrivateKeyByCertificate: ";
      private static final String h = "PKCS11DBProvider.insertPrivateKeyByPublicKey: ";
      private static final String i = "PKCS11DBProvider.selectCertificate: Session is not open.";
      private static final String j = "PKCS11 DB provider does not support ";
      private static final String k = "PKCS11";
      private static final int l = 7;
      private static final int m = 0;
      private static final int n = 1;
      private static final int o = 2;
      private final byte[] p;

      private a(CertJ var2, String var3) throws InvalidParameterException {
         super(var2, var3);
         this.b = new Object();
         this.c = new Object();
         this.d = new Object();
         this.e = new Object();
         this.p = "Message to sign".getBytes();
      }

      public void insertCertificate(com.rsa.certj.cert.Certificate var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("PKCS11DBProvider.insertCertificate: cert should not be null.");
         } else if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.insertCertificate: Session is not open. MES DB Provider is not initialized.");
         } else {
            X500Name var2 = ((com.rsa.certj.cert.X509Certificate)var1).getIssuerName();
            byte[] var3 = ((com.rsa.certj.cert.X509Certificate)var1).getSerialNumber();
            if (var2 != null && var3 != null) {
               try {
                  int var4 = ((com.rsa.certj.cert.X509Certificate)var1).getDERLen(0);
                  if (var4 == 0) {
                     throw new DatabaseException("PKCS11DBProvider.insertCertificate: invalid certificate. Cannot DER-encode certificate.");
                  } else {
                     byte[] var5 = new byte[var4];
                     var4 = ((com.rsa.certj.cert.X509Certificate)var1).getDEREncoding(var5, 0, 0);
                     if (var4 == 0) {
                        throw new DatabaseException("PKCS11DBProvider.insertCertificate: invalid certificate. Cannot DER-encode certificate.");
                     } else {
                        byte[] var6 = this.a(var1);
                        synchronized(this.b) {
                           if (PKCS11DB.this.nativeInsertCertificate(var5, var6) != 0) {
                              throw new DatabaseException("PKCS11DBProvider.insertCertificate: unable to insert certificate");
                           }
                        }
                     }
                  }
               } catch (Exception var10) {
                  throw new DatabaseException("PKCS11DBProvider.insertCertificate: invalid certificate.", var10);
               }
            } else {
               throw new DatabaseException("PKCS11DBProvider.insertCertificate: invalid certificate. IssuerName or SerialNumber is null.");
            }
         }
      }

      private byte[] a(JSAFE_PublicKey var1) {
         try {
            byte[] var2 = null;
            if (var1.getAlgorithm().equalsIgnoreCase("RSA")) {
               var2 = this.a(var1.getKeyData("RSAPublicKey")[0]);
            } else if (var1.getAlgorithm().equalsIgnoreCase("DSA")) {
               byte[][] var3 = var1.getKeyData("DSAPublicKey");
               var2 = this.a(var3[var3.length - 1]);
            } else if (var1.getAlgorithm().equalsIgnoreCase("EC")) {
               var2 = null;
            }

            return var2;
         } catch (Exception var4) {
            return null;
         }
      }

      private byte[] a(com.rsa.certj.cert.Certificate var1) {
         try {
            JSAFE_PublicKey var2 = var1.getSubjectPublicKey("Java");
            return this.a(var2);
         } catch (Exception var3) {
            return null;
         }
      }

      private byte[] a(byte[] var1) {
         try {
            JSAFE_MessageDigest var2 = JSAFE_MessageDigest.getInstance("SHA1", "Java");
            var2.digestInit();
            var2.digestUpdate(var1, 0, var1.length);
            return var2.digestFinal();
         } catch (Exception var3) {
            return null;
         }
      }

      public void insertCRL(CRL var1) throws NotSupportedException {
         throw new NotSupportedException("insertCRL method is not supported by PKCS11DB provider.");
      }

      public void insertPrivateKeyByCertificate(com.rsa.certj.cert.Certificate var1, JSAFE_PrivateKey var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            if (PKCS11DB.this.p11Provider == null) {
               throw new DatabaseException("PKCS11DBProvider.insertPrivateKey: Session is not open.");
            } else {
               try {
                  JSAFE_PublicKey var3 = var1.getSubjectPublicKey("Java");
                  if (!this.a(var3.getAlgorithm(), var2, var3, this.certJ.getRandomObject())) {
                     throw new DatabaseException("PKCS11DBProvider.insertPrivateKey: pairwise check failure.");
                  }
               } catch (Exception var9) {
                  throw new DatabaseException("PKCS11DBProvider.insertPrivateKey: pairwise check failure.");
               }

               synchronized(this.c) {
                  if (this.selectPrivateKeyByCertificate(var1) != null) {
                     return;
                  }
               }

               byte[] var10 = this.a(var2);
               byte[] var4 = this.a(var1);
               if (var4 == null) {
                  throw new DatabaseException("PKCS11DBImplementation.insertPrivateKeyByCertificate: Public key in certificate is null.");
               } else {
                  synchronized(this.c) {
                     if (PKCS11DB.this.nativeInsertPrivateKey(var2.getAlgorithm(), var4, var10) != 0) {
                        throw new DatabaseException("PKCS11DBProvider.insertPrivateKeyByCertificate: unable to insert private key");
                     } else {
                        if (this.isPrivateKeyIteratorSetup()) {
                           this.setupPrivateKeyIterator();
                        }

                     }
                  }
               }
            }
         } else {
            throw new DatabaseException("PKCS11DBImplementation.insertPrivateKeyByCertificate: cert and private key should not be null");
         }
      }

      private byte[] a(JSAFE_PrivateKey var1) throws DatabaseException {
         byte[][] var2 = (byte[][])null;

         try {
            String[] var3 = var1.getSupportedGetFormats();

            int var4;
            for(var4 = 0; var4 < var3.length; ++var4) {
               if (var3[var4].equals("RSAPrivateKeyBER") || var3[var4].equals("DSAPrivateKeyBER") || var3[var4].equals("DSAPrivateKeyX957BER")) {
                  var2 = var1.getKeyData(var3[var4]);
                  break;
               }
            }

            if (var4 == var3.length) {
               throw new DatabaseException("PKCS11DBProvider.insertPrivateKeyByCertificate: cannot get private key BER data.");
            }

            if (var2 == null || var2.length == 0 || var2[0] == null) {
               throw new DatabaseException("PKCS11DBProvider.insertPrivateKeyByCertificate: cannot get private key data");
            }
         } catch (JSAFE_UnimplementedException var5) {
            throw new DatabaseException("PKCS11DBProvider.insertPrivateKeyByCertificate: ", var5);
         }

         return var2[0];
      }

      public void insertPrivateKeyByPublicKey(JSAFE_PublicKey var1, JSAFE_PrivateKey var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            if (PKCS11DB.this.p11Provider == null) {
               throw new DatabaseException("PKCS11DBProvider.insertPrivateKey: Session is not open.");
            } else {
               try {
                  if (!this.a(var1.getAlgorithm(), var2, var1, this.certJ.getRandomObject())) {
                     throw new DatabaseException("PKCS11DBProvider.insertPrivateKey: pairwise check failure.");
                  }
               } catch (Exception var9) {
                  throw new DatabaseException("PKCS11DBProvider.insertPrivateKey: pairwise check failure.");
               }

               synchronized(this.c) {
                  if (this.selectPrivateKeyByPublicKey(var1) != null) {
                     return;
                  }
               }

               byte[] var3 = this.a(var2);
               byte[] var4 = this.a(var1);
               synchronized(this.c) {
                  if (PKCS11DB.this.nativeInsertPrivateKey(var2.getAlgorithm(), var4, var3) != 0) {
                     throw new DatabaseException("PKCS11DBProvider.insertPrivateKeyByPublicKey: unable to insert private key");
                  } else {
                     if (this.isPrivateKeyIteratorSetup()) {
                        this.setupPrivateKeyIterator();
                     }

                  }
               }
            }
         } else {
            throw new DatabaseException("PKCS11DBProvider.insertPrivateKeyByPublicKey: Neither publicKey nor privateKey should be null.");
         }
      }

      public int selectCertificateByIssuerAndSerialNumber(X500Name var1, byte[] var2, Vector var3) throws DatabaseException {
         if (var1 != null && var2 != null) {
            if (PKCS11DB.this.p11Provider == null) {
               throw new DatabaseException("PKCS11DBProvider.selectCertificate: Session is not open.");
            } else {
               byte[] var4 = new byte[var1.getDERLen(0)];

               try {
                  if (var1.getDEREncoding(var4, 0, 0) == 0) {
                     throw new DatabaseException("PKCS11DBProvider: Invalid IssuerName. Cannot DER-encode IssuerName.");
                  }
               } catch (NameException var11) {
                  throw new DatabaseException("PKCS11DBProvider: Invalid IssuerName.", var11);
               }

               byte[][] var5;
               synchronized(this.b) {
                  var5 = PKCS11DB.this.nativeSelectCertByIssuerSerial(var4, var2, PKCS11DB.this.p11Provider);
               }

               if (var5 == null) {
                  return 0;
               } else {
                  int var6 = 0;

                  try {
                     for(int var7 = 0; var7 < var5.length; ++var7) {
                        com.rsa.certj.cert.X509Certificate var8 = new com.rsa.certj.cert.X509Certificate(var5[var7], 0, 0);
                        if (!var3.contains(var8)) {
                           var3.addElement(var8);
                           ++var6;
                        }
                     }

                     return var6;
                  } catch (CertificateException var9) {
                     throw new DatabaseException("PKCS11DBProvider: Invalid certificate.", var9);
                  }
               }
            }
         } else {
            throw new DatabaseException("PKCS11DBProvider.Neither issuerName nor serialNumber should be null.");
         }
      }

      public int selectCertificateBySubject(X500Name var1, Vector var2) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("PKCS11DBProvider.selectCertificateBySubject: subjectName should not be null.");
         } else if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.selectCertificate: Session is not open.");
         } else {
            byte[] var3 = new byte[var1.getDERLen(0)];

            try {
               if (var1.getDEREncoding(var3, 0, 0) == 0) {
                  throw new DatabaseException("PKCS11DBProvider: Invalid SubjectName. Cannot DER-encode SubjectName.");
               }
            } catch (NameException var10) {
               throw new DatabaseException("PKCS11DBProvider: Invalid SubjectName.", var10);
            }

            byte[][] var4;
            synchronized(this.b) {
               var4 = PKCS11DB.this.nativeSelectCertBySubject(var3, PKCS11DB.this.p11Provider);
            }

            if (var4 == null) {
               return 0;
            } else {
               int var5 = 0;

               try {
                  for(int var6 = 0; var6 < var4.length; ++var6) {
                     com.rsa.certj.cert.X509Certificate var7 = new com.rsa.certj.cert.X509Certificate(var4[var6], 0, 0);
                     if (!var2.contains(var7)) {
                        var2.addElement(var7);
                        ++var5;
                     }
                  }

                  return var5;
               } catch (CertificateException var8) {
                  throw new DatabaseException("PKCS11DBProvider: Invalid certificate.", var8);
               }
            }
         }
      }

      public int selectCertificateByExtensions(X500Name var1, X509V3Extensions var2, Vector var3) throws DatabaseException {
         if (var1 != null && var2 != null) {
            if (PKCS11DB.this.p11Provider == null) {
               throw new DatabaseException("PKCS11DBProvider.selectCertificate: Session is not open.");
            } else {
               byte[] var4 = new byte[var1.getDERLen(0)];

               try {
                  if (var1.getDEREncoding(var4, 0, 0) == 0) {
                     throw new DatabaseException("PKCS11DBProvider: Invalid BaseName. Cannot DER-encode BaseName.");
                  }
               } catch (NameException var12) {
                  throw new DatabaseException("PKCS11DBProvider: Invalid BaseName.", var12);
               }

               byte[] var5 = new byte[var2.getDERLen(0)];
               if (var2.getDEREncoding(var5, 0, 0) == 0) {
                  throw new DatabaseException("PKCS11DBProvider: Invalid extensions. Cannot DER-encode extensions.");
               } else {
                  byte[][] var6;
                  synchronized(this.b) {
                     var6 = PKCS11DB.this.nativeSelectCertByExtensions(var4, var5, PKCS11DB.this.p11Provider);
                  }

                  if (var6 == null) {
                     return 0;
                  } else {
                     int var7 = 0;

                     try {
                        for(int var8 = 0; var8 < var6.length; ++var8) {
                           com.rsa.certj.cert.X509Certificate var9 = new com.rsa.certj.cert.X509Certificate(var6[var8], 0, 0);
                           if (!var3.contains(var9)) {
                              var3.addElement(var9);
                              ++var7;
                           }
                        }

                        return var7;
                     } catch (CertificateException var10) {
                        throw new DatabaseException("PKCS11DBProvider: Invalid certificate.", var10);
                     }
                  }
               }
            }
         } else {
            throw new DatabaseException("PKCS11DBProvider.selectCertificateByExtensions: Either baseName or extensions should have a non-null value.");
         }
      }

      public boolean isCertificateIteratorSetup() {
         synchronized(this.d) {
            return PKCS11DB.this.certIterator != null;
         }
      }

      public void setupCertificateIterator() {
         synchronized(this.d) {
            try {
               PKCS11DB.this.certIterator = PKCS11DB.this.store.certificateIterator(new PKCS11CertIteratorParameters((byte[])null, (String)null));
            } catch (Exception var4) {
            }

         }
      }

      public com.rsa.certj.cert.Certificate firstCertificate() throws DatabaseException {
         if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.firstCertificate: Session is not open.");
         } else {
            this.setupCertificateIterator();
            byte[] var1;
            synchronized(this.d) {
               var1 = PKCS11DB.this.nativeNextCertificate();
               if (var1 == null) {
                  PKCS11DB.this.certIterator = null;
                  return null;
               }
            }

            try {
               return new com.rsa.certj.cert.X509Certificate(var1, 0, 0);
            } catch (CertificateException var4) {
               throw new DatabaseException("PKCS11DBProvider.", var4);
            }
         }
      }

      public com.rsa.certj.cert.Certificate nextCertificate() throws DatabaseException {
         if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.nextCertificate: Session is not open.");
         } else if (!this.isCertificateIteratorSetup()) {
            throw new DatabaseException("PKCS11DBProvider.nextCertificate: iterator is not set up.");
         } else {
            byte[] var1 = null;
            synchronized(this.d) {
               if (PKCS11DB.this.certIterator.hasNext()) {
                  var1 = PKCS11DB.this.nativeNextCertificate();
               }

               if (var1 == null) {
                  PKCS11DB.this.certIterator = null;
                  return null;
               }
            }

            try {
               return new com.rsa.certj.cert.X509Certificate(var1, 0, 0);
            } catch (CertificateException var4) {
               throw new DatabaseException("PKCS11DBProvider.", var4);
            }
         }
      }

      public boolean hasMoreCertificates() throws DatabaseException {
         synchronized(this.d) {
            if (!this.isCertificateIteratorSetup()) {
               throw new DatabaseException("Iterator is not set up.");
            } else {
               return PKCS11DB.this.certIterator.hasNext();
            }
         }
      }

      public int selectCRLByIssuerAndTime(X500Name var1, Date var2, Vector var3) throws NotSupportedException {
         throw new NotSupportedException("PKCS11 DB provider does not support selectCRLByIssuerAndTime method.");
      }

      public boolean isCRLIteratorSetup() throws NotSupportedException {
         throw new NotSupportedException("PKCS11 DB provider does not support isCRLIteratorSetup() method");
      }

      public void setupCRLIterator() throws NotSupportedException {
         throw new NotSupportedException("PKCS11 DB provider does not support setupCRLIterator() method");
      }

      public CRL firstCRL() throws NotSupportedException {
         throw new NotSupportedException("PKCS11 DB provider does not support firstCRL() method");
      }

      public CRL nextCRL() throws NotSupportedException {
         throw new NotSupportedException("PKCS11 DB provider does not support nextCRL() method");
      }

      public boolean hasMoreCRLs() throws NotSupportedException {
         throw new NotSupportedException("PKCS11 DB provider does not support hasMoreCRLs() method");
      }

      private JSAFE_PrivateKey a(byte[][] var1) throws DatabaseException {
         JSAFE_PrivateKey var2 = null;

         try {
            if (var1.length == 1) {
               var2 = h.d(var1[0], 0, "Java", (FIPS140Context)this.context.b);
            } else if (var1[0] != null && var1[1] != null && var1[1].length >= 8) {
               if (var1[1][7] == 0) {
                  var2 = h.i("RSA", "PKCS11", this.context.b);
               } else if (var1[1][7] == 1) {
                  var2 = h.i("DSA", "PKCS11", this.context.b);
               } else {
                  if (var1[1][7] != 2) {
                     throw new DatabaseException("PKCS11DBImplementation.selectPrivateKeyByCertificate: Invalid Private key - unknown algorithm: " + var1[1][7]);
                  }

                  var2 = h.i("DH", "PKCS11", this.context.b);
               }

               byte[][] var3 = new byte[][]{var1[0], null};
               byte[] var4 = new byte[var1[1].length - 7 - 1];
               System.arraycopy(var1[1], 8, var4, 0, var4.length);
               var3[1] = var4;
               var2.setKeyData("KeyToken", var3);
            }

            return var2;
         } catch (JSAFE_Exception var5) {
            throw new DatabaseException("Cannot set the private key data.", var5);
         }
      }

      public JSAFE_PrivateKey selectPrivateKeyByCertificate(com.rsa.certj.cert.Certificate var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("PKCS11DBImplementation.selectPrivateKeyByCertificate: cert should not be null.");
         } else if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.selectPrivateKey: Session is not open.");
         } else {
            byte[] var3 = this.a(var1);
            if (var3 == null) {
               throw new DatabaseException("Cert does not contain public key info.");
            } else {
               byte[][] var2;
               synchronized(this.c) {
                  try {
                     var2 = PKCS11DB.this.nativeSelectPrivateKey(var1.getSubjectPublicKey("Java").getAlgorithm(), var3, PKCS11DB.this.p11Provider);
                  } catch (Exception var7) {
                     var2 = (byte[][])null;
                  }
               }

               return var2 == null ? null : this.a(var2);
            }
         }
      }

      public JSAFE_PrivateKey selectPrivateKeyByPublicKey(JSAFE_PublicKey var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("PKCS11DBImplementation.selectPrivateKeyByPublicKey: publicKey should not be null.");
         } else if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.selectPrivateKey: Session is not open.");
         } else {
            byte[] var2 = this.a(var1);
            byte[][] var3;
            synchronized(this.c) {
               var3 = PKCS11DB.this.nativeSelectPrivateKey(var1.getAlgorithm(), var2, PKCS11DB.this.p11Provider);
            }

            return var3 == null ? null : this.a(var3);
         }
      }

      public boolean isPrivateKeyIteratorSetup() {
         synchronized(this.e) {
            return PKCS11DB.this.keyIterator != null;
         }
      }

      public void setupPrivateKeyIterator() {
         synchronized(this.e) {
            try {
               PKCS11DB.this.keyIterator = PKCS11DB.this.store.keyIterator(new PKCS11KeyIteratorParameters((byte[])null, (String)null, "RSA"));
            } catch (HardwareStoreException var4) {
            }

         }
      }

      public JSAFE_PrivateKey firstPrivateKey() throws DatabaseException {
         if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.firstPrivateKey: Session is not open.");
         } else {
            byte[][] var1 = (byte[][])null;
            this.setupPrivateKeyIterator();
            synchronized(this.e) {
               try {
                  var1 = PKCS11DB.this.nativeNextPrivateKey(PKCS11DB.this.keyIterator);
               } catch (Exception var5) {
               }

               if (var1 == null) {
                  PKCS11DB.this.keyIterator = null;
                  return null;
               }
            }

            return this.a(var1);
         }
      }

      public JSAFE_PrivateKey nextPrivateKey() throws DatabaseException {
         if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.nextPrivateKey: Session is not open.");
         } else if (!this.isPrivateKeyIteratorSetup()) {
            throw new DatabaseException("PKCS11DBProvider.nextPrivateKey: iterator is not set up.");
         } else {
            byte[][] var1 = (byte[][])null;
            synchronized(this.e) {
               if (var1 == null) {
                  PKCS11DB.this.keyIterator = null;
                  return null;
               }
            }

            return this.a(var1);
         }
      }

      public boolean hasMorePrivateKeys() throws NotSupportedException {
         if (!this.isPrivateKeyIteratorSetup()) {
            this.setupPrivateKeyIterator();
         }

         return PKCS11DB.this.keyIterator.hasNext();
      }

      public void deleteCertificate(X500Name var1, byte[] var2) throws DatabaseException {
         if (var1 != null && var2 != null) {
            if (PKCS11DB.this.p11Provider == null) {
               throw new DatabaseException("PKCS11DBProvider.deleteCertificate: Session is not open. MES DB provider is not initialized.");
            } else {
               byte[] var3 = new byte[var1.getDERLen(0)];

               try {
                  if (var1.getDEREncoding(var3, 0, 0) == 0) {
                     throw new DatabaseException("PKCS11DBProvider: Invalid IssuerName. Cannot DER-encode Issuer Name.");
                  }
               } catch (NameException var8) {
                  throw new DatabaseException("PKCS11DBProvider: Invalid IssuerName.", var8);
               }

               int var4;
               synchronized(this.b) {
                  var4 = PKCS11DB.this.nativeDeleteCert(var3, var2, PKCS11DB.this.p11Provider);
               }

               if (var4 != 0) {
                  throw new DatabaseException("PKCS11DBProvider: Unable to delete certificate.");
               }
            }
         } else {
            throw new DatabaseException("PKCS11DBImplementation.deleteCertificate: Neither issuerName nor serialNumber should be null.");
         }
      }

      public void deleteCRL(X500Name var1, Date var2) throws NotSupportedException {
         throw new NotSupportedException("deleteCRL method is not supported by PKCS11DB provider.");
      }

      public void deletePrivateKeyByCertificate(com.rsa.certj.cert.Certificate var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("PKCS11DBImplementation.deletePrivateKeyByCertificate: cert should not be null.");
         } else if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.deletePrivateKey: Session is not open.");
         } else {
            int var2;
            try {
               byte[] var3 = this.a(var1);
               if (var3 == null) {
                  throw new DatabaseException("PKCS11DBProvider: cert is missing public Key.");
               }

               synchronized(this.c) {
                  var2 = PKCS11DB.this.nativeDeletePrivateKey(var1.getSubjectPublicKey("Java").getAlgorithm(), var3);
               }
            } catch (CertificateException var7) {
               throw new DatabaseException("PKCS11DBProvider: invalid cert.", var7);
            }

            if (var2 != 0) {
               throw new DatabaseException("PKCS11DBProvider: Unable to delete private key.");
            }
         }
      }

      public void deletePrivateKeyByPublicKey(JSAFE_PublicKey var1) throws DatabaseException {
         if (var1 == null) {
            throw new DatabaseException("PKCS11DBImplementation.deletePrivateKeyByPublicKey: publicKey should not be null.");
         } else if (PKCS11DB.this.p11Provider == null) {
            throw new DatabaseException("PKCS11DBProvider.deletePrivateKey: Session is not open.");
         } else {
            byte[] var2 = this.a(var1);
            int var3;
            synchronized(this.c) {
               var3 = PKCS11DB.this.nativeDeletePrivateKey(var1.getAlgorithm(), var2);
            }

            if (var3 != 0) {
               throw new DatabaseException("PKCS11DB: Unable to delete private key.");
            }
         }
      }

      private boolean a(String var1, JSAFE_PrivateKey var2, JSAFE_PublicKey var3, JSAFE_SecureRandom var4) {
         JSAFE_Signature var5 = null;
         JSAFE_Signature var6 = null;
         String var7 = "SHA1/" + var1 + "/PKCS1Block01Pad";

         boolean var9;
         try {
            var5 = JSAFE_Signature.getInstance(var7, "Java");
            var5.signInit(var2, var4);
            var5.signUpdate(this.p, 0, this.p.length);
            byte[] var8 = var5.signFinal();
            var6 = JSAFE_Signature.getInstance(var7, "Java");
            var6.verifyInit(var3, var4);
            var6.verifyUpdate(this.p, 0, this.p.length);
            var9 = var6.verifyFinal(var8, 0, var8.length);
            return var9;
         } catch (Exception var13) {
            var9 = false;
         } finally {
            if (var5 != null) {
               var5.clearSensitiveData();
            }

            if (var6 != null) {
               var6.clearSensitiveData();
            }

         }

         return var9;
      }

      public void unregister() {
         if (PKCS11DB.this.p11Provider != null) {
            PKCS11DB.this.nativeFinalizeSession();
         }

         if (PKCS11DB.this.sessionFlag) {
            PKCS11DB.this.session.clearSensitiveData();
            PKCS11DB.this.session.closeSession();
         }

         PKCS11DB.this.store = null;
         PKCS11DB.this.p11Provider = null;
      }

      /** @deprecated */
      protected void finalize() {
         this.unregister();
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException {
         this(var2, var3);
      }
   }
}
