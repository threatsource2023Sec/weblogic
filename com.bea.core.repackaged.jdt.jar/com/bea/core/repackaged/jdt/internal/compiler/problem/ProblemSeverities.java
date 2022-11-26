package com.bea.core.repackaged.jdt.internal.compiler.problem;

public interface ProblemSeverities {
   int Warning = 0;
   int Error = 1;
   int AbortCompilation = 2;
   int AbortCompilationUnit = 4;
   int AbortType = 8;
   int AbortMethod = 16;
   int Abort = 30;
   int Optional = 32;
   int SecondaryError = 64;
   int Fatal = 128;
   int Ignore = 256;
   int InternalError = 512;
   int Info = 1024;
   int CoreSeverityMASK = 1281;
}
