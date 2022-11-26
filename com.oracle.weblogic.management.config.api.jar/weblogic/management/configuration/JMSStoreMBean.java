package weblogic.management.configuration;

/** @deprecated */
@Deprecated
public interface JMSStoreMBean extends ConfigurationMBean {
   JMSServerMBean getJMSServer();

   void setJMSServer(JMSServerMBean var1);
}
