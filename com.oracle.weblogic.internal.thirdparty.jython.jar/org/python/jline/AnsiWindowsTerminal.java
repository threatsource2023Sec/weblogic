package org.python.jline;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.AnsiOutputStream;
import org.fusesource.jansi.WindowsAnsiOutputStream;
import org.python.jline.internal.Configuration;

public class AnsiWindowsTerminal extends WindowsTerminal {
   private final boolean ansiSupported = detectAnsiSupport();

   public OutputStream wrapOutIfNeeded(OutputStream out) {
      return wrapOutputStream(out);
   }

   private static OutputStream wrapOutputStream(OutputStream stream) {
      if (Configuration.isWindows()) {
         try {
            return new WindowsAnsiOutputStream(stream);
         } catch (Throwable var2) {
            return new AnsiOutputStream(stream);
         }
      } else {
         return stream;
      }
   }

   private static boolean detectAnsiSupport() {
      OutputStream out = AnsiConsole.wrapOutputStream(new ByteArrayOutputStream());

      try {
         out.close();
      } catch (Exception var2) {
      }

      return out instanceof WindowsAnsiOutputStream;
   }

   public AnsiWindowsTerminal() throws Exception {
   }

   public boolean isAnsiSupported() {
      return this.ansiSupported;
   }

   public boolean hasWeirdWrap() {
      return false;
   }
}
