package com.bea.core.repackaged.springframework.instrument.classloading.weblogic;

import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class WebLogicClassLoaderAdapter {
   private static final String GENERIC_CLASS_LOADER_NAME = "weblogic.utils.classloaders.GenericClassLoader";
   private static final String CLASS_PRE_PROCESSOR_NAME = "weblogic.utils.classloaders.ClassPreProcessor";
   private final ClassLoader classLoader;
   private final Class wlPreProcessorClass;
   private final Method addPreProcessorMethod;
   private final Method getClassFinderMethod;
   private final Method getParentMethod;
   private final Constructor wlGenericClassLoaderConstructor;

   public WebLogicClassLoaderAdapter(ClassLoader classLoader) {
      Class wlGenericClassLoaderClass;
      try {
         wlGenericClassLoaderClass = classLoader.loadClass("weblogic.utils.classloaders.GenericClassLoader");
         this.wlPreProcessorClass = classLoader.loadClass("weblogic.utils.classloaders.ClassPreProcessor");
         this.addPreProcessorMethod = classLoader.getClass().getMethod("addInstanceClassPreProcessor", this.wlPreProcessorClass);
         this.getClassFinderMethod = classLoader.getClass().getMethod("getClassFinder");
         this.getParentMethod = classLoader.getClass().getMethod("getParent");
         this.wlGenericClassLoaderConstructor = wlGenericClassLoaderClass.getConstructor(this.getClassFinderMethod.getReturnType(), ClassLoader.class);
      } catch (Throwable var4) {
         throw new IllegalStateException("Could not initialize WebLogic LoadTimeWeaver because WebLogic 10 API classes are not available", var4);
      }

      if (!wlGenericClassLoaderClass.isInstance(classLoader)) {
         throw new IllegalArgumentException("ClassLoader must be an instance of [" + wlGenericClassLoaderClass.getName() + "]: " + classLoader);
      } else {
         this.classLoader = classLoader;
      }
   }

   public void addTransformer(ClassFileTransformer transformer) {
      Assert.notNull(transformer, (String)"ClassFileTransformer must not be null");

      try {
         InvocationHandler adapter = new WebLogicClassPreProcessorAdapter(transformer, this.classLoader);
         Object adapterInstance = Proxy.newProxyInstance(this.wlPreProcessorClass.getClassLoader(), new Class[]{this.wlPreProcessorClass}, adapter);
         this.addPreProcessorMethod.invoke(this.classLoader, adapterInstance);
      } catch (InvocationTargetException var4) {
         throw new IllegalStateException("WebLogic addInstanceClassPreProcessor method threw exception", var4.getCause());
      } catch (Throwable var5) {
         throw new IllegalStateException("Could not invoke WebLogic addInstanceClassPreProcessor method", var5);
      }
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public ClassLoader getThrowawayClassLoader() {
      try {
         Object classFinder = this.getClassFinderMethod.invoke(this.classLoader);
         Object parent = this.getParentMethod.invoke(this.classLoader);
         return (ClassLoader)this.wlGenericClassLoaderConstructor.newInstance(classFinder, parent);
      } catch (InvocationTargetException var3) {
         throw new IllegalStateException("WebLogic GenericClassLoader constructor failed", var3.getCause());
      } catch (Throwable var4) {
         throw new IllegalStateException("Could not construct WebLogic GenericClassLoader", var4);
      }
   }
}
