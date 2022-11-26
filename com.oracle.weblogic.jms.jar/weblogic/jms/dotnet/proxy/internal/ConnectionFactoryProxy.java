package weblogic.jms.dotnet.proxy.internal;

import java.security.PrivilegedExceptionAction;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.dotnet.proxy.protocol.ProxyConnectionCreateRequest;
import weblogic.jms.dotnet.proxy.protocol.ProxyConnectionCreateResponse;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.dotnet.transport.ServiceTwoWay;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.jms.dotnet.transport.TransportError;
import weblogic.jms.extensions.WLConnection;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;

public class ConnectionFactoryProxy extends BaseProxy implements ServiceTwoWay {
   private String jndiName;
   private ConnectionFactory factory;

   public ConnectionFactoryProxy(long id, String jndiName, InitialContextProxy parent, ConnectionFactory clientFactory) {
      super(id, parent);
      this.jndiName = jndiName;
      this.factory = clientFactory;
   }

   private final Connection createConnection() throws JMSException {
      return this.factory.createConnection();
   }

   private final Connection createConnection(String username, String password) throws JMSException {
      return this.factory.createConnection(username, password);
   }

   private final MarshalWritable createConnection(ProxyConnectionCreateRequest request) {
      String user;
      String pass;
      try {
         user = request.getUserName();
         user = EncrypUtil.decryptString(this.getTransport(), user);
         pass = request.getPassword();
         pass = EncrypUtil.decryptString(this.getTransport(), pass);
      } catch (Exception var18) {
         return new TransportError(var18);
      }

      final String username = user;
      final String password = pass;
      boolean isXA = request.isCreateXAConnection();
      Connection connection = null;
      AuthenticatedSubject threadSubject = JMSSecurityHelper.getCurrentSubject();

      TransportError var10;
      try {
         connection = (Connection)JMSSecurityHelper.doAs(this.getSubject(), new PrivilegedExceptionAction() {
            public Object run() throws JMSException {
               return username == null ? ConnectionFactoryProxy.this.createConnection() : ConnectionFactoryProxy.this.createConnection(username, password);
            }
         });
         long serviceId = this.getTransport().allocateServiceID();
         int acknowledgePolicy = ((WLConnection)connection).getAcknowledgePolicy();
         if (acknowledgePolicy != 2 && acknowledgePolicy != 1) {
            throw new JMSException("Unknown acknowledge policy " + acknowledgePolicy);
         }

         ((WLConnection)connection).setAcknowledgePolicy(2);
         ConnectionProxy conProxy = new ConnectionProxy(serviceId, (InitialContextProxy)this.parent, connection, username, password, request.getListenerServiceId());
         this.getTransport().registerService(serviceId, conProxy);
         ((InitialContextProxy)this.parent).addConnection(serviceId, conProxy);
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Created JMS connection: id = " + serviceId + " client id = " + connection.getClientID());
         }

         ProxyConnectionCreateResponse var13 = new ProxyConnectionCreateResponse(serviceId, connection.getClientID(), acknowledgePolicy, conProxy.getMetadata());
         return var13;
      } catch (JMSException var19) {
         var10 = new TransportError(var19);
      } finally {
         JMSSecurityHelper.pushSubject(threadSubject);
      }

      return var10;
   }

   synchronized void remove(long serviceId) {
   }

   public final void invoke(ReceivedTwoWay rr) {
      if (this.isShutdown()) {
         rr.send(new TransportError(new JMSException("The JMS service is shutting down")));
      } else {
         MarshalReadable request = rr.getRequest();
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Invoking: code = " + request.getMarshalTypeCode() + " request = " + request);
         }

         MarshalWritable response = null;
         switch (request.getMarshalTypeCode()) {
            case 9:
               response = this.createConnection((ProxyConnectionCreateRequest)request);
               break;
            default:
               response = new TransportError("Invalid MarshalReadableType : " + request.getMarshalTypeCode(), false);
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

   public Transport getTransport() {
      return this.parent.getTransport();
   }

   InitialContextProxy getContext() {
      return this.parent.getContext();
   }

   public AbstractSubject getSubject() {
      return this.parent.getSubject();
   }
}
