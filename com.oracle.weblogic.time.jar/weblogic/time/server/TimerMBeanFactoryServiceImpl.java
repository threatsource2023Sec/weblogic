package weblogic.time.server;

import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.time.api.TimerMBeanFactoryService;
import weblogic.time.common.internal.TimeEventGenerator;

@Service
public class TimerMBeanFactoryServiceImpl implements TimerMBeanFactoryService {
   public void createTimerMBean(ThreadGroup threadGroup) throws ManagementException {
      TimeEventGenerator timer = TimeEventGenerator.init(threadGroup);
      new TimerMBean(timer);
   }
}
