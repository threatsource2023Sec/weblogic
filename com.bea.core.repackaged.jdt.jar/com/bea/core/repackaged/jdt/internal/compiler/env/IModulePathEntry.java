package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public interface IModulePathEntry {
   default IModule getModule() {
      return null;
   }

   default IModule getModule(char[] name) {
      IModule mod = this.getModule();
      return mod != null && CharOperation.equals(name, mod.name()) ? mod : null;
   }

   default boolean servesModule(char[] name) {
      return this.getModule(name) != null;
   }

   char[][] getModulesDeclaringPackage(String var1, String var2);

   boolean hasCompilationUnit(String var1, String var2);

   default boolean isAutomaticModule() {
      return false;
   }
}
