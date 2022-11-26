package weblogic.diagnostics.image.descriptor;

public interface WatchImageSourceBean {
   WatchStatisticsBean getAggregateWatchStatistics();

   WatchStatisticsBean createAggregateWatchStatistics();

   WatchManagerBean[] getWatchManagers();

   WatchManagerBean createWatchManager();
}
