package com.solarmetric.jdbc;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.openjpa.lib.jdbc.AbstractJDBCListener;
import org.apache.openjpa.lib.jdbc.JDBCEvent;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Numbers;

public class PerformanceTracker extends AbstractJDBCListener {
   private static Localizer _loc = Localizer.forPackage(PerformanceTracker.class);
   private static Log _log = null;
   private static final String newline = System.getProperty("line.separator");
   public static long executeStatements;
   public static long executeStatementTimes;
   public static long executeStatementMinTime;
   public static long executeStatementMaxTime;
   public static long executePreparedStatements;
   public static long executePreparedStatementTimes;
   public static long executePreparedStatementMinTime;
   public static long executePreparedStatementMaxTime;
   public static long commits;
   public static long commitTimes;
   public static long commitMinTime;
   public static long commitMaxTime;
   public static long rollbacks;
   public static long rollbackTimes;
   public static long rollbackMinTime;
   public static long rollbackMaxTime;
   public static int maxStatementTracking;
   public static Map slowestStatements;
   public static SortedSet statementTimes;

   public static void reset() {
      executeStatements = 0L;
      executeStatementTimes = 0L;
      executeStatementMinTime = -1L;
      executeStatementMaxTime = 0L;
      executePreparedStatements = 0L;
      executePreparedStatementTimes = 0L;
      executePreparedStatementMinTime = -1L;
      executePreparedStatementMaxTime = 0L;
      commits = 0L;
      commitTimes = 0L;
      commitMinTime = -1L;
      commitMaxTime = 0L;
      rollbacks = 0L;
      rollbackTimes = 0L;
      rollbackMinTime = -1L;
      rollbackMaxTime = 0L;
      maxStatementTracking = 20;
      slowestStatements = new ConcurrentHashMap();
      statementTimes = Collections.synchronizedSortedSet(new TreeSet());
   }

   public static void setLog(Log log) {
      _log = log;
   }

   public void afterExecuteStatement(JDBCEvent event) {
      long time;
      if (event.getStatement() instanceof PreparedStatement) {
         time = this.time(event);
         if (time < 0L) {
            return;
         }

         ++executePreparedStatements;
         executePreparedStatementTimes += time;
         if (time < executePreparedStatementMinTime || executePreparedStatementMinTime == -1L) {
            executePreparedStatementMinTime = time;
         }

         if (time > executePreparedStatementMaxTime) {
            executePreparedStatementMaxTime = time;
         }

         executePreparedStatementTimes += time;
         this.trackSlowStatements(event.getSQL(), time);
      } else {
         time = this.time(event);
         if (time < 0L) {
            return;
         }

         ++executeStatements;
         if (time < executeStatementMinTime || executeStatementMinTime == -1L) {
            executeStatementMinTime = time;
         }

         if (time > executeStatementMaxTime) {
            executeStatementMaxTime = time;
         }

         executeStatementTimes += time;
         this.trackSlowStatements(event.getSQL(), time);
      }

   }

   public void trackSlowStatements(String sql, long time) {
      if (sql != null && time != -1L) {
         Long l;
         if (slowestStatements.size() < maxStatementTracking) {
            l = Numbers.valueOf(time);
            slowestStatements.put(sql, l);
            statementTimes.add(l);
         } else if (getFastest(false) < time) {
            getFastest(true);
            l = Numbers.valueOf(time);
            slowestStatements.put(sql, l);
            statementTimes.add(l);
         }

      }
   }

   private static long getFastest(boolean remove) {
      Long fastestTime = Numbers.valueOf(-1L);
      String fastestSQL = null;
      if (statementTimes.size() == 0) {
         return fastestTime;
      } else if (!remove) {
         return (Long)statementTimes.first();
      } else {
         Iterator i = slowestStatements.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            Long time = (Long)entry.getValue();
            String sql = (String)entry.getKey();
            if (fastestTime == -1L) {
               fastestSQL = sql;
               fastestTime = time;
            } else if (time != -1L && time < fastestTime) {
               fastestTime = time;
               fastestSQL = sql;
            }
         }

         if (remove) {
            slowestStatements.remove(fastestSQL);
            statementTimes.remove(fastestTime);
         }

         return fastestTime;
      }
   }

   public void afterCommit(JDBCEvent event) {
      long time = this.time(event);
      if (time >= 0L) {
         ++commits;
         commitTimes += time;
         if (time < commitMinTime || commitMinTime == -1L) {
            commitMinTime = time;
         }

         if (time > commitMaxTime) {
            commitMaxTime = time;
         }

         if (_log != null && _log.isInfoEnabled()) {
            _log.info(this.toString());
         }

      }
   }

   public void afterRollback(JDBCEvent event) {
      long time = this.time(event);
      if (time >= 0L) {
         ++rollbacks;
         rollbackTimes += time;
         if (time < rollbackMinTime || rollbackMinTime == -1L) {
            rollbackMinTime = time;
         }

         if (time > rollbackMaxTime) {
            rollbackMaxTime = time;
         }

      }
   }

   public final long time(JDBCEvent event) {
      JDBCEvent assoc = event.getAssociatedEvent();
      if (assoc == null) {
         return -1L;
      } else {
         long endTime = event.getTime();
         long startTime = assoc.getTime();
         return endTime - startTime;
      }
   }

   public static String summarizePerformance() {
      StringBuffer buffer = new StringBuffer();
      buffer.append(pad(_loc.get("tracker-executestmnt").getMessage(), 25)).append(minMaxAvg(executeStatementMinTime, executeStatementMaxTime, executeStatements, executeStatementTimes)).append(newline);
      buffer.append(pad(_loc.get("tracker-executeprepstmnt").getMessage(), 25)).append(minMaxAvg(executePreparedStatementMinTime, executePreparedStatementMaxTime, executePreparedStatements, executePreparedStatementTimes)).append(newline);
      buffer.append(pad(_loc.get("tracker-commit").getMessage(), 25)).append(minMaxAvg(commitMinTime, commitMaxTime, commits, commitTimes)).append(newline);
      buffer.append(pad(_loc.get("tracker-rollback").getMessage(), 25)).append(minMaxAvg(rollbackMinTime, rollbackMaxTime, rollbacks, rollbackTimes)).append(newline);
      if (slowestStatements.size() > 0) {
         buffer.append(slowestStatements.size()).append(" ").append(_loc.get("tracker-slowest-statements")).append(newline);
         Iterator i = slowestStatements.keySet().iterator();

         while(i.hasNext()) {
            String sql = (String)i.next();
            Long time = (Long)slowestStatements.get(sql);
            buffer.append("   ").append(pad(time + _loc.get("tracker-ms").getMessage(), 13)).append(sql).append(newline);
         }
      }

      return buffer.toString();
   }

   private static StringBuffer pad(String str, int len) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(str);

      for(int i = 0; i < len - str.length(); ++i) {
         buffer.append(' ');
      }

      return buffer;
   }

   private static String minMaxAvg(long min, long max, long count, long total) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(count).append(" ").append(_loc.get("tracker-occurances"));
      if (count > 0L) {
         buffer.append(": ");
         buffer.append(" ").append(_loc.get("tracker-total")).append(": ").append(total).append(_loc.get("tracker-ms"));
         buffer.append(" ").append(_loc.get("tracker-average")).append(": ").append(total / count).append(_loc.get("tracker-ms"));
         buffer.append(" ").append(_loc.get("tracker-min")).append(": ").append(min).append(_loc.get("tracker-ms"));
         buffer.append(" ").append(_loc.get("tracker-max")).append(": ").append(max).append(_loc.get("tracker-ms"));
      }

      return buffer.toString();
   }

   public String toString() {
      return summarizePerformance();
   }

   static {
      reset();
   }
}
