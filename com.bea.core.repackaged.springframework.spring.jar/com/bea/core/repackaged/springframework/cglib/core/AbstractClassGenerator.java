package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.ClassReader;
import com.bea.core.repackaged.springframework.cglib.core.internal.Function;
import com.bea.core.repackaged.springframework.cglib.core.internal.LoadingCache;
import java.lang.ref.WeakReference;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class AbstractClassGenerator implements ClassGenerator {
   private static final ThreadLocal CURRENT = new ThreadLocal();
   private static volatile Map CACHE = new WeakHashMap();
   private static final boolean DEFAULT_USE_CACHE = Boolean.parseBoolean(System.getProperty("cglib.useCache", "true"));
   private GeneratorStrategy strategy;
   private NamingPolicy namingPolicy;
   private Source source;
   private ClassLoader classLoader;
   private Class contextClass;
   private String namePrefix;
   private Object key;
   private boolean useCache;
   private String className;
   private boolean attemptLoad;

   protected Object wrapCachedClass(Class klass) {
      return new WeakReference(klass);
   }

   protected Object unwrapCachedValue(Object cached) {
      return ((WeakReference)cached).get();
   }

   protected AbstractClassGenerator(Source source) {
      this.strategy = DefaultGeneratorStrategy.INSTANCE;
      this.namingPolicy = DefaultNamingPolicy.INSTANCE;
      this.useCache = DEFAULT_USE_CACHE;
      this.source = source;
   }

   protected void setNamePrefix(String namePrefix) {
      this.namePrefix = namePrefix;
   }

   protected final String getClassName() {
      return this.className;
   }

   private void setClassName(String className) {
      this.className = className;
   }

   private String generateClassName(Predicate nameTestPredicate) {
      return this.namingPolicy.getClassName(this.namePrefix, this.source.name, this.key, nameTestPredicate);
   }

   public void setClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public void setContextClass(Class contextClass) {
      this.contextClass = contextClass;
   }

   public void setNamingPolicy(NamingPolicy namingPolicy) {
      if (namingPolicy == null) {
         namingPolicy = DefaultNamingPolicy.INSTANCE;
      }

      this.namingPolicy = (NamingPolicy)namingPolicy;
   }

   public NamingPolicy getNamingPolicy() {
      return this.namingPolicy;
   }

   public void setUseCache(boolean useCache) {
      this.useCache = useCache;
   }

   public boolean getUseCache() {
      return this.useCache;
   }

   public void setAttemptLoad(boolean attemptLoad) {
      this.attemptLoad = attemptLoad;
   }

   public boolean getAttemptLoad() {
      return this.attemptLoad;
   }

   public void setStrategy(GeneratorStrategy strategy) {
      if (strategy == null) {
         strategy = DefaultGeneratorStrategy.INSTANCE;
      }

      this.strategy = (GeneratorStrategy)strategy;
   }

   public GeneratorStrategy getStrategy() {
      return this.strategy;
   }

   public static AbstractClassGenerator getCurrent() {
      return (AbstractClassGenerator)CURRENT.get();
   }

   public ClassLoader getClassLoader() {
      ClassLoader t = this.classLoader;
      if (t == null) {
         t = this.getDefaultClassLoader();
      }

      if (t == null) {
         t = this.getClass().getClassLoader();
      }

      if (t == null) {
         t = Thread.currentThread().getContextClassLoader();
      }

      if (t == null) {
         throw new IllegalStateException("Cannot determine classloader");
      } else {
         return t;
      }
   }

   protected abstract ClassLoader getDefaultClassLoader();

   protected ProtectionDomain getProtectionDomain() {
      return null;
   }

   protected Object create(Object key) {
      try {
         ClassLoader loader = this.getClassLoader();
         Map cache = CACHE;
         ClassLoaderData data = (ClassLoaderData)cache.get(loader);
         if (data == null) {
            Class var5 = AbstractClassGenerator.class;
            synchronized(AbstractClassGenerator.class) {
               cache = CACHE;
               data = (ClassLoaderData)cache.get(loader);
               if (data == null) {
                  Map newCache = new WeakHashMap(cache);
                  data = new ClassLoaderData(loader);
                  newCache.put(loader, data);
                  CACHE = newCache;
               }
            }
         }

         this.key = key;
         Object obj = data.get(this, this.getUseCache());
         return obj instanceof Class ? this.firstInstance((Class)obj) : this.nextInstance(obj);
      } catch (Error | RuntimeException var9) {
         throw var9;
      } catch (Exception var10) {
         throw new CodeGenerationException(var10);
      }
   }

   protected Class generate(ClassLoaderData data) {
      Object save = CURRENT.get();
      CURRENT.set(this);

      Class var8;
      try {
         ClassLoader classLoader = data.getClassLoader();
         if (classLoader == null) {
            throw new IllegalStateException("ClassLoader is null while trying to define class " + this.getClassName() + ". It seems that the loader has been expired from a weak reference somehow. Please file an issue at cglib's issue tracker.");
         }

         String className;
         synchronized(classLoader) {
            className = this.generateClassName(data.getUniqueNamePredicate());
            data.reserveName(className);
            this.setClassName(className);
         }

         Class gen;
         if (this.attemptLoad) {
            try {
               gen = classLoader.loadClass(this.getClassName());
               Class var23 = gen;
               return var23;
            } catch (ClassNotFoundException var19) {
            }
         }

         byte[] b = this.strategy.generate(this);
         className = ClassNameReader.getClassName(new ClassReader(b));
         ProtectionDomain protectionDomain = this.getProtectionDomain();
         synchronized(classLoader) {
            gen = ReflectUtils.defineClass(className, b, classLoader, protectionDomain, this.contextClass);
         }

         var8 = gen;
      } catch (Error | RuntimeException var20) {
         throw var20;
      } catch (Exception var21) {
         throw new CodeGenerationException(var21);
      } finally {
         CURRENT.set(save);
      }

      return var8;
   }

   protected abstract Object firstInstance(Class var1) throws Exception;

   protected abstract Object nextInstance(Object var1) throws Exception;

   protected static class Source {
      String name;

      public Source(String name) {
         this.name = name;
      }
   }

   protected static class ClassLoaderData {
      private final Set reservedClassNames = new HashSet();
      private final LoadingCache generatedClasses;
      private final WeakReference classLoader;
      private final Predicate uniqueNamePredicate = new Predicate() {
         public boolean evaluate(Object name) {
            return ClassLoaderData.this.reservedClassNames.contains(name);
         }
      };
      private static final Function GET_KEY = new Function() {
         public Object apply(AbstractClassGenerator gen) {
            return gen.key;
         }
      };

      public ClassLoaderData(ClassLoader classLoader) {
         if (classLoader == null) {
            throw new IllegalArgumentException("classLoader == null is not yet supported");
         } else {
            this.classLoader = new WeakReference(classLoader);
            Function load = new Function() {
               public Object apply(AbstractClassGenerator gen) {
                  Class klass = gen.generate(ClassLoaderData.this);
                  return gen.wrapCachedClass(klass);
               }
            };
            this.generatedClasses = new LoadingCache(GET_KEY, load);
         }
      }

      public ClassLoader getClassLoader() {
         return (ClassLoader)this.classLoader.get();
      }

      public void reserveName(String name) {
         this.reservedClassNames.add(name);
      }

      public Predicate getUniqueNamePredicate() {
         return this.uniqueNamePredicate;
      }

      public Object get(AbstractClassGenerator gen, boolean useCache) {
         if (!useCache) {
            return gen.generate(this);
         } else {
            Object cachedValue = this.generatedClasses.get(gen);
            return gen.unwrapCachedValue(cachedValue);
         }
      }
   }
}
