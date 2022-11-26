package weblogic.connector.outbound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.ResourcePoolProfiler;
import weblogic.connector.common.Debug;
import weblogic.diagnostics.instrumentation.EventDispatcher.Helper;
import weblogic.utils.StackTraceUtils;

public class ConnectionPoolProfiler implements ResourcePoolProfiler {
   private int NUM_TYPES = 4;
   private int TYPE_CONN_USAGE = 0;
   private int TYPE_CONN_WAIT = 1;
   private int TYPE_CONN_LEAK = 2;
   private int TYPE_CONN_RESV_FAIL = 3;
   private ConnectionPool pool;
   private HashMap[] profileData;
   private boolean resProfEnabled = false;

   ConnectionPoolProfiler(ConnectionPool pool) {
      this.pool = pool;
      this.profileData = new HashMap[this.NUM_TYPES];
      this.profileData[this.TYPE_CONN_USAGE] = new HashMap();
      this.profileData[this.TYPE_CONN_WAIT] = new HashMap();
      this.profileData[this.TYPE_CONN_LEAK] = new HashMap();
      this.profileData[this.TYPE_CONN_RESV_FAIL] = new HashMap();
      this.resProfEnabled = Boolean.getBoolean("weblogic.connector.ConnectionPoolProfilingEnabled");
   }

   private boolean isResourceProfilingEnabled() {
      return this.resProfEnabled;
   }

   public boolean isResourceUsageProfilingEnabled() {
      return this.isResourceProfilingEnabled();
   }

   public boolean isResourceReserveWaitProfilingEnabled() {
      return this.isResourceProfilingEnabled();
   }

   public boolean isResourceReserveFailProfilingEnabled() {
      return this.isResourceProfilingEnabled();
   }

   public boolean isResourceLeakProfilingEnabled() {
      return this.isResourceProfilingEnabled();
   }

   public synchronized void dumpData() {
      this.printData(this.profileData[this.TYPE_CONN_USAGE].values().iterator());
      this.printData(this.profileData[this.TYPE_CONN_WAIT].values().iterator());
      this.printData(this.profileData[this.TYPE_CONN_LEAK].values().iterator());
      this.printData(this.profileData[this.TYPE_CONN_RESV_FAIL].values().iterator());
   }

   public synchronized void harvestData() {
      if (this.isResourceProfilingEnabled()) {
         this.persistData("WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.USAGE", this.profileData[this.TYPE_CONN_USAGE].values().iterator());
         this.persistData("WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.WAIT", this.profileData[this.TYPE_CONN_WAIT].values().iterator());
         this.persistData("WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.LEAK", this.profileData[this.TYPE_CONN_LEAK].values().iterator());
         this.persistData("WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.RESVFAIL", this.profileData[this.TYPE_CONN_RESV_FAIL].values().iterator());
      }

   }

   public synchronized void deleteData() {
      this.deleteLeakData();
      this.deleteResvFailData();
   }

   public void addUsageData(PooledResource res) {
      Properties props = new Properties();
      props.setProperty("Connection", res.toString());
      ProfileDataRecord record = new ProfileDataRecord("WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.USAGE", this.pool.getName(), props);
      synchronized(this) {
         this.profileData[this.TYPE_CONN_USAGE].put(res, record);
      }
   }

   public synchronized void deleteUsageData(PooledResource res) {
      this.profileData[this.TYPE_CONN_USAGE].remove(res);
   }

   public void addWaitData() {
      String thrId = Thread.currentThread().getName();
      Properties props = new Properties();
      props.setProperty("ThreadID", thrId);
      props.setProperty("StackTrace", StackTraceUtils.throwable2StackTrace(new Exception()));
      ProfileDataRecord record = new ProfileDataRecord("WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.WAIT", this.pool.getName(), props);
      synchronized(this) {
         this.profileData[this.TYPE_CONN_WAIT].put(thrId, record);
      }
   }

   public void deleteWaitData() {
      synchronized(this) {
         this.profileData[this.TYPE_CONN_WAIT].remove(Thread.currentThread().getName());
      }
   }

   public void addLeakData(PooledResource res) {
      Properties props = new Properties();
      props.setProperty("Connection", res.toString());
      ProfileDataRecord record = new ProfileDataRecord("WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.LEAK", this.pool.getName(), props);
      synchronized(this) {
         this.profileData[this.TYPE_CONN_LEAK].put(res, record);
      }
   }

   private void deleteLeakData() {
      this.profileData[this.TYPE_CONN_LEAK].clear();
   }

   public void addResvFailData(String stackTrace) {
      Properties props = new Properties();
      props.setProperty("ThreadID", Thread.currentThread().getName());
      props.setProperty("StackTrace", stackTrace);
      String thrId = Thread.currentThread().getName();
      ProfileDataRecord record = new ProfileDataRecord("WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.RESVFAIL", this.pool.getName(), props);
      synchronized(this) {
         this.profileData[this.TYPE_CONN_RESV_FAIL].put(thrId, record);
      }
   }

   private void deleteResvFailData() {
      this.profileData[this.TYPE_CONN_RESV_FAIL].clear();
   }

   private synchronized void printData(Iterator iter) {
      while(iter.hasNext()) {
         ProfileDataRecord record = (ProfileDataRecord)iter.next();
         Debug.logPoolProfilingRecord(record);
      }

   }

   private void persistData(String type, Iterator iter) {
      while(iter.hasNext()) {
         Helper.dispatch(type, (ProfileDataRecord)iter.next());
      }

   }
}
