package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;

public interface ICompilationUnit extends IDependent {
   char[] getContents();

   char[] getMainTypeName();

   char[][] getPackageName();

   default boolean ignoreOptionalProblems() {
      return false;
   }

   default ModuleBinding module(LookupEnvironment environment) {
      return environment.getModule(this.getModuleName());
   }

   default char[] getModuleName() {
      return null;
   }

   default String getDestinationPath() {
      return null;
   }
}
