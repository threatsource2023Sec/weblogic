package weblogic.jms.client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.JMSException;
import javax.jms.ServerSession;
import javax.jms.ServerSessionPool;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEServerSessionGetRequest;
import weblogic.jms.backend.BEServerSessionGetResponse;
import weblogic.jms.backend.BEServerSessionPoolCloseRequest;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSUtilities;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.Response;

public final class JMSServerSessionPool implements ServerSessionPool, Externalizable {
   static final long serialVersionUID = -6985998084744986160L;
   private static final byte EXTVERSION = 1;
   private boolean isLocal;
   private JMSServerId backEndId;
   private JMSID serverSessionPoolId;

   public JMSServerSessionPool(JMSServerId backEndId, JMSID serverSessionPoolId) {
      this.backEndId = backEndId;
      this.serverSessionPoolId = serverSessionPoolId;
   }

   public synchronized void close() throws JMSException {
      try {
         JMSService jmsService = JMSService.getJMSServiceWithJMSException();
         Object response = jmsService.dispatcherFindOrCreate(this.backEndId.getDispatcherId()).dispatchSync(new BEServerSessionPoolCloseRequest(this.backEndId.getId(), this.serverSessionPoolId));
      } catch (DispatcherException var3) {
         throw new weblogic.jms.common.JMSException("Error closing server session pool", var3);
      }
   }

   public ServerSession getServerSession() throws JMSException {
      Response response;
      try {
         JMSService jmsService = JMSService.getJMSServiceWithJMSException();
         response = jmsService.dispatcherFindOrCreate(this.backEndId.getDispatcherId()).dispatchSync(new BEServerSessionGetRequest(this.backEndId.getId(), this.serverSessionPoolId));
      } catch (DispatcherException var3) {
         throw new weblogic.jms.common.JMSException("Error getting server session", var3);
      }

      return ((BEServerSessionGetResponse)response).getServerSession();
   }

   public JMSID getServerSessionPoolId() {
      return this.serverSessionPoolId;
   }

   public JMSServerId getBackEndId() {
      return this.backEndId;
   }

   public JMSServerSessionPool() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(1);
      if (this.isLocal) {
         out.writeBoolean(true);
      } else {
         out.writeBoolean(false);
      }

      this.backEndId.writeExternal(out);
      this.serverSessionPoolId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte vrsn = in.readByte();
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      } else {
         this.isLocal = in.readBoolean();
         this.backEndId = new JMSServerId();
         this.backEndId.readExternal(in);
         this.serverSessionPoolId = new JMSID();
         this.serverSessionPoolId.readExternal(in);
      }
   }
}
