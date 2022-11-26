package org.python.modules.posix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotLinkException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jnr.constants.Constant;
import jnr.constants.platform.Errno;
import jnr.constants.platform.Sysconf;
import jnr.posix.FileStat;
import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;
import jnr.posix.POSIXHandler;
import jnr.posix.Times;
import jnr.posix.WindowsRawFileStat;
import jnr.posix.util.FieldAccess;
import jnr.posix.windows.CommonFileInformation;
import org.python.core.BufferProtocol;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyBuffer;
import org.python.core.PyBuiltinFunctionNarrow;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFile;
import org.python.core.PyFloat;
import org.python.core.PyJavaType;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.Untraversable;
import org.python.core.imp;
import org.python.core.io.FileIO;
import org.python.core.io.IOBase;
import org.python.core.io.RawIOBase;
import org.python.core.io.StreamIO;
import org.python.core.util.StringUtil;

public class PosixModule implements ClassDictInit {
   public static final PyString __doc__ = new PyString("This module provides access to operating system functionality that is\nstandardized by the C Standard and the POSIX standard (a thinly\ndisguised Unix interface).  Refer to the library manual and\ncorresponding Unix manual entries for more information on calls.");
   private static final OS os = OS.getOS();
   private static final POSIXHandler posixHandler = new PythonPOSIXHandler();
   private static final POSIX posix;
   private static final int O_RDONLY = 0;
   private static final int O_WRONLY = 1;
   private static final int O_RDWR = 2;
   private static final int O_APPEND = 8;
   private static final int O_SYNC = 128;
   private static final int O_CREAT = 512;
   private static final int O_TRUNC = 1024;
   private static final int O_EXCL = 2048;
   private static final int F_OK = 0;
   private static final int X_OK = 1;
   private static final int W_OK = 2;
   private static final int R_OK = 4;
   public static PyString __doc___exit;
   public static PyString __doc__access;
   public static PyString __doc__chdir;
   public static PyString __doc__chmod;
   public static PyString __doc__chown;
   public static PyString __doc__close;
   public static PyString __doc__fdopen;
   public static PyString __doc__fdatasync;
   public static PyString __doc__fsync;
   public static PyString __doc__ftruncate;
   public static PyString __doc__getcwd;
   public static PyString __doc__getcwdu;
   public static PyString __doc__getegid;
   public static PyString __doc__geteuid;
   public static PyString __doc__getgid;
   public static PyString __doc__getlogin;
   public static PyString __doc__getppid;
   public static PyString __doc__getuid;
   public static PyString __doc__getpid;
   public static PyString __doc__getpgrp;
   public static PyString __doc__isatty;
   public static PyString __doc__kill;
   public static PyString __doc__lchmod;
   public static PyString __doc__lchown;
   public static PyString __doc__link;
   public static PyString __doc__listdir;
   public static PyString __doc__lseek;
   public static PyString __doc__mkdir;
   public static PyString __doc__open;
   public static PyString __doc__pipe;
   public static PyString __doc__popen;
   public static PyString __doc__putenv;
   public static PyString __doc__read;
   public static PyString __doc__readlink;
   public static PyString __doc__remove;
   public static PyString __doc__rename;
   public static PyString __doc__rmdir;
   public static PyString __doc__setpgrp;
   public static PyString __doc__setsid;
   public static PyString __doc__strerror;
   public static PyString __doc__symlink;
   public static PyString __doc__times;
   public static PyString __doc__umask;
   public static PyString __doc__uname;
   private static PyTuple uname_cache;
   public static PyString __doc__unlink;
   public static PyString __doc__utime;
   public static PyString __doc__wait;
   public static PyString __doc__waitpid;
   public static PyString __doc__write;
   public static PyString __doc__unsetenv;
   public static PyString __doc__urandom;

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"O_RDONLY", Py.newInteger(0));
      dict.__setitem__((String)"O_WRONLY", Py.newInteger(1));
      dict.__setitem__((String)"O_RDWR", Py.newInteger(2));
      dict.__setitem__((String)"O_APPEND", Py.newInteger(8));
      dict.__setitem__((String)"O_SYNC", Py.newInteger(128));
      dict.__setitem__((String)"O_CREAT", Py.newInteger(512));
      dict.__setitem__((String)"O_TRUNC", Py.newInteger(1024));
      dict.__setitem__((String)"O_EXCL", Py.newInteger(2048));
      dict.__setitem__((String)"F_OK", Py.newInteger(0));
      dict.__setitem__((String)"X_OK", Py.newInteger(1));
      dict.__setitem__((String)"W_OK", Py.newInteger(2));
      dict.__setitem__((String)"R_OK", Py.newInteger(4));
      dict.__setitem__((String)"EX_OK", Py.Zero);
      boolean nativePosix = false;

      try {
         nativePosix = posix.isNative();
         dict.__setitem__((String)"_native_posix", Py.newBoolean(nativePosix));
         dict.__setitem__("_posix_impl", Py.java2py(posix));
      } catch (SecurityException var5) {
      }

      dict.__setitem__("environ", getEnviron());
      dict.__setitem__("error", Py.OSError);
      dict.__setitem__((String)"stat_result", PyStatResult.TYPE);
      dict.__setitem__((String)"fstat", new FstatFunction());
      if (os == OS.NT) {
         WindowsStatFunction stat = new WindowsStatFunction();
         dict.__setitem__((String)"lstat", stat);
         dict.__setitem__((String)"stat", stat);
      } else {
         dict.__setitem__((String)"lstat", new LstatFunction());
         dict.__setitem__((String)"stat", new StatFunction());
      }

      Hider.hideFunctions(PosixModule.class, dict, os, nativePosix);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"__init__", (PyObject)null);
      dict.__setitem__((String)"getPOSIX", (PyObject)null);
      dict.__setitem__((String)"getOSName", (PyObject)null);
      dict.__setitem__((String)"badFD", (PyObject)null);
      PyList keys = (PyList)dict.invoke("keys");
      Iterator it = keys.listIterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         if (key.startsWith("__doc__")) {
            it.remove();
            dict.__setitem__((String)key, (PyObject)null);
         }
      }

      dict.__setitem__((String)"__all__", keys);
      dict.__setitem__((String)"__name__", new PyString(os.getModuleName()));
      dict.__setitem__((String)"__doc__", __doc__);
   }

   private static FDUnion getFD(PyObject fdObj) {
      if (fdObj.isInteger()) {
         int intFd = fdObj.asInt();
         switch (intFd) {
            case -1:
               break;
            case 0:
               return new FDUnion(FileDescriptor.in);
            case 1:
               return new FDUnion(FileDescriptor.out);
            case 2:
               return new FDUnion(FileDescriptor.err);
            default:
               return new FDUnion(intFd);
         }
      }

      Object tojava = fdObj.__tojava__(FileDescriptor.class);
      if (tojava != Py.NoConversion) {
         return new FDUnion((FileDescriptor)tojava);
      } else {
         tojava = fdObj.__tojava__(FileIO.class);
         if (tojava != Py.NoConversion) {
            return new FDUnion(((FileIO)tojava).getFD());
         } else {
            throw Py.TypeError("an integer or Java/Jython file descriptor is required");
         }
      }
   }

   public static void _exit() {
      _exit(0);
   }

   public static void _exit(int status) {
      System.exit(status);
   }

   public static boolean access(PyObject path, int mode) {
      File file = absolutePath(path).toFile();
      boolean result = true;
      if (!file.exists()) {
         result = false;
      }

      if ((mode & 4) != 0 && !file.canRead()) {
         result = false;
      }

      if ((mode & 2) != 0 && !file.canWrite()) {
         result = false;
      }

      if ((mode & 1) != 0 && !file.canExecute()) {
         result = false;
      }

      return result;
   }

   public static void chdir(PyObject path) {
      PySystemState sys = Py.getSystemState();
      Path absolutePath = absolutePath(path);
      if (!basicstat(path, absolutePath).isDirectory()) {
         throw Py.OSError(Errno.ENOTDIR, path);
      } else {
         if (os == OS.NT) {
            sys.setCurrentWorkingDir(absolutePath.toString());
         } else {
            try {
               sys.setCurrentWorkingDir(absolutePath.toRealPath().toString());
            } catch (IOException var4) {
               throw Py.OSError(var4);
            }
         }

      }
   }

   public static void chmod(PyObject path, int mode) {
      if (os == OS.NT) {
         try {
            boolean writable = (mode & 128) != 0;
            File f = absolutePath(path).toFile();
            if (!f.exists()) {
               throw Py.OSError(Errno.ENOENT, path);
            }

            if (!f.isDirectory() && !f.setWritable(writable)) {
               throw Py.OSError(Errno.EPERM, path);
            }
         } catch (SecurityException var4) {
            throw Py.OSError(Errno.EACCES, path);
         }
      } else if (posix.chmod(absolutePath(path).toString(), mode) < 0) {
         throw errorFromErrno(path);
      }

   }

   @Hide({OS.NT})
   public static void chown(PyObject path, int uid, int gid) {
      if (posix.chown(absolutePath(path).toString(), uid, gid) < 0) {
         throw errorFromErrno(path);
      }
   }

   public static void close(PyObject fd) {
      Object obj = fd.__tojava__(RawIOBase.class);
      if (obj != Py.NoConversion) {
         ((RawIOBase)obj).close();
      } else {
         posix.close(getFD(fd).getIntFD());
      }

   }

   @Hide({OS.NT})
   public static void closerange(PyObject fd_lowObj, PyObject fd_highObj) {
      int fd_low = getFD(fd_lowObj).getIntFD(false);
      int fd_high = getFD(fd_highObj).getIntFD(false);

      for(int i = fd_low; i < fd_high; ++i) {
         try {
            posix.close(i);
         } catch (Exception var6) {
         }
      }

   }

   public static PyObject fdopen(PyObject fd) {
      return fdopen(fd, "r");
   }

   public static PyObject fdopen(PyObject fd, String mode) {
      return fdopen(fd, mode, -1);
   }

   public static PyObject fdopen(PyObject fd, String mode, int bufsize) {
      if (mode.length() != 0 && "rwa".contains("" + mode.charAt(0))) {
         Object javaobj = fd.__tojava__(RawIOBase.class);
         if (javaobj == Py.NoConversion) {
            getFD(fd).getIntFD();
            throw Py.NotImplementedError("Integer file descriptors not currently supported for fdopen");
         } else {
            RawIOBase rawIO = (RawIOBase)javaobj;
            if (rawIO.closed()) {
               throw badFD();
            } else {
               try {
                  return new PyFile(rawIO, "<fdopen>", mode, bufsize);
               } catch (PyException var6) {
                  if (!var6.match(Py.IOError)) {
                     throw var6;
                  } else {
                     throw Py.OSError((Constant)Errno.EINVAL);
                  }
               }
            }
         }
      } else {
         throw Py.ValueError(String.format("invalid file mode '%s'", mode));
      }
   }

   @Hide({OS.NT})
   public static void fdatasync(PyObject fd) {
      Object javaobj = fd.__tojava__(RawIOBase.class);
      if (javaobj != Py.NoConversion) {
         fsync((RawIOBase)javaobj, false);
      } else {
         posix.fdatasync(getFD(fd).getIntFD());
      }

   }

   public static void fsync(PyObject fd) {
      Object javaobj = fd.__tojava__(RawIOBase.class);
      if (javaobj != Py.NoConversion) {
         fsync((RawIOBase)javaobj, true);
      } else {
         posix.fsync(getFD(fd).getIntFD());
      }

   }

   private static void fsync(RawIOBase rawIO, boolean metadata) {
      rawIO.checkClosed();
      Channel channel = rawIO.getChannel();
      if (!(channel instanceof FileChannel)) {
         throw Py.OSError((Constant)Errno.EINVAL);
      } else {
         try {
            ((FileChannel)channel).force(metadata);
         } catch (ClosedChannelException var4) {
            throw Py.ValueError("I/O operation on closed file");
         } catch (IOException var5) {
            throw Py.OSError(var5);
         }
      }
   }

   public static void ftruncate(PyObject fd, long length) {
      Object javaobj = fd.__tojava__(RawIOBase.class);
      if (javaobj != Py.NoConversion) {
         try {
            ((RawIOBase)javaobj).truncate(length);
         } catch (PyException var5) {
            throw Py.OSError((Constant)Errno.EBADF);
         }
      } else {
         posix.ftruncate(getFD(fd).getIntFD(), length);
      }

   }

   public static PyObject getcwd() {
      return Py.fileSystemEncode(Py.getSystemState().getCurrentWorkingDir());
   }

   public static PyObject getcwdu() {
      return Py.newUnicode(Py.getSystemState().getCurrentWorkingDir());
   }

   @Hide({OS.NT})
   public static int getegid() {
      return posix.getegid();
   }

   @Hide({OS.NT})
   public static int geteuid() {
      return posix.geteuid();
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static int getgid() {
      return posix.getgid();
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static PyObject getlogin() {
      String login = posix.getlogin();
      if (login == null) {
         throw Py.OSError("getlogin OS call failed. Preferentially use os.getenv('LOGNAME') instead.");
      } else {
         return new PyString(login);
      }
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static int getppid() {
      return posix.getppid();
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static int getuid() {
      return posix.getuid();
   }

   @Hide(
      posixImpl = PosixImpl.JAVA
   )
   public static int getpid() {
      return posix.getpid();
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static int getpgrp() {
      return posix.getpgrp();
   }

   @Hide(
      posixImpl = PosixImpl.JAVA
   )
   public static boolean isatty(PyObject fdObj) {
      Object tojava = fdObj.__tojava__(IOBase.class);
      if (tojava != Py.NoConversion) {
         try {
            return ((IOBase)tojava).isatty();
         } catch (PyException var5) {
            if (var5.match(Py.ValueError)) {
               return false;
            } else {
               throw var5;
            }
         }
      } else {
         FDUnion fd = getFD(fdObj);
         if (fd.javaFD != null) {
            return posix.isatty(fd.javaFD);
         } else {
            try {
               fd.getIntFD();
            } catch (PyException var4) {
               if (var4.match(Py.OSError)) {
                  return false;
               }

               throw var4;
            }

            throw Py.NotImplementedError("Integer file descriptor compatibility only available for stdin, stdout and stderr (0-2)");
         }
      }
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static void kill(int pid, int sig) {
      if (posix.kill(pid, sig) < 0) {
         throw errorFromErrno();
      }
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static void lchmod(PyObject path, int mode) {
      if (posix.lchmod(absolutePath(path).toString(), mode) < 0) {
         throw errorFromErrno(path);
      }
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static void lchown(PyObject path, int uid, int gid) {
      if (posix.lchown(absolutePath(path).toString(), uid, gid) < 0) {
         throw errorFromErrno(path);
      }
   }

   @Hide({OS.NT})
   public static void link(PyObject src, PyObject dst) {
      try {
         Files.createLink(Paths.get(asPath(dst)), Paths.get(asPath(src)));
      } catch (FileAlreadyExistsException var3) {
         throw Py.OSError((Constant)Errno.EEXIST);
      } catch (NoSuchFileException var4) {
         throw Py.OSError((Constant)Errno.ENOENT);
      } catch (IOException var5) {
         System.err.println("Got this exception " + var5);
         throw Py.OSError(var5);
      } catch (SecurityException var6) {
         throw Py.OSError((Constant)Errno.EACCES);
      }
   }

   public static PyList listdir(PyObject path) {
      File file = absolutePath(path).toFile();
      String[] names = file.list();
      if (names == null) {
         if (!file.isDirectory()) {
            throw Py.OSError(Errno.ENOTDIR, path);
         } else if (!file.canRead()) {
            throw Py.OSError(Errno.EACCES, path);
         } else {
            throw Py.OSError("listdir(): an unknown error occurred: " + path);
         }
      } else {
         PyList list = new PyList();
         String[] var4;
         int var5;
         int var6;
         String name;
         if (path instanceof PyUnicode) {
            var4 = names;
            var5 = names.length;

            for(var6 = 0; var6 < var5; ++var6) {
               name = var4[var6];
               list.append(Py.newUnicode(name));
            }
         } else {
            var4 = names;
            var5 = names.length;

            for(var6 = 0; var6 < var5; ++var6) {
               name = var4[var6];
               list.append(Py.fileSystemEncode(name));
            }
         }

         return list;
      }
   }

   public static long lseek(PyObject fd, long pos, int how) {
      Object javaobj = fd.__tojava__(RawIOBase.class);
      if (javaobj != Py.NoConversion) {
         try {
            return ((RawIOBase)javaobj).seek(pos, how);
         } catch (PyException var6) {
            throw badFD();
         }
      } else {
         return (long)posix.lseek(getFD(fd).getIntFD(), pos, how);
      }
   }

   public static void mkdir(PyObject path) {
      mkdir(path, 511);
   }

   public static void mkdir(PyObject path, int mode) {
      if (os == OS.NT) {
         try {
            Path nioPath = absolutePath(path);
            Files.createDirectory(nioPath);
         } catch (FileAlreadyExistsException var3) {
            throw Py.OSError(Errno.EEXIST, path);
         } catch (IOException var4) {
            throw Py.OSError(var4);
         } catch (SecurityException var5) {
            throw Py.OSError(Errno.EACCES, path);
         }
      } else if (posix.mkdir(absolutePath(path).toString(), mode) < 0) {
         throw errorFromErrno(path);
      }

   }

   public static FileIO open(PyObject path, int flag) {
      return open(path, flag, 511);
   }

   public static FileIO open(PyObject path, int flag, int mode) {
      File file = absolutePath(path).toFile();
      boolean reading = (flag & 0) != 0;
      boolean writing = (flag & 1) != 0;
      boolean updating = (flag & 2) != 0;
      boolean creating = (flag & 512) != 0;
      boolean appending = (flag & 8) != 0;
      boolean truncating = (flag & 1024) != 0;
      boolean exclusive = (flag & 2048) != 0;
      boolean sync = (flag & 128) != 0;
      if (updating && writing) {
         throw Py.OSError(Errno.EINVAL, path);
      } else if (!creating && !file.exists()) {
         throw Py.OSError(Errno.ENOENT, path);
      } else {
         if (!writing) {
            if (updating) {
               writing = true;
            } else {
               reading = true;
            }
         }

         if (truncating && !writing) {
            (new FileIO((PyString)path, "w")).close();
         }

         if (exclusive && creating) {
            try {
               if (!file.createNewFile()) {
                  throw Py.OSError(Errno.EEXIST, path);
               }
            } catch (IOException var14) {
               throw Py.OSError(var14);
            }
         }

         String fileIOMode = (reading ? "r" : "") + (!appending && writing ? "w" : "") + (!appending || !writing && !updating ? "" : "a") + (updating ? "+" : "");
         if (sync && (writing || updating)) {
            try {
               return new FileIO((new RandomAccessFile(file, "rws")).getChannel(), fileIOMode);
            } catch (FileNotFoundException var15) {
               throw Py.OSError(file.isDirectory() ? Errno.EISDIR : Errno.ENOENT, path);
            }
         } else {
            return new FileIO((PyString)path, fileIOMode);
         }
      }
   }

   public static PyTuple pipe() {
      try {
         Pipe pipe = Pipe.open();
         StreamIO pipe_read = new StreamIO(pipe.source());
         StreamIO pipe_write = new StreamIO(pipe.sink());
         return new PyTuple(new PyObject[]{PyJavaType.wrapJavaObject(pipe_read.fileno()), PyJavaType.wrapJavaObject(pipe_write.fileno())});
      } catch (IOException var3) {
         throw Py.OSError(var3);
      }
   }

   public static PyObject popen(PyObject[] args, String[] kwds) {
      return imp.load("os").__getattr__("popen").__call__(args, kwds);
   }

   public static void putenv(String key, String value) {
      PyObject environ = imp.load("os").__getattr__("environ");
      environ.__setitem__((String)key, new PyString(value));
   }

   public static PyObject read(PyObject fd, int buffersize) {
      Object javaobj = fd.__tojava__(RawIOBase.class);
      if (javaobj != Py.NoConversion) {
         try {
            return new PyString(StringUtil.fromBytes(((RawIOBase)javaobj).read(buffersize)));
         } catch (PyException var4) {
            throw badFD();
         }
      } else {
         ByteBuffer buffer = ByteBuffer.allocate(buffersize);
         posix.read(getFD(fd).getIntFD(), buffer, buffersize);
         return new PyString(StringUtil.fromBytes(buffer));
      }
   }

   @Hide({OS.NT})
   public static PyString readlink(PyObject path) {
      try {
         return Py.newStringOrUnicode(path, Files.readSymbolicLink(absolutePath(path)).toString());
      } catch (NotLinkException var2) {
         throw Py.OSError(Errno.EINVAL, path);
      } catch (NoSuchFileException var3) {
         throw Py.OSError(Errno.ENOENT, path);
      } catch (IOException var4) {
         throw Py.OSError(var4);
      } catch (SecurityException var5) {
         throw Py.OSError(Errno.EACCES, path);
      }
   }

   public static void remove(PyObject path) {
      unlink(path);
   }

   public static void rename(PyObject oldpath, PyObject newpath) {
      if (!absolutePath(oldpath).toFile().renameTo(absolutePath(newpath).toFile())) {
         PyObject args = new PyTuple(new PyObject[]{Py.Zero, new PyString("Couldn't rename file")});
         throw new PyException(Py.OSError, args);
      }
   }

   public static void rmdir(PyObject path) {
      File file = absolutePath(path).toFile();
      if (!file.exists()) {
         throw Py.OSError(Errno.ENOENT, path);
      } else if (!file.isDirectory()) {
         throw Py.OSError(Errno.ENOTDIR, path);
      } else if (!file.delete()) {
         PyObject args = new PyTuple(new PyObject[]{Py.Zero, new PyString("Couldn't delete directory"), path});
         throw new PyException(Py.OSError, args);
      }
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static void setpgrp() {
      if (posix.setpgrp(0, 0) < 0) {
         throw errorFromErrno();
      }
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static void setsid() {
      if (posix.setsid() < 0) {
         throw errorFromErrno();
      }
   }

   public static PyObject strerror(int code) {
      Constant errno = Errno.valueOf((long)code);
      if (errno == Errno.__UNKNOWN_CONSTANT__) {
         return new PyString("Unknown error: " + code);
      } else {
         if (((Constant)errno).name() == errno.toString()) {
            errno = (Constant)Enum.valueOf(jnr.constants.platform.linux.Errno.class, ((Constant)errno).name());
         }

         return new PyString(errno.toString());
      }
   }

   @Hide({OS.NT})
   public static void symlink(PyObject src, PyObject dst) {
      try {
         Files.createSymbolicLink(Paths.get(asPath(dst)), Paths.get(asPath(src)));
      } catch (FileAlreadyExistsException var3) {
         throw Py.OSError((Constant)Errno.EEXIST);
      } catch (IOException var4) {
         throw Py.OSError(var4);
      } catch (SecurityException var5) {
         throw Py.OSError((Constant)Errno.EACCES);
      }
   }

   private static PyFloat ratio(long num, long div) {
      return Py.newFloat((double)num / (double)div);
   }

   @Hide(
      posixImpl = PosixImpl.JAVA
   )
   public static PyTuple times() {
      Times times = posix.times();
      long CLK_TCK = Sysconf._SC_CLK_TCK.longValue();
      return new PyTuple(new PyObject[]{ratio(times.utime(), CLK_TCK), ratio(times.stime(), CLK_TCK), ratio(times.cutime(), CLK_TCK), ratio(times.cstime(), CLK_TCK), ratio(ManagementFactory.getRuntimeMXBean().getUptime(), 1000L)});
   }

   @Hide(
      posixImpl = PosixImpl.JAVA
   )
   public static int umask(int mask) {
      return posix.umask(mask);
   }

   public static PyTuple uname() {
      if (uname_cache != null) {
         return uname_cache;
      } else {
         String sysname = System.getProperty("os.name");
         boolean win;
         String sysrelease;
         if (sysname.equals("Mac OS X")) {
            sysname = "Darwin";
            win = false;

            try {
               Process p = Runtime.getRuntime().exec("uname -r");
               BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
               sysrelease = br.readLine();

               while(true) {
                  if (br.readLine() == null) {
                     br.close();
                     if (p.waitFor() != 0) {
                        sysrelease = "";
                     }
                     break;
                  }
               }
            } catch (Exception var11) {
               sysrelease = "";
            }
         } else {
            win = sysname.startsWith("Windows");
            if (win) {
               sysrelease = sysname.length() > 7 ? sysname.substring(8) : System.getProperty("os.version");
               sysname = "Windows";
            } else {
               sysrelease = System.getProperty("os.version");
            }
         }

         String uname_nodename;
         try {
            uname_nodename = InetAddress.getLocalHost().getHostName();
         } catch (Exception var8) {
            uname_nodename = null;
         }

         if (uname_nodename == null && win) {
            uname_nodename = System.getenv("USERDOMAIN");
         }

         if (uname_nodename == null) {
            try {
               Process p = Runtime.getRuntime().exec(win ? "hostname" : "uname -n");
               BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
               uname_nodename = br.readLine();

               while(true) {
                  if (br.readLine() == null) {
                     br.close();
                     if (p.waitFor() != 0) {
                        uname_nodename = "";
                     }
                     break;
                  }
               }
            } catch (Exception var10) {
               uname_nodename = "";
            }
         }

         String uname_sysver = PySystemState.getSystemVersionString();

         String machine;
         String uname_machine;
         try {
            if (win) {
               machine = System.getenv("PROCESSOR_ARCHITECTURE");
               if (machine.equals("x86")) {
                  machine = System.getenv("PROCESSOR_ARCHITEW6432");
               }

               uname_machine = machine == null ? "x86" : machine;
            } else {
               Process p = Runtime.getRuntime().exec("uname -m");
               BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
               uname_machine = br.readLine();

               while(true) {
                  if (br.readLine() == null) {
                     br.close();
                     if (p.waitFor() != 0) {
                        uname_machine = null;
                     }
                     break;
                  }
               }
            }
         } catch (Exception var9) {
            uname_machine = null;
         }

         if (uname_machine == null) {
            machine = System.getProperty("os.arch");
            if (machine == null) {
               uname_machine = "";
            } else if (machine.equals("amd64")) {
               uname_machine = "x86_64";
            } else if (machine.equals("x86")) {
               uname_machine = "i686";
            } else {
               uname_machine = machine;
            }
         }

         PyObject[] vals = new PyObject[]{Py.newString(sysname), Py.newString(uname_nodename), Py.newString(sysrelease), Py.newString(uname_sysver), Py.newString(uname_machine)};
         uname_cache = new PyTuple(vals, false);
         return uname_cache;
      }
   }

   public static void unlink(PyObject path) {
      Path nioPath = absolutePath(path);

      try {
         if (Files.isDirectory(nioPath, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
            throw Py.OSError(Errno.EISDIR, path);
         } else if (!Files.deleteIfExists(nioPath)) {
            basicstat(path, nioPath);
            if (!Files.isWritable(nioPath)) {
               throw Py.OSError(Errno.EACCES, path);
            } else {
               throw Py.OSError("unlink(): an unknown error occurred: " + nioPath.toString());
            }
         }
      } catch (IOException var4) {
         PyException pyError = Py.OSError("unlink(): an unknown error occurred: " + nioPath.toString());
         pyError.initCause(var4);
         throw pyError;
      }
   }

   public static void utime(PyObject path, PyObject times) {
      FileTime mtime;
      FileTime atime;
      if (times == Py.None) {
         atime = mtime = FileTime.from(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
      } else {
         if (!(times instanceof PyTuple) || times.__len__() != 2) {
            throw Py.TypeError("utime() arg 2 must be a tuple (atime, mtime)");
         }

         atime = getFileTime(times.__getitem__(0));
         mtime = getFileTime(times.__getitem__(1));
      }

      try {
         ((BasicFileAttributeView)Files.getFileAttributeView(absolutePath(path), BasicFileAttributeView.class)).setTimes(mtime, atime, (FileTime)null);
      } catch (NoSuchFileException var5) {
         throw Py.OSError(Errno.ENOENT, path);
      } catch (IOException var6) {
         throw Py.OSError(var6);
      } catch (SecurityException var7) {
         throw Py.OSError(Errno.EACCES, path);
      }
   }

   private static FileTime getFileTime(PyObject seconds) {
      try {
         return FileTime.from((long)(seconds.__float__().asDouble() * 1000000.0), TimeUnit.MICROSECONDS);
      } catch (PyException var2) {
         if (!var2.match(Py.AttributeError)) {
            throw var2;
         } else {
            throw Py.TypeError("an integer or float is required");
         }
      }
   }

   @Hide(
      value = {OS.NT},
      posixImpl = PosixImpl.JAVA
   )
   public static PyObject wait$() {
      int[] status = new int[1];
      int pid = posix.wait(status);
      if (pid < 0) {
         throw errorFromErrno();
      } else {
         return new PyTuple(new PyObject[]{Py.newInteger(pid), Py.newInteger(status[0])});
      }
   }

   @Hide(
      posixImpl = PosixImpl.JAVA
   )
   public static PyObject waitpid(int pid, int options) {
      int[] status = new int[1];
      pid = posix.waitpid(pid, status, options);
      if (pid < 0) {
         throw errorFromErrno();
      } else {
         return new PyTuple(new PyObject[]{Py.newInteger(pid), Py.newInteger(status[0])});
      }
   }

   public static int write(PyObject fd, BufferProtocol bytes) {
      PyBuffer buf = bytes.getBuffer(8);
      Throwable var3 = null;

      int var6;
      try {
         ByteBuffer bb = buf.getNIOByteBuffer();
         Object javaobj = fd.__tojava__(RawIOBase.class);
         if (javaobj == Py.NoConversion) {
            var6 = posix.write(getFD(fd).getIntFD(), bb, bb.position());
            return var6;
         }

         try {
            var6 = ((RawIOBase)javaobj).write(bb);
         } catch (PyException var17) {
            throw badFD();
         }
      } catch (Throwable var18) {
         var3 = var18;
         throw var18;
      } finally {
         if (buf != null) {
            if (var3 != null) {
               try {
                  buf.close();
               } catch (Throwable var16) {
                  var3.addSuppressed(var16);
               }
            } else {
               buf.close();
            }
         }

      }

      return var6;
   }

   public static void unsetenv(String key) {
      PyObject environ = imp.load("os").__getattr__("environ");

      try {
         environ.__delitem__(key);
      } catch (PyException var3) {
         if (!var3.match(Py.KeyError)) {
            throw var3;
         }
      }

   }

   public static PyObject urandom(int n) {
      byte[] buf = new byte[n];
      PosixModule.UrandomSource.INSTANCE.nextBytes(buf);
      return new PyString(StringUtil.fromBytes(buf));
   }

   public static PyObject _get_shell_commands() {
      String[][] commands = os.getShellCommands();
      PyObject[] commandsTup = new PyObject[commands.length];
      int i = 0;
      String[][] var3 = commands;
      int var4 = commands.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String[] command = var3[var5];
         PyList args = new PyList();
         String[] var8 = command;
         int var9 = command.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String arg = var8[var10];
            args.append(new PyString(arg));
         }

         commandsTup[i++] = args;
      }

      return new PyTuple(commandsTup);
   }

   private static PyObject getEnviron() {
      PyObject environ = new PyDictionary();

      Map env;
      try {
         env = System.getenv();
      } catch (SecurityException var4) {
         return environ;
      }

      Iterator var2 = env.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         environ.__setitem__((PyObject)Py.newString((String)entry.getKey()), Py.fileSystemEncode((String)entry.getValue()));
      }

      return environ;
   }

   private static String asPath(PyObject path) {
      return Py.fileSystemDecode(path);
   }

   private static Path absolutePath(PyObject pathObj) {
      String pathStr = asPath(pathObj);
      if (pathStr.equals("")) {
         throw Py.OSError(Errno.ENOENT, pathObj);
      } else {
         try {
            Path path = Paths.get(pathStr);
            path = Paths.get(Py.getSystemState().getCurrentWorkingDir()).resolve(path);
            path = path.toAbsolutePath();
            path = path.normalize();
            pathStr = path.toString();
            if (pathStr.endsWith(path.getFileSystem().getSeparator()) && path.getNameCount() > 0) {
               path = Paths.get(pathStr.substring(0, pathStr.length() - 1));
            }

            return path;
         } catch (InvalidPathException var3) {
            throw Py.OSError(Errno.EINVAL, pathObj);
         }
      }
   }

   private static PyException badFD() {
      return Py.OSError((Constant)Errno.EBADF);
   }

   private static PyException errorFromErrno() {
      return Py.OSError((Constant)Errno.valueOf((long)posix.errno()));
   }

   private static PyException errorFromErrno(PyObject path) {
      return Py.OSError(Errno.valueOf((long)posix.errno()), path);
   }

   public static POSIX getPOSIX() {
      return posix;
   }

   public static String getOSName() {
      return os.getModuleName();
   }

   private static void checkTrailingSlash(PyObject path, Map attributes) {
      Boolean isDirectory = (Boolean)attributes.get("isDirectory");
      if (isDirectory != null && !isDirectory) {
         String pathStr = path.toString();
         if (pathStr.endsWith(File.separator) || pathStr.endsWith("/.")) {
            throw Py.OSError(Errno.ENOTDIR, path);
         }
      }

   }

   private static BasicFileAttributes basicstat(PyObject path, Path absolutePath) {
      try {
         BasicFileAttributes attributes = Files.readAttributes(absolutePath, BasicFileAttributes.class);
         if (!attributes.isDirectory()) {
            String pathStr = path.toString();
            if (pathStr.endsWith(File.separator) || pathStr.endsWith("/")) {
               throw Py.OSError(Errno.ENOTDIR, path);
            }
         }

         return attributes;
      } catch (NoSuchFileException var4) {
         throw Py.OSError(Errno.ENOENT, path);
      } catch (IOException var5) {
         throw Py.OSError(Errno.EBADF, path);
      } catch (SecurityException var6) {
         throw Py.OSError(Errno.EACCES, path);
      }
   }

   static {
      posix = POSIXFactory.getPOSIX(posixHandler, true);
      __doc___exit = new PyString("_exit(status)\n\nExit to the system with specified status, without normal exit processing.");
      __doc__access = new PyString("access(path, mode) -> True if granted, False otherwise\n\nUse the real uid/gid to test for access to a path.  Note that most\noperations will use the effective uid/gid, therefore this routine can\nbe used in a suid/sgid environment to test if the invoking user has the\nspecified access to the path.  The mode argument can be F_OK to test\nexistence, or the inclusive-OR of R_OK, W_OK, and X_OK.");
      __doc__chdir = new PyString("chdir(path)\n\nChange the current working directory to the specified path.");
      __doc__chmod = new PyString("chmod(path, mode)\n\nChange the access permissions of a file.");
      __doc__chown = new PyString("chown(path, uid, gid)\n\nChange the owner and group id of path to the numeric uid and gid.");
      __doc__close = new PyString("close(fd)\n\nClose a file descriptor (for low level IO).");
      __doc__fdopen = new PyString("fdopen(fd [, mode='r' [, bufsize]]) -> file_object\n\nReturn an open file object connected to a file descriptor.");
      __doc__fdatasync = new PyString("fdatasync(fildes)\n\nforce write of file with filedescriptor to disk.\ndoes not force update of metadata.");
      __doc__fsync = new PyString("fsync(fildes)\n\nforce write of file with filedescriptor to disk.");
      __doc__ftruncate = new PyString("ftruncate(fd, length)\n\nTruncate a file to a specified length.");
      __doc__getcwd = new PyString("getcwd() -> path\n\nReturn a string representing the current working directory.");
      __doc__getcwdu = new PyString("getcwd() -> path\n\nReturn a unicode string representing the current working directory.");
      __doc__getegid = new PyString("getegid() -> egid\n\nReturn the current process's effective group id.");
      __doc__geteuid = new PyString("geteuid() -> euid\n\nReturn the current process's effective user id.");
      __doc__getgid = new PyString("getgid() -> gid\n\nReturn the current process's group id.");
      __doc__getlogin = new PyString("getlogin() -> string\n\nReturn the actual login name.");
      __doc__getppid = new PyString("getppid() -> ppid\n\nReturn the parent's process id.");
      __doc__getuid = new PyString("getuid() -> uid\n\nReturn the current process's user id.");
      __doc__getpid = new PyString("getpid() -> pid\n\nReturn the current process id");
      __doc__getpgrp = new PyString("getpgrp() -> pgrp\n\nReturn the current process group id.");
      __doc__isatty = new PyString("isatty(fd) -> bool\n\nReturn True if the file descriptor 'fd' is an open file descriptor\nconnected to the slave end of a terminal.");
      __doc__kill = new PyString("kill(pid, sig)\n\nKill a process with a signal.");
      __doc__lchmod = new PyString("lchmod(path, mode)\n\nChange the access permissions of a file. If path is a symlink, this\naffects the link itself rather than the target.");
      __doc__lchown = new PyString("lchown(path, uid, gid)\n\nChange the owner and group id of path to the numeric uid and gid.\nThis function will not follow symbolic links.");
      __doc__link = new PyString("link(src, dst)\n\nCreate a hard link to a file.");
      __doc__listdir = new PyString("listdir(path) -> list_of_strings\n\nReturn a list containing the names of the entries in the directory.\n\npath: path of directory to list\n\nThe list is in arbitrary order.  It does not include the special\nentries '.' and '..' even if they are present in the directory.");
      __doc__lseek = new PyString("lseek(fd, pos, how) -> newpos\n\nSet the current position of a file descriptor.");
      __doc__mkdir = new PyString("mkdir(path [, mode=0777])\n\nCreate a directory.");
      __doc__open = new PyString("open(filename, flag [, mode=0777]) -> fd\n\nOpen a file (for low level IO).\n\nNote that the mode argument is not currently supported on Jython.");
      __doc__pipe = new PyString("pipe() -> (read_end, write_end)\n\nCreate a pipe.");
      __doc__popen = new PyString("popen(command [, mode='r' [, bufsize]]) -> pipe\n\nOpen a pipe to/from a command returning a file object.");
      __doc__putenv = new PyString("putenv(key, value)\n\nChange or add an environment variable.");
      __doc__read = new PyString("read(fd, buffersize) -> string\n\nRead a file descriptor.");
      __doc__readlink = new PyString("readlink(path) -> path\n\nReturn a string representing the path to which the symbolic link points.");
      __doc__remove = new PyString("remove(path)\n\nRemove a file (same as unlink(path)).");
      __doc__rename = new PyString("rename(old, new)\n\nRename a file or directory.");
      __doc__rmdir = new PyString("rmdir(path)\n\nRemove a directory.");
      __doc__setpgrp = new PyString("setpgrp()\n\nMake this process a session leader.");
      __doc__setsid = new PyString("setsid()\n\nCall the system call setsid().");
      __doc__strerror = new PyString("strerror(code) -> string\n\nTranslate an error code to a message string.");
      __doc__symlink = new PyString("symlink(src, dst)\n\nCreate a symbolic link pointing to src named dst.");
      __doc__times = new PyString("times() -> (utime, stime, cutime, cstime, elapsed_time)\n\nReturn a tuple of floating point numbers indicating process times.");
      __doc__umask = new PyString("umask(new_mask) -> old_mask\n\nSet the current numeric umask and return the previous umask.");
      __doc__uname = new PyString("uname() -> (sysname, nodename, release, version, machine)\n\nReturn a tuple identifying the current operating system.");
      uname_cache = null;
      __doc__unlink = new PyString("unlink(path)\n\nRemove a file (same as remove(path)).");
      __doc__utime = new PyString("utime(path, (atime, mtime))\nutime(path, None)\n\nSet the access and modified time of the file to the given values.  If the\nsecond form is used, set the access and modified times to the current time.");
      __doc__wait = new PyString("wait() -> (pid, status)\n\nWait for completion of a child process.");
      __doc__waitpid = new PyString("wait() -> (pid, status)\n\nWait for completion of a child process.");
      __doc__write = new PyString("write(fd, string) -> byteswritten\n\nWrite a string to a file descriptor.");
      __doc__unsetenv = new PyString("unsetenv(key)\n\nDelete an environment variable.");
      __doc__urandom = new PyString("urandom(n) -> str\n\nReturn a string of n random bytes suitable for cryptographic use.");
   }

   private static class WindowsRawFileStat2 extends WindowsRawFileStat {
      private int mode;

      public WindowsRawFileStat2(POSIX posix, POSIXHandler handler) {
         super(posix, handler);
      }

      public void setup(CommonFileInformation fileInfo) {
         super.setup(fileInfo);
         int attr = fileInfo.getFileAttributes();
         int mode = 292;
         if ((attr & CommonFileInformation.FILE_ATTRIBUTE_READONLY) == 0) {
            mode |= 146;
         }

         if ((attr & CommonFileInformation.FILE_ATTRIBUTE_DIRECTORY) != 0) {
            mode |= 16457;
         } else {
            mode |= 32768;
         }

         this.mode = mode;
      }

      public int mode() {
         return this.mode;
      }
   }

   @Untraversable
   static class FstatFunction extends PyBuiltinFunctionNarrow {
      FstatFunction() {
         super("fstat", 1, 1, "fstat(fd) -> stat result\\n\\nLike stat(), but for an open file descriptor.");
      }

      public PyObject __call__(PyObject fdObj) {
         try {
            FDUnion fd = PosixModule.getFD(fdObj);
            Object stat;
            if (PosixModule.os != OS.NT) {
               if (fd.isIntFD()) {
                  stat = PosixModule.posix.fstat(fd.intFD);
               } else {
                  stat = PosixModule.posix.fstat(fd.javaFD);
               }
            } else {
               stat = new WindowsRawFileStat2(PosixModule.posix, PosixModule.posixHandler);
               if (PosixModule.posix.fstat(fd.javaFD, (FileStat)stat) < 0) {
                  throw Py.OSError((Constant)Errno.EBADF);
               }
            }

            return PyStatResult.fromFileStat((FileStat)stat);
         } catch (PyException var4) {
            throw Py.OSError((Constant)Errno.EBADF);
         }
      }
   }

   @Untraversable
   static class WindowsStatFunction extends PyBuiltinFunctionNarrow {
      private static final int _S_IFDIR = 16384;
      private static final int _S_IFREG = 32768;

      WindowsStatFunction() {
         super("stat", 1, 1, "stat(path) -> stat result\n\nPerform a stat system call on the given path.\n\nNote that some platforms may return only a small subset of the\nstandard fields");
      }

      static int attributes_to_mode(DosFileAttributes attr) {
         int m = 0;
         if (attr.isDirectory()) {
            m |= 16457;
         } else {
            m |= 32768;
         }

         if (attr.isReadOnly()) {
            m |= 292;
         } else {
            m |= 438;
         }

         return m;
      }

      public PyObject __call__(PyObject path) {
         Path absolutePath = PosixModule.absolutePath(path);

         try {
            DosFileAttributes attributes = (DosFileAttributes)Files.readAttributes(absolutePath, DosFileAttributes.class);
            if (!attributes.isDirectory()) {
               String pathStr = path.toString();
               if (pathStr.endsWith(File.separator) || pathStr.endsWith("/")) {
                  throw Py.OSError(Errno.ENOTDIR, path);
               }
            }

            int mode = attributes_to_mode(attributes);
            String extension = org.python.google.common.io.Files.getFileExtension(absolutePath.toString());
            if (extension.equals("bat") || extension.equals("cmd") || extension.equals("exe") || extension.equals("com")) {
               mode |= 73;
            }

            return PyStatResult.fromDosFileAttributes(mode, attributes);
         } catch (NoSuchFileException var6) {
            throw Py.OSError(Errno.ENOENT, path);
         } catch (IOException var7) {
            throw Py.OSError(Errno.EBADF, path);
         } catch (SecurityException var8) {
            throw Py.OSError(Errno.EACCES, path);
         }
      }
   }

   @Untraversable
   static class StatFunction extends PyBuiltinFunctionNarrow {
      StatFunction() {
         super("stat", 1, 1, "stat(path) -> stat result\n\nPerform a stat system call on the given path.\n\nNote that some platforms may return only a small subset of the\nstandard fields");
      }

      public PyObject __call__(PyObject path) {
         Path absolutePath = PosixModule.absolutePath(path);

         try {
            Map attributes = Files.readAttributes(absolutePath, "unix:*");
            PosixModule.checkTrailingSlash(path, attributes);
            return PyStatResult.fromUnixFileAttributes(attributes);
         } catch (NoSuchFileException var4) {
            throw Py.OSError(Errno.ENOENT, path);
         } catch (IOException var5) {
            throw Py.OSError(Errno.EBADF, path);
         } catch (SecurityException var6) {
            throw Py.OSError(Errno.EACCES, path);
         }
      }
   }

   @Untraversable
   static class LstatFunction extends PyBuiltinFunctionNarrow {
      LstatFunction() {
         super("lstat", 1, 1, "lstat(path) -> stat result\n\nLike stat(path), but do not follow symbolic links.");
      }

      public PyObject __call__(PyObject path) {
         Path absolutePath = PosixModule.absolutePath(path);

         try {
            Map attributes = Files.readAttributes(absolutePath, "unix:*", LinkOption.NOFOLLOW_LINKS);
            Boolean isSymbolicLink = (Boolean)attributes.get("isSymbolicLink");
            if (isSymbolicLink != null && isSymbolicLink && path.toString().endsWith("/")) {
               Path symlink = Files.readSymbolicLink(absolutePath);
               symlink = absolutePath.getParent().resolve(symlink);
               attributes = Files.readAttributes(symlink, "unix:*", LinkOption.NOFOLLOW_LINKS);
            } else {
               PosixModule.checkTrailingSlash(path, attributes);
            }

            return PyStatResult.fromUnixFileAttributes(attributes);
         } catch (NoSuchFileException var6) {
            throw Py.OSError(Errno.ENOENT, path);
         } catch (IOException var7) {
            throw Py.OSError(Errno.EBADF, path);
         } catch (SecurityException var8) {
            throw Py.OSError(Errno.EACCES, path);
         }
      }
   }

   private static class FDUnion {
      volatile int intFD;
      final FileDescriptor javaFD;

      FDUnion(int fd) {
         this.intFD = fd;
         this.javaFD = null;
      }

      FDUnion(FileDescriptor fd) {
         this.intFD = -1;
         this.javaFD = fd;
      }

      boolean isIntFD() {
         return this.intFD != -1;
      }

      int getIntFD() {
         return this.getIntFD(true);
      }

      int getIntFD(boolean checkFD) {
         if (this.intFD == -1) {
            if (!(this.javaFD instanceof FileDescriptor)) {
               throw Py.OSError((Constant)Errno.EBADF);
            }

            try {
               Field fdField = FieldAccess.getProtectedField(FileDescriptor.class, "fd");
               this.intFD = fdField.getInt(this.javaFD);
            } catch (SecurityException var3) {
            } catch (IllegalArgumentException var4) {
            } catch (IllegalAccessException var5) {
            } catch (NullPointerException var6) {
            }
         }

         if (checkFD) {
            if (this.intFD == -1) {
               throw Py.OSError((Constant)Errno.EBADF);
            }

            PosixModule.posix.fstat(this.intFD);
         }

         return this.intFD;
      }

      public String toString() {
         return "FDUnion(int=" + this.intFD + ", java=" + this.javaFD + ")";
      }
   }

   private static class UrandomSource {
      static final SecureRandom INSTANCE = new SecureRandom();
   }
}
