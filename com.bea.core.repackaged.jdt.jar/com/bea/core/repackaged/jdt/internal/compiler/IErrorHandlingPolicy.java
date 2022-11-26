package com.bea.core.repackaged.jdt.internal.compiler;

public interface IErrorHandlingPolicy {
   boolean proceedOnErrors();

   boolean stopOnFirstError();

   boolean ignoreAllErrors();
}
