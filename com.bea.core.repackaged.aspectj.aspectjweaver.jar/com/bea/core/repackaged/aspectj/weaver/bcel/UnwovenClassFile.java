package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.weaver.IUnwovenClassFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UnwovenClassFile implements IUnwovenClassFile {
   protected String filename;
   protected char[] charfilename;
   protected byte[] bytes;
   protected List writtenChildClasses = Collections.emptyList();
   protected String className = null;

   public UnwovenClassFile(String filename, byte[] bytes) {
      this.filename = filename;
      this.bytes = bytes;
   }

   public UnwovenClassFile(String filename, String classname, byte[] bytes) {
      this.filename = filename;
      this.className = classname;
      this.bytes = bytes;
   }

   public String getFilename() {
      return this.filename;
   }

   public String makeInnerFileName(String innerName) {
      String prefix = this.filename.substring(0, this.filename.length() - 6);
      return prefix + "$" + innerName + ".class";
   }

   public byte[] getBytes() {
      return this.bytes;
   }

   public JavaClass getJavaClass() {
      if (this.getBytes() == null) {
         System.out.println("no bytes for: " + this.getFilename());
         Thread.dumpStack();
      }

      return Utility.makeJavaClass(this.filename, this.getBytes());
   }

   public void writeUnchangedBytes() throws IOException {
      this.writeWovenBytes(this.getBytes(), Collections.EMPTY_LIST);
   }

   public void writeWovenBytes(byte[] bytes, List childClasses) throws IOException {
      this.writeChildClasses(childClasses);
      BufferedOutputStream os = FileUtil.makeOutputStream(new File(this.filename));
      os.write(bytes);
      os.close();
   }

   private void writeChildClasses(List childClasses) throws IOException {
      this.deleteAllChildClasses();
      childClasses.removeAll(this.writtenChildClasses);
      Iterator i$ = childClasses.iterator();

      while(i$.hasNext()) {
         ChildClass childClass = (ChildClass)i$.next();
         this.writeChildClassFile(childClass.name, childClass.bytes);
      }

      this.writtenChildClasses = childClasses;
   }

   private void writeChildClassFile(String innerName, byte[] bytes) throws IOException {
      BufferedOutputStream os = FileUtil.makeOutputStream(new File(this.makeInnerFileName(innerName)));
      os.write(bytes);
      os.close();
   }

   protected void deleteAllChildClasses() {
      Iterator i$ = this.writtenChildClasses.iterator();

      while(i$.hasNext()) {
         ChildClass childClass = (ChildClass)i$.next();
         this.deleteChildClassFile(childClass.name);
      }

   }

   protected void deleteChildClassFile(String innerName) {
      File childClassFile = new File(this.makeInnerFileName(innerName));
      childClassFile.delete();
   }

   static boolean unchanged(byte[] b1, byte[] b2) {
      int len = b1.length;
      if (b2.length != len) {
         return false;
      } else {
         for(int i = 0; i < len; ++i) {
            if (b1[i] != b2[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public char[] getClassNameAsChars() {
      if (this.charfilename == null) {
         this.charfilename = this.getClassName().replace('.', '/').toCharArray();
      }

      return this.charfilename;
   }

   public String getClassName() {
      if (this.className == null) {
         this.className = this.getJavaClass().getClassName();
      }

      return this.className;
   }

   public String toString() {
      return "UnwovenClassFile(" + this.filename + ", " + this.getClassName() + ")";
   }

   public void setClassNameAsChars(char[] classNameAsChars) {
      this.charfilename = classNameAsChars;
   }

   public static class ChildClass {
      public final String name;
      public final byte[] bytes;

      ChildClass(String name, byte[] bytes) {
         this.name = name;
         this.bytes = bytes;
      }

      public boolean equals(Object other) {
         if (!(other instanceof ChildClass)) {
            return false;
         } else {
            ChildClass o = (ChildClass)other;
            return o.name.equals(this.name) && UnwovenClassFile.unchanged(o.bytes, this.bytes);
         }
      }

      public int hashCode() {
         return this.name.hashCode();
      }

      public String toString() {
         return "(ChildClass " + this.name + ")";
      }
   }
}
