package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import java.sql.SQLTransactionRollbackException;
import java.util.Map;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.jdbc.RollbackSQLException;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.transaction.XAResource;

public class JTSXAResourceImpl implements XAResource {
   protected JTSConnection jtsConn;
   static final long serialVersionUID = 1973216983730090199L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.wrapper.JTSXAResourceImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Commit_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Rollback_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Start_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Rollback_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_After_Start_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Is_SameRM_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Before_Commit_Internal;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Start_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Prepare_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Rollback_Before_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Commit_Before_High;
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_End_Before_High;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;
   static final JoinPoint _WLDF$INST_JPFLD_5;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_5;

   public JTSXAResourceImpl(JTSConnection aConnection) {
      this.jtsConn = aConnection;
   }

   public void commit(Xid xid, boolean onePhase) throws XAException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var6.argsCapture) {
            var6.args = InstrumentationSupport.toSensitive(3);
         }

         if (var6.monitorHolder[1] != null) {
            var6.monitorIndex = 1;
            InstrumentationSupport.process(var6);
         }

         if (var6.monitorHolder[2] != null) {
            var6.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var6);
            InstrumentationSupport.process(var6);
         }

         var6.resetPostBegin();
      }

      try {
         Connection conn = this.getConnection(xid);
         if (conn == null) {
            String msg = "No connection associated with xid = " + xid;
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               this.jtsConn.traceConn("commit.XAException : " + msg);
            }

            throw new XAException(msg);
         }

         if (!onePhase && !this.jtsConn.getEmulate2PC()) {
            try {
               this.jtsConn.internalRollback();
            } catch (Exception var11) {
            }

            XAException xae = new XAException("JDBC driver does not support XA, hence cannot be a participant in two-phase commit. To force this participation, set the GlobalTransactionsProtocol attribute to LoggingLastResource (recommended) or EmulateTwoPhaseCommit for the Data Source = " + this.jtsConn.getDataSourceName());
            xae.errorCode = 6;
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               this.jtsConn.traceConn("commit.XAException : ", xae);
            }

            throw xae;
         }

         XAException xae;
         try {
            this.jtsConn.internalCommit();
         } catch (RollbackSQLException var12) {
            xae = new XAException(var12.getMessage());
            xae.errorCode = 100;
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               this.jtsConn.traceConn("commit.XAException : ", xae);
            }

            throw xae;
         } catch (SQLException var13) {
            xae = new XAException(var13.getMessage());
            xae.errorCode = 8;
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               this.jtsConn.traceConn("commit.XAException : ", xae);
            }

            throw xae;
         }
      } catch (Throwable var14) {
         if (var6 != null) {
            var6.th = var14;
            if (var6.monitorHolder[0] != null) {
               var6.monitorIndex = 0;
               InstrumentationSupport.process(var6);
            }
         }

         throw var14;
      }

      if (var6 != null && var6.monitorHolder[0] != null) {
         var6.monitorIndex = 0;
         InstrumentationSupport.process(var6);
      }

   }

   public void rollback(Xid xid) throws XAException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var4.argsCapture) {
            var4.args = InstrumentationSupport.toSensitive(2);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.process(var4);
         }

         var4.resetPostBegin();
      }

      try {
         try {
            Connection conn = this.getConnection(xid);
            if (conn == null) {
               throw new XAException("No connection associated with xid = " + xid);
            }

            this.jtsConn.internalRollback();
         } catch (SQLException var7) {
            XAException xae = new XAException(var7.getMessage());
            if (var7 instanceof SQLTransactionRollbackException) {
               xae.errorCode = 100;
            } else {
               xae.errorCode = 8;
            }

            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               this.jtsConn.traceConn("rollback.XAException : ", xae);
            }

            throw xae;
         }
      } catch (Throwable var8) {
         if (var4 != null) {
            var4.th = var8;
            if (var4.monitorHolder[1] != null) {
               var4.monitorIndex = 1;
               InstrumentationSupport.process(var4);
            }
         }

         throw var8;
      }

      if (var4 != null && var4.monitorHolder[1] != null) {
         var4.monitorIndex = 1;
         InstrumentationSupport.process(var4);
      }

   }

   public int prepare(Xid var1) throws XAException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      byte var10000;
      try {
         if (!this.jtsConn.getEmulate2PC()) {
            throw new XAException("JDBC driver does not support XA, hence cannot be a participant in two-phase commit. To force this participation, set the GlobalTransactionsProtocol attribute to LoggingLastResource (recommended) or EmulateTwoPhaseCommit for the Data Source = " + this.jtsConn.getDataSourceName());
         }

         var10000 = 0;
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            var2.ret = InstrumentationSupport.convertToObject(0);
            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         var2.ret = InstrumentationSupport.convertToObject(var10000);
         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }
      }

      return var10000;
   }

   public boolean isSameRM(javax.transaction.xa.XAResource xar) throws XAException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[5] != null) {
            var2.monitorIndex = 5;
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[6] != null) {
            var2.monitorIndex = 6;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      boolean var10000;
      try {
         var10000 = this == xar;
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            var2.ret = InstrumentationSupport.convertToObject(false);
            if (var2.monitorHolder[4] != null) {
               var2.monitorIndex = 4;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }

            if (var2.monitorHolder[3] != null) {
               var2.monitorIndex = 3;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.createDynamicJoinPoint(var2);
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         var2.ret = InstrumentationSupport.convertToObject(var10000);
         if (var2.monitorHolder[4] != null) {
            var2.monitorIndex = 4;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[3] != null) {
            var2.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }
      }

      return var10000;
   }

   public void start(Xid var1, int var2) throws XAException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var3.argsCapture) {
            var3.args = InstrumentationSupport.toSensitive(3);
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      if (var3 != null && var3.monitorHolder[2] != null) {
         var3.monitorIndex = 2;
         InstrumentationSupport.process(var3);
      }

   }

   public void end(Xid var1, int var2) throws XAException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_5, _WLDF$INST_JPFLD_JPMONS_5)) != null) {
         if (var3.argsCapture) {
            var3.args = InstrumentationSupport.toSensitive(3);
         }

         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[2] != null) {
            var3.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      if (var3 != null && var3.monitorHolder[0] != null) {
         var3.monitorIndex = 0;
         InstrumentationSupport.process(var3);
      }

   }

   public void forget(Xid xid) throws XAException {
   }

   public int getTransactionTimeout() throws XAException {
      return 0;
   }

   public Xid[] recover(int flags) throws XAException {
      return null;
   }

   public boolean setTransactionTimeout(int t) throws XAException {
      return true;
   }

   public int getDelistFlag() {
      return 67108864;
   }

   public boolean detectedUnavailable() {
      return true;
   }

   protected Connection getConnection(Xid xid) {
      return this.jtsConn.getConnection(xid);
   }

   static {
      _WLDF$INST_FLD_JDBC_After_Commit_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Commit_Internal");
      _WLDF$INST_FLD_JDBC_Before_Rollback_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Rollback_Internal");
      _WLDF$INST_FLD_JDBC_Before_Start_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Start_Internal");
      _WLDF$INST_FLD_JDBC_After_Rollback_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Rollback_Internal");
      _WLDF$INST_FLD_JDBC_After_Start_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_After_Start_Internal");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Is_SameRM_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Is_SameRM_Before_High");
      _WLDF$INST_FLD_JDBC_Before_Commit_Internal = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Before_Commit_Internal");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Start_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Start_Before_High");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Prepare_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Prepare_Before_High");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Rollback_Before_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Rollback_Before_Low");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Commit_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_Commit_Before_High");
      _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_End_Before_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Transaction_End_Before_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSXAResourceImpl.java", "weblogic.jdbc.wrapper.JTSXAResourceImpl", "commit", "(Ljavax/transaction/xa/Xid;Z)V", 24, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Commit_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Commit_Before_High, _WLDF$INST_FLD_JDBC_Before_Commit_Internal};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSXAResourceImpl.java", "weblogic.jdbc.wrapper.JTSXAResourceImpl", "rollback", "(Ljavax/transaction/xa/Xid;)V", 70, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Before_Rollback_Internal, _WLDF$INST_FLD_JDBC_After_Rollback_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Rollback_Before_Low};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSXAResourceImpl.java", "weblogic.jdbc.wrapper.JTSXAResourceImpl", "prepare", "(Ljavax/transaction/xa/Xid;)I", 92, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Commit_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Prepare_Before_High, _WLDF$INST_FLD_JDBC_Before_Commit_Internal};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSXAResourceImpl.java", "weblogic.jdbc.wrapper.JTSXAResourceImpl", "isSameRM", "(Ljavax/transaction/xa/XAResource;)Z", 106, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Commit_Internal, _WLDF$INST_FLD_JDBC_Before_Rollback_Internal, _WLDF$INST_FLD_JDBC_Before_Start_Internal, _WLDF$INST_FLD_JDBC_After_Rollback_Internal, _WLDF$INST_FLD_JDBC_After_Start_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Is_SameRM_Before_High, _WLDF$INST_FLD_JDBC_Before_Commit_Internal};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSXAResourceImpl.java", "weblogic.jdbc.wrapper.JTSXAResourceImpl", "start", "(Ljavax/transaction/xa/Xid;I)V", 110, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Before_Start_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_Start_Before_High, _WLDF$INST_FLD_JDBC_After_Start_Internal};
      _WLDF$INST_JPFLD_5 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JTSXAResourceImpl.java", "weblogic.jdbc.wrapper.JTSXAResourceImpl", "end", "(Ljavax/transaction/xa/Xid;I)V", 112, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_5 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_After_Commit_Internal, _WLDF$INST_FLD_JDBC_Diagnostic_Transaction_End_Before_High, _WLDF$INST_FLD_JDBC_Before_Commit_Internal};
   }
}
