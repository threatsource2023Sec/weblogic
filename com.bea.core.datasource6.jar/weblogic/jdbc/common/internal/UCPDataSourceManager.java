package weblogic.jdbc.common.internal;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.wrapper.AbstractDataSource;
import weblogic.jdbc.wrapper.UCPDataSource;
import weblogic.jdbc.wrapper.UCPXADataSource;

public class UCPDataSourceManager extends AbstractDataSourceManager {
   private static final UCPDataSourceManager INSTANCE = new UCPDataSourceManager();
   private static String XADRIVER = "oracle.ucp.jdbc.PoolXADataSourceImpl";
   private static String NONXADRIVER = "oracle.ucp.jdbc.PoolDataSourceImpl";
   private static String XAFACTORY = "oracle.jdbc.xa.client.OracleXADataSource";
   private static String NONXAFACTORY = "oracle.jdbc.pool.OracleDataSource";
   private static String REPLAYFACTORY = "oracle.jdbc.replay.OracleDataSourceImpl";
   private static String XAREPLAYFACTORY = "oracle.jdbc.replay.OracleXADataSourceImpl";

   public static UCPDataSourceManager getInstance() {
      return INSTANCE;
   }

   public DataSource instantiate(JDBCDataSourceBean dsBean, String refKey) throws ResourceException, SQLException {
      String driver = dsBean.getJDBCDriverParams().getDriverName();
      if (driver == null) {
         driver = NONXADRIVER;
      }

      DataSource ds = this.loadDriver(driver);
      JDBCDriverParamsBean driverParams = dsBean.getJDBCDriverParams();
      String url = driverParams.getUrl();
      if (url != null) {
         if (url.equals("")) {
            url = null;
         } else {
            DataSourceUtil.initProp(dsBean.getName(), ds, "url", url);
         }
      }

      String password = driverParams.getPassword();
      if (password != null) {
         if (password.equals("")) {
            password = null;
         } else {
            DataSourceUtil.initProp(dsBean.getName(), ds, "password", password);
         }
      }

      JDBCPropertyBean[] propertyBeans = driverParams.getProperties().getProperties();
      String connectionFactoryClassname = null;
      String dataSourceFromConfiguration = null;
      String name;
      if (propertyBeans != null) {
         for(int i = 0; i < propertyBeans.length; ++i) {
            name = propertyBeans[i].getName();
            if ((url == null || !"url".equals(name)) && (password == null || !"password".equals(name))) {
               String value = JDBCUtil.getPropValue(dsBean, propertyBeans[i], dsBean.getName());
               if (value != null) {
                  if (name.equalsIgnoreCase("XmlConfigFile")) {
                     try {
                        System.setProperty("oracle.ucp.jdbc.xmlConfigFile", value);
                     } catch (Exception var19) {
                     }
                  } else if (name.equalsIgnoreCase("dataSourceFromConfiguration")) {
                     dataSourceFromConfiguration = value;
                  } else {
                     if (name.equalsIgnoreCase("ConnectionFactoryClassname")) {
                        connectionFactoryClassname = value;
                     }

                     if (!name.equalsIgnoreCase("ConnectionProperty") && !name.equalsIgnoreCase("ConnectionFactoryProperty") && !name.equalsIgnoreCase("ConnectionProperties") && !name.equalsIgnoreCase("ConnectionFactoryProperties")) {
                        DataSourceUtil.initProp(dsBean.getName(), ds, name, value);
                     } else {
                        Properties properties = this.parsePropertiesList(value);
                        if (name.toLowerCase().startsWith("connectionpropert")) {
                           DataSourceUtil.initProp(dsBean.getName(), ds, "ConnectionProperties", properties);
                        } else {
                           DataSourceUtil.initProp(dsBean.getName(), ds, "ConnectionFactoryProperties", properties);
                        }
                     }
                  }
               }
            }
         }
      }

      Class c;
      if (dataSourceFromConfiguration != null) {
         name = null;

         try {
            c = Class.forName("oracle.ucp.jdbc.PoolDataSourceFactory");
         } catch (ClassNotFoundException var18) {
            throw new ResourceException(var18);
         }

         Method m2;
         try {
            m2 = c.getMethod("getPoolDataSource", String.class);
         } catch (Exception var17) {
            throw new ResourceException("DataSourceFromConfiguration only supported with 12.2+ UCP");
         }

         try {
            ds = (DataSource)m2.invoke(c, (Object[])(new String[]{dataSourceFromConfiguration}));
         } catch (Exception var16) {
            throw new ResourceException(var16);
         }
      } else if (connectionFactoryClassname == null) {
         if (driver.equals(XADRIVER)) {
            DataSourceUtil.initProp(dsBean.getName(), ds, "connectionFactoryClassName", XAFACTORY);
         } else {
            DataSourceUtil.initProp(dsBean.getName(), ds, "connectionFactoryClassName", NONXAFACTORY);
         }
      } else if (driver.equals(XADRIVER) && connectionFactoryClassname.equals(NONXAFACTORY) || driver.equals(XADRIVER) && connectionFactoryClassname.equals(REPLAYFACTORY) || driver.equals(NONXADRIVER) && connectionFactoryClassname.equals(XAREPLAYFACTORY) || driver.equals(NONXADRIVER) && connectionFactoryClassname.equals(XAFACTORY)) {
         throw new ResourceException("Can't mix XA and non-XA classes");
      }

      c = null;

      DataSource adsWrapped;
      try {
         if (ds instanceof XADataSource) {
            adsWrapped = (DataSource)this.wrapperFactory.createWrapper(UCPXADataSource.class.getCanonicalName(), ds, false, (ClassLoader)null);
            ((UCPXADataSource)adsWrapped).setReferenceKey(refKey);
         } else {
            adsWrapped = (DataSource)this.wrapperFactory.createWrapper(UCPDataSource.class.getCanonicalName(), ds, false, (ClassLoader)null);
            ((UCPDataSource)adsWrapped).setReferenceKey(refKey);
         }
      } catch (Exception var15) {
         throw new ResourceException(var15);
      }

      if (JDBCUtil.isCrossPartitionEnabled(dsBean)) {
         ((AbstractDataSource)adsWrapped).setCrossPartitionEnabled(true);
      }

      return adsWrapped;
   }
}
