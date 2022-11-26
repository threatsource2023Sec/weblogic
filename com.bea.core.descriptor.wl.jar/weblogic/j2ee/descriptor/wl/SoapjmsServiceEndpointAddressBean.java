package weblogic.j2ee.descriptor.wl;

public interface SoapjmsServiceEndpointAddressBean {
   String getLookupVariant();

   void setLookupVariant(String var1);

   String getDestinationName();

   void setDestinationName(String var1);

   String getDestinationType();

   void setDestinationType(String var1);

   String getJndiConnectionFactoryName();

   void setJndiConnectionFactoryName(String var1);

   String getJndiInitialContextFactory();

   void setJndiInitialContextFactory(String var1);

   String getJndiUrl();

   void setJndiUrl(String var1);

   String getJndiContextParameter();

   void setJndiContextParameter(String var1);

   long getTimeToLive();

   void setTimeToLive(long var1);

   int getPriority();

   void setPriority(int var1);

   String getDeliveryMode();

   void setDeliveryMode(String var1);

   String getReplyToName();

   void setReplyToName(String var1);

   String getTargetService();

   void setTargetService(String var1);

   String getBindingVersion();

   void setBindingVersion(String var1);

   String getMessageType();

   void setMessageType(String var1);

   boolean isEnableHttpWsdlAccess();

   void setEnableHttpWsdlAccess(boolean var1);

   String getRunAsPrincipal();

   void setRunAsPrincipal(String var1);

   String getRunAsRole();

   void setRunAsRole(String var1);

   boolean isMdbPerDestination();

   void setMdbPerDestination(boolean var1);

   String getActivationConfig();

   void setActivationConfig(String var1);

   String getJmsMessageHeader();

   void setJmsMessageHeader(String var1);

   String getJmsMessageProperty();

   void setJmsMessageProperty(String var1);
}
