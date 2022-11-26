package weblogic.server;

import java.util.List;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ProgressMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.utils.progress.client.ProgressBean;

public class ProgressMBeanImpl extends RuntimeMBeanDelegate implements ProgressMBean {
   private final ProgressBean progress;

   public ProgressMBeanImpl(ProgressBean subsystem, RuntimeMBean parent) throws ManagementException {
      super(subsystem.getName(), parent);
      this.progress = subsystem;
   }

   public String[] getCurrentWork() {
      List retVal = this.progress.getCurrentWork();
      return (String[])retVal.toArray(new String[retVal.size()]);
   }

   public String getState() {
      return AggregateProgressMBeanImpl.enumToString(this.progress.getState());
   }

   public String toString() {
      return "ProgressMBeanImpl(" + this.progress + "," + System.identityHashCode(this) + ")";
   }
}
