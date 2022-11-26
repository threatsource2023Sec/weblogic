package com.bea.core.repackaged.jdt.internal.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.File;
import java.io.IOException;

public class CompilationUnit implements ICompilationUnit {
   public char[] contents;
   public char[] fileName;
   public char[] mainTypeName;
   String encoding;
   public String destinationPath;
   public char[] module;
   private boolean ignoreOptionalProblems;
   private ModuleBinding moduleBinding;

   public CompilationUnit(char[] contents, String fileName, String encoding) {
      this(contents, fileName, encoding, (String)null);
   }

   public CompilationUnit(char[] contents, String fileName, String encoding, String destinationPath) {
      this(contents, fileName, encoding, destinationPath, false, (String)null);
   }

   public CompilationUnit(char[] contents, String fileName, String encoding, String destinationPath, boolean ignoreOptionalProblems, String modName) {
      this.contents = contents;
      if (modName != null) {
         this.module = modName.toCharArray();
      }

      char[] fileNameCharArray = fileName.toCharArray();
      switch (File.separatorChar) {
         case '/':
            if (CharOperation.indexOf('\\', fileNameCharArray) != -1) {
               CharOperation.replace(fileNameCharArray, '\\', '/');
            }
            break;
         case '\\':
            if (CharOperation.indexOf('/', fileNameCharArray) != -1) {
               CharOperation.replace(fileNameCharArray, '/', '\\');
            }
      }

      this.fileName = fileNameCharArray;
      int start = CharOperation.lastIndexOf(File.separatorChar, fileNameCharArray) + 1;
      int end = CharOperation.lastIndexOf('.', fileNameCharArray);
      if (end == -1) {
         end = fileNameCharArray.length;
      }

      this.mainTypeName = CharOperation.subarray(fileNameCharArray, start, end);
      this.encoding = encoding;
      this.destinationPath = destinationPath;
      this.ignoreOptionalProblems = ignoreOptionalProblems;
   }

   public char[] getContents() {
      if (this.contents != null) {
         return this.contents;
      } else {
         try {
            return Util.getFileCharContent(new File(new String(this.fileName)), this.encoding);
         } catch (IOException var2) {
            this.contents = CharOperation.NO_CHAR;
            throw new AbortCompilationUnit((CompilationResult)null, var2, this.encoding);
         }
      }
   }

   public char[] getFileName() {
      return this.fileName;
   }

   public char[] getMainTypeName() {
      return this.mainTypeName;
   }

   public char[][] getPackageName() {
      return null;
   }

   public boolean ignoreOptionalProblems() {
      return this.ignoreOptionalProblems;
   }

   public String toString() {
      return "CompilationUnit[" + new String(this.fileName) + "]";
   }

   public char[] getModuleName() {
      return this.module;
   }

   public ModuleBinding module(LookupEnvironment rootEnvironment) {
      if (this.moduleBinding != null) {
         return this.moduleBinding;
      } else {
         this.moduleBinding = rootEnvironment.getModule(this.module);
         if (this.moduleBinding == null) {
            throw new IllegalStateException("Module should be known");
         } else {
            return this.moduleBinding;
         }
      }
   }

   public String getDestinationPath() {
      return this.destinationPath;
   }
}
