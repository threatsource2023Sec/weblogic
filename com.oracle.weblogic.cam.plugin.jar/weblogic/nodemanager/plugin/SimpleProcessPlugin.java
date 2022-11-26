package weblogic.nodemanager.plugin;

import java.io.IOException;
import java.util.Properties;
import weblogic.admin.plugin.Plugin;

public interface SimpleProcessPlugin extends Plugin {
   void init(Provider var1);

   ProcessManagementPlugin.SystemComponentState getState(String var1) throws IOException;

   void start(String var1, Properties var2) throws IOException;

   void stop(String var1, Properties var2) throws IOException;

   void softRestart(String var1, Properties var2) throws UnsupportedOperationException, IOException;
}
