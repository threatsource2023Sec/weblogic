package weblogic.xml.util.cache.entitycache;

class StatisticsMonitorSubject {
   Statistics stats = null;
   EntityCacheStats mBean = null;

   StatisticsMonitorSubject(Statistics stats, EntityCacheStats mBean) {
      this.stats = stats;
      this.mBean = mBean;
   }
}
