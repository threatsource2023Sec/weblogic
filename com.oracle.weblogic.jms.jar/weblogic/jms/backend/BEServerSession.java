package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.ServerSession;
import javax.jms.Session;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSUtilities;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.work.WorkAdapter;

public final class BEServerSession extends WorkAdapter implements ServerSession, Externalizable {
   static final long serialVersionUID = 7855664711553219989L;
   private static final byte EXTVERSION = 1;
   private Connection connection;
   private Session session;
   private BEServerSessionPool serverSessionPool;
   private BEServerSession next;

   BEServerSession(Connection connection, Session session, BEServerSessionPool serverSessionPool) {
      this.connection = connection;
      this.session = session;
      this.serverSessionPool = serverSessionPool;
   }

   public BEServerSession() {
   }

   public BEServerSession getNext() {
      return this.next;
   }

   public void setNext(BEServerSession next) {
      this.next = next;
   }

   void close() throws JMSException {
      this.session.close();
   }

   void goBackInPool() {
      this.serverSessionPool.serverSessionPut(this);
   }

   public Session getSession() throws JMSException {
      return this.session;
   }

   public void start() {
      this.serverSessionPool.getBackEnd().getWorkManager().schedule(this);
   }

   public synchronized void run() {
      try {
         if (this.session != null) {
            this.session.run();
         } else if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Error running session for connection consumer");
         }
      } catch (Exception var5) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Error running session for connection consumer" + var5.toString());
         }
      } finally {
         this.goBackInPool();
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(1);
      out.writeObject(this.serverSessionPool);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int vrsn = in.readByte();
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      } else {
         this.serverSessionPool = (BEServerSessionPool)PortableRemoteObject.narrow(in.readObject(), BEServerSessionPool.class);
      }
   }
}
