package weblogic.management.provider.internal;

import javax.management.MBeanServer;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.provider.EditAccess;
import weblogic.server.ServiceFailureException;

@Contract
public interface EditSessionServerManager {
   String MBEANSERVER_JNDI_NAME_PREFIX = "weblogic.management.mbeanservers.editsession.";

   void startNamedEditSessionServer(EditAccess var1) throws ServiceFailureException;

   void stopNamedEditSessionServer(EditAccess var1) throws ServiceFailureException;

   String constructJndiName(String var1, String var2);

   MBeanServer getPartitionDefaultMBeanServer(String var1);
}
