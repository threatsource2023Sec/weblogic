package weblogic.t3.srvr;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@Rank(-100)
@RunLevel(20)
public class EnableListenersService extends AbstractServerService {
   @Inject
   @Named("DeploymentPostAdminServerService")
   private ServerService deploymentPostAdminServerService;
   @Inject
   @Named("StartupClassAfterAppsRunningService")
   private ServerService startupClassAfterAppsRunningService;
   @Inject
   @Named("JobSchedulerService")
   private ServerService jobSchedulerService;
   @Inject
   @Optional
   @Named("JMSServicePostDeploymentImpl")
   private ServerService jMSServicePostDeploymentImpl;
   @Inject
   @Named("ReplicationService")
   private ServerService replicationService;
   @Inject
   @Named("SingletonServicesBatchManager")
   private ServerService singletonServicesBatchManager;

   public void start() throws ServiceFailureException {
      EnableListenersHelper.getInstance().start();
   }

   public void stop() throws ServiceFailureException {
      if (ChannelHelper.isLocalAdminChannelEnabled()) {
         EnableListenersHelper.getInstance().stop();
      }

   }

   public void halt() throws ServiceFailureException {
      if (ChannelHelper.isLocalAdminChannelEnabled()) {
         EnableListenersHelper.getInstance().halt();
      }

   }
}
