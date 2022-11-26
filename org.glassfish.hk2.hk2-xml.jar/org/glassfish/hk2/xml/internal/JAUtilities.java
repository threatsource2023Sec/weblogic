package org.glassfish.hk2.xml.internal;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.cache.HybridCacheEntry;
import org.glassfish.hk2.utilities.cache.LRUHybridCache;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.xml.internal.alt.clazz.ClassAltClassImpl;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class JAUtilities {
   private static final String ID_PREFIX = "XmlServiceUID-";
   private static final boolean DEBUG_PREGEN = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
      public Boolean run() {
         return Boolean.parseBoolean(System.getProperty("org.jvnet.hk2.properties.xmlservice.jaxb.pregenerated", "false"));
      }
   });
   static final boolean DEBUG_GENERATION_TIMING = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
      public Boolean run() {
         return Boolean.parseBoolean(System.getProperty("org.jvnet.hk2.properties.xmlservice.jaxb.generationtime", "false"));
      }
   });
   public static final String GET = "get";
   public static final String SET = "set";
   public static final String IS = "is";
   public static final String LOOKUP = "lookup";
   public static final String ADD = "add";
   public static final String REMOVE = "remove";
   public static final String JAXB_DEFAULT_STRING = "##default";
   public static final String JAXB_DEFAULT_DEFAULT = "\u0000";
   private final ClassReflectionHelper classReflectionHelper;
   private final ClassPool defaultClassPool = ClassPool.getDefault();
   private final CtClass superClazz;
   private final Computer computer;
   private final LRUHybridCache interface2ModelCache;
   private final AtomicLong idGenerator = new AtomicLong();

   private static Set getClassLoaders(final Class myClass) {
      return (Set)AccessController.doPrivileged(new PrivilegedAction() {
         public Set run() {
            Set retVal = new LinkedHashSet();
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            if (ccl != null) {
               retVal.add(ccl);
            }

            retVal.add(myClass.getClassLoader());
            return retVal;
         }
      });
   }

   JAUtilities(ClassReflectionHelper classReflectionHelper) {
      Iterator var2 = getClassLoaders(this.getClass()).iterator();

      while(var2.hasNext()) {
         ClassLoader cl = (ClassLoader)var2.next();
         this.defaultClassPool.appendClassPath(new LoaderClassPath(cl));
      }

      this.classReflectionHelper = classReflectionHelper;

      try {
         this.superClazz = this.defaultClassPool.get(BaseHK2JAXBBean.class.getName());
      } catch (NotFoundException var4) {
         throw new MultiException(var4);
      }

      this.computer = new Computer(this);
      this.interface2ModelCache = new LRUHybridCache(2147483646, this.computer);
   }

   public String getUniqueId() {
      return "XmlServiceUID-" + this.idGenerator.getAndAdd(1L);
   }

   public ModelImpl getModel(Class iFace) {
      HybridCacheEntry entry = this.interface2ModelCache.compute(iFace);
      return (ModelImpl)entry.getValue();
   }

   public synchronized void convertRootAndLeaves(Class root, boolean mustConvertAll) {
      long currentTime = 0L;
      if (DEBUG_GENERATION_TIMING) {
         this.computer.numGenerated = 0;
         this.computer.numPreGenerated = 0;
         currentTime = System.currentTimeMillis();
      }

      ModelImpl rootModel = (ModelImpl)this.interface2ModelCache.compute(root).getValue();
      if (!mustConvertAll) {
         if (DEBUG_GENERATION_TIMING) {
            currentTime = System.currentTimeMillis() - currentTime;
            Logger.getLogger().debug("Took " + currentTime + " to perform " + this.computer.numGenerated + " generations with " + this.computer.numPreGenerated + " pre generated with a lazy parser");
         }

      } else {
         HashSet cycles = new HashSet();
         cycles.add(root);
         this.convertAllRootAndLeaves(rootModel, cycles);
         if (DEBUG_GENERATION_TIMING) {
            currentTime = System.currentTimeMillis() - currentTime;
            Logger.getLogger().debug("Took " + currentTime + " milliseconds to perform " + this.computer.numGenerated + " generations with " + this.computer.numPreGenerated + " pre generated with a non-lazy parser");
         }

      }
   }

   private void convertAllRootAndLeaves(ModelImpl rootModel, HashSet cycles) {
      Iterator var3 = rootModel.getAllChildren().iterator();

      while(var3.hasNext()) {
         ParentedModel parentModel = (ParentedModel)var3.next();
         Class convertMe = parentModel.getChildModel().getOriginalInterfaceAsClass();
         if (!cycles.contains(convertMe)) {
            cycles.add(convertMe);
            ModelImpl childModel = (ModelImpl)this.interface2ModelCache.compute(convertMe).getValue();
            this.convertAllRootAndLeaves(childModel, cycles);
         }
      }

   }

   private CtClass getBaseClass() {
      return this.superClazz;
   }

   private ClassPool getClassPool() {
      return this.defaultClassPool;
   }

   public int getNumGenerated() {
      return this.computer.numGenerated;
   }

   public int getNumPreGenerated() {
      return this.computer.numPreGenerated;
   }

   private final class Computer implements Computable {
      private final JAUtilities jaUtilities;
      private int numGenerated;
      private int numPreGenerated;

      private Computer(JAUtilities jaUtilities) {
         this.jaUtilities = jaUtilities;
      }

      public HybridCacheEntry compute(Class key) {
         String iFaceName = key.getName();
         String proxyName = Utilities.getProxyNameFromInterfaceName(iFaceName);
         Class proxyClass = GeneralUtilities.loadClass(key.getClassLoader(), proxyName);
         if (proxyClass == null) {
            ++this.numGenerated;
            if (JAUtilities.DEBUG_PREGEN) {
               Logger.getLogger().debug("Pregenerating proxy for " + key.getName());
            }

            try {
               CtClass generated = Generator.generate(new ClassAltClassImpl(key, JAUtilities.this.classReflectionHelper), this.jaUtilities.getBaseClass(), this.jaUtilities.getClassPool());
               proxyClass = generated.toClass(key.getClassLoader(), key.getProtectionDomain());
            } catch (RuntimeException var11) {
               throw new RuntimeException("Could not compile proxy for class " + iFaceName, var11);
            } catch (Throwable var12) {
               throw new RuntimeException("Could not compile proxy for class " + iFaceName, var12);
            }
         } else {
            if (JAUtilities.DEBUG_PREGEN) {
               Logger.getLogger().debug("Proxy for " + key.getName() + " was pregenerated");
            }

            ++this.numPreGenerated;
         }

         Method getModelMethod;
         try {
            getModelMethod = proxyClass.getMethod("__getModel");
         } catch (NoSuchMethodException var10) {
            throw new AssertionError("This proxy must have been generated with an old generator, it has no __getModel method for " + key.getName());
         }

         ModelImpl retVal;
         try {
            retVal = (ModelImpl)ReflectionHelper.invoke((Object)null, getModelMethod, new Object[0], false);
         } catch (RuntimeException var8) {
            throw var8;
         } catch (Throwable var9) {
            throw new RuntimeException(var9);
         }

         if (retVal == null) {
            throw new AssertionError("The __getModel method on " + proxyClass.getName() + " returned null");
         } else {
            retVal.setJAUtilities(this.jaUtilities, key.getClassLoader());
            return JAUtilities.this.interface2ModelCache.createCacheEntry(key, retVal, false);
         }
      }

      // $FF: synthetic method
      Computer(JAUtilities x1, Object x2) {
         this(x1);
      }
   }
}
