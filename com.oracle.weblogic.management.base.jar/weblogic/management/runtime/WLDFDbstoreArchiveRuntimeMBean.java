package weblogic.management.runtime;

import weblogic.diagnostics.accessor.runtime.DbstoreArchiveRuntimeMBean;
import weblogic.management.ManagementException;

public interface WLDFDbstoreArchiveRuntimeMBean extends WLDFEditableArchiveRuntimeMBean, DbstoreArchiveRuntimeMBean {
   int purgeDataRetirementTasks(long var1) throws ManagementException;
}
