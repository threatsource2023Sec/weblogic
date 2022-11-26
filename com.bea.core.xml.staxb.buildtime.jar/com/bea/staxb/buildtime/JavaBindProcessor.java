package com.bea.staxb.buildtime;

import com.bea.util.jam.JamServiceParams;
import java.io.File;
import java.util.Arrays;

public abstract class JavaBindProcessor extends BindingCompilerProcessor {
   private File[] sourcepathElements;
   private File[] classpathElements;

   public void setSourcepathElements(File[] sourcepathElements) {
      this.sourcepathElements = sourcepathElements;
   }

   public void setClasspathElements(File[] classpathElements) {
      this.classpathElements = classpathElements;
   }

   protected void addClasspathElements(JamServiceParams params) {
      File[] var2 = this.classpathElements;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File classpathElement = var2[var4];
         params.addClasspath(classpathElement);
      }

   }

   protected void addSourcepathElements(JamServiceParams params) {
      File[] var2 = this.sourcepathElements;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File sourcePathElement = var2[var4];
         params.addSourcepath(sourcePathElement);
      }

   }

   public String toString() {
      return "JavaBindProcessor : { sourcepathElements = " + (this.sourcepathElements == null ? null : Arrays.toString(this.sourcepathElements)) + "classpathElements = " + (this.classpathElements == null ? null : Arrays.toString(this.classpathElements)) + "\n" + super.toString() + "}";
   }
}
