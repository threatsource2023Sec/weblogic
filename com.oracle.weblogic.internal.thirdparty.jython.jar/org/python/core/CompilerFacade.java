package org.python.core;

import org.python.antlr.base.mod;
import org.python.compiler.LegacyCompiler;

public class CompilerFacade {
   private static volatile PythonCompiler compiler = loadDefaultCompiler();

   public static void setCompiler(PythonCompiler compiler) {
      CompilerFacade.compiler = compiler;
   }

   private static PythonCompiler loadDefaultCompiler() {
      return new LegacyCompiler();
   }

   public static PyCode compile(mod node, String name, String filename, boolean linenumbers, boolean printResults, CompilerFlags cflags) {
      try {
         PythonCodeBundle bundle = compiler.compile(node, name, filename, linenumbers, printResults, cflags);
         return bundle.loadCode();
      } catch (Throwable var7) {
         throw ParserFacade.fixParseError((ParserFacade.ExpectedEncodingBufferedReader)null, var7, filename);
      }
   }
}
