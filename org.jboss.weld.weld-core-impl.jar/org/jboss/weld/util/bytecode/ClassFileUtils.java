package org.jboss.weld.util.bytecode;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jboss.classfilewriter.ClassFile;
import org.jboss.weld.serialization.spi.ProxyServices;
import sun.misc.Unsafe;

public class ClassFileUtils {
   private static Method defineClass1;
   private static Method defineClass2;
   private static AtomicBoolean classLoaderMethodsMadeAccessible = new AtomicBoolean(false);

   private ClassFileUtils() {
   }

   public static void makeClassLoaderMethodsAccessible() {
      if (classLoaderMethodsMadeAccessible.compareAndSet(false, true)) {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  Class cl = Class.forName("java.lang.ClassLoader");
                  String name = "defineClass";
                  ClassFileUtils.defineClass1 = cl.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                  ClassFileUtils.defineClass2 = cl.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);

                  try {
                     Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
                     singleoneInstanceField.setAccessible(true);
                     Unsafe theUnsafe = (Unsafe)singleoneInstanceField.get((Object)null);
                     long overrideOffset = theUnsafe.objectFieldOffset(AccessibleObject.class.getDeclaredField("override"));
                     theUnsafe.putBoolean(ClassFileUtils.defineClass1, overrideOffset, true);
                     theUnsafe.putBoolean(ClassFileUtils.defineClass2, overrideOffset, true);
                     return null;
                  } catch (NoSuchFieldException var7) {
                     ClassFileUtils.defineClass1.setAccessible(true);
                     ClassFileUtils.defineClass2.setAccessible(true);
                     return null;
                  }
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("cannot initialize ClassPool", var1.getException());
         }
      }

   }

   public static Class toClass(ClassFile ct, ClassLoader loader, ProtectionDomain domain) {
      try {
         byte[] b = ct.toBytecode();
         Method method;
         Object[] args;
         if (domain == null) {
            method = defineClass1;
            args = new Object[]{ct.getName(), b, 0, b.length};
         } else {
            method = defineClass2;
            args = new Object[]{ct.getName(), b, 0, b.length, domain};
         }

         return toClass2(method, loader, args);
      } catch (RuntimeException var6) {
         throw var6;
      } catch (InvocationTargetException var7) {
         throw new RuntimeException(var7.getTargetException());
      } catch (Exception var8) {
         throw new RuntimeException(var8);
      }
   }

   public static Class toClass(ClassFile ct, Class originalClass, ProxyServices proxyServices, ProtectionDomain domain) {
      try {
         byte[] bytecode = ct.toBytecode();
         Class result;
         if (domain == null) {
            result = proxyServices.defineClass(originalClass, ct.getName(), bytecode, 0, bytecode.length);
         } else {
            result = proxyServices.defineClass(originalClass, ct.getName(), bytecode, 0, bytecode.length, domain);
         }

         return result;
      } catch (RuntimeException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }
   }

   private static synchronized Class toClass2(Method method, ClassLoader loader, Object[] args) throws Exception {
      Class clazz = (Class)Class.class.cast(method.invoke(loader, args));
      return clazz;
   }
}
