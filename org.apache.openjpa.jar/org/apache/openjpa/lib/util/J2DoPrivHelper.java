package org.apache.openjpa.lib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.zip.ZipFile;
import serp.bytecode.BCClass;
import serp.bytecode.BCClassLoader;
import serp.bytecode.Code;
import serp.bytecode.FieldInstruction;
import serp.bytecode.Project;

public abstract class J2DoPrivHelper {
   private static String lineSeparator = null;
   private static String pathSeparator = null;

   public static final String getLineSeparator() {
      if (lineSeparator == null) {
         lineSeparator = (String)AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               return System.getProperty("line.separator");
            }
         });
      }

      return lineSeparator;
   }

   public static final String getPathSeparator() {
      if (pathSeparator == null) {
         pathSeparator = (String)AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               return System.getProperty("path.separator");
            }
         });
      }

      return pathSeparator;
   }

   public static final PrivilegedAction setAccessibleAction(final AccessibleObject aObj, final boolean flag) {
      return new PrivilegedAction() {
         public Object run() {
            aObj.setAccessible(flag);
            return (Object)null;
         }
      };
   }

   public static final PrivilegedExceptionAction getForNameAction(final String className, final boolean initializeBoolean, final ClassLoader classLoader) {
      return new PrivilegedExceptionAction() {
         public Object run() throws ClassNotFoundException {
            return Class.forName(className, initializeBoolean, classLoader);
         }
      };
   }

   public static final PrivilegedAction getClassLoaderAction(final Class clazz) {
      return new PrivilegedAction() {
         public Object run() {
            return clazz.getClassLoader();
         }
      };
   }

   public static final PrivilegedExceptionAction getDeclaredFieldAction(final Class clazz, final String name) {
      return new PrivilegedExceptionAction() {
         public Object run() throws NoSuchFieldException {
            return clazz.getDeclaredField(name);
         }
      };
   }

   public static final PrivilegedAction getDeclaredFieldsAction(final Class clazz) {
      return new PrivilegedAction() {
         public Object run() {
            return clazz.getDeclaredFields();
         }
      };
   }

   public static final PrivilegedExceptionAction getDeclaredMethodAction(final Class clazz, final String name, final Class[] parameterTypes) {
      return new PrivilegedExceptionAction() {
         public Object run() throws NoSuchMethodException {
            return clazz.getDeclaredMethod(name, parameterTypes);
         }
      };
   }

   public static final PrivilegedAction getDeclaredMethodsAction(final Class clazz) {
      return new PrivilegedAction() {
         public Object run() {
            return clazz.getDeclaredMethods();
         }
      };
   }

   public static final PrivilegedAction getResourceAction(final Class clazz, final String resource) {
      return new PrivilegedAction() {
         public Object run() {
            return clazz.getResource(resource);
         }
      };
   }

   public static final PrivilegedExceptionAction newInstanceAction(final Class clazz) throws IllegalAccessException, InstantiationException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IllegalAccessException, InstantiationException {
            return clazz.newInstance();
         }
      };
   }

   public static final PrivilegedAction getParentAction(final ClassLoader loader) {
      return new PrivilegedAction() {
         public Object run() {
            return loader.getParent();
         }
      };
   }

   public static final PrivilegedAction getResourceAction(final ClassLoader loader, final String resource) {
      return new PrivilegedAction() {
         public Object run() {
            return loader.getResource(resource);
         }
      };
   }

   public static final PrivilegedExceptionAction getResourcesAction(final ClassLoader loader, final String resource) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            return loader.getResources(resource);
         }
      };
   }

   public static final PrivilegedAction getSystemClassLoaderAction() {
      return new PrivilegedAction() {
         public Object run() {
            return ClassLoader.getSystemClassLoader();
         }
      };
   }

   public static final PrivilegedAction deleteAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            return f.delete() ? Boolean.TRUE : Boolean.FALSE;
         }
      };
   }

   public static final PrivilegedAction existsAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            try {
               return f.exists() ? Boolean.TRUE : Boolean.FALSE;
            } catch (NullPointerException var2) {
               return Boolean.FALSE;
            }
         }
      };
   }

   public static final PrivilegedAction getAbsoluteFileAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            return f.getAbsoluteFile();
         }
      };
   }

   public static final PrivilegedAction getAbsolutePathAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            return f.getAbsolutePath();
         }
      };
   }

   public static final PrivilegedExceptionAction getCanonicalPathAction(final File f) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            return f.getCanonicalPath();
         }
      };
   }

   public static final PrivilegedAction isDirectoryAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            return f.isDirectory() ? Boolean.TRUE : Boolean.FALSE;
         }
      };
   }

   public static final PrivilegedAction isFileAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            return f.isFile() ? Boolean.TRUE : Boolean.FALSE;
         }
      };
   }

   public static final PrivilegedAction lengthAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            return new Long(f.length());
         }
      };
   }

   public static final PrivilegedAction listFilesAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            return f.listFiles();
         }
      };
   }

   public static final PrivilegedAction mkdirsAction(final File f) {
      return new PrivilegedAction() {
         public Object run() {
            return f.mkdirs() ? Boolean.TRUE : Boolean.FALSE;
         }
      };
   }

   public static final PrivilegedAction renameToAction(final File from, final File to) {
      return new PrivilegedAction() {
         public Object run() {
            return from.renameTo(to) ? Boolean.TRUE : Boolean.FALSE;
         }
      };
   }

   public static final PrivilegedExceptionAction toURLAction(final File file) throws MalformedURLException {
      return new PrivilegedExceptionAction() {
         public Object run() throws MalformedURLException {
            return file.toURL();
         }
      };
   }

   public static final PrivilegedExceptionAction newFileInputStreamAction(final File f) throws FileNotFoundException {
      return new PrivilegedExceptionAction() {
         public Object run() throws FileNotFoundException {
            return new FileInputStream(f);
         }
      };
   }

   public static final PrivilegedExceptionAction newFileOutputStreamAction(final File f) throws FileNotFoundException {
      return new PrivilegedExceptionAction() {
         public Object run() throws FileNotFoundException {
            return new FileOutputStream(f);
         }
      };
   }

   public static final PrivilegedExceptionAction newFileOutputStreamAction(final String f, final boolean append) throws FileNotFoundException {
      return new PrivilegedExceptionAction() {
         public Object run() throws FileNotFoundException {
            return new FileOutputStream(f, append);
         }
      };
   }

   public static final PrivilegedExceptionAction getByNameAction(final String hostname) throws UnknownHostException {
      return new PrivilegedExceptionAction() {
         public Object run() throws UnknownHostException {
            return InetAddress.getByName(hostname);
         }
      };
   }

   public static final PrivilegedExceptionAction newSocketAction(final InetAddress host, final int port) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            return new Socket(host, port);
         }
      };
   }

   public static final PrivilegedExceptionAction newServerSocketAction(final int port) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            return new ServerSocket(port);
         }
      };
   }

   public static final PrivilegedExceptionAction acceptAction(final ServerSocket ss) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            return ss.accept();
         }
      };
   }

   public static final PrivilegedAction getPropertiesAction() {
      return new PrivilegedAction() {
         public Object run() {
            return System.getProperties();
         }
      };
   }

   public static final PrivilegedAction getPropertyAction(final String name) {
      return new PrivilegedAction() {
         public Object run() {
            return System.getProperty(name);
         }
      };
   }

   public static final PrivilegedAction getContextClassLoaderAction() {
      return new PrivilegedAction() {
         public Object run() {
            return Thread.currentThread().getContextClassLoader();
         }
      };
   }

   public static final PrivilegedAction newDaemonThreadAction(final Runnable target, final String name) {
      return new PrivilegedAction() {
         public Object run() {
            Thread thread = new Thread(target, name);
            thread.setDaemon(true);
            return thread;
         }
      };
   }

   public static final PrivilegedExceptionAction openStreamAction(final URL url) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            return url.openStream();
         }
      };
   }

   public static final PrivilegedExceptionAction getContentAction(final URLConnection con) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            return con.getContent();
         }
      };
   }

   public static final PrivilegedExceptionAction newZipFileAction(final File f) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            return new ZipFile(f);
         }
      };
   }

   public static final PrivilegedAction newCodeAction() {
      return new PrivilegedAction() {
         public Object run() {
            return new Code();
         }
      };
   }

   public static final PrivilegedAction newTemporaryClassLoaderAction(final ClassLoader parent) {
      return new PrivilegedAction() {
         public Object run() {
            return new TemporaryClassLoader(parent);
         }
      };
   }

   public static final PrivilegedAction newMultiClassLoaderAction() {
      return new PrivilegedAction() {
         public Object run() {
            return new MultiClassLoader();
         }
      };
   }

   public static final PrivilegedAction newBCClassLoaderAction(final Project project, final ClassLoader parent) {
      return new PrivilegedAction() {
         public Object run() {
            return new BCClassLoader(project, parent);
         }
      };
   }

   public static final PrivilegedAction newBCClassLoaderAction(final Project project) {
      return new PrivilegedAction() {
         public Object run() {
            return new BCClassLoader(project);
         }
      };
   }

   public static final PrivilegedExceptionAction bCClassWrite(final BCClass bc, final File f) throws IOException {
      return new PrivilegedExceptionAction() {
         public Object run() throws IOException {
            bc.write(f);
            return null;
         }
      };
   }

   public static final PrivilegedAction getBCClassFieldsAction(final BCClass bcClass, final String fieldName) {
      return new PrivilegedAction() {
         public Object run() {
            return bcClass.getFields(fieldName);
         }
      };
   }

   public static final PrivilegedAction getFieldInstructionFieldAction(final FieldInstruction instruction) {
      return new PrivilegedAction() {
         public Object run() {
            return instruction.getField();
         }
      };
   }

   public static final PrivilegedAction loadProjectClassAction(final Project project, final Class clazz) {
      return new PrivilegedAction() {
         public Object run() {
            return project.loadClass(clazz);
         }
      };
   }

   public static final PrivilegedAction loadProjectClassAction(final Project project, final String clazzName) {
      return new PrivilegedAction() {
         public Object run() {
            return project.loadClass(clazzName);
         }
      };
   }
}
