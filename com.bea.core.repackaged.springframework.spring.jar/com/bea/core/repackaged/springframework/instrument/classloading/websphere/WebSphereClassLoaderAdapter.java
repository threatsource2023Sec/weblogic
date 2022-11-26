package com.bea.core.repackaged.springframework.instrument.classloading.websphere;

import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

class WebSphereClassLoaderAdapter {
   private static final String COMPOUND_CLASS_LOADER_NAME = "com.ibm.ws.classloader.CompoundClassLoader";
   private static final String CLASS_PRE_PROCESSOR_NAME = "com.ibm.websphere.classloader.ClassLoaderInstancePreDefinePlugin";
   private static final String PLUGINS_FIELD = "preDefinePlugins";
   private ClassLoader classLoader;
   private Class wsPreProcessorClass;
   private Method addPreDefinePlugin;
   private Constructor cloneConstructor;
   private Field transformerList;

   public WebSphereClassLoaderAdapter(ClassLoader classLoader) {
      Class wsCompoundClassLoaderClass;
      try {
         wsCompoundClassLoaderClass = classLoader.loadClass("com.ibm.ws.classloader.CompoundClassLoader");
         this.cloneConstructor = classLoader.getClass().getDeclaredConstructor(wsCompoundClassLoaderClass);
         this.cloneConstructor.setAccessible(true);
         this.wsPreProcessorClass = classLoader.loadClass("com.ibm.websphere.classloader.ClassLoaderInstancePreDefinePlugin");
         this.addPreDefinePlugin = classLoader.getClass().getMethod("addPreDefinePlugin", this.wsPreProcessorClass);
         this.transformerList = wsCompoundClassLoaderClass.getDeclaredField("preDefinePlugins");
         this.transformerList.setAccessible(true);
      } catch (Throwable var4) {
         throw new IllegalStateException("Could not initialize WebSphere LoadTimeWeaver because WebSphere API classes are not available", var4);
      }

      if (!wsCompoundClassLoaderClass.isInstance(classLoader)) {
         throw new IllegalArgumentException("ClassLoader must be an instance of [com.ibm.ws.classloader.CompoundClassLoader]: " + classLoader);
      } else {
         this.classLoader = classLoader;
      }
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public void addTransformer(ClassFileTransformer transformer) {
      Assert.notNull(transformer, (String)"ClassFileTransformer must not be null");

      try {
         InvocationHandler adapter = new WebSphereClassPreDefinePlugin(transformer);
         Object adapterInstance = Proxy.newProxyInstance(this.wsPreProcessorClass.getClassLoader(), new Class[]{this.wsPreProcessorClass}, adapter);
         this.addPreDefinePlugin.invoke(this.classLoader, adapterInstance);
      } catch (InvocationTargetException var4) {
         throw new IllegalStateException("WebSphere addPreDefinePlugin method threw exception", var4.getCause());
      } catch (Throwable var5) {
         throw new IllegalStateException("Could not invoke WebSphere addPreDefinePlugin method", var5);
      }
   }

   public ClassLoader getThrowawayClassLoader() {
      try {
         ClassLoader loader = (ClassLoader)this.cloneConstructor.newInstance(this.getClassLoader());
         List list = (List)this.transformerList.get(loader);
         list.clear();
         return loader;
      } catch (InvocationTargetException var3) {
         throw new IllegalStateException("WebSphere CompoundClassLoader constructor failed", var3.getCause());
      } catch (Throwable var4) {
         throw new IllegalStateException("Could not construct WebSphere CompoundClassLoader", var4);
      }
   }
}
