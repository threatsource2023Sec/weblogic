package com.octetstring.vde.util;

import com.octetstring.nls.Messages;
import java.util.Calendar;
import java.util.Vector;

public class TimedActivityThread extends Thread {
   private static Vector activities = null;
   private static TimedActivityThread instance = null;

   private TimedActivityThread() {
      activities = new Vector();
   }

   public static TimedActivityThread getInstance() {
      if (instance == null) {
         instance = new TimedActivityThread();
      }

      return instance;
   }

   public void addActivity(TimedActivityTask tat) {
      synchronized(activities) {
         activities.addElement(tat);
      }
   }

   public void run() {
      while(true) {
         try {
            sleep(5000L);
            Calendar nowtime = Calendar.getInstance();
            synchronized(activities) {
               for(int ct = 0; ct < activities.size(); ++ct) {
                  TimedActivityTask tat = (TimedActivityTask)activities.elementAt(ct);
                  if (tat.getHour() == nowtime.get(11)) {
                     if (tat.getMinute() == nowtime.get(12)) {
                        if (!tat.hasRun()) {
                           tat.runTask();
                           tat.setRun(true);
                        }
                     } else {
                        tat.setRun(false);
                     }
                  } else {
                     tat.setRun(false);
                  }
               }
            }
         } catch (InterruptedException var7) {
         }
      }
   }

   public void runOnDemand() {
      try {
         Calendar nowtime = Calendar.getInstance();
         synchronized(activities) {
            for(int ct = 0; ct < activities.size(); ++ct) {
               TimedActivityTask tat = (TimedActivityTask)activities.elementAt(ct);
               if (tat.getHour() == nowtime.get(11)) {
                  if (tat.getMinute() != nowtime.get(12) && tat.getMinute() + 1 != nowtime.get(12)) {
                     tat.setRun(false);
                  } else if (!tat.hasRun()) {
                     tat.runTask();
                     tat.setRun(true);
                  }
               } else {
                  tat.setRun(false);
               }
            }
         }
      } catch (Exception var7) {
         Logger.getInstance().log(0, this, Messages.getString("Error_running_timed_activity_task") + var7.getMessage());
      }

   }
}
