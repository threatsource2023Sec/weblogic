package weblogic.management.runtime;

public interface WLDFSystemResourceControlRuntimeMBean extends RuntimeMBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   WLDFHarvesterManagerRuntimeMBean getHarvesterManagerRuntime();

   WLDFWatchManagerRuntimeMBean getWatchManagerRuntime();

   boolean isExternalResource();
}
