package com.bea.core.repackaged.springframework.instrument.classloading.websphere;

import com.bea.core.repackaged.springframework.util.FileCopyUtils;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.CodeSource;
import java.security.ProtectionDomain;

class WebSphereClassPreDefinePlugin implements InvocationHandler {
   private final ClassFileTransformer transformer;

   public WebSphereClassPreDefinePlugin(ClassFileTransformer transformer) {
      this.transformer = transformer;
      ClassLoader classLoader = transformer.getClass().getClassLoader();

      try {
         String dummyClass = Dummy.class.getName().replace('.', '/');
         byte[] bytes = FileCopyUtils.copyToByteArray(classLoader.getResourceAsStream(dummyClass + ".class"));
         transformer.transform(classLoader, dummyClass, (Class)null, (ProtectionDomain)null, bytes);
      } catch (Throwable var5) {
         throw new IllegalArgumentException("Cannot load transformer", var5);
      }
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String name = method.getName();
      if ("equals".equals(name)) {
         return proxy == args[0];
      } else if ("hashCode".equals(name)) {
         return this.hashCode();
      } else if ("toString".equals(name)) {
         return this.toString();
      } else if ("transformClass".equals(name)) {
         return this.transform((String)args[0], (byte[])((byte[])args[1]), (CodeSource)args[2], (ClassLoader)args[3]);
      } else {
         throw new IllegalArgumentException("Unknown method: " + method);
      }
   }

   protected byte[] transform(String className, byte[] classfileBuffer, CodeSource codeSource, ClassLoader classLoader) throws Exception {
      byte[] result = this.transformer.transform(classLoader, className.replace('.', '/'), (Class)null, (ProtectionDomain)null, classfileBuffer);
      return result != null ? result : classfileBuffer;
   }

   public String toString() {
      return this.getClass().getName() + " for transformer: " + this.transformer;
   }

   private static class Dummy {
   }
}
