package weblogic.nodemanager.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public abstract class ProcessControl {
   public abstract String getProcessId();

   public abstract boolean killProcess(String var1);

   public abstract boolean isProcessAlive(String var1);

   public abstract String createProcess(String[] var1, Map var2, File var3, File var4) throws IOException;
}
