package com.rsa.certj.pkcs12;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.attributes.FriendlyName;
import com.rsa.certj.cert.attributes.LocalKeyID;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Vector;

/** @deprecated */
public class PKCS12 implements Serializable {
   /** @deprecated */
   public static final int USE_MS_FORMAT = 1;
   /** @deprecated */
   public static final int USE_NS_FORMAT = 2;
   /** @deprecated */
   public static final String[] POSSIBLE_ENCRYPTION_ALGORITHMS = new String[]{"PBE/SHA1/3DES_EDE/CBC/PKCS12V1PBE-1-3", "PBE/SHA1/RC4/PKCS12V1PBE-1-128", "PBE/SHA1/RC4/PKCS12V1PBE-1-40", "PBE/SHA1/3DES_EDE/CBC/PKCS12V1PBE-1-2", "PBE/SHA1/RC2/CBC/PKCS12V1PBE-1-128", "PBE/SHA1/RC2/CBC/PKCS12V1PBE-1-40"};
   /** @deprecated */
   public static final String[] POSSIBLE_DIGEST_ALGORITHMS = new String[]{"SHA1"};
   /** @deprecated */
   public static final int DEFAULT_ITERATIONS = 1;
   /** @deprecated */
   public static final int[] POSSIBLE_OPTIONS = new int[]{1, 2};
   private static final int READ_BUFFER_SIZE = 1024;
   private PFX pfx;
   private byte[] ber;
   private CertJ certJ;

   /** @deprecated */
   protected PKCS12(CertJ var1) {
      this.certJ = var1;
   }

   /** @deprecated */
   public PKCS12(CertJ var1, DatabaseService var2, char[] var3, InputStream var4, int var5) throws PKCS12Exception {
      this(var1);
      this.importPKCS12(var2, var3, (char[])null, var4, var5);
   }

   /** @deprecated */
   public PKCS12(CertJ var1, DatabaseService var2, char[] var3, char[] var4, InputStream var5, int var6) throws PKCS12Exception {
      this(var1);
      this.importPKCS12(var2, var3, var4, var5, var6);
   }

   /** @deprecated */
   public PKCS12(CertJ var1, DatabaseService var2, char[] var3, InputStream var4) throws PKCS12Exception {
      this(var1, var2, var3, var4, -1);
   }

   /** @deprecated */
   public PKCS12(CertJ var1, DatabaseService var2, char[] var3, char[] var4, InputStream var5) throws PKCS12Exception {
      this(var1, var2, var3, var4, var5, -1);
   }

   /** @deprecated */
   public PKCS12(CertJ var1, DatabaseService var2, char[] var3, String var4) throws PKCS12Exception {
      this(var1);
      if (var4 == null) {
         throw new PKCS12Exception("PKCS12.PKCS12: pkcs12File should not be null.");
      } else {
         this.importPKCS12(var2, var3, (char[])null, new File(var4));
      }
   }

   /** @deprecated */
   public PKCS12(CertJ var1, DatabaseService var2, char[] var3, char[] var4, String var5) throws PKCS12Exception {
      this(var1);
      if (var5 == null) {
         throw new PKCS12Exception("PKCS12.PKCS12: pkcs12File should not be null.");
      } else {
         this.importPKCS12(var2, var3, var4, new File(var5));
      }
   }

   /** @deprecated */
   public PKCS12(CertJ var1, DatabaseService var2, char[] var3, File var4) throws PKCS12Exception {
      this(var1);
      this.importPKCS12(var2, var3, (char[])null, var4);
   }

   /** @deprecated */
   public PKCS12(CertJ var1, DatabaseService var2, char[] var3, char[] var4, File var5) throws PKCS12Exception {
      this(var1);
      this.importPKCS12(var2, var3, var4, var5);
   }

   private void importPKCS12(DatabaseService var1, char[] var2, char[] var3, InputStream var4, int var5) throws PKCS12Exception {
      if (var2 == null) {
         throw new PKCS12Exception("PKCS12.importPKCS12: password should not be null.");
      } else if (var4 == null) {
         throw new PKCS12Exception("PKCS12.importPKCS12: inputStream should not be null.");
      } else {
         this.readBER(var4, var5);
         this.pfx = new PFX(this.certJ, var1, var2, var3, this.ber, 0, 0);
      }
   }

   private void importPKCS12(DatabaseService var1, char[] var2, char[] var3, File var4) throws PKCS12Exception {
      if (var4 == null) {
         throw new PKCS12Exception("PKCS12.import: pkcs12File should not be null.");
      } else {
         FileInputStream var5 = null;

         try {
            var5 = new FileInputStream(var4);
            this.importPKCS12(var1, var2, var3, var5, (int)var4.length());
         } catch (FileNotFoundException var14) {
            throw new PKCS12Exception("PKCS12.import: Could not find file " + var4.toString() + ".", var14);
         } finally {
            if (var5 != null) {
               try {
                  var5.close();
               } catch (IOException var13) {
                  throw new PKCS12Exception("PKCS12.import: Could not close file " + var4.toString() + ".", var13);
               }
            }

         }

      }
   }

   private void readBER(InputStream var1, int var2) throws PKCS12Exception {
      try {
         int var3;
         if (var2 > 0) {
            this.ber = new byte[var2];
            var3 = var1.read(this.ber);
            if (var2 != var3) {
               throw new PKCS12Exception("PKCS12.readBER: Not enough bytes read. " + var2 + " bytes expected, " + var3 + " read.");
            }
         } else {
            var3 = 0;
            byte[] var4 = new byte[0];
            byte[] var5 = new byte[1024];

            while(true) {
               int var6 = var1.read(var5);
               if (var6 == -1) {
                  this.ber = var4;
                  break;
               }

               byte[] var7 = new byte[var3 + var6];
               System.arraycopy(var4, 0, var7, 0, var3);
               System.arraycopy(var5, 0, var7, var3, var6);
               var3 += var6;
               var4 = var7;
            }
         }

      } catch (Exception var8) {
         throw new PKCS12Exception("PKCS12.readBER: error occurred while reading from stream " + var1.toString() + ".", var8);
      }
   }

   /** @deprecated */
   public PKCS12(CertJ var1, Certificate[] var2, CRL[] var3, JSAFE_PrivateKey[] var4, X501Attributes[] var5, X501Attributes[] var6, X501Attributes[] var7) throws InvalidParameterException {
      this(var1, var2, var3, var4, var5, var6, var7, (String[])null);
   }

   /** @deprecated */
   public PKCS12(CertJ var1, Certificate[] var2, CRL[] var3, JSAFE_PrivateKey[] var4, X501Attributes[] var5, X501Attributes[] var6, X501Attributes[] var7, String[] var8) throws InvalidParameterException {
      this(var1);
      if (var2 == null && var3 == null && var4 == null) {
         throw new InvalidParameterException("PKCS12.PKCS12: At least one of certs, crls and keys should not be null.");
      } else if (var4 == null || var2 == null || var5 != null && var7 != null) {
         this.pfx = new PFX(var2, var3, var4, var5, var6, var7, var8);
      } else {
         Certificate[] var9 = this.orderCerts(var2, var4);
         if (var5 == null) {
            var5 = new X501Attributes[var9.length];
         } else {
            var5 = reorderCertAttr(var9, var2, var5);
         }

         if (var7 == null) {
            var7 = new X501Attributes[var4.length];
         }

         this.fixAttributes(var9, var4, var5, var7);
         this.pfx = new PFX(var9, var3, var4, var5, var6, var7, var8);
      }
   }

   private static X501Attributes[] reorderCertAttr(Certificate[] var0, Certificate[] var1, X501Attributes[] var2) {
      int var3 = var2.length;
      X501Attributes[] var4 = new X501Attributes[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         for(int var6 = 0; var6 < var3; ++var6) {
            if (var0[var5].hashCode() == var1[var6].hashCode()) {
               var4[var5] = var2[var6];
               break;
            }
         }
      }

      return var4;
   }

   private Certificate[] orderCerts(Certificate[] var1, JSAFE_PrivateKey[] var2) throws InvalidParameterException {
      Certificate[] var6 = new Certificate[var1.length];
      Vector var7 = new Vector();

      int var9;
      for(var9 = 0; var9 < var1.length; ++var9) {
         var7.addElement(var1[var9]);
      }

      int var10;
      int var11;
      try {
         for(var9 = 0; var9 < var2.length; ++var9) {
            String var3 = var2[var9].getAlgorithm();
            var10 = var7.size();
            byte[][] var4;
            byte[][] var5;
            JSAFE_PublicKey var8;
            if (var3.equals("RSA")) {
               var4 = var2[var9].getKeyData("RSAPrivateKeyCRT");
               var11 = 0;

               while(true) {
                  label131: {
                     if (var11 < var10) {
                        var8 = ((Certificate)var7.elementAt(var11)).getSubjectPublicKey("Java");
                        if (!var8.getAlgorithm().equals("RSA")) {
                           break label131;
                        }

                        var5 = var8.getKeyData("RSAPublicKey");
                        if (var4 == null || var5 == null) {
                           throw new InvalidParameterException("Invalid RSA key.");
                        }

                        if (!CertJUtils.byteArraysEqual(var5[0], var4[0]) || !CertJUtils.byteArraysEqual(var5[1], var4[1])) {
                           break label131;
                        }

                        var6[var9] = (Certificate)var7.elementAt(var11);
                        var7.removeElementAt(var11);
                     }

                     if (var11 == var10) {
                        throw new InvalidParameterException("No corresponding cert found.");
                     }
                     break;
                  }

                  ++var11;
               }
            } else if (var3.equals("DSA")) {
               var4 = var2[var9].getKeyData("DSAPrivateKey");
               if (var4 == null || var4[0] == null || var4[2] == null || var4[3] == null) {
                  throw new InvalidParameterException("Invalid DSA private key.");
               }

               BigInteger var12 = new BigInteger(1, var4[0]);
               BigInteger var13 = new BigInteger(1, var4[2]);
               BigInteger var14 = new BigInteger(1, var4[3]);
               BigInteger var15 = var13.modPow(var14, var12);

               for(var11 = 0; var11 < var10; ++var11) {
                  var8 = ((Certificate)var7.elementAt(var11)).getSubjectPublicKey("Java");
                  if (var8.getAlgorithm().equals("DSA")) {
                     var5 = var8.getKeyData("DSAPublicKey");
                     if (var5 == null || var5[3] == null) {
                        throw new InvalidParameterException("Invalid DSA public key.");
                     }

                     BigInteger var16 = new BigInteger(1, var5[3]);
                     if (var16.equals(var15)) {
                        var6[var9] = (Certificate)var7.elementAt(var11);
                        var7.removeElementAt(var11);
                        break;
                     }
                  }
               }

               if (var11 == var10) {
                  throw new InvalidParameterException("No corresponding cert found.");
               }
            }
         }
      } catch (JSAFE_Exception var17) {
         throw new InvalidParameterException("Invalid key.", var17);
      } catch (CertificateException var18) {
         throw new InvalidParameterException("Invalid certificate.", var18);
      }

      var10 = var7.size();
      var11 = var9;

      for(int var19 = 0; var19 < var10; ++var19) {
         var6[var11] = (Certificate)var7.elementAt(var19);
         ++var11;
      }

      return var6;
   }

   /** @deprecated */
   public PKCS12(CertJ var1, X500Name var2, CertPathCtx var3) throws InvalidParameterException, PKCS12Exception {
      this(var1, (X500Name)var2, (CertPathCtx)var3, (String)null);
   }

   /** @deprecated */
   public PKCS12(CertJ var1, X500Name var2, CertPathCtx var3, String var4) throws InvalidParameterException, PKCS12Exception {
      this(var1);
      if (var1 == null) {
         throw new InvalidParameterException("PKCS12.PKCS12: certJ should not be null.");
      } else if (var2 == null) {
         throw new InvalidParameterException("PKCS12.PKCS12: subjectName should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("PKCS12.PKCS12: pathCtx should not be null.");
      } else {
         DatabaseService var5 = var3.getDatabase();
         Vector var6 = new Vector();

         int var7;
         try {
            var7 = var5.selectCertificateBySubject(var2, var6);
         } catch (Exception var20) {
            throw new PKCS12Exception("PKCS12.PKCS12: Finding certificates for the subjectName failed.", var20);
         }

         if (var7 == 0) {
            throw new PKCS12Exception("PKCS12.PKCS12: No certificate found for the subjectName(" + var2.toString() + ").");
         } else {
            Vector var8 = new Vector();
            Vector var9 = new Vector();

            for(int var10 = 0; var10 < var7; ++var10) {
               try {
                  JSAFE_PrivateKey var11 = var5.selectPrivateKeyByCertificate((Certificate)var6.elementAt(var10));
                  if (var11 != null) {
                     var9.addElement(var11);
                     var8.addElement(var6.elementAt(var10));
                  }
               } catch (Exception var21) {
                  throw new PKCS12Exception("PKCS12.PKCS12: Retrieving private key certificate failed.", var21);
               }
            }

            Vector var22 = new Vector();
            Vector var23 = new Vector();

            int var12;
            for(var12 = 0; var12 < var7; ++var12) {
               try {
                  var1.buildCertPath(var3, var8.elementAt(var12), var8, var23, var22, (Vector)null);
               } catch (Exception var19) {
                  throw new PKCS12Exception("PKCS12.PKCS12: Building certification path failed.", var19);
               }
            }

            for(var12 = 0; var12 < var22.size(); ++var12) {
               var8.addElement(var22.elementAt(var12));
            }

            var7 = var8.size();
            X501Attributes[] var24 = new X501Attributes[var7];
            Certificate[] var13 = new Certificate[var7];

            for(int var14 = 0; var14 < var7; ++var14) {
               var13[var14] = (Certificate)var8.elementAt(var14);
            }

            var7 = var23.size();
            CRL[] var25 = null;
            if (var7 != 0) {
               var25 = new CRL[var7];

               for(int var15 = 0; var15 < var7; ++var15) {
                  var25[var15] = (CRL)var23.elementAt(var15);
               }
            }

            var7 = var9.size();
            X501Attributes[] var26 = new X501Attributes[var7];
            JSAFE_PrivateKey[] var16 = new JSAFE_PrivateKey[var7];
            String[] var17 = new String[var7];

            for(int var18 = 0; var18 < var7; ++var18) {
               var16[var18] = (JSAFE_PrivateKey)var9.elementAt(var18);
               var17[var18] = var4;
            }

            this.fixAttributes(var13, var16, var24, var26);
            this.pfx = new PFX(var13, var25, var16, var24, (X501Attributes[])null, var26, var17);
         }
      }
   }

   /** @deprecated */
   public PKCS12(CertJ var1, Certificate var2, CertPathCtx var3) throws InvalidParameterException, PKCS12Exception {
      this(var1, (Certificate)var2, (CertPathCtx)var3, (String)null);
   }

   /** @deprecated */
   public PKCS12(CertJ var1, Certificate var2, CertPathCtx var3, String var4) throws InvalidParameterException, PKCS12Exception {
      this(var1);
      if (var1 == null) {
         throw new InvalidParameterException("PKCS12.PKCS12: cert should not be null.");
      } else if (var2 == null) {
         throw new InvalidParameterException("PKCS12.PKCS12: certJ should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("PKCS12.PKCS12: pathCtx should not be null.");
      } else {
         DatabaseService var5 = var3.getDatabase();
         Vector var6 = new Vector();
         var6.addElement(var2);
         Vector var7 = new Vector();

         JSAFE_PrivateKey var8;
         try {
            var8 = var5.selectPrivateKeyByCertificate(var2);
         } catch (CertJException var18) {
            throw new PKCS12Exception("PKCS12.PKCS12: Finding private key for given certificate failed.", var18);
         }

         if (var8 == null) {
            throw new PKCS12Exception("PKCS12.PKCS12: Matching private key not found.");
         } else {
            Vector var9 = new Vector();

            try {
               var1.buildCertPath(var3, var2, var6, var7, var9, (Vector)null);
            } catch (CertJException var17) {
               throw new PKCS12Exception("PKCS12.PKCS12: Building certification path failed.", var17);
            }

            int var10;
            for(var10 = 0; var10 < var9.size(); ++var10) {
               var6.addElement(var9.elementAt(var10));
            }

            var10 = var6.size();
            X501Attributes[] var11 = new X501Attributes[var10];
            Certificate[] var12 = new Certificate[var10];

            for(int var13 = 0; var13 < var10; ++var13) {
               var12[var13] = (Certificate)var6.elementAt(var13);
            }

            var10 = var7.size();
            CRL[] var19 = null;
            if (var10 != 0) {
               var19 = new CRL[var10];

               for(int var14 = 0; var14 < var10; ++var14) {
                  var19[var14] = (CRL)var7.elementAt(var14);
               }
            }

            X501Attributes[] var20 = new X501Attributes[1];
            JSAFE_PrivateKey[] var15 = new JSAFE_PrivateKey[]{var8};
            String[] var16 = new String[]{var4};
            this.fixAttributes(var12, var15, var11, var20);
            this.pfx = new PFX(var12, var19, var15, var11, (X501Attributes[])null, var20, var16);
         }
      }
   }

   /** @deprecated */
   public void export(String var1, char[] var2, String var3, String var4, int var5, int var6) throws InvalidParameterException, PKCS12Exception {
      if (var1 == null) {
         throw new InvalidParameterException("PKCS12.export: filename should not be null.");
      } else {
         this.export(new File(var1), var2, var3, var4, var5, var6);
      }
   }

   /** @deprecated */
   public void export(File var1, char[] var2, String var3, String var4, int var5, int var6) throws InvalidParameterException, PKCS12Exception {
      if (var1 == null) {
         throw new InvalidParameterException("PKCS12.export: file should not be null.");
      } else {
         FileOutputStream var7 = null;

         try {
            var7 = new FileOutputStream(var1);
            this.export((OutputStream)var7, var2, var3, var4, var5, var6);
         } catch (IOException var16) {
            throw new PKCS12Exception("PKCS12.export: Creation of stream failed.", var16);
         } finally {
            if (var7 != null) {
               try {
                  var7.close();
               } catch (IOException var15) {
                  throw new PKCS12Exception("PKCS12.export: Could not close file.", var15);
               }
            }

         }

      }
   }

   /** @deprecated */
   public void export(String var1, char[] var2, char[] var3, String var4, String var5, int var6, int var7) throws InvalidParameterException, PKCS12Exception {
      if (var1 == null) {
         throw new InvalidParameterException("PKCS12.export: filename should not be null.");
      } else {
         this.export(new File(var1), var2, var3, var4, var5, var6, var7);
      }
   }

   /** @deprecated */
   public void export(File var1, char[] var2, char[] var3, String var4, String var5, int var6, int var7) throws InvalidParameterException, PKCS12Exception {
      if (var1 == null) {
         throw new InvalidParameterException("PKCS12.export: file should not be null.");
      } else {
         FileOutputStream var8 = null;

         try {
            var8 = new FileOutputStream(var1);
            this.export((OutputStream)var8, var2, var3, var4, var5, var6, var7);
         } catch (IOException var17) {
            throw new PKCS12Exception("PKCS12.export: stream IO failed.", var17);
         } finally {
            if (var8 != null) {
               try {
                  var8.close();
               } catch (IOException var16) {
                  throw new PKCS12Exception("PKCS12.export: Could not close file.", var16);
               }
            }

         }

      }
   }

   /** @deprecated */
   public void export(OutputStream var1, char[] var2, String var3, String var4, int var5, int var6) throws InvalidParameterException, PKCS12Exception {
      this.export((OutputStream)var1, var2, (char[])null, var3, var4, var5, var6);
   }

   /** @deprecated */
   public void export(OutputStream var1, char[] var2, char[] var3, String var4, String var5, int var6, int var7) throws InvalidParameterException, PKCS12Exception {
      if (var1 == null) {
         throw new InvalidParameterException("PKCS12.export: stream should not be null.");
      } else if (this.certJ == null) {
         throw new PKCS12Exception("PKCS12.export: Object not initialized with certJ.");
      } else if (this.pfx == null) {
         throw new PKCS12Exception("PKCS12.export: Object not initialized with pfx.");
      } else if (var2 == null) {
         throw new InvalidParameterException("PKCS12.export: password can not be null.");
      } else {
         if (var4 == null) {
            var4 = POSSIBLE_ENCRYPTION_ALGORITHMS[0];
         }

         if (var5 == null) {
            var5 = POSSIBLE_DIGEST_ALGORITHMS[0];
         }

         if (var6 <= 0) {
            var6 = 1;
         }

         if (var7 == 0) {
            var7 = POSSIBLE_OPTIONS[0];
         } else if (!this.validOption(var7)) {
            throw new InvalidParameterException("PKCS12.export: option is invalid.");
         }

         this.ber = this.pfx.a(this.certJ, var2, var3, var4, var5, var6, var7);

         try {
            var1.write(this.ber);
            var1.close();
         } catch (IOException var9) {
            throw new PKCS12Exception("PKCS12.export: Writing to stream failed.", var9);
         }
      }
   }

   /** @deprecated */
   public Certificate[] getCertificates() {
      if (this.pfx != null && this.pfx.b() != null) {
         Vector var1 = this.pfx.b().a();
         return (Certificate[])var1.toArray(new Certificate[var1.size()]);
      } else {
         return null;
      }
   }

   /** @deprecated */
   public CRL[] getCrls() {
      if (this.pfx != null && this.pfx.b() != null) {
         Vector var1 = this.pfx.b().b();
         return (CRL[])var1.toArray(new CRL[var1.size()]);
      } else {
         return null;
      }
   }

   /** @deprecated */
   public JSAFE_PrivateKey[] getKeys() {
      if (this.pfx != null && this.pfx.b() != null) {
         Vector var1 = this.pfx.b().c();
         return (JSAFE_PrivateKey[])var1.toArray(new JSAFE_PrivateKey[var1.size()]);
      } else {
         return null;
      }
   }

   /** @deprecated */
   public X501Attributes[] getKeysAttributes() {
      if (this.pfx != null && this.pfx.b() != null) {
         Vector var1 = this.pfx.b().e();
         return (X501Attributes[])var1.toArray(new X501Attributes[var1.size()]);
      } else {
         return null;
      }
   }

   /** @deprecated */
   public X501Attributes[] getCertsAttributes() {
      if (this.pfx != null && this.pfx.b() != null) {
         Vector var1 = this.pfx.b().d();
         return (X501Attributes[])var1.toArray(new X501Attributes[var1.size()]);
      } else {
         return null;
      }
   }

   /** @deprecated */
   public X501Attributes[] getCRLsAttributes() {
      if (this.pfx != null && this.pfx.b() != null) {
         Vector var1 = this.pfx.b().f();
         return (X501Attributes[])var1.toArray(new X501Attributes[var1.size()]);
      } else {
         return null;
      }
   }

   private void fixAttributes(Certificate[] var1, JSAFE_PrivateKey[] var2, X501Attributes[] var3, X501Attributes[] var4) {
      int var5;
      for(var5 = 0; var5 < var2.length; ++var5) {
         if (var3[var5] == null) {
            var3[var5] = new X501Attributes();
         }

         if (var4[var5] == null) {
            var4[var5] = new X501Attributes();
         }

         byte[] var6 = this.createLocalId(var5 + 1);
         LocalKeyID var7 = new LocalKeyID(var6, 0, var6.length);
         FriendlyName var8 = new FriendlyName(((X509Certificate)var1[var5]).getSubjectName().toString());
         if (var3[var5].getAttributeByType(3) == null) {
            var3[var5].addAttribute(var8);
         }

         if (var3[var5].getAttributeByType(4) == null) {
            var3[var5].addAttribute(var7);
         }

         if (var4[var5].getAttributeByType(3) == null) {
            var4[var5].addAttribute(var8);
         }

         if (var4[var5].getAttributeByType(4) == null) {
            var4[var5].addAttribute(var7);
         }
      }

      for(var5 = var2.length; var5 < var1.length; ++var5) {
         FriendlyName var9 = new FriendlyName(((X509Certificate)var1[var5]).getSubjectName().toString());
         if (var3[var5] == null) {
            var3[var5] = new X501Attributes();
         }

         if (var3[var5].getAttributeByType(3) == null) {
            var3[var5].addAttribute(var9);
         }
      }

   }

   private byte[] createLocalId(int var1) {
      return BigInteger.valueOf((long)var1).toByteArray();
   }

   private boolean validOption(int var1) {
      for(int var2 = 0; var2 < POSSIBLE_OPTIONS.length; ++var2) {
         if (var1 == POSSIBLE_OPTIONS[var2]) {
            return true;
         }
      }

      return false;
   }
}
