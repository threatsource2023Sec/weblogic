package org.apache.openjpa.enhance;

import java.io.IOException;
import java.io.ObjectOutput;

public interface StateManager {
   int SET_USER = 0;
   int SET_REMOTE = 1;
   int SET_ATTACH = 2;

   Object getGenericContext();

   Object getPCPrimaryKey(Object var1, int var2);

   StateManager replaceStateManager(StateManager var1);

   Object getVersion();

   boolean isDirty();

   boolean isTransactional();

   boolean isPersistent();

   boolean isNew();

   boolean isDeleted();

   boolean isDetached();

   void dirty(String var1);

   Object fetchObjectId();

   boolean serializing();

   boolean writeDetached(ObjectOutput var1) throws IOException;

   void proxyDetachedDeserialized(int var1);

   void accessingField(int var1);

   void settingBooleanField(PersistenceCapable var1, int var2, boolean var3, boolean var4, int var5);

   void settingCharField(PersistenceCapable var1, int var2, char var3, char var4, int var5);

   void settingByteField(PersistenceCapable var1, int var2, byte var3, byte var4, int var5);

   void settingShortField(PersistenceCapable var1, int var2, short var3, short var4, int var5);

   void settingIntField(PersistenceCapable var1, int var2, int var3, int var4, int var5);

   void settingLongField(PersistenceCapable var1, int var2, long var3, long var5, int var7);

   void settingFloatField(PersistenceCapable var1, int var2, float var3, float var4, int var5);

   void settingDoubleField(PersistenceCapable var1, int var2, double var3, double var5, int var7);

   void settingStringField(PersistenceCapable var1, int var2, String var3, String var4, int var5);

   void settingObjectField(PersistenceCapable var1, int var2, Object var3, Object var4, int var5);

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

   boolean replaceBooleanField(PersistenceCapable var1, int var2);

   char replaceCharField(PersistenceCapable var1, int var2);

   byte replaceByteField(PersistenceCapable var1, int var2);

   short replaceShortField(PersistenceCapable var1, int var2);

   int replaceIntField(PersistenceCapable var1, int var2);

   long replaceLongField(PersistenceCapable var1, int var2);

   float replaceFloatField(PersistenceCapable var1, int var2);

   double replaceDoubleField(PersistenceCapable var1, int var2);

   String replaceStringField(PersistenceCapable var1, int var2);

   Object replaceObjectField(PersistenceCapable var1, int var2);
}
