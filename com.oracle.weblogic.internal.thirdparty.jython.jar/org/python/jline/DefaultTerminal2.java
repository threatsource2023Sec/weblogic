package org.python.jline;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.python.jline.internal.InfoCmp;

public class DefaultTerminal2 implements Terminal2 {
   private final Terminal terminal;
   private final Set bools = new HashSet();
   private final Map strings = new HashMap();

   public DefaultTerminal2(Terminal terminal) {
      this.terminal = terminal;
      this.registerCap("key_backspace", "^H");
      this.registerCap("bell", "^G");
      this.registerCap("carriage_return", "^M");
      if (this.isSupported() && this.isAnsiSupported()) {
         this.registerCap("clr_eol", "\\E[K");
         this.registerCap("clr_bol", "\\E[1K");
         this.registerCap("cursor_up", "\\E[A");
         this.registerCap("cursor_down", "^J");
         this.registerCap("column_address", "\\E[%i%p1%dG");
         this.registerCap("clear_screen", "\\E[H\\E[2J");
         this.registerCap("parm_down_cursor", "\\E[%p1%dB");
         this.registerCap("cursor_left", "^H");
         this.registerCap("cursor_right", "\\E[C");
      }

      if (this.hasWeirdWrap()) {
         this.registerCap("eat_newline_glitch");
         this.registerCap("auto_right_margin");
      }

   }

   public void init() throws Exception {
      this.terminal.init();
   }

   public void restore() throws Exception {
      this.terminal.restore();
   }

   public void reset() throws Exception {
      this.terminal.reset();
   }

   public boolean isSupported() {
      return this.terminal.isSupported();
   }

   public int getWidth() {
      return this.terminal.getWidth();
   }

   public int getHeight() {
      return this.terminal.getHeight();
   }

   public boolean isAnsiSupported() {
      return this.terminal.isAnsiSupported();
   }

   public OutputStream wrapOutIfNeeded(OutputStream out) {
      return this.terminal.wrapOutIfNeeded(out);
   }

   public InputStream wrapInIfNeeded(InputStream in) throws IOException {
      return this.terminal.wrapInIfNeeded(in);
   }

   public boolean hasWeirdWrap() {
      return this.terminal.hasWeirdWrap();
   }

   public boolean isEchoEnabled() {
      return this.terminal.isEchoEnabled();
   }

   public void setEchoEnabled(boolean enabled) {
      this.terminal.setEchoEnabled(enabled);
   }

   public void disableInterruptCharacter() {
      this.terminal.disableInterruptCharacter();
   }

   public void enableInterruptCharacter() {
      this.terminal.enableInterruptCharacter();
   }

   public String getOutputEncoding() {
      return this.terminal.getOutputEncoding();
   }

   private void registerCap(String cap, String value) {
      String[] var3 = InfoCmp.getNames(cap);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String key = var3[var5];
         this.strings.put(key, value);
      }

   }

   private void registerCap(String cap) {
      Collections.addAll(this.bools, InfoCmp.getNames(cap));
   }

   public boolean getBooleanCapability(String capability) {
      return this.bools.contains(capability);
   }

   public Integer getNumericCapability(String capability) {
      return null;
   }

   public String getStringCapability(String capability) {
      return (String)this.strings.get(capability);
   }
}
