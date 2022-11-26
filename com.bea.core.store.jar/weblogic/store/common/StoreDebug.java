package weblogic.store.common;

import weblogic.diagnostics.debug.DebugLogger;

public class StoreDebug {
   public static final DebugLogger store = DebugLogger.getDebugLogger("DebugStore");
   public static final DebugLogger storeAdmin = DebugLogger.getDebugLogger("DebugStoreAdmin");
   public static final DebugLogger storeIOPhysical = DebugLogger.getDebugLogger("DebugStoreIOPhysical");
   public static final DebugLogger storeIOPhysicalVerbose = DebugLogger.getDebugLogger("DebugStoreIOPhysicalVerbose");
   public static final DebugLogger storeIOLogical = DebugLogger.getDebugLogger("DebugStoreIOLogical");
   public static final DebugLogger storeIOLogicalBoot = DebugLogger.getDebugLogger("DebugStoreIOLogicalBoot");
   public static final DebugLogger storeXA = DebugLogger.getDebugLogger("DebugStoreXA");
   public static final DebugLogger storeXAVerbose = DebugLogger.getDebugLogger("DebugStoreXAVerbose");
   public static final DebugLogger cacheDebug = DebugLogger.getDebugLogger("DebugStoreCache");
   public static final DebugLogger persistentStoreManager = DebugLogger.getDebugLogger("DebugPersistentStoreManager");
   public static final DebugLogger defaultStoreVerbose = DebugLogger.getDebugLogger("DebugDefaultStoreVerbose");
   public static final DebugLogger ejbTimerStore = DebugLogger.getDebugLogger("DebugEjbTimerStore");
   public static final DebugLogger storeRCM = DebugLogger.getDebugLogger("DebugStoreRCM");
   public static final DebugLogger storeRCMVerbose = DebugLogger.getDebugLogger("DebugStoreRCMVerbose");
   public static final DebugLogger storeConnectionCaching = DebugLogger.getDebugLogger("DebugStoreConnectionCaching");
}
