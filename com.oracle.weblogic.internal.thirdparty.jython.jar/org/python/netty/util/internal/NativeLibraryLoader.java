package org.python.netty.util.internal;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Locale;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class NativeLibraryLoader {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);
   private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
   private static final String OSNAME;
   private static final File WORKDIR;
   private static final boolean DELETE_NATIVE_LIB_AFTER_LOADING;

   private static File tmpdir() {
      File f;
      try {
         f = toDirectory(SystemPropertyUtil.get("org.python.netty.tmpdir"));
         if (f != null) {
            logger.debug("-Dio.netty.tmpdir: " + f);
            return f;
         }

         f = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
         if (f != null) {
            logger.debug("-Dio.netty.tmpdir: " + f + " (java.io.tmpdir)");
            return f;
         }

         if (isWindows()) {
            f = toDirectory(System.getenv("TEMP"));
            if (f != null) {
               logger.debug("-Dio.netty.tmpdir: " + f + " (%TEMP%)");
               return f;
            }

            String userprofile = System.getenv("USERPROFILE");
            if (userprofile != null) {
               f = toDirectory(userprofile + "\\AppData\\Local\\Temp");
               if (f != null) {
                  logger.debug("-Dio.netty.tmpdir: " + f + " (%USERPROFILE%\\AppData\\Local\\Temp)");
                  return f;
               }

               f = toDirectory(userprofile + "\\Local Settings\\Temp");
               if (f != null) {
                  logger.debug("-Dio.netty.tmpdir: " + f + " (%USERPROFILE%\\Local Settings\\Temp)");
                  return f;
               }
            }
         } else {
            f = toDirectory(System.getenv("TMPDIR"));
            if (f != null) {
               logger.debug("-Dio.netty.tmpdir: " + f + " ($TMPDIR)");
               return f;
            }
         }
      } catch (Exception var2) {
      }

      if (isWindows()) {
         f = new File("C:\\Windows\\Temp");
      } else {
         f = new File("/tmp");
      }

      logger.warn("Failed to get the temporary directory; falling back to: " + f);
      return f;
   }

   private static File toDirectory(String path) {
      if (path == null) {
         return null;
      } else {
         File f = new File(path);
         f.mkdirs();
         if (!f.isDirectory()) {
            return null;
         } else {
            try {
               return f.getAbsoluteFile();
            } catch (Exception var3) {
               return f;
            }
         }
      }
   }

   private static boolean isWindows() {
      return OSNAME.startsWith("windows");
   }

   private static boolean isOSX() {
      return OSNAME.startsWith("macosx") || OSNAME.startsWith("osx");
   }

   public static void loadFirstAvailable(ClassLoader loader, String... names) {
      String[] var2 = names;
      int var3 = names.length;
      int var4 = 0;

      while(var4 < var3) {
         String name = var2[var4];

         try {
            load(name, loader);
            logger.debug("Successfully loaded the library: {}", (Object)name);
            return;
         } catch (Throwable var7) {
            logger.debug("Unable to load the library '{}', trying next name...", name, var7);
            ++var4;
         }
      }

      throw new IllegalArgumentException("Failed to load any of the given libraries: " + Arrays.toString(names));
   }

   public static void load(String name, ClassLoader loader) {
      String libname = System.mapLibraryName(name);
      String path = "META-INF/native/" + libname;
      URL url = loader.getResource(path);
      if (url == null && isOSX()) {
         if (path.endsWith(".jnilib")) {
            url = loader.getResource("META-INF/native/lib" + name + ".dynlib");
         } else {
            url = loader.getResource("META-INF/native/lib" + name + ".jnilib");
         }
      }

      if (url == null) {
         loadLibrary(loader, name, false);
      } else {
         int index = libname.lastIndexOf(46);
         String prefix = libname.substring(0, index);
         String suffix = libname.substring(index, libname.length());
         InputStream in = null;
         OutputStream out = null;
         File tmpFile = null;

         try {
            tmpFile = File.createTempFile(prefix, suffix, WORKDIR);
            in = url.openStream();
            out = new FileOutputStream(tmpFile);
            byte[] buffer = new byte[8192];

            int length;
            while((length = in.read(buffer)) > 0) {
               out.write(buffer, 0, length);
            }

            out.flush();
            closeQuietly(out);
            out = null;
            loadLibrary(loader, tmpFile.getPath(), true);
         } catch (Exception var16) {
            throw (UnsatisfiedLinkError)(new UnsatisfiedLinkError("could not load a native library: " + name)).initCause(var16);
         } finally {
            closeQuietly(in);
            closeQuietly(out);
            if (tmpFile != null && (!DELETE_NATIVE_LIB_AFTER_LOADING || !tmpFile.delete())) {
               tmpFile.deleteOnExit();
            }

         }
      }
   }

   private static void loadLibrary(ClassLoader loader, String name, boolean absolute) {
      try {
         Class newHelper = tryToLoadClass(loader, NativeLibraryUtil.class);
         loadLibraryByHelper(newHelper, name, absolute);
         return;
      } catch (UnsatisfiedLinkError var4) {
         logger.debug("Unable to load the library '{}', trying other loading mechanism.", name, var4);
      } catch (Exception var5) {
         logger.debug("Unable to load the library '{}', trying other loading mechanism.", name, var5);
      }

      NativeLibraryUtil.loadLibrary(name, absolute);
   }

   private static void loadLibraryByHelper(final Class helper, final String name, final boolean absolute) throws UnsatisfiedLinkError {
      Object ret = AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            try {
               Method method = helper.getMethod("loadLibrary", String.class, Boolean.TYPE);
               method.setAccessible(true);
               return method.invoke((Object)null, name, absolute);
            } catch (Exception var2) {
               return var2;
            }
         }
      });
      if (ret instanceof Throwable) {
         Throwable error = (Throwable)ret;
         Throwable cause = error.getCause();
         if (cause != null) {
            if (cause instanceof UnsatisfiedLinkError) {
               throw (UnsatisfiedLinkError)cause;
            } else {
               throw new UnsatisfiedLinkError(cause.getMessage());
            }
         } else {
            throw new UnsatisfiedLinkError(error.getMessage());
         }
      }
   }

   private static Class tryToLoadClass(final ClassLoader loader, final Class helper) throws ClassNotFoundException {
      try {
         return loader.loadClass(helper.getName());
      } catch (ClassNotFoundException var4) {
         final byte[] classBinary = classToByteArray(helper);
         return (Class)AccessController.doPrivileged(new PrivilegedAction() {
            public Class run() {
               try {
                  Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                  defineClass.setAccessible(true);
                  return (Class)defineClass.invoke(loader, helper.getName(), classBinary, 0, classBinary.length);
               } catch (Exception var2) {
                  throw new IllegalStateException("Define class failed!", var2);
               }
            }
         });
      }
   }

   private static byte[] classToByteArray(Class clazz) throws ClassNotFoundException {
      String fileName = clazz.getName();
      int lastDot = fileName.lastIndexOf(46);
      if (lastDot > 0) {
         fileName = fileName.substring(lastDot + 1);
      }

      URL classUrl = clazz.getResource(fileName + ".class");
      if (classUrl == null) {
         throw new ClassNotFoundException(clazz.getName());
      } else {
         byte[] buf = new byte[1024];
         ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
         InputStream in = null;

         try {
            in = classUrl.openStream();

            int r;
            while((r = in.read(buf)) != -1) {
               out.write(buf, 0, r);
            }

            byte[] var13 = out.toByteArray();
            return var13;
         } catch (IOException var11) {
            throw new ClassNotFoundException(clazz.getName(), var11);
         } finally {
            closeQuietly(in);
            closeQuietly(out);
         }
      }
   }

   private static void closeQuietly(Closeable c) {
      if (c != null) {
         try {
            c.close();
         } catch (IOException var2) {
         }
      }

   }

   private NativeLibraryLoader() {
   }

   static {
      OSNAME = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
      String workdir = SystemPropertyUtil.get("org.python.netty.native.workdir");
      if (workdir != null) {
         File f = new File(workdir);
         f.mkdirs();

         try {
            f = f.getAbsoluteFile();
         } catch (Exception var3) {
         }

         WORKDIR = f;
         logger.debug("-Dio.netty.native.workdir: " + WORKDIR);
      } else {
         WORKDIR = tmpdir();
         logger.debug("-Dio.netty.native.workdir: " + WORKDIR + " (io.netty.tmpdir)");
      }

      DELETE_NATIVE_LIB_AFTER_LOADING = SystemPropertyUtil.getBoolean("org.python.netty.native.deleteLibAfterLoading", true);
   }
}
