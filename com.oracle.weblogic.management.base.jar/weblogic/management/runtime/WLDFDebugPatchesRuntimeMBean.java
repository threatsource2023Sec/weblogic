package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface WLDFDebugPatchesRuntimeMBean extends RuntimeMBean {
   String[] getAvailableDebugPatches();

   String[] getActiveDebugPatches();

   String showDebugPatchInfo(String var1);

   WLDFDebugPatchTaskRuntimeMBean activateDebugPatch(String var1, String var2, String var3, String var4) throws ManagementException;

   WLDFDebugPatchTaskRuntimeMBean deactivateDebugPatches(String var1, String var2, String var3, String var4) throws ManagementException;

   WLDFDebugPatchTaskRuntimeMBean deactivateAllDebugPatches() throws ManagementException;

   WLDFDebugPatchTaskRuntimeMBean[] getDebugPatchTasks();

   WLDFDebugPatchTaskRuntimeMBean lookupDebugPatchTask(String var1) throws ManagementException;

   void clearDebugPatchTasks();
}
