package weblogic.diagnostics.debug;

import java.util.Map;

public interface DebugConfig {
   String getName();

   void setName(String var1);

   Map getDebugProperties();

   void setDebugProperties(Map var1);
}
