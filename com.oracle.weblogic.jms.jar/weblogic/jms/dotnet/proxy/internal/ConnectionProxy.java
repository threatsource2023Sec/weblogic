package weblogic.jms.dotnet.proxy.internal;

import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.security.auth.login.LoginException;
import weblogic.jms.client.ConnectionInternal;
import weblogic.jms.common.JMSConstants;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.dotnet.proxy.protocol.ProxyConnectionCommandRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyConnectionMetaDataImpl;
import weblogic.jms.dotnet.proxy.protocol.ProxyConnectionSetClientIdRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyPushExceptionRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxySessionCreateRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxySessionCreateResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyVoidResponse;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.dotnet.transport.SendHandlerOneWay;
import weblogic.jms.dotnet.transport.ServiceTwoWay;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.extensions.WLConnection;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;

public class ConnectionProxy extends BaseProxy implements ServiceTwoWay, ExceptionListener {
   private Connection connection;
   private AbstractSubject subject;
   private final long listenerServiceId;
   private ProxyConnectionMetaDataImpl metadata;
   private Map sessions = new HashMap();

   ConnectionProxy(long serviceId, InitialContextProxy parent, final Connection connection, String username, String password, long listenerServiceId) throws JMSException {
      super(serviceId, parent);
      this.connection = connection;
      if (connection instanceof WLConnection) {
         ((WLConnection)connection).setReconnectPolicy(JMSConstants.RECONNECT_POLICY_NONE);
      }

      if (connection instanceof ConnectionInternal && ((ConnectionInternal)connection).getFEPeerInfo().getMajor() < 8) {
         throw new JMSException(".NET client cannot talk to a pre-8.1 server");
      } else {
         this.subject = null;
         if (username != null && password != null) {
            try {
               this.subject = JMSSecurityHelper.authenticatedSubject(username, password);
            } catch (LoginException var10) {
               throw new JMSException("User '" + username + "' does not have the permission!");
            }
         } else {
            this.subject = parent.getSubject();
         }

         if (JMSSecurityHelper.isServerIdentity((AuthenticatedSubject)this.subject)) {
            this.subject = JMSSecurityHelper.getAnonymousSubject();
         }

         this.listenerServiceId = listenerServiceId;
         JMSSecurityHelper.doAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               connection.setExceptionListener(ConnectionProxy.this);
               return null;
            }
         });
         this.metadata = new ProxyConnectionMetaDataImpl(connection.getMetaData());
      }
   }

   private final synchronized void setClientId(final String clientId) throws JMSException {
      JMSSecurityHelper.doAs(this.subject, new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            if (ConnectionProxy.this.connection != null) {
               ConnectionProxy.this.connection.setClientID(clientId);
            }

            return null;
         }
      });
   }

   private final synchronized void start() throws JMSException {
      JMSSecurityHelper.doAs(this.subject, new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            if (ConnectionProxy.this.connection != null) {
               ConnectionProxy.this.connection.start();
            }

            return null;
         }
      });
   }

   private final synchronized void stop() throws JMSException {
      JMSSecurityHelper.doAs(this.subject, new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            if (ConnectionProxy.this.connection != null) {
               ConnectionProxy.this.connection.stop();
            }

            return null;
         }
      });
   }

   final void close() throws JMSException {
      synchronized(this) {
         if ((this.state & 1) != 0) {
            return;
         }

         this.state = 1;
      }

      this.unregister();
      this.parent.remove(this.serviceId);
      JMSSecurityHelper.doAs(this.subject, new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            ConnectionProxy.this.connection.close();
            return null;
         }
      });
   }

   private void unregister() {
      this.getTransport().unregisterService(this.serviceId);
      synchronized(this) {
         Iterator itr = this.sessions.values().iterator();

         while(itr.hasNext()) {
            SessionProxy session = (SessionProxy)itr.next();
            session.unregister();
         }

         this.sessions.clear();
      }
   }

   public final String toString() {
      return "Proxy for " + this.connection.toString();
   }

   private final MarshalWritable createSession(ProxySessionCreateRequest request) throws JMSException {
      final boolean transacted = request.getTransacted();
      boolean isXA = request.getXASession();
      int ackMode = request.getAcknowledgeMode();
      long sessionMsgListenerServiceId = request.getSessionMsgListenerServiceId();
      final int adjustedAckMode;
      if (ackMode != 1 && ackMode != 3) {
         adjustedAckMode = ackMode;
      } else {
         adjustedAckMode = 2;
      }

      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("CreateSession: transacted = " + transacted + " ackMode = " + this.getAckModeString(ackMode) + " adjusted ackMode = " + this.getAckModeString(adjustedAckMode));
      }

      Session session = (Session)JMSSecurityHelper.doAs(this.subject, new PrivilegedExceptionAction() {
         public Object run() throws JMSException {
            return ConnectionProxy.this.connection.createSession(transacted, adjustedAckMode);
         }
      });
      long sessionId = this.getTransport().allocateServiceID();
      SessionProxy sessionProxy = new SessionProxy(sessionId, this, session, ackMode, sessionMsgListenerServiceId);
      this.getTransport().registerService(sessionId, sessionProxy);
      this.addSession(sessionId, sessionProxy);
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Created JMS session: id = " + this.serviceId);
      }

      return new ProxySessionCreateResponse(sessionId, session);
   }

   private synchronized void addSession(long serviceId, SessionProxy session) throws JMSException {
      this.checkShutdownOrClosed("The connection to the proxy has been closed");
      this.sessions.put(serviceId, session);
   }

   synchronized void remove(long sessionId) {
      this.sessions.remove(sessionId);
   }

   public final void invoke(ReceivedTwoWay rr) {
      if (this.isShutdown()) {
         rr.send(new TransportError(new JMSException("The JMS service is shutting down")));
      } else if (this.isClosed()) {
         rr.send(new TransportError(new JMSException("The connection has been closed")));
      } else {
         MarshalReadable request = rr.getRequest();
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Invoking: code = " + request.getMarshalTypeCode() + " request = " + request);
         }

         MarshalWritable response = ProxyVoidResponse.THE_ONE;

         try {
            switch (request.getMarshalTypeCode()) {
               case 8:
                  int commandCode = ((ProxyConnectionCommandRequest)request).getCommandCode();
                  if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
                     JMSDebug.JMSDotNetProxy.debug("Invoke(): CommandCode = " + ((ProxyConnectionCommandRequest)request).getCommandCodeString());
                  }

                  try {
                     if (commandCode == 1) {
                        this.start();
                     } else if (commandCode == 3) {
                        this.close();
                     } else if (commandCode == 2) {
                        this.stop();
                     }
                  } catch (JMSException var6) {
                     response = new TransportError(var6);
                  }
                  break;
               case 11:
                  this.setClientId(((ProxyConnectionSetClientIdRequest)request).getClientId());
                  break;
               case 30:
                  response = this.createSession((ProxySessionCreateRequest)request);
                  break;
               default:
                  response = new TransportError("Invalid MarshalReadableType : " + request.getMarshalTypeCode(), false);
            }
         } catch (JMSException var7) {
            response = new TransportError(var7);
         }

         rr.send((MarshalWritable)response);
      }
   }

   public void onPeerGone(TransportError te) {
   }

   public void onShutdown() {
   }

   public void onUnregister() {
   }

   Transport getTransport() {
      return this.parent.getTransport();
   }

   InitialContextProxy getContext() {
      return this.parent.getContext();
   }

   AbstractSubject getSubject() {
      return this.subject;
   }

   public ProxyConnectionMetaDataImpl getMetadata() {
      return this.metadata;
   }

   private String getAckModeString(int ackMode) {
      switch (ackMode) {
         case 1:
            return "AUTO_ACKNOWLEDGE";
         case 2:
            return "CLIENT_ACKNOWLEDGE";
         case 3:
            return "DUPS_OK_ACKNOWLEDGE";
         default:
            return this.connection instanceof WLConnection && ackMode == 4 ? "NO_ACKNOWLEDGE" : "Invalid Ack Mode: " + ackMode;
      }
   }

   public void onException(JMSException exception) {
      try {
         this.close();
      } catch (JMSException var10) {
      }

      synchronized(this) {
         Transport transport = this.getTransport();
         final SendHandlerOneWay sow = transport.createOneWay(this.listenerServiceId);
         final MarshalWritable request = new ProxyPushExceptionRequest(exception);

         try {
            JMSSecurityHelper.doAs(JMSSecurityHelper.getAnonymousSubject(), new PrivilegedExceptionAction() {
               public Object run() {
                  sow.send(request);
                  return null;
               }
            });
         } catch (JMSException var8) {
         }

      }
   }
}
