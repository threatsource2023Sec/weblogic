package weblogic.jdbc.common.internal;

import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;

@Service
public final class GenericConnectionPool extends ConnectionPool implements FastThreadLocalMarker {
   public GenericConnectionPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, ClassLoader classLoader) {
      super(dsBean, appName, moduleName, compName, classLoader);
   }

   public GenericConnectionPool(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) {
      super(dsBean, appName, moduleName, compName);
   }

   public GenericConnectionPool(String appName, String moduleName, String compName, ConnectionPoolConfig config, ClassLoader classLoader) {
      super(appName, moduleName, compName, config, classLoader);
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}
