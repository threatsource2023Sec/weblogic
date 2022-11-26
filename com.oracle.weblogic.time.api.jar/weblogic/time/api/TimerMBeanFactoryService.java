package weblogic.time.api;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.ManagementException;

@Contract
public interface TimerMBeanFactoryService {
   void createTimerMBean(ThreadGroup var1) throws ManagementException;
}
