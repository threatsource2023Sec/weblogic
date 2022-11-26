package org.mozilla.javascript;

class DefaultErrorReporter implements ErrorReporter {
   public void error(String var1, String var2, int var3, String var4, int var5) {
      throw new EvaluatorException(var1);
   }

   public EvaluatorException runtimeError(String var1, String var2, int var3, String var4, int var5) {
      return new EvaluatorException(var1);
   }

   public void warning(String var1, String var2, int var3, String var4, int var5) {
   }
}
