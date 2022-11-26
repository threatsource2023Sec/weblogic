package kodo.jdo.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.jdo.datastore.JDOConnection;
import kodo.jdo.DataStoreException;
import kodo.jdo.UserException;
import org.apache.openjpa.lib.jdbc.DelegatingConnection;
import org.apache.openjpa.lib.util.Localizer;

public class JDBCJDOConnection extends DelegatingConnection implements JDOConnection {
   private static final Localizer _loc = Localizer.forPackage(JDBCJDOConnection.class);

   public JDBCJDOConnection(Object conn) {
      super((Connection)conn);
   }

   public Object getNativeConnection() {
      return this.getDelegate();
   }

   public void close() {
      try {
         this.getDelegate().close();
      } catch (SQLException var2) {
         throw new DataStoreException(var2.getMessage(), new Throwable[]{var2}, (Object)null);
      }
   }

   public void commit() {
      throw new UserException(_loc.get("conn-forbid", "commit"), (Throwable[])null, this);
   }

   public void rollback() {
      throw new UserException(_loc.get("conn-forbid", "rollback"), (Throwable[])null, this);
   }

   public void setTransactionIsolation(int level) {
      throw new UserException(_loc.get("conn-forbid", "setTransactionIsolation"), (Throwable[])null, this);
   }

   public void setCatalog(String catalog) {
      throw new UserException(_loc.get("conn-forbid", "setCatalog"), (Throwable[])null, this);
   }

   public void setTypeMap(Map map) {
      throw new UserException(_loc.get("conn-forbid", "setTypeMap"), (Throwable[])null, this);
   }

   public void setAutoCommit(boolean auto) {
      throw new UserException(_loc.get("conn-forbid", "setAutoCommit"), (Throwable[])null, this);
   }
}
