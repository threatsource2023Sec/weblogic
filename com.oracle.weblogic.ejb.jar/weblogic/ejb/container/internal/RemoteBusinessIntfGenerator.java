package weblogic.ejb.container.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import weblogic.ejb.container.deployer.DownloadRemoteBizIntfClassLoader;
import weblogic.ejb.container.deployer.RemoteBizIntfClassLoader;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.GenericClassLoader;

/** @deprecated */
@Deprecated
public final class RemoteBusinessIntfGenerator {
   private String remoteInterfaceName;
   private Class businessInterface;
   private Class generatedRemoteInterface;
   private GenericClassLoader cl;
   private static final ReferenceQueue referenceQueue = new ReferenceQueue();
   private static final Map classLoaderCache = new HashMap();
   private static boolean debug = Boolean.getBoolean("weblogic.ejb.enhancement.debug");

   public RemoteBusinessIntfGenerator(String remoteInterfaceName, Class businessInterface, GenericClassLoader cl) {
      this.remoteInterfaceName = remoteInterfaceName;
      this.businessInterface = businessInterface;
      this.cl = cl;
   }

   public Class getGeneratedRemoteInterface() {
      return this.generatedRemoteInterface;
   }

   public Class generateRemoteInterface() {
      boolean createDownloadLoader = false;
      Method[] ms = this.businessInterface.getMethods();
      Method[] var3 = ms;
      int var4 = ms.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (!m.toGenericString().equals(m.toString()) && this.isGenericInterfaceDeclared(this.businessInterface)) {
            createDownloadLoader = true;
            break;
         }
      }

      String biName = this.businessInterface.getName();
      Class enhancedClass;
      if (!createDownloadLoader) {
         try {
            RemoteBizIntfClassLoader enhanceCL = this.getEnhanceLoader();
            enhancedClass = enhanceCL.loadClass(biName);
            this.debug("loaded by enhanceCL:" + enhancedClass.getName());
            if (enhancedClass.getName().equals(this.remoteInterfaceName)) {
               this.generatedRemoteInterface = enhancedClass;
               this.debug("get generatedRemoteInterface:" + this.generatedRemoteInterface + " by enhancing business interface on RemoteBizIntfClassLoader: \n" + enhanceCL);
            } else {
               this.generatedRemoteInterface = enhanceCL.loadClass(this.remoteInterfaceName);
               this.debug("get generatedRemoteInterface:" + this.generatedRemoteInterface + " by loading it directly on RemoteBizIntfClassLoader: \n" + enhanceCL);
            }

            return this.generatedRemoteInterface;
         } catch (ClassNotFoundException var7) {
            throw new AssertionError(var7);
         }
      } else {
         DownloadRemoteBizIntfClassLoader cpp = null;

         try {
            cpp = new DownloadRemoteBizIntfClassLoader(this.remoteInterfaceName, this.cl);
            enhancedClass = cpp.loadClass(biName);
            if (enhancedClass.getName().equals(this.remoteInterfaceName)) {
               this.generatedRemoteInterface = enhancedClass;
               this.debug("get generatedRemoteInterface:" + this.generatedRemoteInterface + " by enhancing business interface on RemoteBizIntfClassLoader: \n" + cpp);
            } else {
               this.generatedRemoteInterface = cpp.loadClass(this.remoteInterfaceName);
               this.debug("get generatedRemoteInterface:" + this.generatedRemoteInterface + " by loading it directly on RemoteBizIntfClassLoader: \n" + cpp);
            }

            return this.generatedRemoteInterface;
         } catch (ClassNotFoundException var8) {
            throw new AssertionError(var8);
         }
      }
   }

   private void processQueue() {
      CacheKeyReference ref;
      while((ref = (CacheKeyReference)referenceQueue.poll()) != null) {
         classLoaderCache.remove(ref.getCacheKey());
      }

   }

   private RemoteBizIntfClassLoader getEnhanceLoader() {
      if (debug) {
         this.debug("enhance loader cache: " + classLoaderCache);
      }

      this.debug("businessInterface: " + this.businessInterface.getName() + " and generated interface: " + this.remoteInterfaceName);
      LoaderCacheKey key = new LoaderCacheKey(this.businessInterface.getName(), this.remoteInterfaceName, this.cl);
      RemoteBizIntfClassLoader gcl = null;
      synchronized(classLoaderCache) {
         this.processQueue();
         WeakReference ref = (WeakReference)classLoaderCache.get(key);
         if (ref != null && ref.get() != null) {
            gcl = (RemoteBizIntfClassLoader)ref.get();
            this.debug("got RemoteBizIntfClassLoader from cache: " + gcl);
         } else {
            gcl = new RemoteBizIntfClassLoader(this.businessInterface.getName(), this.remoteInterfaceName, this.cl);
            ref = new WeakReference(gcl);
            classLoaderCache.put(key, ref);
            if (debug) {
               this.debug("newly created RemoteBizIntfClassLoader: " + gcl + " enhanceLoaderCache: " + classLoaderCache);
            }
         }

         return gcl;
      }
   }

   private boolean isGenericInterfaceDeclared(Type c) {
      if (c == null) {
         return false;
      } else if (!(c instanceof Class)) {
         return true;
      } else {
         Class cls = (Class)c;
         if (!cls.isInterface()) {
            return false;
         } else {
            Type[] superIntfs = cls.getGenericInterfaces();
            if (superIntfs.length == 0) {
               return cls.getTypeParameters().length != 0;
            } else {
               int var5 = superIntfs.length;
               byte var6 = 0;
               if (var6 < var5) {
                  Type t = superIntfs[var6];
                  return this.isGenericInterfaceDeclared(t);
               } else {
                  return false;
               }
            }
         }
      }
   }

   private static void dumpIfaceInfo(Class iface) {
      System.out.println("iface name: " + iface.getName());
      System.out.println("isInterface: " + iface.isInterface());
      System.out.println("super: " + iface.getSuperclass());
      Class[] var1 = iface.getInterfaces();
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         Class c = var1[var3];
         System.out.println("implemented interface: " + c);
      }

      Method[] var5 = iface.getMethods();
      var2 = var5.length;

      for(var3 = 0; var3 < var2; ++var3) {
         Method m = var5[var3];
         System.out.println("Method: " + m);
      }

      System.out.println("\n");
   }

   public static void main(String[] argv) {
      if (argv.length != 1) {
         System.out.println("Usage: java RemoteBusinessIntfGenerator <remote-business-interface-name>");
      } else {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();

         try {
            String businessInterfaceName = argv[0];
            Class businessInterface = cl.loadClass(businessInterfaceName);
            GenericClassLoader gcl = AugmentableClassLoaderManager.getAugmentableClassLoader(cl);
            RemoteBusinessIntfGenerator generator = new RemoteBusinessIntfGenerator(businessInterfaceName + "Remote", businessInterface, gcl);
            Class ri = generator.generateRemoteInterface();
            dumpIfaceInfo(ri);
         } catch (Exception var7) {
            var7.printStackTrace();
         }

      }
   }

   private void debug(String log) {
      if (debug) {
         System.out.println("[" + this.getClass().getSimpleName() + "]---" + Thread.currentThread() + "---\n\t" + log);
      }

   }

   private static class LoaderReference extends WeakReference implements CacheKeyReference {
      private LoaderCacheKey loaderCacheKey;

      LoaderReference(ClassLoader referent, ReferenceQueue q, LoaderCacheKey loaderCacheKey) {
         super(referent, q);
         this.loaderCacheKey = loaderCacheKey;
      }

      public LoaderCacheKey getCacheKey() {
         return this.loaderCacheKey;
      }
   }

   private static class LoaderCacheKey {
      private String biName;
      private String remoteBIName;
      private LoaderReference appLoader;
      private final int hashCode;

      public LoaderCacheKey(String biName, String remoteBIName, GenericClassLoader appLoader) {
         if (biName != null && remoteBIName != null && appLoader != null) {
            this.biName = biName;
            this.remoteBIName = remoteBIName;
            this.appLoader = new LoaderReference(appLoader, RemoteBusinessIntfGenerator.referenceQueue, this);
            this.hashCode = biName.hashCode() ^ remoteBIName.hashCode() ^ appLoader.hashCode();
         } else {
            throw new AssertionError("Invalid loader cache key. business interface name: " + biName + ", remote business interface name: " + remoteBIName + ",\nGenericClassLoader: " + appLoader);
         }
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof LoaderCacheKey)) {
            return false;
         } else {
            LoaderCacheKey key = (LoaderCacheKey)obj;
            if (this.biName.equals(key.biName) && this.remoteBIName.equals(key.remoteBIName)) {
               ClassLoader refLoader = (ClassLoader)this.appLoader.get();
               if (refLoader != null && refLoader == key.appLoader.get()) {
                  return true;
               }
            }

            return false;
         }
      }

      public int hashCode() {
         return this.hashCode;
      }
   }

   private interface CacheKeyReference {
      LoaderCacheKey getCacheKey();
   }
}
