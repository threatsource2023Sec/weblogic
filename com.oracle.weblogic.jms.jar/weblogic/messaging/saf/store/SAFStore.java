package weblogic.messaging.saf.store;

import java.util.HashMap;
import java.util.Iterator;
import weblogic.common.CompletionRequest;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.internal.Agent;
import weblogic.messaging.saf.internal.ReceivingAgent;
import weblogic.messaging.saf.internal.SendingAgent;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;

public class SAFStore {
   private PersistentHandle SAStoreHandle;
   private PersistentHandle RAStoreHandle;
   private final HashMap conversationStoreHandles;
   private final HashMap conversationInfos;
   protected SendingAgent sendingAgent;
   protected ReceivingAgent receivingAgent;
   private final String agentName;
   private PersistentStore store;
   private final String storeName;
   private boolean recovered;
   private static final int NO_FLAGS = 0;
   private static final String CONN_NAME_PREFIX = "weblogic.messaging.";
   private static final SAFObjectHandler SAF_OBJECT_HANDLER = new SAFObjectHandler();
   private PersistentStoreConnection storeConnectionNotForMaps;

   SAFStore(String name, String agentName) throws SAFException {
      this.conversationStoreHandles = new HashMap();
      this.conversationInfos = new HashMap();
      this.storeName = name;
      this.agentName = agentName;
      this.initPersistentStore();
   }

   SAFStore(String name, String agentName, boolean isRA) throws SAFException {
      this(name, agentName);
      this.open(isRA, agentName);
      if (SAFDebug.SAFStore.isDebugEnabled()) {
         SAFDebug.SAFStore.debug(" == SAFStore Created for Agent " + agentName);
      }

   }

   private void initPersistentStore() throws SAFException {
      PersistentStoreManager psm = PersistentStoreManager.getManager();
      if (this.storeName != null) {
         this.store = psm.getStore(this.storeName);
         if (this.store == null) {
            throw new AssertionError("Persistent Store Service should have already created a PersistentStore  for Name = <" + this.storeName + " > before SAFService is booted");
         }
      } else {
         this.store = psm.getDefaultStore();
         if (this.store == null) {
            throw new SAFException("The default persistent store does not exist");
         }

         if (SAFDebug.SAFStore.isDebugEnabled()) {
            SAFDebug.SAFStore.debug("Agent using the server's default store");
         }
      }

   }

   public String getStoreName() {
      return this.storeName;
   }

   public String getEffectiveStoreName() {
      if (this.storeName != null) {
         return this.storeName;
      } else {
         return this.store != null ? this.store.getName() : null;
      }
   }

   protected String getAgentName() {
      return this.agentName;
   }

   protected HashMap getConversationInfos() {
      return this.conversationInfos;
   }

   protected HashMap getConversationStoreHandles() {
      return this.conversationStoreHandles;
   }

   public String toString() {
      return "<SAFStore> : storeName = " + this.storeName + " agentName =" + this.agentName;
   }

   public synchronized SendingAgent getSendingAgent() {
      return this.sendingAgent;
   }

   public synchronized ReceivingAgent getReceivingAgent() {
      return this.receivingAgent;
   }

   protected void open(boolean isRA, String effectiveAgentName) throws SAFStoreException {
      try {
         this.storeConnectionNotForMaps = this.store.createConnection("weblogic.messaging." + effectiveAgentName, SAF_OBJECT_HANDLER);
      } catch (PersistentStoreException var7) {
         throw new SAFStoreException(this, var7);
      }

      SAFStoreRecord nextRecoverRecord;
      for(SAFStoreRecord recoverRecord = this.recover(); recoverRecord != null; recoverRecord = nextRecoverRecord) {
         nextRecoverRecord = recoverRecord.getNext();
         recoverRecord.setNext((SAFStoreRecord)null);

         Object restoredObject;
         try {
            restoredObject = recoverRecord.getStoreObject();
         } catch (PersistentStoreException var8) {
            continue;
         }

         if (SAFDebug.SAFStore.isDebugEnabled()) {
            SAFDebug.SAFStore.debug("*********** ***** <SAFStoreRecord> state= class=" + restoredObject.getClass().getName() + " code=" + SAF_OBJECT_HANDLER.getCode(restoredObject) + " obj=" + restoredObject);
         }

         switch (SAF_OBJECT_HANDLER.getCode(restoredObject)) {
            case 15:
               this.sendingAgent = (SendingAgent)restoredObject;
               break;
            case 16:
               this.receivingAgent = (ReceivingAgent)restoredObject;
               break;
            case 17:
               SAFConversationInfo conversationInfo = (SAFConversationInfo)restoredObject;
               this.conversationInfos.put(conversationInfo.getConversationName(), conversationInfo);
               this.conversationStoreHandles.put(conversationInfo, recoverRecord.getHandle());
         }
      }

      if (this.sendingAgent != null) {
         this.sendingAgent.setConversationInfosFromStore(this.conversationInfos);
      }

      if (this.receivingAgent != null) {
         this.receivingAgent.setConversationInfosFromStore(this.conversationInfos);
      }

   }

   private SAFStoreRecord recover() throws SAFStoreException {
      if (this.recovered) {
         return null;
      } else {
         this.recovered = true;
         SAFStoreRecord head = null;
         SAFStoreRecord tail = null;

         try {
            PersistentStoreConnection.Cursor cursor = this.storeConnectionNotForMaps.createCursor(0);

            PersistentStoreRecord rec;
            while((rec = cursor.next()) != null) {
               SAFStoreRecord se = new SAFStoreRecord(rec);
               if (tail == null) {
                  tail = se;
                  head = se;
               } else {
                  tail.setNext(se);
                  tail = se;
               }
            }

            return head;
         } catch (PersistentStoreException var6) {
            SAFStoreException ioe = new SAFStoreException(this, var6);
            ioe.initCause(var6);
            throw ioe;
         }
      }
   }

   public void close() {
      synchronized(this.conversationInfos) {
         this.conversationInfos.clear();
         this.conversationStoreHandles.clear();
      }

      if (this.storeConnectionNotForMaps != null) {
         this.storeConnectionNotForMaps.close();
      }

   }

   public void clean() throws SAFStoreException {
      this.delete(this.SAStoreHandle);
      this.delete(this.RAStoreHandle);
      synchronized(this.conversationInfos) {
         Iterator itr = this.conversationStoreHandles.values().iterator();

         while(itr.hasNext()) {
            PersistentHandle safConversationInfoHandle = (PersistentHandle)itr.next();
            this.delete(safConversationInfoHandle);
         }

      }
   }

   public void addAgent(Agent agent) throws SAFStoreException {
      if (SAFDebug.SAFStore.isDebugEnabled()) {
         SAFDebug.SAFStore.debug(" == BEFORE ADDING Agent " + agent.getName() + " and store's agentName = " + this.agentName);
      }

      PersistentHandle agentStoreHandle = this.storeAgent(agent);
      if (agent instanceof SendingAgent) {
         this.sendingAgent = (SendingAgent)agent;
         this.SAStoreHandle = agentStoreHandle;
      } else if (agent instanceof ReceivingAgent) {
         this.receivingAgent = (ReceivingAgent)agent;
         this.RAStoreHandle = agentStoreHandle;
      }

      if (SAFDebug.SAFStore.isDebugEnabled()) {
         SAFDebug.SAFStore.debug(" == AFTER ADDING Agent " + agent);
      }

   }

   public PersistentHandle addConversationInfo(SAFConversationInfo conversationInfo) throws SAFStoreException {
      synchronized(this.conversationInfos) {
         PersistentHandle handle;
         if (this.conversationInfos.get(conversationInfo.getConversationName()) == null) {
            handle = this.storeSync(conversationInfo);
            this.conversationInfos.put(conversationInfo.getConversationName(), conversationInfo);
            this.conversationStoreHandles.put(conversationInfo, handle);
         } else {
            handle = (PersistentHandle)this.conversationStoreHandles.get(conversationInfo);
            if (handle != null) {
               this.updateSync(handle, conversationInfo);
            }
         }

         return handle;
      }
   }

   public PersistentHandle removeConversationInfo(SAFConversationInfo conversationInfo) throws SAFStoreException {
      PersistentHandle handle = null;
      synchronized(this.conversationInfos) {
         if (this.conversationInfos.remove(conversationInfo.getConversationName()) != null) {
            handle = (PersistentHandle)this.conversationStoreHandles.remove(conversationInfo.getConversationName());
            if (handle != null) {
               this.delete(handle);
            }
         }

         return handle;
      }
   }

   private PersistentHandle storeAgent(Agent agent) throws SAFStoreException {
      return this.storeSync(agent);
   }

   private PersistentHandle storeInternal(Object object, CompletionRequest cr) {
      PersistentStoreTransaction ptx = this.store.begin();
      PersistentHandle handle = this.storeConnectionNotForMaps.create(ptx, object, 0);
      ptx.commit(cr);
      return handle;
   }

   private void updateInternal(PersistentHandle handle, Object object, CompletionRequest cr) {
      PersistentStoreTransaction ptx = this.store.begin();
      this.storeConnectionNotForMaps.update(ptx, handle, object, 0);
      ptx.commit(cr);
   }

   private PersistentHandle storeSync(Object object) throws SAFStoreException {
      CompletionRequest cr = new CompletionRequest();
      PersistentHandle handle = this.storeInternal(object, cr);

      try {
         cr.getResult();
         return handle;
      } catch (Throwable var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof Error) {
            throw (Error)var5;
         } else {
            throw new SAFStoreException(this, var5);
         }
      }
   }

   protected void updateSync(PersistentHandle handle, Object object) throws SAFStoreException {
      CompletionRequest cr = new CompletionRequest();
      this.updateInternal(handle, object, cr);

      try {
         cr.getResult();
      } catch (Throwable var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof Error) {
            throw (Error)var5;
         } else {
            throw new SAFStoreException(this, var5);
         }
      }
   }

   protected void delete(PersistentHandle handle) throws SAFStoreException {
      PersistentStoreTransaction ptx = this.store.begin();
      this.storeConnectionNotForMaps.delete(ptx, handle, 0);

      try {
         ptx.commit();
      } catch (PersistentStoreException var4) {
         throw new SAFStoreException(this, var4);
      }
   }

   protected PersistentStoreConnection getConnection(String name) {
      return this.store.getConnection("weblogic.messaging." + name);
   }

   protected boolean hasConversations() {
      return this.conversationInfos.size() != 0;
   }

   protected boolean hasConversations(String alternativeAgentName) throws SAFStoreException {
      PersistentStoreConnection storeConnection = null;

      try {
         storeConnection = this.store.createConnection("weblogic.messaging." + alternativeAgentName, SAF_OBJECT_HANDLER);
         PersistentStoreConnection.Cursor cursor = storeConnection.createCursor(0);

         PersistentStoreRecord storeRecord;
         while((storeRecord = cursor.next()) != null) {
            SAFStoreRecord rec = new SAFStoreRecord(storeRecord);

            Object restoredObject;
            try {
               restoredObject = rec.getStoreObject();
            } catch (PersistentStoreException var12) {
               if (SAFDebug.SAFStore.isDebugEnabled()) {
                  SAFDebug.SAFStore.debug("*********** <SAFStoreRecord> got an exception " + var12.getMessage());
               }
               continue;
            }

            if (SAF_OBJECT_HANDLER.getCode(restoredObject) == 17) {
               boolean var7 = true;
               return var7;
            }
         }

         boolean var15 = false;
         return var15;
      } catch (PersistentStoreException var13) {
         throw new SAFStoreException(this, var13);
      } finally {
         if (storeConnection != null) {
            storeConnection.close();
         }

      }
   }
}
