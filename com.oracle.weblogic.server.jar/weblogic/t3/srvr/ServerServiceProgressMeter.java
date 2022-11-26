package weblogic.t3.srvr;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.ErrorService;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InstanceLifecycleEvent;
import org.glassfish.hk2.api.InstanceLifecycleEventType;
import org.glassfish.hk2.api.InstanceLifecycleListener;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.runlevel.ChangeableRunLevelFuture;
import org.glassfish.hk2.runlevel.ErrorInformation;
import org.glassfish.hk2.runlevel.RunLevel;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.glassfish.hk2.runlevel.RunLevelListener;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.RuntimeAccess;
import weblogic.utils.progress.ProgressTrackerRegistrar;
import weblogic.utils.progress.ProgressTrackerService;
import weblogic.utils.progress.ProgressWorkHandle;

@Service
public class ServerServiceProgressMeter implements InstanceLifecycleListener, RunLevelListener, ErrorService {
   static final Filter FILTER = new Filter() {
      public boolean matches(Descriptor d) {
         return d.getScope() != null && d.getScope().equals(RunLevel.class.getName());
      }
   };
   @Inject
   private ProgressTrackerRegistrar registrar;
   @Inject
   private Provider runtimeAccess;
   ProgressTrackerService meter;
   private final Map work = new HashMap();

   @PostConstruct
   private void postConstruct() {
      this.meter = this.registrar.registerProgressTrackerSubsystem("Server Services");
   }

   @PreDestroy
   private void preDestroy() {
      if (this.meter != null) {
         this.meter.close();
      }

   }

   public void onProgress(ChangeableRunLevelFuture currentJob, int levelAchieved) {
      if (!currentJob.isDown()) {
         if (levelAchieved == 15) {
            String startingMode = ((RuntimeAccess)this.runtimeAccess.get()).getServer().getStartupMode();
            if (!"RUNNING".equals(startingMode)) {
               this.meter.finished();
            }
         } else if (levelAchieved == 20) {
            this.meter.finished();
         }
      }
   }

   public void onCancelled(RunLevelFuture currentJob, int levelAchieved) {
      if (currentJob.isUp()) {
         this.meter.failed();
      }
   }

   public void onError(RunLevelFuture currentJob, ErrorInformation errorInformation) {
      if (currentJob.isUp()) {
         this.meter.failed();
      }
   }

   public Filter getFilter() {
      return FILTER;
   }

   public void lifecycleEvent(InstanceLifecycleEvent lifecycleEvent) {
      ActiveDescriptor descriptor;
      if (lifecycleEvent.getEventType().equals(InstanceLifecycleEventType.PRE_PRODUCTION)) {
         descriptor = lifecycleEvent.getActiveDescriptor();
         String workDescription = descriptor.getImplementation();
         String name = descriptor.getName();
         if (name != null) {
            workDescription = workDescription + "(" + name + ")";
         }

         ProgressWorkHandle handle = this.meter.startWork(workDescription);
         synchronized(this.work) {
            this.work.put(descriptor, handle);
         }
      } else {
         if (lifecycleEvent.getEventType().equals(InstanceLifecycleEventType.POST_PRODUCTION)) {
            descriptor = lifecycleEvent.getActiveDescriptor();
            ProgressWorkHandle handle = null;
            synchronized(this.work) {
               handle = (ProgressWorkHandle)this.work.remove(descriptor);
            }

            if (handle == null) {
               return;
            }

            handle.complete();
         }

      }
   }

   public void onFailure(org.glassfish.hk2.api.ErrorInformation errorInformation) throws MultiException {
      Descriptor desc = errorInformation.getDescriptor();
      if (desc instanceof ActiveDescriptor) {
         ActiveDescriptor descriptor = (ActiveDescriptor)desc;
         ProgressWorkHandle handle = null;
         synchronized(this.work) {
            handle = (ProgressWorkHandle)this.work.remove(descriptor);
         }

         if (handle != null) {
            handle.fail();
         }
      }
   }
}
