package jnr.ffi.provider;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Runtime;

public abstract class FFIProvider {
   public static FFIProvider getSystemProvider() {
      return FFIProvider.SystemProviderSingletonHolder.INSTANCE;
   }

   protected FFIProvider() {
   }

   public abstract Runtime getRuntime();

   public abstract LibraryLoader createLibraryLoader(Class var1);

   private static FFIProvider newInvalidProvider(String message, Throwable cause) {
      return new InvalidProvider(message, cause);
   }

   private static final class SystemProviderSingletonHolder {
      private static final FFIProvider INSTANCE = getInstance();

      static FFIProvider getInstance() {
         String providerName = System.getProperty("jnr.ffi.provider");
         if (providerName == null) {
            Package pkg = FFIProvider.class.getPackage();
            String pkgName = pkg != null && pkg.getName() != null ? pkg.getName() : "jnr.ffi.provider";
            providerName = pkgName + ".jffi.Provider";
         }

         try {
            return (FFIProvider)Class.forName(providerName).newInstance();
         } catch (Throwable var3) {
            return FFIProvider.newInvalidProvider("could not load FFI provider " + providerName, var3);
         }
      }
   }
}
