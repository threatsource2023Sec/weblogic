package weblogic.work;

public interface DaemonTaskStatisticsCollector {
   void daemonTaskStarted();

   void daemonTaskCompleted();
}
