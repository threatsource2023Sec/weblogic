package org.apache.openjpa.kernel;

import java.util.BitSet;
import org.apache.openjpa.enhance.FieldManager;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.meta.ClassMetaData;

public interface OpenJPAStateManager extends StateManager, FieldManager {
   int SET_USER = 0;
   int SET_REMOTE = 1;
   int SET_ATTACH = 2;

   void initialize(Class var1, PCState var2);

   void load(FetchConfiguration var1);

   Object getManagedInstance();

   PersistenceCapable getPersistenceCapable();

   ClassMetaData getMetaData();

   OpenJPAStateManager getOwner();

   int getOwnerIndex();

   boolean isEmbedded();

   boolean isFlushed();

   boolean isFlushedDirty();

   boolean isProvisional();

   BitSet getLoaded();

   BitSet getDirty();

   BitSet getFlushed();

   BitSet getUnloaded(FetchConfiguration var1);

   Object newProxy(int var1);

   Object newFieldProxy(int var1);

   boolean isDefaultValue(int var1);

   StoreContext getContext();

   PCState getPCState();

   Object getId();

   Object getObjectId();

   void setObjectId(Object var1);

   boolean assignObjectId(boolean var1);

   Object getLock();

   void setLock(Object var1);

   Object getVersion();

   void setVersion(Object var1);

   void setNextVersion(Object var1);

   boolean isVersionUpdateRequired();

   boolean isVersionCheckRequired();

   Object getImplData();

   Object setImplData(Object var1, boolean var2);

   boolean isImplDataCacheable();

   Object getImplData(int var1);

   Object setImplData(int var1, Object var2);

   boolean isImplDataCacheable(int var1);

   Object getIntermediate(int var1);

   void setIntermediate(int var1, Object var2);

   boolean fetchBoolean(int var1);

   byte fetchByte(int var1);

   char fetchChar(int var1);

   double fetchDouble(int var1);

   float fetchFloat(int var1);

   int fetchInt(int var1);

   long fetchLong(int var1);

   Object fetchObject(int var1);

   short fetchShort(int var1);

   String fetchString(int var1);

   Object fetch(int var1);

   Object fetchField(int var1, boolean var2);

   Object fetchInitialField(int var1);

   void storeBoolean(int var1, boolean var2);

   void storeByte(int var1, byte var2);

   void storeChar(int var1, char var2);

   void storeDouble(int var1, double var2);

   void storeFloat(int var1, float var2);

   void storeInt(int var1, int var2);

   void storeLong(int var1, long var2);

   void storeObject(int var1, Object var2);

   void storeShort(int var1, short var2);

   void storeString(int var1, String var2);

   void store(int var1, Object var2);

   void storeField(int var1, Object var2);

   void dirty(int var1);

   void removed(int var1, Object var2, boolean var3);

   boolean beforeRefresh(boolean var1);

   void setRemote(int var1, Object var2);
}
