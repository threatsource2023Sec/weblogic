package weblogic.j2ee.descriptor.wl;

public interface ConnectorWorkManagerBean {
   int getMaxConcurrentLongRunningRequests();

   void setMaxConcurrentLongRunningRequests(int var1);

   String getId();

   void setId(String var1);
}
