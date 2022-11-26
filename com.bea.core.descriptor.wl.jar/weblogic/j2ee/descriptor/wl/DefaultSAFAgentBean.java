package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface DefaultSAFAgentBean extends SettableBean {
   String getNotes();

   void setNotes(String var1);

   long getBytesMaximum();

   void setBytesMaximum(long var1);

   long getMessagesMaximum();

   void setMessagesMaximum(long var1);

   int getMaximumMessageSize();

   void setMaximumMessageSize(int var1);

   long getDefaultRetryDelayBase();

   void setDefaultRetryDelayBase(long var1);

   long getDefaultRetryDelayMaximum();

   void setDefaultRetryDelayMaximum(long var1);

   double getDefaultRetryDelayMultiplier();

   void setDefaultRetryDelayMultiplier(double var1);

   int getWindowSize();

   void setWindowSize(int var1);

   /** @deprecated */
   @Deprecated
   boolean isLoggingEnabled();

   void setLoggingEnabled(boolean var1);

   long getDefaultTimeToLive();

   void setDefaultTimeToLive(long var1);

   long getMessageBufferSize();

   void setMessageBufferSize(long var1);

   String getPagingDirectory();

   void setPagingDirectory(String var1);

   long getWindowInterval();

   void setWindowInterval(long var1);
}
