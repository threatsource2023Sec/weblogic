package weblogic.j2ee.descriptor.wl;

public interface BufferingConfigBean {
   boolean isCustomized();

   void setCustomized(boolean var1);

   BufferingQueueBean getRequestQueue();

   BufferingQueueBean getResponseQueue();

   int getRetryCount();

   void setRetryCount(int var1);

   String getRetryDelay();

   void setRetryDelay(String var1);
}
