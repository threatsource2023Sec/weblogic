package weblogic.diagnostics.debug;

import java.util.logging.Logger;

public interface DebugProvider {
   String getName();

   String getCommandLineOverridePrefix();

   void intializeDebugScopes() throws DebugScopeInitializationException;

   DebugScopeTree getDebugScopeTree() throws DebugScopeInitializationException;

   Object getDebugConfiguration() throws DebugBeanConfigurationException;

   Logger getLogger();
}
