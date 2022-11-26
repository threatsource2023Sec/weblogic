package weblogic.nodemanager.plugin;

import java.io.IOException;
import java.io.OutputStream;
import weblogic.admin.plugin.Plugin;

public interface InvocationPlugin extends Plugin {
   String METRICS = "METRICS";
   String LOGGING = "LOGGING";
   String DIAGNOSTICS = "DIAGNOSTICS";

   void init(Provider var1);

   void invocationRequest(String[] var1, OutputStream var2) throws IOException;
}
