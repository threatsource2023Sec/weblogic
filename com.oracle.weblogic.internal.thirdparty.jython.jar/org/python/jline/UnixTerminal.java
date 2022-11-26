package org.python.jline;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.python.jline.internal.Configuration;
import org.python.jline.internal.InfoCmp;
import org.python.jline.internal.Log;
import org.python.jline.internal.Preconditions;
import org.python.jline.internal.TerminalLineSettings;

public class UnixTerminal extends TerminalSupport implements Terminal2 {
   private final TerminalLineSettings settings;
   private final String type;
   private String intr;
   private String lnext;
   private Set bools;
   private Map ints;
   private Map strings;

   public UnixTerminal() throws Exception {
      this("/dev/tty", (String)null);
   }

   public UnixTerminal(String ttyDevice) throws Exception {
      this(ttyDevice, (String)null);
   }

   public UnixTerminal(String ttyDevice, String type) throws Exception {
      super(true);
      this.bools = new HashSet();
      this.ints = new HashMap();
      this.strings = new HashMap();
      Preconditions.checkNotNull(ttyDevice);
      this.settings = TerminalLineSettings.getSettings(ttyDevice);
      if (type == null) {
         type = System.getenv("TERM");
      }

      this.type = type;
      this.parseInfoCmp();
   }

   public TerminalLineSettings getSettings() {
      return this.settings;
   }

   public void init() throws Exception {
      super.init();
      this.setAnsiSupported(true);
      if (Configuration.getOsName().contains("freebsd")) {
         this.settings.set("-icanon min 1 -inlcr -ixon");
      } else {
         this.settings.set("-icanon min 1 -icrnl -inlcr -ixon");
      }

      this.settings.undef("dsusp");
      this.setEchoEnabled(false);
      this.parseInfoCmp();
   }

   public void restore() throws Exception {
      this.settings.restore();
      super.restore();
   }

   public int getWidth() {
      int w = this.settings.getProperty("columns");
      return w < 1 ? 80 : w;
   }

   public int getHeight() {
      int h = this.settings.getProperty("rows");
      return h < 1 ? 24 : h;
   }

   public boolean hasWeirdWrap() {
      return this.getBooleanCapability("auto_right_margin") && this.getBooleanCapability("eat_newline_glitch");
   }

   public synchronized void setEchoEnabled(boolean enabled) {
      try {
         if (enabled) {
            this.settings.set("echo");
         } else {
            this.settings.set("-echo");
         }

         super.setEchoEnabled(enabled);
      } catch (Exception var3) {
         if (var3 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
         }

         Log.error("Failed to ", enabled ? "enable" : "disable", " echo", var3);
      }

   }

   public void disableInterruptCharacter() {
      try {
         this.intr = this.getSettings().getPropertyAsString("intr");
         if ("<undef>".equals(this.intr)) {
            this.intr = null;
         }

         this.settings.undef("intr");
      } catch (Exception var2) {
         if (var2 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
         }

         Log.error("Failed to disable interrupt character", var2);
      }

   }

   public void enableInterruptCharacter() {
      try {
         if (this.intr != null) {
            this.settings.set("intr", this.intr);
         }
      } catch (Exception var2) {
         if (var2 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
         }

         Log.error("Failed to enable interrupt character", var2);
      }

   }

   public void disableLitteralNextCharacter() {
      try {
         this.lnext = this.getSettings().getPropertyAsString("lnext");
         if ("<undef>".equals(this.lnext)) {
            this.lnext = null;
         }

         this.settings.undef("lnext");
      } catch (Exception var2) {
         if (var2 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
         }

         Log.error("Failed to disable litteral next character", var2);
      }

   }

   public void enableLitteralNextCharacter() {
      try {
         if (this.lnext != null) {
            this.settings.set("lnext", this.lnext);
         }
      } catch (Exception var2) {
         if (var2 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
         }

         Log.error("Failed to enable litteral next character", var2);
      }

   }

   public boolean getBooleanCapability(String capability) {
      return this.bools.contains(capability);
   }

   public Integer getNumericCapability(String capability) {
      return (Integer)this.ints.get(capability);
   }

   public String getStringCapability(String capability) {
      return (String)this.strings.get(capability);
   }

   private void parseInfoCmp() {
      String capabilities = null;
      if (this.type != null) {
         try {
            capabilities = InfoCmp.getInfoCmp(this.type);
         } catch (Exception var3) {
         }
      }

      if (capabilities == null) {
         capabilities = InfoCmp.getAnsiCaps();
      }

      InfoCmp.parseInfoCmp(capabilities, this.bools, this.ints, this.strings);
   }
}
