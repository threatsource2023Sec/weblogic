package weblogic.jms.adapter;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.IllegalStateException;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.resource.spi.SecurityException;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;
import weblogic.jms.bridge.AdapterConnection;
import weblogic.jms.bridge.AdapterConnectionMetaData;

public class JMSManagedConnection implements ManagedConnection {
   private AdapterConnection con;
   private JMSConnectionRequestInfo connRequestInfo;
   private String user;
   private EventListenerManager listenerMgr;
   private ManagedConnectionFactory mcf;
   private transient PrintWriter logWriter;
   private final Object logWriterLock = new Object();
   private boolean supportsXA;
   private boolean supportsLocalTx;
   private boolean destroyed;
   private Set connectionSet;
   private AdapterConnectionMetaData metaData;
   private ManagedConnectionMetaData conMetaData;

   JMSManagedConnection(ManagedConnectionFactory mcf, String user, AdapterConnection con, JMSConnectionRequestInfo info, boolean supportsXA, boolean supportsLocalTx) {
      this.mcf = mcf;
      this.user = user;
      this.con = con;
      this.connRequestInfo = info;
      this.supportsXA = supportsXA;
      this.supportsLocalTx = supportsLocalTx;
      this.connectionSet = new HashSet();
      this.listenerMgr = new EventListenerManager(this);

      try {
         this.metaData = ((JMSBaseConnection)con).getMetaData();
      } catch (ResourceException var8) {
      }

      this.conMetaData = new ConnectionMetaDataImpl(this);
   }

   public Object getConnection(Subject subject, ConnectionRequestInfo connectionRequestInfo) throws ResourceException {
      PasswordCredential pc = Util.getPasswordCredential(this.mcf, subject, connectionRequestInfo);
      if (pc == null) {
         if (this.user != null) {
            throw new SecurityException("Principal does not match. Reauthentication not supported");
         }
      } else if (!pc.getUserName().equals(this.user)) {
         throw new SecurityException("Principal does not match. Reauthentication not supported");
      }

      synchronized(this) {
         this.checkIfDestroyed();
         JMSConnectionHandle jmsCon = new JMSConnectionHandle(this);
         this.addJMSConnectionHandle(jmsCon);
         return jmsCon;
      }
   }

   public void destroy() throws ResourceException {
      Iterator it = null;
      synchronized(this) {
         if (this.destroyed) {
            return;
         }

         it = this.connectionSet.iterator();
      }

      this.con.close();

      while(it.hasNext()) {
         JMSConnectionHandle jmsCon = (JMSConnectionHandle)it.next();
         jmsCon.invalidate();
      }

      synchronized(this) {
         this.connectionSet.clear();
         this.destroyed = true;
      }
   }

   public void cleanup() throws ResourceException {
      Iterator it = null;
      synchronized(this) {
         if (this.isDestroyed()) {
            return;
         }

         it = this.connectionSet.iterator();
      }

      try {
         if (((JMSManagedConnectionFactory)this.mcf).isWLSAdapter()) {
            this.con.close();
         } else {
            ((JMSBaseConnection)this.con).cleanup();
         }
      } catch (Exception var6) {
      }

      while(it.hasNext()) {
         JMSConnectionHandle jmsCon = (JMSConnectionHandle)it.next();
         jmsCon.invalidate();
      }

      synchronized(this) {
         this.connectionSet.clear();
      }
   }

   public void associateConnection(Object connection) throws ResourceException {
      this.checkIfDestroyed();
      if (connection instanceof JMSConnectionHandle) {
         JMSConnectionHandle jmsCon = (JMSConnectionHandle)connection;
         jmsCon.associateConnection(this);
      } else {
         throw new IllegalStateException("Invalid connection object: " + connection);
      }
   }

   public void addConnectionEventListener(ConnectionEventListener listener) {
      this.listenerMgr.addConnectorListener(listener);
   }

   public void removeConnectionEventListener(ConnectionEventListener listener) {
      this.listenerMgr.removeConnectorListener(listener);
   }

   public XAResource getXAResource() throws ResourceException {
      if (!this.supportsXA) {
         throw new NotSupportedException("XA transaction not supported");
      } else {
         this.checkIfDestroyed();
         return this.con.getXAResource();
      }
   }

   public synchronized LocalTransaction getLocalTransaction() throws ResourceException {
      if (!this.supportsLocalTx) {
         throw new NotSupportedException("Local transaction not supported");
      } else {
         this.checkIfDestroyed();
         return new SpiLocalTransactionImpl(this);
      }
   }

   public ManagedConnectionMetaData getMetaData() throws ResourceException {
      this.checkIfDestroyed();
      return this.conMetaData;
   }

   public void setLogWriter(PrintWriter out) throws ResourceException {
      this.checkIfDestroyed();
      synchronized(this.logWriterLock) {
         this.logWriter = out;
      }
   }

   public PrintWriter getLogWriter() throws ResourceException {
      this.checkIfDestroyed();
      synchronized(this.logWriterLock) {
         return this.logWriter;
      }
   }

   AdapterConnection getJMSBaseConnection() throws ResourceException {
      this.checkIfDestroyed();
      return this.con;
   }

   synchronized boolean isDestroyed() {
      return this.destroyed;
   }

   String getUserName() throws ResourceException {
      this.checkIfDestroyed();
      return this.user;
   }

   void sendEvent(int eventType, Exception ex) {
      this.sendEvent(eventType, ex, (Object)null);
   }

   void sendEvent(int eventType, Exception ex, Object connectionHandle) {
      if (eventType != 1) {
         synchronized(this) {
            if (this.destroyed) {
               return;
            }
         }
      }

      this.listenerMgr.sendEvent(eventType, ex, connectionHandle);
   }

   synchronized void removeJMSConnectionHandle(JMSConnectionHandle jmsCon) throws ResourceException {
      if (!this.destroyed) {
         this.connectionSet.remove(jmsCon);
      }
   }

   synchronized void addJMSConnectionHandle(JMSConnectionHandle jmsCon) throws ResourceException {
      this.checkIfDestroyed();
      this.connectionSet.add(jmsCon);
   }

   private synchronized void checkIfDestroyed() throws ResourceException {
      if (this.isDestroyed()) {
         throw new IllegalStateException("Managed connection is destroyed");
      }
   }

   ManagedConnectionFactory getManagedConnectionFactory() {
      return this.mcf;
   }

   JMSConnectionRequestInfo getConnectionRequestInfo() {
      return this.connRequestInfo;
   }

   synchronized int getMaxConnections() throws ResourceException {
      this.checkIfDestroyed();
      return this.connectionSet.size();
   }
}
