package org.apache.openjpa.slice.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.schema.DataSourceFactory;
import org.apache.openjpa.lib.conf.BooleanValue;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.conf.StringListValue;
import org.apache.openjpa.lib.conf.StringValue;
import org.apache.openjpa.lib.jdbc.DecoratingDataSource;
import org.apache.openjpa.lib.jdbc.DelegatingDataSource;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.log.LogFactory;
import org.apache.openjpa.lib.log.LogFactoryImpl;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.slice.DistributedBrokerImpl;
import org.apache.openjpa.slice.DistributionPolicy;
import org.apache.openjpa.slice.ExecutorServiceValue;
import org.apache.openjpa.slice.Slice;
import org.apache.openjpa.util.UserException;

public class DistributedJDBCConfigurationImpl extends JDBCConfigurationImpl implements DistributedJDBCConfiguration {
   private final List _slices = new ArrayList();
   private List _activeSliceNames = new ArrayList();
   private Slice _master;
   private DecoratingDataSource virtualDataSource;
   protected BooleanValue lenientPlugin;
   protected StringValue masterPlugin;
   protected StringListValue namesPlugin;
   protected ExecutorServiceValue executorServicePlugin;
   protected PluginValue distributionPolicyPlugin;
   public static final String DOT = ".";
   public static final String REGEX_DOT = "\\.";
   public static final String PREFIX_SLICE = "openjpa.slice.";
   public static final String PREFIX_OPENJPA = "openjpa.";
   private static Localizer _loc = Localizer.forPackage(DistributedJDBCConfigurationImpl.class);

   public DistributedJDBCConfigurationImpl(ConfigurationProvider cp) {
      super(true, false);
      Map p = cp.getProperties();
      String pUnit = this.getPersistenceUnitName(p);
      this.setDiagnosticContext(pUnit);
      this.brokerPlugin.setString(DistributedBrokerImpl.class.getName());
      this.distributionPolicyPlugin = this.addPlugin("DistributionPolicy", true);
      this.distributionPolicyPlugin.setDynamic(true);
      this.lenientPlugin = this.addBoolean("Lenient");
      this.masterPlugin = this.addString("Master");
      this.namesPlugin = this.addStringList("Names");
      this.executorServicePlugin = new ExecutorServiceValue();
      this.addValue(this.executorServicePlugin);
      this.setSlices(p);
   }

   private String getPersistenceUnitName(Map p) {
      Object unit = p.get("openjpa." + this.id.getProperty());
      return unit == null ? "?" : unit.toString();
   }

   private void setDiagnosticContext(String unit) {
      LogFactory logFactory = this.getLogFactory();
      if (logFactory instanceof LogFactoryImpl) {
         ((LogFactoryImpl)logFactory).setDiagnosticContext(unit);
      }

   }

   public List getActiveSliceNames() {
      if (this._activeSliceNames.isEmpty()) {
         Iterator i$ = this._slices.iterator();

         while(i$.hasNext()) {
            Slice slice = (Slice)i$.next();
            if (slice.isActive()) {
               this._activeSliceNames.add(slice.getName());
            }
         }
      }

      return this._activeSliceNames;
   }

   public List getAvailableSliceNames() {
      List result = new ArrayList();
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         Slice slice = (Slice)i$.next();
         result.add(slice.getName());
      }

      return result;
   }

   public List getSlices(Slice.Status... statuses) {
      if (statuses == null) {
         return Collections.unmodifiableList(this._slices);
      } else {
         List result = new ArrayList();
         Iterator i$ = this._slices.iterator();

         while(i$.hasNext()) {
            Slice slice = (Slice)i$.next();
            Slice.Status[] arr$ = statuses;
            int len$ = statuses.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Slice.Status status = arr$[i$];
               if (slice.getStatus().equals(status)) {
                  result.add(slice);
               }
            }
         }

         return result;
      }
   }

   public Slice getMaster() {
      return this._master;
   }

   public Slice getSlice(String name) {
      Iterator i$ = this._slices.iterator();

      Slice slice;
      do {
         if (!i$.hasNext()) {
            throw new UserException(_loc.get("slice-not-found", name, this.getActiveSliceNames()));
         }

         slice = (Slice)i$.next();
      } while(!slice.getName().equals(name));

      return slice;
   }

   public DistributionPolicy getDistributionPolicyInstance() {
      if (this.distributionPolicyPlugin.get() == null) {
         this.distributionPolicyPlugin.instantiate(DistributionPolicy.class, this, true);
      }

      return (DistributionPolicy)this.distributionPolicyPlugin.get();
   }

   public void setDistributionPolicyInstance(String val) {
      this.distributionPolicyPlugin.set(val);
   }

   public Object getConnectionFactory() {
      if (this.virtualDataSource == null) {
         DistributedDataSource ds = this.createDistributedDataStore();
         this.virtualDataSource = DataSourceFactory.installDBDictionary(this.getDBDictionaryInstance(), ds, this, false);
      }

      return this.virtualDataSource;
   }

   private DistributedDataSource createDistributedDataStore() {
      List dataSources = new ArrayList();
      boolean isLenient = this.lenientPlugin.get();
      boolean isXA = true;
      Iterator i$ = this._slices.iterator();

      while(i$.hasNext()) {
         Slice slice = (Slice)i$.next();
         JDBCConfiguration conf = (JDBCConfiguration)slice.getConfiguration();
         Log log = conf.getConfigurationLog();
         String url = this.getConnectionInfo(conf);
         if (log.isInfoEnabled()) {
            log.info(_loc.get("slice-connect", slice, url));
         }

         try {
            DataSource ds = DataSourceFactory.newDataSource(conf, false);
            DecoratingDataSource dds = new DecoratingDataSource(ds);
            DataSource ds = DataSourceFactory.installDBDictionary(conf.getDBDictionaryInstance(), dds, conf, false);
            if (this.verifyDataSource(slice, ds)) {
               dataSources.add(ds);
               isXA &= this.isXACompliant(ds);
            }
         } catch (Throwable var11) {
            this.handleBadConnection(isLenient, slice, var11);
         }
      }

      if (dataSources.isEmpty()) {
         throw new UserException(_loc.get("no-slice"));
      } else {
         DistributedDataSource result = new DistributedDataSource(dataSources);
         return result;
      }
   }

   String getConnectionInfo(OpenJPAConfiguration conf) {
      String result = conf.getConnectionURL();
      if (result == null) {
         result = conf.getConnectionDriverName();
         String props = conf.getConnectionProperties();
         if (props != null) {
            result = result + "(" + props + ")";
         }
      }

      return result;
   }

   boolean isXACompliant(DataSource ds) {
      return ds instanceof DelegatingDataSource ? ((DelegatingDataSource)ds).getInnermostDelegate() instanceof XADataSource : ds instanceof XADataSource;
   }

   private boolean verifyDataSource(Slice slice, DataSource ds) {
      Connection con = null;

      boolean var4;
      try {
         con = ds.getConnection();
         slice.setStatus(Slice.Status.ACTIVE);
         if (con != null) {
            var4 = true;
            return var4;
         }

         slice.setStatus(Slice.Status.INACTIVE);
         var4 = false;
      } catch (SQLException var16) {
         slice.setStatus(Slice.Status.INACTIVE);
         boolean var5 = false;
         return var5;
      } finally {
         if (con != null) {
            try {
               con.close();
            } catch (SQLException var15) {
            }
         }

      }

      return var4;
   }

   private void handleBadConnection(boolean isLenient, Slice slice, Throwable ex) {
      OpenJPAConfiguration conf = slice.getConfiguration();
      String url = conf.getConnectionURL();
      Log log = this.getLog("openjpa.Runtime");
      if (isLenient) {
         if (ex != null) {
            log.warn(_loc.get("slice-connect-known-warn", slice, url, ex.getCause()));
         } else {
            log.warn(_loc.get("slice-connect-warn", slice, url));
         }

      } else if (ex != null) {
         throw new UserException(_loc.get("slice-connect-known-error", slice, url, ex), ex.getCause());
      } else {
         throw new UserException(_loc.get("slice-connect-error", slice, url));
      }
   }

   void setSlices(Map original) {
      List sliceNames = this.findSlices(original);
      Log log = this.getConfigurationLog();
      if (sliceNames.isEmpty()) {
         throw new UserException(_loc.get("slice-none-configured"));
      } else {
         String unit = this.getPersistenceUnitName(original);
         Iterator i$ = sliceNames.iterator();

         while(i$.hasNext()) {
            String key = (String)i$.next();
            JDBCConfiguration child = new JDBCConfigurationImpl();
            child.fromProperties(this.createSliceProperties(original, key));
            child.setId(unit + "." + key);
            Slice slice = new Slice(key, child);
            this._slices.add(slice);
            if (log.isTraceEnabled()) {
               log.trace(_loc.get("slice-configuration", key, child.toProperties(false)));
            }
         }

         this.setMaster(original);
      }
   }

   private List findSlices(Map p) {
      List sliceNames = new ArrayList();
      Log log = this.getConfigurationLog();
      String key = "openjpa.slice." + this.namesPlugin.getProperty();
      boolean explicit = p.containsKey(key);
      if (explicit) {
         String[] values = p.get(key).toString().split("\\,");
         String[] arr$ = values;
         int len$ = values.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String name = arr$[i$];
            if (!((List)sliceNames).contains(name.trim())) {
               ((List)sliceNames).add(name.trim());
            }
         }
      } else {
         if (log.isWarnEnabled()) {
            log.warn(_loc.get("no-slice-names", (Object)key));
         }

         sliceNames = this.scanForSliceNames(p);
         Collections.sort((List)sliceNames);
      }

      if (log.isInfoEnabled()) {
         log.info(_loc.get("slice-available", sliceNames));
      }

      return (List)sliceNames;
   }

   private List scanForSliceNames(Map p) {
      List sliceNames = new ArrayList();
      Iterator i$ = p.keySet().iterator();

      while(i$.hasNext()) {
         Object o = i$.next();
         String key = o.toString();
         if (key.startsWith("openjpa.slice.") && getPartCount(key) > 3) {
            String sliceName = chopTail(chopHead(o.toString(), "openjpa.slice."), ".");
            if (!sliceNames.contains(sliceName)) {
               sliceNames.add(sliceName);
            }
         }
      }

      return sliceNames;
   }

   private static int getPartCount(String s) {
      return s == null ? 0 : s.split("\\.").length;
   }

   private static String chopHead(String s, String head) {
      return s.startsWith(head) ? s.substring(head.length()) : s;
   }

   private static String chopTail(String s, String tail) {
      int i = s.lastIndexOf(tail);
      return i == -1 ? s : s.substring(0, i);
   }

   Map createSliceProperties(Map original, String slice) {
      Map result = new Properties();
      String prefix = "openjpa.slice." + slice + ".";
      Iterator i$ = original.keySet().iterator();

      while(i$.hasNext()) {
         Object o = i$.next();
         String key = o.toString();
         String newKey;
         if (key.startsWith(prefix)) {
            newKey = "openjpa." + key.substring(prefix.length());
            result.put(newKey, original.get(o));
         } else if (!key.startsWith("openjpa.slice.")) {
            if (key.startsWith("openjpa.")) {
               newKey = prefix + key.substring("openjpa.".length());
               if (!original.containsKey(newKey)) {
                  result.put(key, original.get(o));
               }
            } else {
               result.put(key, original.get(o));
            }
         }
      }

      return result;
   }

   private void setMaster(Map original) {
      String key = "openjpa.slice." + this.masterPlugin.getProperty();
      Object masterSlice = original.get(key);
      Log log = this.getConfigurationLog();
      List activeSlices = this.getSlices((Slice.Status[])null);
      if (masterSlice == null) {
         this._master = (Slice)activeSlices.get(0);
         if (log.isWarnEnabled()) {
            log.warn(_loc.get("no-master-slice", key, this._master));
         }

      } else {
         Iterator i$ = activeSlices.iterator();

         while(i$.hasNext()) {
            Slice slice = (Slice)i$.next();
            if (slice.getName().equals(masterSlice)) {
               this._master = slice;
            }
         }

         if (this._master == null) {
            this._master = (Slice)activeSlices.get(0);
         }

      }
   }

   public String getExecutorService() {
      return this.executorServicePlugin.getString();
   }

   public void setExecutorService(ExecutorService txnManager) {
      this.executorServicePlugin.set(txnManager);
   }

   public ExecutorService getExecutorServiceInstance() {
      if (this.executorServicePlugin.get() == null) {
         this.executorServicePlugin.instantiate(ExecutorService.class, this);
      }

      return (ExecutorService)this.executorServicePlugin.get();
   }
}
