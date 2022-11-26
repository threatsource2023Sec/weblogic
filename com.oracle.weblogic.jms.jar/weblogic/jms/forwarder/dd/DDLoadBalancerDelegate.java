package weblogic.jms.forwarder.dd;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Session;
import javax.naming.Context;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.forwarder.DestinationName;
import weblogic.security.subject.AbstractSubject;
import weblogic.store.PersistentStoreException;

public interface DDLoadBalancerDelegate {
   DestinationName getDestinationName();

   int getLoadBalancingPolicy();

   boolean isDDInLocalCluster();

   Destination loadBalance();

   Destination loadBalance(MessageImpl var1);

   void addFailedEndPoint(MessageImpl var1, DestinationImpl var2) throws PersistentStoreException;

   boolean hasNonFailedDDMembers();

   void close();

   void refreshSessionRuntimeContext(Context var1, Connection var2, Session var3, AbstractSubject var4);

   void unfreezeDDLBTable();
}
