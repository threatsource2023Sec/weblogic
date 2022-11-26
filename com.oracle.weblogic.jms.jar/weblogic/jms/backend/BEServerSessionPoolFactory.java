package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.ServerSessionPool;
import javax.jms.TopicConnection;
import weblogic.jms.JMSService;
import weblogic.jms.client.ConnectionInternal;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSServerSessionPoolCreateResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.extensions.ServerSessionPoolFactory;
import weblogic.jms.frontend.FEServerSessionPoolCreateRequest;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.Response;
import weblogic.store.common.PartitionNameUtils;

public final class BEServerSessionPoolFactory implements ServerSessionPoolFactory, Externalizable {
   private static final byte EXTVERSION = 1;
   static final long serialVersionUID = -5270077330309148361L;
   private JMSServerId backEndId;
   private JMSService jmsService;

   public BEServerSessionPoolFactory(BackEnd backend) {
      this.backEndId = backend.getJMSServerId();
      this.jmsService = backend.getJmsService();
   }

   public ServerSessionPool getServerSessionPool(QueueConnection connection, int sessionsMaximum, boolean transacted, int acknowledgeMode, String messageListenerClass) throws JMSException {
      return this.createServerSessionPool(connection, sessionsMaximum, acknowledgeMode, transacted, messageListenerClass, (Serializable)null);
   }

   public ServerSessionPool getServerSessionPool(TopicConnection connection, int sessionsMaximum, boolean transacted, int acknowledgeMode, String messageListenerClass) throws JMSException {
      return this.createServerSessionPool(connection, sessionsMaximum, acknowledgeMode, transacted, messageListenerClass, (Serializable)null);
   }

   public ServerSessionPool createServerSessionPool(Connection connection, int sessionsMaximum, int acknowledgeMode, boolean transacted, String messageListenerClass, Serializable clientData) throws JMSException {
      if (!(connection instanceof ConnectionInternal)) {
         throw new weblogic.jms.common.JMSException("Connection is foreign");
      } else {
         JMSID connectionId = ((ConnectionInternal)connection).getJMSID();
         if (connectionId.getTimestamp() == this.backEndId.getTimestamp() && connectionId.getSeed() == this.backEndId.getSeed()) {
            if (PartitionNameUtils.isInPartition()) {
               throw new JMSException("Cannot create server session pool in RG/RGT scope");
            } else {
               Response response;
               try {
                  JMSDispatcher feDispatcher = ((ConnectionInternal)connection).getFrontEndDispatcher();
                  if (!feDispatcher.isLocal()) {
                     if (this.jmsService == null) {
                        this.jmsService = JMSService.getJMSServiceWithJMSException();
                     }

                     feDispatcher = this.jmsService.dispatcherFindOrCreate(feDispatcher.getId());
                  }

                  response = feDispatcher.dispatchSync(new FEServerSessionPoolCreateRequest(connectionId, this.backEndId, sessionsMaximum, acknowledgeMode, transacted, messageListenerClass, clientData));
               } catch (DispatcherException var10) {
                  throw new weblogic.jms.common.JMSException("Error creating server session pool", var10);
               }

               return ((JMSServerSessionPoolCreateResponse)response).getServerSessionPool();
            }
         } else {
            throw new weblogic.jms.common.JMSException("Connection is remote");
         }
      }
   }

   public BEServerSessionPoolFactory() {
   }

   public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
      byte vrsn = in.readByte();
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      } else {
         this.backEndId = new JMSServerId();
         this.backEndId.readExternal(in);
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(1);
      this.backEndId.writeExternal(out);
   }
}
