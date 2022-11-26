package jnr.posix.windows;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public abstract class CommonFileInformation extends Struct {
   public static int FILE_ATTRIBUTE_READONLY = 1;
   public static int FILE_ATTRIBUTE_DIRECTORY = 16;
   private static final int MICROSECONDS = 1000000;
   private static final double DAYS_BETWEEN_WINDOWS_AND_UNIX = 134774.4825;
   private static final long MICROSECONDS_TO_UNIX_EPOCH_FROM_WINDOWS = 11644473600000000L;

   protected CommonFileInformation(Runtime runtime) {
      super(runtime);
   }

   public abstract int getFileAttributes();

   public abstract HackyFileTime getCreationTime();

   public abstract HackyFileTime getLastAccessTime();

   public abstract HackyFileTime getLastWriteTime();

   public abstract long getFileSizeHigh();

   public abstract long getFileSizeLow();

   public int getMode(java.lang.String path) {
      int attr = this.getFileAttributes();
      int mode = 256;
      if ((attr & FILE_ATTRIBUTE_READONLY) == 0) {
         mode |= 128;
      }

      mode |= (attr & FILE_ATTRIBUTE_DIRECTORY) != 0 ? 16448 : '耀';
      path = path.toLowerCase();
      if (path != null && (mode & '耀') != 0 && (path.endsWith(".bat") || path.endsWith(".cmd") || path.endsWith(".com") || path.endsWith(".exe"))) {
         mode |= 64;
      }

      mode |= (mode & 448) >> 3;
      mode |= (mode & 448) >> 6;
      return mode;
   }

   public long getLastWriteTimeMicroseconds() {
      return this.asMicroSeconds(this.getLastWriteTime().getLongValue()) / 1000000L;
   }

   public long getLastAccessTimeMicroseconds() {
      return this.asMicroSeconds(this.getLastAccessTime().getLongValue()) / 1000000L;
   }

   public long getCreationTimeMicroseconds() {
      return this.asMicroSeconds(this.getCreationTime().getLongValue()) / 1000000L;
   }

   public long getFileSize() {
      return this.getFileSizeHigh() << 32 | this.getFileSizeLow();
   }

   private long asMicroSeconds(long windowsNanosecondTime) {
      return windowsNanosecondTime / 10L - 11644473600000000L;
   }

   public static long asNanoSeconds(long seconds) {
      return (seconds * 1000L + 11644473600000000L) * 10L;
   }

   public class HackyFileTime {
      private final Struct.UnsignedLong dwHighDateTime;
      private final Struct.UnsignedLong dwLowDateTime;

      public HackyFileTime(Struct.UnsignedLong high, Struct.UnsignedLong low) {
         this.dwHighDateTime = high;
         this.dwLowDateTime = low;
      }

      public long getLowDateTime() {
         return this.dwLowDateTime.longValue();
      }

      public long getHighDateTime() {
         return this.dwHighDateTime.longValue();
      }

      public long getLongValue() {
         return (this.getHighDateTime() & 4294967295L) << 32 | this.getLowDateTime() & 4294967295L;
      }
   }
}
