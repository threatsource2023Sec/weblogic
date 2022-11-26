package weblogic.j2ee.descriptor.wl;

public interface AsyncDescriptorBean {
   int getTimeoutSecs();

   void setTimeoutSecs(int var1);

   int getTimeoutCheckIntervalSecs();

   void setTimeoutCheckIntervalSecs(int var1);

   String getAsyncWorkManager();

   void setAsyncWorkManager(String var1);
}
