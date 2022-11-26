package weblogic.platform;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class VM15 extends VM {
   public String threadDumpAsString() {
      ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
      if (threadMXBean == null) {
         return null;
      } else {
         long[] threadIds = threadMXBean.getAllThreadIds();
         if (threadIds != null && threadIds.length != 0) {
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds, true, true);
            StringBuilder sb = new StringBuilder();
            ThreadInfo[] var5 = threadInfos;
            int var6 = threadInfos.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ThreadInfo info = var5[var7];
               if (info != null) {
                  sb.append(formattedThreadInfo(info)).append("\n");
               }
            }

            return sb.toString();
         } else {
            return null;
         }
      }
   }

   public String threadDumpAsString(boolean lockedMonitors, boolean lockedSynchronizers) {
      ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
      if (threadMXBean == null) {
         return null;
      } else {
         long[] threadIds = threadMXBean.getAllThreadIds();
         if (threadIds != null && threadIds.length != 0) {
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds, lockedMonitors, lockedSynchronizers);
            StringBuilder sb = new StringBuilder();
            ThreadInfo[] var7 = threadInfos;
            int var8 = threadInfos.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               ThreadInfo info = var7[var9];
               if (info != null) {
                  sb.append(formattedThreadInfo(info)).append("\n");
               }
            }

            return sb.toString();
         } else {
            return null;
         }
      }
   }

   private static String formattedThreadInfo(ThreadInfo info) {
      StringBuilder sb = new StringBuilder();
      sb.append("\"").append(info.getThreadName()).append("\"").append(" Id=").append(info.getThreadId()).append(" ").append(info.getThreadState());
      if (info.getLockName() != null) {
         sb.append(" on ").append(info.getLockName());
      }

      if (info.getLockOwnerName() != null) {
         sb.append(" owned by \"").append(info.getLockOwnerName()).append("\" Id=").append(info.getLockOwnerId());
      }

      if (info.isSuspended()) {
         sb.append(" (suspended)");
      }

      if (info.isInNative()) {
         sb.append(" (in native)");
      }

      sb.append('\n');
      int i = 0;

      int var6;
      int var7;
      for(StackTraceElement[] stackTrace = info.getStackTrace(); i < stackTrace.length; ++i) {
         StackTraceElement ste = stackTrace[i];
         sb.append("\tat ").append(ste.toString());
         sb.append('\n');
         if (i == 0 && info.getLockInfo() != null) {
            Thread.State ts = info.getThreadState();
            switch (ts) {
               case BLOCKED:
                  sb.append("\t-  blocked on ").append(info.getLockInfo());
                  sb.append('\n');
                  break;
               case WAITING:
                  sb.append("\t-  waiting on ").append(info.getLockInfo());
                  sb.append('\n');
                  break;
               case TIMED_WAITING:
                  sb.append("\t-  waiting on ").append(info.getLockInfo());
                  sb.append('\n');
            }
         }

         MonitorInfo[] var10 = info.getLockedMonitors();
         var6 = var10.length;

         for(var7 = 0; var7 < var6; ++var7) {
            MonitorInfo mi = var10[var7];
            if (mi.getLockedStackDepth() == i) {
               sb.append("\t-  locked ").append(mi).append('\n');
            }
         }
      }

      LockInfo[] locks = info.getLockedSynchronizers();
      if (locks.length > 0) {
         sb.append("\n\tNumber of locked synchronizers = ").append(locks.length).append('\n');
         LockInfo[] var11 = locks;
         var6 = locks.length;

         for(var7 = 0; var7 < var6; ++var7) {
            LockInfo li = var11[var7];
            sb.append("\t- ").append(li).append('\n');
         }
      }

      sb.append('\n');
      return sb.toString();
   }

   public String threadDumpAsString(Thread thread) {
      return thread == null ? null : getStackTrace(thread.getStackTrace());
   }

   public String dumpDeadlockedThreads() {
      ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
      if (threadMXBean == null) {
         return null;
      } else {
         long[] threadIds = threadMXBean.findDeadlockedThreads();
         if (threadIds != null && threadIds.length != 0) {
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds, Integer.MAX_VALUE);
            StringBuilder sb = new StringBuilder();
            ThreadInfo[] var5 = threadInfos;
            int var6 = threadInfos.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ThreadInfo info = var5[var7];
               if (info != null) {
                  String name = "[deadlocked thread] " + info.getThreadName();
                  sb.append(name).append(":\n");
                  sb.append(underline(name.length()));
                  sb.append("Thread '").append(info.getThreadName()).append("' is waiting to acquire lock '").append(info.getLockName()).append("' that is held by thread '").append(info.getLockOwnerName()).append("'\n");
                  sb.append("\nStack trace:\n");
                  sb.append(underline(12));
                  sb.append(getStackTrace(info.getStackTrace())).append("\n");
               }
            }

            return sb.toString();
         } else {
            return null;
         }
      }
   }

   private static String underline(int length) {
      StringBuilder buffer = new StringBuilder(length);

      for(int count = 0; count < length; ++count) {
         buffer.append("-");
      }

      buffer.append("\n");
      return buffer.toString();
   }

   private static String getStackTrace(StackTraceElement[] elements) {
      if (elements != null && elements.length != 0) {
         StringBuilder sb = new StringBuilder();
         StackTraceElement[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            StackTraceElement element = var2[var4];
            sb.append("\t").append(element.toString()).append("\n");
         }

         return sb.toString();
      } else {
         return null;
      }
   }
}
