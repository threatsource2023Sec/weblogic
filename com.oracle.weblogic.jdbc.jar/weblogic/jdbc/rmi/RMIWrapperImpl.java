package weblogic.jdbc.rmi;

import java.sql.SQLException;
import weblogic.common.resourcepool.ResourceUnusableException;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.jdbc.common.internal.ConnectionEnv;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.extensions.PoolUnavailableSQLException;
import weblogic.jdbc.wrapper.JDBCWrapperImpl;
import weblogic.utils.StackTraceUtils;

public class RMIWrapperImpl extends JDBCWrapperImpl {
   static final long serialVersionUID = -6070228019593000541L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.rmi.RMIWrapperImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
         String s = methodName + "(";
         if (params != null && params.length > 0) {
            for(int i = 0; i < params.length - 1; ++i) {
               s = s + params[i] + ", ";
            }

            s = s + params[params.length - 1] + ")";
         } else {
            s = s + ")";
         }

         this.trace(s);
      }

   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
         String s = methodName + " returns";
         if (ret != null) {
            s = s + " " + ret;
         }

         this.trace(s);
      }

      return ret;
   }

   public void trace(String s) {
      JdbcDebug.JDBCRMI.debug("[" + this + "] " + s);
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws SQLException {
      if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
         String s = methodName + "(";
         if (params != null && params.length > 0) {
            for(int i = 0; i < params.length - 1; ++i) {
               s = s + params[i] + ", ";
            }

            s = s + params[params.length - 1] + ") throws ";
         } else {
            s = s + "unknown) throws: ";
         }

         s = s + StackTraceUtils.throwable2StackTrace(t);
         this.trace(s);
      }

      if (t instanceof ResourceUnusableException) {
         throw new PoolUnavailableSQLException(t.getMessage());
      } else if (t instanceof SQLException) {
         throw (SQLException)t;
      } else if (t instanceof SecurityException) {
         throw (SecurityException)t;
      } else {
         SQLException sqlException = new SQLException(methodName + ", Exception = " + t.getMessage());
         sqlException.initCause(t);
         throw sqlException;
      }
   }

   public ConnectionEnv getConnectionEnv() {
      return null;
   }

   public Object unwrap(Class iface) throws SQLException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
      }

      Object var10000;
      try {
         if (!iface.isInstance(this)) {
            throw new SQLException(this + " is not an instance of " + iface);
         }

         var10000 = iface.cast(this);
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            var2.ret = null;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         throw var4;
      }

      if (var2 != null) {
         var2.ret = var10000;
         InstrumentationSupport.createDynamicJoinPoint(var2);
         InstrumentationSupport.process(var2);
      }

      return var10000;
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return iface.isInstance(this);
   }

   static {
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "RMIWrapperImpl.java", "weblogic.jdbc.rmi.RMIWrapperImpl", "unwrap", "(Ljava/lang/Class;)Ljava/lang/Object;", 112, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium};
   }
}
