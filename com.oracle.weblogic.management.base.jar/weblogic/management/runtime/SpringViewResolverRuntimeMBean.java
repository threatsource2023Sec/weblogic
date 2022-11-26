package weblogic.management.runtime;

public interface SpringViewResolverRuntimeMBean extends RuntimeMBean {
   String getBeanId();

   String getApplicationContextDisplayName();

   long getResolveViewNameCount();

   long getResolveViewNameFailedCount();

   double getAverageResolveViewNameTime();
}
