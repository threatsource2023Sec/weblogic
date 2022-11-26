package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import javax.sql.DataSource;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ObjectLifeCycle;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.wrapper.AbstractWrapperFactory;
import weblogic.utils.wrapper.WrapperFactory;

public abstract class AbstractDataSourceManager implements ObjectLifeCycle, ObjectFactory {
   private static boolean DEBUG = false;
   private static Map adsMap = new HashMap();
   protected WrapperFactory wrapperFactory = AbstractWrapperFactory.getInstance();

   public Object create(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) throws ResourceException, SQLException {
      String decoratedName = JDBCUtil.getDecoratedName(dsBean.getName(), appName, moduleName, compName);
      if (DEBUG) {
         this.debug("create() dsBean=" + dsBean + ", decoratedName=" + decoratedName);
      }

      if (adsMap.containsKey(decoratedName)) {
         throw new ResourceException(dsBean.getDatasourceType() + " DataSource " + dsBean.getName() + " already exists");
      } else {
         DataSource ads = this.instantiate(dsBean, decoratedName);
         adsMap.put(decoratedName, ads);
         return ads;
      }
   }

   public Object get(String key) {
      return adsMap.get(key);
   }

   public Object remove(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) throws ResourceException {
      String decoratedName = JDBCUtil.getDecoratedName(dsBean.getName(), appName, moduleName, compName);
      if (DEBUG) {
         this.debug("remove() dsBean=" + dsBean + ", decoratedName=" + decoratedName);
      }

      DataSource ds = (DataSource)adsMap.remove(decoratedName);
      return ds;
   }

   public Object bind(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName, Context appCtx) throws ResourceException, NamingException {
      String decoratedName = JDBCUtil.getDecoratedName(dsBean.getName(), appName, moduleName, compName);
      DataSource ads = (DataSource)adsMap.get(decoratedName);
      if (DEBUG) {
         this.debug("bind() decoratedName=" + decoratedName + ", datasource=" + ads + ", JNDINames=" + dsBean.getJDBCDataSourceParams().getJNDINames()[0]);
      }

      Context isolatedContext = JDBCUtil.getIsolatedContext();
      JDBCUtil.bindAll(isolatedContext, dsBean.getJDBCDataSourceParams().getJNDINames(), ads);
      return ads;
   }

   public void unbind(JDBCDataSourceBean dsBean, String appName, String moduleName, String compName) throws ResourceException, NamingException {
      String decoratedName = JDBCUtil.getDecoratedName(dsBean.getName(), appName, moduleName, compName);
      if (DEBUG) {
         this.debug("unbind() decoratedName=" + decoratedName);
      }

      Context isolatedContext = JDBCUtil.getIsolatedContext();
      JDBCUtil.unBindAll(isolatedContext, dsBean.getJDBCDataSourceParams().getJNDINames());
   }

   public void resume() {
   }

   public void suspend(boolean b) {
   }

   public void forceSuspend(boolean b) {
   }

   public void shutdown() {
   }

   public void start(Object initInfo) throws ResourceException {
   }

   public abstract DataSource instantiate(JDBCDataSourceBean var1, String var2) throws ResourceException, SQLException;

   protected Properties parsePropertiesList(String propList) {
      Properties properties = new Properties();
      if (propList != null && !propList.equals("")) {
         String[] nameValues = propList.split("[\\s,]");
         if (nameValues != null && nameValues.length != 0) {
            String[] var4 = nameValues;
            int var5 = nameValues.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String nameValue = var4[var6];
               String[] nv = nameValue.split("=");
               properties.put(nv[0], nv[1]);
            }

            return properties;
         } else {
            return properties;
         }
      } else {
         return properties;
      }
   }

   protected DataSource loadDriver(String driver) throws ResourceException {
      Object obj;
      try {
         obj = DataSourceUtil.loadDriver(driver, this.getClass().getClassLoader());
      } catch (ClassNotFoundException var4) {
         throw new ResourceException(var4);
      } catch (InstantiationException var5) {
         throw new ResourceException(var5);
      } catch (IllegalAccessException var6) {
         throw new ResourceException(var6);
      }

      if (!(obj instanceof DataSource)) {
         throw new ResourceException(JDBCUtil.getTextFormatter().driverNotFound(driver));
      } else {
         return (DataSource)obj;
      }
   }

   protected void debug(String msg) {
      System.out.println("AbstractDataSourceManager." + msg);
   }

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      if (obj instanceof Reference) {
         Reference reference = (Reference)obj;
         RefAddr addr = reference.get("key");
         if (addr != null) {
            return this.get((String)addr.getContent());
         }
      }

      if (DEBUG) {
         this.debug("getObjectInstance() no match. obj=" + obj + ", name=" + name + ", nameCtx=" + nameCtx + ", environment=" + environment);
      }

      return null;
   }
}
