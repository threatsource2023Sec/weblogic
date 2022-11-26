package jnr.posix;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import jnr.constants.platform.Fcntl;
import jnr.constants.platform.Signal;
import jnr.constants.platform.Sysconf;
import jnr.ffi.Pointer;
import jnr.posix.util.MethodName;
import jnr.posix.util.ProcessMaker;

final class CheckedPOSIX implements POSIX {
   private final POSIX posix;
   private final POSIXHandler handler;

   CheckedPOSIX(POSIX posix, POSIXHandler handler) {
      this.posix = posix;
      this.handler = handler;
   }

   private Object unimplementedNull() {
      this.handler.unimplementedError(MethodName.getCallerMethodName());
      return null;
   }

   private int unimplementedInt() {
      this.handler.unimplementedError(MethodName.getCallerMethodName());
      return -1;
   }

   private boolean unimplementedBool() {
      this.handler.unimplementedError(MethodName.getCallerMethodName());
      return false;
   }

   private String unimplementedString() {
      this.handler.unimplementedError(MethodName.getCallerMethodName());
      return null;
   }

   public ProcessMaker newProcessMaker(String... command) {
      try {
         return this.posix.newProcessMaker(command);
      } catch (UnsatisfiedLinkError var3) {
         return (ProcessMaker)this.unimplementedNull();
      }
   }

   public ProcessMaker newProcessMaker() {
      try {
         return this.posix.newProcessMaker();
      } catch (UnsatisfiedLinkError var2) {
         return (ProcessMaker)this.unimplementedNull();
      }
   }

   public FileStat allocateStat() {
      try {
         return this.posix.allocateStat();
      } catch (UnsatisfiedLinkError var2) {
         return (FileStat)this.unimplementedNull();
      }
   }

   public MsgHdr allocateMsgHdr() {
      try {
         return this.posix.allocateMsgHdr();
      } catch (UnsatisfiedLinkError var2) {
         return (MsgHdr)this.unimplementedNull();
      }
   }

   public int chdir(String path) {
      try {
         return this.posix.chdir(path);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int chmod(String filename, int mode) {
      try {
         return this.posix.chmod(filename, mode);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int fchmod(int fd, int mode) {
      try {
         return this.posix.fchmod(fd, mode);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int chown(String filename, int user, int group) {
      try {
         return this.posix.chown(filename, user, group);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public CharSequence crypt(CharSequence key, CharSequence salt) {
      try {
         return this.posix.crypt(key, salt);
      } catch (UnsatisfiedLinkError var4) {
         return (CharSequence)this.unimplementedNull();
      }
   }

   public byte[] crypt(byte[] key, byte[] salt) {
      try {
         return this.posix.crypt(key, salt);
      } catch (UnsatisfiedLinkError var4) {
         return (byte[])this.unimplementedNull();
      }
   }

   public int fchown(int fd, int user, int group) {
      try {
         return this.posix.fchown(fd, user, group);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int endgrent() {
      try {
         return this.posix.endgrent();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int endpwent() {
      try {
         return this.posix.endpwent();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int errno() {
      return this.posix.errno();
   }

   public void errno(int value) {
      this.posix.errno(value);
   }

   public int exec(String path, String... args) {
      try {
         return this.posix.exec(path, args);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int exec(String path, String[] args, String[] envp) {
      try {
         return this.posix.exec(path, args, envp);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int execv(String path, String[] argv) {
      try {
         return this.posix.execv(path, argv);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int execve(String path, String[] argv, String[] envp) {
      try {
         return this.posix.execve(path, argv, envp);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int fork() {
      try {
         return this.posix.fork();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public FileStat fstat(int fd) {
      try {
         return this.posix.fstat(fd);
      } catch (UnsatisfiedLinkError var3) {
         return (FileStat)this.unimplementedNull();
      }
   }

   public int fstat(int fd, FileStat stat) {
      try {
         return this.posix.fstat(fd, stat);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public FileStat fstat(FileDescriptor descriptor) {
      try {
         return this.posix.fstat(descriptor);
      } catch (UnsatisfiedLinkError var3) {
         return (FileStat)this.unimplementedNull();
      }
   }

   public int fstat(FileDescriptor descriptor, FileStat stat) {
      try {
         return this.posix.fstat(descriptor, stat);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int getegid() {
      try {
         return this.posix.getegid();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int geteuid() {
      try {
         return this.posix.geteuid();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int getgid() {
      try {
         return this.posix.getgid();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int getdtablesize() {
      try {
         return this.posix.getdtablesize();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public Group getgrent() {
      try {
         return this.posix.getgrent();
      } catch (UnsatisfiedLinkError var2) {
         return (Group)this.unimplementedNull();
      }
   }

   public Group getgrgid(int which) {
      try {
         return this.posix.getgrgid(which);
      } catch (UnsatisfiedLinkError var3) {
         return (Group)this.unimplementedNull();
      }
   }

   public Group getgrnam(String which) {
      try {
         return this.posix.getgrnam(which);
      } catch (UnsatisfiedLinkError var3) {
         return (Group)this.unimplementedNull();
      }
   }

   public String getlogin() {
      try {
         return this.posix.getlogin();
      } catch (UnsatisfiedLinkError var2) {
         return (String)this.unimplementedNull();
      }
   }

   public int getpgid() {
      try {
         return this.posix.getpgid();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int getpgid(int pid) {
      try {
         return this.posix.getpgid(pid);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int getpgrp() {
      try {
         return this.posix.getpgrp();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int getpid() {
      try {
         return this.posix.getpid();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int getppid() {
      try {
         return this.posix.getppid();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int getpriority(int which, int who) {
      try {
         return this.posix.getpriority(which, who);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public Passwd getpwent() {
      try {
         return this.posix.getpwent();
      } catch (UnsatisfiedLinkError var2) {
         return (Passwd)this.unimplementedNull();
      }
   }

   public Passwd getpwnam(String which) {
      try {
         return this.posix.getpwnam(which);
      } catch (UnsatisfiedLinkError var3) {
         return (Passwd)this.unimplementedNull();
      }
   }

   public Passwd getpwuid(int which) {
      try {
         return this.posix.getpwuid(which);
      } catch (UnsatisfiedLinkError var3) {
         return (Passwd)this.unimplementedNull();
      }
   }

   public int getuid() {
      try {
         return this.posix.getuid();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int getrlimit(int resource, RLimit rlim) {
      try {
         return this.posix.getrlimit(resource, rlim);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int getrlimit(int resource, Pointer rlim) {
      try {
         return this.posix.getrlimit(resource, rlim);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public RLimit getrlimit(int resource) {
      try {
         return this.posix.getrlimit(resource);
      } catch (UnsatisfiedLinkError var3) {
         return (RLimit)this.unimplementedNull();
      }
   }

   public int setrlimit(int resource, RLimit rlim) {
      try {
         return this.posix.setrlimit(resource, rlim);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int setrlimit(int resource, Pointer rlim) {
      try {
         return this.posix.setrlimit(resource, rlim);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int setrlimit(int resource, long rlimCur, long rlimMax) {
      try {
         return this.posix.setrlimit(resource, rlimCur, rlimMax);
      } catch (UnsatisfiedLinkError var7) {
         return this.unimplementedInt();
      }
   }

   public boolean isatty(FileDescriptor descriptor) {
      try {
         return this.posix.isatty(descriptor);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedBool();
      }
   }

   public int isatty(int descriptor) {
      try {
         return this.posix.isatty(descriptor);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int kill(int pid, int signal) {
      return this.kill((long)pid, signal);
   }

   public int kill(long pid, int signal) {
      try {
         return this.posix.kill(pid, signal);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public SignalHandler signal(Signal sig, SignalHandler handler) {
      try {
         return this.posix.signal(sig, handler);
      } catch (UnsatisfiedLinkError var4) {
         return (SignalHandler)this.unimplementedNull();
      }
   }

   public int lchmod(String filename, int mode) {
      try {
         return this.posix.lchmod(filename, mode);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int lchown(String filename, int user, int group) {
      try {
         return this.posix.lchown(filename, user, group);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int link(String oldpath, String newpath) {
      try {
         return this.posix.link(oldpath, newpath);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public FileStat lstat(String path) {
      try {
         return this.posix.lstat(path);
      } catch (UnsatisfiedLinkError var3) {
         return (FileStat)this.unimplementedNull();
      }
   }

   public int lstat(String path, FileStat stat) {
      try {
         return this.posix.lstat(path, stat);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int mkdir(String path, int mode) {
      try {
         return this.posix.mkdir(path, mode);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public String readlink(String path) throws IOException {
      try {
         return this.posix.readlink(path);
      } catch (UnsatisfiedLinkError var3) {
         return (String)this.unimplementedNull();
      }
   }

   public int readlink(CharSequence path, byte[] buf, int bufsize) {
      try {
         return this.posix.readlink(path, buf, bufsize);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int readlink(CharSequence path, ByteBuffer buf, int bufsize) {
      try {
         return this.posix.readlink(path, buf, bufsize);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int readlink(CharSequence path, Pointer bufPtr, int bufsize) {
      try {
         return this.posix.readlink(path, bufPtr, bufsize);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int rmdir(String path) {
      try {
         return this.posix.rmdir(path);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int setegid(int egid) {
      try {
         return this.posix.setegid(egid);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int seteuid(int euid) {
      try {
         return this.posix.seteuid(euid);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int setgid(int gid) {
      try {
         return this.posix.setgid(gid);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int setgrent() {
      try {
         return this.posix.setgrent();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int setpgid(int pid, int pgid) {
      try {
         return this.posix.setpgid(pid, pgid);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int setpgrp(int pid, int pgrp) {
      try {
         return this.posix.setpgrp(pid, pgrp);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int setpriority(int which, int who, int prio) {
      try {
         return this.posix.setpriority(which, who, prio);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int setpwent() {
      try {
         return this.posix.setpwent();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int setsid() {
      try {
         return this.posix.setsid();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedInt();
      }
   }

   public int setuid(int uid) {
      try {
         return this.posix.setuid(uid);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public FileStat stat(String path) {
      try {
         return this.posix.stat(path);
      } catch (UnsatisfiedLinkError var3) {
         return (FileStat)this.unimplementedNull();
      }
   }

   public int stat(String path, FileStat stat) {
      try {
         return this.posix.stat(path, stat);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int symlink(String oldpath, String newpath) {
      try {
         return this.posix.symlink(oldpath, newpath);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int umask(int mask) {
      try {
         return this.posix.umask(mask);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int utimes(String path, long[] atimeval, long[] mtimeval) {
      try {
         return this.posix.utimes(path, atimeval, mtimeval);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int utimes(String path, Pointer times) {
      try {
         return this.posix.utimes(path, times);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int futimes(int fd, long[] atimeval, long[] mtimeval) {
      try {
         return this.posix.futimes(fd, atimeval, mtimeval);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int lutimes(String path, long[] atimeval, long[] mtimeval) {
      try {
         return this.posix.lutimes(path, atimeval, mtimeval);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int wait(int[] status) {
      try {
         return this.posix.wait(status);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int waitpid(int pid, int[] status, int flags) {
      return this.waitpid((long)pid, status, flags);
   }

   public int waitpid(long pid, int[] status, int flags) {
      try {
         return this.posix.waitpid(pid, status, flags);
      } catch (UnsatisfiedLinkError var6) {
         return this.unimplementedInt();
      }
   }

   public boolean isNative() {
      return this.posix.isNative();
   }

   public LibC libc() {
      return this.posix.libc();
   }

   public Pointer environ() {
      try {
         return this.posix.environ();
      } catch (UnsatisfiedLinkError var2) {
         return (Pointer)this.unimplementedNull();
      }
   }

   public String getenv(String envName) {
      try {
         return this.posix.getenv(envName);
      } catch (UnsatisfiedLinkError var3) {
         return (String)this.unimplementedNull();
      }
   }

   public int setenv(String envName, String envValue, int overwrite) {
      try {
         return this.posix.setenv(envName, envValue, overwrite);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int unsetenv(String envName) {
      try {
         return this.posix.unsetenv(envName);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public long posix_spawnp(String path, Collection fileActions, Collection argv, Collection envp) {
      try {
         return this.posix.posix_spawnp(path, fileActions, argv, envp);
      } catch (UnsatisfiedLinkError var6) {
         return (long)this.unimplementedInt();
      }
   }

   public long posix_spawnp(String path, Collection fileActions, Collection spawnAttributes, Collection argv, Collection envp) {
      try {
         return this.posix.posix_spawnp(path, fileActions, spawnAttributes, argv, envp);
      } catch (UnsatisfiedLinkError var7) {
         return (long)this.unimplementedInt();
      }
   }

   public long sysconf(Sysconf name) {
      try {
         return this.posix.sysconf(name);
      } catch (UnsatisfiedLinkError var3) {
         return (long)this.unimplementedInt();
      }
   }

   public Times times() {
      try {
         return this.posix.times();
      } catch (UnsatisfiedLinkError var2) {
         return (Times)this.unimplementedNull();
      }
   }

   public int flock(int fd, int mode) {
      return this.posix.flock(fd, mode);
   }

   public int dup(int fd) {
      try {
         return this.posix.dup(fd);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int dup2(int oldFd, int newFd) {
      try {
         return this.posix.dup2(oldFd, newFd);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int fcntlInt(int fd, Fcntl fcntlConst, int arg) {
      try {
         return this.posix.fcntlInt(fd, fcntlConst, arg);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int fcntl(int fd, Fcntl fcntlConst) {
      try {
         return this.posix.fcntl(fd, fcntlConst);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int fcntl(int fd, Fcntl fcntlConst, int... arg) {
      try {
         return this.posix.fcntl(fd, fcntlConst);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int access(CharSequence path, int amode) {
      try {
         return this.posix.access(path, amode);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int close(int fd) {
      try {
         return this.posix.close(fd);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int unlink(CharSequence path) {
      try {
         return this.posix.unlink(path);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int open(CharSequence path, int flags, int perm) {
      try {
         return this.posix.open(path, flags, perm);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public long read(int fd, byte[] buf, long n) {
      try {
         return this.posix.read(fd, buf, n);
      } catch (UnsatisfiedLinkError var6) {
         return (long)this.unimplementedInt();
      }
   }

   public long write(int fd, byte[] buf, long n) {
      try {
         return this.posix.write(fd, buf, n);
      } catch (UnsatisfiedLinkError var6) {
         return (long)this.unimplementedInt();
      }
   }

   public long read(int fd, ByteBuffer buf, long n) {
      try {
         return this.posix.read(fd, buf, n);
      } catch (UnsatisfiedLinkError var6) {
         return (long)this.unimplementedInt();
      }
   }

   public long write(int fd, ByteBuffer buf, long n) {
      try {
         return this.posix.write(fd, buf, n);
      } catch (UnsatisfiedLinkError var6) {
         return (long)this.unimplementedInt();
      }
   }

   public long pread(int fd, byte[] buf, long n, long offset) {
      try {
         return this.posix.pread(fd, buf, n, offset);
      } catch (UnsatisfiedLinkError var8) {
         return (long)this.unimplementedInt();
      }
   }

   public long pwrite(int fd, byte[] buf, long n, long offset) {
      try {
         return this.posix.pwrite(fd, buf, n, offset);
      } catch (UnsatisfiedLinkError var8) {
         return (long)this.unimplementedInt();
      }
   }

   public long pread(int fd, ByteBuffer buf, long n, long offset) {
      try {
         return this.posix.pread(fd, buf, n, offset);
      } catch (UnsatisfiedLinkError var8) {
         return (long)this.unimplementedInt();
      }
   }

   public long pwrite(int fd, ByteBuffer buf, long n, long offset) {
      try {
         return this.posix.pwrite(fd, buf, n, offset);
      } catch (UnsatisfiedLinkError var8) {
         return (long)this.unimplementedInt();
      }
   }

   public int read(int fd, byte[] buf, int n) {
      try {
         return this.posix.read(fd, buf, n);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int write(int fd, byte[] buf, int n) {
      try {
         return this.posix.write(fd, buf, n);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int read(int fd, ByteBuffer buf, int n) {
      try {
         return this.posix.read(fd, buf, n);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int write(int fd, ByteBuffer buf, int n) {
      try {
         return this.posix.write(fd, buf, n);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int pread(int fd, byte[] buf, int n, int offset) {
      try {
         return this.posix.pread(fd, buf, n, offset);
      } catch (UnsatisfiedLinkError var6) {
         return this.unimplementedInt();
      }
   }

   public int pwrite(int fd, byte[] buf, int n, int offset) {
      try {
         return this.posix.pwrite(fd, buf, n, offset);
      } catch (UnsatisfiedLinkError var6) {
         return this.unimplementedInt();
      }
   }

   public int pread(int fd, ByteBuffer buf, int n, int offset) {
      try {
         return this.posix.pread(fd, buf, n, offset);
      } catch (UnsatisfiedLinkError var6) {
         return this.unimplementedInt();
      }
   }

   public int pwrite(int fd, ByteBuffer buf, int n, int offset) {
      try {
         return this.posix.pwrite(fd, buf, n, offset);
      } catch (UnsatisfiedLinkError var6) {
         return this.unimplementedInt();
      }
   }

   public int lseek(int fd, long offset, int whence) {
      try {
         return this.posix.lseek(fd, offset, whence);
      } catch (UnsatisfiedLinkError var6) {
         return this.unimplementedInt();
      }
   }

   public long lseekLong(int fd, long offset, int whence) {
      try {
         return this.posix.lseekLong(fd, offset, whence);
      } catch (UnsatisfiedLinkError var6) {
         return (long)this.unimplementedInt();
      }
   }

   public int pipe(int[] fds) {
      try {
         return this.posix.pipe(fds);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int socketpair(int domain, int type, int protocol, int[] fds) {
      try {
         return this.posix.socketpair(domain, type, protocol, fds);
      } catch (UnsatisfiedLinkError var6) {
         return this.unimplementedInt();
      }
   }

   public int sendmsg(int socket, MsgHdr message, int flags) {
      try {
         return this.posix.sendmsg(socket, message, flags);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int recvmsg(int socket, MsgHdr message, int flags) {
      try {
         return this.posix.recvmsg(socket, message, flags);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int truncate(CharSequence path, long length) {
      try {
         return this.posix.truncate(path, length);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int ftruncate(int fd, long offset) {
      try {
         return this.posix.ftruncate(fd, offset);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int rename(CharSequence oldName, CharSequence newName) {
      try {
         return this.posix.rename(oldName, newName);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public String getcwd() {
      try {
         return this.posix.getcwd();
      } catch (UnsatisfiedLinkError var2) {
         return this.unimplementedString();
      }
   }

   public int fsync(int fd) {
      try {
         return this.posix.fsync(fd);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int fdatasync(int fd) {
      try {
         return this.posix.fsync(fd);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }

   public int mkfifo(String path, int mode) {
      try {
         return this.posix.mkfifo(path, mode);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int daemon(int nochdir, int noclose) {
      try {
         return this.posix.daemon(nochdir, noclose);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public long[] getgroups() {
      try {
         return this.posix.getgroups();
      } catch (UnsatisfiedLinkError var2) {
         return null;
      }
   }

   public int getgroups(int size, int[] groups) {
      try {
         return this.posix.getgroups(size, groups);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public String nl_langinfo(int item) {
      try {
         return this.posix.nl_langinfo(item);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedString();
      }
   }

   public String setlocale(int category, String locale) {
      try {
         return this.posix.setlocale(category, locale);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedString();
      }
   }

   public String strerror(int code) {
      try {
         return this.posix.strerror(code);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedString();
      }
   }

   public Timeval allocateTimeval() {
      try {
         return this.posix.allocateTimeval();
      } catch (UnsatisfiedLinkError var2) {
         return (Timeval)this.unimplementedNull();
      }
   }

   public int gettimeofday(Timeval tv) {
      try {
         return this.posix.gettimeofday(tv);
      } catch (UnsatisfiedLinkError var3) {
         return this.unimplementedInt();
      }
   }
}
