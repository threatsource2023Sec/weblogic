package weblogic.diagnostics.accessor.runtime;

import weblogic.management.runtime.RuntimeMBean;

public interface ArchiveRuntimeMBean extends RuntimeMBean {
   long getRecordSeekCount();

   long getRecordSeekTime();

   long getRetrievedRecordCount();

   long getRecordRetrievalTime();
}
