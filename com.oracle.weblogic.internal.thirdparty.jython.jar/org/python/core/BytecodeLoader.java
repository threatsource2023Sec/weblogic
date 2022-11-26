package org.python.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import org.python.objectweb.asm.ClassReader;
import org.python.util.Generic;

public class BytecodeLoader {
   public static Class makeClass(String name, byte[] data, Class... referents) {
      Loader loader = new Loader();
      Class[] var4 = referents;
      int var5 = referents.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class referent = var4[var6];

         try {
            ClassLoader cur = referent.getClassLoader();
            if (cur != null) {
               loader.addParent(cur);
            }
         } catch (SecurityException var13) {
         }
      }

      Class c = loader.loadClassFromBytes(name, data);
      if (ContainsPyBytecode.class.isAssignableFrom(c)) {
         try {
            fixPyBytecode(c);
         } catch (IllegalAccessException var9) {
            throw new RuntimeException(var9);
         } catch (NoSuchFieldException var10) {
            throw new RuntimeException(var10);
         } catch (ClassNotFoundException var11) {
            throw new RuntimeException(var11);
         } catch (IOException var12) {
            throw new RuntimeException(var12);
         }
      }

      BytecodeNotification.notify(name, data, c);
      return c;
   }

   public static Class makeClass(String name, List referents, byte[] data) {
      return referents != null ? makeClass(name, data, (Class[])referents.toArray(new Class[referents.size()])) : makeClass(name, data);
   }

   private static PyCode parseSerializedCode(String code_str) throws IOException, ClassNotFoundException {
      byte[] b = DatatypeConverter.parseBase64Binary(code_str);
      ByteArrayInputStream bi = new ByteArrayInputStream(b);
      ObjectInputStream si = new ObjectInputStream(bi);
      PyBytecode meth_code = (PyBytecode)si.readObject();
      si.close();
      bi.close();
      return meth_code;
   }

   public static void fixPyBytecode(Class c) throws IllegalAccessException, NoSuchFieldException, IOException, ClassNotFoundException {
      Field[] fields = c.getDeclaredFields();
      Field[] var2 = fields;
      int var3 = fields.length;

      label59:
      for(int var4 = 0; var4 < var3; ++var4) {
         Field fld = var2[var4];
         String fldName = fld.getName();
         if (fldName.startsWith("___")) {
            fldName = fldName.substring(3);
            String[] splt = fldName.split("_");
            Field codeField;
            if (splt[0].equals("0")) {
               fldName = fldName.substring(2);
               codeField = c.getDeclaredField(fldName);
               if (codeField.get((Object)null) == null) {
                  codeField.set((Object)null, parseSerializedCode((String)fld.get((Object)null)));
               }
            } else if (splt[1].equals("0")) {
               fldName = fldName.substring(splt[0].length() + splt[1].length() + 2);
               codeField = c.getDeclaredField(fldName);
               if (codeField.get((Object)null) == null) {
                  int len = Integer.parseInt(splt[0]);
                  StringBuilder blt = new StringBuilder((String)fld.get((Object)null));
                  int pos = 1;

                  do {
                     if (pos >= len) {
                        codeField.set((Object)null, parseSerializedCode(blt.toString()));
                        continue label59;
                     }

                     Field[] var13 = fields;
                     int var14 = fields.length;

                     for(int var15 = 0; var15 < var14; ++var15) {
                        Field fldPart = var13[var15];
                        String partName = fldPart.getName();
                        if (partName.length() != fldName.length() && partName.startsWith("___") && partName.endsWith(fldName)) {
                           String[] splt2 = partName.substring(3).split("_");
                           if (Integer.parseInt(splt2[1]) == pos) {
                              blt.append((String)fldPart.get((Object)null));
                              ++pos;
                              if (pos == len) {
                                 break;
                              }
                           }
                        }
                     }
                  } while(pos != pos);

                  throw new RuntimeException("Invalid PyBytecode splitting in " + c.getName() + ":\nSplit-index " + pos + " wasn't found.");
               }
            }
         }
      }

   }

   public static PyCode makeCode(String name, byte[] data, String filename) {
      try {
         Class c = makeClass(name, data);
         Object o = c.getConstructor(String.class).newInstance(filename);
         PyCode result = ((PyRunnable)o).getMain();
         return result;
      } catch (Exception var6) {
         throw Py.JavaError(var6);
      }
   }

   public static class Loader extends URLClassLoader {
      private List parents = Generic.list();

      public Loader() {
         super(new URL[0]);
         this.parents.add(imp.getSyspathJavaLoader());
      }

      public void addParent(ClassLoader referent) {
         if (!this.parents.contains(referent)) {
            this.parents.add(0, referent);
         }

      }

      protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
         Class c = this.findLoadedClass(name);
         if (c != null) {
            return c;
         } else {
            Iterator var4 = this.parents.iterator();

            while(var4.hasNext()) {
               ClassLoader loader = (ClassLoader)var4.next();

               try {
                  return loader.loadClass(name);
               } catch (ClassNotFoundException var7) {
               }
            }

            throw new ClassNotFoundException(name);
         }
      }

      public Class loadClassFromBytes(String name, byte[] data) {
         if (name.endsWith("$py")) {
            try {
               ClassReader cr = new ClassReader(data);
               name = cr.getClassName().replace('/', '.');
            } catch (RuntimeException var4) {
            }
         }

         Class c = this.defineClass(name, data, 0, data.length, this.getClass().getProtectionDomain());
         this.resolveClass(c);
         return c;
      }
   }
}
