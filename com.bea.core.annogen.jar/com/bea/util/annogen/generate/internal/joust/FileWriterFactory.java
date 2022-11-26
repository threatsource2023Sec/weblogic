package com.bea.util.annogen.generate.internal.joust;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileWriterFactory implements WriterFactory {
   private static final String EXTENSION = ".java";
   private static final char PACKAGE_SEPARATOR = '.';
   private File mSourceRoot;
   private String mEncoding = null;

   public FileWriterFactory(File sourceRoot) {
      if (sourceRoot == null) {
         throw new IllegalArgumentException();
      } else {
         this.mSourceRoot = sourceRoot;
      }
   }

   public FileWriterFactory(File sourceRoot, String encoding) {
      if (sourceRoot == null) {
         throw new IllegalArgumentException();
      } else if (encoding == null) {
         throw new IllegalArgumentException();
      } else {
         this.mSourceRoot = sourceRoot;
         this.mEncoding = encoding;
      }
   }

   public Writer createWriter(String packageName, String className) throws IOException {
      if (this.mEncoding == null) {
         return new FileWriter(this.createFile(packageName, className));
      } else {
         FileOutputStream fos = new FileOutputStream(this.createFile(packageName, className));
         return new OutputStreamWriter(fos, this.mEncoding);
      }
   }

   public File createFile(String packageName, String className) throws IOException {
      File dir = new File(this.mSourceRoot, packageName.replace('.', File.separatorChar));
      if (!dir.exists() && !dir.mkdirs()) {
         throw new IOException("Failed to create directory " + dir);
      } else {
         return new File(dir, className + ".java");
      }
   }
}
