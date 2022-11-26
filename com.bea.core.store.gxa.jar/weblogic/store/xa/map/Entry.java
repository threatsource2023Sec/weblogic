package weblogic.store.xa.map;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.common.StoreDebug;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAOperationWrapper;
import weblogic.store.gxa.GXATraceLogger;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.gxa.GXid;

final class Entry implements GXAOperation {
   private static final int NO_FLAGS = 0;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 65535;
   static final int PUT = 1;
   static final int TXPUT = 2;
   static final int TXREMOVE = 3;
   static final int TXPREPARE = 4;
   static final int TXGETFORUPDATE = 5;
   private int type;
   private long seqNo;
   private Object key;
   private Object value;
   private Entry prevUpdateEntry;
   private PersistentHandle handle;
   private PersistentHandle valueHandle;
   private Entry next;
   private GXATransaction gxaTran;
   private GXATraceLogger gxaTrace;
   private GXid gxid;
   private PersistentMapXAImpl pMap;
   private boolean forceRollback;
   private GXAOperationWrapper opWrapper;

   Entry() {
   }

   Entry(PersistentMapXAImpl pMap, int type, Object key, PersistentHandle valueHandle) {
      this.pMap = pMap;
      this.type = type;
      this.key = key;
      this.valueHandle = valueHandle;
      if (type != 1 && type != 2 || key != null && valueHandle != null) {
         if (type == 3 && (key == null || valueHandle != null)) {
            throw new AssertionError();
         } else if (type == 4 && (key != null || valueHandle != null)) {
            throw new AssertionError();
         } else if (type == 5 && (key == null || valueHandle != null)) {
            throw new AssertionError();
         }
      } else {
         throw new AssertionError();
      }
   }

   Entry(PersistentMapXAImpl pMap, int type, Object key, Object value) {
      this.pMap = pMap;
      this.type = type;
      this.key = key;
      this.value = value;
      if (type != 1 && type != 2 || key != null && value != null) {
         if (type == 3 && (key == null || this.valueHandle != null)) {
            throw new AssertionError();
         } else if (type == 4 && (key != null || this.valueHandle != null)) {
            throw new AssertionError();
         } else if (type == 5 && (key == null || this.valueHandle != null)) {
            throw new AssertionError();
         }
      } else {
         throw new AssertionError();
      }
   }

   public synchronized void onInitialize(GXATraceLogger traceLogger, GXATransaction gxaTransaction, GXAOperationWrapper opWrapper) {
      this.gxaTran = gxaTransaction;
      this.gxaTrace = traceLogger;
      this.gxid = this.gxaTran.getGXid();
      this.opWrapper = opWrapper;
      if (this.type != 4) {
         if (this.gxaTran.isRecovered()) {
            this.getPMap().obtainLock(this.gxaTran, this.key);
         } else {
            synchronized(this.getPMap()) {
               String name = this.getPMap().getName();
               Long seqNoL = (Long)this.gxaTran.getProperty(name, "seqNo");
               if (seqNoL != null) {
                  this.seqNo = seqNoL;
               } else {
                  this.seqNo = this.getPMap().nextSequenceNo();
                  this.gxaTran.putProperty(name, "seqNo", new Long(this.seqNo));
                  this.gxaTran.putProperty(name, "map", new HashMap());

                  try {
                     Entry pentry = new Entry(this.getPMap(), 4, (Object)null, (PersistentHandle)null);
                     pentry.seqNo = this.seqNo;
                     this.getPMap().getGXAResource().addNewOperation(this.gxaTran, pentry);
                  } catch (PersistentStoreException var9) {
                     StoreDebug.storeIOLogical.debug("Unexpected exception in GXA", var9);
                     this.forceRollback = true;
                  }
               }

               if (this.type == 5) {
                  this.prevUpdateEntry = this.localGet(this.key);
               } else {
                  this.prevUpdateEntry = this.localPut(this.key, this);
                  if (this.prevUpdateEntry != null) {
                     boolean b = this.prevUpdateEntry.opWrapper.removeFromTransaction();
                     if (!b) {
                        throw new AssertionError();
                     }

                     this.getPMap().releaseLock(this.gxaTran, this.key);
                  }
               }

            }
         }
      }
   }

   public synchronized boolean onPrepare(int pass, boolean isOnePhase) {
      if (this.forceRollback) {
         return false;
      } else {
         if (pass == 1) {
            PersistentStoreTransaction ptx = this.gxaTran.getStoreTransaction();
            if (this.type == 2) {
               this.valueHandle = this.getPMap().getValueConnection().create(ptx, this.value, 0);
            }

            this.handle = this.getPMap().getKeyConnection().create(ptx, this, 0);
         }

         return true;
      }
   }

   public synchronized void onRollback(int pass) {
      if (!this.forceRollback) {
         if (pass == 1) {
            PersistentStoreTransaction ptx = this.gxaTran.getStoreTransaction();
            if (this.type == 2 && this.valueHandle != null) {
               this.getPMap().getValueConnection().delete(ptx, this.valueHandle, 0);
               this.valueHandle = null;
            }

            if (this.handle != null) {
               this.getPMap().getKeyConnection().delete(ptx, this.handle, 0);
               this.handle = null;
            }
         }

         if (pass == 3 && (this.type == 2 || this.type == 3 || this.type == 5)) {
            try {
               this.getPMap().releaseLock(this.gxaTran, this.key);
            } catch (IllegalStateException var3) {
            }
         }

      }
   }

   public synchronized void onCommit(int pass) {
      if (!this.forceRollback) {
         if (pass == 1) {
            PersistentStoreTransaction ptx;
            if (this.type != 2) {
               ptx = this.gxaTran.getStoreTransaction();
               this.getPMap().getKeyConnection().delete(ptx, this.handle, 0);
            }

            if (this.type == 2 || this.type == 3) {
               ptx = this.gxaTran.getStoreTransaction();
               PersistentHandle handleToRemove = null;
               PersistentHandle valueToRemove = null;
               synchronized(this.getPMap()) {
                  Entry entryToRemove = (Entry)this.getPMap().getKeyMap().get(this.key);
                  if (entryToRemove != null) {
                     handleToRemove = entryToRemove.getHandle();
                     valueToRemove = entryToRemove.getValueHandle();
                  }
               }

               if (valueToRemove != null) {
                  this.getPMap().getValueConnection().delete(ptx, valueToRemove, 0);
               }

               if (handleToRemove != null) {
                  this.getPMap().getKeyConnection().delete(ptx, handleToRemove, 0);
               }
            }
         } else if (pass == 3) {
            if (this.type == 2) {
               synchronized(this.getPMap()) {
                  this.getPMap().getKeyMap().put(this.key, this);
               }
            } else if (this.type == 3) {
               synchronized(this.getPMap()) {
                  this.getPMap().getKeyMap().remove(this.key);
               }
            }

            if (this.type == 2 || this.type == 3 || this.type == 5) {
               this.getPMap().releaseLock(this.gxaTran, this.key);
            }
         }

      }
   }

   public String getDebugPrefix() {
      switch (this.type) {
         case 1:
            return "put";
         case 2:
            return "txput";
         case 3:
            return "txrem";
         case 4:
            return "txprep";
         case 5:
            return "txget";
         default:
            throw new AssertionError();
      }
   }

   synchronized void write(ObjectOutput oo, ObjectHandler delegate) throws IOException {
      oo.writeInt(1);
      oo.writeInt(this.type);
      oo.writeLong(this.seqNo);
      if (this.type == 4) {
         this.gxid.write(oo);
      } else {
         oo.writeBoolean(this.key != null);
         if (this.key != null) {
            delegate.writeObject(oo, this.key);
         }

         oo.writeBoolean(this.valueHandle != null);
         if (this.valueHandle != null) {
            this.valueHandle.writeExternal(oo);
         }

      }
   }

   synchronized void read(ObjectInput oi, ObjectHandler delegate) throws IOException, ClassNotFoundException {
      int flags = oi.readInt();
      int version = flags & '\uffff';
      if (version != 1) {
         throw new IOException("Unexpected version " + version + ", expected " + 1);
      } else {
         this.type = oi.readInt();
         this.seqNo = oi.readLong();
         if (this.type == 4) {
            this.gxid = GXid.read(oi);
         } else {
            if (oi.readBoolean()) {
               this.key = delegate.readObject(oi);
            }

            if (oi.readBoolean()) {
               this.valueHandle = PersistentHandle.read(oi);
            }

         }
      }
   }

   synchronized long getSeqNo() {
      return this.seqNo;
   }

   synchronized PersistentHandle getHandle() {
      return this.handle;
   }

   synchronized Object getKey() {
      return this.key;
   }

   synchronized Object getValue() {
      return this.value;
   }

   synchronized PersistentHandle getValueHandle() {
      return this.valueHandle;
   }

   synchronized Entry getPrevUpdateEntry() {
      return this.prevUpdateEntry;
   }

   synchronized int getType() {
      return this.type;
   }

   synchronized void setGXid(GXid gxid) {
      this.gxid = gxid;
   }

   synchronized void setHandle(PersistentHandle h) {
      this.handle = h;
   }

   synchronized void setNext(Entry next) {
      this.next = next;
   }

   synchronized Entry getNext() {
      return this.next;
   }

   public synchronized GXid getGXid() {
      return this.gxid;
   }

   private Map getLocalMap() {
      return getLocalMap(this.getPMap(), this.gxaTran);
   }

   private static Map getLocalMap(PersistentMapXAImpl pMap, GXATransaction gxaTran) {
      return (Map)gxaTran.getProperty(pMap.getName(), "map");
   }

   private Entry localPut(Object key, Entry value) {
      return (Entry)this.getLocalMap().put(key, value);
   }

   private Entry localGet(Object key) {
      return (Entry)this.getLocalMap().get(key);
   }

   static void localizeKeySet(PersistentMapXAImpl pMap, GXATransaction gxaTran, HashSet keys) {
      Map changes = getLocalMap(pMap, gxaTran);
      if (changes != null) {
         Iterator iter = changes.keySet().iterator();

         while(iter.hasNext()) {
            Entry entry = (Entry)changes.get(iter.next());
            switch (entry.type) {
               case 2:
                  keys.add(entry.getKey());
                  break;
               case 3:
                  keys.remove(entry.getKey());
            }
         }

      }
   }

   static int localizeSize(PersistentMapXAImpl pMap, GXATransaction gxaTran) {
      Map changes = getLocalMap(pMap, gxaTran);
      Map keyMap = pMap.getKeyMap();
      int siz = keyMap.size();
      if (changes == null) {
         return siz;
      } else {
         Iterator iter = changes.keySet().iterator();

         while(iter.hasNext()) {
            Entry entry = (Entry)changes.get(iter.next());
            switch (entry.type) {
               case 2:
                  siz += keyMap.containsKey(entry.getKey()) ? 0 : 1;
                  break;
               case 3:
                  siz -= keyMap.containsKey(entry.getKey()) ? 1 : 0;
            }
         }

         return siz;
      }
   }

   void setPMap(PersistentMapXAImpl pMap) {
      this.pMap = pMap;
   }

   private PersistentMapXAImpl getPMap() {
      return this.pMap;
   }

   public String toString() {
      String keyString = "" + this.key;
      if (keyString.length() > 100) {
         keyString = keyString.substring(0, 100) + "[truncated]";
      }

      String valueString = "" + this.valueHandle;
      if (valueString.length() > 100) {
         valueString = valueString.substring(0, 100) + "[truncated]";
      }

      return this.getDebugPrefix() + " seqNo=" + this.seqNo + " key=" + keyString + " value=" + valueString;
   }
}
