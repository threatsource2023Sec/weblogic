package weblogic.diagnostics.accessor.runtime;

import weblogic.management.ManagementException;

public interface EditableArchiveRuntimeMBean extends ArchiveRuntimeMBean {
   int getDataRetirementCycles();

   long getDataRetirementTotalTime();

   long getLastDataRetirementStartTime();

   long getLastDataRetirementTime();

   long getRetiredRecordCount();

   DataRetirementTaskRuntimeMBean performRetirement() throws ManagementException;

   DataRetirementTaskRuntimeMBean[] getDataRetirementTasks() throws ManagementException;

   int purgeDataRetirementTasks(long var1) throws ManagementException;
}
