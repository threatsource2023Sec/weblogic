package org.apache.openjpa.datacache;

import java.security.AccessController;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.UserException;
import serp.util.Strings;

public class DataCacheScheduler implements Runnable {
   private static final Localizer _loc = Localizer.forPackage(DataCacheScheduler.class);
   private Map _caches = new ConcurrentHashMap();
   private boolean _stop = false;
   private int _interval = 2;
   private Log _log;
   private Thread _thread;

   public DataCacheScheduler(OpenJPAConfiguration conf) {
      this._log = conf.getLogFactory().getLog("openjpa.DataCache");
   }

   public int getInterval() {
      return this._interval;
   }

   public void setInterval(int interval) {
      this._interval = interval;
   }

   public synchronized void stop() {
      this._stop = true;
   }

   private boolean isStopped() {
      return this._stop;
   }

   public synchronized void scheduleEviction(DataCache cache, String times) {
      if (times != null) {
         Schedule schedule = new Schedule(times);
         this._caches.put(cache, schedule);
         this._stop = false;
         if (this._thread == null) {
            this._thread = (Thread)AccessController.doPrivileged(J2DoPrivHelper.newDaemonThreadAction(this, _loc.get("scheduler-name").getMessage()));
            this._thread.start();
            if (this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("scheduler-start", (Object)this._thread.getName()));
            }
         }

      }
   }

   public synchronized void removeFromSchedule(DataCache cache) {
      this._caches.remove(cache);
      if (this._caches.size() == 0) {
         this.stop();
      }

   }

   public void run() {
      if (this._log.isTraceEnabled()) {
         this._log.trace(_loc.get("scheduler-interval", (Object)(this._interval + "")));
      }

      Date lastRun = new Date();
      DateFormat fom = new SimpleDateFormat("E HH:mm:ss");

      while(!this.isStopped()) {
         try {
            Thread.sleep((long)(this._interval * 60 * 1000));
            Date now = new Date();
            Iterator i = this._caches.entrySet().iterator();

            while(i.hasNext()) {
               Map.Entry entry = (Map.Entry)i.next();
               DataCache cache = (DataCache)entry.getKey();
               Schedule schedule = (Schedule)entry.getValue();
               if (schedule.matches(lastRun, now)) {
                  if (this._log.isTraceEnabled()) {
                     this._log.trace(_loc.get("scheduler-clear", cache.getName(), fom.format(now)));
                  }

                  this.evict(cache);
               }
            }

            lastRun = now;
         } catch (Exception var10) {
            throw (new InvalidStateException(_loc.get("scheduler-fail"), var10)).setFatal(true);
         }
      }

      this._log.info(_loc.get("scheduler-stop"));
      synchronized(this) {
         if (this.isStopped()) {
            this._thread = null;
         }

      }
   }

   protected void evict(DataCache cache) {
      cache.clear();
   }

   private static class Schedule {
      static final int[] WILDCARD = new int[0];
      static final int[] UNITS = new int[]{2, 5, 7, 11, 12};
      final int[] month;
      final int[] dayOfMonth;
      final int[] dayOfWeek;
      final int[] hour;
      final int[] min;

      public Schedule(String date) {
         StringTokenizer token = new StringTokenizer(date, " \t");
         if (token.countTokens() != 5) {
            throw (new UserException(DataCacheScheduler._loc.get("bad-count", (Object)date))).setFatal(true);
         } else {
            try {
               this.min = this.parse(token.nextToken(), 0, 60);
               this.hour = this.parse(token.nextToken(), 0, 24);
               this.dayOfMonth = this.parse(token.nextToken(), 1, 31);
               this.month = this.parse(token.nextToken(), 1, 13);
               this.dayOfWeek = this.parse(token.nextToken(), 1, 8);
            } catch (Throwable var4) {
               throw (new UserException(DataCacheScheduler._loc.get("bad-schedule", (Object)date), var4)).setFatal(true);
            }
         }
      }

      private int[] parse(String token, int min, int max) {
         if ("*".equals(token.trim())) {
            return WILDCARD;
         } else {
            String[] tokens = Strings.split(token, ",", 0);
            int[] times = new int[tokens.length];
            int i = 0;

            while(true) {
               if (i < tokens.length) {
                  try {
                     times[i] = Integer.parseInt(tokens[i]);
                  } catch (Throwable var8) {
                     throw (new UserException(DataCacheScheduler._loc.get("not-number", (Object)token))).setFatal(true);
                  }

                  if (times[i] >= min && times[i] < max) {
                     ++i;
                     continue;
                  }

                  throw (new UserException(DataCacheScheduler._loc.get("not-range", token, String.valueOf(min), String.valueOf(max)))).setFatal(true);
               }

               return times;
            }
         }
      }

      boolean matches(Date last, Date now) {
         Calendar time = Calendar.getInstance();
         time.setTime(now);
         time.set(13, 0);
         time.set(14, 0);
         int[][] all = new int[][]{this.month, this.dayOfMonth, this.dayOfWeek, this.hour, this.min};
         return this.matches(last, now, time, all, 0);
      }

      private boolean matches(Date last, Date now, Calendar time, int[][] times, int depth) {
         if (depth == UNITS.length) {
            Date compare = time.getTime();
            return compare.compareTo(last) >= 0 && compare.compareTo(now) < 0;
         } else {
            if (times[depth] != WILDCARD) {
               for(int i = 0; i < times[depth].length; ++i) {
                  time.set(UNITS[depth], times[depth][i]);
                  if (this.matches(last, now, time, times, depth + 1)) {
                     return true;
                  }
               }
            }

            return this.matches(last, now, time, times, depth + 1);
         }
      }
   }
}
