package weblogic.transaction;

import java.util.Hashtable;
import java.util.Map;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.nonxa.NonXAResource;

public interface TransactionManager extends ClientTransactionManager {
   String NAME = "name";
   String TRANSACTION_TIMEOUT = "transaction-timeout";
   String COMPLETION_TIMEOUT_SECONDS = "completion-timeout-seconds";
   String RESOURCE_TYPE = "weblogic.transaction.resource.type";
   String RESOURCE_DATASOURCE = "datasource";
   String RESOURCE_JMS = "jms";
   String RESOURCE_JCA = "jca";
   String RESOURCE_OTHER = "other";
   String ENLISTMENT_TYPE = "weblogic.transaction.registration.type";
   String ENLISTMENT_DYNAMIC = "dynamic";
   String ENLISTMENT_STATIC = "static";
   String ENLISTMENT_STANDARD = "standard";
   String INTERLEAVING_ENLISTMENTS = "weblogic.transaction.registration.interleaving";
   String CALL_SET_TRANSACTION_TIMEOUT = "weblogic.transaction.registration.settransactiontimeout";
   String LOCAL_ASSIGNMENT_OF_REMOTE_RESOURCES = "weblogic.transaction.registration.localassignment";
   String ASYNC_TIMEOUT_DELIST = "weblogic.transaction.registration.asynctimeoutdelist";
   String THREAD_AFFINITY = "weblogic.transaction.registration.threadAffinity";
   String RECOVER_RETRY_DURATION_SECONDS = "weblogic.transaction.registration.recoverRetryDurationSeconds";
   String RECOVER_RETRY_INTERVAL_SECONDS = "weblogic.transaction.registration.recoverRetryIntervalSeconds";
   String CALL_SET_DELIST_TMSUCCESS_ALWAYS = "weblogic.transaction.registration.setdelistTMSUCCESSAlways";
   String CALL_SET_DELIST_TMSUCCESS_INSTEAD_OF_TMSUSPEND = "weblogic.transaction.registration.setdelistTMSUCCESSInsteadOfTMSUSPEND";
   String FIRST_RESOURCE_COMMIT = "weblogic.transaction.first.resource.commit";
   String FIRST_RESOURCE_COMMIT_SERVER = "weblogic.transaction.first.resource.commitServer";
   String VENDOR_NAME = "weblogic.transaction.resource.manager.name";

   void registerStaticResource(String var1, javax.transaction.xa.XAResource var2) throws SystemException;

   void registerStaticResource(String var1, javax.transaction.xa.XAResource var2, Hashtable var3) throws SystemException;

   void registerDynamicResource(String var1, javax.transaction.xa.XAResource var2) throws SystemException;

   void registerDynamicResource(String var1, javax.transaction.xa.XAResource var2, Hashtable var3) throws SystemException;

   void registerDynamicResource(String var1, NonXAResource var2) throws SystemException;

   void registerLoggingResourceTransactions(LoggingResource var1) throws SystemException;

   void registerFailedLoggingResource(Throwable var1);

   void registerCoordinatorService(String var1, CoordinatorService var2);

   void unregisterResource(String var1) throws SystemException;

   void setResourceHealthy(String var1);

   void unregisterResource(String var1, boolean var2) throws SystemException;

   javax.transaction.Transaction getTransaction(Xid var1);

   TransactionInterceptor getInterceptor();

   void begin(String var1) throws NotSupportedException, SystemException;

   void begin(int var1) throws NotSupportedException, SystemException;

   void begin(String var1, int var2) throws NotSupportedException, SystemException;

   void begin(Map var1) throws NotSupportedException, SystemException;

   void registerResource(String var1, javax.transaction.xa.XAResource var2) throws SystemException;

   void registerResource(String var1, javax.transaction.xa.XAResource var2, Hashtable var3) throws SystemException;

   void registerResource(String var1, javax.transaction.xa.XAResource var2, Hashtable var3, boolean var4) throws SystemException;

   void registerResource(String var1, javax.transaction.xa.XAResource var2, boolean var3) throws SystemException;
}
