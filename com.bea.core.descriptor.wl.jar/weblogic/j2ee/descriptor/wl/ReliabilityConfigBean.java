package weblogic.j2ee.descriptor.wl;

public interface ReliabilityConfigBean {
   boolean isCustomized();

   void setCustomized(boolean var1);

   String getInactivityTimeout();

   void setInactivityTimeout(String var1);

   String getBaseRetransmissionInterval();

   void setBaseRetransmissionInterval(String var1);

   boolean getRetransmissionExponentialBackoff();

   void setRetransmissionExponentialBackoff(boolean var1);

   boolean getNonBufferedSource();

   void setNonBufferedSource(boolean var1);

   String getAcknowledgementInterval();

   void setAcknowledgementInterval(String var1);

   String getSequenceExpiration();

   void setSequenceExpiration(String var1);

   int getBufferRetryCount();

   void setBufferRetryCount(int var1);

   String getBufferRetryDelay();

   void setBufferRetryDelay(String var1);

   boolean getNonBufferedDestination();

   void setNonBufferedDestination(boolean var1);
}
