package com.bea.core.repackaged.jdt.internal.compiler.env;

public interface INameEnvironmentExtension extends INameEnvironment {
   NameEnvironmentAnswer findType(char[] var1, char[][] var2, boolean var3, char[] var4);

   default NameEnvironmentAnswer findType(char[] typeName, char[][] packageName, boolean searchWithSecondaryTypes) {
      return this.findType(typeName, packageName, searchWithSecondaryTypes, (char[])null);
   }
}
