package weblogic.j2ee.descriptor.wl;

public interface ProxyBean {
   int getInactiveConnectionTimeoutSeconds();

   void setInactiveConnectionTimeoutSeconds(int var1);

   boolean isConnectionProfilingEnabled();

   void setConnectionProfilingEnabled(boolean var1);

   String getUseConnectionProxies();

   void setUseConnectionProxies(String var1);
}
