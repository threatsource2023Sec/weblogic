package weblogic.store;

import weblogic.store.io.file.direct.DirectIOManager;

public final class StoreWritePolicy {
   private static final String CACHE_FLUSH_NAME = "Cache-Flush";
   public static final StoreWritePolicy CACHE_FLUSH = new StoreWritePolicy("Cache-Flush");
   private static final String DIRECT_WRITE_NAME = "Direct-Write";
   public static final StoreWritePolicy DIRECT_WRITE = new StoreWritePolicy("Direct-Write");
   private static final String DISABLED_NAME = "Disabled";
   public static final StoreWritePolicy DISABLED = new StoreWritePolicy("Disabled");
   private static final String NON_DURABLE_NAME = "Non-Durable";
   public static final StoreWritePolicy NON_DURABLE = new StoreWritePolicy("Non-Durable");
   private static final String DIRECT_WRITE_WITH_CACHE_NAME = "Direct-Write-With-Cache";
   public static final StoreWritePolicy DIRECT_WRITE_WITH_CACHE = new StoreWritePolicy("Direct-Write-With-Cache");
   private final String policyName;
   private static boolean is64 = Integer.getInteger("sun.arch.data.model", 32) >= 64;
   private static boolean isWin2008 = System.getProperty("os.name").toLowerCase().startsWith("windows server 2008");

   private StoreWritePolicy(String name) {
      this.policyName = name;
   }

   public static StoreWritePolicy getPolicy(String name) throws PersistentStoreException {
      if (name != null && !name.equalsIgnoreCase("Direct-Write")) {
         if (name.equalsIgnoreCase("Direct-Write-With-Cache")) {
            return DIRECT_WRITE_WITH_CACHE;
         } else if (name.equalsIgnoreCase("Cache-Flush")) {
            return CACHE_FLUSH;
         } else if (name.equalsIgnoreCase("Disabled")) {
            return DISABLED;
         } else if (name.equalsIgnoreCase("Non-Durable")) {
            return NON_DURABLE;
         } else {
            throw new PersistentStoreException(StoreLogger.logInvalidWritePolicyLoggable(name));
         }
      } else {
         return DIRECT_WRITE;
      }
   }

   public String toString() {
      return this.policyName;
   }

   public boolean equals(Object policy) {
      if (policy != null && policy instanceof StoreWritePolicy) {
         if (this == policy) {
            return true;
         } else {
            String otherName = ((StoreWritePolicy)policy).getName();
            if (this.policyName == null) {
               return otherName == null;
            } else {
               return otherName == null ? false : this.policyName.equalsIgnoreCase(otherName);
            }
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.policyName != null ? this.policyName.hashCode() : 0;
   }

   public String getName() {
      return this.policyName;
   }

   public boolean unforced() {
      return this == NON_DURABLE || this == DISABLED;
   }

   public boolean genuineMemoryMap() {
      return DirectIOManager.getManager().nativeFileCodeAvailable() && this.unforced() && (!is64 || !isWin2008);
   }

   public boolean mappedRead() {
      return DirectIOManager.getManager().nativeFileCodeAvailable() && (this.unforced() || this == DIRECT_WRITE_WITH_CACHE);
   }

   public boolean writeExplicit() {
      return !this.genuineMemoryMap();
   }

   public boolean schedulerNeeded() {
      return this == DIRECT_WRITE_WITH_CACHE || this == DIRECT_WRITE || this == CACHE_FLUSH;
   }

   public static StoreWritePolicy getDefault() {
      return DIRECT_WRITE;
   }

   public boolean synchronous() {
      return this == DIRECT_WRITE_WITH_CACHE || this == DIRECT_WRITE;
   }

   public boolean configurable() {
      return this != NON_DURABLE;
   }

   public boolean usesCacheFile() {
      return this == DIRECT_WRITE_WITH_CACHE;
   }
}
