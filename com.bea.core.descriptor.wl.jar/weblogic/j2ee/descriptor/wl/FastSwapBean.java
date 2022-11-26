package weblogic.j2ee.descriptor.wl;

public interface FastSwapBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   int getRefreshInterval();

   void setRefreshInterval(int var1);

   int getRedefinitionTaskLimit();

   void setRedefinitionTaskLimit(int var1);
}
