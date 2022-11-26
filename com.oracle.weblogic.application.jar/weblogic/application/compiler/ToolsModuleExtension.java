package weblogic.application.compiler;

import java.util.Map;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

public interface ToolsModuleExtension {
   Map compile(GenericClassLoader var1, Map var2) throws ToolFailureException;

   Map merge(Map var1) throws ToolFailureException;

   void write() throws ToolFailureException;
}
