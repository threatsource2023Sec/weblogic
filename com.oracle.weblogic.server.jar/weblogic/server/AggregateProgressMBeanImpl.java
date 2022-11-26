package weblogic.server;

import java.util.HashMap;
import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.runtime.AggregateProgressMBean;
import weblogic.management.runtime.ProgressMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.utils.progress.ProgressTrackerRegistrar;
import weblogic.utils.progress.ProgressTrackerService;
import weblogic.utils.progress.client.ProgressBean;

public class AggregateProgressMBeanImpl extends RuntimeMBeanDelegate implements AggregateProgressMBean {
   private final ProgressTrackerRegistrar registrar;
   private final Map children = new HashMap();

   public AggregateProgressMBeanImpl(String nameArg, RuntimeMBean parentArg, ProgressTrackerRegistrar registrar) throws ManagementException {
      super(nameArg, parentArg);
      this.registrar = registrar;
   }

   public ProgressMBean[] getProgress() {
      synchronized(this.children) {
         return (ProgressMBean[])this.children.values().toArray(new ProgressMBean[this.children.size()]);
      }
   }

   public ProgressMBean lookupProgress(String subsystemName) {
      synchronized(this.children) {
         return (ProgressMBean)this.children.get(subsystemName);
      }
   }

   public ProgressMBean createProgress(String subsystemName) throws ManagementException {
      ProgressTrackerService service = this.registrar.findProgressTrackerSubsystem(subsystemName);
      if (service == null) {
         throw new IllegalStateException("There was no progress subsystem with name " + subsystemName);
      } else {
         synchronized(this.children) {
            if (this.children.containsKey(subsystemName)) {
               throw new ManagementException("There is already a progress meter subsystem with name " + subsystemName);
            } else {
               ProgressBean subsystem = service.getProgress();
               ProgressMBeanImpl retVal = new ProgressMBeanImpl(subsystem, this);
               this.children.put(subsystemName, retVal);
               return retVal;
            }
         }
      }
   }

   public void destroyProgress(ProgressMBean mbean) throws ManagementException {
      String subsystemName = mbean.getName();
      ProgressTrackerService service = this.registrar.findProgressTrackerSubsystem(subsystemName);
      if (service == null) {
         throw new IllegalStateException("There was no progress subsystem with name " + subsystemName);
      } else {
         synchronized(this.children) {
            ProgressMBeanImpl progressMBean = (ProgressMBeanImpl)this.children.get(subsystemName);
            if (progressMBean == null) {
               throw new ManagementException("There is no progress meter subsystem with name " + subsystemName);
            } else {
               this.children.remove(subsystemName);
            }
         }
      }
   }

   public String getAggregateState() {
      return enumToString(this.registrar.getAggregateProgress().getAggregateState(new String[0]));
   }

   static String enumToString(ProgressTrackerService.ProgressState state) {
      switch (state) {
         case IN_PROGRESS:
            return "IN_PROGRESS";
         case FINAL:
            return "FINAL";
         case FAILED:
            return "FAILED";
         default:
            throw new AssertionError("Unknown state: " + state);
      }
   }

   public String toString() {
      return "AggregateProgressMBeanImpl(" + System.identityHashCode(this) + ")";
   }
}
