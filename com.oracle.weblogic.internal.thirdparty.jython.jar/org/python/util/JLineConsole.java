package org.python.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import jnr.constants.platform.Errno;
import org.python.core.PlainConsole;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.jline.Terminal;
import org.python.jline.WindowsTerminal;
import org.python.jline.console.ConsoleReader;
import org.python.jline.console.history.FileHistory;

public class JLineConsole extends PlainConsole {
   public ConsoleReader reader;
   protected PyObject startup_hook;
   protected PyObject pre_input_hook;
   private boolean windows;
   protected static final String CTRL_Z = "\u001a";
   private ConsoleOutputStream outWrapper;
   private static final List SUSPENDED_STRERRORS;

   public JLineConsole(String encoding) {
      super(encoding);
      System.setProperty("org.python.jline.WindowsTerminal.input.encoding", this.encoding);
      System.setProperty("input.encoding", this.encoding);
   }

   public void install() {
      String userHomeSpec = System.getProperty("user.home", ".");

      try {
         InputStream in = new FileInputStream(FileDescriptor.in);
         this.reader = new ConsoleReader("jython", in, System.out, (Terminal)null, this.encoding);
         this.reader.setKeyMap("jython");
         this.reader.setHandleUserInterrupt(true);
         this.reader.setCopyPasteDetection(true);
         this.reader.setBellEnabled(false);
         this.reader.setExpandEvents(false);
         this.outWrapper = new ConsoleOutputStream(System.out, this.reader.getTerminal().getWidth());
         System.setOut(new PrintStream(this.outWrapper, true, this.encoding));
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      try {
         File historyFile = new File(userHomeSpec, ".jline-jython.history");
         FileHistory history = new FileHistory(historyFile);
         Runtime.getRuntime().addShutdownHook(new Thread(new HistoryCloser(history)));
         this.reader.setHistory(history);
      } catch (IOException var4) {
      }

      this.windows = this.reader.getTerminal() instanceof WindowsTerminal;
      FilterInputStream wrapper = new Stream();
      System.setIn(wrapper);
   }

   private String readerReadLine(String prompt) throws IOException, EOFException {
      while(true) {
         try {
            if (this.startup_hook != null) {
               this.startup_hook.__call__();
            }

            try {
               this.reader.getTerminal().init();
            } catch (Exception var5) {
            }

            this.reader.setPrompt((String)null);
            this.reader.redrawLine();
            return this.reader.readLine(prompt);
         } catch (IOException var6) {
            if (!this.fromSuspend(var6)) {
               throw var6;
            }

            try {
               this.reader.resetPromptLine(prompt, (String)null, 0);
               prompt = "";
            } catch (Exception var4) {
               throw new IOException("Failed to re-initialize JLine: " + var4.getMessage());
            }
         }
      }
   }

   private boolean fromSuspend(IOException ioe) {
      return !this.windows && SUSPENDED_STRERRORS.contains(ioe.getMessage());
   }

   private boolean isEOF(String line) {
      return line == null || this.windows && "\u001a".equals(line);
   }

   public ConsoleReader getReader() {
      return this.reader;
   }

   public PyObject getStartupHook() {
      return this.startup_hook;
   }

   public void setStartupHook(PyObject hook) {
      if (hook == Py.None) {
         hook = null;
      }

      this.startup_hook = hook;
   }

   static {
      SUSPENDED_STRERRORS = Arrays.asList(Errno.EINTR.description(), Errno.EIO.description());
   }

   private class Stream extends ConsoleInputStream {
      Stream() {
         super(System.in, JLineConsole.this.encodingCharset, ConsoleInputStream.EOLPolicy.ADD, LINE_SEPARATOR);
      }

      protected CharSequence getLine() throws IOException, EOFException {
         String prompt = JLineConsole.this.outWrapper.getPrompt(JLineConsole.this.encodingCharset).toString();
         String line = JLineConsole.this.readerReadLine(prompt);
         if (!JLineConsole.this.isEOF(line)) {
            return line;
         } else {
            throw new EOFException();
         }
      }
   }

   private static class HistoryCloser implements Runnable {
      FileHistory history;

      public HistoryCloser(FileHistory history) {
         this.history = history;
      }

      public void run() {
         try {
            this.history.flush();
         } catch (IOException var2) {
         }

      }
   }
}
