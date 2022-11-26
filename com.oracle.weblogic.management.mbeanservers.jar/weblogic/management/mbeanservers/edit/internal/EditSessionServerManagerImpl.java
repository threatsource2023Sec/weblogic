package weblogic.management.mbeanservers.edit.internal;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.MBeanServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementLogger;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.internal.EditSessionServerManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.FileUtils;

@Service
@Named
public final class EditSessionServerManagerImpl extends AbstractServerService implements EditSessionServerManager {
   private final Map servers = Maps.newIdentityHashMap();
   private final Map defaultServers = new HashMap();
   @Inject
   private ServiceLocator locator;

   public void startNamedEditSessionServer(EditAccess editAccess) throws ServiceFailureException {
      EditSessionServer server = new EditSessionServer(editAccess);
      this.locator.inject(server);
      this.servers.put(editAccess, server);
      if (editAccess.isDefault()) {
         String partitionName = editAccess.getPartitionName();
         if (partitionName == null) {
            partitionName = "default";
         }

         this.defaultServers.put(partitionName, server);
      }

      server.start();
   }

   public void stopNamedEditSessionServer(EditAccess editAccess) throws ServiceFailureException {
      EditSessionServer server = (EditSessionServer)this.servers.remove(editAccess);
      if (editAccess.isDefault()) {
         EditSessionServer var10000 = (EditSessionServer)this.defaultServers.remove(editAccess.getPartitionName());
      }

      if (server != null) {
         server.stop();
      }

   }

   public String constructJndiName(String partitionName, String editSessionName) {
      if (partitionName == null || partitionName.isEmpty()) {
         partitionName = "DOMAIN";
      }

      if (editSessionName == null || editSessionName.isEmpty()) {
         editSessionName = "default";
      }

      return "DOMAIN".equals(partitionName) && "default".equals(editSessionName) ? "weblogic.management.mbeanservers.edit" : "weblogic.management.mbeanservers.editsession." + FileUtils.mapNameToFileName(partitionName, false) + "." + FileUtils.mapNameToFileName(editSessionName, false);
   }

   public void stop() throws ServiceFailureException {
      Iterator var1 = this.servers.values().iterator();

      while(var1.hasNext()) {
         EditSessionServer server = (EditSessionServer)var1.next();

         try {
            server.stop();
         } catch (ServiceFailureException var4) {
            ManagementLogger.logMBeanServerInitException(var4);
         }
      }

      this.servers.clear();
      this.defaultServers.clear();
   }

   public void halt() throws ServiceFailureException {
      this.stop();
   }

   public MBeanServer getPartitionDefaultMBeanServer(String partitionName) {
      EditSessionServer editSessionServer = (EditSessionServer)this.defaultServers.get(partitionName);
      return editSessionServer != null ? editSessionServer.getMBeanServer() : null;
   }
}
