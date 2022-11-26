package com.bea.core.repackaged.springframework.jca.cci.connection;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.jca.cci.CannotGetCciConnectionException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.support.ResourceHolderSynchronization;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationManager;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;

public abstract class ConnectionFactoryUtils {
   private static final Log logger = LogFactory.getLog(ConnectionFactoryUtils.class);

   public static Connection getConnection(ConnectionFactory cf) throws CannotGetCciConnectionException {
      return getConnection(cf, (ConnectionSpec)null);
   }

   public static Connection getConnection(ConnectionFactory cf, @Nullable ConnectionSpec spec) throws CannotGetCciConnectionException {
      try {
         if (spec != null) {
            Assert.notNull(cf, (String)"No ConnectionFactory specified");
            return cf.getConnection(spec);
         } else {
            return doGetConnection(cf);
         }
      } catch (ResourceException var3) {
         throw new CannotGetCciConnectionException("Could not get CCI Connection", var3);
      }
   }

   public static Connection doGetConnection(ConnectionFactory cf) throws ResourceException {
      Assert.notNull(cf, (String)"No ConnectionFactory specified");
      ConnectionHolder conHolder = (ConnectionHolder)TransactionSynchronizationManager.getResource(cf);
      if (conHolder != null) {
         return conHolder.getConnection();
      } else {
         logger.debug("Opening CCI Connection");
         Connection con = cf.getConnection();
         if (TransactionSynchronizationManager.isSynchronizationActive()) {
            conHolder = new ConnectionHolder(con);
            conHolder.setSynchronizedWithTransaction(true);
            TransactionSynchronizationManager.registerSynchronization(new ConnectionSynchronization(conHolder, cf));
            TransactionSynchronizationManager.bindResource(cf, conHolder);
         }

         return con;
      }
   }

   public static boolean isConnectionTransactional(Connection con, @Nullable ConnectionFactory cf) {
      if (cf == null) {
         return false;
      } else {
         ConnectionHolder conHolder = (ConnectionHolder)TransactionSynchronizationManager.getResource(cf);
         return conHolder != null && conHolder.getConnection() == con;
      }
   }

   public static void releaseConnection(@Nullable Connection con, @Nullable ConnectionFactory cf) {
      try {
         doReleaseConnection(con, cf);
      } catch (ResourceException var3) {
         logger.debug("Could not close CCI Connection", var3);
      } catch (Throwable var4) {
         logger.debug("Unexpected exception on closing CCI Connection", var4);
      }

   }

   public static void doReleaseConnection(@Nullable Connection con, @Nullable ConnectionFactory cf) throws ResourceException {
      if (con != null && !isConnectionTransactional(con, cf)) {
         con.close();
      }
   }

   private static class ConnectionSynchronization extends ResourceHolderSynchronization {
      public ConnectionSynchronization(ConnectionHolder connectionHolder, ConnectionFactory connectionFactory) {
         super(connectionHolder, connectionFactory);
      }

      protected void releaseResource(ConnectionHolder resourceHolder, ConnectionFactory resourceKey) {
         ConnectionFactoryUtils.releaseConnection(resourceHolder.getConnection(), resourceKey);
      }
   }
}
