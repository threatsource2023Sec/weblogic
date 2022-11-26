package weblogic.j2ee.descriptor.wl;

public interface ApplicationAdminModeTriggerBean {
   int getMaxStuckThreadTime();

   void setMaxStuckThreadTime(int var1);

   int getStuckThreadCount();

   void setStuckThreadCount(int var1);

   String getId();

   void setId(String var1);
}
