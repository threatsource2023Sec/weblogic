package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import java.sql.Wrapper;
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
import weblogic.jdbc.extensions.DriverInterceptor;
import weblogic.jdbc.extensions.PoolUnavailableSQLException;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.wrapper.WrapperImpl;

public abstract class JDBCWrapperImpl extends WrapperImpl {
   protected boolean JDBCSQLDebug;
   private static int globalHashcode;
   private int localHashcode;
   static final long serialVersionUID = 417396593662520536L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.jdbc.wrapper.JDBCWrapperImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public JDBCWrapperImpl() {
      this.JDBCSQLDebug = JdbcDebug.JDBCSQL.isDebugEnabled();
      this.localHashcode = globalHashcode++;
   }

   public Object getVendorObj() {
      Object ret;
      for(ret = this.vendorObj; ret != null && ret instanceof JDBCWrapperImpl; ret = ((JDBCWrapperImpl)ret).getVendorObj()) {
      }

      return ret;
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      if (this.JDBCSQLDebug) {
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

      ConnectionEnv cc = this.getConnectionEnv();
      if (cc != null) {
         DriverInterceptor cb = cc.getConnectionPool().getDriverInterceptor();
         if (cb != null) {
            cb.preInvokeCallback(this.vendorObj, methodName, params);
         }

         cc.setInUse();
      }

   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      ConnectionEnv cc = this.getConnectionEnv();
      if (cc != null) {
         cc.setNotInUse();
         if (cc.getConnectionPool() != null) {
            DriverInterceptor cb = cc.getConnectionPool().getDriverInterceptor();
            if (cb != null) {
               cb.postInvokeCallback(this.vendorObj, methodName, params, ret);
            }
         }
      }

      if (this.JDBCSQLDebug) {
         String s = methodName + " returns";
         if (ret != null) {
            s = s + " " + ret;
         }

         this.trace(s);
      }

      return ret;
   }

   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws SQLException {
      ConnectionEnv cc = this.getConnectionEnv();
      if (cc != null) {
         cc.resetLastSuccessfulConnectionUse();
         cc.setNotInUse();
         DriverInterceptor cb = cc.getConnectionPool().getDriverInterceptor();
         if (cb != null) {
            cb.postInvokeExceptionCallback(this.vendorObj, methodName, params, t);
         }

         this.removeConnFromPoolIfFatalError(t, cc);
      }

      if (this.JDBCSQLDebug) {
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

   protected void removeConnFromPoolIfFatalError(Throwable t, ConnectionEnv ce) {
      if (ce != null) {
         int[] codes = ce.getFatalErrorCodes();
         if (codes != null && t instanceof SQLException) {
            int code = ((SQLException)t).getErrorCode();

            for(int i = 0; i < codes.length; ++i) {
               if (codes[i] == code) {
                  ce.setInfected(true);
                  ce.setRefreshNeeded(true);
                  ce.disable();
                  break;
               }
            }
         }

      }
   }

   public void trace(String s) {
      JdbcDebug.JDBCSQL.debug("[" + this + "] " + s);
   }

   public void traceConn(String s) {
      JdbcDebug.JDBCCONN.debug("[" + this + "] " + s);
   }

   public int hashCode() {
      return this.localHashcode;
   }

   public abstract ConnectionEnv getConnectionEnv();

   public Object unwrap(Class iface) throws SQLException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
      }

      Object var10000;
      label123: {
         label124: {
            try {
               if (!iface.isInterface() && !iface.getName().startsWith("oracle.sql")) {
                  throw new SQLException("not an interface");
               }

               if (iface.isInstance(this)) {
                  var10000 = iface.cast(this);
                  break label123;
               }

               Object vendorObject = this.getVendorObj();
               if (vendorObject == null) {
                  throw new SQLException(this + " is not an instance of " + iface);
               }

               this.infectConnection();
               if (iface.isInstance(vendorObject)) {
                  var10000 = iface.cast(vendorObject);
                  break label124;
               }

               var10000 = ((Wrapper)vendorObject).unwrap(iface);
            } catch (Throwable var5) {
               if (var3 != null) {
                  var3.th = var5;
                  var3.ret = null;
                  InstrumentationSupport.createDynamicJoinPoint(var3);
                  InstrumentationSupport.process(var3);
               }

               throw var5;
            }

            if (var3 != null) {
               var3.ret = var10000;
               InstrumentationSupport.createDynamicJoinPoint(var3);
               InstrumentationSupport.process(var3);
            }

            return var10000;
         }

         if (var3 != null) {
            var3.ret = var10000;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         return var10000;
      }

      if (var3 != null) {
         var3.ret = var10000;
         InstrumentationSupport.createDynamicJoinPoint(var3);
         InstrumentationSupport.process(var3);
      }

      return var10000;
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      if (iface.isInstance(this)) {
         return true;
      } else {
         Object vendorObject = this.getVendorObj();
         if (vendorObject == null) {
            return false;
         } else {
            return iface.isInstance(vendorObject) ? true : ((Wrapper)vendorObject).isWrapperFor(iface);
         }
      }
   }

   private void infectConnection() throws SQLException {
      ConnectionEnv cc = this.getConnectionEnv();
      if (cc == null) {
         throw new SQLException("This feature is not supported in this scenario");
      } else {
         cc.infect();
      }
   }

   static {
      _WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JDBCWrapperImpl.java", "weblogic.jdbc.wrapper.JDBCWrapperImpl", "unwrap", "(Ljava/lang/Class;)Ljava/lang/Object;", 188, "", "", "", InstrumentationSupport.makeMap(new String[]{"JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JDBCConnectionRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_JDBC_Diagnostic_Connection_Get_Vendor_Connection_After_Medium};
      globalHashcode = 0;
   }
}
