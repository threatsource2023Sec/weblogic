package jnr.posix.windows;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public class WindowsFileInformation extends CommonFileInformation {
   final Struct.UnsignedLong dwFileAttributes = new Struct.UnsignedLong();
   final Struct.UnsignedLong chigh = new Struct.UnsignedLong();
   final Struct.UnsignedLong clow = new Struct.UnsignedLong();
   final Struct.UnsignedLong ahigh = new Struct.UnsignedLong();
   final Struct.UnsignedLong alow = new Struct.UnsignedLong();
   final Struct.UnsignedLong uhigh = new Struct.UnsignedLong();
   final Struct.UnsignedLong ulow = new Struct.UnsignedLong();
   final Struct.UnsignedLong nFileSizeHigh = new Struct.UnsignedLong();
   final Struct.UnsignedLong nFileSizeLow = new Struct.UnsignedLong();

   public WindowsFileInformation(Runtime runtime) {
      super(runtime);
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
