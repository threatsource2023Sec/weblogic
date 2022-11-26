package com.bea.core.repackaged.springframework.context.weaving;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.LoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.glassfish.GlassFishLoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.jboss.JBossLoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.tomcat.TomcatLoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.weblogic.WebLogicLoadTimeWeaver;
import com.bea.core.repackaged.springframework.instrument.classloading.websphere.WebSphereLoadTimeWeaver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.instrument.ClassFileTransformer;

public class DefaultContextLoadTimeWeaver implements LoadTimeWeaver, BeanClassLoaderAware, DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private LoadTimeWeaver loadTimeWeaver;

   public DefaultContextLoadTimeWeaver() {
   }

   public DefaultContextLoadTimeWeaver(ClassLoader beanClassLoader) {
      this.setBeanClassLoader(beanClassLoader);
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      LoadTimeWeaver serverSpecificLoadTimeWeaver = this.createServerSpecificLoadTimeWeaver(classLoader);
      if (serverSpecificLoadTimeWeaver != null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Determined server-specific load-time weaver: " + serverSpecificLoadTimeWeaver.getClass().getName());
         }

         this.loadTimeWeaver = serverSpecificLoadTimeWeaver;
      } else if (InstrumentationLoadTimeWeaver.isInstrumentationAvailable()) {
         this.logger.debug("Found Spring's JVM agent for instrumentation");
         this.loadTimeWeaver = new InstrumentationLoadTimeWeaver(classLoader);
      } else {
         try {
            this.loadTimeWeaver = new ReflectiveLoadTimeWeaver(classLoader);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Using reflective load-time weaver for class loader: " + this.loadTimeWeaver.getInstrumentableClassLoader().getClass().getName());
            }
         } catch (IllegalStateException var4) {
            throw new IllegalStateException(var4.getMessage() + " Specify a custom LoadTimeWeaver or start your Java virtual machine with Spring's agent: -javaagent:org.springframework.instrument.jar");
         }
      }

   }

   @Nullable
   protected LoadTimeWeaver createServerSpecificLoadTimeWeaver(ClassLoader classLoader) {
      String name = classLoader.getClass().getName();

      try {
         if (name.startsWith("org.apache.catalina")) {
            return new TomcatLoadTimeWeaver(classLoader);
         }

         if (name.startsWith("org.glassfish")) {
            return new GlassFishLoadTimeWeaver(classLoader);
         }

         if (name.startsWith("org.jboss.modules")) {
            return new JBossLoadTimeWeaver(classLoader);
         }

         if (name.startsWith("com.ibm.ws.classloader")) {
            return new WebSphereLoadTimeWeaver(classLoader);
         }

         if (name.startsWith("weblogic")) {
            return new WebLogicLoadTimeWeaver(classLoader);
         }
      } catch (Exception var4) {
         if (this.logger.isInfoEnabled()) {
            this.logger.info("Could not obtain server-specific LoadTimeWeaver: " + var4.getMessage());
         }
      }

      return null;
   }

   public void destroy() {
      if (this.loadTimeWeaver instanceof InstrumentationLoadTimeWeaver) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Removing all registered transformers for class loader: " + this.loadTimeWeaver.getInstrumentableClassLoader().getClass().getName());
         }

         ((InstrumentationLoadTimeWeaver)this.loadTimeWeaver).removeTransformers();
      }

   }

   public void addTransformer(ClassFileTransformer transformer) {
      Assert.state(this.loadTimeWeaver != null, "Not initialized");
      this.loadTimeWeaver.addTransformer(transformer);
   }

   public ClassLoader getInstrumentableClassLoader() {
      Assert.state(this.loadTimeWeaver != null, "Not initialized");
      return this.loadTimeWeaver.getInstrumentableClassLoader();
   }

   public ClassLoader getThrowawayClassLoader() {
      Assert.state(this.loadTimeWeaver != null, "Not initialized");
      return this.loadTimeWeaver.getThrowawayClassLoader();
   }
}
