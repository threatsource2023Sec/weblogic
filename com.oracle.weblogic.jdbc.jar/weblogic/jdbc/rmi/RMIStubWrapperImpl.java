package weblogic.jdbc.rmi;

import java.rmi.Remote;
import java.sql.SQLException;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.jdbc.common.internal.JdbcDebug;

public class RMIStubWrapperImpl extends RMIWrapperImpl {
   public Object invocationExceptionHandler(String methodName, Object[] params, Throwable t) throws SQLException {
      if (!(this.vendorObj instanceof Remote)) {
         throw new AssertionError("object not Remote : " + this.vendorObj);
      } else {
         try {
            JDBCHelper.getHelper().removeRMIContext(this.vendorObj);
         } catch (Exception var5) {
            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("error removing RMI context", var5);
            }

            throw new SQLException(var5);
         }

         return super.invocationExceptionHandler(methodName, params, t);
      }
   }

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (!(this.vendorObj instanceof Remote)) {
         throw new AssertionError("object not Remote : " + this.vendorObj);
      } else {
         try {
            JDBCHelper.getHelper().removeRMIContext(this.vendorObj);
         } catch (Exception var5) {
            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("error removing RMI context", var5);
            }

            throw var5;
         }

         return super.postInvocationHandler(methodName, params, ret);
      }
   }

   public void preInvocationHandler(String methodName, Object[] params) throws Exception {
      super.preInvocationHandler(methodName, params);
      if (!(this.vendorObj instanceof Remote)) {
         throw new AssertionError("object not Remote : " + this.vendorObj);
      } else {
         try {
            JDBCHelper.getHelper().addRMIContext(this.vendorObj);
         } catch (Exception var4) {
            if (JdbcDebug.JDBCRMI.isDebugEnabled()) {
               JdbcDebug.JDBCRMI.debug("error adding RMI context", var4);
            }

            throw var4;
         }
      }
   }
}
