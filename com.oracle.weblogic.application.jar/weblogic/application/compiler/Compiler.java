package weblogic.application.compiler;

import weblogic.utils.compiler.ToolFailureException;

public interface Compiler {
   void compile() throws ToolFailureException;
}
