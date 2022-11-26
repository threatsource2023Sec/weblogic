package weblogic.jdbc.common.internal;

import java.util.Date;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ONSDaemonRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.utils.StackTraceUtils;

public class ONSDaemonRuntimeImpl extends RuntimeMBeanDelegate implements ONSDaemonRuntimeMBean {
   private String host;
   private String wallet;
   private String password;
   private int port;
   private String status;

   public ONSDaemonRuntimeImpl(String host, int port, String wallet, String password, String beanName, RuntimeMBean parent) throws ManagementException {
      super(beanName, parent, true);
      this.host = host;
      this.port = port;
      this.wallet = wallet;
      this.password = password;
      this.status = "Unknown";
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public String getStatus() {
      try {
         this.ping();
      } catch (RuntimeException var2) {
      }

      return this.status;
   }

   public void ping() {
      try {
         if (this.wallet != null && this.password != null) {
            DataSourceUtil.onsPing(this.host, this.port, this.wallet, this.password.toCharArray());
         } else {
            DataSourceUtil.onsPing(this.host, this.port, (String)null, (char[])null);
         }

         this.status = "Running";
      } catch (Exception var2) {
         this.status = "Failed ";
         if (var2.getMessage() != null) {
            this.status = this.status + var2.getMessage();
         }

         this.status = this.status + " - checked at " + new Date();
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            JdbcDebug.JDBCRAC.debug("ONS ping failed for " + this.host + ":" + this.port + " with: " + StackTraceUtils.throwable2StackTrace(var2));
         }

         throw new RuntimeException("ONS ping failed for " + this.host + ":" + this.port, var2);
      }
   }
}
