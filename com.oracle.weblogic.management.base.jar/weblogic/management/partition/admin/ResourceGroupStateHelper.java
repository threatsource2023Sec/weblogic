package weblogic.management.partition.admin;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.ResourceGroupMBean;

@Contract
public interface ResourceGroupStateHelper {
   boolean isDesiredStateAdmin(ResourceGroupMBean var1) throws ResourceGroupLifecycleException;

   boolean isDesiredStateRunning(ResourceGroupMBean var1) throws ResourceGroupLifecycleException;

   boolean isActiveInAdmin(ResourceGroupMBean var1) throws ResourceGroupLifecycleException;

   boolean isActiveInRunning(ResourceGroupMBean var1) throws ResourceGroupLifecycleException;

   boolean isShuttingDown(ResourceGroupMBean var1) throws ResourceGroupLifecycleException;

   boolean isSuspending(ResourceGroupMBean var1) throws ResourceGroupLifecycleException;

   void markShutdownAsAdmin() throws ResourceGroupLifecycleException;

   void markAdminAsRunning() throws ResourceGroupLifecycleException;

   void markRunningAsAdmin() throws ResourceGroupLifecycleException;

   void markAdminAsShutdown() throws ResourceGroupLifecycleException;
}
