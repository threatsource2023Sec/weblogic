package weblogic.persistence;

import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsExtension;
import weblogic.application.compiler.ToolsExtensionFactory;

public class PersistenceToolsExtensionFactory implements ToolsExtensionFactory {
   public ToolsExtension createExtension(ToolsContext ctx) {
      return new PersistenceToolsModule();
   }
}
