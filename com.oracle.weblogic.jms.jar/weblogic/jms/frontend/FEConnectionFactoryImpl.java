package weblogic.jms.frontend;

import java.io.IOException;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.security.auth.login.LoginException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.client.JMSConnection;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.dispatcher.DispatcherOneWay;
import weblogic.messaging.dispatcher.DispatcherRemote;
import weblogic.messaging.dispatcher.PartitionAwareSetter;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class FEConnectionFactoryImpl implements FEConnectionFactoryRemote {
   private final transient FEConnectionFactory feConnectionFactory;

   public FEConnectionFactoryImpl(FEConnectionFactory feConnectionFactory) {
      this.feConnectionFactory = feConnectionFactory;
   }

   public JMSConnection connectionCreate(DispatcherWrapper dispatcher, String username, String password) throws JMSException {
      ComponentInvocationContext cic = this.feConnectionFactory.getFrontEnd().getService().getComponentInvocationContext();
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(cic);
      Throwable var6 = null;

      JMSConnection var7;
      try {
         if (!JMSSecurityHelper.authenticate(username, password)) {
            throw new JMSSecurityException("Authentication failure");
         }

         var7 = this.connectionCreateInternal(dispatcher, true);
      } catch (Throwable var16) {
         var6 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var15) {
                  var6.addSuppressed(var15);
               }
            } else {
               mic.close();
            }
         }

      }

      return var7;
   }

   public JMSConnection connectionCreate(DispatcherWrapper dispatcher) throws JMSException {
      ComponentInvocationContext cic = this.feConnectionFactory.getFrontEnd().getService().getComponentInvocationContext();
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(cic);
      Throwable var4 = null;

      JMSConnection var5;
      try {
         var5 = this.connectionCreateInternal(dispatcher, true);
      } catch (Throwable var14) {
         var4 = var14;
         throw var14;
      } finally {
         if (mic != null) {
            if (var4 != null) {
               try {
                  mic.close();
               } catch (Throwable var13) {
                  var4.addSuppressed(var13);
               }
            } else {
               mic.close();
            }
         }

      }

      return var5;
   }

   public JMSConnection connectionCreateRequest(FEConnectionCreateRequest req) throws JMSException {
      this.checkOBS(req);
      ComponentInvocationContext cic = this.feConnectionFactory.getFrontEnd().getService().getComponentInvocationContext();
      AuthenticatedSubject subject = null;

      try {
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(cic);
         Throwable var21 = null;

         JMSConnection var7;
         try {
            if (req.getUserName() != null && (subject = JMSSecurityHelper.getJMSSecurityHelper().getPrincipalAuthenticator().authenticate(new SimpleCallbackHandler(req.getUserName(), req.getPassword()))) == null) {
               throw new JMSSecurityException("Authentication failure");
            }

            JMSConnection conn = this.connectionCreateInternal(req.getDispatcherWrapper(), req.getCreateXAConnection());
            if (req.isSendBackSubject()) {
               conn.setSubject(subject);
            }

            var7 = conn;
         } catch (Throwable var18) {
            var21 = var18;
            throw var18;
         } finally {
            if (mic != null) {
               if (var21 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var17) {
                     var21.addSuppressed(var17);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var7;
      } catch (LoginException var20) {
         JMSSecurityException jse = new JMSSecurityException("Authentication failure due to LoginException");
         jse.setLinkedException(var20);
         throw jse;
      }
   }

   private JMSConnection connectionCreateInternal(DispatcherWrapper clientDispatcher, boolean allowXAConnection) throws JMSException {
      DispatcherOneWay dispatcherOneWay = clientDispatcher.getOneWayDispatcher();
      DispatcherRemote dispatcherRemote = clientDispatcher.getRemoteDispatcher();
      String jmsServicePartitionName = this.feConnectionFactory.getFrontEnd().getService().getPartitionName();

      try {
         PartitionAwareSetter partitionAware;
         String connectionPartitionName;
         if (dispatcherOneWay instanceof PartitionAwareSetter) {
            partitionAware = (PartitionAwareSetter)dispatcherOneWay;
            connectionPartitionName = partitionAware.getConnectionPartitionName();
            if (connectionPartitionName == null || connectionPartitionName.trim().length() == 0) {
               partitionAware.setConnectionPartitionName(jmsServicePartitionName);
            }
         }

         if (dispatcherRemote instanceof PartitionAwareSetter) {
            partitionAware = (PartitionAwareSetter)dispatcherRemote;
            connectionPartitionName = partitionAware.getConnectionPartitionName();
            if (connectionPartitionName == null || connectionPartitionName.trim().length() == 0) {
               partitionAware.setConnectionPartitionName(jmsServicePartitionName);
            }
         }
      } catch (IOException var8) {
         throw new weblogic.jms.common.JMSException(var8);
      }

      return this.feConnectionFactory.connectionCreateInternal(clientDispatcher, allowXAConnection);
   }

   private void checkOBS(FEConnectionCreateRequest req) throws JMSException {
      if (!req.isOBSSupported() && !this.feConnectionFactory.getSecurityPolicy().equals("ThreadBased")) {
         throw new JMSException(JMSExceptionLogger.logOBSCFNotSupportLoggable(this.feConnectionFactory.getJNDIName()).getMessage());
      }
   }
}
