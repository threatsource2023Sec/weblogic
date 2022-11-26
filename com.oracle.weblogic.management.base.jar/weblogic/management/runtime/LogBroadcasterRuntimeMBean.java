package weblogic.management.runtime;

public interface LogBroadcasterRuntimeMBean extends RuntimeMBean {
   String BROADCASTER_NAME = "TheLogBroadcaster";

   long getMessagesLogged();
}
