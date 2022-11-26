package weblogic.diagnostics.accessor.runtime;

import weblogic.management.runtime.TaskRuntimeMBean;

public interface DataRetirementTaskRuntimeMBean extends TaskRuntimeMBean {
   long getRetiredRecordsCount();
}
