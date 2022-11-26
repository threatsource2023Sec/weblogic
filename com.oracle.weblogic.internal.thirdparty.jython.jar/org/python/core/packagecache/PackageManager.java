package org.python.core.packagecache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.python.core.Py;
import org.python.core.PyJavaPackage;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyStringMap;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;

public abstract class PackageManager {
   public PyJavaPackage topLevelPackage = new PyJavaPackage("", this);

   public abstract Class findClass(String var1, String var2, String var3);

   public Class findClass(String pkg, String name) {
      return this.findClass(pkg, name, "java class");
   }

   public void notifyPackageImport(String pkg, String name) {
   }

   public abstract boolean packageExists(String var1, String var2);

   public abstract PyList doDir(PyJavaPackage var1, boolean var2, boolean var3);

   public abstract void addDirectory(File var1);

   public abstract void addJarDir(String var1, boolean var2);

   public abstract void addJar(String var1, boolean var2);

   protected PyList basicDoDir(PyJavaPackage jpkg, boolean instantiate, boolean exclpkgs) {
      PyStringMap dict = jpkg.__dict__;
      PyStringMap cls = jpkg.clsSet;
      if (instantiate) {
         Iterator var10 = cls.keys().asIterable().iterator();

         while(var10.hasNext()) {
            PyObject pyname = (PyObject)var10.next();
            if (!dict.has_key(pyname)) {
               String name = pyname.toString();
               jpkg.addClass(name, Py.findClass(jpkg.__name__ + "." + name));
            }
         }

         return dict.keys();
      } else {
         PyList ret = cls.keys();
         PyList dictKeys = dict.keys();
         Iterator var8 = dictKeys.asIterable().iterator();

         while(true) {
            PyObject name;
            do {
               do {
                  if (!var8.hasNext()) {
                     return ret;
                  }

                  name = (PyObject)var8.next();
               } while(cls.has_key(name));
            } while(exclpkgs && dict.get(name) instanceof PyJavaPackage);

            ret.append(name);
         }
      }
   }

   protected PyList merge(PyList list1, PyList list2) {
      Iterator var3 = list2.asIterable().iterator();

      while(var3.hasNext()) {
         PyObject name = (PyObject)var3.next();
         list1.append(name);
      }

      return list1;
   }

   public PyObject lookupName(String name) {
      PyObject top = this.topLevelPackage;

      String lastName;
      do {
         int dot = name.indexOf(46);
         String firstName = name;
         lastName = null;
         if (dot != -1) {
            firstName = name.substring(0, dot);
            lastName = name.substring(dot + 1, name.length());
         }

         firstName = firstName.intern();
         top = ((PyObject)top).__findattr__(firstName);
         if (top == null) {
            return null;
         }

         name = lastName;
      } while(lastName != null);

      return (PyObject)top;
   }

   public PyJavaPackage makeJavaPackage(String name, String classes, String jarfile) {
      PyJavaPackage p = this.topLevelPackage;
      if (name.length() != 0) {
         p = p.addPackage(name, jarfile);
      }

      if (classes != null) {
         p.addPlaceholders(classes);
      }

      return p;
   }

   protected static int checkAccess(InputStream cstream) throws IOException {
      try {
         ClassReader reader = new ClassReader(cstream);
         AccessVisitor visitor = new AccessVisitor();
         reader.accept(visitor, 0);
         return visitor.getClassAccess();
      } catch (RuntimeException var3) {
         return -1;
      }
   }

   private static class AccessVisitor extends ClassVisitor {
      private int class_access;

      public AccessVisitor() throws IOException {
         super(327680);
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         this.class_access = access;
      }

      public int getClassAccess() {
         return this.class_access;
      }
   }
}
