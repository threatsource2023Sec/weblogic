package weblogic.management.runtime;

public interface JMSConnectionRuntimeMBean extends RuntimeMBean {
   void destroy();

   String getClientID();

   String getClientIDPolicy();

   JMSSessionRuntimeMBean[] getSessions();

   long getSessionsCurrentCount();

   long getSessionsHighCount();

   long getSessionsTotalCount();

   String getHostAddress();
}
