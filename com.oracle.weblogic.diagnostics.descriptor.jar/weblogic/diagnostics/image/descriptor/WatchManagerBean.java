package weblogic.diagnostics.image.descriptor;

public interface WatchManagerBean {
   String getModuleName();

   void setModuleName(String var1);

   WatchAlarmStateBean[] getWatchAlarmStates();

   WatchAlarmStateBean createWatchAlarmState();

   WatchManagerStatisticsBean getWatchManagerStatistics();

   WatchManagerStatisticsBean createWatchManagerStatistics();
}
