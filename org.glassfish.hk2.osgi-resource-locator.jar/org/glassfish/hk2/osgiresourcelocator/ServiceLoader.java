package org.glassfish.hk2.osgiresourcelocator;

public abstract class ServiceLoader {
   private static ServiceLoader _me;

   ServiceLoader() {
   }

   public static synchronized void initialize(ServiceLoader singleton) {
      if (singleton == null) {
         throw new NullPointerException("Did you intend to call reset()?");
      } else if (_me != null) {
         throw new IllegalStateException("Already initialzed with [" + _me + "]");
      } else {
         _me = singleton;
      }
   }

   public static synchronized void reset() {
      if (_me == null) {
         throw new IllegalStateException("Not yet initialized");
      } else {
         _me = null;
      }
   }

   public static Iterable lookupProviderInstances(Class serviceClass) {
      return lookupProviderInstances(serviceClass, (ProviderFactory)null);
   }

   public static Iterable lookupProviderInstances(Class serviceClass, ProviderFactory factory) {
      return _me == null ? null : _me.lookupProviderInstances1(serviceClass, factory);
   }

   public static Iterable lookupProviderClasses(Class serviceClass) {
      return _me.lookupProviderClasses1(serviceClass);
   }

   abstract Iterable lookupProviderInstances1(Class var1, ProviderFactory var2);

   abstract Iterable lookupProviderClasses1(Class var1);

   public interface ProviderFactory {
      Object make(Class var1, Class var2) throws Exception;
   }
}
