package weblogic.osgi.internal;

import com.oracle.core.registryhelper.RegistryListener;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.launch.Framework;
import weblogic.jdbc.common.internal.DataSourceManager;
import weblogic.jdbc.common.internal.RmiDataSource;
import weblogic.osgi.OSGiLogger;

public class OSGiDataSourceRegistryListener implements RegistryListener {
   private static final String NAME = "Name";
   private static final String APPLICATION = "Application";
   private final Object lock = new Object();
   private final Framework framework;
   private final Map dataSources = new HashMap();
   private final String frameworkName;

   OSGiDataSourceRegistryListener(Framework myFramework, String frameworkName) {
      this.framework = myFramework;
      this.frameworkName = frameworkName;
   }

   void initialize() {
      DataSourceManager.getInstance().addRegistryListener(this);
   }

   void destroy() {
      synchronized(this.lock) {
         Iterator var2 = this.dataSources.values().iterator();

         while(true) {
            if (!var2.hasNext()) {
               this.dataSources.clear();
               break;
            }

            ServiceRegistration handle = (ServiceRegistration)var2.next();
            handle.unregister();
         }
      }

      DataSourceManager.getInstance().removeRegistryListener(this);
   }

   private void addDataSource(String mangledName, RmiDataSource object) {
      if (object != null) {
         if (this.isDataSourceinScope(object)) {
            DataSourceName dsn = new DataSourceName(mangledName);
            if (dsn.getModuleName() == null) {
               BundleContext bc = this.framework.getBundleContext();
               Dictionary props = new Hashtable();
               props.put("Name", dsn.getName());
               if (dsn.getApplicationName() != null) {
                  props.put("Application", dsn.getApplicationName());
               }

               String[] iFaces = Utilities.getAllInterfaces(object);
               if (iFaces.length <= 0) {
                  iFaces = new String[]{object.getClass().getName()};
               }

               ServiceRegistration handle;
               try {
                  handle = bc.registerService(iFaces, object, props);
               } catch (Throwable var11) {
                  OSGiLogger.logCouldNotAdvertiseDataSource(mangledName, var11.getMessage(), this.frameworkName);
                  return;
               }

               synchronized(this.lock) {
                  this.dataSources.put(mangledName, handle);
               }
            }
         }
      }
   }

   public void init(Collection initialElements) {
      Iterator var2 = initialElements.iterator();

      while(var2.hasNext()) {
         Map.Entry element = (Map.Entry)var2.next();
         String name = (String)element.getKey();
         RmiDataSource manager = (RmiDataSource)element.getValue();
         this.addDataSource(name, manager);
      }

   }

   public void added(Map.Entry added) {
      this.addDataSource((String)added.getKey(), (RmiDataSource)added.getValue());
   }

   public void removed(Map.Entry removed) {
      ServiceRegistration handle = null;
      synchronized(this.lock) {
         handle = (ServiceRegistration)this.dataSources.remove(removed.getKey());
      }

      if (handle != null) {
         handle.unregister();
      }

   }

   public void modified(Map.Entry oldValue, Map.Entry newValue) {
      this.removed(oldValue);
      this.added(newValue);
   }

   private boolean isDataSourceinScope(RmiDataSource object) {
      if (object.getPartitionName() != null) {
         String partitionName = this.frameworkName.substring(this.frameworkName.lastIndexOf("$") + 1);
         return partitionName.equals(object.getPartitionName());
      } else {
         return !this.frameworkName.contains("$");
      }
   }

   public String toString() {
      return "OSGiWorkManagerRegistryListener(" + System.identityHashCode(this) + "," + this.framework.getSymbolicName() + ")";
   }

   private static class DataSourceName {
      private final String name;
      private final String applicationName;
      private final String moduleName;

      private DataSourceName(String chunkedName) {
         StringTokenizer st = new StringTokenizer(chunkedName, "@");
         int numTokens = st.countTokens();
         if (numTokens >= 1 && numTokens <= 4) {
            if (numTokens == 1) {
               this.applicationName = null;
               this.moduleName = null;
               this.name = setDSName(st.nextToken());
            } else if (numTokens == 2) {
               this.applicationName = st.nextToken();
               this.moduleName = null;
               this.name = setDSName(st.nextToken());
            } else if (numTokens == 3) {
               this.applicationName = st.nextToken();
               this.moduleName = st.nextToken();
               this.name = setDSName(st.nextToken());
            } else {
               this.applicationName = st.nextToken();
               this.moduleName = st.nextToken();
               st.nextToken();
               this.name = setDSName(st.nextToken());
            }

         } else {
            throw new AssertionError("Unknown name format with " + numTokens + " tokens");
         }
      }

      private static String setDSName(String name) {
         return name.contains("$") ? name.substring(0, name.indexOf("$")) : name;
      }

      private String getName() {
         return this.name;
      }

      private String getModuleName() {
         return this.moduleName;
      }

      private String getApplicationName() {
         return this.applicationName;
      }

      // $FF: synthetic method
      DataSourceName(String x0, Object x1) {
         this(x0);
      }
   }
}
