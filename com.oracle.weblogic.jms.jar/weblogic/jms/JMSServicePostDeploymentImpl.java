package weblogic.jms;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(20)
public final class JMSServicePostDeploymentImpl extends AbstractServerService {
   @Inject
   private JMSServiceServerLifeCycleImpl dependency;
   @Inject
   @Named("DeploymentPostAdminServerService")
   private ServerService dependencyOnDeploymentPostAdminServerService;
   private JMSService jmsService = JMSService.getJMSServiceWithManagementException();

   public JMSServicePostDeploymentImpl() throws ManagementException, JMSException, ServiceFailureException {
   }

   public void start() throws ServiceFailureException {
      this.jmsService.postDeploymentStart();
   }

   public void stop() {
      this.jmsService.postDeploymentStop();
   }

   public void halt() {
      this.jmsService.postDeploymentHalt();
   }
}
