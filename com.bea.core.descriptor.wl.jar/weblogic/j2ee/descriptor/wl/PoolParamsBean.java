package weblogic.j2ee.descriptor.wl;

public interface PoolParamsBean extends ConnectionPoolParamsBean {
   int getMaxCapacity();

   void setMaxCapacity(int var1);

   int getConnectionReserveTimeoutSeconds();

   void setConnectionReserveTimeoutSeconds(int var1);

   int getHighestNumWaiters();

   void setHighestNumWaiters(int var1);

   boolean isMatchConnectionsSupported();

   void setMatchConnectionsSupported(boolean var1);

   boolean isUseFirstAvailable();

   void setUseFirstAvailable(boolean var1);
}
