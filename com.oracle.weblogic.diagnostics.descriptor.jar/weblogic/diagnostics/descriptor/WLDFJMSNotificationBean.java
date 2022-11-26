package weblogic.diagnostics.descriptor;

public interface WLDFJMSNotificationBean extends WLDFNotificationBean {
   String DEFAULT_JMS_CONNECTION_FACTORY_JNDI_NAME = "weblogic.jms.ConnectionFactory";

   String getDestinationJNDIName();

   void setDestinationJNDIName(String var1);

   String getConnectionFactoryJNDIName();

   void setConnectionFactoryJNDIName(String var1);
}
