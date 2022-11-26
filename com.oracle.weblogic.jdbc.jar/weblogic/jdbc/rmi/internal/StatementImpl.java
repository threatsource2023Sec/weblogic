package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.jdbc.wrapper.JDBCWrapperImpl;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.server.UnicastRemoteObject;

public class StatementImpl extends RMISkelWrapperImpl implements InteropWriteReplaceable {
   private java.sql.Statement t2_stmt = null;
   RmiDriverSettings rmiSettings = null;
   private java.sql.ResultSet curr_remote_rs = null;
   private weblogic.jdbc.wrapper.ResultSet curr_rs = null;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         try {
            if (ret instanceof java.sql.ResultSet) {
               if (this.curr_rs != null && !this.curr_rs.internalIsClosed() && this.curr_remote_rs != null) {
                  try {
                     if (this.curr_remote_rs instanceof JDBCWrapperImpl && ret instanceof JDBCWrapperImpl && ((JDBCWrapperImpl)this.curr_remote_rs).getVendorObj() == ((JDBCWrapperImpl)ret).getVendorObj()) {
                        super.postInvocationHandler(methodName, params, this.curr_remote_rs);
                        return this.curr_remote_rs;
                     }
                  } catch (Exception var5) {
                  }
               }

               if (ret instanceof weblogic.jdbc.wrapper.ResultSet) {
                  this.curr_rs = (weblogic.jdbc.wrapper.ResultSet)ret;
               } else {
                  this.curr_rs = null;
               }

               this.curr_remote_rs = ResultSetImpl.makeResultSetImpl((java.sql.ResultSet)ret, this.rmiSettings);
               ret = this.curr_remote_rs;
            } else if (ret instanceof java.sql.Blob) {
               ret = OracleTBlobImpl.makeOracleTBlobImpl((java.sql.Blob)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Clob) {
               ret = OracleTClobImpl.makeOracleTClobImpl((java.sql.Clob)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Struct) {
               ret = StructImpl.makeStructImpl((java.sql.Struct)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Ref) {
               ret = RefImpl.makeRefImpl((java.sql.Ref)ret, this.rmiSettings);
            } else if (ret instanceof java.sql.Array) {
               ret = ArrayImpl.makeArrayImpl((java.sql.Array)ret, this.rmiSettings);
            }
         } catch (Exception var6) {
            JDBCLogger.logStackTrace(var6);
            throw var6;
         }

         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }

   public StatementImpl() {
      this.rmiSettings = new RmiDriverSettings();
   }

   public StatementImpl(java.sql.Statement s, RmiDriverSettings settings) {
      this.init(s, settings);
   }

   public void init(java.sql.Statement s, RmiDriverSettings settings) {
      this.t2_stmt = s;
      this.rmiSettings = new RmiDriverSettings(settings);
   }

   public java.sql.Statement getImplDelegate() {
      return this.t2_stmt;
   }

   public static java.sql.Statement makeStatementImpl(java.sql.Statement s, RmiDriverSettings settings) {
      if (s == null) {
         return null;
      } else {
         StatementImpl rmi_stmt = (StatementImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.StatementImpl", s, true);
         rmi_stmt.init(s, settings);
         return (java.sql.Statement)rmi_stmt;
      }
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      Object stub = StubFactory.getStub((Remote)this);
      return new StatementStub((Statement)stub, this.rmiSettings);
   }

   public int getRmiFetchSize() throws SQLException {
      return this.rmiSettings.getRowCacheSize();
   }

   public void setRmiFetchSize(int new_size) throws SQLException {
      this.rmiSettings.setRowCacheSize(new_size);
   }

   public void close() throws SQLException {
      this.t2_stmt.close();

      try {
         UnicastRemoteObject.unexportObject(this, true);
      } catch (NoSuchObjectException var2) {
      }

   }
}
