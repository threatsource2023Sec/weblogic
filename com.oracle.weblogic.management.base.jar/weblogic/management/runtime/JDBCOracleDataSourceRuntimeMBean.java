package weblogic.management.runtime;

public interface JDBCOracleDataSourceRuntimeMBean extends JDBCDataSourceRuntimeMBean {
   String getServiceName();

   JDBCOracleDataSourceInstanceRuntimeMBean[] getInstances();

   ONSClientRuntimeMBean getONSClientRuntime();

   long getSuccessfulRCLBBasedBorrowCount();

   long getFailedRCLBBasedBorrowCount();

   long getSuccessfulAffinityBasedBorrowCount();

   long getFailedAffinityBasedBorrowCount();
}
