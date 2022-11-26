package weblogic.management.runtime;

import java.rmi.RemoteException;

public interface JMSConsumerRuntimeMBean extends RuntimeMBean {
   String getDestinationName();

   String getMemberDestinationName();

   boolean isActive() throws RemoteException;

   String getSelector();

   boolean isDurable();

   long getMessagesPendingCount();

   long getMessagesReceivedCount();

   long getBytesPendingCount();

   long getBytesReceivedCount();

   String getSubscriptionSharingPolicy();

   String getClientID();

   String getClientIDPolicy();
}
