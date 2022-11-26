package jnr.ffi.util.ref;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinalizableReferenceQueue {
   private static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
   private static final String FINALIZER_CLASS_NAME = "jnr.ffi.util.ref.internal.Finalizer";
   private static final Method startFinalizer;
   private static final Map finalizerQueues;
   final ReferenceQueue queue;
   final boolean threadStarted;

   public FinalizableReferenceQueue() {
      boolean threadStarted = false;

      ReferenceQueue queue;
      try {
         queue = (ReferenceQueue)startFinalizer.invoke((Object)null, FinalizableReference.class, this);
         threadStarted = true;
      } catch (IllegalAccessException var4) {
         throw new AssertionError(var4);
      } catch (Throwable var5) {
         logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", var5);
         queue = new ReferenceQueue();
      }

      this.queue = queue;
      this.threadStarted = threadStarted;
      finalizerQueues.put(this, Boolean.TRUE);
   }

   void cleanUp() {
      if (!this.threadStarted) {
         this.pollReferenceQueue();
      }

   }

   private void pollReferenceQueue() {
      Reference reference;
      while((reference = this.queue.poll()) != null) {
         reference.clear();

         try {
            ((FinalizableReference)reference).finalizeReferent();
         } catch (Throwable var3) {
            logger.log(Level.SEVERE, "Error cleaning up after reference.", var3);
         }
      }

   }

   private static Class loadFinalizer(FinalizerLoader... loaders) {
      FinalizerLoader[] var1 = loaders;
      int var2 = loaders.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         FinalizerLoader loader = var1[var3];
         Class finalizer = loader.loadFinalizer();
         if (finalizer != null) {
            return finalizer;
         }
      }

      throw new AssertionError();
   }

   static Method getStartFinalizer(Class finalizer) {
      try {
         return finalizer.getMethod("startFinalizer", Class.class, Object.class);
      } catch (NoSuchMethodException var2) {
         throw new AssertionError(var2);
      }
   }

   public static void cleanUpAll() {
      try {
         Object[] var0 = finalizerQueues.keySet().toArray();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Object frq = var0[var2];
            ((FinalizableReferenceQueue)frq).cleanUp();
         }
      } catch (Throwable var4) {
      }

   }

   static {
      Class finalizer = loadFinalizer(new SystemLoader(), new DecoupledLoader(), new DirectLoader());
      startFinalizer = getStartFinalizer(finalizer);
      finalizerQueues = Collections.synchronizedMap(new WeakHashMap());
   }

   static class DirectLoader implements FinalizerLoader {
      public Class loadFinalizer() {
         try {
            return Class.forName("jnr.ffi.util.ref.internal.Finalizer");
         } catch (ClassNotFoundException var2) {
            throw new AssertionError(var2);
         }
      }
   }

   static class DecoupledLoader implements FinalizerLoader {
      private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader.Loading Finalizer in the current class loader instead. As a result, you will not be ableto garbage collect this class loader. To support reclaiming this class loader, eitherresolve the underlying issue, or move Google Collections to your system class path.";

      public Class loadFinalizer() {
         try {
            ClassLoader finalizerLoader = this.newLoader(this.getBaseUrl());
            return finalizerLoader.loadClass("jnr.ffi.util.ref.internal.Finalizer");
         } catch (Exception var2) {
            FinalizableReferenceQueue.logger.log(Level.WARNING, "Could not load Finalizer in its own class loader.Loading Finalizer in the current class loader instead. As a result, you will not be ableto garbage collect this class loader. To support reclaiming this class loader, eitherresolve the underlying issue, or move Google Collections to your system class path.", var2);
            return null;
         }
      }

      URL getBaseUrl() throws IOException {
         String finalizerPath = "jnr.ffi.util.ref.internal.Finalizer".replace('.', '/') + ".class";
         URL finalizerUrl = this.getClass().getClassLoader().getResource(finalizerPath);
         if (finalizerUrl == null) {
            throw new FileNotFoundException(finalizerPath);
         } else {
            String urlString = finalizerUrl.toString();
            if (!urlString.endsWith(finalizerPath)) {
               throw new IOException("Unsupported path style: " + urlString);
            } else {
               urlString = urlString.substring(0, urlString.length() - finalizerPath.length());
               return new URL(finalizerUrl, urlString);
            }
         }
      }

      URLClassLoader newLoader(URL base) {
         return new URLClassLoader(new URL[]{base});
      }
   }

   static class SystemLoader implements FinalizerLoader {
      public Class loadFinalizer() {
         ClassLoader systemLoader;
         try {
            systemLoader = ClassLoader.getSystemClassLoader();
         } catch (SecurityException var4) {
            FinalizableReferenceQueue.logger.info("Not allowed to access system class loader.");
            return null;
         }

         if (systemLoader != null) {
            try {
               return systemLoader.loadClass("jnr.ffi.util.ref.internal.Finalizer");
            } catch (ClassNotFoundException var3) {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   interface FinalizerLoader {
      Class loadFinalizer();
   }
}
