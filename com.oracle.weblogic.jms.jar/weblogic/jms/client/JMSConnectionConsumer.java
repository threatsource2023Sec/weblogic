package weblogic.jms.client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.InvocableManagerDelegate;
import weblogic.jms.frontend.FEConnectionConsumerCloseRequest;
import weblogic.messaging.dispatcher.DispatcherUtils;

public final class JMSConnectionConsumer implements ConnectionConsumer, Externalizable {
   static final long serialVersionUID = -4442173828993452834L;
   private static final byte EXTVERSION = 1;
   private JMSID connectionId;
   private ServerSessionPool serverSessionPool;
   private JMSID connectionConsumerId;

   public JMSConnectionConsumer(JMSID connectionId, ServerSessionPool serverSessionPool, JMSID connectionConsumerId) {
      this.connectionId = connectionId;
      this.serverSessionPool = serverSessionPool;
      this.connectionConsumerId = connectionConsumerId;
   }

   public JMSConnectionConsumer() {
   }

   public synchronized void close() throws JMSException {
      String partId = DispatcherUtils.getPartitionId();
      Map allPartitionMap = InvocableManagerDelegate.mapForAllParitions(3);
      if (allPartitionMap != null) {
         JMSConnection connection = (JMSConnection)allPartitionMap.get(this.connectionId);
         if (connection != null) {
            Object response = connection.getFrontEndDispatcher().dispatchSync(new FEConnectionConsumerCloseRequest(this.connectionId, this.connectionConsumerId));
         }
      }
   }

   public ServerSessionPool getServerSessionPool() throws JMSException {
      return this.serverSessionPool;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(1);
      this.connectionId.writeExternal(out);
      ((JMSServerSessionPool)this.serverSessionPool).writeExternal(out);
      this.connectionConsumerId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte vrsn = in.readByte();
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      } else {
         this.connectionId = new JMSID();
         this.connectionId.readExternal(in);
         this.serverSessionPool = new JMSServerSessionPool();
         ((JMSServerSessionPool)this.serverSessionPool).readExternal(in);
         this.connectionConsumerId = new JMSID();
         this.connectionConsumerId.readExternal(in);
      }
   }
}
