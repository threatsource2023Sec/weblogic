package com.bea.staxb.buildtime.internal.joust;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileWriterFactory implements WriterFactory {
   private static final String EXTENSION = ".java";
   private static final char PACKAGE_SEPARATOR = '.';
   private File mSourceRoot;

   public FileWriterFactory(File sourceRoot) {
      if (sourceRoot == null) {
         throw new IllegalArgumentException();
      } else {
         this.mSourceRoot = sourceRoot;
      }
   }

   public Writer createWriter(String packageName, String className) throws IOException {
      return new FileWriter(this.createFile(packageName, className));
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
