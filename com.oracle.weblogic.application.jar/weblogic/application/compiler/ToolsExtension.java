package weblogic.application.compiler;

import java.util.Map;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

public interface ToolsExtension {
   void init(ToolsContext var1, GenericClassLoader var2) throws ToolFailureException;

   Map merge() throws ToolFailureException;

   void compile() throws ToolFailureException;

   void cleanup();
}
