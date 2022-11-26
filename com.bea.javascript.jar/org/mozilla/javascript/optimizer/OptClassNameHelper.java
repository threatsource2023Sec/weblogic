package org.mozilla.javascript.optimizer;

import java.io.File;
import java.util.Hashtable;
import org.mozilla.javascript.ClassNameHelper;
import org.mozilla.javascript.ClassOutput;

public class OptClassNameHelper implements ClassNameHelper {
   private String generatingDirectory;
   private String packageName;
   private String initialName;
   private static int globalSerial = 1;
   private int serial = 1;
   private Class targetExtends;
   private Class[] targetImplements;
   private ClassOutput classOutput;
   private Hashtable classNames;

   public OptClassNameHelper() {
      this.setTargetClassFileName((String)null);
   }

   public ClassOutput getClassOutput() {
      return this.classOutput;
   }

   public String getGeneratingDirectory() {
      return this.generatingDirectory;
   }

   String getInitialClassName() {
      return this.initialName;
   }

   public synchronized String getJavaScriptClassName(String var1, boolean var2) {
      StringBuffer var3 = new StringBuffer();
      if (this.packageName != null && this.packageName.length() > 0) {
         var3.append(this.packageName);
         var3.append('.');
      }

      var3.append(this.initialName);
      if (this.generatingDirectory != null) {
         if (var1 != null) {
            var3.append('$');
            var3.append(var1);
         } else if (!var2) {
            var3.append(++this.serial);
         }
      } else {
         var3.append(globalSerial++);
      }

      String var4 = var3.toString();
      String var5 = var4.toLowerCase();
      String var6 = var5;
      int var7 = 0;
      if (this.classNames == null) {
         this.classNames = new Hashtable();
      }

      while(this.classNames.get(var5) != null) {
         StringBuffer var10000 = new StringBuffer(String.valueOf(var6));
         ++var7;
         var5 = var10000.append(var7).toString();
      }

      this.classNames.put(var5, Boolean.TRUE);
      return var7 == 0 ? var4 : var4 + var7;
   }

   public String getTargetClassFileName() {
      return this.getTargetClassFileName(this.getInitialClassName());
   }

   public String getTargetClassFileName(String var1) {
      if (this.generatingDirectory == null) {
         return null;
      } else {
         StringBuffer var2 = new StringBuffer();
         if (this.generatingDirectory.length() > 0) {
            var2.append(this.generatingDirectory);
            var2.append(File.separator);
         }

         var2.append(var1);
         var2.append(".class");
         return var2.toString();
      }
   }

   public Class getTargetExtends() {
      return this.targetExtends;
   }

   public Class[] getTargetImplements() {
      return this.targetImplements;
   }

   public String getTargetPackage() {
      return this.packageName;
   }

   public void reset() {
      this.classNames = null;
   }

   public void setClassOutput(ClassOutput var1) {
      this.classOutput = var1;
   }

   void setInitialClassName(String var1) {
      this.initialName = var1;
      this.serial = 0;
   }

   public void setTargetClassFileName(String var1) {
      if (var1 == null) {
         this.packageName = "org.mozilla.javascript.gen";
         this.initialName = "c";
      } else {
         int var2 = var1.lastIndexOf(File.separatorChar);
         String var3;
         if (var2 == -1) {
            this.generatingDirectory = "";
            var3 = var1;
         } else {
            this.generatingDirectory = var1.substring(0, var2);
            var3 = var1.substring(var2 + 1);
         }

         if (var3.endsWith(".class")) {
            var3 = var3.substring(0, var3.length() - 6);
         }

         this.setInitialClassName(var3);
      }
   }

   public void setTargetExtends(Class var1) {
      this.targetExtends = var1;
   }

   public void setTargetImplements(Class[] var1) {
      this.targetImplements = var1;
   }

   public void setTargetPackage(String var1) {
      this.packageName = var1;
   }
}
