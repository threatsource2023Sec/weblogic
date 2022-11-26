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
public final class EnhancedFlatFileDB extends Provider {
   File path;
   char[] passphrase;
   byte[] salt;
   static Hashtable accessHash = new Hashtable();

   /** @deprecated */
   public EnhancedFlatFileDB(String var1, File var2, char[] var3, byte[] var4) throws InvalidParameterException {
      super(1, var1);
      if (var2 == null) {
         throw new InvalidParameterException("Error: path should not be null.");
      } else if (var3 == null) {
         throw new InvalidParameterException("Error: passphrase should not be null.");
      } else if (var4 == null) {
         throw new InvalidParameterException("Error: salt should not be null.");
      } else {
         this.path = var2;
         this.passphrase = (char[])var3.clone();
         this.salt = (byte[])var4.clone();
      }
   }

   /** @deprecated */
   public static boolean delete(File var0) throws InvalidParameterException {
      if (var0 == null) {
         throw new InvalidParameterException("Path should not be null.");
      } else if (!var0.exists()) {
         return false;
      } else if (accessHash.get(var0) != null) {
         throw new InvalidParameterException("Cannot delete database currently in use.");
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

   /** @deprecated */
   public synchronized ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new EnhancedFlatFileDBImpl(this, var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException(var3);
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
