package weblogic.management.runtime;

public interface SpringViewRuntimeMBean extends RuntimeMBean {
   String getBeanId();

   String getApplicationContextDisplayName();

   long getRenderCount();

   long getRenderFailedCount();

   double getAverageRenderTime();
}
