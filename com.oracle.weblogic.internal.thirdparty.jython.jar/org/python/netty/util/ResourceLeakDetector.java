package org.python.netty.util;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentMap;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class ResourceLeakDetector {
   private static final String PROP_LEVEL_OLD = "org.python.netty.leakDetectionLevel";
   private static final String PROP_LEVEL = "org.python.netty.leakDetection.level";
   private static final Level DEFAULT_LEVEL;
   private static final String PROP_MAX_RECORDS = "org.python.netty.leakDetection.maxRecords";
   private static final int DEFAULT_MAX_RECORDS = 4;
   private static final int MAX_RECORDS;
   private static Level level;
   private static final InternalLogger logger;
   static final int DEFAULT_SAMPLING_INTERVAL = 128;
   private final ConcurrentMap allLeaks;
   private final ReferenceQueue refQueue;
   private final ConcurrentMap reportedLeaks;
   private final String resourceType;
   private final int samplingInterval;
   private static final String[] STACK_TRACE_ELEMENT_EXCLUSIONS;

   /** @deprecated */
   @Deprecated
   public static void setEnabled(boolean enabled) {
      setLevel(enabled ? ResourceLeakDetector.Level.SIMPLE : ResourceLeakDetector.Level.DISABLED);
   }

   public static boolean isEnabled() {
      return getLevel().ordinal() > ResourceLeakDetector.Level.DISABLED.ordinal();
   }

   public static void setLevel(Level level) {
      if (level == null) {
         throw new NullPointerException("level");
      } else {
         ResourceLeakDetector.level = level;
      }
   }

   public static Level getLevel() {
      return level;
   }

   /** @deprecated */
   @Deprecated
   public ResourceLeakDetector(Class resourceType) {
      this(StringUtil.simpleClassName(resourceType));
   }

   /** @deprecated */
   @Deprecated
   public ResourceLeakDetector(String resourceType) {
      this((String)resourceType, 128, Long.MAX_VALUE);
   }

   /** @deprecated */
   @Deprecated
   public ResourceLeakDetector(Class resourceType, int samplingInterval, long maxActive) {
      this(resourceType, samplingInterval);
   }

   public ResourceLeakDetector(Class resourceType, int samplingInterval) {
      this(StringUtil.simpleClassName(resourceType), samplingInterval, Long.MAX_VALUE);
   }

   /** @deprecated */
   @Deprecated
   public ResourceLeakDetector(String resourceType, int samplingInterval, long maxActive) {
      this.allLeaks = PlatformDependent.newConcurrentHashMap();
      this.refQueue = new ReferenceQueue();
      this.reportedLeaks = PlatformDependent.newConcurrentHashMap();
      if (resourceType == null) {
         throw new NullPointerException("resourceType");
      } else {
         this.resourceType = resourceType;
         this.samplingInterval = samplingInterval;
      }
   }

   /** @deprecated */
   @Deprecated
   public final ResourceLeak open(Object obj) {
      return this.track0(obj);
   }

   public final ResourceLeakTracker track(Object obj) {
      return this.track0(obj);
   }

   private DefaultResourceLeak track0(Object obj) {
      Level level = ResourceLeakDetector.level;
      if (level == ResourceLeakDetector.Level.DISABLED) {
         return null;
      } else if (level.ordinal() < ResourceLeakDetector.Level.PARANOID.ordinal()) {
         if (PlatformDependent.threadLocalRandom().nextInt(this.samplingInterval) == 0) {
            this.reportLeak(level);
            return new DefaultResourceLeak(obj);
         } else {
            return null;
         }
      } else {
         this.reportLeak(level);
         return new DefaultResourceLeak(obj);
      }
   }

   private void reportLeak(Level level) {
      DefaultResourceLeak ref;
      if (logger.isErrorEnabled()) {
         while(true) {
            ref = (DefaultResourceLeak)this.refQueue.poll();
            if (ref == null) {
               return;
            }

            ref.clear();
            if (ref.close()) {
               String records = ref.toString();
               if (this.reportedLeaks.putIfAbsent(records, Boolean.TRUE) == null) {
                  if (records.isEmpty()) {
                     this.reportUntracedLeak(this.resourceType);
                  } else {
                     this.reportTracedLeak(this.resourceType, records);
                  }
               }
            }
         }
      } else {
         while(true) {
            ref = (DefaultResourceLeak)this.refQueue.poll();
            if (ref == null) {
               return;
            }

            ref.close();
         }
      }
   }

   protected void reportTracedLeak(String resourceType, String records) {
      logger.error("LEAK: {}.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.{}", resourceType, records);
   }

   protected void reportUntracedLeak(String resourceType) {
      logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See http://netty.io/wiki/reference-counted-objects.html for more information.", resourceType, "org.python.netty.leakDetection.level", ResourceLeakDetector.Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName((Object)this));
   }

   /** @deprecated */
   @Deprecated
   protected void reportInstancesLeak(String resourceType) {
   }

   static String newRecord(Object hint, int recordsToSkip) {
      StringBuilder buf = new StringBuilder(4096);
      if (hint != null) {
         buf.append("\tHint: ");
         if (hint instanceof ResourceLeakHint) {
            buf.append(((ResourceLeakHint)hint).toHintString());
         } else {
            buf.append(hint);
         }

         buf.append(StringUtil.NEWLINE);
      }

      StackTraceElement[] array = (new Throwable()).getStackTrace();
      StackTraceElement[] var4 = array;
      int var5 = array.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         StackTraceElement e = var4[var6];
         if (recordsToSkip > 0) {
            --recordsToSkip;
         } else {
            String estr = e.toString();
            boolean excluded = false;
            String[] var10 = STACK_TRACE_ELEMENT_EXCLUSIONS;
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               String exclusion = var10[var12];
               if (estr.startsWith(exclusion)) {
                  excluded = true;
                  break;
               }
            }

            if (!excluded) {
               buf.append('\t');
               buf.append(estr);
               buf.append(StringUtil.NEWLINE);
            }
         }
      }

      return buf.toString();
   }

   static {
      DEFAULT_LEVEL = ResourceLeakDetector.Level.SIMPLE;
      logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
      boolean disabled;
      if (SystemPropertyUtil.get("org.python.netty.noResourceLeakDetection") != null) {
         disabled = SystemPropertyUtil.getBoolean("org.python.netty.noResourceLeakDetection", false);
         logger.debug("-Dio.netty.noResourceLeakDetection: {}", (Object)disabled);
         logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", "org.python.netty.leakDetection.level", DEFAULT_LEVEL.name().toLowerCase());
      } else {
         disabled = false;
      }

      Level defaultLevel = disabled ? ResourceLeakDetector.Level.DISABLED : DEFAULT_LEVEL;
      String levelStr = SystemPropertyUtil.get("org.python.netty.leakDetectionLevel", defaultLevel.name());
      levelStr = SystemPropertyUtil.get("org.python.netty.leakDetection.level", levelStr);
      Level level = ResourceLeakDetector.Level.parseLevel(levelStr);
      MAX_RECORDS = SystemPropertyUtil.getInt("org.python.netty.leakDetection.maxRecords", 4);
      ResourceLeakDetector.level = level;
      if (logger.isDebugEnabled()) {
         logger.debug("-D{}: {}", "org.python.netty.leakDetection.level", level.name().toLowerCase());
         logger.debug("-D{}: {}", "org.python.netty.leakDetection.maxRecords", MAX_RECORDS);
      }

      STACK_TRACE_ELEMENT_EXCLUSIONS = new String[]{"io.netty.util.ReferenceCountUtil.touch(", "io.netty.buffer.AdvancedLeakAwareByteBuf.touch(", "io.netty.buffer.AbstractByteBufAllocator.toLeakAwareBuffer(", "io.netty.buffer.AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation("};
   }

   private static final class LeakEntry {
      static final LeakEntry INSTANCE = new LeakEntry();
      private static final int HASH;

      public int hashCode() {
         return HASH;
      }

      public boolean equals(Object obj) {
         return obj == this;
      }

      static {
         HASH = System.identityHashCode(INSTANCE);
      }
   }

   private final class DefaultResourceLeak extends PhantomReference implements ResourceLeakTracker, ResourceLeak {
      private final String creationRecord;
      private final Deque lastRecords = new ArrayDeque();
      private final int trackedHash;
      private int removedRecords;

      DefaultResourceLeak(Object referent) {
         super(referent, ResourceLeakDetector.this.refQueue);

         assert referent != null;

         this.trackedHash = System.identityHashCode(referent);
         Level level = ResourceLeakDetector.getLevel();
         if (level.ordinal() >= ResourceLeakDetector.Level.ADVANCED.ordinal()) {
            this.creationRecord = ResourceLeakDetector.newRecord((Object)null, 3);
         } else {
            this.creationRecord = null;
         }

         ResourceLeakDetector.this.allLeaks.put(this, ResourceLeakDetector.LeakEntry.INSTANCE);
      }

      public void record() {
         this.record0((Object)null, 3);
      }

      public void record(Object hint) {
         this.record0(hint, 3);
      }

      private void record0(Object hint, int recordsToSkip) {
         if (this.creationRecord != null) {
            String value = ResourceLeakDetector.newRecord(hint, recordsToSkip);
            synchronized(this.lastRecords) {
               int size = this.lastRecords.size();
               if (size == 0 || !((String)this.lastRecords.getLast()).equals(value)) {
                  this.lastRecords.add(value);
               }

               if (size > ResourceLeakDetector.MAX_RECORDS) {
                  this.lastRecords.removeFirst();
                  ++this.removedRecords;
               }
            }
         }

      }

      public boolean close() {
         return ResourceLeakDetector.this.allLeaks.remove(this, ResourceLeakDetector.LeakEntry.INSTANCE);
      }

      public boolean close(Object trackedObject) {
         assert this.trackedHash == System.identityHashCode(trackedObject);

         return this.close() && trackedObject != null;
      }

      public String toString() {
         if (this.creationRecord == null) {
            return "";
         } else {
            Object[] array;
            int removedRecords;
            synchronized(this.lastRecords) {
               array = this.lastRecords.toArray();
               removedRecords = this.removedRecords;
            }

            StringBuilder buf = (new StringBuilder(16384)).append(StringUtil.NEWLINE);
            if (removedRecords > 0) {
               buf.append("WARNING: ").append(removedRecords).append(" leak records were discarded because the leak record count is limited to ").append(ResourceLeakDetector.MAX_RECORDS).append(". Use system property ").append("org.python.netty.leakDetection.maxRecords").append(" to increase the limit.").append(StringUtil.NEWLINE);
            }

            buf.append("Recent access records: ").append(array.length).append(StringUtil.NEWLINE);
            if (array.length > 0) {
               for(int i = array.length - 1; i >= 0; --i) {
                  buf.append('#').append(i + 1).append(':').append(StringUtil.NEWLINE).append(array[i]);
               }
            }

            buf.append("Created at:").append(StringUtil.NEWLINE).append(this.creationRecord);
            buf.setLength(buf.length() - StringUtil.NEWLINE.length());
            return buf.toString();
         }
      }
   }

   public static enum Level {
      DISABLED,
      SIMPLE,
      ADVANCED,
      PARANOID;

      static Level parseLevel(String levelStr) {
         String trimmedLevelStr = levelStr.trim();
         Level[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Level l = var2[var4];
            if (trimmedLevelStr.equalsIgnoreCase(l.name()) || trimmedLevelStr.equals(String.valueOf(l.ordinal()))) {
               return l;
            }
         }

         return ResourceLeakDetector.DEFAULT_LEVEL;
      }
   }
}
