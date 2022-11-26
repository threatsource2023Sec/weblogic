package com.bea.core.repackaged.aspectj.weaver.loadtime;

import com.bea.core.repackaged.aspectj.bridge.context.CompilationAndWeavingContext;
import com.bea.core.repackaged.aspectj.weaver.Dump;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import com.bea.core.repackaged.aspectj.weaver.tools.WeavingAdaptor;
import com.bea.core.repackaged.aspectj.weaver.tools.cache.SimpleCache;
import com.bea.core.repackaged.aspectj.weaver.tools.cache.SimpleCacheFactory;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Aj implements ClassPreProcessor {
   private IWeavingContext weavingContext;
   public static SimpleCache laCache = SimpleCacheFactory.createSimpleCache();
   private static ReferenceQueue adaptorQueue = new ReferenceQueue();
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(Aj.class);
   private static final String deleLoader = "sun.reflect.DelegatingClassLoader";
   public static List loadersToSkip = null;

   public Aj() {
      this((IWeavingContext)null);
   }

   public Aj(IWeavingContext context) {
      if (trace.isTraceEnabled()) {
         trace.enter("<init>", this, (Object[])(new Object[]{context, this.getClass().getClassLoader()}));
      }

      this.weavingContext = context;
      if (trace.isTraceEnabled()) {
         trace.exit("<init>");
      }

   }

   public void initialize() {
   }

   public byte[] preProcess(String className, byte[] bytes, ClassLoader loader, ProtectionDomain protectionDomain) {
      if (loader != null && className != null && !loader.getClass().getName().equals("sun.reflect.DelegatingClassLoader")) {
         if (loadersToSkip != null && loadersToSkip.contains(loader.getClass().getName())) {
            return bytes;
         } else {
            if (trace.isTraceEnabled()) {
               trace.enter("preProcess", this, (Object[])(new Object[]{className, bytes, loader}));
            }

            if (trace.isTraceEnabled()) {
               trace.event("preProcess", this, (Object[])(new Object[]{loader.getParent(), Thread.currentThread().getContextClassLoader()}));
            }

            byte[] var8;
            try {
               byte[] cacheBytes;
               try {
                  synchronized(loader) {
                     byte[] newBytes;
                     if (SimpleCacheFactory.isEnabled()) {
                        cacheBytes = laCache.getAndInitialize(className, bytes, loader, protectionDomain);
                        if (cacheBytes != null) {
                           newBytes = cacheBytes;
                           return newBytes;
                        }
                     }

                     WeavingAdaptor weavingAdaptor = Aj.WeaverContainer.getWeaver(loader, this.weavingContext);
                     if (weavingAdaptor == null) {
                        if (trace.isTraceEnabled()) {
                           trace.exit("preProcess");
                        }

                        newBytes = bytes;
                        return newBytes;
                     }

                     try {
                        weavingAdaptor.setActiveProtectionDomain(protectionDomain);
                        newBytes = weavingAdaptor.weaveClass(className, bytes, false);
                        Dump.dumpOnExit(weavingAdaptor.getMessageHolder(), true);
                        if (trace.isTraceEnabled()) {
                           trace.exit("preProcess", (Object)newBytes);
                        }

                        if (SimpleCacheFactory.isEnabled()) {
                           laCache.put(className, bytes, newBytes);
                        }

                        var8 = newBytes;
                     } finally {
                        weavingAdaptor.setActiveProtectionDomain((ProtectionDomain)null);
                     }
                  }
               } catch (Throwable var22) {
                  trace.error(className, var22);
                  Dump.dumpWithException(var22);
                  if (trace.isTraceEnabled()) {
                     trace.exit("preProcess", var22);
                  }

                  cacheBytes = bytes;
                  return cacheBytes;
               }
            } finally {
               CompilationAndWeavingContext.resetForThread();
            }

            return var8;
         }
      } else {
         return bytes;
      }
   }

   public static int removeStaleAdaptors(boolean displayProgress) {
      int removed = 0;
      synchronized(Aj.WeaverContainer.weavingAdaptors) {
         if (displayProgress) {
            System.err.println("Weaver adaptors before queue processing:");
            Map m = Aj.WeaverContainer.weavingAdaptors;
            Set keys = m.keySet();
            Iterator iterator = keys.iterator();

            while(iterator.hasNext()) {
               Object object = iterator.next();
               System.err.println(object + " = " + Aj.WeaverContainer.weavingAdaptors.get(object));
            }
         }

         for(Object o = adaptorQueue.poll(); o != null; o = adaptorQueue.poll()) {
            if (displayProgress) {
               System.err.println("Processing referencequeue entry " + o);
            }

            AdaptorKey wo = (AdaptorKey)o;
            boolean didit = Aj.WeaverContainer.weavingAdaptors.remove(wo) != null;
            if (!didit) {
               throw new RuntimeException("Eh?? key=" + wo);
            }

            ++removed;
            if (displayProgress) {
               System.err.println("Removed? " + didit);
            }
         }

         if (displayProgress) {
            System.err.println("Weaver adaptors after queue processing:");
            Map m = Aj.WeaverContainer.weavingAdaptors;
            Set keys = m.keySet();
            Iterator iterator = keys.iterator();

            while(iterator.hasNext()) {
               Object object = iterator.next();
               System.err.println(object + " = " + Aj.WeaverContainer.weavingAdaptors.get(object));
            }
         }

         return removed;
      }
   }

   public static int getActiveAdaptorCount() {
      return Aj.WeaverContainer.weavingAdaptors.size();
   }

   public static void checkQ() {
      synchronized(adaptorQueue) {
         for(Object o = adaptorQueue.poll(); o != null; o = adaptorQueue.poll()) {
            AdaptorKey wo = (AdaptorKey)o;
            Aj.WeaverContainer.weavingAdaptors.remove(wo);
         }

      }
   }

   public String getNamespace(ClassLoader loader) {
      ClassLoaderWeavingAdaptor weavingAdaptor = (ClassLoaderWeavingAdaptor)Aj.WeaverContainer.getWeaver(loader, this.weavingContext);
      return weavingAdaptor.getNamespace();
   }

   public boolean generatedClassesExist(ClassLoader loader) {
      return ((ClassLoaderWeavingAdaptor)Aj.WeaverContainer.getWeaver(loader, this.weavingContext)).generatedClassesExistFor((String)null);
   }

   public void flushGeneratedClasses(ClassLoader loader) {
      ((ClassLoaderWeavingAdaptor)Aj.WeaverContainer.getWeaver(loader, this.weavingContext)).flushGeneratedClasses();
   }

   static {
      new ExplicitlyInitializedClassLoaderWeavingAdaptor(new ClassLoaderWeavingAdaptor());

      try {
         String loadersToSkipProperty = System.getProperty("aj.weaving.loadersToSkip", "");
         StringTokenizer st = new StringTokenizer(loadersToSkipProperty, ",");
         if (loadersToSkipProperty != null && loadersToSkip == null) {
            if (st.hasMoreTokens()) {
               loadersToSkip = new ArrayList();
            }

            while(st.hasMoreTokens()) {
               String nextLoader = st.nextToken();
               loadersToSkip.add(nextLoader);
            }
         }
      } catch (Exception var3) {
      }

   }

   static class ExplicitlyInitializedClassLoaderWeavingAdaptor {
      private final ClassLoaderWeavingAdaptor weavingAdaptor;
      private boolean isInitialized;

      public ExplicitlyInitializedClassLoaderWeavingAdaptor(ClassLoaderWeavingAdaptor weavingAdaptor) {
         this.weavingAdaptor = weavingAdaptor;
         this.isInitialized = false;
      }

      private void initialize(ClassLoader loader, IWeavingContext weavingContext) {
         if (!this.isInitialized) {
            this.isInitialized = true;
            this.weavingAdaptor.initialize(loader, weavingContext);
         }

      }

      public ClassLoaderWeavingAdaptor getWeavingAdaptor(ClassLoader loader, IWeavingContext weavingContext) {
         this.initialize(loader, weavingContext);
         return this.weavingAdaptor;
      }
   }

   static class WeaverContainer {
      static final Map weavingAdaptors = Collections.synchronizedMap(new HashMap());
      private static final ClassLoader myClassLoader = WeavingAdaptor.class.getClassLoader();
      private static ExplicitlyInitializedClassLoaderWeavingAdaptor myClassLoaderAdaptor;

      static WeavingAdaptor getWeaver(ClassLoader loader, IWeavingContext weavingContext) {
         ExplicitlyInitializedClassLoaderWeavingAdaptor adaptor = null;
         AdaptorKey adaptorKey = new AdaptorKey(loader);
         String loaderClassName = loader.getClass().getName();
         synchronized(weavingAdaptors) {
            Aj.checkQ();
            if (loader.equals(myClassLoader)) {
               adaptor = myClassLoaderAdaptor;
            } else {
               adaptor = (ExplicitlyInitializedClassLoaderWeavingAdaptor)weavingAdaptors.get(adaptorKey);
            }

            if (adaptor == null) {
               ClassLoaderWeavingAdaptor weavingAdaptor = new ClassLoaderWeavingAdaptor();
               adaptor = new ExplicitlyInitializedClassLoaderWeavingAdaptor(weavingAdaptor);
               if (myClassLoaderAdaptor == null && loader.equals(myClassLoader)) {
                  myClassLoaderAdaptor = adaptor;
               } else {
                  weavingAdaptors.put(adaptorKey, adaptor);
               }
            }
         }

         return adaptor.getWeavingAdaptor(loader, weavingContext);
      }
   }

   private static class AdaptorKey extends WeakReference {
      private final int loaderHashCode;
      private final int sysHashCode;
      private final int hashValue;
      private final String loaderClass;

      public AdaptorKey(ClassLoader loader) {
         super(loader, Aj.adaptorQueue);
         this.loaderHashCode = loader.hashCode();
         this.sysHashCode = System.identityHashCode(loader);
         this.loaderClass = loader.getClass().getName();
         this.hashValue = this.loaderHashCode + this.sysHashCode + this.loaderClass.hashCode();
      }

      public ClassLoader getClassLoader() {
         ClassLoader instance = (ClassLoader)this.get();
         return instance;
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof AdaptorKey)) {
            return false;
         } else {
            AdaptorKey other = (AdaptorKey)obj;
            return other.loaderHashCode == this.loaderHashCode && other.sysHashCode == this.sysHashCode && this.loaderClass.equals(other.loaderClass);
         }
      }

      public int hashCode() {
         return this.hashValue;
      }
   }
}
