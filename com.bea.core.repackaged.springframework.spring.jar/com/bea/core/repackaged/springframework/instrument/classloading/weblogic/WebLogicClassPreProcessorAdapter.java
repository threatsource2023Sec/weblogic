package com.bea.core.repackaged.springframework.instrument.classloading.weblogic;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Hashtable;

class WebLogicClassPreProcessorAdapter implements InvocationHandler {
   private final ClassFileTransformer transformer;
   private final ClassLoader loader;

   public WebLogicClassPreProcessorAdapter(ClassFileTransformer transformer, ClassLoader loader) {
      this.transformer = transformer;
      this.loader = loader;
   }

   @Nullable
   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String name = method.getName();
      if ("equals".equals(name)) {
         return proxy == args[0];
      } else if ("hashCode".equals(name)) {
         return this.hashCode();
      } else if ("toString".equals(name)) {
         return this.toString();
      } else if ("initialize".equals(name)) {
         this.initialize((Hashtable)args[0]);
         return null;
      } else if ("preProcess".equals(name)) {
         return this.preProcess((String)args[0], (byte[])((byte[])args[1]));
      } else {
         throw new IllegalArgumentException("Unknown method: " + method);
      }
   }

   public void initialize(Hashtable params) {
   }

   public byte[] preProcess(String className, byte[] classBytes) {
      try {
         byte[] result = this.transformer.transform(this.loader, className, (Class)null, (ProtectionDomain)null, classBytes);
         return result != null ? result : classBytes;
      } catch (IllegalClassFormatException var4) {
         throw new IllegalStateException("Cannot transform due to illegal class format", var4);
      }
   }

   public String toString() {
      return this.getClass().getName() + " for transformer: " + this.transformer;
   }
}
