package weblogic.jms.dotnet.proxy.internal;

import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.dotnet.proxy.protocol.ProxyContextCloseRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyContextLookupConnectionFactoryRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyContextLookupConnectionFactoryResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyContextLookupDestinationRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyContextLookupDestinationResponse;
import weblogic.jms.dotnet.proxy.protocol.ProxyVoidResponse;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.dotnet.transport.ServiceTwoWay;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.extensions.WLDestination;
import weblogic.jndi.internal.WLInternalContext;
import weblogic.protocol.ChannelHelperBase;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;

public class InitialContextProxy extends BaseProxy implements ServiceTwoWay {
   private HashMap connections = new HashMap();
   private HashSet cfServiceIds = new HashSet();
   private Context initialContext;
   private Transport transport;
   private AbstractSubject subject;
   private boolean closedAll;

   public InitialContextProxy(Transport transport, long serviceId, Context context) {
      super(serviceId, (BaseProxy)null);
      this.initialContext = context;
      this.transport = transport;
      this.subject = JMSSecurityHelper.getCurrentSubject();
      if (SubjectUtils.doesUserHaveAnyAdminRoles((AuthenticatedSubject)this.subject) && ChannelHelperBase.isLocalAdminChannelEnabled()) {
         throw new SecurityException("Server has admin channel configured, therefore admin traffic from .NET client is not allowed to go through");
      } else {
         if (JMSSecurityHelper.isServerIdentity((AuthenticatedSubject)this.subject)) {
            this.subject = JMSSecurityHelper.getAnonymousSubject();
         }

         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Creating initial context: subject = " + this.subject);
         }

      }
   }

   private final MarshalWritable lookupFactory(final String jndiName) {
      long id = 0L;
      ConnectionFactoryProxy cf = null;
      synchronized(this) {
         label51: {
            TransportError var10000;
            try {
               ConnectionFactory obj = (ConnectionFactory)JMSSecurityHelper.doAsJNDIOperation(this.subject, new PrivilegedExceptionAction() {
                  public Object run() throws NamingException {
                     return InitialContextProxy.this.initialContext.lookup(jndiName);
                  }
               });
               if (obj instanceof JMSConnectionFactory) {
                  JMSConnectionFactory connectionFactory = (JMSConnectionFactory)obj;
                  String partitionName = connectionFactory.getPartitionName();
                  if (connectionFactory.getPartitionName() != null && !partitionName.equals("DOMAIN")) {
                     throw new JMSException(JMSExceptionLogger.logPartitionedConnectionFactoryNotSupportedLoggable(jndiName).getMessage());
                  }

                  id = this.transport.allocateServiceID();
                  cf = new ConnectionFactoryProxy(id, jndiName, this, obj);
                  this.transport.registerService(id, cf);
                  this.addServiceId(id);
                  break label51;
               }

               var10000 = new TransportError(new JMSException("The connection factory is not a WebLogic JMS connection factory.  The WebLogic JMS .NET client supports WebLogic JMS destinations and connection factories."));
            } catch (ClassCastException var10) {
               return new TransportError(var10);
            } catch (JMSException var11) {
               return new TransportError(var11);
            } catch (NamingException var12) {
               return new TransportError(var12);
            }

            return var10000;
         }
      }

      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Looked up connection factory: id = " + id + " factory = " + cf);
      }

      return new ProxyContextLookupConnectionFactoryResponse(id);
   }

   private final MarshalWritable lookupDestination(final String jndiName) {
      Destination obj = null;

      try {
         obj = (Destination)JMSSecurityHelper.doAsJNDIOperation(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               return InitialContextProxy.this.initialContext.lookup(jndiName);
            }
         });
         if (!(obj instanceof WLDestination)) {
            return new TransportError(new JMSException("The destination is not a WebLogic JMS destination.  The WebLogic JMS .NET client supports WebLogic JMS destinations and connection factories."));
         } else {
            if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
               JMSDebug.JMSDotNetProxy.debug("Looked up JMS destination: " + obj);
            }

            DestinationImpl destination = (DestinationImpl)obj;
            String partitionName = destination.getPartitionName();
            if (destination.getPartitionName() != null && !partitionName.equals("DOMAIN")) {
               throw new JMSException(JMSExceptionLogger.logPartitionedDestinationNotSupportedLoggable(destination.getName(), jndiName).getMessage());
            } else {
               return new ProxyContextLookupDestinationResponse(obj);
            }
         }
      } catch (ClassCastException var5) {
         return new TransportError(var5);
      } catch (JMSException var6) {
         return new TransportError(var6);
      } catch (NamingException var7) {
         return new TransportError(var7);
      }
   }

   private void close() {
      this.closeInternal();
   }

   private void closeInternal() {
      synchronized(this) {
         if ((this.state & 1) != 0) {
            return;
         }

         this.state = 1;
      }

      try {
         JMSSecurityHelper.doAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               if (InitialContextProxy.this.initialContext instanceof WLInternalContext) {
                  ((WLInternalContext)InitialContextProxy.this.initialContext).disableThreadWarningOnClose();
               }

               InitialContextProxy.this.initialContext.close();
               return null;
            }
         });
      } catch (JMSException var3) {
      }

      ProxyManagerImpl.getProxyManager().removeContext(this.getServiceId());
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Closed initial context:  id = " + this.getServiceId());
      }

   }

   private void closeAll() {
      synchronized(this) {
         this.closedAll = true;
      }

      this.cleanup(false);
   }

   synchronized void addConnection(long serviceId, ConnectionProxy conProxy) throws JMSException {
      this.checkShutdownOrClosed("The context has been closed");
      this.connections.put(serviceId, conProxy);
   }

   synchronized void remove(long serviceId) {
      this.connections.remove(serviceId);
   }

   InitialContextProxy getContext() {
      return this;
   }

   private synchronized boolean isClosedAll() {
      return this.closedAll;
   }

   public final void invoke(ReceivedTwoWay rr) {
      if (this.isShutdown()) {
         rr.send(new TransportError(new JMSException("The JMS service is shutting down")));
      } else if (this.isClosedAll()) {
         rr.send(new TransportError(new JMSException("The context has been closed")));
      } else {
         MarshalReadable request = rr.getRequest();
         MarshalWritable response = null;
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Invoking: code = " + request.getMarshalTypeCode() + " request = " + request);
         }

         switch (request.getMarshalTypeCode()) {
            case 3:
               response = this.lookupFactory(((ProxyContextLookupConnectionFactoryRequest)request).getJNDIName());
               break;
            case 4:
            case 6:
            default:
               response = new TransportError("Invalid MarshalReadableType : " + request.getMarshalTypeCode(), false);
               break;
            case 5:
               response = this.lookupDestination(((ProxyContextLookupDestinationRequest)request).getJNDIName());
               break;
            case 7:
               if (((ProxyContextCloseRequest)request).isCloseAll()) {
                  this.closeAll();
               } else {
                  this.close();
               }

               response = ProxyVoidResponse.THE_ONE;
         }

         rr.send((MarshalWritable)response);
      }
   }

   private synchronized void addServiceId(long id) throws JMSException {
      this.checkShutdownOrClosed("The session has been closed");
      this.cfServiceIds.add(id);
   }

   public void cleanup(boolean force) {
      if (!force) {
         this.closeInternal();
         Iterator itr = null;
         synchronized(this) {
            itr = ((HashMap)this.connections.clone()).values().iterator();
            this.connections.clear();
         }

         while(itr.hasNext()) {
            ConnectionProxy connection = (ConnectionProxy)itr.next();

            try {
               connection.close();
            } catch (JMSException var5) {
            }
         }

         this.unregister();
      }
   }

   private void unregister() {
      this.transport.unregisterService(this.serviceId);
      synchronized(this) {
         Iterator itr = this.cfServiceIds.iterator();

         while(itr.hasNext()) {
            long id = (Long)itr.next();
            this.getTransport().unregisterService(id);
         }

         this.cfServiceIds.clear();
      }
   }

   public void onPeerGone(TransportError te) {
      this.cleanup(false);
   }

   public void onShutdown() {
   }

   public void onUnregister() {
   }

   public Transport getTransport() {
      return this.transport;
   }

   public AbstractSubject getSubject() {
      return this.subject;
   }
}
