package weblogic.jdbc.wrapper;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.CommonDataSource;
import javax.sql.DataSource;
import weblogic.jndi.CrossPartitionAware;
import weblogic.utils.wrapper.WrapperFactory;

public class AbstractDataSource extends AbstractWrapperImpl implements DataSource, Serializable, CrossPartitionAware {
   private static final long serialVersionUID = 2980692681797600612L;
   private transient WrapperFactory wrapperFactory = AbstractWrapperFactory.getInstance();
   private boolean crossPartitionEnabled;

   public PrintWriter getLogWriter() throws SQLException {
      String methodName = "getLogWriter";
      Object[] params = new Object[0];
      PrintWriter ret = null;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((CommonDataSource)this.getDataSource()).getLogWriter();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public void setLogWriter(PrintWriter out) throws SQLException {
      String methodName = "setLogWriter";
      Object[] params = new Object[]{out};

      try {
         this.preInvocationHandler(methodName, params);
         ((CommonDataSource)this.getDataSource()).setLogWriter(out);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public void setLoginTimeout(int seconds) throws SQLException {
      String methodName = "setLoginTimeout";
      Object[] params = new Object[]{seconds};

      try {
         this.preInvocationHandler(methodName, params);
         ((CommonDataSource)this.getDataSource()).setLoginTimeout(seconds);
         this.postInvocationHandler(methodName, params, (Object)null);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

   }

   public int getLoginTimeout() throws SQLException {
      String methodName = "getLoginTimeout";
      Object[] params = new Object[0];
      int ret = 0;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((CommonDataSource)this.getDataSource()).getLoginTimeout();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      String methodName = "getParentLogger";
      Object[] params = new Object[0];
      Logger ret = null;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((CommonDataSource)this.getDataSource()).getParentLogger();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         Exception e = var7;

         try {
            this.invocationExceptionHandler(methodName, params, e);
         } catch (SQLException var6) {
            if (var6 instanceof SQLFeatureNotSupportedException) {
               throw (SQLFeatureNotSupportedException)var6;
            }

            throw new RuntimeException(var6);
         }
      }

      return ret;
   }

   public Object unwrap(Class iface) throws SQLException {
      String methodName = "unwrap";
      Object[] params = new Object[]{iface};
      Object ret = null;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((DataSource)this.getDataSource()).unwrap(iface);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      String methodName = "isWrapperFor";
      Object[] params = new Object[]{iface};
      boolean ret = false;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((DataSource)this.getDataSource()).isWrapperFor(iface);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public java.sql.Connection getConnection() throws SQLException {
      String methodName = "getConnection";
      Object[] params = new Object[0];
      java.sql.Connection ret = null;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((DataSource)this.getDataSource()).getConnection();
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }

   public java.sql.Connection getConnection(String username, String password) throws SQLException {
      String methodName = "getConnection";
      Object[] params = new Object[]{username, password};
      java.sql.Connection ret = null;

      try {
         this.preInvocationHandler(methodName, params);
         ret = ((DataSource)this.getDataSource()).getConnection(username, password);
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var7) {
         this.invocationExceptionHandler(methodName, params, var7);
      }

      return ret;
   }

   public Object getDataSource() {
      return this.vendorObj;
   }

   public void setCrossPartitionEnabled(boolean enabled) {
      this.crossPartitionEnabled = enabled;
   }

   public boolean isAccessAllowed() {
      return this.crossPartitionEnabled;
   }
}
