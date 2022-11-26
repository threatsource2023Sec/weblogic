package org.python.jline.internal;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TerminalLineSettings {
   public static final String JLINE_STTY = "org.python.jline.stty";
   public static final String DEFAULT_STTY = "stty";
   public static final String JLINE_SH = "org.python.jline.sh";
   public static final String DEFAULT_SH = "sh";
   private static final String UNDEFINED;
   public static final String DEFAULT_TTY = "/dev/tty";
   private static final boolean SUPPORTS_REDIRECT;
   private static final Object REDIRECT_INHERIT;
   private static final Method REDIRECT_INPUT_METHOD;
   private static final Map SETTINGS = new HashMap();
   private String sttyCommand;
   private String shCommand;
   private String ttyDevice;
   private String config;
   private String initialConfig;
   private long configLastFetched;
   private boolean useRedirect;

   /** @deprecated */
   @Deprecated
   public TerminalLineSettings() throws IOException, InterruptedException {
      this("/dev/tty");
   }

   /** @deprecated */
   @Deprecated
   public TerminalLineSettings(String ttyDevice) throws IOException, InterruptedException {
      this(ttyDevice, false);
   }

   private TerminalLineSettings(String ttyDevice, boolean unused) throws IOException, InterruptedException {
      Preconditions.checkNotNull(ttyDevice);
      this.sttyCommand = Configuration.getString("org.python.jline.stty", "stty");
      this.shCommand = Configuration.getString("org.python.jline.sh", "sh");
      this.ttyDevice = ttyDevice;
      this.useRedirect = SUPPORTS_REDIRECT && "/dev/tty".equals(ttyDevice);
      this.initialConfig = this.get("-g").trim();
      this.config = this.get("-a");
      this.configLastFetched = System.currentTimeMillis();
      Log.debug("Config: ", this.config);
      if (this.config.length() == 0) {
         throw new IOException(MessageFormat.format("Unrecognized stty code: {0}", this.config));
      }
   }

   public static synchronized TerminalLineSettings getSettings(String device) throws IOException, InterruptedException {
      TerminalLineSettings settings = (TerminalLineSettings)SETTINGS.get(device);
      if (settings == null) {
         settings = new TerminalLineSettings(device, false);
         SETTINGS.put(device, settings);
      }

      return settings;
   }

   public String getTtyDevice() {
      return this.ttyDevice;
   }

   public String getConfig() {
      return this.config;
   }

   public void restore() throws IOException, InterruptedException {
      this.set(this.initialConfig);
   }

   public String get(String args) throws IOException, InterruptedException {
      Preconditions.checkNotNull(args);
      return this.stty(args);
   }

   public void set(String args) throws IOException, InterruptedException {
      Preconditions.checkNotNull(args);
      this.stty(args.split(" "));
   }

   public void set(String... args) throws IOException, InterruptedException {
      Preconditions.checkNotNull(args);
      this.stty(args);
   }

   public void undef(String name) throws IOException, InterruptedException {
      Preconditions.checkNotNull(name);
      this.stty(name, UNDEFINED);
   }

   public int getProperty(String name) {
      Preconditions.checkNotNull(name);
      return !this.fetchConfig(name) ? -1 : getProperty(name, this.config);
   }

   public String getPropertyAsString(String name) {
      Preconditions.checkNotNull(name);
      return !this.fetchConfig(name) ? null : getPropertyAsString(name, this.config);
   }

   private boolean fetchConfig(String name) {
      long currentTime = System.currentTimeMillis();

      try {
         if (this.config == null || currentTime - this.configLastFetched > 1000L) {
            this.config = this.get("-a");
         }
      } catch (Exception var5) {
         if (var5 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
         }

         Log.debug("Failed to query stty ", name, "\n", var5);
         if (this.config == null) {
            return false;
         }
      }

      if (currentTime - this.configLastFetched > 1000L) {
         this.configLastFetched = currentTime;
      }

      return true;
   }

   protected static String getPropertyAsString(String name, String stty) {
      Pattern pattern = Pattern.compile(name + "\\s+=\\s+(.*?)[;\\n\\r]");
      Matcher matcher = pattern.matcher(stty);
      if (!matcher.find()) {
         pattern = Pattern.compile(name + "\\s+([^;]*)[;\\n\\r]");
         matcher = pattern.matcher(stty);
         if (!matcher.find()) {
            pattern = Pattern.compile("(\\S*)\\s+" + name);
            matcher = pattern.matcher(stty);
            if (!matcher.find()) {
               return null;
            }
         }
      }

      return matcher.group(1);
   }

   protected static int getProperty(String name, String stty) {
      String str = getPropertyAsString(name, stty);
      return str != null ? parseControlChar(str) : -1;
   }

   private static int parseControlChar(String str) {
      if ("<undef>".equals(str)) {
         return -1;
      } else if (str.charAt(0) == '0') {
         return Integer.parseInt(str, 8);
      } else if (str.charAt(0) >= '1' && str.charAt(0) <= '9') {
         return Integer.parseInt(str, 10);
      } else if (str.charAt(0) == '^') {
         return str.charAt(1) == '?' ? 127 : str.charAt(1) - 64;
      } else if (str.charAt(0) == 'M' && str.charAt(1) == '-') {
         if (str.charAt(2) == '^') {
            return str.charAt(3) == '?' ? 255 : str.charAt(3) - 64 + 128;
         } else {
            return str.charAt(2) + 128;
         }
      } else {
         return str.charAt(0);
      }
   }

   private String stty(String... args) throws IOException, InterruptedException {
      String[] s = new String[args.length + 1];
      s[0] = this.sttyCommand;
      System.arraycopy(args, 0, s, 1, args.length);
      return this.exec(s);
   }

   private String exec(String... cmd) throws IOException, InterruptedException {
      Preconditions.checkNotNull(cmd);
      Log.trace("Running: ", cmd);
      Process p = null;
      if (this.useRedirect) {
         try {
            p = inheritInput(new ProcessBuilder(cmd)).start();
         } catch (Throwable var5) {
            this.useRedirect = false;
         }
      }

      if (p == null) {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < cmd.length; ++i) {
            if (i > 0) {
               sb.append(' ');
            }

            sb.append(cmd[i]);
         }

         sb.append(" < ");
         sb.append(this.ttyDevice);
         p = (new ProcessBuilder(new String[]{this.shCommand, "-c", sb.toString()})).start();
      }

      String result = waitAndCapture(p);
      Log.trace("Result: ", result);
      return result;
   }

   private static ProcessBuilder inheritInput(ProcessBuilder pb) throws Exception {
      REDIRECT_INPUT_METHOD.invoke(pb, REDIRECT_INHERIT);
      return pb;
   }

   public static String waitAndCapture(Process p) throws IOException, InterruptedException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      InputStream in = null;
      InputStream err = null;
      OutputStream out = null;

      try {
         in = p.getInputStream();

         int c;
         while((c = in.read()) != -1) {
            bout.write(c);
         }

         err = p.getErrorStream();

         while((c = err.read()) != -1) {
            bout.write(c);
         }

         out = p.getOutputStream();
         p.waitFor();
         return bout.toString();
      } finally {
         close(in, out, err);
      }
   }

   private static void close(Closeable... closeables) {
      Closeable[] var1 = closeables;
      int var2 = closeables.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Closeable c = var1[var3];
         if (c != null) {
            try {
               c.close();
            } catch (Exception var6) {
            }
         }
      }

   }

   static {
      if (Configuration.isHpux()) {
         UNDEFINED = "^-";
      } else {
         UNDEFINED = "undef";
      }

      Object redirectInherit = null;
      Method redirectInputMethod = null;

      boolean supportsRedirect;
      try {
         Class redirect = Class.forName("java.lang.ProcessBuilder$Redirect");
         redirectInherit = redirect.getField("INHERIT").get((Object)null);
         redirectInputMethod = ProcessBuilder.class.getMethod("redirectInput", redirect);
         supportsRedirect = System.class.getMethod("console").invoke((Object)null) != null;
      } catch (Throwable var4) {
         supportsRedirect = false;
      }

      SUPPORTS_REDIRECT = supportsRedirect;
      REDIRECT_INHERIT = redirectInherit;
      REDIRECT_INPUT_METHOD = redirectInputMethod;
   }
}
