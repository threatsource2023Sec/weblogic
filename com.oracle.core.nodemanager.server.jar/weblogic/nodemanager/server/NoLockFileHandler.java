package weblogic.nodemanager.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.LoggingPermission;
import java.util.logging.StreamHandler;
import java.util.logging.XMLFormatter;

public class NoLockFileHandler extends StreamHandler {
   private MeteredStream meter;
   private boolean append;
   private int limit;
   private int count;
   private String pattern;
   private File[] files;
   private LogManager _manager = LogManager.getLogManager();
   private final Permission controlPermission = new LoggingPermission("control", (String)null);

   private void open(File fname, boolean append) throws IOException {
      int len = 0;
      if (append) {
         len = (int)fname.length();
      }

      FileOutputStream fout = new FileOutputStream(fname.toString(), append);
      BufferedOutputStream bout = new BufferedOutputStream(fout);
      this.meter = new MeteredStream(bout, len);
      this.setOutputStream(this.meter);
   }

   String getStringProperty(String name, String defaultValue) {
      String val = this._manager.getProperty(name);
      return val == null ? defaultValue : val.trim();
   }

   int getIntProperty(String name, int defaultValue) {
      String val = this._manager.getProperty(name);
      if (val == null) {
         return defaultValue;
      } else {
         try {
            return Integer.parseInt(val.trim());
         } catch (Exception var5) {
            return defaultValue;
         }
      }
   }

   boolean getBooleanProperty(String name, boolean defaultValue) {
      String val = this._manager.getProperty(name);
      if (val == null) {
         return defaultValue;
      } else {
         val = val.toLowerCase();
         if (!val.equals("true") && !val.equals("1")) {
            return !val.equals("false") && !val.equals("0") ? defaultValue : false;
         } else {
            return true;
         }
      }
   }

   Level getLevelProperty(String name, Level defaultValue) {
      String val = this._manager.getProperty(name);
      if (val == null) {
         return defaultValue;
      } else {
         Level l = null;

         try {
            l = Level.parse(val.trim());
         } catch (Exception var6) {
         }

         return l != null ? l : defaultValue;
      }
   }

   Filter getFilterProperty(String name, Filter defaultValue) {
      String val = this._manager.getProperty(name);

      try {
         if (val != null) {
            Class clz = ClassLoader.getSystemClassLoader().loadClass(val);
            return (Filter)clz.newInstance();
         }
      } catch (Exception var5) {
      }

      return defaultValue;
   }

   Formatter getFormatterProperty(String name, Formatter defaultValue) {
      String val = this._manager.getProperty(name);

      try {
         if (val != null) {
            Class clz = ClassLoader.getSystemClassLoader().loadClass(val);
            return (Formatter)clz.newInstance();
         }
      } catch (Exception var5) {
      }

      return defaultValue;
   }

   private void configure() {
      LogManager manager = LogManager.getLogManager();
      String cname = this.getClass().getName();
      this.pattern = this.getStringProperty(cname + ".pattern", "%h/java%u.log");
      this.limit = this.getIntProperty(cname + ".limit", 0);
      if (this.limit < 0) {
         this.limit = 0;
      }

      this.count = this.getIntProperty(cname + ".count", 1);
      if (this.count <= 0) {
         this.count = 1;
      }

      this.append = this.getBooleanProperty(cname + ".append", false);
      this.setLevel(this.getLevelProperty(cname + ".level", Level.ALL));
      this.setFilter(this.getFilterProperty(cname + ".filter", (Filter)null));
      this.setFormatter(this.getFormatterProperty(cname + ".formatter", new XMLFormatter()));

      try {
         this.setEncoding(this.getStringProperty(cname + ".encoding", (String)null));
      } catch (Exception var6) {
         try {
            this.setEncoding((String)null);
         } catch (Exception var5) {
         }
      }

   }

   public NoLockFileHandler(String pattern, int limit, int count, boolean append) throws IOException, SecurityException {
      if (limit >= 0 && count >= 1 && pattern.length() >= 1) {
         this.checkPermission();
         this.configure();
         this.pattern = pattern;
         this.limit = limit;
         this.count = count;
         this.append = append;
         this.openFiles();
      } else {
         throw new IllegalArgumentException();
      }
   }

   private boolean isParentWritable(Path path) {
      Path parent = path.getParent();
      if (parent == null) {
         parent = path.toAbsolutePath().getParent();
      }

      return parent != null && Files.isWritable(parent);
   }

   private void openFiles() throws IOException {
      LogManager manager = LogManager.getLogManager();
      this.checkPermission();
      if (this.count < 1) {
         throw new IllegalArgumentException("file count = " + this.count);
      } else {
         if (this.limit < 0) {
            this.limit = 0;
         }

         InitializationErrorManager em = new InitializationErrorManager();
         this.setErrorManager(em);
         this.files = new File[this.count];

         for(int i = 0; i < this.count; ++i) {
            this.files[i] = this.generate(this.pattern, i, 0);
         }

         if (this.append) {
            this.open(this.files[0], true);
         } else {
            this.rotate();
         }

         Exception ex = em.lastException;
         if (ex != null) {
            if (ex instanceof IOException) {
               throw (IOException)ex;
            } else if (ex instanceof SecurityException) {
               throw (SecurityException)ex;
            } else {
               throw new IOException("Exception: " + ex);
            }
         } else {
            this.setErrorManager(new ErrorManager());
         }
      }
   }

   private File generate(String pattern, int generation, int unique) throws IOException {
      File file = null;
      String word = "";
      int ix = 0;
      boolean sawg = false;
      boolean sawu = false;

      while(true) {
         while(ix < pattern.length()) {
            char ch = pattern.charAt(ix);
            ++ix;
            char ch2 = 0;
            if (ix < pattern.length()) {
               ch2 = Character.toLowerCase(pattern.charAt(ix));
            }

            if (ch == '/') {
               if (file == null) {
                  file = new File(word);
               } else {
                  file = new File(file, word);
               }

               word = "";
            } else {
               if (ch == '%') {
                  if (ch2 == 't') {
                     String tmpDir = System.getProperty("java.io.tmpdir");
                     if (tmpDir == null) {
                        tmpDir = System.getProperty("user.home");
                     }

                     file = new File(tmpDir);
                     ++ix;
                     word = "";
                     continue;
                  }

                  if (ch2 == 'h') {
                     file = new File(System.getProperty("user.home"));
                     if (isSetUID()) {
                        throw new IOException("can't use %h in set UID program");
                     }

                     ++ix;
                     word = "";
                     continue;
                  }

                  if (ch2 == 'g') {
                     word = word + generation;
                     sawg = true;
                     ++ix;
                     continue;
                  }

                  if (ch2 == 'u') {
                     word = word + unique;
                     sawu = true;
                     ++ix;
                     continue;
                  }

                  if (ch2 == '%') {
                     word = word + "%";
                     ++ix;
                     continue;
                  }
               }

               word = word + ch;
            }
         }

         if (this.count > 1 && !sawg) {
            word = word + "." + generation;
         }

         if (unique > 0 && !sawu) {
            word = word + "." + unique;
         }

         if (word.length() > 0) {
            if (file == null) {
               file = new File(word);
            } else {
               file = new File(file, word);
            }
         }

         return file;
      }
   }

   private synchronized void rotate() {
      Level oldLevel = this.getLevel();
      this.setLevel(Level.OFF);
      super.close();

      for(int i = this.count - 2; i >= 0; --i) {
         File f1 = this.files[i];
         File f2 = this.files[i + 1];
         if (f1.exists()) {
            if (f2.exists()) {
               f2.delete();
            }

            f1.renameTo(f2);
         }
      }

      try {
         this.open(this.files[0], false);
      } catch (IOException var5) {
         this.reportError((String)null, var5, 4);
      }

      this.setLevel(oldLevel);
   }

   public synchronized void publish(LogRecord record) {
      if (this.isLoggable(record)) {
         super.publish(record);
         this.flush();
         if (this.limit > 0 && this.meter.written >= this.limit) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  NoLockFileHandler.this.rotate();
                  return null;
               }
            });
         }

      }
   }

   public synchronized void close() throws SecurityException {
      super.close();
   }

   private static native boolean isSetUID();

   void checkPermission() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(this.controlPermission);
      }

   }

   private static class InitializationErrorManager extends ErrorManager {
      Exception lastException;

      private InitializationErrorManager() {
      }

      public void error(String msg, Exception ex, int code) {
         this.lastException = ex;
      }

      // $FF: synthetic method
      InitializationErrorManager(Object x0) {
         this();
      }
   }

   private class MeteredStream extends OutputStream {
      final OutputStream out;
      int written;

      MeteredStream(OutputStream out, int written) {
         this.out = out;
         this.written = written;
      }

      public void write(int b) throws IOException {
         this.out.write(b);
         ++this.written;
      }

      public void write(byte[] buff) throws IOException {
         this.out.write(buff);
         this.written += buff.length;
      }

      public void write(byte[] buff, int off, int len) throws IOException {
         this.out.write(buff, off, len);
         this.written += len;
      }

      public void flush() throws IOException {
         this.out.flush();
      }

      public void close() throws IOException {
         this.out.close();
      }
   }
}
