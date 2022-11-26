package com.bea.core.repackaged.jdt.internal.compiler.env;

public interface INameEnvironment {
   NameEnvironmentAnswer findType(char[][] var1);

   NameEnvironmentAnswer findType(char[] var1, char[][] var2);

   boolean isPackage(char[][] var1, char[] var2);

   void cleanup();
}
