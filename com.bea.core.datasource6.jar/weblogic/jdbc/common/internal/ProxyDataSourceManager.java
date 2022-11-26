package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import javax.sql.DataSource;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.wrapper.AbstractDataSource;
import weblogic.jdbc.wrapper.ProxyDataSource;

public class ProxyDataSourceManager extends AbstractDataSourceManager {
   private static final ProxyDataSourceManager INSTANCE = new ProxyDataSourceManager();

   public static ProxyDataSourceManager getInstance() {
      return INSTANCE;
   }

   public DataSource instantiate(JDBCDataSourceBean dsBean, String refKey) throws ResourceException, SQLException {
      JDBCDriverParamsBean driverParams = dsBean.getJDBCDriverParams();
      JDBCDataSourceParamsBean datasourceParams = dsBean.getJDBCDataSourceParams();
      JDBCPropertyBean[] propertyBeans = driverParams.getProperties().getProperties();
      DataSource adsWrapped = null;
      String callbackClass = "weblogic.jdbc.common.internal.DataSourceSwitchingCallbackImpl";
      String switchingProps = null;

      try {
         DataSource ds = new ProxyDataSource();
         adsWrapped = (DataSource)this.wrapperFactory.createWrapper(ProxyDataSource.class.getCanonicalName(), ds, false, (ClassLoader)null);
         ((ProxyDataSource)adsWrapped).setReferenceKey(refKey);
         String[] names = dsBean.getJDBCDataSourceParams().getJNDINames();
         if (names != null && names.length != 0) {
            StringBuffer sbuf = new StringBuffer();

            int i;
            for(i = 0; i < names.length; ++i) {
               if (i > 0) {
                  sbuf.append(",");
               }

               if (names[i].indexOf(",") != -1) {
                  throw new ResourceException("Comma not allowed in JNDI name for Proxy data source");
               }

               sbuf.append(names[i]);
            }

            ((ProxyDataSource)adsWrapped).setName(sbuf.toString());
            if (propertyBeans != null) {
               for(i = 0; i < propertyBeans.length; ++i) {
                  String name = propertyBeans[i].getName();
                  String value = JDBCUtil.getPropValue(dsBean, propertyBeans[i], dsBean.getName());
                  if (value != null) {
                     if (name.equalsIgnoreCase("SwitchingProperties")) {
                        switchingProps = value;
                     } else if (name.equalsIgnoreCase("DBSwitchingCallbackClassName")) {
                        if (value != null && !value.equals("")) {
                           callbackClass = value;
                        }
                     } else {
                        DataSourceUtil.initProp(dsBean.getName(), ds, name, value);
                     }
                  }
               }
            }

            String val = datasourceParams.getProxySwitchingProperties();
            if (val != null) {
               switchingProps = val;
            }

            ((ProxyDataSource)adsWrapped).setProxySwitchingProperties(switchingProps);
            val = datasourceParams.getProxySwitchingCallback();
            if (val != null && !val.equals("")) {
               callbackClass = val;
            }

            ((ProxyDataSource)adsWrapped).setProxySwitchingCallback(callbackClass);
            if (JDBCUtil.isCrossPartitionEnabled(dsBean)) {
               ((AbstractDataSource)adsWrapped).setCrossPartitionEnabled(true);
            }

            return adsWrapped;
         } else {
            throw new ResourceException("JNDI name required for Proxy data source");
         }
      } catch (Exception var15) {
         throw new ResourceException(var15);
      }
   }
}
