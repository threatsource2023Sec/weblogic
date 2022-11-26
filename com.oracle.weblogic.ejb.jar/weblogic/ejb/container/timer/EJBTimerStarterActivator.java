package weblogic.ejb.container.timer;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.ejb.container.EJBServiceActivator;
import weblogic.server.ServerService;

@Service
@Named
@RunLevel(20)
public final class EJBTimerStarterActivator extends EJBServiceActivator {
   private static EJBTimerStarterActivator INSTANCE;
   @Inject
   @Named("EnableListenersService")
   private ServerService dependencyOnEnableListenersService;
   @Inject
   @Named("JobSchedulerService")
   private ServerService dependencyOnJobSchedulerService;
   @Inject
   @Named("MDBServiceActivator")
   private ServerService dependencyOnMDBServiceActivator;

   public static EJBTimerStarterActivator getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new EJBTimerStarterActivator();
      }

      return INSTANCE;
   }

   private EJBTimerStarterActivator() {
      super("weblogic.ejb.container.timer.EJBTimerStarter");
   }

   public String getName() {
      return "EJBTimerService";
   }
}
