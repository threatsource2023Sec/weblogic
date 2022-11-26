package org.jboss.classfilewriter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import sun.misc.Unsafe;

final class DefaultClassFactory implements ClassFactory {
   static final ClassFactory INSTANCE = new DefaultClassFactory();
   private final Method defineClass1;
   private final Method defineClass2;

   private DefaultClassFactory() {
      try {
         Method[] defineClassMethods = (Method[])AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Method[] run() throws Exception {
               Unsafe UNSAFE;
               long overrideOffset;
               try {
                  Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                  theUnsafe.setAccessible(true);
                  UNSAFE = (Unsafe)theUnsafe.get((Object)null);
                  overrideOffset = UNSAFE.objectFieldOffset(AccessibleObject.class.getDeclaredField("override"));
               } catch (Exception var7) {
                  throw new Error(var7);
               }

               Class cl = ClassLoader.class;
               Method defClass1 = cl.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
               Method defClass2 = cl.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
               UNSAFE.putBoolean(defClass1, overrideOffset, true);
               UNSAFE.putBoolean(defClass2, overrideOffset, true);
               return new Method[]{defClass1, defClass2};
            }
         });
         this.defineClass1 = defineClassMethods[0];
         this.defineClass2 = defineClassMethods[1];
      } catch (PrivilegedActionException var2) {
         throw new RuntimeException("Cannot initialize DefaultClassFactory", var2.getException());
      }
   }

   public Class defineClass(ClassLoader loader, String name, byte[] b, int off, int len, ProtectionDomain domain) throws ClassFormatError {
      try {
         SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            int index = name.lastIndexOf(46);
            String packageName;
            if (index == -1) {
               packageName = "";
            } else {
               packageName = name.substring(0, index);
            }

            RuntimePermission permission = new RuntimePermission("defineClassInPackage." + packageName);
            sm.checkPermission(permission);
         }

         Method method;
         Object[] args;
         if (domain == null) {
            method = this.defineClass1;
            args = new Object[]{name, b, 0, b.length};
         } else {
            method = this.defineClass2;
            args = new Object[]{name, b, 0, b.length, domain};
         }

         return (Class)method.invoke(loader, args);
      } catch (RuntimeException var11) {
         throw var11;
      } catch (Exception var12) {
         throw new RuntimeException(var12);
      }
   }
}
