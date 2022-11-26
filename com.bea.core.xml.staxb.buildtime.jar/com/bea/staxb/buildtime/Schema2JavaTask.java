package com.bea.staxb.buildtime;

import java.io.File;
import java.util.ArrayList;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.Path;

public class Schema2JavaTask extends BindingCompilerTask {
   private Path mXsdPath = null;
   private Schema2JavaProcessor processor = (Schema2JavaProcessor)super.getProcessor();

   public Schema2JavaTask() {
      super(new Schema2JavaProcessor());
   }

   public void setJaxRpcRules(boolean jaxRpc) {
      this.processor.setUseJaxRpcRules(jaxRpc);
   }

   public void setSrcdir(Path srcDir) {
      if (this.mXsdPath == null) {
         this.mXsdPath = srcDir;
      } else {
         this.mXsdPath.append(srcDir);
      }

   }

   public Path createSrc() {
      if (this.mXsdPath == null) {
         this.mXsdPath = new Path(this.getProject());
      }

      return this.mXsdPath.createPath();
   }

   public void setCompileJava(boolean b) {
      this.processor.setCompileJava(b);
   }

   public void setKeepGeneratedJava(boolean b) {
      this.processor.setKeepGeneratedJava(b);
   }

   public void setJavac(String javacPath) {
      this.processor.setPathToJavac(javacPath);
   }

   public void setJavacSourceAndTarget(String version) {
      this.processor.setJavacSourceAndTarget(version);
   }

   public void setJavacClasspath(File[] classpath) {
      this.processor.setJavacClasspath(classpath);
   }

   protected void populateProcessor() {
      if (this.mXsdPath != null && this.mXsdPath.size() != 0) {
         this.processor.setSchemaFiles(this.getSchemaFiles());
      } else {
         throw new BuildException("srcdir attribute must be set!", this.getLocation());
      }
   }

   private File[] getSchemaFiles() {
      ArrayList schemaFiles = new ArrayList();
      String[] fileNames = this.mXsdPath.list();
      String[] var3 = fileNames;
      int var4 = fileNames.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String fileName = var3[var5];
         File srcDir = this.getSourceDir(fileName);
         DirectoryScanner ds = this.getDirectoryScanner(srcDir);
         String[] files = ds.getIncludedFiles();
         String[] var10 = files;
         int var11 = files.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            String file = var10[var12];
            if (file.endsWith(".xsd")) {
               schemaFiles.add(new File(srcDir, file));
            }
         }
      }

      return (File[])schemaFiles.toArray(new File[schemaFiles.size()]);
   }

   private File getSourceDir(String fileName) {
      File srcDir = this.getProject() == null ? new File(fileName) : this.getProject().resolveFile(fileName);
      if (!srcDir.exists()) {
         throw new BuildException("srcdir \"" + srcDir.getPath() + "\" does not exist!", this.getLocation());
      } else {
         return srcDir;
      }
   }
}
