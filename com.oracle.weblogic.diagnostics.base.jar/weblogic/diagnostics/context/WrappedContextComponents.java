package weblogic.diagnostics.context;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public interface WrappedContextComponents {
   String getECID();

   int[] getRIDComponents();

   Map getParameters();

   Set getLogKeys();

   Level getLogLevel();
}
