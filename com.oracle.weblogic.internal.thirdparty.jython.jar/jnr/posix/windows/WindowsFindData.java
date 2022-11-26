package jnr.posix.windows;

import jnr.ffi.NativeType;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class WindowsFindData extends CommonFileInformation {
   public static final int MAX_PATH = 260;
   final Struct.UnsignedLong dwFileAttributes = new Struct.UnsignedLong();
   final Struct.UnsignedLong chigh = new Struct.UnsignedLong();
   final Struct.UnsignedLong clow = new Struct.UnsignedLong();
   final Struct.UnsignedLong ahigh = new Struct.UnsignedLong();
   final Struct.UnsignedLong alow = new Struct.UnsignedLong();
   final Struct.UnsignedLong uhigh = new Struct.UnsignedLong();
   final Struct.UnsignedLong ulow = new Struct.UnsignedLong();
   final Struct.UnsignedLong nFileSizeHigh = new Struct.UnsignedLong();
   final Struct.UnsignedLong nFileSizeLow = new Struct.UnsignedLong();
   final Struct.UnsignedLong dwReserved0 = new Struct.UnsignedLong();
   final Struct.UnsignedLong dwReserved1 = new Struct.UnsignedLong();
   final Struct.Padding cFileName;
   final Struct.Padding cAlternateFileName;

   public WindowsFindData(Runtime runtime) {
      super(runtime);
      this.cFileName = new Struct.Padding(NativeType.USHORT, 32767);
      this.cAlternateFileName = new Struct.Padding(NativeType.USHORT, 14);
   }

   public CommonFileInformation.HackyFileTime getCreationTime() {
      return new CommonFileInformation.HackyFileTime(this.chigh, this.clow);
   }

   public CommonFileInformation.HackyFileTime getLastAccessTime() {
      return new CommonFileInformation.HackyFileTime(this.ahigh, this.alow);
   }

   public CommonFileInformation.HackyFileTime getLastWriteTime() {
      return new CommonFileInformation.HackyFileTime(this.uhigh, this.ulow);
   }

   public int getFileAttributes() {
      return this.dwFileAttributes.intValue();
   }

   public long getFileSizeHigh() {
      return this.nFileSizeHigh.longValue();
   }

   public long getFileSizeLow() {
      return this.nFileSizeLow.longValue();
   }
}
