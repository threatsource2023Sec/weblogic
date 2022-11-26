package org.python.compiler;

import org.python.antlr.PythonTree;

public interface CompilationContext {
   Future getFutures();

   void error(String var1, boolean var2, PythonTree var3) throws Exception;

   String getFilename();

   ScopeInfo getScopeInfo(PythonTree var1);
}
