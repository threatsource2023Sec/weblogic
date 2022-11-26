package com.bea.core.repackaged.springframework.context.weaving;

import com.bea.core.repackaged.aspectj.weaver.loadtime.ClassPreProcessorAgentAdapter;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class AspectJWeavingEnabler implements BeanFactoryPostProcessor, BeanClassLoaderAware, LoadTimeWeaverAware, Ordered {
   public static final String ASPECTJ_AOP_XML_RESOURCE = "META-INF/aop.xml";
   @Nullable
   private ClassLoader beanClassLoader;
   @Nullable
   private LoadTimeWeaver loadTimeWeaver;

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {
      this.loadTimeWeaver = loadTimeWeaver;
   }

   public int getOrder() {
      return Integer.MIN_VALUE;
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      enableAspectJWeaving(this.loadTimeWeaver, this.beanClassLoader);
   }

   public static void enableAspectJWeaving(@Nullable LoadTimeWeaver weaverToUse, @Nullable ClassLoader beanClassLoader) {
      if (weaverToUse == null) {
         if (!InstrumentationLoadTimeWeaver.isInstrumentationAvailable()) {
            throw new IllegalStateException("No LoadTimeWeaver available");
         }

         weaverToUse = new InstrumentationLoadTimeWeaver(beanClassLoader);
      }

      ((LoadTimeWeaver)weaverToUse).addTransformer(new AspectJClassBypassingClassFileTransformer(new ClassPreProcessorAgentAdapter()));
   }

   private static class AspectJClassBypassingClassFileTransformer implements ClassFileTransformer {
      private final ClassFileTransformer delegate;

      public AspectJClassBypassingClassFileTransformer(ClassFileTransformer delegate) {
         this.delegate = delegate;
      }

      public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
         return !className.startsWith("org.aspectj") && !className.startsWith("org/aspectj") ? this.delegate.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer) : classfileBuffer;
      }
   }
}
