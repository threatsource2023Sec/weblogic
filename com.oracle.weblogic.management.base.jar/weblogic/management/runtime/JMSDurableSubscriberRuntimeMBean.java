package weblogic.management.runtime;

import java.rmi.RemoteException;
import javax.jms.JMSException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface JMSDurableSubscriberRuntimeMBean extends JMSMessageManagementRuntimeMBean {
   String getClientID();

   String getClientIDPolicy();

   String getSubscriptionName();

   boolean isNoLocal();

   boolean isActive() throws RemoteException;

   String getSelector();

   long getMessagesPendingCount();

   long getMessagesCurrentCount();

   long getMessagesHighCount();

   long getMessagesReceivedCount();

   long getSubscriptionLimitDeletedCount();

   long getBytesPendingCount();

   long getBytesCurrentCount();

   long getLastMessagesReceivedTime();

   /** @deprecated */
   @Deprecated
   void purge();

   JMSDestinationRuntimeMBean getDestinationRuntime();

   CompositeData getCurrentConsumerInfo() throws OpenDataException;

   void destroy() throws JMSException;

   CompositeData getDestinationInfo() throws OpenDataException;

   String getSubscriptionSharingPolicy();

   int getSubscribersTotalCount();

   int getSubscribersHighCount();

   int getSubscribersCurrentCount();
}
