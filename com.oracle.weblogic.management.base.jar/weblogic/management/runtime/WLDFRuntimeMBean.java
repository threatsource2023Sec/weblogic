package weblogic.management.runtime;

public interface WLDFRuntimeMBean extends RuntimeMBean {
   WLDFAccessRuntimeMBean getWLDFAccessRuntime();

   void setWLDFAccessRuntime(WLDFAccessRuntimeMBean var1);

   WLDFArchiveRuntimeMBean[] getWLDFArchiveRuntimes();

   boolean addWLDFArchiveRuntime(WLDFArchiveRuntimeMBean var1);

   boolean removeWLDFArchiveRuntime(WLDFArchiveRuntimeMBean var1);

   WLDFHarvesterRuntimeMBean getWLDFHarvesterRuntime();

   void setWLDFHarvesterRuntime(WLDFHarvesterRuntimeMBean var1);

   WLDFImageRuntimeMBean getWLDFImageRuntime();

   void setWLDFImageRuntime(WLDFImageRuntimeMBean var1);

   WLDFInstrumentationRuntimeMBean[] getWLDFInstrumentationRuntimes();

   WLDFInstrumentationRuntimeMBean lookupWLDFInstrumentationRuntime(String var1);

   boolean addWLDFInstrumentationRuntime(WLDFInstrumentationRuntimeMBean var1);

   boolean removeWLDFInstrumentationRuntime(WLDFInstrumentationRuntimeMBean var1);

   WLDFWatchNotificationRuntimeMBean getWLDFWatchNotificationRuntime();

   void setWLDFWatchNotificationRuntime(WLDFWatchNotificationRuntimeMBean var1);

   WLDFControlRuntimeMBean getWLDFControlRuntime();

   void setWLDFControlRuntime(WLDFControlRuntimeMBean var1);

   WLDFDebugPatchesRuntimeMBean getWLDFDebugPatchesRuntime();

   void setWLDFDebugPatchesRuntime(WLDFDebugPatchesRuntimeMBean var1);
}
