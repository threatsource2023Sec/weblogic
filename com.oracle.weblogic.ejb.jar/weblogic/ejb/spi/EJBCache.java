package weblogic.ejb.spi;

public interface EJBCache {
   void updateMaxBeansInCache(int var1);

   void updateIdleTimeoutSeconds(int var1);

   void reInitializeCacheAndPools();

   void startScrubber();

   void stopScrubber();
}
