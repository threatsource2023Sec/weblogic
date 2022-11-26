package weblogic.ejb.container.timer;

import java.util.Iterator;
import java.util.Map;
import javax.ejb.ScheduleExpression;
import javax.ejb.TimerConfig;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.container.internal.MethodDescriptor;

public class TimerManagerHelper {
   private TimerManagerHelper() {
   }

   public static void initializeAutoCreatedTimers(TimerManager tm, Map autoTimers, Map persistedTimers) {
      initializeAutoCreatedTimers(tm, autoTimers, persistedTimers, (Map)null);
   }

   public static void initializeAutoCreatedTimers(TimerManager tm, Map autoTimers, Map persistedTimers, Map isTransactionalMap) {
      Iterator var4 = autoTimers.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry me = (Map.Entry)var4.next();
         Map.Entry tc = (Map.Entry)me.getKey();
         MethodDescriptor md = (MethodDescriptor)me.getValue();
         TimerImpl timer = persistedTimers != null ? getMatchedTimer(tc, persistedTimers, md) : null;
         if (timer == null) {
            boolean isTransactional = md.requiresTransaction();
            if (isTransactionalMap != null) {
               isTransactional = (Boolean)isTransactionalMap.get(md);
            }

            tm.createTimer(new Integer(1), (ScheduleExpression)tc.getKey(), (TimerConfig)tc.getValue(), isTransactional, true, md);
         }
      }

   }

   private static TimerImpl getMatchedTimer(Map.Entry tc, Map persistedTimers, MethodDescriptor md) {
      String sigToMatch = DDUtils.getMethodSignature(md.getMethod());
      String timerConfigToMatch = tc.toString();
      Iterator var5 = persistedTimers.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry me = (Map.Entry)var5.next();
         if (((Map.Entry)me.getKey()).toString().equals(timerConfigToMatch)) {
            TimerImpl ti = (TimerImpl)me.getValue();
            if (sigToMatch.equals(ti.getCallbackMethodSignature())) {
               return ti;
            }
         }
      }

      return null;
   }
}
