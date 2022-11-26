package weblogic.xml.util.cache.entitycache;

class StatsSpec {
   Statistics stats = null;
   boolean isPersistent = false;

   StatsSpec(Statistics stats, boolean isPersistent) {
      this.stats = stats;
      this.isPersistent = isPersistent;
   }
}
