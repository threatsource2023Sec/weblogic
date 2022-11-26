package weblogic.jdbc.extensions;

import java.sql.SQLException;

public interface DriverInterceptor {
   String INTERFACE_NAME = "weblogic.jdbc.extensions.DriverInterceptor";

   Object preInvokeCallback(Object var1, String var2, Object[] var3) throws SQLException;

   void postInvokeCallback(Object var1, String var2, Object[] var3, Object var4) throws SQLException;

   void postInvokeExceptionCallback(Object var1, String var2, Object[] var3, Throwable var4) throws SQLException;
}
