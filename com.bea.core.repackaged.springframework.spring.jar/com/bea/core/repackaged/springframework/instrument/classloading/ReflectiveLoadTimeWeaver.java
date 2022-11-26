package com.bea.core.repackaged.springframework.instrument.classloading;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.DecoratingClassLoader;
import com.bea.core.repackaged.springframework.core.OverridingClassLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;

public class ReflectiveLoadTimeWeaver implements LoadTimeWeaver {
   private static final String ADD_TRANSFORMER_METHOD_NAME = "addTransformer";
   private static final String GET_THROWAWAY_CLASS_LOADER_METHOD_NAME = "getThrowawayClassLoader";
   private static final Log logger = LogFactory.getLog(ReflectiveLoadTimeWeaver.class);
   private final ClassLoader classLoader;
   private final Method addTransformerMethod;
   @Nullable
   private final Method getThrowawayClassLoaderMethod;

   public ReflectiveLoadTimeWeaver() {
      this(ClassUtils.getDefaultClassLoader());
   }

   public ReflectiveLoadTimeWeaver(@Nullable ClassLoader classLoader) {
      Assert.notNull(classLoader, (String)"ClassLoader must not be null");
      this.classLoader = classLoader;
      Method addTransformerMethod = ClassUtils.getMethodIfAvailable(this.classLoader.getClass(), "addTransformer", ClassFileTransformer.class);
      if (addTransformerMethod == null) {
         throw new IllegalStateException("ClassLoader [" + classLoader.getClass().getName() + "] does NOT provide an 'addTransformer(ClassFileTransformer)' method.");
      } else {
         this.addTransformerMethod = addTransformerMethod;
         Method getThrowawayClassLoaderMethod = ClassUtils.getMethodIfAvailable(this.classLoader.getClass(), "getThrowawayClassLoader");
         if (getThrowawayClassLoaderMethod == null && logger.isDebugEnabled()) {
            logger.debug("The ClassLoader [" + classLoader.getClass().getName() + "] does NOT provide a 'getThrowawayClassLoader()' method; SimpleThrowawayClassLoader will be used instead.");
         }

         this.getThrowawayClassLoaderMethod = getThrowawayClassLoaderMethod;
      }
   }

   public void addTransformer(ClassFileTransformer transformer) {
      Assert.notNull(transformer, (String)"Transformer must not be null");
      ReflectionUtils.invokeMethod(this.addTransformerMethod, this.classLoader, transformer);
   }

   public ClassLoader getInstrumentableClassLoader() {
      return this.classLoader;
   }

   public ClassLoader getThrowawayClassLoader() {
      if (this.getThrowawayClassLoaderMethod != null) {
         ClassLoader target = (ClassLoader)ReflectionUtils.invokeMethod(this.getThrowawayClassLoaderMethod, this.classLoader);
         return (ClassLoader)(target instanceof DecoratingClassLoader ? target : new OverridingClassLoader(this.classLoader, target));
      } else {
         return new SimpleThrowawayClassLoader(this.classLoader);
      }
   }
}
