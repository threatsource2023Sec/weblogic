package org.python.jline;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.python.jline.internal.Log;
import org.python.jline.internal.ShutdownHooks;

public abstract class TerminalSupport implements Terminal {
   public static final int DEFAULT_WIDTH = 80;
   public static final int DEFAULT_HEIGHT = 24;
   private ShutdownHooks.Task shutdownTask;
   private boolean supported;
   private boolean echoEnabled;
   private boolean ansiSupported;

   protected TerminalSupport(boolean supported) {
      this.supported = supported;
   }

   public void init() throws Exception {
      if (this.shutdownTask != null) {
         ShutdownHooks.remove(this.shutdownTask);
      }

      this.shutdownTask = ShutdownHooks.add(new ShutdownHooks.Task() {
         public void run() throws Exception {
            TerminalSupport.this.restore();
         }
      });
   }

   public void restore() throws Exception {
      TerminalFactory.resetIf(this);
      if (this.shutdownTask != null) {
         ShutdownHooks.remove(this.shutdownTask);
         this.shutdownTask = null;
      }

   }

   public void reset() throws Exception {
      this.restore();
      this.init();
   }

   public final boolean isSupported() {
      return this.supported;
   }

   public synchronized boolean isAnsiSupported() {
      return this.ansiSupported;
   }

   protected synchronized void setAnsiSupported(boolean supported) {
      this.ansiSupported = supported;
      Log.debug("Ansi supported: ", supported);
   }

   public OutputStream wrapOutIfNeeded(OutputStream out) {
      return out;
   }

   public boolean hasWeirdWrap() {
      return true;
   }

   public int getWidth() {
      return 80;
   }

   public int getHeight() {
      return 24;
   }

   public synchronized boolean isEchoEnabled() {
      return this.echoEnabled;
   }

   public synchronized void setEchoEnabled(boolean enabled) {
      this.echoEnabled = enabled;
      Log.debug("Echo enabled: ", enabled);
   }

   public void disableInterruptCharacter() {
   }

   public void enableInterruptCharacter() {
   }

   public InputStream wrapInIfNeeded(InputStream in) throws IOException {
      return in;
   }

   public String getOutputEncoding() {
      return null;
   }
}
