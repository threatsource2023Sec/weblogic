package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import javax.sql.XADataSource;
import weblogic.utils.wrapper.WrapperFactory;

public class AbstractXADataSource extends AbstractDataSource implements XADataSource {
   private static final long serialVersionUID = -5863998557256099616L;
   private transient WrapperFactory wrapperFactory = AbstractWrapperFactory.getInstance();

   public java.sql.Connection getConnection() throws SQLException {
      String methodName = "getConnection";
      Object[] params = new Object[0];
      java.sql.Connection conn = null;

      try {
         this.preInvocationHandler(methodName, params);
         javax.sql.XAConnection xaconn = this.getXAConnection();
         conn = xaconn.getConnection();
         this.postInvocationHandler(methodName, params, conn);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return conn;
   }

   public java.sql.Connection getConnection(String username, String password) throws SQLException {
      String methodName = "getConnection";
      Object[] params = new Object[]{username, password};
      java.sql.Connection conn = null;

      try {
         this.preInvocationHandler(methodName, params);
         javax.sql.XAConnection xaconn = this.getXAConnection(username, password);
         conn = xaconn.getConnection();
         this.postInvocationHandler(methodName, params, conn);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return conn;
   }

   public javax.sql.XAConnection getXAConnection() throws SQLException {
      String methodName = "getXAConnection";
      Object[] params = new Object[0];
      javax.sql.XAConnection ret = null;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((XADataSource)this.getDataSource()).getXAConnection();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public javax.sql.XAConnection getXAConnection(String user, String password) throws SQLException {
      String methodName = "getXAConnection";
      Object[] params = new Object[]{user, password};
      javax.sql.XAConnection ret = null;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((XADataSource)this.getDataSource()).getXAConnection(user, password);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Object getDataSource() {
      return this.vendorObj;
   }
}
