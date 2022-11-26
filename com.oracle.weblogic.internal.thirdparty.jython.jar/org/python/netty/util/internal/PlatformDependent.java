package org.python.netty.util.internal;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;
import org.python.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;
import org.python.netty.util.internal.shaded.org.jctools.queues.MpscChunkedArrayQueue;
import org.python.netty.util.internal.shaded.org.jctools.queues.SpscLinkedQueue;
import org.python.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueue;
import org.python.netty.util.internal.shaded.org.jctools.queues.atomic.MpscLinkedAtomicQueue;
import org.python.netty.util.internal.shaded.org.jctools.queues.atomic.SpscLinkedAtomicQueue;
import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

public final class PlatformDependent {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
   private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
   private static final boolean IS_ANDROID = isAndroid0();
   private static final boolean IS_WINDOWS = isWindows0();
   private static final boolean MAYBE_SUPER_USER;
   private static final int JAVA_VERSION = javaVersion0();
   private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = !isAndroid();
   private static final boolean HAS_UNSAFE = hasUnsafe0();
   private static final boolean DIRECT_BUFFER_PREFERRED;
   private static final long MAX_DIRECT_MEMORY;
   private static final int MPSC_CHUNK_SIZE = 1024;
   private static final int MIN_MAX_MPSC_CAPACITY = 2048;
   private static final int DEFAULT_MAX_MPSC_CAPACITY = 1048576;
   private static final int MAX_ALLOWED_MPSC_CAPACITY = 1073741824;
   private static final long BYTE_ARRAY_BASE_OFFSET;
   private static final File TMPDIR;
   private static final int BIT_MODE;
   private static final int ADDRESS_SIZE;
   private static final boolean USE_DIRECT_BUFFER_NO_CLEANER;
   private static final AtomicLong DIRECT_MEMORY_COUNTER;
   private static final long DIRECT_MEMORY_LIMIT;
   private static final ThreadLocalRandomProvider RANDOM_PROVIDER;
   private static final Cleaner CLEANER;
   private static final int UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD;
   public static final boolean BIG_ENDIAN_NATIVE_ORDER;
   private static final Cleaner NOOP;

   public static byte[] allocateUninitializedArray(int size) {
      return UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD >= 0 && UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD <= size ? PlatformDependent0.allocateUninitializedArray(size) : new byte[size];
   }

   public static boolean isAndroid() {
      return IS_ANDROID;
   }

   public static boolean isWindows() {
      return IS_WINDOWS;
   }

   public static boolean maybeSuperUser() {
      return MAYBE_SUPER_USER;
   }

   public static int javaVersion() {
      return JAVA_VERSION;
   }

   public static boolean canEnableTcpNoDelayByDefault() {
      return CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
   }

   public static boolean hasUnsafe() {
      return HAS_UNSAFE;
   }

   public static boolean isUnaligned() {
      return PlatformDependent0.isUnaligned();
   }

   public static boolean directBufferPreferred() {
      return DIRECT_BUFFER_PREFERRED;
   }

   public static long maxDirectMemory() {
      return MAX_DIRECT_MEMORY;
   }

   public static File tmpdir() {
      return TMPDIR;
   }

   public static int bitMode() {
      return BIT_MODE;
   }

   public static int addressSize() {
      return ADDRESS_SIZE;
   }

   public static long allocateMemory(long size) {
      return PlatformDependent0.allocateMemory(size);
   }

   public static void freeMemory(long address) {
      PlatformDependent0.freeMemory(address);
   }

   public static long reallocateMemory(long address, long newSize) {
      return PlatformDependent0.reallocateMemory(address, newSize);
   }

   public static void throwException(Throwable t) {
      if (hasUnsafe()) {
         PlatformDependent0.throwException(t);
      } else {
         throwException0(t);
      }

   }

   private static void throwException0(Throwable t) throws Throwable {
      throw t;
   }

   public static ConcurrentMap newConcurrentHashMap() {
      return new ConcurrentHashMap();
   }

   public static LongCounter newLongCounter() {
      return (LongCounter)(javaVersion() >= 8 ? new LongAdderCounter() : new AtomicLongCounter());
   }

   public static ConcurrentMap newConcurrentHashMap(int initialCapacity) {
      return new ConcurrentHashMap(initialCapacity);
   }

   public static ConcurrentMap newConcurrentHashMap(int initialCapacity, float loadFactor) {
      return new ConcurrentHashMap(initialCapacity, loadFactor);
   }

   public static ConcurrentMap newConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
      return new ConcurrentHashMap(initialCapacity, loadFactor, concurrencyLevel);
   }

   public static ConcurrentMap newConcurrentHashMap(Map map) {
      return new ConcurrentHashMap(map);
   }

   public static void freeDirectBuffer(ByteBuffer buffer) {
      CLEANER.freeDirectBuffer(buffer);
   }

   public static long directBufferAddress(ByteBuffer buffer) {
      return PlatformDependent0.directBufferAddress(buffer);
   }

   public static ByteBuffer directBuffer(long memoryAddress, int size) {
      if (PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
         return PlatformDependent0.newDirectBuffer(memoryAddress, size);
      } else {
         throw new UnsupportedOperationException("sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
      }
   }

   public static int getInt(Object object, long fieldOffset) {
      return PlatformDependent0.getInt(object, fieldOffset);
   }

   public static byte getByte(long address) {
      return PlatformDependent0.getByte(address);
   }

   public static short getShort(long address) {
      return PlatformDependent0.getShort(address);
   }

   public static int getInt(long address) {
      return PlatformDependent0.getInt(address);
   }

   public static long getLong(long address) {
      return PlatformDependent0.getLong(address);
   }

   public static byte getByte(byte[] data, int index) {
      return PlatformDependent0.getByte(data, index);
   }

   public static short getShort(byte[] data, int index) {
      return PlatformDependent0.getShort(data, index);
   }

   public static int getInt(byte[] data, int index) {
      return PlatformDependent0.getInt(data, index);
   }

   public static long getLong(byte[] data, int index) {
      return PlatformDependent0.getLong(data, index);
   }

   private static long getLongSafe(byte[] bytes, int offset) {
      return BIG_ENDIAN_NATIVE_ORDER ? (long)bytes[offset] << 56 | ((long)bytes[offset + 1] & 255L) << 48 | ((long)bytes[offset + 2] & 255L) << 40 | ((long)bytes[offset + 3] & 255L) << 32 | ((long)bytes[offset + 4] & 255L) << 24 | ((long)bytes[offset + 5] & 255L) << 16 | ((long)bytes[offset + 6] & 255L) << 8 | (long)bytes[offset + 7] & 255L : (long)bytes[offset] & 255L | ((long)bytes[offset + 1] & 255L) << 8 | ((long)bytes[offset + 2] & 255L) << 16 | ((long)bytes[offset + 3] & 255L) << 24 | ((long)bytes[offset + 4] & 255L) << 32 | ((long)bytes[offset + 5] & 255L) << 40 | ((long)bytes[offset + 6] & 255L) << 48 | (long)bytes[offset + 7] << 56;
   }

   private static int getIntSafe(byte[] bytes, int offset) {
      return BIG_ENDIAN_NATIVE_ORDER ? bytes[offset] << 24 | (bytes[offset + 1] & 255) << 16 | (bytes[offset + 2] & 255) << 8 | bytes[offset + 3] & 255 : bytes[offset] & 255 | (bytes[offset + 1] & 255) << 8 | (bytes[offset + 2] & 255) << 16 | bytes[offset + 3] << 24;
   }

   private static short getShortSafe(byte[] bytes, int offset) {
      return BIG_ENDIAN_NATIVE_ORDER ? (short)(bytes[offset] << 8 | bytes[offset + 1] & 255) : (short)(bytes[offset] & 255 | bytes[offset + 1] << 8);
   }

   private static int hashCodeAsciiCompute(CharSequence value, int offset, int hash) {
      return BIG_ENDIAN_NATIVE_ORDER ? hash * 461845907 + hashCodeAsciiSanitizeInt(value, offset + 4) * 461845907 + hashCodeAsciiSanitizeInt(value, offset) : hash * 461845907 + hashCodeAsciiSanitizeInt(value, offset) * 461845907 + hashCodeAsciiSanitizeInt(value, offset + 4);
   }

   private static int hashCodeAsciiSanitizeInt(CharSequence value, int offset) {
      return BIG_ENDIAN_NATIVE_ORDER ? value.charAt(offset + 3) & 31 | (value.charAt(offset + 2) & 31) << 8 | (value.charAt(offset + 1) & 31) << 16 | (value.charAt(offset) & 31) << 24 : (value.charAt(offset + 3) & 31) << 24 | (value.charAt(offset + 2) & 31) << 16 | (value.charAt(offset + 1) & 31) << 8 | value.charAt(offset) & 31;
   }

   private static int hashCodeAsciiSanitizeShort(CharSequence value, int offset) {
      return BIG_ENDIAN_NATIVE_ORDER ? value.charAt(offset + 1) & 31 | (value.charAt(offset) & 31) << 8 : (value.charAt(offset + 1) & 31) << 8 | value.charAt(offset) & 31;
   }

   private static int hashCodeAsciiSanitizeByte(char value) {
      return value & 31;
   }

   public static void putByte(long address, byte value) {
      PlatformDependent0.putByte(address, value);
   }

   public static void putShort(long address, short value) {
      PlatformDependent0.putShort(address, value);
   }

   public static void putInt(long address, int value) {
      PlatformDependent0.putInt(address, value);
   }

   public static void putLong(long address, long value) {
      PlatformDependent0.putLong(address, value);
   }

   public static void putByte(byte[] data, int index, byte value) {
      PlatformDependent0.putByte(data, index, value);
   }

   public static void putShort(byte[] data, int index, short value) {
      PlatformDependent0.putShort(data, index, value);
   }

   public static void putInt(byte[] data, int index, int value) {
      PlatformDependent0.putInt(data, index, value);
   }

   public static void putLong(byte[] data, int index, long value) {
      PlatformDependent0.putLong(data, index, value);
   }

   public static void copyMemory(long srcAddr, long dstAddr, long length) {
      PlatformDependent0.copyMemory(srcAddr, dstAddr, length);
   }

   public static void copyMemory(byte[] src, int srcIndex, long dstAddr, long length) {
      PlatformDependent0.copyMemory(src, BYTE_ARRAY_BASE_OFFSET + (long)srcIndex, (Object)null, dstAddr, length);
   }

   public static void copyMemory(long srcAddr, byte[] dst, int dstIndex, long length) {
      PlatformDependent0.copyMemory((Object)null, srcAddr, dst, BYTE_ARRAY_BASE_OFFSET + (long)dstIndex, length);
   }

   public static void setMemory(byte[] dst, int dstIndex, long bytes, byte value) {
      PlatformDependent0.setMemory(dst, BYTE_ARRAY_BASE_OFFSET + (long)dstIndex, bytes, value);
   }

   public static void setMemory(long address, long bytes, byte value) {
      PlatformDependent0.setMemory(address, bytes, value);
   }

   public static ByteBuffer allocateDirectNoCleaner(int capacity) {
      assert USE_DIRECT_BUFFER_NO_CLEANER;

      incrementMemoryCounter(capacity);

      try {
         return PlatformDependent0.allocateDirectNoCleaner(capacity);
      } catch (Throwable var2) {
         decrementMemoryCounter(capacity);
         throwException(var2);
         return null;
      }
   }

   public static ByteBuffer reallocateDirectNoCleaner(ByteBuffer buffer, int capacity) {
      assert USE_DIRECT_BUFFER_NO_CLEANER;

      int len = capacity - buffer.capacity();
      incrementMemoryCounter(len);

      try {
         return PlatformDependent0.reallocateDirectNoCleaner(buffer, capacity);
      } catch (Throwable var4) {
         decrementMemoryCounter(len);
         throwException(var4);
         return null;
      }
   }

   public static void freeDirectNoCleaner(ByteBuffer buffer) {
      assert USE_DIRECT_BUFFER_NO_CLEANER;

      int capacity = buffer.capacity();
      PlatformDependent0.freeMemory(PlatformDependent0.directBufferAddress(buffer));
      decrementMemoryCounter(capacity);
   }

   private static void incrementMemoryCounter(int capacity) {
      long usedMemory;
      long newUsedMemory;
      if (DIRECT_MEMORY_COUNTER != null) {
         do {
            usedMemory = DIRECT_MEMORY_COUNTER.get();
            newUsedMemory = usedMemory + (long)capacity;
            if (newUsedMemory > DIRECT_MEMORY_LIMIT) {
               throw new OutOfDirectMemoryError("failed to allocate " + capacity + " byte(s) of direct memory (used: " + usedMemory + ", max: " + DIRECT_MEMORY_LIMIT + ')');
            }
         } while(!DIRECT_MEMORY_COUNTER.compareAndSet(usedMemory, newUsedMemory));
      }

   }

   private static void decrementMemoryCounter(int capacity) {
      if (DIRECT_MEMORY_COUNTER != null) {
         long usedMemory = DIRECT_MEMORY_COUNTER.addAndGet((long)(-capacity));

         assert usedMemory >= 0L;
      }

   }

   public static boolean useDirectBufferNoCleaner() {
      return USE_DIRECT_BUFFER_NO_CLEANER;
   }

   public static boolean equals(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
      return hasUnsafe() && PlatformDependent0.unalignedAccess() ? PlatformDependent0.equals(bytes1, startPos1, bytes2, startPos2, length) : equalsSafe(bytes1, startPos1, bytes2, startPos2, length);
   }

   public static boolean isZero(byte[] bytes, int startPos, int length) {
      return hasUnsafe() && PlatformDependent0.unalignedAccess() ? PlatformDependent0.isZero(bytes, startPos, length) : isZeroSafe(bytes, startPos, length);
   }

   public static int equalsConstantTime(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
      return hasUnsafe() && PlatformDependent0.unalignedAccess() ? PlatformDependent0.equalsConstantTime(bytes1, startPos1, bytes2, startPos2, length) : ConstantTimeUtils.equalsConstantTime(bytes1, startPos1, bytes2, startPos2, length);
   }

   public static int hashCodeAscii(byte[] bytes, int startPos, int length) {
      return hasUnsafe() && PlatformDependent0.unalignedAccess() ? PlatformDependent0.hashCodeAscii(bytes, startPos, length) : hashCodeAsciiSafe(bytes, startPos, length);
   }

   public static int hashCodeAscii(CharSequence bytes) {
      int hash = -1028477387;
      int remainingBytes = bytes.length() & 7;
      switch (bytes.length()) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
            break;
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
            hash = hashCodeAsciiCompute(bytes, bytes.length() - 8, hash);
            break;
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
            hash = hashCodeAsciiCompute(bytes, bytes.length() - 16, hashCodeAsciiCompute(bytes, bytes.length() - 8, hash));
            break;
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
            hash = hashCodeAsciiCompute(bytes, bytes.length() - 24, hashCodeAsciiCompute(bytes, bytes.length() - 16, hashCodeAsciiCompute(bytes, bytes.length() - 8, hash)));
            break;
         default:
            for(int i = bytes.length() - 8; i >= remainingBytes; i -= 8) {
               hash = hashCodeAsciiCompute(bytes, i, hash);
            }
      }

      switch (remainingBytes) {
         case 1:
            return hash * 461845907 + hashCodeAsciiSanitizeByte(bytes.charAt(0));
         case 2:
            return hash * 461845907 + hashCodeAsciiSanitizeShort(bytes, 0);
         case 3:
            return (hash * 461845907 + hashCodeAsciiSanitizeByte(bytes.charAt(0))) * 461845907 + hashCodeAsciiSanitizeShort(bytes, 1);
         case 4:
            return hash * 461845907 + hashCodeAsciiSanitizeInt(bytes, 0);
         case 5:
            return (hash * 461845907 + hashCodeAsciiSanitizeByte(bytes.charAt(0))) * 461845907 + hashCodeAsciiSanitizeInt(bytes, 1);
         case 6:
            return (hash * 461845907 + hashCodeAsciiSanitizeShort(bytes, 0)) * 461845907 + hashCodeAsciiSanitizeInt(bytes, 2);
         case 7:
            return ((hash * 461845907 + hashCodeAsciiSanitizeByte(bytes.charAt(0))) * 461845907 + hashCodeAsciiSanitizeShort(bytes, 1)) * 461845907 + hashCodeAsciiSanitizeInt(bytes, 3);
         default:
            return hash;
      }
   }

   public static Queue newMpscQueue() {
      return newMpscQueue(1048576);
   }

   public static Queue newMpscQueue(int maxCapacity) {
      return PlatformDependent.Mpsc.newMpscQueue(maxCapacity);
   }

   public static Queue newSpscQueue() {
      return (Queue)(hasUnsafe() ? new SpscLinkedQueue() : new SpscLinkedAtomicQueue());
   }

   public static Queue newFixedMpscQueue(int capacity) {
      return (Queue)(hasUnsafe() ? new MpscArrayQueue(capacity) : new MpscAtomicArrayQueue(capacity));
   }

   public static ClassLoader getClassLoader(Class clazz) {
      return PlatformDependent0.getClassLoader(clazz);
   }

   public static ClassLoader getContextClassLoader() {
      return PlatformDependent0.getContextClassLoader();
   }

   public static ClassLoader getSystemClassLoader() {
      return PlatformDependent0.getSystemClassLoader();
   }

   public static Deque newConcurrentDeque() {
      return (Deque)(javaVersion() < 7 ? new LinkedBlockingDeque() : new ConcurrentLinkedDeque());
   }

   public static Random threadLocalRandom() {
      return RANDOM_PROVIDER.current();
   }

   private static boolean isAndroid0() {
      boolean android;
      try {
         Class.forName("android.app.Application", false, getSystemClassLoader());
         android = true;
      } catch (Throwable var2) {
         android = false;
      }

      if (android) {
         logger.debug("Platform: Android");
      }

      return android;
   }

   private static boolean isWindows0() {
      boolean windows = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
      if (windows) {
         logger.debug("Platform: Windows");
      }

      return windows;
   }

   private static boolean maybeSuperUser0() {
      String username = SystemPropertyUtil.get("user.name");
      if (isWindows()) {
         return "Administrator".equals(username);
      } else {
         return "root".equals(username) || "toor".equals(username);
      }
   }

   private static int javaVersion0() {
      int majorVersion;
      if (isAndroid()) {
         majorVersion = 6;
      } else {
         majorVersion = majorVersionFromJavaSpecificationVersion();
      }

      logger.debug("Java version: {}", (Object)majorVersion);
      return majorVersion;
   }

   static int majorVersionFromJavaSpecificationVersion() {
      return majorVersion(SystemPropertyUtil.get("java.specification.version", "1.6"));
   }

   static int majorVersion(String javaSpecVersion) {
      String[] components = javaSpecVersion.split("\\.");
      int[] version = new int[components.length];

      for(int i = 0; i < components.length; ++i) {
         version[i] = Integer.parseInt(components[i]);
      }

      if (version[0] == 1) {
         assert version[1] >= 6;

         return version[1];
      } else {
         return version[0];
      }
   }

   private static boolean hasUnsafe0() {
      if (isAndroid()) {
         logger.debug("sun.misc.Unsafe: unavailable (Android)");
         return false;
      } else if (PlatformDependent0.isExplicitNoUnsafe()) {
         return false;
      } else {
         try {
            boolean hasUnsafe = PlatformDependent0.hasUnsafe();
            logger.debug("sun.misc.Unsafe: {}", (Object)(hasUnsafe ? "available" : "unavailable"));
            return hasUnsafe;
         } catch (Throwable var1) {
            return false;
         }
      }
   }

   private static long maxDirectMemory0() {
      long maxDirectMemory = 0L;
      ClassLoader systemClassLoader = null;

      Class mgmtFactoryClass;
      try {
         systemClassLoader = getSystemClassLoader();
         mgmtFactoryClass = Class.forName("sun.misc.VM", true, systemClassLoader);
         Method m = mgmtFactoryClass.getDeclaredMethod("maxDirectMemory");
         maxDirectMemory = ((Number)m.invoke((Object)null)).longValue();
      } catch (Throwable var9) {
      }

      if (maxDirectMemory > 0L) {
         return maxDirectMemory;
      } else {
         try {
            mgmtFactoryClass = Class.forName("java.lang.management.ManagementFactory", true, systemClassLoader);
            Class runtimeClass = Class.forName("java.lang.management.RuntimeMXBean", true, systemClassLoader);
            Object runtime = mgmtFactoryClass.getDeclaredMethod("getRuntimeMXBean").invoke((Object)null);
            List vmArgs = (List)runtimeClass.getDeclaredMethod("getInputArguments").invoke(runtime);

            label41:
            for(int i = vmArgs.size() - 1; i >= 0; --i) {
               Matcher m = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN.matcher((CharSequence)vmArgs.get(i));
               if (m.matches()) {
                  maxDirectMemory = Long.parseLong(m.group(1));
                  switch (m.group(2).charAt(0)) {
                     case 'G':
                     case 'g':
                        maxDirectMemory *= 1073741824L;
                        break label41;
                     case 'K':
                     case 'k':
                        maxDirectMemory *= 1024L;
                        break label41;
                     case 'M':
                     case 'm':
                        maxDirectMemory *= 1048576L;
                     default:
                        break label41;
                  }
               }
            }
         } catch (Throwable var10) {
         }

         if (maxDirectMemory <= 0L) {
            maxDirectMemory = Runtime.getRuntime().maxMemory();
            logger.debug("maxDirectMemory: {} bytes (maybe)", (Object)maxDirectMemory);
         } else {
            logger.debug("maxDirectMemory: {} bytes", (Object)maxDirectMemory);
         }

         return maxDirectMemory;
      }
   }

   private static File tmpdir0() {
      File f;
      try {
         f = toDirectory(SystemPropertyUtil.get("org.python.netty.tmpdir"));
         if (f != null) {
            logger.debug("-Dio.netty.tmpdir: {}", (Object)f);
            return f;
         }

         f = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
         if (f != null) {
            logger.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", (Object)f);
            return f;
         }

         if (isWindows()) {
            f = toDirectory(System.getenv("TEMP"));
            if (f != null) {
               logger.debug("-Dio.netty.tmpdir: {} (%TEMP%)", (Object)f);
               return f;
            }

            String userprofile = System.getenv("USERPROFILE");
            if (userprofile != null) {
               f = toDirectory(userprofile + "\\AppData\\Local\\Temp");
               if (f != null) {
                  logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", (Object)f);
                  return f;
               }

               f = toDirectory(userprofile + "\\Local Settings\\Temp");
               if (f != null) {
                  logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", (Object)f);
                  return f;
               }
            }
         } else {
            f = toDirectory(System.getenv("TMPDIR"));
            if (f != null) {
               logger.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", (Object)f);
               return f;
            }
         }
      } catch (Throwable var2) {
      }

      if (isWindows()) {
         f = new File("C:\\Windows\\Temp");
      } else {
         f = new File("/tmp");
      }

      logger.warn("Failed to get the temporary directory; falling back to: {}", (Object)f);
      return f;
   }

   private static File toDirectory(String path) {
      if (path == null) {
         return null;
      } else {
         File f = new File(path);
         f.mkdirs();
         if (!f.isDirectory()) {
            return null;
         } else {
            try {
               return f.getAbsoluteFile();
            } catch (Exception var3) {
               return f;
            }
         }
      }
   }

   private static int bitMode0() {
      int bitMode = SystemPropertyUtil.getInt("org.python.netty.bitMode", 0);
      if (bitMode > 0) {
         logger.debug("-Dio.netty.bitMode: {}", (Object)bitMode);
         return bitMode;
      } else {
         bitMode = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
         if (bitMode > 0) {
            logger.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", (Object)bitMode);
            return bitMode;
         } else {
            bitMode = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
            if (bitMode > 0) {
               logger.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", (Object)bitMode);
               return bitMode;
            } else {
               String arch = SystemPropertyUtil.get("os.arch", "").toLowerCase(Locale.US).trim();
               if (!"amd64".equals(arch) && !"x86_64".equals(arch)) {
                  if ("i386".equals(arch) || "i486".equals(arch) || "i586".equals(arch) || "i686".equals(arch)) {
                     bitMode = 32;
                  }
               } else {
                  bitMode = 64;
               }

               if (bitMode > 0) {
                  logger.debug("-Dio.netty.bitMode: {} (os.arch: {})", bitMode, arch);
               }

               String vm = SystemPropertyUtil.get("java.vm.name", "").toLowerCase(Locale.US);
               Pattern BIT_PATTERN = Pattern.compile("([1-9][0-9]+)-?bit");
               Matcher m = BIT_PATTERN.matcher(vm);
               return m.find() ? Integer.parseInt(m.group(1)) : 64;
            }
         }
      }
   }

   private static int addressSize0() {
      return !hasUnsafe() ? -1 : PlatformDependent0.addressSize();
   }

   private static long byteArrayBaseOffset0() {
      return !hasUnsafe() ? -1L : PlatformDependent0.byteArrayBaseOffset();
   }

   private static boolean equalsSafe(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
      for(int end = startPos1 + length; startPos1 < end; ++startPos2) {
         if (bytes1[startPos1] != bytes2[startPos2]) {
            return false;
         }

         ++startPos1;
      }

      return true;
   }

   private static boolean isZeroSafe(byte[] bytes, int startPos, int length) {
      for(int end = startPos + length; startPos < end; ++startPos) {
         if (bytes[startPos] != 0) {
            return false;
         }
      }

      return true;
   }

   static int hashCodeAsciiSafe(byte[] bytes, int startPos, int length) {
      int hash = -1028477387;
      int remainingBytes = length & 7;
      int end = startPos + remainingBytes;

      for(int i = startPos - 8 + length; i >= end; i -= 8) {
         hash = PlatformDependent0.hashCodeAsciiCompute(getLongSafe(bytes, i), hash);
      }

      switch (remainingBytes) {
         case 1:
            return hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(bytes[startPos]);
         case 2:
            return hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bytes, startPos));
         case 3:
            return (hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(bytes[startPos])) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bytes, startPos + 1));
         case 4:
            return hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bytes, startPos));
         case 5:
            return (hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(bytes[startPos])) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bytes, startPos + 1));
         case 6:
            return (hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bytes, startPos))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bytes, startPos + 2));
         case 7:
            return ((hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(bytes[startPos])) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(getShortSafe(bytes, startPos + 1))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(getIntSafe(bytes, startPos + 3));
         default:
            return hash;
      }
   }

   private PlatformDependent() {
   }

   static {
      DIRECT_BUFFER_PREFERRED = HAS_UNSAFE && !SystemPropertyUtil.getBoolean("org.python.netty.noPreferDirect", false);
      MAX_DIRECT_MEMORY = maxDirectMemory0();
      BYTE_ARRAY_BASE_OFFSET = byteArrayBaseOffset0();
      TMPDIR = tmpdir0();
      BIT_MODE = bitMode0();
      ADDRESS_SIZE = addressSize0();
      BIG_ENDIAN_NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
      NOOP = new Cleaner() {
         public void freeDirectBuffer(ByteBuffer buffer) {
         }
      };
      if (javaVersion() >= 7) {
         RANDOM_PROVIDER = new ThreadLocalRandomProvider() {
            public Random current() {
               return java.util.concurrent.ThreadLocalRandom.current();
            }
         };
      } else {
         RANDOM_PROVIDER = new ThreadLocalRandomProvider() {
            public Random current() {
               return ThreadLocalRandom.current();
            }
         };
      }

      if (logger.isDebugEnabled()) {
         logger.debug("-Dio.netty.noPreferDirect: {}", (Object)(!DIRECT_BUFFER_PREFERRED));
      }

      if (!hasUnsafe() && !isAndroid() && !PlatformDependent0.isExplicitNoUnsafe()) {
         logger.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system instability.");
      }

      long maxDirectMemory = SystemPropertyUtil.getLong("org.python.netty.maxDirectMemory", -1L);
      if (maxDirectMemory != 0L && hasUnsafe() && PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
         USE_DIRECT_BUFFER_NO_CLEANER = true;
         if (maxDirectMemory < 0L) {
            maxDirectMemory = maxDirectMemory0();
            if (maxDirectMemory <= 0L) {
               DIRECT_MEMORY_COUNTER = null;
            } else {
               DIRECT_MEMORY_COUNTER = new AtomicLong();
            }
         } else {
            DIRECT_MEMORY_COUNTER = new AtomicLong();
         }
      } else {
         USE_DIRECT_BUFFER_NO_CLEANER = false;
         DIRECT_MEMORY_COUNTER = null;
      }

      DIRECT_MEMORY_LIMIT = maxDirectMemory;
      logger.debug("-Dio.netty.maxDirectMemory: {} bytes", (Object)maxDirectMemory);
      int tryAllocateUninitializedArray = SystemPropertyUtil.getInt("org.python.netty.uninitializedArrayAllocationThreshold", 1024);
      UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD = javaVersion() >= 9 && PlatformDependent0.hasAllocateArrayMethod() ? tryAllocateUninitializedArray : -1;
      logger.debug("-Dio.netty.uninitializedArrayAllocationThreshold: {}", (Object)UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD);
      MAYBE_SUPER_USER = maybeSuperUser0();
      if (!isAndroid() && hasUnsafe()) {
         if (javaVersion() >= 9) {
            CLEANER = (Cleaner)(CleanerJava9.isSupported() ? new CleanerJava9() : NOOP);
         } else {
            CLEANER = (Cleaner)(CleanerJava6.isSupported() ? new CleanerJava6() : NOOP);
         }
      } else {
         CLEANER = NOOP;
      }

   }

   private interface ThreadLocalRandomProvider {
      Random current();
   }

   private static final class AtomicLongCounter extends AtomicLong implements LongCounter {
      private static final long serialVersionUID = 4074772784610639305L;

      private AtomicLongCounter() {
      }

      public void add(long delta) {
         this.addAndGet(delta);
      }

      public void increment() {
         this.incrementAndGet();
      }

      public void decrement() {
         this.decrementAndGet();
      }

      public long value() {
         return this.get();
      }

      // $FF: synthetic method
      AtomicLongCounter(Object x0) {
         this();
      }
   }

   private static final class Mpsc {
      private static final boolean USE_MPSC_CHUNKED_ARRAY_QUEUE;

      static Queue newMpscQueue(int maxCapacity) {
         if (USE_MPSC_CHUNKED_ARRAY_QUEUE) {
            int capacity = Math.max(Math.min(maxCapacity, 1073741824), 2048);
            return new MpscChunkedArrayQueue(1024, capacity);
         } else {
            return new MpscLinkedAtomicQueue();
         }
      }

      static {
         Object unsafe = null;
         if (PlatformDependent.hasUnsafe()) {
            unsafe = AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  return UnsafeAccess.UNSAFE;
               }
            });
         }

         if (unsafe == null) {
            PlatformDependent.logger.debug("org.jctools-core.MpscChunkedArrayQueue: unavailable");
            USE_MPSC_CHUNKED_ARRAY_QUEUE = false;
         } else {
            PlatformDependent.logger.debug("org.jctools-core.MpscChunkedArrayQueue: available");
            USE_MPSC_CHUNKED_ARRAY_QUEUE = true;
         }

      }
   }
}
