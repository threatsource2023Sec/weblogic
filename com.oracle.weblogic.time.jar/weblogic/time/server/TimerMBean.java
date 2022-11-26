package weblogic.time.server;

import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.TimeServiceRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.time.common.internal.TimeEventGenerator;

public final class TimerMBean extends RuntimeMBeanDelegate implements TimeServiceRuntimeMBean {
   private final TimeEventGenerator teg;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public TimerMBean(TimeEventGenerator teg) throws ManagementException {
      super(teg.getRuntimeName());
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setTimeServiceRuntime(this);
      this.teg = teg;
   }

   public int getScheduledTriggerCount() {
      return this.teg.getTriggerInstanceCount() - this.teg.getTriggerExpiredCount();
   }

   public int getExecutionsPerMinute() {
      return this.teg.getAvgExecCount();
   }

   public int getExecutionCount() {
      return this.teg.getExecuteCount();
   }

   public int getExceptionCount() {
      return this.teg.getExceptionCount();
   }
}
