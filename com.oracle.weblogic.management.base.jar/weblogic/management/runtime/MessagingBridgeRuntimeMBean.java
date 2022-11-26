package weblogic.management.runtime;

public interface MessagingBridgeRuntimeMBean extends RuntimeMBean {
   void stop();

   void start();

   String getState();

   String getDescription();
}
