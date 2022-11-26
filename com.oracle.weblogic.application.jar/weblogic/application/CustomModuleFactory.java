package weblogic.application;

import weblogic.application.compiler.ToolsModule;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.utils.compiler.ToolFailureException;

public abstract class CustomModuleFactory {
   public abstract void init(CustomModuleContext var1);

   public abstract Module createModule(CustomModuleBean var1) throws ModuleException;

   public ToolsModule createToolsModule(CustomModuleBean cmb) throws ToolFailureException {
      return null;
   }
}
