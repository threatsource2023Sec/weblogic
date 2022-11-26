package weblogic.application.compiler;

import weblogic.utils.compiler.ToolFailureException;

public interface Merger {
   void merge() throws ToolFailureException;

   void cleanup() throws ToolFailureException;
}
