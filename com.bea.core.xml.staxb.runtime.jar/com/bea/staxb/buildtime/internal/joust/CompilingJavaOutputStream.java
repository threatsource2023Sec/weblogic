package com.bea.staxb.buildtime.internal.joust;

import com.bea.xbean.tool.CodeGenUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompilingJavaOutputStream extends SourceJavaOutputStream implements WriterFactory {
   private static final String PREFIX = "[CompilingJavaOutputStream] ";
   private FileWriterFactory mWriterFactoryDelegate;
   private List mSourceFiles;
   private File mSourceDir;
   private File mCompileDir;
   private File[] mJavacClasspath;
   private boolean mKeepGenerated;
   private String mJavacPath;
   private boolean mDoCompile;
   private String javacSourceAndTarget;

   public CompilingJavaOutputStream(File srcDir) {
      this();
      this.setSourceDir(srcDir);
   }

   public CompilingJavaOutputStream() {
      this.mSourceDir = null;
      this.mCompileDir = null;
      this.mJavacClasspath = null;
      this.mJavacPath = null;
      this.mDoCompile = true;
      this.javacSourceAndTarget = "1.7";
      this.setWriterFactory(this);
      this.mSourceFiles = new ArrayList();
   }

   public void setSourceDir(File srcDir) {
      if (srcDir == null) {
         throw new IllegalArgumentException("null srcDir");
      } else {
         this.mWriterFactoryDelegate = new FileWriterFactory(this.mSourceDir = srcDir);
      }
   }

   public void setCompilationDir(File destDir) {
      this.mCompileDir = destDir;
   }

   public void setJavac(String javacPath) {
      this.mJavacPath = javacPath;
   }

   public void setJavacClasspath(File[] classpath) {
      this.mJavacClasspath = classpath;
   }

   public void setKeepGenerated(boolean b) {
      this.mKeepGenerated = b;
   }

   public void setDoCompile(boolean b) {
      this.mDoCompile = b;
   }

   public void setJavacSourceAndTarget(String val) {
      this.javacSourceAndTarget = val;
   }

   public Writer createWriter(String packageName, String className) throws IOException {
      if (this.mWriterFactoryDelegate == null) {
         throw new IllegalStateException("delegate never set called on the CompilingJavaOutputStream");
      } else {
         File out = this.mWriterFactoryDelegate.createFile(packageName, className);
         this.mSourceFiles.add(out);
         return new FileWriter(out);
      }
   }

   public void close() throws IOException {
      super.close();
      this.mLogger.logVerbose("[CompilingJavaOutputStream]  closing");
      if (this.mDoCompile && this.mCompileDir != null) {
         this.mLogger.logVerbose("[CompilingJavaOutputStream] compileDir = " + this.mCompileDir);
         Iterator i = this.mSourceFiles.iterator();

         while(i.hasNext()) {
            this.mLogger.logVerbose("[CompilingJavaOutputStream] " + i.next().toString());
         }

         boolean verbose = this.mLogger.isVerbose();
         boolean result = this.mSourceFiles.size() > 0 ? CodeGenUtil.externalCompile(this.mSourceFiles, this.mCompileDir, this.mJavacClasspath, verbose, this.mJavacPath, this.javacSourceAndTarget, (String)null, (String)null, !verbose, verbose) : true;
         this.mLogger.logVerbose("[CompilingJavaOutputStream]  compilation result: " + result);
         if (!result) {
            throw new IOException("Compilation of sources failed, check log for details.");
         }

         if (!this.mKeepGenerated) {
            this.mLogger.logVerbose("[CompilingJavaOutputStream]  deleting " + this.mSourceDir);
            this.mSourceDir.delete();
         }
      }

   }
}
