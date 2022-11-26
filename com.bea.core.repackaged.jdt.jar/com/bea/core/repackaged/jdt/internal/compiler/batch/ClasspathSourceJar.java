package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

public class ClasspathSourceJar extends ClasspathJar {
   private String encoding;

   public ClasspathSourceJar(File file, boolean closeZipFileAtEnd, AccessRuleSet accessRuleSet, String encoding, String destinationPath) {
      super(file, closeZipFileAtEnd, accessRuleSet, destinationPath);
      this.encoding = encoding;
   }

   public NameEnvironmentAnswer findClass(char[] typeName, String qualifiedPackageName, String moduleName, String qualifiedBinaryFileName, boolean asBinaryOnly) {
      if (!this.isPackage(qualifiedPackageName, moduleName)) {
         return null;
      } else {
         ZipEntry sourceEntry = this.zipFile.getEntry(qualifiedBinaryFileName.substring(0, qualifiedBinaryFileName.length() - 6) + ".java");
         if (sourceEntry != null) {
            try {
               InputStream stream = null;
               char[] contents = null;

               char[] contents;
               try {
                  stream = this.zipFile.getInputStream(sourceEntry);
                  contents = Util.getInputStreamAsCharArray(stream, -1, this.encoding);
               } finally {
                  if (stream != null) {
                     stream.close();
                  }

               }

               CompilationUnit compilationUnit = new CompilationUnit(contents, qualifiedBinaryFileName.substring(0, qualifiedBinaryFileName.length() - 6) + ".java", this.encoding, this.destinationPath);
               compilationUnit.module = this.module == null ? null : this.module.name();
               return new NameEnvironmentAnswer(compilationUnit, this.fetchAccessRestriction(qualifiedBinaryFileName));
            } catch (IOException var13) {
            }
         }

         return null;
      }
   }

   public int getMode() {
      return 1;
   }
}
