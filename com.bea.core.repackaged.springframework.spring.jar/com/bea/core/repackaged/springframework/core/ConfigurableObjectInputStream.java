package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class ConfigurableObjectInputStream extends ObjectInputStream {
   @Nullable
   private final ClassLoader classLoader;
   private final boolean acceptProxyClasses;

   public ConfigurableObjectInputStream(InputStream in, @Nullable ClassLoader classLoader) throws IOException {
      this(in, classLoader, true);
   }

   public ConfigurableObjectInputStream(InputStream in, @Nullable ClassLoader classLoader, boolean acceptProxyClasses) throws IOException {
      super(in);
      this.classLoader = classLoader;
      this.acceptProxyClasses = acceptProxyClasses;
   }

   protected Class resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
      try {
         return this.classLoader != null ? ClassUtils.forName(classDesc.getName(), this.classLoader) : super.resolveClass(classDesc);
      } catch (ClassNotFoundException var3) {
         return this.resolveFallbackIfPossible(classDesc.getName(), var3);
      }
   }

   protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      if (!this.acceptProxyClasses) {
         throw new NotSerializableException("Not allowed to accept serialized proxy classes");
      } else if (this.classLoader == null) {
         try {
            return super.resolveProxyClass(interfaces);
         } catch (ClassNotFoundException var7) {
            ClassNotFoundException ex = var7;
            Class[] resolvedInterfaces = new Class[interfaces.length];

            for(int i = 0; i < interfaces.length; ++i) {
               resolvedInterfaces[i] = this.resolveFallbackIfPossible(interfaces[i], ex);
            }

            return ClassUtils.createCompositeInterface(resolvedInterfaces, this.getFallbackClassLoader());
         }
      } else {
         Class[] resolvedInterfaces = new Class[interfaces.length];

         for(int i = 0; i < interfaces.length; ++i) {
            try {
               resolvedInterfaces[i] = ClassUtils.forName(interfaces[i], this.classLoader);
            } catch (ClassNotFoundException var6) {
               resolvedInterfaces[i] = this.resolveFallbackIfPossible(interfaces[i], var6);
            }
         }

         try {
            return ClassUtils.createCompositeInterface(resolvedInterfaces, this.classLoader);
         } catch (IllegalArgumentException var5) {
            throw new ClassNotFoundException((String)null, var5);
         }
      }
   }

   protected Class resolveFallbackIfPossible(String className, ClassNotFoundException ex) throws IOException, ClassNotFoundException {
      throw ex;
   }

   @Nullable
   protected ClassLoader getFallbackClassLoader() throws IOException {
      return null;
   }
}
