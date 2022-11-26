package weblogic.management.configuration;

public interface WebServiceReliabilityMBean extends ConfigurationMBean {
   String getBaseRetransmissionInterval();

   void setBaseRetransmissionInterval(String var1);

   Boolean isRetransmissionExponentialBackoff();

   void setRetransmissionExponentialBackoff(Boolean var1);

   Boolean isNonBufferedSource();

   void setNonBufferedSource(Boolean var1);

   String getAcknowledgementInterval();

   void setAcknowledgementInterval(String var1);

   String getInactivityTimeout();

   void setInactivityTimeout(String var1);

   String getSequenceExpiration();

   void setSequenceExpiration(String var1);

   Boolean isNonBufferedDestination();

   void setNonBufferedDestination(Boolean var1);
}
