package weblogic.nodemanager.mbean;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ManagedExternalServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.nodemanager.ScriptExecutionFailureException;

@Contract
public interface NodeManagerLifecycleService {
   void initState(ServerTemplateMBean var1) throws IOException;

   void initState(ManagedExternalServerMBean var1) throws IOException;

   String getState(ServerTemplateMBean var1, int var2) throws IOException;

   String getState(ServerTemplateMBean var1) throws IOException;

   String getState(ManagedExternalServerMBean var1, int var2) throws IOException;

   String getState(ManagedExternalServerMBean var1) throws IOException;

   void syncMachineIfNecessary(MachineMBean var1) throws IOException;

   NodeManagerTask start(ServerTemplateMBean var1, String var2) throws IOException;

   NodeManagerTask start(ServerTemplateMBean var1) throws IOException;

   NodeManagerTask start(ManagedExternalServerMBean var1) throws IOException;

   NodeManagerTask start(SystemComponentMBean var1, Properties var2) throws IOException;

   void remove(ServerTemplateMBean var1) throws IOException;

   void remove(ManagedExternalServerMBean var1) throws IOException;

   void kill(ServerTemplateMBean var1) throws IOException;

   void kill(ManagedExternalServerMBean var1) throws IOException;

   void kill(SystemComponentMBean var1, Properties var2) throws IOException;

   void softRestart(SystemComponentMBean var1, Properties var2) throws IOException;

   String getStates(boolean var1, int var2) throws IOException;

   void runScript(File var1, long var2) throws IOException, ScriptExecutionFailureException;

   String getProgress(ServerTemplateMBean var1) throws IOException;
}
