package weblogic.time.t3client.internal;

import weblogic.common.T3Exception;
import weblogic.common.T3Executable;
import weblogic.common.T3ServicesDef;
import weblogic.t3.srvr.ExecutionContext;
import weblogic.t3.srvr.T3Srvr;
import weblogic.time.common.ScheduledTriggerDef;
import weblogic.time.common.TimeTriggerException;
import weblogic.time.common.internal.TimeEventGenerator;
import weblogic.time.t3client.ScheduledTrigger;

public final class TimeProxy implements T3Executable {
   public void initialize() {
   }

   public void destroy() {
   }

   public Object execute(ExecutionContext executionContext, Object o) throws T3Exception {
      TimeMsg tm = (TimeMsg)o;
      Object retVal = null;
      ScheduledTriggerDef timeTrigger;
      switch (tm.cmd) {
         case 1:
         case 2:
            ScheduledTrigger st = tm.sch;

            try {
               T3ServicesDef tDef = T3Srvr.getT3Srvr().getT3Services();
               ScheduledTriggerDef timeTrigger = tDef.time().getScheduledTrigger(st.getScheduler(), st.getTrigger());
               retVal = new Integer(timeTrigger.schedule());
               TimeEventGenerator.getOne().register(retVal, timeTrigger);
               break;
            } catch (TimeTriggerException var11) {
               throw new T3Exception("nested exception:", var11);
            }
         case 3:
            timeTrigger = TimeEventGenerator.getOne().unregister(new Integer(tm.key));

            try {
               if (timeTrigger != null) {
                  retVal = new Boolean(timeTrigger.cancel());
               } else {
                  retVal = new Boolean(false);
               }
               break;
            } catch (TimeTriggerException var9) {
               throw new T3Exception("TimeTriggerException: " + var9);
            }
         case 4:
         case 5:
            timeTrigger = TimeEventGenerator.getOne().registered(new Integer(tm.key));

            try {
               if (timeTrigger == null) {
                  throw new TimeTriggerException("Unknown ScheduledTrigger, id=" + tm.key);
               }

               timeTrigger.setDaemon(tm.cmd == 4);
               break;
            } catch (TimeTriggerException var10) {
               throw new T3Exception("TimeTriggerException: " + var10);
            }
         case 6:
            retVal = tm;
            break;
         default:
            throw new T3Exception("Unknown TimeMsg Command: " + tm.cmd);
      }

      return retVal;
   }
}
