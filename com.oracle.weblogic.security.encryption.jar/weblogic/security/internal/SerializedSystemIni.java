package weblogic.security.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessController;
import weblogic.kernel.KernelStatus;
import weblogic.management.DomainDir;
import weblogic.security.Salt;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.internal.encryption.JSafeEncryptionServiceImpl;
import weblogic.security.service.PrivilegedActions;

public final class SerializedSystemIni {
   private static final String PW = "0xccb97558940b82637c8bec3c770f86fa3a391a56";
   private static final int VERSION = 2;
   private static final String FILE = "SerializedSystemIni.dat";
   private static final int SALT_LENGTH = 4;
   private static final boolean DEBUG = false;
   private static final int UPDATE_VERSION = 1;
   private static SerializedSystemIni theInstance = null;
   private static SerializedSystemIni standaloneInstance = null;
   private byte[] salt = null;
   private byte[] encryptedSecretKey = null;
   private byte[] encryptedAESSecretKey = null;
   private static AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static EncryptionService encryptionService = null;

   private static void debug(String msg) {
   }

   private void generateSalt() {
      this.salt = Salt.getRandomBytes(4);
   }

   private void generateEncryptedSecretKey() {
      this.encryptedSecretKey = JSafeEncryptionServiceImpl.getFactory().createEncryptedSecretKey(this.salt, "0xccb97558940b82637c8bec3c770f86fa3a391a56");
   }

   private void generateAESEncryptedSecretKey() {
      this.encryptedAESSecretKey = JSafeEncryptionServiceImpl.getFactory().createAESEncryptedSecretKey(this.salt, "0xccb97558940b82637c8bec3c770f86fa3a391a56");
   }

   private byte[] readBytes(InputStream is) throws IOException {
      int len = is.read();
      if (len < 0) {
         throw new IOException("SerializedSystemIni is empty");
      } else {
         byte[] bytes = new byte[len];
         int readin = 0;

         int justread;
         for(int justread = false; readin < len; readin += justread) {
            justread = is.read(bytes, readin, len - readin);
            if (justread == -1) {
               break;
            }
         }

         return bytes;
      }
   }

   private void write(String path, boolean ensureDomainDir) {
      if (ensureDomainDir) {
         ensureDomainSecurityDirExists();
      }

      FileWriter writer = new FileWriter() {
         public void write(OutputStream os) throws IOException {
            os.write(SerializedSystemIni.this.salt.length);
            os.write(SerializedSystemIni.this.salt);
            os.write(2);
            os.write(SerializedSystemIni.this.encryptedSecretKey.length);
            os.write(SerializedSystemIni.this.encryptedSecretKey);
            os.write(SerializedSystemIni.this.encryptedAESSecretKey.length);
            os.write(SerializedSystemIni.this.encryptedAESSecretKey);
         }
      };
      FileUtils.replace(path, writer);
   }

   private SerializedSystemIni(String path) {
      File f = new File(path);
      if (!f.exists()) {
         this.generateSalt();
         this.generateEncryptedSecretKey();
         this.generateAESEncryptedSecretKey();
         this.write(path, true);
      } else {
         try {
            FileInputStream is = new FileInputStream(f);

            try {
               this.salt = this.readBytes(is);
               int version = is.read();
               if (version != -1) {
                  this.encryptedSecretKey = this.readBytes(is);
                  if (version >= 2) {
                     this.encryptedAESSecretKey = this.readBytes(is);
                  }
               }
            } catch (IOException var13) {
               throw new SerializedSystemIniException(SecurityLogger.getIniCorruptFile(path), var13);
            } finally {
               try {
                  is.close();
               } catch (IOException var12) {
                  throw new SerializedSystemIniException(SecurityLogger.getIniCouldNotClose(path), var12);
               }
            }

            if (this.encryptedSecretKey == null) {
               this.generateEncryptedSecretKey();
               this.generateAESEncryptedSecretKey();
               this.write(path, true);
            }
         } catch (FileNotFoundException var15) {
            throw new SerializedSystemIniException(SecurityLogger.getIniErrorOpeningFile(path), var15);
         }
      }

   }

   private SerializedSystemIni(String path, boolean ensureDomainDir) {
      File f = new File(path);
      if (!f.exists()) {
         if (this.salt == null) {
            this.generateSalt();
         }

         if (this.encryptedSecretKey == null) {
            this.generateEncryptedSecretKey();
         }

         if (this.encryptedAESSecretKey == null) {
            this.generateAESEncryptedSecretKey();
         }

         this.write(path, ensureDomainDir);
      }

   }

   private SerializedSystemIni(String file, int expectedVersion) {
      File f = new File(file);
      if (!f.exists()) {
         throw new SerializedSystemIniException(SecurityLogger.getIniErrorOpeningFile(file));
      } else {
         try {
            FileInputStream is = new FileInputStream(f);

            try {
               this.salt = this.readBytes(is);
               int version = is.read();
               if (version != expectedVersion) {
                  throw new SerializedSystemIniException(SecurityLogger.getIniVersionMismatch("" + version, "" + expectedVersion));
               }

               this.encryptedSecretKey = this.readBytes(is);
            } catch (IOException var14) {
               throw new SerializedSystemIniException(SecurityLogger.getIniCorruptFile(file), var14);
            } finally {
               try {
                  is.close();
               } catch (IOException var13) {
                  throw new SerializedSystemIniException(SecurityLogger.getIniCouldNotClose(file), var13);
               }
            }

         } catch (FileNotFoundException var16) {
            throw new SerializedSystemIniException(SecurityLogger.getIniErrorOpeningFile(file), var16);
         }
      }
   }

   private byte[] getTheSalt() {
      return this.salt;
   }

   private byte[] getTheEncryptedSecretKey() {
      return this.encryptedSecretKey;
   }

   private byte[] getTheAESEncryptedSecretKey() {
      return this.encryptedAESSecretKey;
   }

   private static synchronized void init() {
      if (theInstance == null) {
         theInstance = new SerializedSystemIni(DomainDir.getPathRelativeSecurityDir("SerializedSystemIni.dat"));
      }

   }

   public static boolean exists() {
      File f = new File(DomainDir.getPathRelativeSecurityDir("SerializedSystemIni.dat"));
      return f.exists();
   }

   public static String getPath() {
      return DomainDir.getPathRelativeSecurityDir("SerializedSystemIni.dat");
   }

   public static byte[] getSalt(String path) {
      return (new SerializedSystemIni(path)).getTheSalt();
   }

   public static byte[] getSalt() {
      init();
      return theInstance.getTheSalt();
   }

   public static byte[] getEncryptedSecretKey() {
      init();
      return theInstance.getTheEncryptedSecretKey();
   }

   public static byte[] getEncryptedAESSecretKey() {
      init();
      return theInstance.getTheAESEncryptedSecretKey();
   }

   static EncryptionService getEncryptionService(byte[] salt, byte[] encryptedSecretKey, byte[] encryptedAESSecretKey) {
      return JSafeEncryptionServiceImpl.getFactory().getEncryptionService(salt, "0xccb97558940b82637c8bec3c770f86fa3a391a56", encryptedSecretKey, encryptedAESSecretKey);
   }

   public static EncryptionService getExistingEncryptionService() {
      String domainDir = DomainDir.getRootDir();
      String path = domainDir + File.separator + "security" + File.separator + "SerializedSystemIni.dat";
      File newFile = new File(path);
      if (!newFile.exists()) {
         String oldPath = domainDir + File.separator + "SerializedSystemIni.dat";
         File oldFile = new File(oldPath);
         if (!oldFile.exists()) {
            return null;
         }

         path = oldPath;
      }

      SerializedSystemIni data = new SerializedSystemIni(path);
      return getEncryptionService(data.getTheSalt(), data.getTheEncryptedSecretKey(), data.getTheAESEncryptedSecretKey());
   }

   public static EncryptionService getEncryptionService(String domainDir) {
      String path = domainDir + File.separator + "security" + File.separator + "SerializedSystemIni.dat";
      File newFile = new File(path);
      if (!newFile.exists()) {
         String oldPath = domainDir + File.separator + "SerializedSystemIni.dat";
         File oldFile = new File(oldPath);
         if (oldFile.exists()) {
            path = oldPath;
         }
      }

      SerializedSystemIni data = new SerializedSystemIni(path);
      return getEncryptionService(data.getTheSalt(), data.getTheEncryptedSecretKey(), data.getTheAESEncryptedSecretKey());
   }

   public static EncryptionService getEncryptionService(String fileDir, boolean create) {
      if (KernelStatus.isServer()) {
         throw new IllegalStateException("This method is not supported in server mode.");
      } else {
         String path = fileDir + File.separator + "SerializedSystemIni.dat";
         File file = new File(path);
         if (!file.exists()) {
            if (!create) {
               throw new SerializedSystemIniException(SecurityLogger.getIniFileNotExist(path));
            }

            standaloneInstance = new SerializedSystemIni(path, false);
         }

         return getEncryptionService(standaloneInstance.getTheSalt(), standaloneInstance.getTheEncryptedSecretKey(), standaloneInstance.getTheAESEncryptedSecretKey());
      }
   }

   private static void ensureDomainSecurityDirExists() {
      File newSecDir = new File(DomainDir.getSecurityDir());
      if (!newSecDir.exists()) {
         try {
            newSecDir.mkdir();
         } catch (SecurityException var2) {
         }
      }

   }

   static void upgradeSSI() {
      File newSSI = new File(DomainDir.getPathRelativeSecurityDir("SerializedSystemIni.dat"));
      if (!newSSI.exists()) {
         File oldSSI = new File(DomainDir.getPathRelativeRootDir("SerializedSystemIni.dat"));
         if (oldSSI.exists()) {
            ensureDomainSecurityDirExists();

            try {
               weblogic.utils.FileUtils.copy(oldSSI, newSSI);
               if (oldSSI.canWrite()) {
                  oldSSI.delete();
               }
            } catch (IOException var3) {
            }

         }
      }
   }

   public static EncryptionService getEncryptionService() {
      if (encryptionService == null) {
         encryptionService = getEncryptionService(DomainDir.getRootDir());
      }

      return encryptionService;
   }

   public static void addAESKey() {
      updateDatFileWithAESKey(DomainDir.getPathRelativeSecurityDir("SerializedSystemIni.dat"));
   }

   public static void updateDatFileWithAESKey(String filename) {
      SerializedSystemIni ssi = new SerializedSystemIni(filename, 1);
      ssi.generateAESEncryptedSecretKey();
      ssi.write(filename, false);
   }

   public static void rollbackAESKey() {
      updateDatFileRollbackAESKey(DomainDir.getPathRelativeSecurityDir("SerializedSystemIni.dat"));
   }

   public static void updateDatFileRollbackAESKey(String filename) {
      SerializedSystemIni ssi = new SerializedSystemIni(filename, 2);
      final byte[] salt = ssi.getTheSalt();
      final byte[] encKey = ssi.getTheEncryptedSecretKey();
      FileWriter writer = new FileWriter() {
         public void write(OutputStream os) throws IOException {
            os.write(salt.length);
            os.write(salt);
            os.write(1);
            os.write(encKey.length);
            os.write(encKey);
         }
      };
      FileUtils.replace(filename, writer);
   }
}
