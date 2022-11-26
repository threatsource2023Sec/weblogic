package com.bea.core.repackaged.springframework.objenesis.instantiator.util;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.strategy.PlatformDescription;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.security.ProtectionDomain;
import sun.misc.Unsafe;

public final class DefineClassHelper {
   private static final Helper privileged = PlatformDescription.isAfterJava11() ? new Java11() : new Java8();

   public static Class defineClass(String name, byte[] b, int off, int len, Class neighbor, ClassLoader loader, ProtectionDomain protectionDomain) {
      return privileged.defineClass(name, b, off, len, neighbor, loader, protectionDomain);
   }

   private DefineClassHelper() {
   }

   private static class Java11 extends Helper {
      private final Class module;
      private final MethodHandles.Lookup lookup;
      private final MethodHandle getModule;
      private final MethodHandle addReads;
      private final MethodHandle privateLookupIn;
      private final MethodHandle defineClass;

      private Java11() {
         super(null);
         this.module = this.module();
         this.lookup = MethodHandles.lookup();
         this.getModule = this.getModule();
         this.addReads = this.addReads();
         this.privateLookupIn = this.privateLookupIn();
         this.defineClass = this.defineClass();
      }

      private Class module() {
         try {
            return Class.forName("java.lang.Module");
         } catch (ClassNotFoundException var2) {
            throw new ObjenesisException(var2);
         }
      }

      private MethodHandle getModule() {
         try {
            return this.lookup.findVirtual(Class.class, "getModule", MethodType.methodType(this.module));
         } catch (IllegalAccessException | NoSuchMethodException var2) {
            throw new ObjenesisException(var2);
         }
      }

      private MethodHandle addReads() {
         try {
            return this.lookup.findVirtual(this.module, "addReads", MethodType.methodType(this.module, this.module));
         } catch (IllegalAccessException | NoSuchMethodException var2) {
            throw new ObjenesisException(var2);
         }
      }

      private MethodHandle privateLookupIn() {
         try {
            return this.lookup.findStatic(MethodHandles.class, "privateLookupIn", MethodType.methodType(MethodHandles.Lookup.class, Class.class, MethodHandles.Lookup.class));
         } catch (IllegalAccessException | NoSuchMethodException var2) {
            throw new ObjenesisException(var2);
         }
      }

      private MethodHandle defineClass() {
         try {
            return this.lookup.findVirtual(MethodHandles.Lookup.class, "defineClass", MethodType.methodType(Class.class, byte[].class));
         } catch (IllegalAccessException | NoSuchMethodException var2) {
            throw new ObjenesisException(var2);
         }
      }

      Class defineClass(String className, byte[] b, int off, int len, Class neighbor, ClassLoader loader, ProtectionDomain protectionDomain) {
         try {
            Object module = this.getModule.invokeWithArguments(DefineClassHelper.class);
            Object neighborModule = this.getModule.invokeWithArguments(neighbor);
            this.addReads.invokeWithArguments(module, neighborModule);
            MethodHandles.Lookup prvlookup = this.privateLookupIn.invokeExact(neighbor, this.lookup);
            return this.defineClass.invokeExact(prvlookup, b);
         } catch (Throwable var11) {
            throw new ObjenesisException(neighbor.getName() + " has no permission to define the class", var11);
         }
      }

      // $FF: synthetic method
      Java11(Object x0) {
         this();
      }
   }

   private static class Java8 extends Helper {
      private final MethodHandle defineClass;

      private Java8() {
         super(null);
         this.defineClass = this.defineClass();
      }

      private MethodHandle defineClass() {
         MethodType mt = MethodType.methodType(Class.class, String.class, byte[].class, Integer.TYPE, Integer.TYPE, ClassLoader.class, ProtectionDomain.class);

         MethodHandle m;
         try {
            m = MethodHandles.publicLookup().findVirtual(Unsafe.class, "defineClass", mt);
         } catch (IllegalAccessException | NoSuchMethodException var4) {
            throw new ObjenesisException(var4);
         }

         Unsafe unsafe = UnsafeUtils.getUnsafe();
         return m.bindTo(unsafe);
      }

      Class defineClass(String className, byte[] b, int off, int len, Class neighbor, ClassLoader loader, ProtectionDomain protectionDomain) {
         try {
            return this.defineClass.invokeExact(className, b, off, len, loader, protectionDomain);
         } catch (Throwable var9) {
            if (var9 instanceof Error) {
               throw (Error)var9;
            } else if (var9 instanceof RuntimeException) {
               throw (RuntimeException)var9;
            } else {
               throw new ObjenesisException(var9);
            }
         }
      }

      // $FF: synthetic method
      Java8(Object x0) {
         this();
      }
   }

   private abstract static class Helper {
      private Helper() {
      }

      abstract Class defineClass(String var1, byte[] var2, int var3, int var4, Class var5, ClassLoader var6, ProtectionDomain var7);

      // $FF: synthetic method
      Helper(Object x0) {
         this();
      }
   }
}
