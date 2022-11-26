package weblogic.management.runtime;

import weblogic.diagnostics.accessor.runtime.WlstoreArchiveRuntimeMBean;
import weblogic.management.ManagementException;

public interface WLDFWlstoreArchiveRuntimeMBean extends WLDFEditableArchiveRuntimeMBean, WlstoreArchiveRuntimeMBean {
   int purgeDataRetirementTasks(long var1) throws ManagementException;
}
