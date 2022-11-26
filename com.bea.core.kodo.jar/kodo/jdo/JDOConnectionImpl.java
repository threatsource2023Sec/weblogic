package kodo.jdo;

import javax.jdo.datastore.JDOConnection;

public class JDOConnectionImpl implements JDOConnection {
   private final Object _conn;

   public JDOConnectionImpl(Object conn) {
      this._conn = conn;
   }

   public Object getNativeConnection() {
      return this._conn;
   }

   public void close() {
   }

   public int hashCode() {
      return this._conn.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof JDOConnectionImpl) ? false : this._conn.equals(((JDOConnectionImpl)other).getNativeConnection());
      }
   }
}
