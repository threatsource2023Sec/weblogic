package weblogic.ejb.container.interfaces;

import javax.ejb.MessageDrivenContext;
import javax.naming.Context;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.jms.extensions.DestinationDetail;
import weblogic.management.configuration.TargetMBean;

public interface MessageDrivenManagerIntf extends BeanManager {
   void setup(MessageDrivenBeanInfo var1, Context var2, String var3, TargetMBean var4, DestinationDetail var5, ISecurityHelper var6) throws WLDeploymentException;

   void start() throws WLDeploymentException;

   void stop();

   void resume();

   void suspend();

   boolean shouldConnectionSuspendOnStart();

   boolean isConnectionSuspendOnStartPropertySet();

   void enableDestination(DestinationDetail var1, TargetMBean var2) throws WLDeploymentException;

   String updateStatus(int var1);

   void remove();

   void destroyInstance(InvocationWrapper var1, Throwable var2);

   void resetMessageConsumer(boolean var1);

   String getDestinationName();

   String getUniqueGlobalId();

   String getJMSClientId();

   String getSubscriberName();

   PoolIntf getPool();

   MessageDrivenContext getMessageDrivenContext();

   TargetMBean getTargetMBean();

   boolean isNonDDMD();

   boolean getDeleteDurableSubscription();

   String getDDMemberName();

   String getDDJNDIName();

   DestinationDetail getDestination();

   String getMessageSelector();

   int getTopicMessagesDistributionMode();

   String getProviderURL();

   String getConnectionFactoryJNDIName();

   void disableCheckMDBS_TO_SUSPEND_ON_STARTUP();
}
