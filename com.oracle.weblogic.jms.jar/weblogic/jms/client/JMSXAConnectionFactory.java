package weblogic.jms.client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.Remote;
import javax.jms.JMSException;
import javax.jms.XAConnection;
import javax.jms.XAJMSContext;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;
import javax.jms.XATopicConnection;
import javax.jms.XATopicConnectionFactory;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.frontend.FEConnectionFactoryRemote;
import weblogic.jndi.annotation.CrossPartitionAware;
import weblogic.rmi.extensions.server.RemoteWrapper;

@CrossPartitionAware
public final class JMSXAConnectionFactory extends JMSConnectionFactory implements XAQueueConnectionFactory, XATopicConnectionFactory, Externalizable, RemoteWrapper {
   static final long serialVersionUID = 343051208017579157L;
   private static final byte EXTVERSION = 1;

   public JMSXAConnectionFactory(FEConnectionFactoryRemote feConnectionFactoryRemote, String paramFullyQualifiedName, String partitionName, int securityPolicy) {
      super(feConnectionFactoryRemote, paramFullyQualifiedName, partitionName, securityPolicy);
   }

   public XAQueueConnection createXAQueueConnection(String username, String password) throws JMSException {
      ConnectionInternal connection = this.createConnectionInternal(username, password, true, 1);
      return (XAQueueConnection)connection;
   }

   public XAQueueConnection createXAQueueConnection() throws JMSException {
      ConnectionInternal connection = this.createConnectionInternal((String)null, (String)null, true, 1);
      return (XAQueueConnection)connection;
   }

   public XAConnection createXAConnection(String username, String password) throws JMSException {
      return (XAConnection)this.createConnectionInternal(username, password, true, 0);
   }

   public XAConnection createXAConnection() throws JMSException {
      return (XAConnection)this.createConnectionInternal((String)null, (String)null, true, 0);
   }

   public XATopicConnection createXATopicConnection(String username, String password) throws JMSException {
      ConnectionInternal connection = this.createConnectionInternal(username, password, true, 2);
      return (XATopicConnection)connection;
   }

   public XATopicConnection createXATopicConnection() throws JMSException {
      ConnectionInternal connection = this.createConnectionInternal((String)null, (String)null, true, 2);
      return (XATopicConnection)connection;
   }

   public XAJMSContext createXAContext() {
      return new XAJMSContextImpl(this, getContainerType());
   }

   public XAJMSContext createXAContext(String userName, String password) {
      return new XAJMSContextImpl(this, getContainerType(), userName, password);
   }

   public JMSXAConnectionFactory() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeByte(1);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      int vrsn = in.readByte();
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      }
   }

   public Remote getRemoteDelegate() {
      return super.getRemoteDelegate();
   }
}
