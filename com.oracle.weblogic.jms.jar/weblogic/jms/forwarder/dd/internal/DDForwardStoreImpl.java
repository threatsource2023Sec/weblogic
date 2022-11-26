package weblogic.jms.forwarder.dd.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.forwarder.dd.DDForwardStore;
import weblogic.jms.forwarder.dd.DDInfo;
import weblogic.jms.forwarder.dd.DDLBTable;
import weblogic.jms.forwarder.dd.DDMemberInfo;
import weblogic.jms.store.JMSObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.xa.PersistentStoreXA;

public class DDForwardStoreImpl implements DDForwardStore {
   private PersistentStoreXA persistentStore;
   private String name;
   private DDInfo ddInfo;
   private PersistentStoreConnection storeConnection;
   private DDLBTable ddLBTable;
   private PersistentHandle handle;
   private static final int NO_FLAGS = 0;
   private boolean poisoned;

   public DDForwardStoreImpl(String name, DDInfo ddInfo, PersistentStoreXA persistentStore) throws JMSException {
      this.name = name;
      this.persistentStore = persistentStore;
      this.ddInfo = ddInfo;

      try {
         this.storeConnection = persistentStore.createConnection(name, new JMSObjectHandler());
         this.open();
      } catch (PersistentStoreException var6) {
         JMSException jmse = new JMSException(var6.getMessage());
         jmse.setLinkedException(var6);
         throw jmse;
      }
   }

   private void open() throws JMSException {
      this.recover();
   }

   private void recover() throws JMSException {
      DDLBTable ddLBTableOnBoot = null;

      PersistentStoreRecord rec;
      try {
         for(PersistentStoreConnection.Cursor cursor = this.storeConnection.createCursor(32); (rec = cursor.next()) != null; ddLBTableOnBoot = (DDLBTable)rec.getData()) {
            this.handle = rec.getHandle();
         }
      } catch (PersistentStoreException var10) {
         JMSException jmse = new JMSException(var10.getMessage());
         jmse.setLinkedException(var10);
         throw jmse;
      }

      this.ddLBTable = new DDLBTableImpl(this.name, this.ddInfo);
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("ddLBTableOnBoot = " + ddLBTableOnBoot);
      }

      if (ddLBTableOnBoot != null) {
         HashMap failedDDMemberInfos = ddLBTableOnBoot.getFailedDDMemberInfosBySeqNum();
         List failedDestinations = ddLBTableOnBoot.getFailedDDMemberInfos();
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("DDForwardStoreImpl.recover() failedDDMemberInfos = " + failedDDMemberInfos + "\n failedDestinations = " + failedDestinations);
         }

         Iterator iterator = failedDDMemberInfos.values().iterator();

         while(iterator.hasNext()) {
            DDMemberInfo ddMemberInfo = (DDMemberInfo)iterator.next();
            DestinationImpl destination = ddMemberInfo.getDestination();
            if (destination != null) {
               destination.markStale();
            }
         }

         DDMemberInfo[] ddMemberInfoInDoubt = ddLBTableOnBoot.getDDMemberInfos();
         if (ddMemberInfoInDoubt != null) {
            for(int i = 0; i < ddMemberInfoInDoubt.length; ++i) {
               DDMemberInfo ddMemberInfo = ddMemberInfoInDoubt[i];
               DestinationImpl destination = ddMemberInfo.getDestination();
               if (destination != null) {
                  destination.markStale();
               }
            }
         }

         ddLBTableOnBoot.removeDDMemberInfos();
         ddLBTableOnBoot.removeInDoubtDDMemberInfos();
         this.ddLBTable.setFailedDDMemberInfosBySeqNum(failedDDMemberInfos);
         this.ddLBTable.setFailedDDMemberInfos(failedDestinations);
         this.ddLBTable.addInDoubtDDMemberInfos(ddMemberInfoInDoubt);
      }

   }

   public PersistentStoreXA getStore() {
      return this.persistentStore;
   }

   public void addOrUpdateDDLBTable(DDLBTable ddlbTable) throws PersistentStoreException {
      PersistentStoreTransaction ptx = this.persistentStore.begin();
      if (this.handle == null) {
         this.handle = this.storeConnection.create(ptx, ddlbTable, 0);
      } else {
         this.storeConnection.update(ptx, this.handle, ddlbTable, 0);
      }

      ptx.commit();
   }

   public void removeDDLBTable() throws PersistentStoreException {
      if (this.handle != null) {
         PersistentStoreTransaction ptx = this.persistentStore.begin();
         this.storeConnection.delete(ptx, this.handle, 0);
         ptx.commit();
         this.handle = null;
      }
   }

   public void close() {
      this.storeConnection.close();
   }

   public void poisoned() {
      this.poisoned = true;
   }

   public boolean isPoisoned() {
      return this.poisoned;
   }

   public DDLBTable getDDLBTable() {
      return this.ddLBTable;
   }
}
