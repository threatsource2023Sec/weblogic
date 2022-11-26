package jnr.posix;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import jnr.constants.Constant;
import jnr.constants.platform.Errno;
import jnr.constants.platform.Fcntl;
import jnr.constants.platform.Signal;
import jnr.constants.platform.Sysconf;
import jnr.ffi.LastError;
import jnr.ffi.Pointer;
import jnr.ffi.Struct;
import jnr.ffi.TypeAlias;
import jnr.ffi.byref.NumberByReference;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.posix.util.Java5ProcessMaker;
import jnr.posix.util.MethodName;
import jnr.posix.util.ProcessMaker;

public abstract class BaseNativePOSIX extends NativePOSIX implements POSIX {
   private final LibC libc;
   protected final POSIXHandler handler;
   protected final JavaLibCHelper helper;
   protected final Map signalHandlers = new HashMap();
   public static final PointerConverter GROUP = new PointerConverter() {
      public Object fromNative(Object arg, FromNativeContext ctx) {
         return arg != null ? new DefaultNativeGroup((Pointer)arg) : null;
      }
   };
   public static final ToNativeConverter FileStatConverter = new ToNativeConverter() {
      public Pointer toNative(FileStat value, ToNativeContext context) {
         if (value instanceof BaseFileStat) {
            return ((BaseFileStat)value).memory;
         } else if (value instanceof Struct) {
            return Struct.getMemory((Struct)value);
         } else if (value == null) {
            return null;
         } else {
            throw new IllegalArgumentException("instance of " + value.getClass() + " is not a struct");
         }
      }

      public Class nativeType() {
         return Pointer.class;
      }
   };
   public static final ToNativeConverter TimesConverter = new ToNativeConverter() {
      public Pointer toNative(NativeTimes value, ToNativeContext context) {
         return value.memory;
      }

      public Class nativeType() {
         return Pointer.class;
      }
   };
   public static final ToNativeConverter ConstantConverter = new ToNativeConverter() {
      public Integer toNative(Constant value, ToNativeContext context) {
         return value.intValue();
      }

      public Class nativeType() {
         return Integer.class;
      }
   };
   public static final ToNativeConverter MsgHdrConverter = new ToNativeConverter() {
      public Pointer toNative(MsgHdr value, ToNativeContext context) {
         if (value instanceof BaseMsgHdr) {
            return ((BaseMsgHdr)value).memory;
         } else if (value instanceof Struct) {
            return Struct.getMemory((Struct)value);
         } else if (value == null) {
            return null;
         } else {
            throw new IllegalArgumentException("instance of " + value.getClass() + " is not a struct");
         }
      }

      public Class nativeType() {
         return Pointer.class;
      }
   };

   protected BaseNativePOSIX(LibCProvider libcProvider, POSIXHandler handler) {
      this.handler = handler;
      this.libc = libcProvider.getLibC();
      this.helper = new JavaLibCHelper(handler);
   }

   public ProcessMaker newProcessMaker(String... command) {
      return new Java5ProcessMaker(this.handler, command);
   }

   public ProcessMaker newProcessMaker() {
      return new Java5ProcessMaker(this.handler);
   }

   public final LibC libc() {
      return this.libc;
   }

   POSIXHandler handler() {
      return this.handler;
   }

   protected Object unimplementedNull() {
      this.handler().unimplementedError(MethodName.getCallerMethodName());
      return null;
   }

   protected int unimplementedInt() {
      this.handler().unimplementedError(MethodName.getCallerMethodName());
      return -1;
   }

   public int chmod(String filename, int mode) {
      return this.libc().chmod(filename, mode);
   }

   public int fchmod(int fd, int mode) {
      return this.libc().fchmod(fd, mode);
   }

   public int chown(String filename, int user, int group) {
      return this.libc().chown(filename, user, group);
   }

   public int fchown(int fd, int user, int group) {
      return this.libc().fchown(fd, user, group);
   }

   public CharSequence crypt(CharSequence key, CharSequence salt) {
      return this.libc().crypt(key, salt);
   }

   public byte[] crypt(byte[] key, byte[] salt) {
      Pointer ptr = this.libc().crypt(key, salt);
      if (ptr == null) {
         return null;
      } else {
         int end = ptr.indexOf(0L, (byte)0);
         byte[] bytes = new byte[end + 1];
         ptr.get(0L, (byte[])bytes, 0, end);
         return bytes;
      }
   }

   public int exec(String path, String... args) {
      this.handler.unimplementedError("exec unimplemented");
      return -1;
   }

   public int exec(String path, String[] args, String[] envp) {
      this.handler.unimplementedError("exec unimplemented");
      return -1;
   }

   public int execv(String path, String[] args) {
      return this.libc().execv(path, args);
   }

   public int execve(String path, String[] args, String[] env) {
      return this.libc().execve(path, args, env);
   }

   public FileStat fstat(FileDescriptor fileDescriptor) {
      FileStat stat = this.allocateStat();
      if (this.fstat(fileDescriptor, stat) < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "fstat", "" + this.helper.getfd(fileDescriptor));
      }

      return stat;
   }

   public FileStat fstat(int fd) {
      FileStat stat = this.allocateStat();
      if (this.fstat(fd, stat) < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "fstat", "" + fd);
      }

      return stat;
   }

   public int fstat(FileDescriptor fileDescriptor, FileStat stat) {
      int fd = this.helper.getfd(fileDescriptor);
      return this.libc().fstat(fd, stat);
   }

   public int fstat(int fd, FileStat stat) {
      return this.libc().fstat(fd, stat);
   }

   public Pointer environ() {
      return this.getRuntime().getMemoryManager().newPointer((Long)this.libc().environ().get());
   }

   public String getenv(String envName) {
      return this.libc().getenv(envName);
   }

   public int getegid() {
      return this.libc().getegid();
   }

   public int geteuid() {
      return this.libc().geteuid();
   }

   public int getgid() {
      return this.libc().getgid();
   }

   public int getdtablesize() {
      return this.libc().getdtablesize();
   }

   public String getlogin() {
      return this.libc().getlogin();
   }

   public int getpgid() {
      return this.libc().getpgid();
   }

   public int getpgrp() {
      return this.libc().getpgrp();
   }

   public int getpid() {
      return this.libc().getpid();
   }

   public int getppid() {
      return this.libc().getppid();
   }

   public Passwd getpwent() {
      return this.libc().getpwent();
   }

   public Passwd getpwuid(int which) {
      return this.libc().getpwuid(which);
   }

   public Passwd getpwnam(String which) {
      return this.libc().getpwnam(which);
   }

   public Group getgrent() {
      return this.libc().getgrent();
   }

   public Group getgrgid(int which) {
      return this.libc().getgrgid(which);
   }

   public Group getgrnam(String which) {
      return this.libc().getgrnam(which);
   }

   public int setpwent() {
      return this.libc().setpwent();
   }

   public int endpwent() {
      return this.libc().endpwent();
   }

   public int setgrent() {
      return this.libc().setgrent();
   }

   public int endgrent() {
      return this.libc().endgrent();
   }

   public int getuid() {
      return this.libc().getuid();
   }

   public int getrlimit(int resource, RLimit rlim) {
      return this.libc().getrlimit(resource, rlim);
   }

   public int getrlimit(int resource, Pointer rlim) {
      return this.libc().getrlimit(resource, rlim);
   }

   public RLimit getrlimit(int resource) {
      RLimit rlim = new DefaultNativeRLimit(this.getRuntime());
      if (this.getrlimit(resource, (RLimit)rlim) < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "rlim");
      }

      return rlim;
   }

   public int setrlimit(int resource, RLimit rlim) {
      return this.libc().setrlimit(resource, rlim);
   }

   public int setrlimit(int resource, Pointer rlim) {
      return this.libc().setrlimit(resource, rlim);
   }

   public int setrlimit(int resource, long rlimCur, long rlimMax) {
      RLimit rlim = new DefaultNativeRLimit(this.getRuntime());
      rlim.init(rlimCur, rlimMax);
      return this.libc().setrlimit(resource, (RLimit)rlim);
   }

   public int setegid(int egid) {
      return this.libc().setegid(egid);
   }

   public int seteuid(int euid) {
      return this.libc().seteuid(euid);
   }

   public int setgid(int gid) {
      return this.libc().setgid(gid);
   }

   public int getfd(FileDescriptor descriptor) {
      return this.helper.getfd(descriptor);
   }

   public int getpgid(int pid) {
      return this.libc().getpgid(pid);
   }

   public int setpgid(int pid, int pgid) {
      return this.libc().setpgid(pid, pgid);
   }

   public int setpgrp(int pid, int pgrp) {
      return this.libc().setpgrp(pid, pgrp);
   }

   public int setsid() {
      return this.libc().setsid();
   }

   public int setuid(int uid) {
      return this.libc().setuid(uid);
   }

   public int kill(int pid, int signal) {
      return this.kill((long)pid, signal);
   }

   public int kill(long pid, int signal) {
      return this.libc().kill(pid, signal);
   }

   public SignalHandler signal(Signal sig, final SignalHandler handler) {
      synchronized(this.signalHandlers) {
         SignalHandler old = (SignalHandler)this.signalHandlers.get(sig);
         long result = this.libc().signal(sig.intValue(), new LibC.LibCSignalHandler() {
            public void signal(int sig) {
               handler.handle(sig);
            }
         });
         if (result != -1L) {
            this.signalHandlers.put(sig, handler);
         }

         return old;
      }
   }

   public int lchmod(String filename, int mode) {
      try {
         return this.libc().lchmod(filename, mode);
      } catch (UnsatisfiedLinkError var4) {
         return this.unimplementedInt();
      }
   }

   public int lchown(String filename, int user, int group) {
      try {
         return this.libc().lchown(filename, user, group);
      } catch (UnsatisfiedLinkError var5) {
         return this.unimplementedInt();
      }
   }

   public int link(String oldpath, String newpath) {
      return this.libc().link(oldpath, newpath);
   }

   public FileStat lstat(String path) {
      FileStat stat = this.allocateStat();
      if (this.lstat(path, stat) < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "lstat", path);
      }

      return stat;
   }

   public int lstat(String path, FileStat stat) {
      return this.libc().lstat(path, stat);
   }

   public int mkdir(String path, int mode) {
      int res = this.libc().mkdir(path, mode);
      if (res < 0) {
         int errno = this.errno();
         this.handler.error(Errno.valueOf((long)errno), "mkdir", path);
      }

      return res;
   }

   public int rmdir(String path) {
      int res = this.libc().rmdir(path);
      if (res < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "rmdir", path);
      }

      return res;
   }

   public int setenv(String envName, String envValue, int overwrite) {
      return this.libc().setenv(envName, envValue, overwrite);
   }

   public FileStat stat(String path) {
      FileStat stat = this.allocateStat();
      if (this.stat(path, stat) < 0) {
         this.handler.error(Errno.valueOf((long)this.errno()), "stat", path);
      }

      return stat;
   }

   public int stat(String path, FileStat stat) {
      return this.libc().stat(path, stat);
   }

   public int symlink(String oldpath, String newpath) {
      return this.libc().symlink(oldpath, newpath);
   }

   public String readlink(String oldpath) throws IOException {
      ByteBuffer buffer = ByteBuffer.allocate(256);
      int result = this.libc().readlink(oldpath, (ByteBuffer)buffer, buffer.capacity());
      if (result == -1) {
         return null;
      } else {
         buffer.position(0);
         buffer.limit(result);
         return Charset.forName("ASCII").decode(buffer).toString();
      }
   }

   public int readlink(CharSequence path, byte[] buf, int bufsize) {
      return this.libc().readlink(path, buf, bufsize);
   }

   public int readlink(CharSequence path, ByteBuffer buf, int bufsize) {
      return this.libc().readlink(path, buf, bufsize);
   }

   public int readlink(CharSequence path, Pointer bufPtr, int bufsize) {
      return this.libc().readlink(path, bufPtr, bufsize);
   }

   public int unsetenv(String envName) {
      return this.libc().unsetenv(envName);
   }

   public int umask(int mask) {
      return this.libc().umask(mask);
   }

   public int utimes(String path, long[] atimeval, long[] mtimeval) {
      Timeval[] times = null;
      if (atimeval != null && mtimeval != null) {
         times = (Timeval[])Struct.arrayOf(this.getRuntime(), DefaultNativeTimeval.class, 2);
         times[0].setTime(atimeval);
         times[1].setTime(mtimeval);
      }

      return this.libc().utimes((CharSequence)path, (Timeval[])times);
   }

   public int utimes(String path, Pointer times) {
      return this.libc().utimes(path, times);
   }

   public int futimes(int fd, long[] atimeval, long[] mtimeval) {
      Timeval[] times = null;
      if (atimeval != null && mtimeval != null) {
         times = (Timeval[])Struct.arrayOf(this.getRuntime(), DefaultNativeTimeval.class, 2);
         times[0].setTime(atimeval);
         times[1].setTime(mtimeval);
      }

      return this.libc().futimes(fd, times);
   }

   public int lutimes(String path, long[] atimeval, long[] mtimeval) {
      Timeval[] times = null;
      if (atimeval != null && mtimeval != null) {
         times = (Timeval[])Struct.arrayOf(this.getRuntime(), DefaultNativeTimeval.class, 2);
         times[0].setTime(atimeval);
         times[1].setTime(mtimeval);
      }

      return this.libc().lutimes(path, times);
   }

   public int fork() {
      return this.libc().fork();
   }

   public int waitpid(int pid, int[] status, int flags) {
      return this.waitpid((long)pid, status, flags);
   }

   public int waitpid(long pid, int[] status, int flags) {
      return this.libc().waitpid(pid, status, flags);
   }

   public int wait(int[] status) {
      return this.libc().wait(status);
   }

   public int getpriority(int which, int who) {
      return this.libc().getpriority(which, who);
   }

   public int setpriority(int which, int who, int prio) {
      return this.libc().setpriority(which, who, prio);
   }

   public boolean isatty(FileDescriptor fd) {
      return this.isatty(this.helper.getfd(fd)) != 0;
   }

   public int isatty(int fd) {
      return this.libc().isatty(fd);
   }

   public int errno() {
      return LastError.getLastError(this.getRuntime());
   }

   public void errno(int value) {
      LastError.setLastError(this.getRuntime(), value);
   }

   public int chdir(String path) {
      return this.libc().chdir(path);
   }

   public boolean isNative() {
      return true;
   }

   public long posix_spawnp(String path, Collection fileActions, CharSequence[] argv, CharSequence[] envp) {
      return this.posix_spawnp(path, fileActions, (Collection)null, (CharSequence[])argv, (CharSequence[])envp);
   }

   public long posix_spawnp(String path, Collection fileActions, Collection argv, Collection envp) {
      return this.posix_spawnp(path, fileActions, (Collection)null, (Collection)argv, (Collection)envp);
   }

   public long posix_spawnp(String path, Collection fileActions, Collection spawnAttributes, Collection argv, Collection envp) {
      CharSequence[] nativeArgv = new CharSequence[argv.size()];
      argv.toArray(nativeArgv);
      CharSequence[] nativeEnv = new CharSequence[envp.size()];
      envp.toArray(nativeEnv);
      return this.posix_spawnp(path, fileActions, spawnAttributes, nativeArgv, nativeEnv);
   }

   public long posix_spawnp(String path, Collection fileActions, Collection spawnAttributes, CharSequence[] argv, CharSequence[] envp) {
      NumberByReference pid = new NumberByReference(TypeAlias.pid_t);
      Pointer nativeFileActions = fileActions != null && !fileActions.isEmpty() ? this.nativeFileActions(fileActions) : null;
      Pointer nativeSpawnAttributes = spawnAttributes != null && !spawnAttributes.isEmpty() ? this.nativeSpawnAttributes(spawnAttributes) : null;

      long result;
      try {
         result = (long)((UnixLibC)this.libc()).posix_spawnp(pid, path, nativeFileActions, nativeSpawnAttributes, argv, envp);
      } finally {
         if (nativeFileActions != null) {
            ((UnixLibC)this.libc()).posix_spawn_file_actions_destroy(nativeFileActions);
         }

         if (nativeSpawnAttributes != null) {
            ((UnixLibC)this.libc()).posix_spawnattr_destroy(nativeSpawnAttributes);
         }

      }

      return result != 0L ? -1L : pid.longValue();
   }

   public int flock(int fd, int mode) {
      return this.libc().flock(fd, mode);
   }

   public int dup(int fd) {
      return this.libc().dup(fd);
   }

   public int dup2(int oldFd, int newFd) {
      return this.libc().dup2(oldFd, newFd);
   }

   public int fcntlInt(int fd, Fcntl fcntl, int arg) {
      return this.libc().fcntl(fd, fcntl.intValue(), arg);
   }

   public int fcntl(int fd, Fcntl fcntl) {
      return this.libc().fcntl(fd, fcntl.intValue());
   }

   public int fcntl(int fd, Fcntl fcntl, int... arg) {
      return this.libc().fcntl(fd, fcntl.intValue());
   }

   public int access(CharSequence path, int amode) {
      return this.libc().access(path, amode);
   }

   public int close(int fd) {
      return this.libc().close(fd);
   }

   private Pointer nativeFileActions(Collection fileActions) {
      Pointer nativeFileActions = this.allocatePosixSpawnFileActions();
      ((UnixLibC)this.libc()).posix_spawn_file_actions_init(nativeFileActions);
      Iterator var3 = fileActions.iterator();

      while(var3.hasNext()) {
         SpawnFileAction action = (SpawnFileAction)var3.next();
         action.act(this, nativeFileActions);
      }

      return nativeFileActions;
   }

   private Pointer nativeSpawnAttributes(Collection spawnAttributes) {
      Pointer nativeSpawnAttributes = this.allocatePosixSpawnattr();
      ((UnixLibC)this.libc()).posix_spawnattr_init(nativeSpawnAttributes);
      Iterator var3 = spawnAttributes.iterator();

      while(var3.hasNext()) {
         SpawnAttribute action = (SpawnAttribute)var3.next();
         action.set(this, nativeSpawnAttributes);
      }

      return nativeSpawnAttributes;
   }

   public abstract FileStat allocateStat();

   public long sysconf(Sysconf name) {
      switch (name) {
         case _SC_CLK_TCK:
            return 1000L;
         default:
            this.errno(Errno.EOPNOTSUPP.intValue());
            return -1L;
      }
   }

   public Times times() {
      return new JavaTimes();
   }

   public int unlink(CharSequence path) {
      return this.libc().unlink(path);
   }

   public int open(CharSequence path, int flags, int perm) {
      return this.libc().open(path, flags, perm);
   }

   public long read(int fd, byte[] buf, long n) {
      return this.libc().read(fd, buf, n);
   }

   public long write(int fd, byte[] buf, long n) {
      return this.libc().write(fd, buf, n);
   }

   public long read(int fd, ByteBuffer buf, long n) {
      return this.libc().read(fd, buf, n);
   }

   public long write(int fd, ByteBuffer buf, long n) {
      return this.libc().write(fd, buf, n);
   }

   public long pread(int fd, byte[] buf, long n, long offset) {
      return this.libc().pread(fd, buf, n, offset);
   }

   public long pwrite(int fd, byte[] buf, long n, long offset) {
      return this.libc().pwrite(fd, buf, n, offset);
   }

   public long pread(int fd, ByteBuffer buf, long n, long offset) {
      return this.libc().pread(fd, buf, n, offset);
   }

   public long pwrite(int fd, ByteBuffer buf, long n, long offset) {
      return this.libc().pwrite(fd, buf, n, offset);
   }

   public int read(int fd, byte[] buf, int n) {
      return this.libc().read(fd, buf, n);
   }

   public int write(int fd, byte[] buf, int n) {
      return this.libc().write(fd, buf, n);
   }

   public int read(int fd, ByteBuffer buf, int n) {
      return this.libc().read(fd, buf, n);
   }

   public int write(int fd, ByteBuffer buf, int n) {
      return this.libc().write(fd, buf, n);
   }

   public int pread(int fd, byte[] buf, int n, int offset) {
      return this.libc().pread(fd, buf, n, offset);
   }

   public int pwrite(int fd, byte[] buf, int n, int offset) {
      return this.libc().pwrite(fd, buf, n, offset);
   }

   public int pread(int fd, ByteBuffer buf, int n, int offset) {
      return this.libc().pread(fd, buf, n, offset);
   }

   public int pwrite(int fd, ByteBuffer buf, int n, int offset) {
      return this.libc().pwrite(fd, buf, n, offset);
   }

   public int lseek(int fd, long offset, int whence) {
      return (int)this.libc().lseek(fd, offset, whence);
   }

   public long lseekLong(int fd, long offset, int whence) {
      return this.libc().lseek(fd, offset, whence);
   }

   public int pipe(int[] fds) {
      return this.libc().pipe(fds);
   }

   public int socketpair(int domain, int type, int protocol, int[] fds) {
      return this.libc().socketpair(domain, type, protocol, fds);
   }

   public int sendmsg(int socket, MsgHdr message, int flags) {
      return this.libc().sendmsg(socket, message, flags);
   }

   public int recvmsg(int socket, MsgHdr message, int flags) {
      return this.libc().recvmsg(socket, message, flags);
   }

   public int truncate(CharSequence path, long length) {
      return this.libc().truncate(path, length);
   }

   public int ftruncate(int fd, long offset) {
      return this.libc().ftruncate(fd, offset);
   }

   public int rename(CharSequence oldName, CharSequence newName) {
      return this.libc().rename(oldName, newName);
   }

   public String getcwd() {
      byte[] cwd = new byte[1024];
      long result = this.libc().getcwd(cwd, 1024);
      if (result == -1L) {
         return null;
      } else {
         int len;
         for(len = 0; len < 1024 && cwd[len] != 0; ++len) {
         }

         return new String(cwd, 0, len);
      }
   }

   public int fsync(int fd) {
      return this.libc().fsync(fd);
   }

   public int fdatasync(int fd) {
      return this.libc().fdatasync(fd);
   }

   public int mkfifo(String filename, int mode) {
      return ((UnixLibC)this.libc()).mkfifo(filename, mode);
   }

   public int daemon(int nochdir, int noclose) {
      return this.libc().daemon(nochdir, noclose);
   }

   public long[] getgroups() {
      int size = this.getgroups(0, (int[])null);
      int[] groups = new int[size];
      long[] castGroups = new long[size];
      int actualSize = this.getgroups(size, groups);
      if (actualSize == -1) {
         return null;
      } else {
         for(int i = 0; i < actualSize; ++i) {
            castGroups[i] = (long)groups[i] & 4294967295L;
         }

         return actualSize < size ? Arrays.copyOfRange(castGroups, 0, actualSize) : castGroups;
      }
   }

   public int getgroups(int size, int[] groups) {
      return this.libc().getgroups(size, groups);
   }

   public String nl_langinfo(int item) {
      return this.libc().nl_langinfo(item);
   }

   public String setlocale(int category, String locale) {
      return this.libc().setlocale(category, locale);
   }

   public String strerror(int code) {
      return this.libc().strerror(code);
   }

   public Timeval allocateTimeval() {
      return new DefaultNativeTimeval(this.getRuntime());
   }

   public int gettimeofday(Timeval tv) {
      return this.libc().gettimeofday(tv, 0L);
   }

   public abstract static class PointerConverter implements FromNativeConverter {
      public Class nativeType() {
         return Pointer.class;
      }
   }
}
