package weblogic.application.compiler;

import java.util.List;
import weblogic.application.archive.ApplicationArchive;

public interface StandaloneToolsModuleFactory extends Factory {
   /** @deprecated */
   @Deprecated
   Boolean claim(ApplicationArchive var1);

   /** @deprecated */
   @Deprecated
   Boolean claim(ApplicationArchive var1, List var2);

   /** @deprecated */
   @Deprecated
   ToolsModule create(ApplicationArchive var1);
}
