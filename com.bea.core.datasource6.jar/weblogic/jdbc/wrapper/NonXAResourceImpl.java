package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import javax.transaction.xa.Xid;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.transaction.nonxa.NonXAException;
import weblogic.transaction.nonxa.NonXAResource;

public class NonXAResourceImpl implements NonXAResource {
   private JTSConnection jtsConn;

   public NonXAResourceImpl(JTSConnection aConnection) {
      this.jtsConn = aConnection;
   }

   public void commit(Xid xid, boolean onePhase) throws NonXAException {
      if (!onePhase && !this.jtsConn.getEmulate2PC()) {
         NonXAException nxae = new NonXAException("JDBC driver does not support XA, hence cannot be a participant in two-phase commit. To force this participation, set the GlobalTransactionsProtocol attribute to LoggingLastResource (recommended) or EmulateTwoPhaseCommit for the Data Source = " + this.jtsConn.getDataSourceName());
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            this.jtsConn.traceConn("commit.XAException : ", nxae);
         }

         throw nxae;
      } else {
         try {
            JTSConnection conn = this.jtsConn.getConnection(xid);
            if (conn == null) {
               throw new NonXAException("No connection associated with xid = " + xid);
            } else {
               conn.internalCommit();
            }
         } catch (SQLException var5) {
            NonXAException nxae = new NonXAException(var5.getMessage());
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               this.jtsConn.traceConn("commit.NonXAException : ", nxae);
            }

            throw nxae;
         }
      }
   }

   public void rollback(Xid xid) throws NonXAException {
      try {
         JTSConnection conn = this.jtsConn.getConnection(xid);
         if (conn == null) {
            throw new NonXAException("No connection associated with xid = " + xid);
         } else {
            conn.internalRollback();
         }
      } catch (SQLException var4) {
         NonXAException nxae = new NonXAException(var4.getMessage());
         if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
            this.jtsConn.traceConn("rollback.NonXAException : ", nxae);
         }

         throw nxae;
      }
   }

   public boolean isSameRM(NonXAResource nxar) throws NonXAException {
      if (!(nxar instanceof NonXAResourceImpl)) {
         return false;
      } else {
         NonXAResourceImpl that = (NonXAResourceImpl)nxar;
         return this.getClass().equals(that.getClass());
      }
   }
}
