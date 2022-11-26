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

final class LazyPOSIX implements POSIX {
   private final POSIXHandler handler;
   private final boolean useNativePosix;
   private volatile POSIX posix;

   LazyPOSIX(POSIXHandler handler, boolean useNativePosix) {
      this.handler = handler;
      this.useNativePosix = useNativePosix;
   }

   private final POSIX posix() {
      return this.posix != null ? this.posix : this.loadPOSIX();
   }

   private final synchronized POSIX loadPOSIX() {
      return this.posix != null ? this.posix : (this.posix = POSIXFactory.loadPOSIX(this.handler, this.useNativePosix));
   }

   public ProcessMaker newProcessMaker(String... command) {
      return this.posix().newProcessMaker(command);
   }

   public ProcessMaker newProcessMaker() {
      return this.posix().newProcessMaker();
   }

   public FileStat allocateStat() {
      return this.posix().allocateStat();
   }

   public MsgHdr allocateMsgHdr() {
      return this.posix().allocateMsgHdr();
   }

   public int chdir(String path) {
      return this.posix().chdir(path);
   }

   public int chmod(String filename, int mode) {
      return this.posix().chmod(filename, mode);
   }

   public int fchmod(int fd, int mode) {
      return this.posix().fchmod(fd, mode);
   }

   public int chown(String filename, int user, int group) {
      return this.posix().chown(filename, user, group);
   }

   public CharSequence crypt(CharSequence key, CharSequence salt) {
      return this.posix().crypt(key, salt);
   }

   public byte[] crypt(byte[] key, byte[] salt) {
      return this.posix().crypt(key, salt);
   }

   public int fchown(int fd, int user, int group) {
      return this.posix().fchown(fd, user, group);
   }

   public int endgrent() {
      return this.posix().endgrent();
   }

   public int endpwent() {
      return this.posix().endpwent();
   }

   public int errno() {
      return this.posix().errno();
   }

   public void errno(int value) {
      this.posix().errno(value);
   }

   public int exec(String path, String... args) {
      return this.posix().exec(path, args);
   }

   public int exec(String path, String[] args, String[] envp) {
      return this.posix().exec(path, args, envp);
   }

   public int execv(String path, String[] argv) {
      return this.posix().execv(path, argv);
   }

   public int execve(String path, String[] argv, String[] envp) {
      return this.posix().execve(path, argv, envp);
   }

   public int fork() {
      return this.posix().fork();
   }

   public FileStat fstat(int fd) {
      return this.posix().fstat(fd);
   }

   public int fstat(int fd, FileStat stat) {
      return this.posix().fstat(fd, stat);
   }

   public FileStat fstat(FileDescriptor descriptor) {
      return this.posix().fstat(descriptor);
   }

   public int fstat(FileDescriptor descriptor, FileStat stat) {
      return this.posix().fstat(descriptor, stat);
   }

   public int getegid() {
      return this.posix().getegid();
   }

   public int geteuid() {
      return this.posix().geteuid();
   }

   public int getgid() {
      return this.posix().getgid();
   }

   public int getdtablesize() {
      return this.posix().getdtablesize();
   }

   public Group getgrent() {
      return this.posix().getgrent();
   }

   public Group getgrgid(int which) {
      return this.posix().getgrgid(which);
   }

   public Group getgrnam(String which) {
      return this.posix().getgrnam(which);
   }

   public String getlogin() {
      return this.posix().getlogin();
   }

   public int getpgid() {
      return this.posix().getpgid();
   }

   public int getpgid(int pid) {
      return this.posix().getpgid(pid);
   }

   public int getpgrp() {
      return this.posix().getpgrp();
   }

   public int getpid() {
      return this.posix().getpid();
   }

   public int getppid() {
      return this.posix().getppid();
   }

   public int getpriority(int which, int who) {
      return this.posix().getpriority(which, who);
   }

   public Passwd getpwent() {
      return this.posix().getpwent();
   }

   public Passwd getpwnam(String which) {
      return this.posix().getpwnam(which);
   }

   public Passwd getpwuid(int which) {
      return this.posix().getpwuid(which);
   }

   public int getuid() {
      return this.posix().getuid();
   }

   public int getrlimit(int resource, RLimit rlim) {
      return this.posix().getrlimit(resource, rlim);
   }

   public int getrlimit(int resource, Pointer rlim) {
      return this.posix().getrlimit(resource, rlim);
   }

   public RLimit getrlimit(int resource) {
      return this.posix().getrlimit(resource);
   }

   public int setrlimit(int resource, RLimit rlim) {
      return this.posix().setrlimit(resource, rlim);
   }

   public int setrlimit(int resource, Pointer rlim) {
      return this.posix().setrlimit(resource, rlim);
   }

   public int setrlimit(int resource, long rlimCur, long rlimMax) {
      return this.posix().setrlimit(resource, rlimCur, rlimMax);
   }

   public boolean isatty(FileDescriptor descriptor) {
      return this.posix().isatty(descriptor);
   }

   public int isatty(int descriptor) {
      return this.posix().isatty(descriptor);
   }

   public int kill(int pid, int signal) {
      return this.kill((long)pid, signal);
   }

   public int kill(long pid, int signal) {
      return this.posix().kill(pid, signal);
   }

   public SignalHandler signal(Signal sig, SignalHandler handler) {
      return this.posix().signal(sig, handler);
   }

   public int lchmod(String filename, int mode) {
      return this.posix().lchmod(filename, mode);
   }

   public int lchown(String filename, int user, int group) {
      return this.posix().lchown(filename, user, group);
   }

   public int link(String oldpath, String newpath) {
      return this.posix().link(oldpath, newpath);
   }

   public FileStat lstat(String path) {
      return this.posix().lstat(path);
   }

   public int lstat(String path, FileStat stat) {
      return this.posix().lstat(path, stat);
   }

   public int mkdir(String path, int mode) {
      return this.posix().mkdir(path, mode);
   }

   public String readlink(String path) throws IOException {
      return this.posix().readlink(path);
   }

   public int readlink(CharSequence path, byte[] buf, int bufsize) {
      return this.posix().readlink(path, buf, bufsize);
   }

   public int readlink(CharSequence path, ByteBuffer buf, int bufsize) {
      return this.posix().readlink(path, buf, bufsize);
   }

   public int readlink(CharSequence path, Pointer bufPtr, int bufsize) {
      return this.posix().readlink(path, bufPtr, bufsize);
   }

   public int rmdir(String path) {
      return this.posix().rmdir(path);
   }

   public int setegid(int egid) {
      return this.posix().setegid(egid);
   }

   public int seteuid(int euid) {
      return this.posix().seteuid(euid);
   }

   public int setgid(int gid) {
      return this.posix().setgid(gid);
   }

   public int setgrent() {
      return this.posix().setgrent();
   }

   public int setpgid(int pid, int pgid) {
      return this.posix().setpgid(pid, pgid);
   }

   public int setpgrp(int pid, int pgrp) {
      return this.posix().setpgrp(pid, pgrp);
   }

   public int setpriority(int which, int who, int prio) {
      return this.posix().setpriority(which, who, prio);
   }

   public int setpwent() {
      return this.posix().setpwent();
   }

   public int setsid() {
      return this.posix().setsid();
   }

   public int setuid(int uid) {
      return this.posix().setuid(uid);
   }

   public FileStat stat(String path) {
      return this.posix().stat(path);
   }

   public int stat(String path, FileStat stat) {
      return this.posix().stat(path, stat);
   }

   public int symlink(String oldpath, String newpath) {
      return this.posix().symlink(oldpath, newpath);
   }

   public int umask(int mask) {
      return this.posix().umask(mask);
   }

   public int utimes(String path, long[] atimeval, long[] mtimeval) {
      return this.posix().utimes(path, atimeval, mtimeval);
   }

   public int utimes(String path, Pointer times) {
      return this.posix().utimes(path, times);
   }

   public int futimes(int fd, long[] atimeval, long[] mtimeval) {
      return this.posix().futimes(fd, atimeval, mtimeval);
   }

   public int lutimes(String path, long[] atimeval, long[] mtimeval) {
      return this.posix().lutimes(path, atimeval, mtimeval);
   }

   public int wait(int[] status) {
      return this.posix().wait(status);
   }

   public int waitpid(int pid, int[] status, int flags) {
      return this.waitpid((long)pid, status, flags);
   }

   public int waitpid(long pid, int[] status, int flags) {
      return this.posix().waitpid(pid, status, flags);
   }

   public boolean isNative() {
      return this.posix().isNative();
   }

   public LibC libc() {
      return this.posix().libc();
   }

   public Pointer environ() {
      return this.posix().environ();
   }

   public String getenv(String envName) {
      return this.posix().getenv(envName);
   }

   public int setenv(String envName, String envValue, int overwrite) {
      return this.posix().setenv(envName, envValue, overwrite);
   }

   public int unsetenv(String envName) {
      return this.posix().unsetenv(envName);
   }

   public long posix_spawnp(String path, Collection fileActions, Collection argv, Collection envp) {
      return this.posix().posix_spawnp(path, fileActions, argv, envp);
   }

   public long posix_spawnp(String path, Collection fileActions, Collection spawnAttributes, Collection argv, Collection envp) {
      return this.posix().posix_spawnp(path, fileActions, spawnAttributes, argv, envp);
   }

   public long sysconf(Sysconf name) {
      return this.posix().sysconf(name);
   }

   public Times times() {
      return this.posix().times();
   }

   public int flock(int fd, int mode) {
      return this.posix().flock(fd, mode);
   }

   public int dup(int fd) {
      return this.posix().dup(fd);
   }

   public int dup2(int oldFd, int newFd) {
      return this.posix().dup2(oldFd, newFd);
   }

   public int fcntlInt(int fd, Fcntl fcntlConst, int arg) {
      return this.posix().fcntlInt(fd, fcntlConst, arg);
   }

   public int fcntl(int fd, Fcntl fcntlConst) {
      return this.posix().fcntl(fd, fcntlConst);
   }

   public int fcntl(int fd, Fcntl fcntlConst, int... arg) {
      return this.posix().fcntl(fd, fcntlConst);
   }

   public int access(CharSequence path, int amode) {
      return this.posix().access(path, amode);
   }

   public int close(int fd) {
      return this.posix().close(fd);
   }

   public int unlink(CharSequence path) {
      return this.posix().unlink(path);
   }

   public int open(CharSequence path, int flags, int perm) {
      return this.posix().open(path, flags, perm);
   }

   public long read(int fd, byte[] buf, long n) {
      return this.posix().read(fd, buf, n);
   }

   public long write(int fd, byte[] buf, long n) {
      return this.posix().write(fd, buf, n);
   }

   public long read(int fd, ByteBuffer buf, long n) {
      return this.posix().read(fd, buf, n);
   }

   public long write(int fd, ByteBuffer buf, long n) {
      return this.posix().write(fd, buf, n);
   }

   public long pread(int fd, byte[] buf, long n, long offset) {
      return this.posix().pread(fd, buf, n, offset);
   }

   public long pwrite(int fd, byte[] buf, long n, long offset) {
      return this.posix().pwrite(fd, buf, n, offset);
   }

   public long pread(int fd, ByteBuffer buf, long n, long offset) {
      return this.posix().pread(fd, buf, n, offset);
   }

   public long pwrite(int fd, ByteBuffer buf, long n, long offset) {
      return this.posix().pwrite(fd, buf, n, offset);
   }

   public int read(int fd, byte[] buf, int n) {
      return this.posix().read(fd, buf, n);
   }

   public int write(int fd, byte[] buf, int n) {
      return this.posix().write(fd, buf, n);
   }

   public int read(int fd, ByteBuffer buf, int n) {
      return this.posix().read(fd, buf, n);
   }

   public int write(int fd, ByteBuffer buf, int n) {
      return this.posix().write(fd, buf, n);
   }

   public int pread(int fd, byte[] buf, int n, int offset) {
      return this.posix().pread(fd, buf, n, offset);
   }

   public int pwrite(int fd, byte[] buf, int n, int offset) {
      return this.posix().pwrite(fd, buf, n, offset);
   }

   public int pread(int fd, ByteBuffer buf, int n, int offset) {
      return this.posix().pread(fd, buf, n, offset);
   }

   public int pwrite(int fd, ByteBuffer buf, int n, int offset) {
      return this.posix().pwrite(fd, buf, n, offset);
   }

   public int lseek(int fd, long offset, int whence) {
      return this.posix().lseek(fd, offset, whence);
   }

   public long lseekLong(int fd, long offset, int whence) {
      return this.posix().lseekLong(fd, offset, whence);
   }

   public int pipe(int[] fds) {
      return this.posix().pipe(fds);
   }

   public int socketpair(int domain, int type, int protocol, int[] fds) {
      return this.posix().socketpair(domain, type, protocol, fds);
   }

   public int sendmsg(int socket, MsgHdr message, int flags) {
      return this.posix().sendmsg(socket, message, flags);
   }

   public int recvmsg(int socket, MsgHdr message, int flags) {
      return this.posix().recvmsg(socket, message, flags);
   }

   public int truncate(CharSequence path, long length) {
      return this.posix().truncate(path, length);
   }

   public int ftruncate(int fd, long offset) {
      return this.posix().ftruncate(fd, offset);
   }

   public int rename(CharSequence oldName, CharSequence newName) {
      return this.posix().rename(oldName, newName);
   }

   public String getcwd() {
      return this.posix().getcwd();
   }

   public int fsync(int fd) {
      return this.posix().fsync(fd);
   }

   public int fdatasync(int fd) {
      return this.posix().fdatasync(fd);
   }

   public int mkfifo(String path, int mode) {
      return this.posix().mkfifo(path, mode);
   }

   public int daemon(int nochdir, int noclose) {
      return this.posix().daemon(nochdir, noclose);
   }

   public long[] getgroups() {
      return this.posix().getgroups();
   }

   public int getgroups(int size, int[] groups) {
      return this.posix().getgroups(size, groups);
   }

   public String nl_langinfo(int item) {
      return this.posix().nl_langinfo(item);
   }

   public String setlocale(int category, String locale) {
      return this.posix().setlocale(category, locale);
   }

   public String strerror(int code) {
      return this.posix().strerror(code);
   }

   public Timeval allocateTimeval() {
      return this.posix().allocateTimeval();
   }

   public int gettimeofday(Timeval tv) {
      return this.posix().gettimeofday(tv);
   }
}
