package org.apache.openjpa.jdbc.schema;

import java.security.AccessController;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.jdbc.ConfiguringConnectionDecorator;
import org.apache.openjpa.lib.jdbc.ConnectionDecorator;
import org.apache.openjpa.lib.jdbc.DecoratingDataSource;
import org.apache.openjpa.lib.jdbc.DelegatingDataSource;
import org.apache.openjpa.lib.jdbc.JDBCEventConnectionDecorator;
import org.apache.openjpa.lib.jdbc.JDBCListener;
import org.apache.openjpa.lib.jdbc.LoggingConnectionDecorator;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UserException;

public class DataSourceFactory {
   private static final Localizer _loc = Localizer.forPackage(DataSourceFactory.class);

   public static DataSource newDataSource(JDBCConfiguration conf, boolean factory2) {
      String driver = factory2 ? conf.getConnection2DriverName() : conf.getConnectionDriverName();
      if (StringUtils.isEmpty(driver)) {
         throw (new UserException(_loc.get("no-driver", (Object)driver))).setFatal(true);
      } else {
         ClassLoader loader = conf.getClassResolverInstance().getClassLoader(DataSourceFactory.class, (ClassLoader)null);
         String props = factory2 ? conf.getConnection2Properties() : conf.getConnectionProperties();

         try {
            Class driverClass;
            try {
               driverClass = Class.forName(driver, true, loader);
            } catch (ClassNotFoundException var7) {
               driverClass = Class.forName(driver);
            }

            if (Driver.class.isAssignableFrom(driverClass)) {
               DriverDataSource ds = conf.newDriverDataSourceInstance();
               ds.setClassLoader(loader);
               ds.setConnectionDriverName(driver);
               ds.setConnectionProperties(Configurations.parseProperties(props));
               if (!factory2) {
                  ds.setConnectionFactoryProperties(Configurations.parseProperties(conf.getConnectionFactoryProperties()));
                  ds.setConnectionURL(conf.getConnectionURL());
                  ds.setConnectionUserName(conf.getConnectionUserName());
                  ds.setConnectionPassword(conf.getConnectionPassword());
               } else {
                  ds.setConnectionFactoryProperties(Configurations.parseProperties(conf.getConnectionFactory2Properties()));
                  ds.setConnectionURL(conf.getConnection2URL());
                  ds.setConnectionUserName(conf.getConnection2UserName());
                  ds.setConnectionPassword(conf.getConnection2Password());
               }

               return ds;
            }

            if (DataSource.class.isAssignableFrom(driverClass)) {
               return (DataSource)Configurations.newInstance(driver, conf, props, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(DataSource.class)));
            }
         } catch (OpenJPAException var8) {
            throw var8;
         } catch (Exception var9) {
            throw (new StoreException(var9)).setFatal(true);
         }

         throw (new UserException(_loc.get("bad-driver", (Object)driver))).setFatal(true);
      }
   }

   public static DecoratingDataSource decorateDataSource(DataSource ds, JDBCConfiguration conf, boolean factory2) {
      Options opts = Configurations.parseProperties(factory2 ? conf.getConnectionFactory2Properties() : conf.getConnectionFactoryProperties());
      Log jdbcLog = conf.getLog("openjpa.jdbc.JDBC");
      Log sqlLog = conf.getLog("openjpa.jdbc.SQL");
      DecoratingDataSource dds = new DecoratingDataSource(ds);

      try {
         List decorators = new ArrayList();
         decorators.addAll(Arrays.asList(conf.getConnectionDecoratorInstances()));
         JDBCEventConnectionDecorator ecd = new JDBCEventConnectionDecorator();
         Configurations.configureInstance(ecd, conf, (Properties)opts);
         JDBCListener[] listeners = conf.getJDBCListenerInstances();

         for(int i = 0; i < listeners.length; ++i) {
            ecd.addListener(listeners[i]);
         }

         decorators.add(ecd);
         if (ds instanceof DriverDataSource) {
            List decs = ((DriverDataSource)ds).createConnectionDecorators();
            if (decs != null) {
               decorators.addAll(decs);
            }
         }

         LoggingConnectionDecorator lcd = new LoggingConnectionDecorator();
         Configurations.configureInstance(lcd, conf, (Properties)opts);
         lcd.getLogs().setJDBCLog(jdbcLog);
         lcd.getLogs().setSQLLog(sqlLog);
         decorators.add(lcd);
         dds.addDecorators(decorators);
         return dds;
      } catch (OpenJPAException var11) {
         throw var11;
      } catch (Exception var12) {
         throw (new StoreException(var12)).setFatal(true);
      }
   }

   public static DecoratingDataSource installDBDictionary(DBDictionary dict, DecoratingDataSource ds, JDBCConfiguration conf, boolean factory2) {
      DataSource inner = ds.getInnermostDelegate();
      if (inner instanceof DriverDataSource) {
         ((DriverDataSource)inner).initDBDictionary(dict);
      }

      Connection conn = null;

      DecoratingDataSource var9;
      try {
         Iterator itr = ds.getDecorators().iterator();

         while(itr.hasNext()) {
            ConnectionDecorator cd = (ConnectionDecorator)itr.next();
            if (cd instanceof LoggingConnectionDecorator) {
               ((LoggingConnectionDecorator)cd).setWarningHandler(dict);
            }
         }

         ConfiguringConnectionDecorator ccd = new ConfiguringConnectionDecorator();
         ccd.setTransactionIsolation(conf.getTransactionIsolationConstant());
         if (factory2 || !conf.isConnectionFactoryModeManaged()) {
            if (!dict.supportsMultipleNontransactionalResultSets) {
               ccd.setAutoCommit(Boolean.FALSE);
            } else {
               ccd.setAutoCommit(Boolean.TRUE);
            }
         }

         Options opts = Configurations.parseProperties(factory2 ? conf.getConnectionFactory2Properties() : conf.getConnectionFactoryProperties());
         Configurations.configureInstance(ccd, conf, (Properties)opts);
         ds.addDecorator(ccd);
         ds.addDecorator(dict);
         if (!factory2) {
            conn = ds.getConnection(conf.getConnectionUserName(), conf.getConnectionPassword());
         } else {
            conn = ds.getConnection(conf.getConnection2UserName(), conf.getConnection2Password());
         }

         var9 = ds;
      } catch (Exception var18) {
         throw (new StoreException(var18)).setFatal(true);
      } finally {
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var17) {
            }
         }

      }

      return var9;
   }

   public static DataSource defaultsDataSource(DataSource ds, String user, String pass) {
      if (user == null && pass == null) {
         return ds;
      } else {
         return (DataSource)("".equals(user) && "".equals(pass) ? ds : new DefaultsDataSource(ds, user, pass));
      }
   }

   public static void closeDataSource(DataSource ds) {
      if (ds instanceof DelegatingDataSource) {
         ds = ((DelegatingDataSource)ds).getInnermostDelegate();
      }

      ImplHelper.close(ds);
   }

   private static class DefaultsDataSource extends DelegatingDataSource {
      private final String _user;
      private final String _pass;

      public DefaultsDataSource(DataSource ds, String user, String pass) {
         super(ds);
         this._user = user;
         this._pass = pass;
      }

      public Connection getConnection() throws SQLException {
         return super.getConnection(this._user, this._pass);
      }

      public Connection getConnection(String user, String pass) throws SQLException {
         return super.getConnection(user, pass);
      }
   }
}
