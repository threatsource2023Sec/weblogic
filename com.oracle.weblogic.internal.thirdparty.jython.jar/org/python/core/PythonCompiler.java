package org.python.core;

import org.python.antlr.base.mod;

public interface PythonCompiler {
   PythonCodeBundle compile(mod var1, String var2, String var3, boolean var4, boolean var5, CompilerFlags var6) throws Exception;
}
