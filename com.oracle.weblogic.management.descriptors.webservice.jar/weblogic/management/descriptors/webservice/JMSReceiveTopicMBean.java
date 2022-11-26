package weblogic.management.descriptors.webservice;

public interface JMSReceiveTopicMBean extends ComponentMBean {
   JNDINameMBean getJNDIName();

   void setJNDIName(JNDINameMBean var1);

   String getConnectionFactory();

   void setConnectionFactory(String var1);

   String getProviderUrl();

   void setProviderUrl(String var1);

   String getInitialContextFactory();

   void setInitialContextFactory(String var1);
}
