package jnr.posix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import jnr.constants.platform.Errno;
import jnr.posix.util.Chmod;
import jnr.posix.util.ExecIt;
import jnr.posix.util.JavaCrypt;
import jnr.posix.util.Platform;

public class JavaLibCHelper {
   public static final int STDIN = 0;
   public static final int STDOUT = 1;
   public static final int STDERR = 2;
   private static final ThreadLocal errno = new ThreadLocal();
   private final POSIXHandler handler;
   private final Map env = new HashMap();
   ThreadLocal pwIndex = new ThreadLocal() {
      protected Integer initialValue() {
         return 0;
      }
   };

   public JavaLibCHelper(POSIXHandler handler) {
      this.handler = handler;
   }

   public static FileDescriptor getDescriptorFromChannel(Channel channel) {
      if (JavaLibCHelper.ReflectiveAccess.SEL_CH_IMPL_GET_FD != null && JavaLibCHelper.ReflectiveAccess.SEL_CH_IMPL.isInstance(channel)) {
         try {
            return (FileDescriptor)JavaLibCHelper.ReflectiveAccess.SEL_CH_IMPL_GET_FD.invoke(channel);
         } catch (Exception var5) {
         }
      } else if (JavaLibCHelper.ReflectiveAccess.FILE_CHANNEL_IMPL_FD != null && JavaLibCHelper.ReflectiveAccess.FILE_CHANNEL_IMPL.isInstance(channel)) {
         try {
            return (FileDescriptor)JavaLibCHelper.ReflectiveAccess.FILE_CHANNEL_IMPL_FD.get(channel);
         } catch (Exception var4) {
         }
      } else if (JavaLibCHelper.ReflectiveAccess.FILE_DESCRIPTOR_FD != null) {
         FileDescriptor unixFD = new FileDescriptor();

         try {
            Method getFD = channel.getClass().getMethod("getFD");
            JavaLibCHelper.ReflectiveAccess.FILE_DESCRIPTOR_FD.set(unixFD, (Integer)getFD.invoke(channel));
            return unixFD;
         } catch (Exception var3) {
         }
      }

      return new FileDescriptor();
   }

   static int errno() {
      Integer errno = (Integer)JavaLibCHelper.errno.get();
      return errno != null ? errno : 0;
   }

   static void errno(int errno) {
      JavaLibCHelper.errno.set(errno);
   }

   static void errno(Errno errno) {
      JavaLibCHelper.errno.set(errno.intValue());
   }

   public int chmod(String filename, int mode) {
      return Chmod.chmod(new JavaSecuredFile(filename), Integer.toOctalString(mode));
   }

   public int chown(String filename, int user, int group) {
      PosixExec launcher = new PosixExec(this.handler);
      int chownResult = -1;
      int chgrpResult = -1;

      try {
         if (user != -1) {
            chownResult = launcher.runAndWait("chown", "" + user, filename);
         }

         if (group != -1) {
            chgrpResult = launcher.runAndWait("chgrp ", "" + user, filename);
         }
      } catch (InterruptedException var8) {
         Thread.currentThread().interrupt();
      } catch (Exception var9) {
      }

      return chownResult != -1 && chgrpResult != -1 ? 0 : 1;
   }

   public static CharSequence crypt(CharSequence original, CharSequence salt) {
      return JavaCrypt.crypt(original, salt);
   }

   public static byte[] crypt(byte[] original, byte[] salt) {
      return JavaCrypt.crypt(new String(original), new String(salt)).toString().getBytes();
   }

   public int getfd(FileDescriptor descriptor) {
      return getfdFromDescriptor(descriptor);
   }

   public static int getfdFromDescriptor(FileDescriptor descriptor) {
      if (descriptor != null && JavaLibCHelper.ReflectiveAccess.FILE_DESCRIPTOR_FD != null) {
         try {
            return JavaLibCHelper.ReflectiveAccess.FILE_DESCRIPTOR_FD.getInt(descriptor);
         } catch (SecurityException var2) {
         } catch (IllegalArgumentException var3) {
         } catch (IllegalAccessException var4) {
         }

         return -1;
      } else {
         return -1;
      }
   }

   public static HANDLE gethandle(FileDescriptor descriptor) {
      if (descriptor != null && JavaLibCHelper.ReflectiveAccess.FILE_DESCRIPTOR_HANDLE != null) {
         try {
            return gethandle(JavaLibCHelper.ReflectiveAccess.FILE_DESCRIPTOR_HANDLE.getLong(descriptor));
         } catch (SecurityException var2) {
         } catch (IllegalArgumentException var3) {
         } catch (IllegalAccessException var4) {
         }

         return HANDLE.valueOf(-1L);
      } else {
         return HANDLE.valueOf(-1L);
      }
   }

   public static HANDLE gethandle(long descriptor) {
      return HANDLE.valueOf(descriptor);
   }

   public String getlogin() {
      return System.getProperty("user.name");
   }

   public String gethostname() {
      String hn = System.getenv("HOSTNAME");
      if (hn == null) {
         hn = System.getenv("COMPUTERNAME");
      }

      return hn;
   }

   public int getpid() {
      return this.handler.getPID();
   }

   public Passwd getpwent() {
      Passwd retVal = (Integer)this.pwIndex.get() == 0 ? new JavaPasswd(this.handler) : null;
      this.pwIndex.set((Integer)this.pwIndex.get() + 1);
      return retVal;
   }

   public int setpwent() {
      return 0;
   }

   public int endpwent() {
      this.pwIndex.set(0);
      return 0;
   }

   public Passwd getpwuid(int which) {
      return which == JavaPOSIX.LoginInfo.UID ? new JavaPasswd(this.handler) : null;
   }

   public int isatty(int fd) {
      return fd != 1 && fd != 0 && fd != 2 ? 0 : 1;
   }

   public int link(String oldpath, String newpath) {
      try {
         return (new PosixExec(this.handler)).runAndWait("ln", oldpath, newpath);
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
      } catch (Exception var5) {
      }

      errno(Errno.EINVAL);
      return -1;
   }

   public int lstat(String path, FileStat stat) {
      File file = new JavaSecuredFile(path);
      if (!file.exists()) {
         errno(Errno.ENOENT);
         return -1;
      } else {
         JavaFileStat jstat = (JavaFileStat)stat;
         jstat.setup(path);
         return 0;
      }
   }

   public int mkdir(String path, int mode) {
      File dir = new JavaSecuredFile(path);
      if (!dir.mkdir()) {
         return -1;
      } else {
         this.chmod(path, mode);
         return 0;
      }
   }

   public int rmdir(String path) {
      return (new JavaSecuredFile(path)).delete() ? 0 : -1;
   }

   public static int chdir(String path) {
      System.setProperty("user.dir", path);
      return 0;
   }

   public int stat(String path, FileStat stat) {
      JavaFileStat jstat = (JavaFileStat)stat;

      try {
         File file = new JavaSecuredFile(path);
         if (!file.exists()) {
            return -1;
         }

         jstat.setup(file.getCanonicalPath());
      } catch (IOException var5) {
      }

      return 0;
   }

   public int symlink(String oldpath, String newpath) {
      try {
         return (new PosixExec(this.handler)).runAndWait("ln", "-s", oldpath, newpath);
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
      } catch (Exception var5) {
      }

      errno(Errno.EEXIST);
      return -1;
   }

   public int readlink(String oldpath, ByteBuffer buffer, int length) throws IOException {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         (new PosixExec(this.handler)).runAndWait(baos, "readlink", oldpath);
         byte[] bytes = baos.toByteArray();
         if (bytes.length <= length && bytes.length != 0) {
            buffer.put(bytes, 0, bytes.length - 1);
            return buffer.position();
         } else {
            return -1;
         }
      } catch (InterruptedException var6) {
         Thread.currentThread().interrupt();
         errno(Errno.ENOENT);
         return -1;
      }
   }

   public Map getEnv() {
      return this.env;
   }

   public static FileDescriptor toFileDescriptor(int fileDescriptor) {
      FileDescriptor descriptor = new FileDescriptor();

      try {
         JavaLibCHelper.ReflectiveAccess.FILE_DESCRIPTOR_FD.set(descriptor, fileDescriptor);
         return descriptor;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException(var3);
      }
   }

   public static FileDescriptor toFileDescriptor(HANDLE fileDescriptor) {
      FileDescriptor descriptor = new FileDescriptor();

      try {
         JavaLibCHelper.ReflectiveAccess.FILE_DESCRIPTOR_HANDLE.set(descriptor, fileDescriptor.toPointer().address());
         return descriptor;
      } catch (IllegalAccessException var3) {
         throw new RuntimeException(var3);
      }
   }

   private static final class ErrnoParsingOutputStream extends OutputStream {
      private final ByteArrayOutputStream baos;
      private final AtomicReference errno;
      static Map errorPatterns = new HashMap();

      private ErrnoParsingOutputStream(AtomicReference errno) {
         this.baos = new ByteArrayOutputStream();
         this.errno = errno;
      }

      public void write(int b) throws IOException {
         if (b != 13 && b != 10 && b != -1) {
            this.baos.write(b);
         } else if (this.baos.size() > 0) {
            String errorString = this.baos.toString();
            this.baos.reset();
            this.parseError(errorString);
         }

      }

      void parseError(String errorString) {
         Iterator var2 = errorPatterns.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            if (((Pattern)entry.getKey()).matcher(errorString).find()) {
               this.errno.set(entry.getValue());
            }
         }

      }

      // $FF: synthetic method
      ErrnoParsingOutputStream(AtomicReference x0, Object x1) {
         this(x0);
      }

      static {
         errorPatterns.put(Pattern.compile("File exists"), Errno.EEXIST);
         errorPatterns.put(Pattern.compile("Operation not permitted"), Errno.EPERM);
         errorPatterns.put(Pattern.compile("No such file or directory"), Errno.ENOENT);
         errorPatterns.put(Pattern.compile("Input/output error"), Errno.EIO);
         errorPatterns.put(Pattern.compile("Not a directory"), Errno.ENOTDIR);
         errorPatterns.put(Pattern.compile("No space left on device"), Errno.ENOSPC);
         errorPatterns.put(Pattern.compile("Read-only file system"), Errno.EROFS);
         errorPatterns.put(Pattern.compile("Too many links"), Errno.EMLINK);
      }
   }

   private static class PosixExec extends ExecIt {
      private final AtomicReference errno;
      private final ErrnoParsingOutputStream errorStream;

      public PosixExec(POSIXHandler handler) {
         super(handler);
         this.errno = new AtomicReference(Errno.EINVAL);
         this.errorStream = new ErrnoParsingOutputStream(this.errno);
      }

      private int parseResult(int result) {
         if (result == 0) {
            return result;
         } else {
            JavaLibCHelper.errno((Errno)this.errno.get());
            return -1;
         }
      }

      public int runAndWait(String... args) throws IOException, InterruptedException {
         return this.runAndWait(this.handler.getOutputStream(), this.errorStream, args);
      }

      public int runAndWait(OutputStream output, String... args) throws IOException, InterruptedException {
         return this.runAndWait(output, this.errorStream, args);
      }

      public int runAndWait(OutputStream output, OutputStream error, String... args) throws IOException, InterruptedException {
         return this.parseResult(super.runAndWait(output, error, args));
      }
   }

   private static class ReflectiveAccess {
      private static final Class SEL_CH_IMPL;
      private static final Method SEL_CH_IMPL_GET_FD;
      private static final Class FILE_CHANNEL_IMPL;
      private static final Field FILE_CHANNEL_IMPL_FD;
      private static final Field FILE_DESCRIPTOR_FD;
      private static final Field FILE_DESCRIPTOR_HANDLE;

      static {
         boolean callSetAccessible = System.getProperty("java.version").compareTo("11") < 0;

         Method getFD;
         Class selChImpl;
         try {
            selChImpl = Class.forName("sun.nio.ch.SelChImpl");

            try {
               getFD = selChImpl.getMethod("getFD");
               if (callSetAccessible) {
                  getFD.setAccessible(true);
               }
            } catch (Exception var12) {
               getFD = null;
            }
         } catch (Exception var13) {
            selChImpl = null;
            getFD = null;
         }

         SEL_CH_IMPL = selChImpl;
         SEL_CH_IMPL_GET_FD = getFD;

         Field fd;
         Class fileChannelImpl;
         try {
            fileChannelImpl = Class.forName("sun.nio.ch.FileChannelImpl");

            try {
               fd = fileChannelImpl.getDeclaredField("fd");
               if (callSetAccessible) {
                  fd.setAccessible(true);
               }
            } catch (Exception var10) {
               fd = null;
            }
         } catch (Exception var11) {
            fileChannelImpl = null;
            fd = null;
         }

         FILE_CHANNEL_IMPL = fileChannelImpl;
         FILE_CHANNEL_IMPL_FD = fd;

         Field ffd;
         try {
            ffd = FileDescriptor.class.getDeclaredField("fd");
            if (callSetAccessible) {
               ffd.setAccessible(true);
            }
         } catch (Exception var9) {
            ffd = null;
         }

         FILE_DESCRIPTOR_FD = ffd;
         if (Platform.IS_WINDOWS) {
            Field handle;
            try {
               handle = FileDescriptor.class.getDeclaredField("handle");
               if (callSetAccessible) {
                  handle.setAccessible(true);
               }
            } catch (Exception var8) {
               handle = null;
            }

            FILE_DESCRIPTOR_HANDLE = handle;
         } else {
            FILE_DESCRIPTOR_HANDLE = null;
         }

      }
   }
}
