package weblogic.corba.server.transaction;

import java.util.Hashtable;
import java.util.Map;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.corba.j2ee.transaction.TransactionManagerWrapper;
import weblogic.rmi.cluster.ThreadPreferredHost;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.transaction.CoordinatorService;
import weblogic.transaction.ServerTransactionInterceptor;
import weblogic.transaction.TransactionInterceptor;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.nonxa.NonXAResource;

public final class TransactionManagerImpl extends TransactionManagerWrapper implements TransactionManager, ServerTransactionInterceptor {
   public static TransactionManagerWrapper getTransactionManager() {
      return TransactionManagerImpl.TMMaker.SINGLETON;
   }

   protected TransactionManagerImpl() {
   }

   public void registerStaticResource(String name, XAResource xar) throws SystemException {
      throw new SystemException();
   }

   public void registerStaticResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      throw new SystemException();
   }

   public void registerDynamicResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      throw new SystemException();
   }

   public void registerDynamicResource(String name, XAResource xar) throws SystemException {
      throw new SystemException();
   }

   public void registerDynamicResource(String name, NonXAResource nxar) throws SystemException {
      throw new SystemException();
   }

   public void registerFailedLoggingResource(Throwable t) {
      throw new IllegalStateException();
   }

   public void registerResource(String name, XAResource xar) throws SystemException {
      throw new SystemException();
   }

   public void registerResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      throw new SystemException();
   }

   public void registerResource(String name, XAResource xar, boolean localResourceAssignment) throws SystemException {
      throw new SystemException();
   }

   public void registerResource(String name, XAResource xar, Hashtable properties, boolean localResourceAssignment) throws SystemException {
      throw new SystemException();
   }

   public void unregisterResource(String name) throws SystemException {
      throw new SystemException();
   }

   public void unregisterResource(String name, boolean blocking) throws SystemException {
      throw new SystemException();
   }

   public void registerCoordinatorService(String name, CoordinatorService cs) {
      throw new IllegalStateException();
   }

   public void registerLoggingResourceTransactions(LoggingResource lr) throws SystemException {
      throw new SystemException();
   }

   public Transaction getTransaction(Xid xid) {
      return null;
   }

   public TransactionInterceptor getInterceptor() {
      return this;
   }

   public void begin(String name) throws SystemException {
      throw new SystemException();
   }

   public void begin(int timeoutseconds) throws SystemException {
      throw new SystemException();
   }

   public void begin(String name, int timeoutseconds) throws SystemException {
      throw new SystemException();
   }

   public void begin(Map properties) throws SystemException {
      throw new SystemException();
   }

   public Object sendRequest(Object endPoint) {
      Object tx = this.getTM().get_txcontext();
      EndPoint ep = null;
      if (endPoint instanceof EndPoint) {
         ep = (EndPoint)endPoint;
      }

      if (tx != null && ep != null && ep.getHostID() != null) {
         ThreadPreferredHost.set(ep.getHostID());
      } else {
         ThreadPreferredHost.set((HostID)null);
      }

      return tx;
   }

   public Object sendResponse(Object txContext) {
      return null;
   }

   public void receiveRequest(Object txContext) {
   }

   public void receiveResponse(Object txContext) {
   }

   public void dispatchRequest(Object txContext) {
   }

   public void receiveAsyncResponse(Object txContext) {
   }

   public boolean isParticipant(String serverName) {
      return false;
   }

   public boolean needsInterception() {
      return false;
   }

   public void setResourceHealthy(String name) {
   }

   private static final class TMMaker {
      private static final TransactionManagerWrapper SINGLETON = new TransactionManagerImpl();
   }
}
