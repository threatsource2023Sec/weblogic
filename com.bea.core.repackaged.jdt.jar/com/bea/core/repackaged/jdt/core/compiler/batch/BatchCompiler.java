package com.bea.core.repackaged.jdt.core.compiler.batch;

import com.bea.core.repackaged.jdt.core.compiler.CompilationProgress;
import com.bea.core.repackaged.jdt.internal.compiler.batch.Main;
import java.io.PrintWriter;

public final class BatchCompiler {
   public static boolean compile(String commandLine, PrintWriter outWriter, PrintWriter errWriter, CompilationProgress progress) {
      return compile(Main.tokenize(commandLine), outWriter, errWriter, progress);
   }

   public static boolean compile(String[] commandLineArguments, PrintWriter outWriter, PrintWriter errWriter, CompilationProgress progress) {
      return Main.compile(commandLineArguments, outWriter, errWriter, progress);
   }

   private BatchCompiler() {
   }
}
