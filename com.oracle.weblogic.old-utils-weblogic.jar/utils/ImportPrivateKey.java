package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import weblogic.security.utils.SSLCertUtility;
import weblogic.security.utils.SSLContextWrapper;

public class ImportPrivateKey {
   private static void printError(String message) {
      printUsage(message, true);
   }

   private static void printUsage(String message) {
      printUsage(message, false);
   }

   private static void printUsage(String message, boolean brief) {
      if (message != null) {
         System.out.println();
         System.out.println(message);
      }

      System.out.println("\nUsage: java utils.ImportPrivateKey \n\t-certfile <cert_file> -keyfile <private_key_file>\n\t[-keyfilepass <private_key_password>]\n\t-keystore <keystore> -storepass <storepass> [-storetype <storetype>]\n\t-alias <alias> [-keypass <keypass>]\n\t[-help]\n");
      if (!brief) {
         System.out.println("Where:\n-certfile, -keyfile, -keyfilepass\n\tcertificate and private key files, and the private key password\n\n-keystore, -storepass, -storetype\n\tkeystore file name, password, and type. The default type is JKS.\n\n-alias -keypass\n\talias and password of the keystore key entry where the private key\n\tand the public certificate will be imported. When the key entry\n\tpassword is not specified, the private key password will be used\n\tinstead, or when it is not specified either, the keystore password.\n");
      }
   }

   public static void main(String[] args) {
      boolean imported = false;

      try {
         imported = importKey(args);
      } catch (Throwable var3) {
         var3.printStackTrace();
         printError("Caught unexpected exception while trying to import the key");
      }

      System.exit(imported ? 0 : 1);
   }

   public static boolean importKey(String[] args) {
      String ksFile = null;
      String ksPwd = null;
      String keyAlias = null;
      String keyPwd = null;
      String certFile = null;
      String keyFile = null;
      String keyFilePwd = null;
      String ksType = KeyStore.getDefaultType();
      if (args.length == 0) {
         printUsage((String)null);
         return false;
      } else {
         try {
            for(int i = 0; i < args.length; ++i) {
               if (args[i].equals("-keystore")) {
                  ++i;
                  ksFile = args[i];
               } else if (args[i].equals("-storepass")) {
                  ++i;
                  ksPwd = args[i];
               } else if (args[i].equals("-storetype")) {
                  ++i;
                  ksType = args[i];
               } else if (args[i].equals("-keypass")) {
                  ++i;
                  keyPwd = args[i];
               } else if (args[i].equals("-alias")) {
                  ++i;
                  keyAlias = args[i];
               } else if (args[i].equals("-keyfile")) {
                  ++i;
                  keyFile = args[i];
               } else if (args[i].equals("-keyfilepass")) {
                  ++i;
                  keyFilePwd = args[i];
               } else {
                  if (!args[i].equals("-certfile")) {
                     if (args[i].equals("-help")) {
                        printUsage((String)null);
                        return true;
                     }

                     if ((i != 0 || args.length != 6) && args.length != 7) {
                        printError("Unknown flag: " + args[i]);
                        return false;
                     }

                     if (args.length == 7) {
                        ksType = args[6];
                     }

                     return importKey(args[0], args[1], args[2], args[3], args[4], args[5], ksType);
                  }

                  ++i;
                  certFile = args[i];
               }
            }
         } catch (ArrayIndexOutOfBoundsException var10) {
            printError("Please specify value for " + args[args.length - 1]);
            return false;
         }

         if (keyFile == null) {
            printError("Please specify the private key file");
            return false;
         } else if (certFile == null) {
            printError("Please specify the public certificate file");
            return false;
         } else if (ksFile == null) {
            printError("Please specify the key store file name");
            return false;
         } else if (ksPwd == null) {
            printError("Please specify the key store password");
            return false;
         } else if (keyAlias == null) {
            printError("Please specify the alias of the key entry");
            return false;
         } else {
            if (keyPwd == null) {
               System.out.println("No password was specified for the key entry");
               if (keyFilePwd != null) {
                  System.out.println("Key file password will be used");
                  keyPwd = keyFilePwd;
               } else {
                  System.out.println("Keystore password will be used");
                  keyPwd = ksPwd;
               }
            }

            return importKey(ksFile, ksPwd, ksType, keyAlias, keyPwd, certFile, keyFile, keyFilePwd);
         }
      }
   }

   public static boolean importKey(String ksFile, String ksPwd, String keyAlias, String keyPwd, String certFile, String keyFile) {
      return importKey(ksFile, ksPwd, KeyStore.getDefaultType(), keyAlias, keyPwd, certFile, keyFile, keyPwd);
   }

   public static boolean importKey(String ksFile, String ksPwd, String keyAlias, String keyPwd, String certFile, String keyFile, String ksType) {
      return importKey(ksFile, ksPwd, ksType, keyAlias, keyPwd, certFile, keyFile, keyPwd);
   }

   public static boolean importKey(String ksFile, String ksPwd, String ksType, String keyAlias, String keyPwd, String certFile, String keyFile, String keyFilePwd) {
      try {
         char[] storePwd = ksPwd.toCharArray();
         char[] storeKeyPwd = keyPwd.toCharArray();
         char[] fileKeyPwd = keyFilePwd == null ? new char[0] : keyFilePwd.toCharArray();
         SSLContextWrapper sslContext = SSLContextWrapper.getImportInstance();

         PrivateKey key;
         try {
            key = sslContext.inputPrivateKey(new FileInputStream(keyFile), fileKeyPwd);
         } catch (FileNotFoundException var29) {
            System.out.println("\nCannot find the key file " + keyFile + "\n");
            return false;
         } catch (SecurityException var30) {
            System.out.println("\nDo not have permission to read key file " + keyFile + "\n");
            return false;
         } catch (KeyManagementException var31) {
            System.out.println("\nCannot construct PrivateKey from file " + keyFile + "\nPlease make sure the file is valid and the key password is correct\n");
            return false;
         }

         X509Certificate[] chain;
         try {
            chain = SSLCertUtility.inputCertificateChain(sslContext, new FileInputStream(certFile));
         } catch (FileNotFoundException var26) {
            System.out.println("\nCannot find the certificate file " + certFile + "\n");
            return false;
         } catch (SecurityException var27) {
            System.out.println("\nDo not have permission to read certificate file " + certFile + "\n");
            return false;
         } catch (KeyManagementException var28) {
            System.out.println("\nCannot construct Certificate from file " + certFile + "\nPlease make sure the file is in a valid format\n");
            return false;
         }

         FileInputStream ksIn = null;

         try {
            ksIn = new FileInputStream(ksFile);
         } catch (FileNotFoundException var24) {
         } catch (SecurityException var25) {
            System.out.println("\nDo not have permission to read the keystore file " + ksFile + "\n");
            return false;
         }

         KeyStore ks = null;

         try {
            ks = KeyStore.getInstance(ksType);
            ks.load(ksIn, storePwd);
         } catch (KeyStoreException var21) {
            System.out.println("\nThe requested keystore type " + ksType + " is not available\n");
            return false;
         } catch (IOException var22) {
            System.out.println("\nCannot load keystore from file " + ksFile + "\nPlease make sure the file is a keystore, and the keystore password is correct\n");
            return false;
         } catch (CertificateException var23) {
            System.out.println("\nCannot load certificates from the keystore file " + ksFile + "\n");
            return false;
         }

         try {
            ks.setKeyEntry(keyAlias, key, storeKeyPwd, chain);
            ks.store(new FileOutputStream(ksFile), storePwd);
            System.out.println("\nImported private key " + keyFile + " and certificate " + certFile + "\ninto" + (ksIn == null ? " a new" : "") + " keystore " + ksFile + " of type " + ksType + " under alias " + keyAlias);
            return true;
         } catch (FileNotFoundException var17) {
            System.out.println("\nCannot open for writing keystore file " + ksFile + "\nPlease make sure the file is not write protected\n");
            return false;
         } catch (SecurityException var18) {
            System.out.println("\nDo not have permission to write to the keystore file " + ksFile);
            return false;
         } catch (KeyStoreException var19) {
            System.out.println("\nCannot import the key and the certificate to the keystore\n" + var19.getMessage() + "\n");
            return false;
         } catch (CertificateException var20) {
            System.out.println("\nCannot write certificates to the keystore file " + ksFile + "\n");
            return false;
         }
      } catch (Throwable var32) {
         System.out.println("\nFailed to import private key and certificate to the keystore file " + ksFile + "\n" + var32.getMessage() + "\n");
         var32.printStackTrace();
         return false;
      }
   }
}
