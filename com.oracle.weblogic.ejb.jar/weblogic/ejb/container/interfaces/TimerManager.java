package weblogic.ejb.container.interfaces;

import java.util.Collection;
import java.util.Date;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import weblogic.ejb.WLTimerInfo;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.management.runtime.EJBTimerRuntimeMBean;

public interface TimerManager {
   void setup(EJBTimerRuntimeMBean var1) throws WLDeploymentException;

   void perhapsStart();

   void start();

   void undeploy();

   void enableDisabledTimers();

   Timer createTimer(Object var1, Date var2, long var3, TimerConfig var5, WLTimerInfo var6);

   Timer createTimer(Object var1, Date var2, TimerConfig var3, WLTimerInfo var4);

   Timer createTimer(Object var1, long var2, long var4, TimerConfig var6, WLTimerInfo var7);

   Timer createTimer(Object var1, long var2, TimerConfig var4, WLTimerInfo var5);

   Timer createTimer(Object var1, ScheduleExpression var2, TimerConfig var3, boolean var4, boolean var5, weblogic.ejb.container.internal.MethodDescriptor var6);

   Timer createTimer(Object var1, ScheduleExpression var2, TimerConfig var3);

   Collection getTimers(Object var1);

   Collection getAllTimers();

   void removeTimersForPK(Object var1);
}
