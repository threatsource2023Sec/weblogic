package weblogic.jdbc.rmi.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.rmi.RMIStubWrapperImpl;
import weblogic.jdbc.rmi.RmiStatement;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.extensions.server.StubDelegateInfo;

public class StatementStub extends RMIStubWrapperImpl implements Serializable, StubDelegateInfo {
   private static final long serialVersionUID = 3782203674892516357L;
   RmiDriverSettings rmiSettings = null;
   java.sql.Statement stmt;

   public StatementStub() {
   }

   public StatementStub(java.sql.Statement stmt, RmiDriverSettings settings) {
      this.init(stmt, settings);
   }

   public void init(java.sql.Statement stmt, RmiDriverSettings settings) {
      this.stmt = stmt;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public Object readResolve() throws ObjectStreamException {
      StatementStub stub = null;

      try {
         stub = (StatementStub)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.StatementStub", this.stmt, false);
         stub.init(this.stmt, this.rmiSettings);
         return (java.sql.Statement)stub;
      } catch (Exception var3) {
         JDBCLogger.logStackTrace(var3);
         return this.stmt;
      }
   }

   public Object getStubDelegate() {
      return this.stmt;
   }

   public int getRmiFetchSize() throws SQLException {
      return this.rmiSettings.getRowCacheSize();
   }

   public void setRmiFetchSize(int new_size) throws SQLException {
      ((RmiStatement)this.stmt).setRmiFetchSize(new_size);
      this.rmiSettings.setRowCacheSize(new_size);
   }

   public void close() throws SQLException {
      if (this.rmiSettings.isVerbose()) {
         String msg = "time=" + System.currentTimeMillis() + " : close";
         JdbcDebug.JDBCRMIInternal.debug(msg);
      }

      this.stmt.close();
   }
}
