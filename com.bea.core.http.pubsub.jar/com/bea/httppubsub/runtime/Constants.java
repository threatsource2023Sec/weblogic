package com.bea.httppubsub.runtime;

public interface Constants {
   String PUB_SUB_SERVER_NAME_PROPERTY = "com.bea.httppubsub.ServerName";
   String PUB_SUB_BEAN_PROPERTY = "com.bea.httppubsub.PubSubBean";
   String PUB_SUB_SERVLET_CONTEXT_PROPERTY = "com.bea.httppubsub.ServletContext";
   String PUB_SUB_SERVLET_CONTEXTPATH_PROPERTY = "com.bea.httppubsub.ServletContextPath";
   String MBEAN_MANAGER_FACTORY_PROPERTY = "com.bea.httppubsub.runtime.MBeanManagerFactory";
   String WLS_MBEAN_MANAGER_FACTORY_IMPL = "weblogic.servlet.httppubsub.runtime.MBeanManagerFactoryImpl";
   String PUB_SUB_SECURITY_DISABLE_PROPERTY = "com.bea.httppubsub.security.Disabled";
   String PERSISTENT_STORE_MANAGER = "com.bea.httppubsub.internal.PersistentStoreManager.impl";
   String PUB_SUB_DISABLE_WM_ON_CHANNEL_LISTENER = "com.bea.httppubsub.internal.ChannelEventPublisher.disableWorkManager";
   String PUB_SUB_AUTHORIZATION_MANAGER_FACTORY_PROPERTY = "com.bea.httppubsub.security.ChannelAuthorizationManagerFactory";
   String WLS_CHANNEL_AUTH_MANAGER_CLASS = "com.bea.httppubsub.security.wls.WlsChannelAuthorizationManagerFactory";
}
