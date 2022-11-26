package weblogic.messaging.kernel;

public interface Quota {
   String getName();

   void setPolicy(QuotaPolicy var1);

   QuotaPolicy getPolicy();

   void setBytesMaximum(long var1);

   long getBytesMaximum();

   void setMessagesMaximum(int var1);

   int getMessagesMaximum();

   Kernel getKernel();
}
