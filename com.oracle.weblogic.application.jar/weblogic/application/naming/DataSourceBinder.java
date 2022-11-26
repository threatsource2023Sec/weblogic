package weblogic.application.naming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.common.internal.DataSourceManager;
import weblogic.jdbc.common.internal.DataSourceService;

public class DataSourceBinder {
   private final Context globalContext;
   private final Context appContext;
   private final Context moduleContext;
   private final Context compContext;
   private final String applicationName;
   private final String moduleName;
   private final String componentName;
   private final List dataSources;

   public DataSourceBinder(Context globalContext, Context appContext, Context moduleContext, Context compContext, String applicationName, String moduleName, String componentName) {
      this.globalContext = globalContext;
      this.appContext = appContext;
      this.moduleContext = moduleContext;
      this.compContext = compContext;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.componentName = componentName;
      this.dataSources = new ArrayList();
   }

   public void bindDataSources(DataSourceBean[] dataSourceBeans) throws NamingException, ResourceException {
      if (dataSourceBeans != null && dataSourceBeans.length != 0) {
         DataSourceService service = DataSourceManager.getInstance().getDataSourceService();
         DataSourceBean[] var3 = dataSourceBeans;
         int var4 = dataSourceBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            DataSourceBean dataSourceBean = var3[var5];
            this.createAndBindDataSource(service, dataSourceBean);
         }

      }
   }

   public static DataSource createDataSource(DataSourceBean dataSourceBean, String applicationName, String moduleName, String componentName) throws ResourceException {
      DataSourceService dsService = DataSourceManager.getInstance().getDataSourceService();
      JDBCDataSourceBean jdbcDataSource = dsService.createJDBCDataSourceBean(dataSourceBean);
      return dsService.createDataSource(jdbcDataSource, applicationName, moduleName, componentName);
   }

   public DataSource createAndBindDataSource(DataSourceService service, DataSourceBean dataSourceBean) throws ResourceException, NamingException {
      String name = dataSourceBean.getName();
      DataSource rmiDataSource = createDataSource(dataSourceBean, this.applicationName, this.moduleName, this.componentName);
      this.bindWithPortableJNDIName(name, rmiDataSource);
      this.dataSources.add(name);
      return rmiDataSource;
   }

   public void unbindDataSources() throws NamingException, ResourceException {
      if (!this.dataSources.isEmpty()) {
         Iterator var1 = this.dataSources.iterator();

         while(var1.hasNext()) {
            String dataSource = (String)var1.next();
            this.unbindWithPortableJNDIName(dataSource);
            this.destroyDataSource(dataSource);
         }

      }
   }

   private void bindWithPortableJNDIName(String name, Object value) throws NamingException {
      if (this.isValidJavaCompName(name)) {
         this.compContext.bind(name.substring("java:comp".length() + 1), value);
      } else if (this.isValidJavaModuleName(name)) {
         this.moduleContext.bind(name.substring("java:module".length() + 1), value);
      } else if (this.isValidJavaAppName(name)) {
         this.appContext.bind(name.substring("java:app".length() + 1), value);
      } else if (this.isJavaGlobalName(name)) {
         this.globalContext.bind(name.substring("java:global".length() + 1), value);
      } else {
         Context compEnvContext = this.findCompEnvContext();
         if (compEnvContext != null) {
            compEnvContext.bind(name, value);
         }
      }

   }

   private void unbindWithPortableJNDIName(String name) throws NamingException {
      if (this.isValidJavaCompName(name)) {
         this.compContext.unbind(name.substring("java:comp".length() + 1));
      } else if (this.isValidJavaModuleName(name)) {
         this.moduleContext.unbind(name.substring("java:module".length() + 1));
      } else if (this.isValidJavaAppName(name)) {
         this.appContext.unbind(name.substring("java:app".length() + 1));
      } else if (this.isJavaGlobalName(name)) {
         this.globalContext.unbind(name.substring("java:global".length() + 1));
      } else {
         Context compEnvContext = this.findCompEnvContext();
         if (compEnvContext != null) {
            compEnvContext.unbind(name);
         }
      }

   }

   private void destroyDataSource(String dataSourceName) throws ResourceException {
      DataSourceService dsService = DataSourceManager.getInstance().getDataSourceService();
      dsService.destroyDataSource(dataSourceName, this.applicationName, this.moduleName, this.componentName);
   }

   private boolean isJavaGlobalName(String name) {
      return name.startsWith("java:global") && this.globalContext != null;
   }

   private boolean isValidJavaAppName(String name) {
      return name.startsWith("java:app") && this.appContext != null;
   }

   private boolean isValidJavaModuleName(String name) {
      return name.startsWith("java:module") && this.moduleContext != null;
   }

   private boolean isValidJavaCompName(String name) {
      return name.startsWith("java:comp") && this.compContext != null;
   }

   private Context findCompEnvContext() {
      if (this.compContext == null) {
         return null;
      } else {
         try {
            return (Context)this.compContext.lookup("env");
         } catch (NamingException var2) {
            return null;
         }
      }
   }
}
