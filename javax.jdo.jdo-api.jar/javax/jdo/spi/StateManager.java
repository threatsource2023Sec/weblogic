package javax.jdo.spi;

import javax.jdo.PersistenceManager;

public interface StateManager {
   byte replacingFlags(PersistenceCapable var1);

   StateManager replacingStateManager(PersistenceCapable var1, StateManager var2);

   boolean isDirty(PersistenceCapable var1);

   boolean isTransactional(PersistenceCapable var1);

   boolean isPersistent(PersistenceCapable var1);

   boolean isNew(PersistenceCapable var1);

   boolean isDeleted(PersistenceCapable var1);

   PersistenceManager getPersistenceManager(PersistenceCapable var1);

   void makeDirty(PersistenceCapable var1, String var2);

   Object getObjectId(PersistenceCapable var1);

   Object getTransactionalObjectId(PersistenceCapable var1);

   Object getVersion(PersistenceCapable var1);

   boolean isLoaded(PersistenceCapable var1, int var2);

   void preSerialize(PersistenceCapable var1);

   boolean getBooleanField(PersistenceCapable var1, int var2, boolean var3);

   char getCharField(PersistenceCapable var1, int var2, char var3);

   byte getByteField(PersistenceCapable var1, int var2, byte var3);

   short getShortField(PersistenceCapable var1, int var2, short var3);

   int getIntField(PersistenceCapable var1, int var2, int var3);

   long getLongField(PersistenceCapable var1, int var2, long var3);

   float getFloatField(PersistenceCapable var1, int var2, float var3);

   double getDoubleField(PersistenceCapable var1, int var2, double var3);

   String getStringField(PersistenceCapable var1, int var2, String var3);

   Object getObjectField(PersistenceCapable var1, int var2, Object var3);

   void setBooleanField(PersistenceCapable var1, int var2, boolean var3, boolean var4);

   void setCharField(PersistenceCapable var1, int var2, char var3, char var4);

   void setByteField(PersistenceCapable var1, int var2, byte var3, byte var4);

   void setShortField(PersistenceCapable var1, int var2, short var3, short var4);

   void setIntField(PersistenceCapable var1, int var2, int var3, int var4);

   void setLongField(PersistenceCapable var1, int var2, long var3, long var5);

   void setFloatField(PersistenceCapable var1, int var2, float var3, float var4);

   void setDoubleField(PersistenceCapable var1, int var2, double var3, double var5);

   void setStringField(PersistenceCapable var1, int var2, String var3, String var4);

   void setObjectField(PersistenceCapable var1, int var2, Object var3, Object var4);

   void providedBooleanField(PersistenceCapable var1, int var2, boolean var3);

   void providedCharField(PersistenceCapable var1, int var2, char var3);

   void providedByteField(PersistenceCapable var1, int var2, byte var3);

   void providedShortField(PersistenceCapable var1, int var2, short var3);

   void providedIntField(PersistenceCapable var1, int var2, int var3);

   void providedLongField(PersistenceCapable var1, int var2, long var3);

   void providedFloatField(PersistenceCapable var1, int var2, float var3);

   void providedDoubleField(PersistenceCapable var1, int var2, double var3);

   void providedStringField(PersistenceCapable var1, int var2, String var3);

   void providedObjectField(PersistenceCapable var1, int var2, Object var3);

   boolean replacingBooleanField(PersistenceCapable var1, int var2);

   char replacingCharField(PersistenceCapable var1, int var2);

   byte replacingByteField(PersistenceCapable var1, int var2);

   short replacingShortField(PersistenceCapable var1, int var2);

   int replacingIntField(PersistenceCapable var1, int var2);

   long replacingLongField(PersistenceCapable var1, int var2);

   float replacingFloatField(PersistenceCapable var1, int var2);

   double replacingDoubleField(PersistenceCapable var1, int var2);

   String replacingStringField(PersistenceCapable var1, int var2);

   Object replacingObjectField(PersistenceCapable var1, int var2);

   Object[] replacingDetachedState(Detachable var1, Object[] var2);
}
