package weblogic.management.scripting;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JLineWrapper {
   private final TerminalWrapper termWrapper;
   private final Object jLineReader;
   private final ClassLoader classLoader;
   private final Class jLineConsoleStream;

   public JLineWrapper(ClassLoader classLoader, File historyFile) throws Exception {
      this.classLoader = classLoader;
      ClassLoader origCL = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(classLoader);
         TerminalWrapper t = null;
         String osName = System.getProperty("os.name").toLowerCase();
         if (osName != null && osName.contains("windows")) {
            t = new WindowsTermWrapper(classLoader);
         }

         System.setProperty("input.encoding", "ISO-8859-1");
         System.setProperty("jline.UnixTerminal.output.encoding", "ISO-8859-1");
         System.setProperty("jline.WindowsTerminal.output.encoding", "ISO-8859-1");
         Class readerClass = Class.forName("jline.console.ConsoleReader", true, classLoader);
         this.jLineReader = readerClass.newInstance();

         Method setInMethod;
         try {
            setInMethod = readerClass.getMethod("setExpandEvents", Boolean.TYPE);
            setInMethod.invoke(this.jLineReader, false);
         } catch (NoSuchMethodException var12) {
         }

         this.setupHistory(historyFile, readerClass);
         this.jLineConsoleStream = Class.forName("jline.console.internal.ConsoleReaderInputStream", true, classLoader);
         setInMethod = this.jLineConsoleStream.getMethod("setIn", readerClass);
         setInMethod.setAccessible(true);
         setInMethod.invoke((Object)null, this.jLineReader);
         Object terminal = readerClass.getMethod("getTerminal").invoke(this.jLineReader);
         if ("jline.UnixTerminal".equals(terminal.getClass().getName())) {
            t = this.setupUnixTermWrapper(readerClass, terminal);
         }

         this.termWrapper = (TerminalWrapper)t;
      } finally {
         Thread.currentThread().setContextClassLoader(origCL);
      }

   }

   private UnixTermWrapper setupUnixTermWrapper(Class readerClass, Object terminal) throws Exception {
      Method method = terminal.getClass().getMethod("getSettings");
      Object terminalLineSettings = method.invoke(terminal);
      Field f = terminalLineSettings.getClass().getDeclaredField("initialConfig");
      f.setAccessible(true);
      String initialSettings = (String)f.get(terminalLineSettings);
      return new UnixTermWrapper(initialSettings);
   }

   private void setupHistory(File historyFile, Class readerClass) throws Exception {
      Object historyObj;
      Class historyClass;
      if (historyFile == null) {
         historyClass = Class.forName("jline.console.history.MemoryHistory", true, this.classLoader);
         historyObj = historyClass.newInstance();
      } else {
         historyClass = Class.forName("jline.console.history.FileHistory", true, this.classLoader);
         Constructor constructor = historyClass.getConstructor(File.class);
         historyObj = constructor.newInstance(historyFile);
      }

      historyClass = Class.forName("jline.console.history.History", true, this.classLoader);
      Method setHistoryMethod = readerClass.getMethod("setHistory", historyClass);
      setHistoryMethod.invoke(this.jLineReader, historyObj);
   }

   public void reInit() {
      if (this.termWrapper != null) {
         this.termWrapper.reInit();
      }

   }

   public void unInit() {
      if (this.termWrapper != null) {
         this.termWrapper.unInit();
      }

   }

   public void cleanup() {
      ClassLoader prevCls = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(this.classLoader);
         this.jLineConsoleStream.getMethod("restoreIn").invoke((Object)null);
      } catch (Throwable var6) {
      } finally {
         Thread.currentThread().setContextClassLoader(prevCls);
      }

   }

   public String readPassword() {
      ClassLoader prevCls = Thread.currentThread().getContextClassLoader();

      String var3;
      try {
         Thread.currentThread().setContextClassLoader(this.classLoader);
         Method readLineMethod = this.jLineReader.getClass().getMethod("readLine", Character.class);
         var3 = (String)readLineMethod.invoke(this.jLineReader, new Character('\u0000'));
         return var3;
      } catch (Throwable var7) {
         if (Boolean.getBoolean("wlst.debug.init")) {
            var7.printStackTrace();
         }

         var3 = null;
      } finally {
         Thread.currentThread().setContextClassLoader(prevCls);
      }

      return var3;
   }

   public String toString() {
      return "JLineWrapper: " + this.termWrapper;
   }

   private class WindowsTermWrapper implements TerminalWrapper {
      private final int origSettings;
      private final int newSettings;
      private final Method consoleModeSetter;
      private final Object windowsTerm;

      public WindowsTermWrapper(ClassLoader classLoader) throws Exception {
         Class clazz = Class.forName("jline.WindowsTerminal", true, classLoader);
         this.windowsTerm = clazz.newInstance();
         Method m = clazz.getDeclaredMethod("loadLibrary", String.class);
         m.setAccessible(true);
         m.invoke(this.windowsTerm, "jline");
         m = clazz.getDeclaredMethod("getConsoleMode");
         m.setAccessible(true);
         this.origSettings = (Integer)m.invoke(this.windowsTerm);
         this.newSettings = this.origSettings & -16;
         this.consoleModeSetter = clazz.getDeclaredMethod("setConsoleMode", Integer.TYPE);
         this.consoleModeSetter.setAccessible(true);
      }

      public String toString() {
         return "WINDOWS - orig: " + this.origSettings;
      }

      private void setMode(int mode) {
         try {
            this.consoleModeSetter.invoke(this.windowsTerm, mode);
         } catch (Exception var3) {
            if (Boolean.getBoolean("wlst.debug.init")) {
               var3.printStackTrace();
            }
         }

      }

      public void reInit() {
         this.setMode(this.newSettings);
      }

      public void unInit() {
         this.setMode(this.origSettings);
      }
   }

   private class UnixTermWrapper implements TerminalWrapper {
      private final String origSettings;

      public UnixTermWrapper(String origSettings) {
         this.origSettings = origSettings;
      }

      public String toString() {
         return "UNIX - [" + this.origSettings + "]";
      }

      private void execShell(String args) {
         try {
            Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty " + args + " < /dev/tty"});
            p.waitFor();
         } catch (Exception var3) {
            if (Boolean.getBoolean("wlst.debug.init")) {
               var3.printStackTrace();
            }
         }

      }

      public void reInit() {
         this.execShell("-icanon min 1");
         this.execShell("-echo");
      }

      public void unInit() {
         this.execShell(this.origSettings);
      }
   }

   private interface TerminalWrapper {
      void reInit();

      void unInit();
   }
}
