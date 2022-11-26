package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import java.util.Date;
import weblogic.utils.StackTraceUtils;

public class ProfileClosedUsage {
   private ConnectionPoolProfiler profiler;
   private volatile SQLException whereClosed;

   public void setProfiler(ConnectionPoolProfiler profiler) {
      this.profiler = profiler;
   }

   public boolean isClosedUsageEnabled() {
      return this.profiler != null ? this.profiler.isClosedUsageEnabled() : false;
   }

   public void saveWhereClosed() {
      if (this.isClosedUsageEnabled()) {
         this.whereClosed = new SQLException("Where closed: " + Thread.currentThread().toString());
      }

   }

   public SQLException addClosedUsageProfilingRecord() {
      if (this.isClosedUsageEnabled()) {
         this.profiler.addClosedUsageData(StackTraceUtils.throwable2StackTrace(new Throwable(Thread.currentThread().toString())), StackTraceUtils.throwable2StackTrace(this.whereClosed), new Date());
      }

      return this.whereClosed;
   }

   public void clearWhereClosed() {
      this.whereClosed = null;
   }

   public Throwable getWhereClosed() {
      return this.whereClosed;
   }
}
