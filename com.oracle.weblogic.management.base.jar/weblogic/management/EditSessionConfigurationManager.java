package weblogic.management;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditSessionLifecycleListener;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.server.ServiceFailureException;

@Contract
public interface EditSessionConfigurationManager {
   EditSessionConfigurationManagerMBean createEditSessionConfigurationManagerMBean(RuntimeMBean var1, String var2, AuthenticatedSubject var3) throws ManagementException;

   void destroyAllPartitionEditSessions(String var1, AuthenticatedSubject var2);

   List getEditSessions();

   List getEditSessions(String var1);

   EditAccess lookupEditSession(String var1);

   EditAccess lookupEditSession(String var1, String var2);

   EditAccess createEditSession(String var1, String var2) throws ServiceFailureException, ManagementException;

   void destroyEditSession(EditAccess var1) throws ManagementException;

   void registerSessionLifecycleListener(EditSessionLifecycleListener var1);

   void unregisterSessionLifecycleListener(EditSessionLifecycleListener var1);
}
