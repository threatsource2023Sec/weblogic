package weblogic.jms.adapter;

import javax.jms.ExceptionListener;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.resource.ResourceException;
import javax.resource.spi.IllegalStateException;
import javax.transaction.xa.XAResource;
import weblogic.jms.bridge.AdapterConnection;
import weblogic.jms.bridge.AdapterConnectionMetaData;
import weblogic.jms.bridge.GenericMessage;
import weblogic.jms.bridge.LocalTransaction;
import weblogic.jms.bridge.NotificationListener;
import weblogic.jms.bridge.SourceConnection;
import weblogic.jms.bridge.TargetConnection;

public class JMSConnectionHandle implements SourceConnection, TargetConnection {
   private JMSManagedConnection managedCon;

   public JMSConnectionHandle(JMSManagedConnection managedCon) {
      this.managedCon = managedCon;
   }

   public void start() throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      con.start();
   }

   public void close() throws ResourceException {
      if (this.managedCon != null) {
         if (!this.managedCon.isDestroyed()) {
            this.managedCon.sendEvent(5, (Exception)null, this);
            this.managedCon = null;
         }
      }
   }

   public void pause() throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      con.pause();
   }

   public void resume() throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      con.resume();
   }

   public boolean isClosed() {
      return this.managedCon == null;
   }

   public LocalTransaction getLocalTransaction() throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      return con.getLocalTransaction();
   }

   public void addNotificationListener(NotificationListener nListener, int event) throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      con.addNotificationListener(nListener, event);
   }

   public void removeNotificationListener(NotificationListener nListener, int event) throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      con.removeNotificationListener(nListener, event);
   }

   public void associateTransaction(Message msg) throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      con.associateTransaction(msg);
   }

   public void associateTransaction(GenericMessage msg) throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      con.associateTransaction(msg);
   }

   public Message receive() throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      return con.receive();
   }

   public GenericMessage receiveGenericMessage() throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      return con.receiveGenericMessage();
   }

   public Message receive(long timeout) throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      return con.receive(timeout);
   }

   public GenericMessage receiveGenericMessage(long timeout) throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      return con.receiveGenericMessage(timeout);
   }

   public void setMessageListener(MessageListener mListener) throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      con.setMessageListener(mListener);
   }

   public void setExceptionListener(ExceptionListener eListener) throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      con.setExceptionListener(eListener);
   }

   public void send(Message msg) throws ResourceException {
      TargetConnection con = (TargetConnection)this.getJMSBaseConnection();
      con.send(msg);
   }

   public void send(GenericMessage msg) throws ResourceException {
      TargetConnection con = (TargetConnection)this.getJMSBaseConnection();
      con.send(msg);
   }

   public Message createMessage(Message msg) throws ResourceException {
      TargetConnection con = (TargetConnection)this.getJMSBaseConnection();
      return con.createMessage(msg);
   }

   public Message createMessage(GenericMessage msg) throws ResourceException {
      TargetConnection con = (TargetConnection)this.getJMSBaseConnection();
      return con.createMessage(msg);
   }

   public GenericMessage createGenericMessage(Message msg) throws ResourceException {
      TargetConnection con = (TargetConnection)this.getJMSBaseConnection();
      return con.createGenericMessage(msg);
   }

   public GenericMessage createGenericMessage(GenericMessage msg) throws ResourceException {
      TargetConnection con = (TargetConnection)this.getJMSBaseConnection();
      return con.createGenericMessage(msg);
   }

   public XAResource getXAResource() throws ResourceException {
      AdapterConnection con = this.getJMSBaseConnection();
      return con.getXAResource();
   }

   public boolean implementsMDBTransaction() throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      return ((JMSBaseConnection)con).implementsMDBTransaction();
   }

   public void setAcknowledgeMode(int mode) throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      con.setAcknowledgeMode(mode);
   }

   public void recover() throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      con.recover();
   }

   public void acknowledge(Message msg) throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      con.acknowledge(msg);
   }

   public AdapterConnectionMetaData getMetaData() throws ResourceException {
      SourceConnection con = (SourceConnection)this.getJMSBaseConnection();
      return con.getMetaData();
   }

   void associateConnection(JMSManagedConnection newMc) throws ResourceException {
      this.checkIfValid();
      this.managedCon.removeJMSConnectionHandle(this);
      newMc.addJMSConnectionHandle(this);
      this.managedCon = newMc;
   }

   void checkIfValid() throws ResourceException {
      if (this.managedCon == null) {
         throw new IllegalStateException("Connection is invalid");
      }
   }

   AdapterConnection getJMSBaseConnection() throws ResourceException {
      this.checkIfValid();
      return this.managedCon.getJMSBaseConnection();
   }

   void invalidate() throws ResourceException {
      if (this.managedCon != null) {
         this.managedCon.removeJMSConnectionHandle(this);
         this.managedCon = null;
      }
   }
}
