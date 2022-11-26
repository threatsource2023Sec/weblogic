package org.mozilla.javascript;

public interface ErrorReporter {
   void error(String var1, String var2, int var3, String var4, int var5);

   EvaluatorException runtimeError(String var1, String var2, int var3, String var4, int var5);

   void warning(String var1, String var2, int var3, String var4, int var5);
}
