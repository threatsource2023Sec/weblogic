package weblogic.nodemanager.plugin;

import java.io.IOException;
import java.io.Writer;
import weblogic.admin.plugin.Plugin;

/** @deprecated */
@Deprecated
public interface MonitoringPlugin extends Plugin {
   String METRICS = "METRICS";
   String LOGGING = "LOGGING";
   String DIAGNOSTICS = "DIAGNOSTICS";

   void init(Provider var1);

   void diagnosticsRequest(String var1, String[] var2, Writer var3) throws IOException;
}
