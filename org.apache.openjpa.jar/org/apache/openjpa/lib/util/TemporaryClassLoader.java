package org.apache.openjpa.lib.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import serp.bytecode.lowlevel.ConstantPoolTable;

public class TemporaryClassLoader extends ClassLoader {
   public TemporaryClassLoader(ClassLoader parent) {
      super(parent);
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      return this.loadClass(name, false);
   }

   protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      Class c = this.findLoadedClass(name);
      if (c != null) {
         return c;
      } else if (!name.startsWith("java.") && !name.startsWith("javax.") && !name.startsWith("sun.") && !name.startsWith("jdk.")) {
         String resourceName = name.replace('.', '/') + ".class";
         InputStream resource = this.getResourceAsStream(resourceName);
         if (resource == null) {
            throw new ClassNotFoundException(name);
         } else {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] b = new byte[1024];

            try {
               int n = false;

               int n;
               while((n = resource.read(b, 0, b.length)) != -1) {
                  bout.write(b, 0, n);
               }

               byte[] classBytes = bout.toByteArray();
               if (isAnnotation(classBytes)) {
                  return Class.forName(name, resolve, this.getClass().getClassLoader());
               } else {
                  try {
                     return this.defineClass(name, classBytes, 0, classBytes.length);
                  } catch (SecurityException var10) {
                     return super.loadClass(name, resolve);
                  }
               }
            } catch (IOException var11) {
               return super.loadClass(name, resolve);
            }
         }
      } else {
         return Class.forName(name, resolve, this.getClass().getClassLoader());
      }
   }

   private static boolean isAnnotation(byte[] b) {
      if (JavaVersions.VERSION < 5) {
         return false;
      } else {
         int idx = ConstantPoolTable.getEndIndex(b);
         int access = ConstantPoolTable.readUnsignedShort(b, idx);
         return (access & 8192) != 0;
      }
   }
}
