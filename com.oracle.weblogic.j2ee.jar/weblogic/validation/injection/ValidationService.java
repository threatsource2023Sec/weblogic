package weblogic.validation.injection;

import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.logging.LoggingHelper;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;

@Service
@Named
@RunLevel(10)
public final class ValidationService extends AbstractServerService {
   @Inject
   @Named("PersistenceRegistrationService")
   private ServerService dependencyOnPersistenceRegistrationService;
   @Inject
   @Named("SNMPAgentDeploymentService")
   private ServerService dependencyOnSNMPAgentDeploymentService;
   private static ValidationService singleton;
   private Logger hibernateValidatorLogger;

   public ValidationService() {
      assert singleton == null : "More than one validation service singleton created";

      singleton = this;
   }

   public static ValidationService getValidationService() {
      if (singleton == null) {
         new ValidationService();
      }

      return singleton;
   }

   public void start() {
      this.hibernateValidatorLogger = Logger.getLogger("org.hibernate.validator");
      LoggingHelper.addServerLoggingHandler(this.hibernateValidatorLogger, false);
   }

   public void stop() {
   }

   public void halt() {
   }
}
