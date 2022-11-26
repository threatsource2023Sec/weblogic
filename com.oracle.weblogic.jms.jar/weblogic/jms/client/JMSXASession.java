package weblogic.jms.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.jms.TransactionInProgressException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.xa.XAResource;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.kernel.KernelStatus;
import weblogic.transaction.TransactionHelper;

public final class JMSXASession extends JMSSession implements XASessionInternal, Reconnectable, Cloneable {
   private static final String SERVER_TX_HELPER_CLASS = "weblogic.transaction.TxHelper";
   private static final String CLIENT_TX_HELPER_CLASS = "weblogic.transaction.TransactionHelper";
   private final boolean originalTransacted;

   protected JMSXASession(JMSConnection connection, boolean transacted, boolean stopped) throws JMSException {
      super(connection, false, 2, stopped);
      this.setUserTransactionsEnabled(true);
      this.originalTransacted = transacted;
      this.checkITM(connection);
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public boolean getTransacted() throws JMSException {
      this.checkClosed();
      return this.originalTransacted;
   }

   public void commit() throws JMSException {
      if (TransactionHelper.getTransactionHelper().getTransaction() == null) {
         throw new IllegalStateException(JMSClientExceptionLogger.logNoTransactionLoggable().getMessage());
      } else {
         throw new TransactionInProgressException(JMSClientExceptionLogger.logErrorCommittingSessionLoggable().getMessage());
      }
   }

   public void rollback() throws JMSException {
      if (TransactionHelper.getTransactionHelper().getTransaction() == null) {
         throw new IllegalStateException(JMSClientExceptionLogger.logNoTransaction2Loggable().getMessage());
      } else {
         throw new TransactionInProgressException(JMSClientExceptionLogger.logErrorRollingBackSessionLoggable().getMessage());
      }
   }

   public XAResource getXAResource() {
      if (!KernelStatus.isServer()) {
         if (!this.getConnection().isWrappedIC()) {
            throw new java.lang.IllegalStateException(JMSClientExceptionLogger.logOnlyFromServerLoggable().getMessage());
         }

         this.xaRes = this.getXAResource((String)null);
      } else {
         try {
            Class txHelperClz = Class.forName("weblogic.transaction.TxHelper");
            Method m = txHelperClz.getMethod("getServerInterposedTransactionManager");
            Object o = m.invoke((Object)null);
            if (o == null) {
               return null;
            }

            m = o.getClass().getMethod("getXAResource");
            this.xaRes = (XAResource)m.invoke(o);
         } catch (InvocationTargetException var4) {
            throw new AssertionError(var4);
         } catch (ClassNotFoundException var5) {
            throw new AssertionError(var5);
         } catch (NoSuchMethodException var6) {
            throw new AssertionError(var6);
         } catch (IllegalAccessException var7) {
            throw new AssertionError(var7);
         }
      }

      return this.xaRes;
   }

   public XAResource getXAResource(String serverName) {
      if (!this.getConnection().isWrappedIC()) {
         throw new java.lang.IllegalStateException("No jndi environment available in connection. Please use weblogic.jms.WLInitialContext or weblogic.jms.WrappedInitialContext for jndi lookup.");
      } else {
         if (serverName == null) {
            serverName = this.getConnection().getWLSServerName();
         }

         Hashtable tmpEnv = (Hashtable)this.getConnection().getJndiEnv().clone();
         tmpEnv.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");

         try {
            JMSConnectionFactory.decryptCredential(tmpEnv);
         } catch (JMSException var14) {
            throw new AssertionError(var14);
         }

         Context ctx = null;

         try {
            ctx = new InitialContext(tmpEnv);
            this.xaRes = this.innerGetXAResource(ctx, serverName, true);
         } catch (NamingException var13) {
            throw new AssertionError(var13);
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var12) {
               }
            }

         }

         return this.xaRes;
      }
   }

   public XAResource getXAResource(Context ctx, String serverName) {
      return this.innerGetXAResource(ctx, serverName, false);
   }

   private XAResource innerGetXAResource(Context ctx, String serverName, boolean useXAWrapper) {
      try {
         Class txHelperClz = Class.forName("weblogic.transaction.TransactionHelper");
         Method m = txHelperClz.getMethod("getClientInterposedTransactionManager", Context.class, String.class);
         Object o = m.invoke((Object)null, ctx, serverName);
         if (o == null) {
            return null;
         } else {
            m = o.getClass().getMethod("setClusterwideRecoveryEnabled", Boolean.TYPE);
            m.invoke(o, Boolean.TRUE);
            if (useXAWrapper) {
               m = o.getClass().getMethod("setReturnTransactionThreadStateAwareITMXAResource", Boolean.TYPE);
               m.invoke(o, Boolean.TRUE);
            }

            m = o.getClass().getMethod("getXAResource");
            return (XAResource)m.invoke(o);
         }
      } catch (InvocationTargetException var7) {
         throw new AssertionError(var7);
      } catch (ClassNotFoundException var8) {
         throw new AssertionError(var8);
      } catch (NoSuchMethodException var9) {
         throw new AssertionError(var9);
      } catch (IllegalAccessException var10) {
         throw new AssertionError(var10);
      }
   }

   public QueueSession getQueueSession() {
      return (QueueSession)this.getSession();
   }

   public TopicSession getTopicSession() {
      return (TopicSession)this.getSession();
   }

   public Session getSession() {
      return this;
   }

   private void checkITM(JMSConnection conn) throws JMSException {
      if (!KernelStatus.isServer() && conn.isWrappedIC()) {
         Context ctx = null;

         try {
            String serverName = conn.getWLSServerName();
            Hashtable tmpEnv = (Hashtable)conn.getJndiEnv().clone();
            tmpEnv.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
            JMSConnectionFactory.decryptCredential(tmpEnv);
            ctx = new InitialContext(tmpEnv);
            Class txHelperClz = Class.forName("weblogic.transaction.TransactionHelper");
            Method m = txHelperClz.getMethod("getClientInterposedTransactionManagerThrowsOnException", Context.class, String.class);
            m.invoke((Object)null, ctx, serverName);
         } catch (Exception var15) {
            throw new weblogic.jms.common.JMSException("Error when try to get ITM via jndi lookup", var15);
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var14) {
               }
            }

         }
      }

   }
}
