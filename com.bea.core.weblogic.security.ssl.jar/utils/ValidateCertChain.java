package utils;

import com.rsa.certj.CertJ;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.Provider;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.BasicConstraints;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.pkcs12.PKCS12;
import com.rsa.certj.pkcs12.PKCS12Exception;
import com.rsa.certj.provider.db.MemoryDB;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Date;
import java.util.Enumeration;
import weblogic.security.SecurityLogger;
import weblogic.security.utils.SSLCertUtility;
import weblogic.security.utils.SSLContextWrapper;

public class ValidateCertChain {
   private boolean outputEnabled = false;
   private boolean debugEnabled = Boolean.getBoolean("debug");
   private boolean constraintsIssues = false;
   private boolean chainIncomplete = false;

   public void setOutputEnabled(boolean enabled) {
      this.outputEnabled = enabled;
   }

   public static void main(String[] args) {
      ValidateCertChain validator = new ValidateCertChain();
      validator.setOutputEnabled(true);
      if (validator.processCommandLine(args)) {
         System.exit(0);
      } else {
         System.exit(1);
      }

   }

   public boolean processCommandLine(String[] args) {
      boolean returnValue = false;
      if (args != null && args.length != 0) {
         try {
            if ((args[0].equalsIgnoreCase("-file") || args[0].equalsIgnoreCase("-pem")) && args.length == 2) {
               this.printOut("The " + args[0] + " option is deprecated. Use -pkcs12store or -jks instead.");
               returnValue = this.processPemFile(args[1]);
            } else if (!args[0].equalsIgnoreCase("-jks") || args.length != 3 && args.length != 4) {
               if (args[0].equalsIgnoreCase("-pkcs12store") && args.length == 2) {
                  returnValue = this.processPkcs12(args[1]);
               } else {
                  if (!args[0].equalsIgnoreCase("-pkcs12file") || args.length != 3) {
                     this.usage();
                     return returnValue;
                  }

                  this.printOut("The " + args[0] + " option is deprecated. Use -pkcs12store or -jks instead.");
                  returnValue = this.processPkcs12UsingCertJ(args[1], args[2]);
               }
            } else {
               returnValue = this.processJksKeyStore(args[1], args[2], args.length == 4 ? args[3] : null);
            }
         } catch (Exception var4) {
            this.printOut(var4);
         }

         if (returnValue) {
            this.printOut("Certificate chain appears valid");
         } else {
            this.printOut("Certificate chain is invalid");
         }

         return returnValue;
      } else {
         this.usage();
         return returnValue;
      }
   }

   public boolean processPemFile(String pemFile) {
      boolean returnValue = false;

      try {
         returnValue = this.validateCertChain(this.convertChain((Certificate[])SSLCertUtility.inputCertificateChain(SSLContextWrapper.getInstance(), new FileInputStream(pemFile))));
      } catch (FileNotFoundException var4) {
         this.printOut("File not found: " + pemFile);
      } catch (IOException var5) {
         this.printOut("Failure processing: " + pemFile);
         this.printOut((Exception)var5);
      } catch (KeyManagementException var6) {
         this.printOut("Failure processing: " + pemFile);
         this.printOut((Exception)var6);
      }

      return returnValue;
   }

   public boolean processJksKeyStore(String alias, String storeFile, String storePass) {
      boolean returnValue = false;

      try {
         KeyStore ks = KeyStore.getInstance("JKS");
         ks.load(new FileInputStream(storeFile), storePass == null ? null : storePass.toCharArray());
         returnValue = this.validateCertChain(this.convertChain(ks.getCertificateChain(alias)));
      } catch (FileNotFoundException var6) {
         this.printOut("File not found: " + storeFile);
      } catch (Exception var7) {
         this.printOut("Failure processing: " + storeFile);
         this.printOut(var7);
      }

      return returnValue;
   }

   public boolean processPkcs12(String pkcs12File) {
      boolean returnValue = false;

      try {
         KeyStore ks = KeyStore.getInstance("PKCS12");
         ks.load(new FileInputStream(pkcs12File), (char[])null);
         String alias = null;
         Enumeration enum_ = ks.aliases();

         while(enum_.hasMoreElements()) {
            alias = (String)enum_.nextElement();
            if (ks.isKeyEntry(alias)) {
               break;
            }
         }

         if (alias == null) {
            this.printOut("No alias for certs/key found");
         } else {
            returnValue = this.validateCertChain(this.convertChain(ks.getCertificateChain(alias)));
         }
      } catch (FileNotFoundException var6) {
         this.printOut("File not found: " + pkcs12File);
      } catch (KeyStoreException var7) {
         if (var7.getMessage().equalsIgnoreCase("PKCS12 not found")) {
            this.printOut("PKCS12 keystore not supported, try using -pkcs12file");
         } else {
            this.printOut("Failure processing: " + pkcs12File);
            this.printOut((Exception)var7);
         }
      } catch (Exception var8) {
         this.printOut("Failure processing: " + pkcs12File);
         this.printOut(var8);
      }

      return returnValue;
   }

   public boolean processPkcs12UsingCertJ(String pkcs12File, String password) {
      boolean returnValue = false;

      try {
         Provider[] providers = new Provider[]{new MemoryDB("In-Memory Provider")};
         CertJ certJ = new CertJ(providers);
         DatabaseService dbService = (DatabaseService)certJ.bindServices(1);
         PKCS12 p12 = new PKCS12(certJ, dbService, password.toCharArray(), pkcs12File);
         returnValue = this.validateCertChain(this.convertChain(p12.getCertificates()));
      } catch (PKCS12Exception var8) {
         this.printOut("Failure processing: " + pkcs12File);
         this.printOut((Exception)var8);
      } catch (Exception var9) {
         this.printOut("Failure processing: " + pkcs12File);
         this.printOut(var9);
      }

      return returnValue;
   }

   private X509Certificate[] convertChain(com.rsa.certj.cert.Certificate[] certChain) {
      if (certChain != null && certChain.length != 0) {
         try {
            X509Certificate[] newChain = new X509Certificate[certChain.length];

            for(int i = 0; i < certChain.length; ++i) {
               newChain[i] = (X509Certificate)certChain[i];
            }

            return newChain;
         } catch (ClassCastException var4) {
            this.printOut("Problem converting certificate chain");
            this.printOut((Exception)var4);
            return null;
         }
      } else {
         return null;
      }
   }

   private X509Certificate[] convertChain(Certificate[] certChain) {
      if (certChain != null && certChain.length != 0) {
         try {
            X509Certificate[] newChain = new X509Certificate[certChain.length];

            for(int i = 0; i < certChain.length; ++i) {
               newChain[i] = new X509Certificate(certChain[i].getEncoded(), 0, 0);
            }

            return newChain;
         } catch (CertificateException var4) {
            this.printOut("Problem converting certificate chain");
            this.printOut((Exception)var4);
         } catch (CertificateEncodingException var5) {
            this.printOut("Problem converting certificate chain");
            this.printOut((Exception)var5);
         }

         return null;
      } else {
         return null;
      }
   }

   public static void validateServerCertChain(Certificate[] certChain) {
      ValidateCertChain validator = new ValidateCertChain();
      validator.validateCertChain(validator.convertChain(certChain));
      validator.logForServer();
   }

   public void logForServer() {
      if (this.constraintsIssues) {
         SecurityLogger.logCertificateChainConstraints();
      } else if (this.chainIncomplete) {
         SecurityLogger.logCertificateChainIncompleteConstraintsNotChecked();
      }

   }

   public boolean validateCertChain(X509Certificate[] certChain) {
      if (certChain != null && certChain.length != 0) {
         boolean returnValue = true;
         int remainingPathLen = certChain.length - 1;
         Date now = new Date();

         for(int i = 0; i < certChain.length; ++i) {
            this.printOut("Cert[" + i + "]: " + certChain[i].getSubjectName());
            if (!certChain[i].checkValidityDate(now)) {
               returnValue = false;
               this.printOut("     Validity date check failed");
            }

            if (i == 0 && !this.verifyEndEntity(certChain[0])) {
               this.printOut("First cert in chain is not an end entity\nthis doesn't conform to TLS V1.0 and may be rejected");
            }

            if (i < certChain.length - 1) {
               if (!this.verifyIssuedBy(certChain[i], certChain[i + 1], i)) {
                  returnValue = false;
               }
            } else if (i == certChain.length - 1 && !this.verifySelfSignedCert(certChain[i], i)) {
               returnValue = false;
            }
         }

         return returnValue;
      } else {
         this.printOut("No certificates found");
         return false;
      }
   }

   private boolean verifyIssuedBy(X509Certificate cert, X509Certificate issuer, int depth) {
      boolean returnValue = true;
      if (!cert.getIssuerName().equals(issuer.getSubjectName())) {
         this.printOut("Issuer DN from certificate, doesn't match subjectDN from the issuer certificate");
         this.printOut("     Expected DN: " + cert.getIssuerName());
         this.printOut("       Actual DN: " + issuer.getSubjectName());
         returnValue = false;
      }

      if (!this.verifyCAExtensions(issuer, depth)) {
         returnValue = false;
      }

      return returnValue;
   }

   public boolean verifySelfSignedCert(X509Certificate cert, int depth) {
      boolean returnValue = true;
      boolean selfSigned = true;
      if (!cert.getIssuerName().equals(cert.getSubjectName())) {
         this.chainIncomplete = true;
         this.printOut("Certificate chain is incomplete, can't confirm the entire chain is valid");
         selfSigned = false;
         if (depth == 0) {
            return returnValue;
         }
      }

      if (depth == 0 && !this.verifyCAExtensions(cert, depth)) {
         returnValue = false;
      }

      if (selfSigned) {
      }

      return returnValue;
   }

   public boolean verifyCAExtensions(X509Certificate cert, int depth) {
      boolean returnValue = true;
      boolean basicConstraintsChecked = false;
      boolean keyUsageChecked = false;
      int version = cert.getVersion();
      if (version != 0 && version != 1) {
         X509V3Extensions extensions = cert.getExtensions();
         if (extensions != null) {
            try {
               for(int i = 0; i < extensions.getExtensionCount(); ++i) {
                  X509V3Extension extension = extensions.getExtensionByIndex(i);
                  if (extension instanceof BasicConstraints) {
                     basicConstraintsChecked = true;
                     BasicConstraints basic = (BasicConstraints)extension;
                     if (!basic.getCA() || !basic.getCriticality()) {
                        this.constraintsIssues = true;
                        this.printOut("CA cert not marked with critical BasicConstraint indicating it is a CA");
                        returnValue = false;
                     }

                     if (depth != -1) {
                        int pathLen = basic.getPathLen();
                        if (pathLen != -1 && depth > pathLen) {
                           this.constraintsIssues = true;
                           this.printOut("PathLength constraint exceeded, constraint = " + pathLen + ", current = " + depth);
                           returnValue = false;
                        }
                     }
                  }
               }
            } catch (CertificateException var12) {
               this.printOut("Failed getting extensions");
               this.printOut((Exception)var12);
            }
         }

         if (!basicConstraintsChecked) {
            this.constraintsIssues = true;
            this.printOut("CA cert not marked with critical BasicConstraint indicating it is a CA");
            returnValue = false;
         }

         return returnValue;
      } else {
         this.printOut("CA is version " + (version + 1) + ", BasicConstraints extension will not be present which is valid for that version");
         return returnValue;
      }
   }

   public boolean verifyEndEntity(X509Certificate cert) {
      return true;
   }

   public void usage() {
      String usage = "\nUsage:\n\t java utils.ValidateCertChain -file pemcertificatefilename (deprecated, use -pkcs12store or -jks)\n\t java utils.ValidateCertChain -pem pemcertificatefilename (deprecated, use -pkcs12store or -jks)\n\t java utils.ValidateCertChain -pkcs12store pkcs12storefilename\n\t java utils.ValidateCertChain -pkcs12file pkcs12filename password (deprecated, use -pkcs12store or -jks)\n\t java utils.ValidateCertChain -jks alias storefilename [storePass]";
      this.printOut(usage);
   }

   private void printOut(String outputInfo) {
      if (this.outputEnabled) {
         System.out.println(outputInfo);
      }

   }

   private void printOut(Exception exc) {
      if (this.outputEnabled && this.debugEnabled) {
         exc.printStackTrace();
      }

   }
}
