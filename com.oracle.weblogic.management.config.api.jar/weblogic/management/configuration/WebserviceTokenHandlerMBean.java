package weblogic.management.configuration;

public interface WebserviceTokenHandlerMBean extends WebserviceSecurityConfigurationMBean {
   int getHandlingOrder();

   void setHandlingOrder(int var1);
}
