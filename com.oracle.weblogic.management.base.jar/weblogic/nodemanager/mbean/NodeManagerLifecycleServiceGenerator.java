package weblogic.nodemanager.mbean;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ManagedExternalServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface NodeManagerLifecycleServiceGenerator {
   NodeManagerLifecycleService getInstance(String var1, int var2, String var3);

   NodeManagerLifecycleService getInstance(MachineMBean var1);

   NodeManagerLifecycleService getInstance(ServerTemplateMBean var1);

   NodeManagerLifecycleService getInstance(ManagedExternalServerMBean var1);

   void checkStartPrivileges(String var1, AuthenticatedSubject var2) throws SecurityException;
}
