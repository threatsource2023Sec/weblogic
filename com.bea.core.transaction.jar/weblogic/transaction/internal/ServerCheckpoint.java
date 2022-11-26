package weblogic.transaction.internal;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.transaction.TransactionLoggable;
import weblogic.transaction.TransactionLogger;

final class ServerCheckpoint implements TransactionLoggable {
   private static final int VERSION = 1;
   private boolean ioComplete = false;
   private List myList;

   public void writeExternal(DataOutput out) throws IOException {
      LogDataOutput encoder = (LogDataOutput)out;
      if (this.myList == null) {
         this.myList = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getAllCheckpointServers();
      }

      encoder.writeNonNegativeInt(1);
      if (this.myList == null) {
         encoder.writeNonNegativeInt(0);
      } else {
         int cnt = this.myList.size();
         encoder.writeNonNegativeInt(cnt);
         Iterator i = this.myList.iterator();

         while(i.hasNext()) {
            String coURL = (String)i.next();
            encoder.writeAbbrevString(coURL);
         }

         if (TxDebug.JTARecovery.isDebugEnabled()) {
            TxDebug.JTARecovery.debug("ServerCheckpoint: writeExternal: " + this);
         }

      }
   }

   public void readExternal(DataInput in) throws IOException {
      LogDataInput decoder = (LogDataInput)in;
      if (this.myList == null) {
         this.myList = new LinkedList();
      } else {
         this.myList.clear();
      }

      int version = decoder.readNonNegativeInt();
      if (version != 1) {
         throw new InvalidObjectException("ServerCheckpoint transaction log record: unrecognized version number " + version);
      } else {
         int cnt = decoder.readNonNegativeInt();

         for(int i = 0; i < cnt; ++i) {
            String coURL = decoder.readAbbrevString();
            this.myList.add(coURL);
         }

      }
   }

   public void onDisk(TransactionLogger tlog) {
      synchronized(this) {
         this.ioComplete = true;
         this.notifyAll();
      }

      ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).setLatestServerCheckpoint(tlog, this);
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("ServerCheckpoint: onDisk() callback: " + this);
      }

   }

   public void onError(TransactionLogger tlog) {
      synchronized(this) {
         this.ioComplete = true;
         this.notifyAll();
      }

      tlog.release(this);
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("ServerCheckpoint: onError() callback: " + this);
      }

   }

   public void onRecovery(TransactionLogger tlog) {
      ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).setLatestServerCheckpoint(tlog, this);
      Iterator i = this.myList.iterator();

      while(i.hasNext()) {
         String coURL = (String)i.next();
         CoordinatorDescriptor cd = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getOrCreate(coURL);
         if (tlog.getMigratedCoordinatorURL() != null) {
            String migratedServer = CoordinatorDescriptor.getServerName(tlog.getMigratedCoordinatorURL());
            if (migratedServer != null) {
               PlatformHelper.getPlatformHelper().addRecoveredServerCheckpointToMigratedTRS(migratedServer, cd);
            }
         }
      }

   }

   void blockingStore(TransactionLogger tlog) {
      this.myList = ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getAllCheckpointServers();
      if (this.myList.size() == 0) {
         ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).setLatestServerCheckpoint(tlog, (ServerCheckpoint)null);
      } else {
         synchronized(this) {
            tlog.store(this);

            try {
               if (!this.ioComplete) {
                  this.wait();
               }
            } catch (InterruptedException var5) {
            }

         }
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      sb.append("ServerCheckpoint={");
      Iterator i = this.myList.iterator();

      while(i.hasNext()) {
         sb.append(i.next().toString());
         if (i.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append("}");
      return sb.toString();
   }

   private ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }
}
