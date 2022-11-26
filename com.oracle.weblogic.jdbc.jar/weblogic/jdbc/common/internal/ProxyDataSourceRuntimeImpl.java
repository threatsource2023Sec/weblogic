package weblogic.jdbc.common.internal;

import java.sql.Connection;
import javax.sql.DataSource;
import weblogic.descriptor.DescriptorBean;
import weblogic.jdbc.wrapper.ProxyDataSource;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCProxyDataSourceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.utils.StackTraceUtils;

public class ProxyDataSourceRuntimeImpl extends AbstractDataSourceRuntimeImpl implements JDBCProxyDataSourceRuntimeMBean {
   private DataSource ds;

   public ProxyDataSourceRuntimeImpl(DataSource ds, String beanName, RuntimeMBean parent, RuntimeMBean restParent, DescriptorBean dsBean) throws ManagementException {
      super(ds, beanName, parent, restParent, dsBean);
      this.ds = ds;
   }

   public String testPool() {
      if (!(this.ds instanceof ProxyDataSource)) {
         return "Test failed: not a PROXY data source";
      } else {
         ProxyDataSource pds = (ProxyDataSource)this.ds;
         DataSource realds = null;

         try {
            realds = (DataSource)pds.getDataSource();
         } catch (Throwable var16) {
            return "Test failed: failed to get datasource from switching callback\n" + StackTraceUtils.throwable2StackTrace(var16);
         }

         if (realds == null) {
            return "Test failed: proxy switching class failed to find a data source";
         } else {
            Connection conn = null;

            String var5;
            try {
               conn = realds.getConnection();
               Object var4 = null;
               return (String)var4;
            } catch (Throwable var17) {
               var5 = "Test failed: failed to get a connection\n" + StackTraceUtils.throwable2StackTrace(var17);
            } finally {
               try {
                  if (conn != null) {
                     conn.close();
                  }
               } catch (Exception var15) {
               }

            }

            return var5;
         }
      }
   }
}
