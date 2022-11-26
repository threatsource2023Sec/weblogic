package weblogic.management.configuration;

public interface WebServiceBufferingMBean extends ConfigurationMBean {
   WebServiceRequestBufferingQueueMBean getWebServiceRequestBufferingQueue();

   WebServiceResponseBufferingQueueMBean getWebServiceResponseBufferingQueue();

   int getRetryCount();

   void setRetryCount(int var1);

   String getRetryDelay();

   void setRetryDelay(String var1);
}
