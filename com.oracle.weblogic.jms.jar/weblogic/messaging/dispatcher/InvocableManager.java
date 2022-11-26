package weblogic.messaging.dispatcher;

import java.util.Map;
import weblogic.jms.common.JMSDebug;
import weblogic.messaging.ID;

public abstract class InvocableManager {
   private final Map[] INVOCABLE_MAPS;
   private final Map[] SUPERSET_MAPS;
   private final String[] INVOCABLE_STRINGS;
   private int[] invocablesHighCount;
   private int[] invocablesTotalCount;
   private final Invocable[] managers;
   private final Invocable[] singletonManagers;
   public static final int INVOCABLE_TYPE_MASK = 255;
   public static final int INVOCABLE_METHOD_MASK = 16776960;
   public static final int DISPATCHER_MANAGER = 0;
   public static final int DSP_HANDSHAKE_HELLO = 15872;
   public static final int DSP_HANDSHAKE_METHOD_MASK = 16776960;

   protected InvocableManager(Map[] INVOCABLE_MAPS, Map[] SUPERSET_MAPS, String[] INVOCABLE_STRINGS, int[] invocablesHighCount, int[] invocablesTotalCount, Invocable[] managers, Invocable[] singletonManagers) {
      this.INVOCABLE_MAPS = INVOCABLE_MAPS;
      this.SUPERSET_MAPS = SUPERSET_MAPS;
      this.INVOCABLE_STRINGS = INVOCABLE_STRINGS;
      this.invocablesHighCount = invocablesHighCount;
      this.invocablesTotalCount = invocablesTotalCount;
      this.managers = managers;
      this.singletonManagers = singletonManagers;
      if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
         JMSDebug.JMSInvocableVerbose.debug("InvocableManager.<init>: " + this.toString() + " hashCode: " + this.hashCode());
      }

   }

   public void invocableAdd(int invocableType, Invocable invocable) throws Exception {
      Map invocableMap = this.INVOCABLE_MAPS[invocableType];
      Map supersetMap = this.SUPERSET_MAPS[invocableType];
      if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
         JMSDebug.JMSInvocableVerbose.debug("InvocableManager.invocableAdd: invocableManager: " + this.toString() + " manager.hashCode: " + this.hashCode() + " invocableType:" + invocableType + " " + this.INVOCABLE_STRINGS[invocableType] + " invocableId: " + invocable.getId());
      }

      assert invocableMap != null && supersetMap != null : "Attempting to cache uncachable " + this.INVOCABLE_STRINGS[invocableType] + "[" + invocableType + "]";

      synchronized(invocableMap) {
         Invocable oldSupersetInvocable = (Invocable)supersetMap.put(invocable.getId(), invocable);
         Invocable oldInvocable = (Invocable)invocableMap.put(invocable.getId(), invocable);
         if (oldInvocable == null && oldSupersetInvocable == null) {
            if (invocableMap.size() > this.invocablesHighCount[invocableType]) {
               this.invocablesHighCount[invocableType] = invocableMap.size();
            }

            int var10002 = this.invocablesTotalCount[invocableType]++;
            return;
         }

         if (invocable == oldInvocable && invocable == oldSupersetInvocable) {
            return;
         }

         invocableMap.put(oldInvocable.getId(), oldInvocable);
         supersetMap.put(oldInvocable.getId(), oldSupersetInvocable);
      }

      throw new Exception(this.INVOCABLE_STRINGS[invocableType] + "[" + invocableType + "] already exists");
   }

   public Invocable invocableFind(int invocableType, ID invocableId) throws Exception {
      Map invocableMap = this.INVOCABLE_MAPS[invocableType];
      if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
         JMSDebug.JMSInvocableVerbose.debug("InvocableManager.invocableFind: invocableManager: " + this.toString() + " manager.hashCode: " + this.hashCode() + " invocableType:" + invocableType + " " + this.INVOCABLE_STRINGS[invocableType] + " invocableId: " + invocableId);
      }

      if (invocableMap != null) {
         synchronized(invocableMap) {
            Invocable invocable = (Invocable)invocableMap.get(invocableId);
            if (invocable != null) {
               if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
                  JMSDebug.JMSInvocableVerbose.debug("Found " + this.INVOCABLE_STRINGS[invocableType] + "(" + invocableId + "): InvocableManager:" + this);
               }

               return invocable;
            }
         }
      } else {
         if (this.isManager(invocableType)) {
            return this.managers[invocableType];
         }

         if (this.isSingletonManager(invocableType)) {
            return this.singletonManagers[invocableType];
         }
      }

      Exception invocableNotFound = new Exception(this.INVOCABLE_STRINGS[invocableType] + "[" + invocableType + "] not found");
      if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
         JMSDebug.JMSInvocableVerbose.debug(this.INVOCABLE_STRINGS[invocableType] + "(" + invocableId + ") not found: InvocableManager:" + this, invocableNotFound);
      }

      throw invocableNotFound;
   }

   public Invocable invocableRemove(int invocableType, ID invocableId) {
      Map invocableMap = this.INVOCABLE_MAPS[invocableType];
      Map supersetMap = this.SUPERSET_MAPS[invocableType];
      if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
         JMSDebug.JMSInvocableVerbose.debug("InvocableManager.invocableRemove: invocableManager: " + this.toString() + " manager.hashCode: " + this.hashCode() + " invocableType:" + invocableType + " " + this.INVOCABLE_STRINGS[invocableType] + " invocableId: " + invocableId);
      }

      assert invocableMap != null && supersetMap != null : "Attempting to remove uncachable " + this.INVOCABLE_STRINGS[invocableType] + "[" + invocableType + "]";

      synchronized(invocableMap) {
         Invocable removedSupersetInvocable = (Invocable)supersetMap.remove(invocableId);
         Invocable removedInvocable = (Invocable)invocableMap.remove(invocableId);
         if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
            if (removedSupersetInvocable != null) {
               JMSDebug.JMSInvocableVerbose.debug("superset " + this.INVOCABLE_STRINGS[invocableType] + "(" + invocableId + "): invocable removed");
            } else {
               JMSDebug.JMSInvocableVerbose.debug("superset " + this.INVOCABLE_STRINGS[invocableType] + "(" + invocableId + "): invocable not found");
            }

            if (removedInvocable != null) {
               JMSDebug.JMSInvocableVerbose.debug(this.INVOCABLE_STRINGS[invocableType] + "(" + invocableId + "): invocable removed");
            } else {
               JMSDebug.JMSInvocableVerbose.debug(this.INVOCABLE_STRINGS[invocableType] + "(" + invocableId + "): invocable not found");
            }
         }

         return removedInvocable;
      }
   }

   public Map getInvocableMap(int invocableType) {
      return this.INVOCABLE_MAPS[invocableType];
   }

   public int getInvocablesCurrentCount(int invocableType) {
      return this.INVOCABLE_MAPS[invocableType].size();
   }

   public int getInvocablesHighCount(int invocableType) {
      return this.invocablesHighCount[invocableType];
   }

   public int getInvocablesTotalCount(int invocableType) {
      return this.invocablesTotalCount[invocableType];
   }

   protected abstract boolean isManager(int var1);

   protected abstract boolean isSingletonManager(int var1);

   public void addManager(int invocableType, Invocable invocable) {
      this.managers[invocableType] = invocable;
   }

   public void addSingletonManager(int invocableType, Invocable invocable) {
      this.singletonManagers[invocableType] = invocable;
   }
}
