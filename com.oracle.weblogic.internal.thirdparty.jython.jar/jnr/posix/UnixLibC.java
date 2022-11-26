package jnr.posix;

import jnr.ffi.Pointer;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.ByReference;
import jnr.ffi.byref.NumberByReference;
import jnr.ffi.byref.ShortByReference;
import jnr.ffi.types.pid_t;

public interface UnixLibC extends LibC {
   int posix_spawn(@Out ByReference var1, @In CharSequence var2, @In Pointer var3, @In Pointer var4, @In CharSequence[] var5, @In CharSequence[] var6);

   int posix_spawnp(@Out ByReference var1, @In CharSequence var2, @In Pointer var3, @In Pointer var4, @In CharSequence[] var5, @In CharSequence[] var6);

   int posix_spawn_file_actions_init(Pointer var1);

   int posix_spawn_file_actions_destroy(Pointer var1);

   int posix_spawn_file_actions_addclose(Pointer var1, int var2);

   int posix_spawn_file_actions_addopen(Pointer var1, int var2, CharSequence var3, int var4, int var5);

   int posix_spawn_file_actions_adddup2(Pointer var1, int var2, int var3);

   int posix_spawnattr_init(Pointer var1);

   int posix_spawnattr_destroy(Pointer var1);

   int posix_spawnattr_setflags(Pointer var1, short var2);

   int posix_spawnattr_getflags(Pointer var1, ShortByReference var2);

   int posix_spawnattr_setpgroup(Pointer var1, @pid_t long var2);

   int posix_spawnattr_getpgroup(Pointer var1, NumberByReference var2);

   int posix_spawnattr_setsigmask(Pointer var1, Pointer var2);

   int posix_spawnattr_getsigmask(Pointer var1, Pointer var2);

   int posix_spawnattr_setsigdefault(Pointer var1, Pointer var2);

   int posix_spawnattr_getsigdefault(Pointer var1, Pointer var2);

   int sigprocmask(int var1, Pointer var2, Pointer var3);

   int mkfifo(CharSequence var1, int var2);
}
