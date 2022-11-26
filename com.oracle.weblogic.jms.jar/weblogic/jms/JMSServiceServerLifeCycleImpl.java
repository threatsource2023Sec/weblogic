package weblogic.jms;

import javax.inject.Singleton;
import javax.jms.JMSException;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.jms.deployer.BEAdminHandler;
import weblogic.jms.interception.service;
import weblogic.jms.module.JMSDeploymentFactory;
import weblogic.jms.module.JMSModuleFactory;
import weblogic.management.ManagementException;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.utils.GenericManagedService;
import weblogic.management.utils.GenericServiceManager;
import weblogic.messaging.dispatcher.DispatcherServerRef;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.server.ActivatedService;
import weblogic.server.ServiceFailureException;

@Service
@Singleton
public final class JMSServiceServerLifeCycleImpl extends ActivatedService {
   public static final boolean DEPLOYSERVICE_START_ALL = false;
   private GenericManagedService beAdminManager;
   private transient JMSService jmsService;
   public static boolean interceptionEnabled = checkInterceptionEnabled();

   private static final boolean checkInterceptionEnabled() {
      boolean interceptionEnabled = false;
      String str = System.getProperty("weblogic.jms.interception.enabled");
      if (str != null && str.equals("true")) {
         interceptionEnabled = true;
      }

      if (interceptionEnabled) {
         System.out.println("***** JMS Interception is enabled *****");
      }

      return interceptionEnabled;
   }

   public JMSServiceServerLifeCycleImpl() {
      DispatcherServerRef.createJmsJavaOid();
      this.beAdminManager = GenericServiceManager.getManager().register(JMSServerMBean.class, BEAdminHandler.class, true);
   }

   public void stopService() throws ServiceFailureException {
      JMSLogger.logJMSSuspending();
      if (this.jmsService != null) {
         this.jmsService.stopAll(false);
      }

      this.beAdminManager.stop();
   }

   public void haltService() throws ServiceFailureException {
      JMSLogger.logJMSForceSuspending();
      if (this.jmsService != null) {
         this.jmsService.stopAll(true);
      }

      this.beAdminManager.stop();
   }

   public boolean startService() throws ServiceFailureException {
      if (interceptionEnabled) {
         try {
            service.initialize();
         } catch (InterceptionServiceException var4) {
            JMSLogger.logJMSFailedInit();
            throw new ServiceFailureException("JMS service failed in initialization - registering with Interception Service", var4);
         }
      }

      try {
         this.jmsService = JMSService.createAndStartService();
         this.beAdminManager.start();
         ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
         afm.addDeploymentFactory(new JMSDeploymentFactory());
         afm.addWblogicModuleFactory(new JMSModuleFactory());
         return true;
      } catch (JMSException var2) {
         JMSLogger.logJMSFailedInit();
         throw new ServiceFailureException("JMS service failed in initialization", var2);
      } catch (ManagementException var3) {
         JMSLogger.logJMSFailedInit();
         throw new ServiceFailureException("JMS service failed in initialization", var3);
      }
   }
}
