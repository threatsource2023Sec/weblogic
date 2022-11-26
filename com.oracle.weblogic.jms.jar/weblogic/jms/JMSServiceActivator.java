package weblogic.jms;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.ServerService;
import weblogic.server.ServiceActivator;

@Service
@Named
@RunLevel(10)
public final class JMSServiceActivator extends ServiceActivator {
   @Inject
   @Named("PartitionRuntimeBuilderService")
   private ServerService dependencyOnPartitionRuntimeBuilderService;
   @Inject
   @Named("TransactionRecoveryNoOpService")
   private ServerService dependencyOnTransactionRecoveryNoOpService;
   @Inject
   @Named("DiagnosticFoundationService")
   private ServerService dependencyOnDiagnosticFoundationService;
   @Inject
   @Named("StoreDeploymentService")
   private ServerService dependencyOnStoreDeploymentService;
   @Inject
   @Named("RMIServerService")
   private ServerService dependencyOnRMIServerService;
   @Inject
   @Named("Configurator")
   private ServerService dependencyOnConfigurator;
   private static JMSServiceActivator INSTANCE;

   public static synchronized JMSServiceActivator getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new JMSServiceActivator();
      }

      return INSTANCE;
   }

   private JMSServiceActivator() {
      super("weblogic.jms.JMSServiceServerLifeCycleImpl");
   }

   public String getName() {
      return "JMS Service";
   }

   public String getVersion() {
      return "JMS 2.0";
   }
}
