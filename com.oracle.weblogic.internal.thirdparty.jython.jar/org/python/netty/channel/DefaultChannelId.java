package org.python.netty.channel;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.util.internal.EmptyArrays;
import org.python.netty.util.internal.MacAddressUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class DefaultChannelId implements ChannelId {
   private static final long serialVersionUID = 3884076183504074063L;
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelId.class);
   private static final byte[] MACHINE_ID;
   private static final int PROCESS_ID_LEN = 4;
   private static final int PROCESS_ID;
   private static final int SEQUENCE_LEN = 4;
   private static final int TIMESTAMP_LEN = 8;
   private static final int RANDOM_LEN = 4;
   private static final AtomicInteger nextSequence = new AtomicInteger();
   private final byte[] data;
   private final int hashCode;
   private transient String shortValue;
   private transient String longValue;

   public static DefaultChannelId newInstance() {
      return new DefaultChannelId();
   }

   private static int defaultProcessId() {
      ClassLoader loader = null;

      Class processType;
      Method myPid;
      String value;
      try {
         loader = PlatformDependent.getClassLoader(DefaultChannelId.class);
         Class mgmtFactoryType = Class.forName("java.lang.management.ManagementFactory", true, loader);
         processType = Class.forName("java.lang.management.RuntimeMXBean", true, loader);
         myPid = mgmtFactoryType.getMethod("getRuntimeMXBean", EmptyArrays.EMPTY_CLASSES);
         Object bean = myPid.invoke((Object)null, EmptyArrays.EMPTY_OBJECTS);
         Method getName = processType.getMethod("getName", EmptyArrays.EMPTY_CLASSES);
         value = (String)getName.invoke(bean, EmptyArrays.EMPTY_OBJECTS);
      } catch (Throwable var9) {
         logger.debug("Could not invoke ManagementFactory.getRuntimeMXBean().getName(); Android?", var9);

         try {
            processType = Class.forName("android.os.Process", true, loader);
            myPid = processType.getMethod("myPid", EmptyArrays.EMPTY_CLASSES);
            value = myPid.invoke((Object)null, EmptyArrays.EMPTY_OBJECTS).toString();
         } catch (Throwable var8) {
            logger.debug("Could not invoke Process.myPid(); not Android?", var8);
            value = "";
         }
      }

      int atIndex = value.indexOf(64);
      if (atIndex >= 0) {
         value = value.substring(0, atIndex);
      }

      int pid;
      try {
         pid = Integer.parseInt(value);
      } catch (NumberFormatException var7) {
         pid = -1;
      }

      if (pid < 0) {
         pid = PlatformDependent.threadLocalRandom().nextInt();
         logger.warn("Failed to find the current process ID from '{}'; using a random value: {}", value, pid);
      }

      return pid;
   }

   private DefaultChannelId() {
      this.data = new byte[MACHINE_ID.length + 4 + 4 + 8 + 4];
      int i = 0;
      System.arraycopy(MACHINE_ID, 0, this.data, i, MACHINE_ID.length);
      i += MACHINE_ID.length;
      i = this.writeInt(i, PROCESS_ID);
      i = this.writeInt(i, nextSequence.getAndIncrement());
      i = this.writeLong(i, Long.reverse(System.nanoTime()) ^ System.currentTimeMillis());
      int random = PlatformDependent.threadLocalRandom().nextInt();
      i = this.writeInt(i, random);

      assert i == this.data.length;

      this.hashCode = Arrays.hashCode(this.data);
   }

   private int writeInt(int i, int value) {
      this.data[i++] = (byte)(value >>> 24);
      this.data[i++] = (byte)(value >>> 16);
      this.data[i++] = (byte)(value >>> 8);
      this.data[i++] = (byte)value;
      return i;
   }

   private int writeLong(int i, long value) {
      this.data[i++] = (byte)((int)(value >>> 56));
      this.data[i++] = (byte)((int)(value >>> 48));
      this.data[i++] = (byte)((int)(value >>> 40));
      this.data[i++] = (byte)((int)(value >>> 32));
      this.data[i++] = (byte)((int)(value >>> 24));
      this.data[i++] = (byte)((int)(value >>> 16));
      this.data[i++] = (byte)((int)(value >>> 8));
      this.data[i++] = (byte)((int)value);
      return i;
   }

   public String asShortText() {
      String shortValue = this.shortValue;
      if (shortValue == null) {
         this.shortValue = shortValue = ByteBufUtil.hexDump((byte[])this.data, this.data.length - 4, 4);
      }

      return shortValue;
   }

   public String asLongText() {
      String longValue = this.longValue;
      if (longValue == null) {
         this.longValue = longValue = this.newLongValue();
      }

      return longValue;
   }

   private String newLongValue() {
      StringBuilder buf = new StringBuilder(2 * this.data.length + 5);
      int i = 0;
      i = this.appendHexDumpField(buf, i, MACHINE_ID.length);
      i = this.appendHexDumpField(buf, i, 4);
      i = this.appendHexDumpField(buf, i, 4);
      i = this.appendHexDumpField(buf, i, 8);
      i = this.appendHexDumpField(buf, i, 4);

      assert i == this.data.length;

      return buf.substring(0, buf.length() - 1);
   }

   private int appendHexDumpField(StringBuilder buf, int i, int length) {
      buf.append(ByteBufUtil.hexDump(this.data, i, length));
      buf.append('-');
      i += length;
      return i;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public int compareTo(ChannelId o) {
      if (this == o) {
         return 0;
      } else if (o instanceof DefaultChannelId) {
         byte[] otherData = ((DefaultChannelId)o).data;
         int len1 = this.data.length;
         int len2 = otherData.length;
         int len = Math.min(len1, len2);

         for(int k = 0; k < len; ++k) {
            byte x = this.data[k];
            byte y = otherData[k];
            if (x != y) {
               return (x & 255) - (y & 255);
            }
         }

         return len1 - len2;
      } else {
         return this.asLongText().compareTo(o.asLongText());
      }
   }

   public boolean equals(Object obj) {
      return this == obj || obj instanceof DefaultChannelId && Arrays.equals(this.data, ((DefaultChannelId)obj).data);
   }

   public String toString() {
      return this.asShortText();
   }

   static {
      int processId = -1;
      String customProcessId = SystemPropertyUtil.get("org.python.netty.processId");
      if (customProcessId != null) {
         try {
            processId = Integer.parseInt(customProcessId);
         } catch (NumberFormatException var6) {
         }

         if (processId < 0) {
            processId = -1;
            logger.warn("-Dio.netty.processId: {} (malformed)", (Object)customProcessId);
         } else if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.processId: {} (user-set)", (Object)processId);
         }
      }

      if (processId < 0) {
         processId = defaultProcessId();
         if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.processId: {} (auto-detected)", (Object)processId);
         }
      }

      PROCESS_ID = processId;
      byte[] machineId = null;
      String customMachineId = SystemPropertyUtil.get("org.python.netty.machineId");
      if (customMachineId != null) {
         try {
            machineId = MacAddressUtil.parseMAC(customMachineId);
         } catch (Exception var5) {
            logger.warn("-Dio.netty.machineId: {} (malformed)", customMachineId, var5);
         }

         if (machineId != null) {
            logger.debug("-Dio.netty.machineId: {} (user-set)", (Object)customMachineId);
         }
      }

      if (machineId == null) {
         machineId = MacAddressUtil.defaultMachineId();
         if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.machineId: {} (auto-detected)", (Object)MacAddressUtil.formatAddress(machineId));
         }
      }

      MACHINE_ID = machineId;
   }
}
