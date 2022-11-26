package weblogic.ejb.container.interfaces;

import java.lang.reflect.Method;
import java.util.Collection;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.ejb.container.timer.MDBTimerManagerFactory;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.j2ee.descriptor.ActivationConfigBean;
import weblogic.j2ee.descriptor.wl.SecurityPluginBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.WorkManager;

public interface MessageDrivenBeanInfo extends BeanInfo, weblogic.ejb.spi.MessageDrivenBeanInfo {
   boolean isDestinationQueue();

   boolean isDestinationTopic();

   boolean isDurableSubscriber();

   int getAcknowledgeMode();

   boolean isOnMessageTransacted();

   Integer getOnMessageTxIsolationLevel();

   MethodInfo getOnMessageMethodInfo();

   weblogic.ejb.container.internal.MethodDescriptor getOnMessageMethodDescriptor();

   Class getMessageDrivenLocalObjectClass();

   String getMessagingTypeInterfaceName();

   Collection getAllMessagingTypeMethodInfos();

   Context getInitialContext() throws NamingException;

   Context getInitialContext(String var1, String var2) throws NamingException;

   String getProviderURL();

   String getDestinationName();

   String getConnectionFactoryJNDIName();

   String getMessageSelector();

   int getMaxConcurrentInstances();

   int getJMSPollingIntervalSeconds();

   int getInitSuspendSeconds();

   int getMaxSuspendSeconds();

   SecurityPluginBean getSecurityPlugin();

   boolean getDeleteDurableSubscription();

   String computeUniqueGlobalId(String var1, TargetMBean var2);

   String computeJmsClientId(String var1, TargetMBean var2);

   String computeSubscriptionName(String var1);

   void deployMessageDrivenBeans() throws WLDeploymentException;

   void removeManager(BeanManager var1);

   void suspendManagers();

   void resumeManagers();

   boolean shouldManagerStartSuspended();

   void disableManagerStartSuspended();

   boolean getIsJMSBased();

   ActivationConfigBean getActivationConfigBean();

   String getResourceAdapterJndiName();

   boolean isDeliveryTransacted(Method var1) throws NoSuchMethodException;

   weblogic.ejb.container.internal.MethodDescriptor getMDBMethodDescriptor(Method var1);

   int getMaxMessagesInTransaction();

   boolean getUse81StylePolling();

   boolean getMinimizeAQSessions();

   int getMdbDestinationPollIntervalMillis();

   boolean isReceiveNoWaitAQ();

   String getDestinationResourceLink();

   void setEJBComponentRuntime(EJBRuntimeHolder var1);

   String getBeanClassNameToInstantiate();

   Class getGeneratedBeanInterface();

   boolean exposesNoInterfaceClientView();

   void reSetUsernameAndPassword();

   boolean getIsRemoteSubjectExists();

   void unRegister();

   int getTopicMessagesDistributionMode();

   int getDistributedDestinationConnection();

   AuthenticatedSubject getSubject();

   boolean getIsInactive();

   WorkManager getCustomWorkManager();

   MDBTimerManagerFactory getTimerManagerFactory();

   void processInactive(boolean var1) throws BeanUpdateFailedException;

   void setResourceAdapterVersion(String var1);

   String getResourceAdapterVersion();

   int getSessionMessagesMaximum();

   void deleteDurableSubscribers();

   void undeployManagers();

   boolean isTopicSubscriptionSharingInPartitions();
}
