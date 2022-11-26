package jnr.posix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import jnr.constants.platform.Errno;
import jnr.constants.platform.Fcntl;
import jnr.constants.platform.Signal;
import jnr.constants.platform.Sysconf;
import jnr.ffi.Pointer;
import jnr.posix.util.Java5ProcessMaker;
import jnr.posix.util.MethodName;
import jnr.posix.util.Platform;
import jnr.posix.util.ProcessMaker;

final class JavaPOSIX implements POSIX {
   private final POSIXHandler handler;
   private final JavaLibCHelper helper;

   JavaPOSIX(POSIXHandler handler) {
      this.handler = handler;
      this.helper = new JavaLibCHelper(handler);
   }

   public ProcessMaker newProcessMaker(String... command) {
      return new Java5ProcessMaker(this.handler, command);
   }

   public ProcessMaker newProcessMaker() {
      return new Java5ProcessMaker(this.handler);
   }

   public FileStat allocateStat() {
      return new JavaFileStat(this, this.handler);
   }

   public MsgHdr allocateMsgHdr() {
      this.handler.unimplementedError(MethodName.getCallerMethodName());
      return null;
   }

   public SocketMacros socketMacros() {
      this.handler.unimplementedError(MethodName.getCallerMethodName());
      return null;
   }

   public int chmod(String filename, int mode) {
      return this.helper.chmod(filename, mode);
   }

   public int fchmod(int fd, int mode) {
      this.handler.unimplementedError("No fchmod in Java (yet)");
      return -1;
   }

   public int chown(String filename, int user, int group) {
      return this.helper.chown(filename, user, group);
   }

   public int fchown(int fd, int user, int group) {
      this.handler.unimplementedError("No fchown in Java (yet)");
      return -1;
   }

   public CharSequence crypt(CharSequence key, CharSequence salt) {
      JavaLibCHelper var10000 = this.helper;
      return JavaLibCHelper.crypt(key, salt);
   }

   public byte[] crypt(byte[] key, byte[] salt) {
      JavaLibCHelper var10000 = this.helper;
      return JavaLibCHelper.crypt(key, salt);
   }

   public int exec(String path, String... argv) {
      this.handler.unimplementedError("No exec in Java (yet)");
      return -1;
   }

   public int exec(String path, String[] argv, String[] envp) {
      this.handler.unimplementedError("No exec in Java (yet)");
      return -1;
   }

   public int execv(String path, String[] argv) {
      this.handler.unimplementedError("No execv in Java (yet)");
      return -1;
   }

   public int execve(String path, String[] argv, String[] envp) {
      this.handler.unimplementedError("No execve in Java (yet)");
      return -1;
   }

   public FileStat fstat(FileDescriptor descriptor) {
      this.handler.unimplementedError("fstat unimplemented");
      return null;
   }

   public FileStat fstat(int descriptor) {
      this.handler.unimplementedError("fstat unimplemented");
      return null;
   }

   public int fstat(int fd, FileStat stat) {
      this.handler.unimplementedError("fstat unimplemented");
      return -1;
   }

   public int fstat(FileDescriptor descriptor, FileStat stat) {
      this.handler.unimplementedError("fstat unimplemented");
      return -1;
   }

   public int getegid() {
      return JavaPOSIX.LoginInfo.GID;
   }

   public int geteuid() {
      return JavaPOSIX.LoginInfo.UID;
   }

   public int getgid() {
      return JavaPOSIX.LoginInfo.GID;
   }

   public int getdtablesize() {
      this.handler.unimplementedError("getdtablesize unimplemented");
      return -1;
   }

   public String getlogin() {
      return this.helper.getlogin();
   }

   public int getpgid() {
      return this.unimplementedInt("getpgid");
   }

   public int getpgrp() {
      return this.unimplementedInt("getpgrp");
   }

   public int getpid() {
      return this.helper.getpid();
   }

   public int getppid() {
      return this.unimplementedInt("getppid");
   }

   public Passwd getpwent() {
      return this.helper.getpwent();
   }

   public Passwd getpwuid(int which) {
      return this.helper.getpwuid(which);
   }

   public Group getgrgid(int which) {
      this.handler.unimplementedError("getgrgid unimplemented");
      return null;
   }

   public Passwd getpwnam(String which) {
      this.handler.unimplementedError("getpwnam unimplemented");
      return null;
   }

   public Group getgrnam(String which) {
      this.handler.unimplementedError("getgrnam unimplemented");
      return null;
   }

   public Group getgrent() {
      this.handler.unimplementedError("getgrent unimplemented");
      return null;
   }

   public int setpwent() {
      return this.helper.setpwent();
   }

   public int endpwent() {
      return this.helper.endpwent();
   }

   public int setgrent() {
      return this.unimplementedInt("setgrent");
   }

   public int endgrent() {
      return this.unimplementedInt("endgrent");
   }

   public Pointer environ() {
      this.handler.unimplementedError("environ");
      return null;
   }

   public String getenv(String envName) {
      return (String)this.helper.getEnv().get(envName);
   }

   public int getuid() {
      return JavaPOSIX.LoginInfo.UID;
   }

   public int getrlimit(int resource, RLimit rlim) {
      return this.unimplementedInt("getrlimit");
   }

   public int getrlimit(int resource, Pointer rlim) {
      return this.unimplementedInt("getrlimit");
   }

   public RLimit getrlimit(int resource) {
      this.handler.unimplementedError("getrlimit");
      return null;
   }

   public int setrlimit(int resource, RLimit rlim) {
      return this.unimplementedInt("setrlimit");
   }

   public int setrlimit(int resource, Pointer rlim) {
      return this.unimplementedInt("setrlimit");
   }

   public int setrlimit(int resource, long rlimCur, long rlimMax) {
      return this.unimplementedInt("setrlimit");
   }

   public int fork() {
      return -1;
   }

   public boolean isatty(FileDescriptor fd) {
      return fd == FileDescriptor.in || fd == FileDescriptor.out || fd == FileDescriptor.err;
   }

   public int isatty(int fd) {
      return fd != 0 && fd != 1 && fd != 2 ? 0 : 1;
   }

   public int kill(int pid, int signal) {
      return this.unimplementedInt("kill");
   }

   public int kill(long pid, int signal) {
      return this.unimplementedInt("kill");
   }

   public SignalHandler signal(Signal sig, SignalHandler handler) {
      sun.misc.Signal s = new sun.misc.Signal(sig.name().substring("SIG".length()));
      sun.misc.SignalHandler oldHandler = sun.misc.Signal.handle(s, new SunMiscSignalHandler(handler));
      return oldHandler instanceof SunMiscSignalHandler ? ((SunMiscSignalHandler)oldHandler).handler : null;
   }

   public int lchmod(String filename, int mode) {
      return this.unimplementedInt("lchmod");
   }

   public int lchown(String filename, int user, int group) {
      return this.unimplementedInt("lchown");
   }

   public int link(String oldpath, String newpath) {
      return this.helper.link(oldpath, newpath);
   }

   public FileStat lstat(String path) {
      FileStat stat = this.allocateStat();
      if (this.lstat(path, stat) < 0) {
         this.handler.error(Errno.ENOENT, "lstat", path);
      }

      return stat;
   }

   public int lstat(String path, FileStat stat) {
      return this.helper.lstat(path, stat);
   }

   public int mkdir(String path, int mode) {
      return this.helper.mkdir(path, mode);
   }

   public int rmdir(String path) {
      return this.helper.rmdir(path);
   }

   public String readlink(String path) throws IOException {
      ByteBuffer buffer = ByteBuffer.allocateDirect(256);
      int result = this.helper.readlink(path, buffer, buffer.capacity());
      if (result == -1) {
         return null;
      } else {
         buffer.position(0);
         buffer.limit(result);
         return Charset.forName("ASCII").decode(buffer).toString();
      }
   }

   public int readlink(CharSequence path, byte[] buf, int bufsize) {
      this.handler.unimplementedError("readlink");
      return -1;
   }

   public int readlink(CharSequence path, ByteBuffer buf, int bufsize) {
      this.handler.unimplementedError("readlink");
      return -1;
   }

   public int readlink(CharSequence path, Pointer bufPtr, int bufsize) {
      this.handler.unimplementedError("readlink");
      return -1;
   }

   public int setenv(String envName, String envValue, int overwrite) {
      Map env = this.helper.getEnv();
      if (envName.contains("=")) {
         this.handler.error(Errno.EINVAL, "setenv", envName);
         return -1;
      } else if (overwrite == 0 && env.containsKey(envName)) {
         return 0;
      } else {
         env.put(envName, envValue);
         return 0;
      }
   }

   public FileStat stat(String path) {
      FileStat stat = this.allocateStat();
      if (this.helper.stat(path, stat) < 0) {
         this.handler.error(Errno.ENOENT, "stat", path);
      }

      return stat;
   }

   public int stat(String path, FileStat stat) {
      return this.helper.stat(path, stat);
   }

   public int symlink(String oldpath, String newpath) {
      return this.helper.symlink(oldpath, newpath);
   }

   public int setegid(int egid) {
      return this.unimplementedInt("setegid");
   }

   public int seteuid(int euid) {
      return this.unimplementedInt("seteuid");
   }

   public int setgid(int gid) {
      return this.unimplementedInt("setgid");
   }

   public int getpgid(int pid) {
      return this.unimplementedInt("getpgid");
   }

   public int setpgid(int pid, int pgid) {
      return this.unimplementedInt("setpgid");
   }

   public int setpgrp(int pid, int pgrp) {
      return this.unimplementedInt("setpgrp");
   }

   public int setsid() {
      return this.unimplementedInt("setsid");
   }

   public int setuid(int uid) {
      return this.unimplementedInt("setuid");
   }

   public int umask(int mask) {
      return 0;
   }

   public int unsetenv(String envName) {
      if (this.helper.getEnv().remove(envName) == null) {
         this.handler.error(Errno.EINVAL, "unsetenv", envName);
         return -1;
      } else {
         return 0;
      }
   }

   public int utimes(String path, long[] atimeval, long[] mtimeval) {
      long mtimeMillis;
      if (mtimeval != null) {
         assert mtimeval.length == 2;

         mtimeMillis = mtimeval[0] * 1000L + mtimeval[1] / 1000L;
      } else {
         mtimeMillis = System.currentTimeMillis();
      }

      (new File(path)).setLastModified(mtimeMillis);
      return 0;
   }

   public int utimes(String path, Pointer times) {
      return this.unimplementedInt("utimes");
   }

   public int futimes(int fd, long[] atimeval, long[] mtimeval) {
      this.handler.unimplementedError("futimes");
      return this.unimplementedInt("futimes");
   }

   public int lutimes(String path, long[] atimeval, long[] mtimeval) {
      this.handler.unimplementedError("lutimes");
      return this.unimplementedInt("lutimes");
   }

   public int wait(int[] status) {
      return this.unimplementedInt("wait");
   }

   public int waitpid(int pid, int[] status, int flags) {
      return this.unimplementedInt("waitpid");
   }

   public int waitpid(long pid, int[] status, int flags) {
      return this.unimplementedInt("waitpid");
   }

   public int getpriority(int which, int who) {
      return this.unimplementedInt("getpriority");
   }

   public int setpriority(int which, int who, int prio) {
      return this.unimplementedInt("setpriority");
   }

   public long posix_spawnp(String path, Collection fileActions, Collection argv, Collection envp) {
      return (long)this.unimplementedInt("posix_spawnp");
   }

   public long posix_spawnp(String path, Collection fileActions, Collection spawnAttributes, Collection argv, Collection envp) {
      return (long)this.unimplementedInt("posix_spawnp");
   }

   public int errno() {
      return JavaLibCHelper.errno();
   }

   public void errno(int value) {
      JavaLibCHelper.errno(value);
   }

   public int chdir(String path) {
      return JavaLibCHelper.chdir(path);
   }

   public boolean isNative() {
      return false;
   }

   public LibC libc() {
      return null;
   }

   private int unimplementedInt(String message) {
      this.handler.unimplementedError(message);
      return -1;
   }

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

   public int flock(int fd, int mode) {
      return this.unimplementedInt("flock");
   }

   public int dup(int fd) {
      return this.unimplementedInt("dup");
   }

   public int dup2(int oldFd, int newFd) {
      return this.unimplementedInt("dup2");
   }

   public int fcntlInt(int fd, Fcntl fcntlConst, int arg) {
      return this.unimplementedInt("fcntl");
   }

   public int fcntl(int fd, Fcntl fcntlConst) {
      return this.unimplementedInt("fcntl");
   }

   public int fcntl(int fd, Fcntl fcntlConst, int... arg) {
      return this.unimplementedInt("fcntl");
   }

   public int access(CharSequence path, int amode) {
      this.handler.unimplementedError("access");
      return -1;
   }

   public int close(int fd) {
      return this.unimplementedInt("close");
   }

   public int unlink(CharSequence path) {
      this.handler.unimplementedError("unlink");
      return -1;
   }

   public int open(CharSequence path, int flags, int perm) {
      this.handler.unimplementedError("open");
      return -1;
   }

   public long read(int fd, byte[] buf, long n) {
      this.handler.unimplementedError("read");
      return -1L;
   }

   public long write(int fd, byte[] buf, long n) {
      this.handler.unimplementedError("write");
      return -1L;
   }

   public long read(int fd, ByteBuffer buf, long n) {
      this.handler.unimplementedError("read");
      return -1L;
   }

   public long write(int fd, ByteBuffer buf, long n) {
      this.handler.unimplementedError("write");
      return -1L;
   }

   public long pread(int fd, byte[] buf, long n, long offset) {
      this.handler.unimplementedError("pread");
      return -1L;
   }

   public long pwrite(int fd, byte[] buf, long n, long offset) {
      this.handler.unimplementedError("pwrite");
      return -1L;
   }

   public long pread(int fd, ByteBuffer buf, long n, long offset) {
      this.handler.unimplementedError("pread");
      return -1L;
   }

   public long pwrite(int fd, ByteBuffer buf, long n, long offset) {
      this.handler.unimplementedError("pwrite");
      return -1L;
   }

   public int read(int fd, byte[] buf, int n) {
      this.handler.unimplementedError("read");
      return -1;
   }

   public int write(int fd, byte[] buf, int n) {
      this.handler.unimplementedError("write");
      return -1;
   }

   public int read(int fd, ByteBuffer buf, int n) {
      this.handler.unimplementedError("read");
      return -1;
   }

   public int write(int fd, ByteBuffer buf, int n) {
      this.handler.unimplementedError("write");
      return -1;
   }

   public int pread(int fd, byte[] buf, int n, int offset) {
      this.handler.unimplementedError("pread");
      return -1;
   }

   public int pwrite(int fd, byte[] buf, int n, int offset) {
      this.handler.unimplementedError("pwrite");
      return -1;
   }

   public int pread(int fd, ByteBuffer buf, int n, int offset) {
      this.handler.unimplementedError("pread");
      return -1;
   }

   public int pwrite(int fd, ByteBuffer buf, int n, int offset) {
      this.handler.unimplementedError("pwrite");
      return -1;
   }

   public int lseek(int fd, long offset, int whence) {
      this.handler.unimplementedError("lseek");
      return -1;
   }

   public long lseekLong(int fd, long offset, int whence) {
      this.handler.unimplementedError("lseek");
      return -1L;
   }

   public int pipe(int[] fds) {
      this.handler.unimplementedError("pipe");
      return -1;
   }

   public int socketpair(int domain, int type, int protocol, int[] fds) {
      this.handler.unimplementedError("socketpair");
      return -1;
   }

   public int sendmsg(int socket, MsgHdr message, int flags) {
      this.handler.unimplementedError("sendmsg");
      return -1;
   }

   public int recvmsg(int socket, MsgHdr message, int flags) {
      this.handler.unimplementedError("recvmsg");
      return -1;
   }

   public int truncate(CharSequence path, long length) {
      this.handler.unimplementedError("truncate");
      return -1;
   }

   public int ftruncate(int fd, long offset) {
      this.handler.unimplementedError("ftruncate");
      return -1;
   }

   public int rename(CharSequence oldName, CharSequence newName) {
      File oldFile = new File(oldName.toString());
      File newFile = new File(newName.toString());
      return oldFile.renameTo(newFile) ? 0 : -1;
   }

   public String getcwd() {
      return System.getProperty("user.dir");
   }

   public int fsync(int fd) {
      this.handler.unimplementedError("fsync");
      return this.unimplementedInt("fsync not available for Java");
   }

   public int fdatasync(int fd) {
      this.handler.unimplementedError("fdatasync");
      return this.unimplementedInt("fdatasync not available for Java");
   }

   public int mkfifo(String filename, int mode) {
      this.handler.unimplementedError("mkfifo");
      return this.unimplementedInt("mkfifo not available for Java");
   }

   public int daemon(int nochdir, int noclose) {
      this.handler.unimplementedError("daemon");
      return this.unimplementedInt("daemon not available for Java");
   }

   public long[] getgroups() {
      this.handler.unimplementedError("getgroups");
      return null;
   }

   public int getgroups(int size, int[] groups) {
      this.handler.unimplementedError("getgroups");
      return this.unimplementedInt("getgroups not available for Java");
   }

   public String nl_langinfo(int item) {
      this.handler.unimplementedError("nl_langinfo");
      return null;
   }

   public String setlocale(int category, String locale) {
      this.handler.unimplementedError("setlocale");
      return null;
   }

   public String strerror(int code) {
      this.handler.unimplementedError("strerror");
      return null;
   }

   public Timeval allocateTimeval() {
      this.handler.unimplementedError("allocateTimeval");
      return null;
   }

   public int gettimeofday(Timeval tv) {
      this.handler.unimplementedError("gettimeofday");
      return -1;
   }

   private static final class FakePasswd implements Passwd {
      public String getLoginName() {
         return JavaPOSIX.LoginInfo.USERNAME;
      }

      public String getPassword() {
         return "";
      }

      public long getUID() {
         return (long)JavaPOSIX.LoginInfo.UID;
      }

      public long getGID() {
         return (long)JavaPOSIX.LoginInfo.GID;
      }

      public int getPasswdChangeTime() {
         return 0;
      }

      public String getAccessClass() {
         return "";
      }

      public String getGECOS() {
         return this.getLoginName();
      }

      public String getHome() {
         return "/";
      }

      public String getShell() {
         return "/bin/sh";
      }

      public int getExpire() {
         return -1;
      }
   }

   private static final class IDHelper {
      private static final String ID_CMD;
      private static final int NOBODY;

      public static int getInt(String option) {
         try {
            Process p = Runtime.getRuntime().exec(new String[]{ID_CMD, option});
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return Integer.parseInt(r.readLine());
         } catch (IOException var3) {
            return NOBODY;
         } catch (NumberFormatException var4) {
            return NOBODY;
         } catch (SecurityException var5) {
            return NOBODY;
         }
      }

      public static String getString(String option) {
         try {
            Process p = Runtime.getRuntime().exec(new String[]{ID_CMD, option});
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return r.readLine();
         } catch (IOException var3) {
            return null;
         }
      }

      static {
         ID_CMD = Platform.IS_SOLARIS ? "/usr/xpg4/bin/id" : "/usr/bin/id";
         NOBODY = Platform.IS_WINDOWS ? 0 : 32767;
      }
   }

   static final class LoginInfo {
      public static final int UID = JavaPOSIX.IDHelper.getInt("-u");
      public static final int GID = JavaPOSIX.IDHelper.getInt("-g");
      public static final String USERNAME = JavaPOSIX.IDHelper.getString("-un");
   }

   private static class SunMiscSignalHandler implements sun.misc.SignalHandler {
      final SignalHandler handler;

      public SunMiscSignalHandler(SignalHandler handler) {
         this.handler = handler;
      }

      public void handle(sun.misc.Signal signal) {
         this.handler.handle(signal.getNumber());
      }
   }
}
