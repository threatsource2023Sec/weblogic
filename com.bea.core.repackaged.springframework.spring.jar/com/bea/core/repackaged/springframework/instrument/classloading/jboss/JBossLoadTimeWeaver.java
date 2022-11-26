package com.bea.core.repackaged.springframework.instrument.classloading.jboss;

import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.SimpleThrowawayClassLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JBossLoadTimeWeaver implements LoadTimeWeaver {
   private static final String DELEGATING_TRANSFORMER_CLASS_NAME = "org.jboss.as.server.deployment.module.DelegatingClassFileTransformer";
   private static final String WRAPPER_TRANSFORMER_CLASS_NAME = "org.jboss.modules.JLIClassTransformer";
   private final ClassLoader classLoader;
   private final Object delegatingTransformer;
   private final Method addTransformer;

   public JBossLoadTimeWeaver() {
      this(ClassUtils.getDefaultClassLoader());
   }

   public JBossLoadTimeWeaver(@Nullable ClassLoader classLoader) {
      Assert.notNull(classLoader, (String)"ClassLoader must not be null");
      this.classLoader = classLoader;

      try {
         Field transformer = ReflectionUtils.findField(classLoader.getClass(), "transformer");
         if (transformer == null) {
            throw new IllegalArgumentException("Could not find 'transformer' field on JBoss ClassLoader: " + classLoader.getClass().getName());
         } else {
            transformer.setAccessible(true);
            Object suggestedTransformer = transformer.get(classLoader);
            if (suggestedTransformer.getClass().getName().equals("org.jboss.modules.JLIClassTransformer")) {
               Field wrappedTransformer = ReflectionUtils.findField(suggestedTransformer.getClass(), "transformer");
               if (wrappedTransformer == null) {
                  throw new IllegalArgumentException("Could not find 'transformer' field on JBoss JLIClassTransformer: " + suggestedTransformer.getClass().getName());
               }

               wrappedTransformer.setAccessible(true);
               suggestedTransformer = wrappedTransformer.get(suggestedTransformer);
            }

            if (!suggestedTransformer.getClass().getName().equals("org.jboss.as.server.deployment.module.DelegatingClassFileTransformer")) {
               throw new IllegalStateException("Transformer not of the expected type DelegatingClassFileTransformer: " + suggestedTransformer.getClass().getName());
            } else {
               this.delegatingTransformer = suggestedTransformer;
               Method addTransformer = ReflectionUtils.findMethod(this.delegatingTransformer.getClass(), "addTransformer", ClassFileTransformer.class);
               if (addTransformer == null) {
                  throw new IllegalArgumentException("Could not find 'addTransformer' method on JBoss DelegatingClassFileTransformer: " + this.delegatingTransformer.getClass().getName());
               } else {
                  addTransformer.setAccessible(true);
                  this.addTransformer = addTransformer;
               }
            }
         }
      } catch (Throwable var5) {
         throw new IllegalStateException("Could not initialize JBoss LoadTimeWeaver", var5);
      }
   }

   public void addTransformer(ClassFileTransformer transformer) {
      try {
         this.addTransformer.invoke(this.delegatingTransformer, transformer);
      } catch (Throwable var3) {
         throw new IllegalStateException("Could not add transformer on JBoss ClassLoader: " + this.classLoader, var3);
      }
   }

   public ClassLoader getInstrumentableClassLoader() {
      return this.classLoader;
   }

   public ClassLoader getThrowawayClassLoader() {
      return new SimpleThrowawayClassLoader(this.getInstrumentableClassLoader());
   }
}
