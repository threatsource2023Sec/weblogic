package com.bea.util.annogen.generate.internal.joust;

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
   private File mCompileDir;
   private File[] mJavacClasspath;
   private boolean mKeepGenerated;
   private String mJavacPath;
   private boolean mDoCompile;
   private String mEncoding;
   private File mSourceDir;
   private String jt;
   private String js;

   public CompilingJavaOutputStream(File srcDir) {
      this();
      this.setSourceDir(srcDir);
   }

   public CompilingJavaOutputStream() {
      this.mCompileDir = null;
      this.mJavacClasspath = null;
      this.mJavacPath = null;
      this.mDoCompile = true;
      this.mEncoding = null;
      this.mSourceDir = null;
      this.setWriterFactory(this);
      this.mSourceFiles = new ArrayList();
   }

   public void setSourceDir(File srcDir) {
      if (srcDir == null) {
         throw new IllegalArgumentException("null srcDir");
      } else {
         this.mWriterFactoryDelegate = null;
         this.mSourceDir = srcDir;
      }
   }

   public void setEncoding(String enc) {
      this.mWriterFactoryDelegate = null;
      this.mEncoding = enc;
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

   public Writer createWriter(String packageName, String className) throws IOException {
      if (this.mWriterFactoryDelegate == null) {
         if (this.mSourceDir == null) {
            throw new IllegalStateException("setSourceDir must be called");
         }

         this.mWriterFactoryDelegate = this.mEncoding != null ? new FileWriterFactory(this.mSourceDir, this.mEncoding) : new FileWriterFactory(this.mSourceDir);
      }

      File out = this.mWriterFactoryDelegate.createFile(packageName, className);
      this.mSourceFiles.add(out);
      return new FileWriter(out);
   }

   public void close() throws IOException {
      super.close();
      this.verbose("closing");
      if (this.mDoCompile && this.mCompileDir != null) {
         this.verbose("compileDir = " + this.mCompileDir);
         Iterator i = this.mSourceFiles.iterator();

         while(i.hasNext()) {
            this.verbose(i.next().toString());
         }

         boolean verbose = this.mLogger.isVerbose((Object)this);
         boolean result = this.mSourceFiles.size() > 0 ? CodeGenUtil.externalCompile(this.mSourceFiles, this.mCompileDir, this.mJavacClasspath, verbose, this.mJavacPath, (String)null, (String)null, !verbose, verbose, this.js, this.jt) : true;
         this.verbose("compilation result: " + result);
         if (!result) {
            throw new IOException("Compilation of sources failed, check log for details.");
         }

         if (!this.mKeepGenerated) {
            Iterator j = this.mSourceFiles.iterator();

            while(j.hasNext()) {
               File f = (File)j.next();
               this.verbose("deleting " + f);
               f.delete();
            }
         }
      }

   }

   private void verbose(String msg) {
      if (this.mLogger.isVerbose((Object)this)) {
         this.mLogger.verbose("[CompilingJavaOutputStream]  " + msg);
      }

   }

   public void setJavacTarget(String target) {
      this.jt = target;
   }

   public void setJavacSource(String source) {
      this.js = source;
   }
}
