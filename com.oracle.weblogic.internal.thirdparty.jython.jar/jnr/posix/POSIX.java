package jnr.posix;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import jnr.constants.platform.Fcntl;
import jnr.constants.platform.Signal;
import jnr.constants.platform.Sysconf;
import jnr.ffi.Pointer;
import jnr.posix.util.ProcessMaker;

public interface POSIX {
   CharSequence crypt(CharSequence var1, CharSequence var2);

   byte[] crypt(byte[] var1, byte[] var2);

   FileStat allocateStat();

   int chmod(String var1, int var2);

   int fchmod(int var1, int var2);

   int chown(String var1, int var2, int var3);

   int fchown(int var1, int var2, int var3);

   int exec(String var1, String... var2);

   int exec(String var1, String[] var2, String[] var3);

   int execv(String var1, String[] var2);

   int execve(String var1, String[] var2, String[] var3);

   int fork();

   FileStat fstat(FileDescriptor var1);

   FileStat fstat(int var1);

   int fstat(FileDescriptor var1, FileStat var2);

   int fstat(int var1, FileStat var2);

   Pointer environ();

   String getenv(String var1);

   int getegid();

   int geteuid();

   int seteuid(int var1);

   int getgid();

   int getdtablesize();

   String getlogin();

   int getpgid();

   int getpgid(int var1);

   int getpgrp();

   int getpid();

   int getppid();

   int getpriority(int var1, int var2);

   Passwd getpwent();

   Passwd getpwuid(int var1);

   Passwd getpwnam(String var1);

   Group getgrgid(int var1);

   Group getgrnam(String var1);

   Group getgrent();

   int endgrent();

   int setgrent();

   int endpwent();

   int setpwent();

   int getuid();

   int getrlimit(int var1, RLimit var2);

   int getrlimit(int var1, Pointer var2);

   RLimit getrlimit(int var1);

   int setrlimit(int var1, RLimit var2);

   int setrlimit(int var1, Pointer var2);

   int setrlimit(int var1, long var2, long var4);

   boolean isatty(FileDescriptor var1);

   int isatty(int var1);

   int kill(int var1, int var2);

   int kill(long var1, int var3);

   SignalHandler signal(Signal var1, SignalHandler var2);

   int lchmod(String var1, int var2);

   int lchown(String var1, int var2, int var3);

   int link(String var1, String var2);

   FileStat lstat(String var1);

   int lstat(String var1, FileStat var2);

   int mkdir(String var1, int var2);

   String readlink(String var1) throws IOException;

   int readlink(CharSequence var1, byte[] var2, int var3);

   int readlink(CharSequence var1, ByteBuffer var2, int var3);

   int readlink(CharSequence var1, Pointer var2, int var3);

   int rmdir(String var1);

   int setenv(String var1, String var2, int var3);

   int setsid();

   int setgid(int var1);

   int setegid(int var1);

   int setpgid(int var1, int var2);

   int setpgrp(int var1, int var2);

   int setpriority(int var1, int var2, int var3);

   int setuid(int var1);

   FileStat stat(String var1);

   int stat(String var1, FileStat var2);

   int symlink(String var1, String var2);

   int umask(int var1);

   int unsetenv(String var1);

   int utimes(String var1, long[] var2, long[] var3);

   int utimes(String var1, Pointer var2);

   int futimes(int var1, long[] var2, long[] var3);

   int lutimes(String var1, long[] var2, long[] var3);

   int waitpid(int var1, int[] var2, int var3);

   int waitpid(long var1, int[] var3, int var4);

   int wait(int[] var1);

   int errno();

   void errno(int var1);

   String strerror(int var1);

   int chdir(String var1);

   boolean isNative();

   LibC libc();

   ProcessMaker newProcessMaker(String... var1);

   ProcessMaker newProcessMaker();

   long sysconf(Sysconf var1);

   Times times();

   long posix_spawnp(String var1, Collection var2, Collection var3, Collection var4);

   long posix_spawnp(String var1, Collection var2, Collection var3, Collection var4, Collection var5);

   int flock(int var1, int var2);

   int dup(int var1);

   int dup2(int var1, int var2);

   int fcntlInt(int var1, Fcntl var2, int var3);

   int fcntl(int var1, Fcntl var2);

   int access(CharSequence var1, int var2);

   int close(int var1);

   int unlink(CharSequence var1);

   int open(CharSequence var1, int var2, int var3);

   long read(int var1, byte[] var2, long var3);

   long write(int var1, byte[] var2, long var3);

   long read(int var1, ByteBuffer var2, long var3);

   long write(int var1, ByteBuffer var2, long var3);

   long pread(int var1, byte[] var2, long var3, long var5);

   long pwrite(int var1, byte[] var2, long var3, long var5);

   long pread(int var1, ByteBuffer var2, long var3, long var5);

   long pwrite(int var1, ByteBuffer var2, long var3, long var5);

   int read(int var1, byte[] var2, int var3);

   int write(int var1, byte[] var2, int var3);

   int read(int var1, ByteBuffer var2, int var3);

   int write(int var1, ByteBuffer var2, int var3);

   int pread(int var1, byte[] var2, int var3, int var4);

   int pwrite(int var1, byte[] var2, int var3, int var4);

   int pread(int var1, ByteBuffer var2, int var3, int var4);

   int pwrite(int var1, ByteBuffer var2, int var3, int var4);

   int lseek(int var1, long var2, int var4);

   long lseekLong(int var1, long var2, int var4);

   int pipe(int[] var1);

   int truncate(CharSequence var1, long var2);

   int ftruncate(int var1, long var2);

   int rename(CharSequence var1, CharSequence var2);

   String getcwd();

   int socketpair(int var1, int var2, int var3, int[] var4);

   int sendmsg(int var1, MsgHdr var2, int var3);

   int recvmsg(int var1, MsgHdr var2, int var3);

   MsgHdr allocateMsgHdr();

   /** @deprecated */
   int fcntl(int var1, Fcntl var2, int... var3);

   int fsync(int var1);

   int fdatasync(int var1);

   int mkfifo(String var1, int var2);

   int daemon(int var1, int var2);

   long[] getgroups();

   int getgroups(int var1, int[] var2);

   String nl_langinfo(int var1);

   String setlocale(int var1, String var2);

   Timeval allocateTimeval();

   int gettimeofday(Timeval var1);
}
