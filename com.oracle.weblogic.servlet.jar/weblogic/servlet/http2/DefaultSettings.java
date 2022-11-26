package weblogic.servlet.http2;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultSettings {
   protected static final int MIN_MAX_FRAME_SIZE = 16384;
   protected static final int MAX_MAX_FRAME_SIZE = 16777215;
   protected static final int MAX_HEADER_TABLE_SIZE = 65536;
   public static final int CONNECTION_INITIAL_WINDOW_SIZE = 65536;
   public static final int HEADER_TABLE_SIZE = 1;
   public static final int ENABLE_PUSH = 2;
   public static final int INITIAL_WINDOW_SIZE = 4;
   public static final int MAX_CONCURRENT_STREAMS = 3;
   public static final int MAX_WINDOW_SIZE = Integer.MAX_VALUE;
   public static final int MAX_FRAME_SIZE = 5;
   public static final int MAX_HEADER_LIST_SIZE = 6;
   protected static final boolean DEFAULT_ENABLE_PUSH = true;
   protected Map current = new HashMap();
   protected Map pending = new HashMap();

   public void set(Integer id, long value) throws Throwable {
      switch (id) {
         case 1:
            this.validateHeaderTableSize(value);
            break;
         case 2:
            this.validateEnablePush(value);
         case 3:
         case 6:
         default:
            break;
         case 4:
            this.validateInitialWindowSize(value);
            break;
         case 5:
            this.validateMaxFrameSize(value);
      }

      this.set(id, value);
   }

   synchronized void set(Integer id, Long value) {
      this.current.put(id, value);
   }

   public int getHeaderTableSize() {
      return this.getMinInt(1);
   }

   public boolean getEnablePush() {
      long result = this.getMin(2);
      return result != 0L;
   }

   public long getMaxConcurrentStreams() {
      return this.getMax(3);
   }

   public int getInitialWindowSize() {
      return this.getMaxInt(4);
   }

   public int getMaxFrameSize() {
      return this.getMaxInt(5);
   }

   public long getMaxHeaderListSize() {
      return this.getMax(6);
   }

   private synchronized long getMin(Integer id) {
      Long pendingValue = (Long)this.pending.get(id);
      Long currentValue = (Long)this.current.get(id);
      if (pendingValue == null) {
         return currentValue;
      } else {
         return currentValue == null ? pendingValue : Math.min(pendingValue, currentValue);
      }
   }

   private synchronized int getMinInt(Integer id) {
      long result = this.getMin(id);
      return result > 2147483647L ? Integer.MAX_VALUE : (int)result;
   }

   private synchronized long getMax(Integer id) {
      Long pendingValue = (Long)this.pending.get(id);
      Long currentValue = (Long)this.current.get(id);
      if (pendingValue == null) {
         return currentValue;
      } else {
         return currentValue == null ? pendingValue : Long.max(pendingValue, currentValue);
      }
   }

   private synchronized int getMaxInt(Integer id) {
      long result = this.getMax(id);
      return result > 2147483647L ? Integer.MAX_VALUE : (int)result;
   }

   private void validateHeaderTableSize(long headerTableSize) throws Throwable {
      if (headerTableSize > 65536L) {
         String msg = MessageManager.getMessage("connectionSettings.headerTableSizeLimit", headerTableSize);
         this.throwException(msg, 1);
      }

   }

   private void validateEnablePush(long enablePush) throws Throwable {
      if (enablePush > 1L) {
         String msg = MessageManager.getMessage("connectionSettings.enablePushInvalid", enablePush);
         this.throwException(msg, 1);
      }

   }

   private void validateInitialWindowSize(long initialWindowSize) throws Throwable {
      if (initialWindowSize > 2147483647L) {
         String msg = MessageManager.getMessage("connectionSettings.windowSizeTooBig", initialWindowSize, Integer.MAX_VALUE);
         this.throwException(msg, 3);
      }

   }

   private void validateMaxFrameSize(long maxFrameSize) throws Throwable {
      if (maxFrameSize < 16384L || maxFrameSize > 16777215L) {
         String msg = MessageManager.getMessage("connectionSettings.maxFrameSizeInvalid", maxFrameSize, 16384, 16777215);
         this.throwException(msg, 1);
      }

   }

   abstract void throwException(String var1, int var2) throws Throwable;
}
