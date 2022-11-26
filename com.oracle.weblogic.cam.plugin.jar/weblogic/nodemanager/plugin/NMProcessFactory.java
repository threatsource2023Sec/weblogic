package weblogic.nodemanager.plugin;

import weblogic.nodemanager.server.NMProcessInfo;

public interface NMProcessFactory {
   ProcessManagementPlugin.Process createProcess(NMProcessInfo var1);

   ProcessManagementPlugin.Process createProcess(NMProcessInfo var1, String var2) throws UnsupportedOperationException;

   boolean isProcessAlive(String var1) throws UnsupportedOperationException;
}
