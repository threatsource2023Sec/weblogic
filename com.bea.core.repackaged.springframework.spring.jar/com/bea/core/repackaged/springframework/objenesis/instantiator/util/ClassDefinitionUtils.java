package com.bea.core.repackaged.springframework.objenesis.instantiator.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.ProtectionDomain;

public final class ClassDefinitionUtils {
   public static final byte OPS_aload_0 = 42;
   public static final byte OPS_invokespecial = -73;
   public static final byte OPS_return = -79;
   public static final byte OPS_new = -69;
   public static final byte OPS_dup = 89;
   public static final byte OPS_areturn = -80;
   public static final int CONSTANT_Utf8 = 1;
   public static final int CONSTANT_Integer = 3;
   public static final int CONSTANT_Float = 4;
   public static final int CONSTANT_Long = 5;
   public static final int CONSTANT_Double = 6;
   public static final int CONSTANT_Class = 7;
   public static final int CONSTANT_String = 8;
   public static final int CONSTANT_Fieldref = 9;
   public static final int CONSTANT_Methodref = 10;
   public static final int CONSTANT_InterfaceMethodref = 11;
   public static final int CONSTANT_NameAndType = 12;
   public static final int CONSTANT_MethodHandle = 15;
   public static final int CONSTANT_MethodType = 16;
   public static final int CONSTANT_InvokeDynamic = 18;
   public static final int ACC_PUBLIC = 1;
   public static final int ACC_FINAL = 16;
   public static final int ACC_SUPER = 32;
   public static final int ACC_INTERFACE = 512;
   public static final int ACC_ABSTRACT = 1024;
   public static final int ACC_SYNTHETIC = 4096;
   public static final int ACC_ANNOTATION = 8192;
   public static final int ACC_ENUM = 16384;
   public static final byte[] MAGIC = new byte[]{-54, -2, -70, -66};
   public static final byte[] VERSION = new byte[]{0, 0, 0, 49};
   private static final ProtectionDomain PROTECTION_DOMAIN;

   private ClassDefinitionUtils() {
   }

   public static Class defineClass(String className, byte[] b, Class neighbor, ClassLoader loader) throws Exception {
      Class c = DefineClassHelper.defineClass(className, b, 0, b.length, neighbor, loader, PROTECTION_DOMAIN);
      Class.forName(className, true, loader);
      return c;
   }

   public static byte[] readClass(String className) throws IOException {
      className = ClassUtils.classNameToResource(className);
      byte[] b = new byte[2500];
      InputStream in = ClassDefinitionUtils.class.getClassLoader().getResourceAsStream(className);
      Throwable var4 = null;

      int length;
      try {
         length = in.read(b);
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         if (in != null) {
            if (var4 != null) {
               try {
                  in.close();
               } catch (Throwable var12) {
                  var4.addSuppressed(var12);
               }
            } else {
               in.close();
            }
         }

      }

      if (length >= 2500) {
         throw new IllegalArgumentException("The class is longer that 2500 bytes which is currently unsupported");
      } else {
         byte[] copy = new byte[length];
         System.arraycopy(b, 0, copy, 0, length);
         return copy;
      }
   }

   public static void writeClass(String fileName, byte[] bytes) throws IOException {
      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
      Throwable var3 = null;

      try {
         out.write(bytes);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (out != null) {
            if (var3 != null) {
               try {
                  out.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               out.close();
            }
         }

      }

   }

   static {
      ClassDefinitionUtils.class.getClass();
      PROTECTION_DOMAIN = (ProtectionDomain)AccessController.doPrivileged(ClassDefinitionUtils.class::getProtectionDomain);
   }
}
