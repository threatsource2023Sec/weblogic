package com.rsa.certj.provider.db;

import com.rsa.certj.CertJ;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Hashtable;

/** @deprecated */
public final class FlatFileDB extends Provider {
   /** @deprecated */
   public static final int DEFAULT_BASE_NAME_LEN = 6;
   /** @deprecated */
   public static final int DEFAULT_PREFIX_LEN = 2;
   static Hashtable accessHash = new Hashtable();
   int baseNameLen;
   int prefixLen;
   File path;
   char[] passphrase;

   /** @deprecated */
   public static boolean create(String var0) throws InvalidParameterException {
      if (var0 == null) {
         throw new InvalidParameterException("Error: pathString should not be null.");
      } else {
         return create(new File(var0));
      }
   }

   /** @deprecated */
   public static boolean create(File var0) throws InvalidParameterException {
      if (var0 == null) {
         throw new InvalidParameterException("Error: path should not be null.");
      } else if (var0.exists()) {
         return false;
      } else {
         var0.mkdirs();
         setupComponentDirectory(var0, "certs");
         setupComponentDirectory(var0, "crls");
         setupComponentDirectory(var0, "privs");
         setupComponentDirectory(var0, "pubs");
         return true;
      }
   }

   /** @deprecated */
   public static boolean delete(String var0) throws InvalidParameterException {
      if (var0 == null) {
         throw new InvalidParameterException("Error: pathString should not be null.");
      } else {
         return delete(new File(var0));
      }
   }

   /** @deprecated */
   public static boolean delete(File var0) throws InvalidParameterException {
      if (var0 == null) {
         throw new InvalidParameterException("Error: path should not be null.");
      } else if (!var0.exists()) {
         return false;
      } else if (accessHash.get(var0) != null) {
         throw new InvalidParameterException("Error: cannot delete database currently in use.");
      } else {
         cleanupDirectory(new File(var0, "certs"), "cer");
         cleanupDirectory(new File(var0, "crls"), "crl");
         cleanupDirectory(new File(var0, "privs"), "prv");
         cleanupDirectory(new File(var0, "pubs"), "pub");
         boolean var1 = var0.delete();
         if (var1) {
            accessHash.remove(var0);
         }

         return var1;
      }
   }

   /** @deprecated */
   public FlatFileDB(String var1, String var2, char[] var3) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("Error: pathString should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("Error: passphrase should not be null.");
      } else {
         this.path = new File(var2);
         this.passphrase = var3;
         this.baseNameLen = 6;
         this.prefixLen = 2;
         this.setupStores();
      }
   }

   /** @deprecated */
   public FlatFileDB(String var1, File var2, char[] var3) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("Error: path should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("Error: passphrase should not be null.");
      } else {
         this.path = var2;
         this.passphrase = var3;
         this.baseNameLen = 6;
         this.prefixLen = 2;
         this.setupStores();
      }
   }

   /** @deprecated */
   public FlatFileDB(String var1, String var2, char[] var3, int var4, int var5) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("Error: pathString should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("Error: passphrase should not be null.");
      } else {
         this.path = new File(var2);
         this.passphrase = var3;
         if (var4 > 0) {
            this.baseNameLen = var4;
         } else {
            this.baseNameLen = 6;
         }

         if (var5 > 0) {
            this.prefixLen = var5;
         } else {
            this.prefixLen = 2;
         }

         this.setupStores();
      }
   }

   /** @deprecated */
   public FlatFileDB(String var1, File var2, char[] var3, int var4, int var5) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("Error: path should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("Error: passphrase should not be null.");
      } else {
         this.path = var2;
         this.passphrase = var3;
         if (var4 > 0) {
            this.baseNameLen = var4;
         } else {
            this.baseNameLen = 6;
         }

         if (var5 > 0) {
            this.prefixLen = var5;
         } else {
            this.prefixLen = 2;
         }

         this.setupStores();
      }
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new EnhancedFlatFileDBImpl(this, var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException("FlatFileDB.instantiate: ", var3);
      }
   }

   private void setupStores() {
      if (!this.path.exists()) {
         this.path.mkdirs();
      }

      setupComponentDirectory(this.path, "certs");
      setupComponentDirectory(this.path, "crls");
      setupComponentDirectory(this.path, "privs");
      setupComponentDirectory(this.path, "pubs");
   }

   private static File setupComponentDirectory(File var0, String var1) {
      File var2 = new File(var0, var1);
      if (var2.exists()) {
         if (!var2.isDirectory()) {
            var2.delete();
            var2.mkdir();
         }
      } else {
         var2.mkdir();
      }

      return var2;
   }

   private static void cleanupDirectory(File var0, String var1) {
      if (var0.exists()) {
         a var2 = new a(var1);
         File[] var3 = var0.listFiles(var2);
         if (var3 != null) {
            for(int var4 = 0; var4 < var3.length; ++var4) {
               var3[var4].delete();
            }
         }

         var0.delete();
      }
   }

   private static class a implements FilenameFilter {
      private String a;

      public a(String var1) {
         this.a = "." + var1;
      }

      public boolean accept(File var1, String var2) {
         return var2.endsWith(this.a);
      }
   }
}
