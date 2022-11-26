package weblogic.utils.classfile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.utils.classfile.cp.CPClass;

public class ClassFileBean extends BaseClassBean {
   private final String name;
   private final String superName;
   private final String sourceFile;
   private String[] interfaces;
   private MethodBean[] methods;
   private FieldBean[] fields;

   public String getName() {
      return this.name;
   }

   public String getSuperName() {
      return this.superName;
   }

   public String getSourceFile() {
      return this.sourceFile;
   }

   public String[] getInterfaces() {
      return this.interfaces;
   }

   public FieldBean[] getFields() {
      return this.fields;
   }

   public MethodBean[] getMethods() {
      return this.methods;
   }

   public MethodBean[] getPublicMethods() {
      List l = new ArrayList();

      for(int i = 0; this.methods != null && i < this.methods.length; ++i) {
         MethodBean m = this.methods[i];
         if (m.isPublic() && !m.isStatic() && !m.isConstructor()) {
            l.add(m);
         }
      }

      MethodBean[] ret = new MethodBean[l.size()];
      l.toArray(ret);
      return ret;
   }

   public boolean hasMethod(MethodBean m) {
      for(int i = 0; this.methods != null && i < this.methods.length; ++i) {
         if (m.equals(this.methods[i])) {
            return true;
         }
      }

      return false;
   }

   public static ClassFileBean load(InputStream is) throws Exception {
      ClassFile cf = new ClassFile(is);
      return new ClassFileBean(cf);
   }

   public static ClassFileBean load(File f) throws Exception {
      ClassFile cf = new ClassFile(f);
      return new ClassFileBean(cf);
   }

   public ClassFileBean(ClassFile cf) {
      this.name = cf.getClassName();
      this.sourceFile = cf.getSourceFile();
      this.superName = cf.getSuperClass().fullName();
      this.modifiers = cf.getAccessFlags();
      List l = new ArrayList();
      Iterator i = cf.getInterfaces().classes();

      String name;
      while(i.hasNext()) {
         CPClass cpclass = (CPClass)i.next();
         name = cpclass.getName();
         l.add(name);
      }

      this.interfaces = new String[l.size()];
      l.toArray(this.interfaces);
      l.clear();
      i = cf.getFields();

      while(i.hasNext()) {
         FieldInfo fi = (FieldInfo)i.next();
         name = fi.getName();
         int mods = fi.getAccessFlags();
         String type = fi.getDescriptor();
         Descriptor d = new Descriptor(type);
         type = d.getFieldType();
         FieldBean fb = new FieldBean(name, type, mods);
         l.add(fb);
      }

      this.fields = new FieldBean[l.size()];
      l.toArray(this.fields);
      l.clear();
      i = cf.getMethods();

      while(i.hasNext()) {
         MethodInfo mi = (MethodInfo)i.next();
         MethodBean mb = new MethodBean(mi);
         l.add(mb);
      }

      this.methods = new MethodBean[l.size()];
      l.toArray(this.methods);
   }

   public static String shortClass(String s) {
      int ind = s.lastIndexOf(46);
      if (ind != -1) {
         s = s.substring(ind + 1);
      }

      return s;
   }

   static void p(String s) {
      System.err.println(s);
   }

   public void printInfo() {
      p("class name    : " + this.getName());
      p("super class   : " + this.getSuperName());
      p("source file   : " + this.getSourceFile());
      String[] s = this.getInterfaces();
      p(s.length + " interfaces:");

      int i;
      for(i = 0; i < s.length; ++i) {
         p(" implements: " + s[i]);
      }

      p(this.fields.length + " fields: ");

      for(i = 0; i < this.fields.length; ++i) {
         p(" " + this.fields[i].toString());
      }

   }

   public static void main(String[] a) throws Exception {
      InputStream is = new FileInputStream(a[0]);
      DataInputStream dis = new DataInputStream(is);
      ClassFile cf = new ClassFile();
      cf.read(dis);
      dis.close();
      ClassFileBean cfb = new ClassFileBean(cf);
      MethodBean[] ms = cfb.getMethods();

      for(int i = 0; i < ms.length; ++i) {
         p(ms[i].toString());
      }

      cfb.printInfo();
   }

   public int hashCode() {
      return this.name == null ? 0 : this.name.hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof ClassFileBean)) {
         return false;
      } else {
         ClassFileBean other = (ClassFileBean)o;
         return this.getName().equals(other.getName());
      }
   }
}
