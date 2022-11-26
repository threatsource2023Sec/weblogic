package jnr.posix;

import java.nio.ByteBuffer;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.NulTerminate;
import jnr.ffi.annotations.Out;
import jnr.ffi.annotations.Transient;

public interface LinuxLibC extends UnixLibC {
   int __fxstat(int var1, int var2, @Out @Transient FileStat var3);

   int __lxstat(int var1, CharSequence var2, @Out @Transient FileStat var3);

   int __lxstat(int var1, @NulTerminate @In ByteBuffer var2, @Out @Transient FileStat var3);

   int __xstat(int var1, CharSequence var2, @Out @Transient FileStat var3);

   int __xstat(int var1, @NulTerminate @In ByteBuffer var2, @Out @Transient FileStat var3);

   int __fxstat64(int var1, int var2, @Out @Transient FileStat var3);

   int __lxstat64(int var1, CharSequence var2, @Out @Transient FileStat var3);

   int __lxstat64(int var1, @NulTerminate @In ByteBuffer var2, @Out @Transient FileStat var3);

   int __xstat64(int var1, CharSequence var2, @Out @Transient FileStat var3);

   int __xstat64(int var1, @NulTerminate @In ByteBuffer var2, @Out @Transient FileStat var3);
}
