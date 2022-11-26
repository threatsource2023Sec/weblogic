package weblogic.management.runtime;

public interface CoherenceClusterRuntimeMBean extends RuntimeMBean {
   String getClusterName();

   Integer getClusterSize();

   String getLicenseMode();

   String[] getMembers();

   String getVersion();
}
