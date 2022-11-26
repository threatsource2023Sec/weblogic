package kodo.jdbc.ee;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.security.auth.Subject;
import kodo.ee.DefaultConnectionManager;
import kodo.ee.KodoConnectionFactory;
import kodo.ee.KodoManagedConnection;
import kodo.ee.KodoManagedConnectionFactory;
import kodo.jdbc.conf.JDBCConsolidatedConfiguration;
import org.apache.commons.lang.ObjectUtils;

public abstract class JDBCManagedConnectionFactory extends JDBCConsolidatedConfiguration implements KodoManagedConnectionFactory {
   private transient PrintWriter _logWriter;

   public JDBCManagedConnectionFactory() {
      this.setTransactionModeManaged(true);
   }

   public Object createConnectionFactory() throws ResourceException {
      return this.createConnectionFactory(new DefaultConnectionManager());
   }

   public abstract Object createConnectionFactory(ConnectionManager var1) throws ResourceException;

   public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cri) throws NotSupportedException, ResourceException {
      return new KodoManagedConnection((KodoConnectionFactory)this.createConnectionFactory(), this, subject, cri);
   }

   public ManagedConnection matchManagedConnections(Set connections, Subject subject, ConnectionRequestInfo cri) throws NotSupportedException, ResourceException {
      Iterator i = connections.iterator();

      while(i.hasNext()) {
         Object next = i.next();
         if (next instanceof KodoManagedConnection) {
            KodoManagedConnection jmc = (KodoManagedConnection)next;
            if (this.equals(jmc.getManagedConnectionFactory())) {
               Subject otherSubject = jmc.getSubject();
               if (ObjectUtils.equals(subject, otherSubject)) {
                  ConnectionRequestInfo othercri = jmc.getConnectionRequestInfo();
                  if (ObjectUtils.equals(cri, othercri)) {
                     return jmc;
                  }
               }
            }
         }
      }

      return null;
   }

   public void write(PrintStream out) {
      throw new UnsupportedOperationException();
   }

   public void setLogWriter(PrintWriter logWriter) {
      this._logWriter = logWriter;
   }

   public PrintWriter getLogWriter() {
      return this._logWriter;
   }
}
