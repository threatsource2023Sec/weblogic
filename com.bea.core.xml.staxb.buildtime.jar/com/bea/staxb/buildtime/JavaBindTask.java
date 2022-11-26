package com.bea.staxb.buildtime;

import java.io.File;
import java.util.ArrayList;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

public abstract class JavaBindTask extends BindingCompilerTask {
   protected Path mSourcepath = null;
   public Path mClasspath = null;
   private JavaBindProcessor processor;

   public JavaBindTask(JavaBindProcessor processor) {
      super(processor);
      this.processor = processor;
   }

   public void setSourcepath(Path path) {
      if (this.mSourcepath == null) {
         this.mSourcepath = path;
      } else {
         this.mSourcepath.append(path);
      }

   }

   public Path createSourcepath() {
      if (this.mSourcepath == null) {
         this.mSourcepath = new Path(this.getProject());
      }

      return this.mSourcepath.createPath();
   }

   public void setSourcepathRef(Reference r) {
      this.createSourcepath().setRefid(r);
   }

   public void setClasspath(Path path) {
      if (this.mClasspath == null) {
         this.mClasspath = path;
      } else {
         this.mClasspath.append(path);
      }

   }

   public void setClasspathRef(Reference r) {
      this.createClasspath().setRefid(r);
   }

   public Path createClasspath() {
      if (this.mClasspath == null) {
         this.mClasspath = new Path(this.getProject());
      }

      return this.mClasspath.createPath();
   }

   protected void populateProcessor() {
      this.processor.setSourcepathElements(this.getSourcePathElements());
      this.processor.setClasspathElements(this.getClasspathElements());
   }

   private File[] getSourcePathElements() {
      if (this.mSourcepath == null) {
         return new File[0];
      } else {
         ArrayList sourceElements = new ArrayList();
         String[] var2 = this.mSourcepath.list();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String pathElement = var2[var4];
            sourceElements.add(new File(pathElement));
         }

         return (File[])sourceElements.toArray(new File[sourceElements.size()]);
      }
   }

   private File[] getClasspathElements() {
      if (this.mClasspath == null) {
         return new File[0];
      } else {
         ArrayList classpathElements = new ArrayList();
         String[] parts = this.mClasspath.list();
         String[] var3 = parts;
         int var4 = parts.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String classpathElement = var3[var5];
            classpathElements.add(new File(classpathElement));
         }

         return (File[])classpathElements.toArray(new File[classpathElements.size()]);
      }
   }
}
