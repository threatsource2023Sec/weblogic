package weblogic.jdbc.common.rac.internal;

import java.util.Properties;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACModuleFailoverEvent;
import weblogic.utils.StackTraceUtils;

public class RACInstanceImpl implements RACInstance {
   static final long serialVersionUID = -7497762020350419845L;
   private static final String DATABASE_KEY = "database";
   private static final String HOST_KEY = "host";
   private static final String INSTANCE_KEY = "instance";
   private static final String SERVICE_KEY = "service";
   private static final String PERCENT_KEY = "percent";
   String database;
   String host;
   String instance;
   String service;
   int percent;
   volatile int intervalDrainCount;
   volatile int currentDrainCount;
   boolean aff;
   int hashcode;
   Properties props;
   RACModuleFailoverEvent downEvent;

   RACInstanceImpl(String database, String host, String instance, String service) {
      this.intervalDrainCount = 0;
      this.currentDrainCount = 0;
      this.props = null;
      this.downEvent = null;
      this.database = this.normalize(database);
      this.host = this.normalize(host);
      this.instance = this.normalize(instance);
      this.service = service;
      this.percent = 1;
      this.init();
   }

   RACInstanceImpl(String database, String host, String instance, String service, int percent, boolean aff) {
      this(database, host, instance, service);
      this.percent = percent;
      this.aff = aff;
   }

   RACInstanceImpl(Properties props) {
      this(props.getProperty("database"), props.getProperty("host"), props.getProperty("instance"), props.getProperty("service"), Integer.parseInt(props.getProperty("percent")), false);
      this.props = (Properties)props.clone();
   }

   private void init() {
      if (this.getInstance() == null) {
         this.debug("service=null at " + StackTraceUtils.throwable2StackTrace(new Throwable()));
         this.hashcode = 1;
      } else {
         this.hashcode = this.getInstance().hashCode();
      }

      this.intervalDrainCount = 0;
      this.currentDrainCount = 0;
      this.downEvent = null;
   }

   public String getDatabase() {
      return this.database;
   }

   public String getHost() {
      return this.host;
   }

   public String getInstance() {
      return this.instance;
   }

   public String getService() {
      return this.service;
   }

   public int getPercent() {
      return this.percent;
   }

   public void setPercent(int percent) {
      this.percent = percent;
   }

   public boolean getAff() {
      return this.aff;
   }

   public void setAff(boolean aff) {
      this.aff = aff;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof RACInstance)) {
         return false;
      } else {
         RACInstance that = (RACInstance)obj;
         return this.isEqual(this.instance, that.getInstance()) && this.isEqual(this.service, that.getService()) && this.isEqual(this.database, that.getDatabase()) && this.isEqual(this.host, that.getHost());
      }
   }

   private final boolean isEqual(String a, String b) {
      if (a == null && b == null) {
         return true;
      } else {
         return a != null ? a.equalsIgnoreCase(b) : false;
      }
   }

   private String normalize(String name) {
      return name == null ? null : name.toLowerCase();
   }

   public int hashCode() {
      return this.hashcode;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("RACInstance[");
      sb.append("service=" + this.getService());
      sb.append(", instance=" + this.getInstance());
      sb.append(", host=" + this.getHost());
      sb.append(", database=" + this.getDatabase());
      sb.append(", percent=" + this.getPercent());
      sb.append(")");
      return sb.toString();
   }

   public Properties getProperties() {
      synchronized(this) {
         if (this.props == null) {
            this.props = new Properties();
            this.props.setProperty("database", this.database);
            this.props.setProperty("host", this.host);
            this.props.setProperty("instance", this.instance);
            this.props.setProperty("service", this.service);
            this.props.setProperty("percent", String.valueOf(this.percent));
         }
      }

      return this.props;
   }

   public int getCurrentDrainCount() {
      return this.currentDrainCount;
   }

   public void setCurrentDrainCount(int currentDrainCount) {
      this.currentDrainCount = currentDrainCount;
   }

   public int getIntervalDrainCount() {
      return this.intervalDrainCount;
   }

   public void setIntervalDrainCount(int intervalDrainCount) {
      this.intervalDrainCount = intervalDrainCount;
   }

   public RACModuleFailoverEvent getDownEvent() {
      return this.downEvent;
   }

   public void setDownEvent(RACModuleFailoverEvent downEvent) {
      this.downEvent = downEvent;
   }

   private void debug(String msg) {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         JdbcDebug.JDBCRAC.debug("RACInstanceImpl [" + this.toString() + "]: " + msg);
      }

   }
}
