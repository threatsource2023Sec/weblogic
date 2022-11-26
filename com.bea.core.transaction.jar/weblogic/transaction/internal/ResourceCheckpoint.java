package weblogic.transaction.internal;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import weblogic.transaction.TransactionLoggable;
import weblogic.transaction.TransactionLogger;
import weblogic.transaction.loggingresource.LoggingResource;

class ResourceCheckpoint implements TransactionLoggable {
   private boolean ioComplete = false;
   static final int VERSION1 = 1;
   static final int VERSION2 = 2;
   static final int VERSION3 = 3;
   static final int VERSION4 = 4;
   static final int VERSION5 = 5;
   private List resourceInfoList = new LinkedList();
   private String serverNameForCrossSiteRecovery;

   public void writeExternal(DataOutput out) throws IOException {
      LogDataOutput encoder = (LogDataOutput)out;
      if (PlatformHelper.getPlatformHelper().isServer()) {
         this.resourceInfoList.clear();
         List rds = ResourceDescriptor.getAllCheckpointResources();
         if (rds != null) {
            Iterator var4 = rds.iterator();

            while(var4.hasNext()) {
               Object rd1 = var4.next();
               ResourceDescriptor rd = (ResourceDescriptor)rd1;
               ResourceInfo resourceInfo = new ResourceInfo(rd.getName(), rd.getLastAccessTimeMillis());
               if (rd instanceof NonXAResourceDescriptor && ((NonXAResourceDescriptor)rd).getNonXAResource() instanceof LoggingResource) {
                  resourceInfo.setLLR(true);
               }

               resourceInfo.setDeterminer(rd.isDeterminer());
               this.resourceInfoList.add(resourceInfo);
               XAResourceDescriptor.get(resourceInfo.name);
            }
         }
      }

      int version = 5;
      if (PlatformHelper.getPlatformHelper().isCheckpointLLR()) {
         version = 4;
      }

      encoder.writeNonNegativeInt(version);
      if (this.resourceInfoList == null) {
         encoder.writeNonNegativeInt(0);
      } else {
         int cnt = this.resourceInfoList.size();
         encoder.writeNonNegativeInt(cnt);
         Iterator var10 = this.resourceInfoList.iterator();

         while(var10.hasNext()) {
            ResourceInfo ri = (ResourceInfo)var10.next();
            encoder.writeAbbrevString(ri.name);
            encoder.writeLong(ri.lastAccessTimeMillis);
            if (ri.props != null && !ri.props.isEmpty()) {
               encoder.writeProperties(ri.props);
            }
         }

         if (TxDebug.JTARecovery.isDebugEnabled()) {
            TxDebug.JTARecovery.debug("writeExternal: " + this);
         }

      }
   }

   public void readExternal(DataInput in) throws IOException {
      LogDataInput decoder = (LogDataInput)in;
      this.resourceInfoList.clear();
      int version = decoder.readNonNegativeInt();
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("readExternal version: " + version + " this:" + this);
      }

      if (version >= 1 && version <= 5) {
         int rcnt;
         int i;
         String rname;
         long time;
         Properties props;
         if (version == 5) {
            rcnt = decoder.readNonNegativeInt();

            for(i = 0; i < rcnt; ++i) {
               rname = decoder.readAbbrevString();
               time = decoder.readLong();
               props = (Properties)decoder.readProperties();
               this.resourceInfoList.add(new ResourceInfo(rname, time, props));
            }
         } else if (version == 4) {
            rcnt = decoder.readNonNegativeInt();

            for(i = 0; i < rcnt; ++i) {
               rname = decoder.readAbbrevString();
               time = decoder.readLong();
               props = (Properties)decoder.readProperties();
               this.resourceInfoList.add(new ResourceInfo(rname, time, props));
            }
         } else if (version == 3) {
            rcnt = decoder.readNonNegativeInt();

            for(i = 0; i < rcnt; ++i) {
               rname = decoder.readAbbrevString();
               time = decoder.readLong();
               this.resourceInfoList.add(new ResourceInfo(rname, time));
            }
         } else if (version == 2) {
            rcnt = decoder.readNonNegativeInt();

            for(i = 0; i < rcnt; ++i) {
               rname = decoder.readAbbrevString();
               this.resourceInfoList.add(new ResourceInfo(rname, 0L));
            }
         } else if (version == 1) {
            rcnt = decoder.readNonNegativeInt();

            for(i = 0; i < rcnt; ++i) {
               rname = decoder.readAbbrevString();
               this.resourceInfoList.add(new ResourceInfo(rname, 0L));
               int scnt = decoder.readNonNegativeInt();

               for(int j = 0; j < scnt; ++j) {
                  decoder.readAbbrevString();
               }
            }
         }

      } else {
         throw new InvalidObjectException("ResourceCheckponit transaction log record: unrecognized version number " + version);
      }
   }

   boolean contains(String resName) {
      return this.resourceInfoList != null && this.resourceInfoList.contains(new ResourceInfo(resName, 0L));
   }

   public void onDisk(TransactionLogger tlog) {
      synchronized(this) {
         this.ioComplete = true;
         this.notifyAll();
      }

      this.setLatestResourceCheckpoint(tlog);
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("onDisk() callback: " + this);
      }

   }

   public void onError(TransactionLogger tlog) {
      synchronized(this) {
         this.ioComplete = true;
         this.notifyAll();
      }

      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("onError() callback: " + this);
      }

   }

   public void onRecovery(TransactionLogger tlog) {
      TxDebug.JTARecovery.debug("ResourceCheckpoint.onRecovery tlog instanceof StoreTransactionLoggerImpl:" + (tlog instanceof StoreTransactionLoggerImpl));
      if (tlog instanceof StoreTransactionLoggerImpl) {
         this.serverNameForCrossSiteRecovery = ((StoreTransactionLoggerImpl)tlog).serverNameForCrossSiteRecovery;
      }

      String migratedFrom = tlog.getMigratedCoordinatorURL();
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug((migratedFrom == null ? "" : migratedFrom) + " onRecovery() callback: " + this);
         TxDebug.JTARecovery.debug("ResourceCheckpoint.onRecovery migratedFrom:" + migratedFrom);
      }

      Iterator var3 = this.resourceInfoList.iterator();

      while(true) {
         while(var3.hasNext()) {
            ResourceInfo resourceInfo = (ResourceInfo)var3.next();
            long lastAccessTimeMillis = -1L;
            String resName = ((ResourceInfo)resourceInfo).name;
            lastAccessTimeMillis = ((ResourceInfo)resourceInfo).lastAccessTimeMillis;
            boolean isllr = ((ResourceInfo)resourceInfo).isLLR();
            ResourceDescriptor rd;
            if (isllr) {
               ServerTransactionManagerImpl stm = (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
               rd = NonXAResourceDescriptor.getOrCreate(resName);
               stm.addCheckpointedLLR(rd);
            } else {
               if (TxDebug.JTARecovery.isDebugEnabled()) {
                  TxDebug.JTARecovery.debug("onRecovery tlog = [" + tlog + "] migratedFrom:" + migratedFrom);
               }

               if (migratedFrom != null && !migratedFrom.equals("~")) {
                  rd = this.onRecoveryMigratedFromIsNotNullNorExternalDomain(migratedFrom, resName);
               } else {
                  rd = this.onRecoveryMigratedFromIsNullOrExternalDomain(migratedFrom, resName, (ResourceInfo)resourceInfo);
               }

               rd.setLastAccessTimeMillis(lastAccessTimeMillis);
            }
         }

         this.setLatestResourceCheckpoint(tlog);
         return;
      }
   }

   ResourceDescriptor onRecoveryMigratedFromIsNullOrExternalDomain(String migratedFrom, String resName, ResourceInfo obj) {
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug((migratedFrom == null ? "" : migratedFrom) + "ResourceCheckpoint.onRecoveryMigratedFromIsNullOrExternalDomain() callback: " + this + " migratedFrom:" + migratedFrom + " ((ResourceInfo) obj).isDeterminer():" + obj.isDeterminer() + " resName:" + resName);
      }

      ResourceDescriptor rd = XAResourceDescriptor.getOrCreateForRecovery(resName);
      XAResourceDescriptor xard = (XAResourceDescriptor)rd;
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("ResourceCheckpoint.onRecoveryMigratedFromIsNullOrExternalDomain() callback: " + this + " migratedFrom:" + migratedFrom + " ((ResourceInfo) obj).isDeterminer():" + obj.isDeterminer() + " resName:" + resName + " rd:" + rd);
      }

      rd.setDeterminer(obj.isDeterminer());
      if (obj.isDeterminer()) {
         rd.setDeterminerFromCheckpoint();
      }

      if (this.serverNameForCrossSiteRecovery != null && (this.getTM().getDeterminersForDomainAndAllPartitions() == null || this.getTM().getDeterminersForDomainAndAllPartitions().length <= 0)) {
         xard.recover(this.getTM().getLocalCoordinatorDescriptor(), this.serverNameForCrossSiteRecovery);
      } else {
         xard.setNeedsRecovery(this.getTM().getLocalCoordinatorDescriptor());
      }

      xard.setCoordinatedLocally();
      return rd;
   }

   ResourceDescriptor onRecoveryMigratedFromIsNotNullNorExternalDomain(String migratedFrom, String resName) {
      CoordinatorDescriptor cd = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getOrCreate(migratedFrom);
      ResourceDescriptor rd = XAResourceDescriptor.getOrCreateForMigratedTLog(resName, cd);
      XAResourceDescriptor xard = (XAResourceDescriptor)rd;
      xard.setNeedsRecovery(cd);
      return rd;
   }

   void setLatestResourceCheckpoint(TransactionLogger tlog) {
      XAResourceDescriptor.setLatestResourceCheckpoint(tlog, this);
   }

   synchronized void blockingStore(TransactionLogger tlog) {
      tlog.store(this);

      try {
         if (!this.ioComplete) {
            this.wait();
         }
      } catch (InterruptedException var3) {
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder(100);
      sb.append("ResourceCheckpoint={");
      Iterator i = this.resourceInfoList.iterator();

      while(i.hasNext()) {
         sb.append(((ResourceInfo)i.next()).toString());
         if (i.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append("}");
      return sb.toString();
   }

   void convertPre810JTSName() {
      Iterator var1 = this.resourceInfoList.iterator();

      while(var1.hasNext()) {
         ResourceInfo ri = (ResourceInfo)var1.next();
         if (ri.name.equals("weblogic.jdbc.jts.Connection")) {
            ri.name = "weblogic.jdbc.wrapper.JTSXAResourceImpl";
         }
      }

   }

   private ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }

   class ResourceInfo {
      String name;
      long lastAccessTimeMillis;
      Properties props;
      private static final String LLR = "LLR";
      private static final String IS_DETERMINER = "IS_DETERMINER";

      ResourceInfo(String name, long time) {
         this.name = name;
         this.lastAccessTimeMillis = time;
         this.props = new Properties();
      }

      ResourceInfo(String name, long time, Properties props) {
         this.name = name;
         this.lastAccessTimeMillis = time;
         this.props = props;
      }

      public String toString() {
         return "{" + this.name + ", props=" + this.props + "}";
      }

      public void setLLR(boolean llr) {
         this.props.put("LLR", llr);
      }

      public Boolean isLLR() {
         Object object = this.props.get("LLR");
         return object == null ? false : (Boolean)object;
      }

      public void setDeterminer(boolean isDeterminer) {
         this.props.put("IS_DETERMINER", isDeterminer);
      }

      public Boolean isDeterminer() {
         Object object = this.props.get("IS_DETERMINER");
         return object == null ? false : (Boolean)object;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else if (o instanceof ResourceInfo) {
            ResourceInfo that = (ResourceInfo)o;
            return this.name == null ? null == that.name : this.name.equals(that.name);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.name == null ? 0 : this.name.hashCode();
      }
   }
}
