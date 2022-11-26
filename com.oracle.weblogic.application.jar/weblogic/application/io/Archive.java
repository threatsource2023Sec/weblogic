package weblogic.application.io;

import java.io.IOException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.ClassFinder;

public abstract class Archive {
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   public abstract ClassFinder getClassFinder() throws IOException;

   public abstract void remove();
}
