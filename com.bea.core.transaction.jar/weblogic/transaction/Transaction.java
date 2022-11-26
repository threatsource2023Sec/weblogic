package weblogic.transaction;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import weblogic.transaction.nonxa.NonXAResource;

public interface Transaction extends javax.transaction.Transaction {
   String TRANSACTION_NAME_PROPERTY_KEY = "weblogic.transaction.name";
   String TRANSACTION_COMPLETION_TIMEOUT_SECONDS_PROPERTY_KEY = "completion-timeout-seconds";
   String ENLISTMENT_RESOURCE_TYPE = "transaction.enlistment.resource.type";
   String ENLISTMENT_RESOURCE_TYPE_WEBLOGIC_JMS = "transaction.enlistment.resource.type.weblogic.jms";
   String TRANSACTION_EAGER_END_ON_DELIST_PROPERTY_KEY = "weblogic.transaction.eager.end.on.delist";

   void setName(String var1);

   String getName();

   void setProperty(String var1, Serializable var2);

   void addProperties(Map var1);

   Serializable getProperty(String var1);

   Map getProperties();

   void setLocalProperty(String var1, Object var2);

   void addLocalProperties(Map var1);

   Object getLocalProperty(String var1);

   Map getLocalProperties();

   Object invokeCoordinatorService(String var1, Object var2) throws SystemException, RemoteException;

   Object invokeCoordinatorService(String var1, Object var2, boolean var3) throws SystemException, RemoteException;

   boolean isCoordinatorLocal() throws SystemException;

   boolean isCoordinatorAssigned() throws SystemException;

   void setRollbackOnly(Throwable var1);

   void setRollbackOnly(String var1, Throwable var2);

   void setRollbackOnly(String var1);

   Throwable getRollbackReason();

   String getHeuristicErrorMessage();

   Xid getXID();

   Xid getXid();

   String getStatusAsString();

   long getMillisSinceBegin();

   long getTimeToLiveMillis();

   boolean isTimedOut();

   boolean isTxAsyncTimeout();

   boolean enlistResource(javax.transaction.xa.XAResource var1, String var2) throws javax.transaction.RollbackException, IllegalStateException, SystemException;

   boolean enlistResourceWithProperties(javax.transaction.xa.XAResource var1, Map var2) throws javax.transaction.RollbackException, IllegalStateException, SystemException;

   boolean enlistResource(NonXAResource var1) throws javax.transaction.RollbackException, IllegalStateException, SystemException;

   boolean delistResourceWithProperties(javax.transaction.xa.XAResource var1, int var2, Map var3) throws IllegalStateException, SystemException;

   Set getServerParticipantNames();
}
