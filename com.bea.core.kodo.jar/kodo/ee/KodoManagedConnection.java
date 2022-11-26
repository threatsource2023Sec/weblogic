package kodo.ee;

import java.io.PrintWriter;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;
import org.apache.openjpa.conf.OpenJPAVersion;
import org.apache.openjpa.lib.util.Localizer;

public class KodoManagedConnection implements ManagedConnection, ManagedConnectionMetaData {
   private static Localizer _loc = Localizer.forPackage(KodoManagedConnectionFactory.class);
   private transient PrintWriter logWriter = null;
   private final KodoManagedConnectionFactory mfactory;
   private final KodoConnectionFactory factory;
   private final Subject subject;
   private final ConnectionRequestInfo connectionRequestInfo;

   public KodoManagedConnection(KodoConnectionFactory factory, KodoManagedConnectionFactory mfactory, Subject subject, ConnectionRequestInfo cri) {
      this.factory = factory;
      this.mfactory = mfactory;
      this.subject = subject;
      this.connectionRequestInfo = cri;
   }

   public Object getConnection(Subject subject, ConnectionRequestInfo cri) throws ResourceException {
      return this.factory.getConnection();
   }

   public void destroy() throws ResourceException {
   }

   public void cleanup() throws ResourceException {
   }

   public void associateConnection(Object connection) throws ResourceException {
   }

   public void addConnectionEventListener(ConnectionEventListener listener) {
   }

   public void removeConnectionEventListener(ConnectionEventListener listener) {
   }

   public XAResource getXAResource() throws ResourceException {
      throw new NotSupportedException(_loc.get("not-supported").getMessage());
   }

   public LocalTransaction getLocalTransaction() throws ResourceException {
      throw new NotSupportedException(_loc.get("not-supported").getMessage());
   }

   public ManagedConnectionMetaData getMetaData() throws ResourceException {
      return this;
   }

   public void setLogWriter(PrintWriter logWriter) {
      this.logWriter = logWriter;
   }

   public PrintWriter getLogWriter() {
      return this.logWriter;
   }

   public ManagedConnectionFactory getManagedConnectionFactory() {
      return this.mfactory;
   }

   public Subject getSubject() {
      return this.subject;
   }

   public ConnectionRequestInfo getConnectionRequestInfo() {
      return this.connectionRequestInfo;
   }

   public String getEISProductName() {
      return "Kodo";
   }

   public String getEISProductVersion() {
      return OpenJPAVersion.VERSION_NUMBER;
   }

   public int getMaxConnections() {
      return Integer.MAX_VALUE;
   }

   public String getUserName() {
      return this.mfactory.getConnectionUserName();
   }
}
