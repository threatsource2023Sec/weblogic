package jnr.posix;

import java.nio.ByteBuffer;
import jnr.constants.platform.Sysconf;
import jnr.ffi.Pointer;
import jnr.ffi.Variable;
import jnr.ffi.annotations.Delegate;
import jnr.ffi.annotations.Direct;
import jnr.ffi.annotations.IgnoreError;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.annotations.Transient;
import jnr.ffi.types.clock_t;
import jnr.ffi.types.intptr_t;
import jnr.ffi.types.off_t;
import jnr.ffi.types.size_t;
import jnr.ffi.types.ssize_t;

public interface LibC {
   CharSequence crypt(CharSequence var1, CharSequence var2);

   Pointer crypt(byte[] var1, byte[] var2);

   int chmod(CharSequence var1, int var2);

   int fchmod(int var1, int var2);

   int chown(CharSequence var1, int var2, int var3);

   int fchown(int var1, int var2, int var3);

   int fstat(int var1, @Out @Transient FileStat var2);

   int fstat64(int var1, @Out @Transient FileStat var2);

   String getenv(CharSequence var1);

   @IgnoreError
   int getegid();

   int setegid(int var1);

   @IgnoreError
   int geteuid();

   int seteuid(int var1);

   @IgnoreError
   int getgid();

   String getlogin();

   int setgid(int var1);

   int getpgid();

   int getpgid(int var1);

   int setpgid(int var1, int var2);

   int getpgrp();

   int setpgrp(int var1, int var2);

   @IgnoreError
   int getppid();

   @IgnoreError
   int getpid();

   NativePasswd getpwent();

   NativePasswd getpwuid(int var1);

   NativePasswd getpwnam(CharSequence var1);

   NativeGroup getgrent();

   NativeGroup getgrgid(int var1);

   NativeGroup getgrnam(CharSequence var1);

   int setpwent();

   int endpwent();

   int setgrent();

   int endgrent();

   @IgnoreError
   int getuid();

   int setsid();

   int setuid(int var1);

   int getrlimit(int var1, @Out RLimit var2);

   int getrlimit(int var1, Pointer var2);

   int setrlimit(int var1, @In RLimit var2);

   int setrlimit(int var1, Pointer var2);

   int kill(int var1, int var2);

   int kill(long var1, int var3);

   int dup(int var1);

   int dup2(int var1, int var2);

   int fcntl(int var1, int var2, Pointer var3);

   int fcntl(int var1, int var2);

   int fcntl(int var1, int var2, int var3);

   /** @deprecated */
   @Deprecated
   int fcntl(int var1, int var2, int... var3);

   int access(CharSequence var1, int var2);

   int getdtablesize();

   @intptr_t
   long signal(int var1, LibCSignalHandler var2);

   int lchmod(CharSequence var1, int var2);

   int lchown(CharSequence var1, int var2, int var3);

   int link(CharSequence var1, CharSequence var2);

   int lstat(CharSequence var1, @Out @Transient FileStat var2);

   int lstat64(CharSequence var1, @Out @Transient FileStat var2);

   int mkdir(CharSequence var1, int var2);

   int rmdir(CharSequence var1);

   int stat(CharSequence var1, @Out @Transient FileStat var2);

   int stat64(CharSequence var1, @Out @Transient FileStat var2);

   int symlink(CharSequence var1, CharSequence var2);

   int readlink(CharSequence var1, @Out ByteBuffer var2, int var3);

   int readlink(CharSequence var1, @Out byte[] var2, int var3);

   int readlink(CharSequence var1, Pointer var2, int var3);

   int setenv(CharSequence var1, CharSequence var2, int var3);

   @IgnoreError
   int umask(int var1);

   int unsetenv(CharSequence var1);

   int utimes(CharSequence var1, @In Timeval[] var2);

   int utimes(String var1, @In Pointer var2);

   int futimes(int var1, @In Timeval[] var2);

   int lutimes(CharSequence var1, @In Timeval[] var2);

   int fork();

   int waitpid(long var1, @Out int[] var3, int var4);

   int wait(@Out int[] var1);

   int getpriority(int var1, int var2);

   int setpriority(int var1, int var2, int var3);

   @IgnoreError
   int isatty(int var1);

   @ssize_t
   long read(int var1, @Out byte[] var2, @size_t long var3);

   @ssize_t
   long write(int var1, @In byte[] var2, @size_t long var3);

   @ssize_t
   long read(int var1, @Out ByteBuffer var2, @size_t long var3);

   @ssize_t
   long write(int var1, @In ByteBuffer var2, @size_t long var3);

   @ssize_t
   long pread(int var1, @Out byte[] var2, @size_t long var3, @off_t long var5);

   @ssize_t
   long pwrite(int var1, @In byte[] var2, @size_t long var3, @off_t long var5);

   @ssize_t
   long pread(int var1, @Out ByteBuffer var2, @size_t long var3, @off_t long var5);

   @ssize_t
   long pwrite(int var1, @In ByteBuffer var2, @size_t long var3, @off_t long var5);

   int read(int var1, @Out byte[] var2, int var3);

   int write(int var1, @In byte[] var2, int var3);

   int read(int var1, @Out ByteBuffer var2, int var3);

   int write(int var1, @In ByteBuffer var2, int var3);

   int pread(int var1, @Out byte[] var2, int var3, int var4);

   int pwrite(int var1, @In byte[] var2, int var3, int var4);

   int pread(int var1, @Out ByteBuffer var2, int var3, int var4);

   int pwrite(int var1, @In ByteBuffer var2, int var3, int var4);

   long lseek(int var1, long var2, int var4);

   int close(int var1);

   int execv(CharSequence var1, @In CharSequence[] var2);

   int execve(CharSequence var1, @In CharSequence[] var2, @In CharSequence[] var3);

   int chdir(CharSequence var1);

   long sysconf(Sysconf var1);

   @clock_t
   long times(@Out @Transient NativeTimes var1);

   int flock(int var1, int var2);

   int unlink(CharSequence var1);

   int open(CharSequence var1, int var2, int var3);

   int pipe(@Out int[] var1);

   int truncate(CharSequence var1, long var2);

   int ftruncate(int var1, long var2);

   int rename(CharSequence var1, CharSequence var2);

   long getcwd(byte[] var1, int var2);

   int fsync(int var1);

   int fdatasync(int var1);

   int socketpair(int var1, int var2, int var3, @Out int[] var4);

   int sendmsg(int var1, @In MsgHdr var2, int var3);

   int recvmsg(int var1, @Direct MsgHdr var2, int var3);

   Variable environ();

   int syscall(int var1);

   int syscall(int var1, int var2);

   int syscall(int var1, int var2, int var3);

   int syscall(int var1, int var2, int var3, int var4);

   int daemon(int var1, int var2);

   int getgroups(int var1, int[] var2);

   String nl_langinfo(int var1);

   String setlocale(int var1, String var2);

   String strerror(int var1);

   int gettimeofday(Timeval var1, long var2);

   public interface LibCSignalHandler {
      @Delegate
      void signal(int var1);
   }
}
