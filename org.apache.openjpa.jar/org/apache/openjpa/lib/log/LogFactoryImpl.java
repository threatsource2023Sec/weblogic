package org.apache.openjpa.lib.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.GenericConfigurable;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;

public class LogFactoryImpl implements LogFactory, GenericConfigurable, Configurable {
   private static Localizer _loc = Localizer.forPackage(LogFactoryImpl.class);
   public static final String TRACE_STR;
   public static final String INFO_STR;
   public static final String WARN_STR;
   public static final String ERROR_STR;
   public static final String FATAL_STR;
   public static final String STDOUT = "stdout";
   public static final String STDERR = "stderr";
   private static final String NEWLINE;
   protected final long initializationMillis;
   private Map _logs = new ConcurrentHashMap();
   private short _defaultLogLevel = 3;
   private Map _configuredLevels = new HashMap();
   private PrintStream _out;
   private String _diagContext;
   private boolean _diagContextComputed;
   private Configuration _conf;

   public LogFactoryImpl() {
      this._out = System.err;
      this._diagContext = null;
      this._diagContextComputed = false;
      this.initializationMillis = System.currentTimeMillis();
   }

   public Log getLog(String channel) {
      LogImpl l = (LogImpl)this._logs.get(channel);
      if (l == null) {
         l = this.newLogImpl();
         l.setChannel(channel);
         Short lvl = (Short)this._configuredLevels.get(shorten(channel));
         l.setLevel(lvl == null ? this._defaultLogLevel : lvl);
         this._logs.put(channel, l);
      }

      return l;
   }

   protected LogImpl newLogImpl() {
      return new LogImpl();
   }

   public void setDefaultLevel(String level) {
      this._defaultLogLevel = getLevel(level);
   }

   public short getDefaultLevel() {
      return this._defaultLogLevel;
   }

   public void setDefaultLevel(short level) {
      this._defaultLogLevel = level;
   }

   public void setDiagnosticContext(String val) {
      this._diagContext = val;
   }

   public String getDiagnosticContext() {
      if (!this._diagContextComputed) {
         if (this._diagContext == null && this._conf != null) {
            this._diagContext = this._conf.getId();
         }

         if ("".equals(this._diagContext)) {
            this._diagContext = null;
         }

         this._diagContextComputed = true;
      }

      return this._diagContext;
   }

   public void setFile(String file) {
      if ("stdout".equals(file)) {
         this._out = System.out;
      } else if ("stderr".equals(file)) {
         this._out = System.err;
      } else {
         File f = Files.getFile(file, (ClassLoader)null);

         try {
            this._out = new PrintStream((FileOutputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileOutputStreamAction((String)AccessController.doPrivileged(J2DoPrivHelper.getCanonicalPathAction(f)), true)));
         } catch (PrivilegedActionException var4) {
            throw new IllegalArgumentException(_loc.get("log-bad-file", (Object)file) + " " + var4.getException());
         } catch (IOException var5) {
            throw new IllegalArgumentException(_loc.get("log-bad-file", (Object)file) + " " + var5.toString());
         }
      }

   }

   public PrintStream getStream() {
      return this._out;
   }

   public void setStream(PrintStream stream) {
      if (stream == null) {
         throw new NullPointerException("stream == null");
      } else {
         this._out = stream;
      }
   }

   public static String getLevelName(short level) {
      switch (level) {
         case 1:
            return TRACE_STR;
         case 2:
         default:
            return _loc.get("log-unknown").getMessage();
         case 3:
            return INFO_STR;
         case 4:
            return WARN_STR;
         case 5:
            return ERROR_STR;
         case 6:
            return FATAL_STR;
      }
   }

   public static short getLevel(String str) {
      str = str.toUpperCase().trim();
      short val = TRACE_STR.equals(str) ? 1 : (INFO_STR.equals(str) ? 3 : (WARN_STR.equals(str) ? 4 : (ERROR_STR.equals(str) ? 5 : (FATAL_STR.equals(str) ? 6 : -1))));
      if (val == -1) {
         throw new IllegalArgumentException(_loc.get("log-bad-constant", (Object)str).getMessage());
      } else {
         return (short)val;
      }
   }

   public void setConfiguration(Configuration conf) {
      this._conf = conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void setInto(Options opts) {
      if (!opts.isEmpty()) {
         Iterator iter = opts.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry e = (Map.Entry)iter.next();
            this._configuredLevels.put(shorten((String)e.getKey()), new Short(getLevel((String)e.getValue())));
         }

         opts.clear();
      }

   }

   private static String shorten(String channel) {
      return channel.substring(channel.lastIndexOf(46) + 1);
   }

   static {
      TRACE_STR = _loc.get("log-trace").getMessage();
      INFO_STR = _loc.get("log-info").getMessage();
      WARN_STR = _loc.get("log-warn").getMessage();
      ERROR_STR = _loc.get("log-error").getMessage();
      FATAL_STR = _loc.get("log-fatal").getMessage();
      NEWLINE = J2DoPrivHelper.getLineSeparator();
   }

   public class LogImpl extends AbstractLog {
      private short _level = 3;
      private String _channel;

      protected boolean isEnabled(short level) {
         return level >= this._level;
      }

      protected void log(short level, String message, Throwable t) {
         String msg = this.formatMessage(level, message, t);
         synchronized(LogFactoryImpl.this._out) {
            LogFactoryImpl.this._out.print(msg);
         }
      }

      protected String formatMessage(short level, String message, Throwable t) {
         StringBuffer buf = new StringBuffer();
         buf.append(this.getOffset());
         buf.append("  ");
         if (LogFactoryImpl.this.getDiagnosticContext() != null) {
            buf.append(LogFactoryImpl.this.getDiagnosticContext()).append("  ");
         }

         buf.append(LogFactoryImpl.getLevelName(level));
         if (level == 3 || level == 4) {
            buf.append(" ");
         }

         buf.append("  [");
         buf.append(Thread.currentThread().getName());
         buf.append("] ");
         buf.append(this._channel);
         buf.append(" - ");
         buf.append(message);
         buf.append(LogFactoryImpl.NEWLINE);
         if (t != null) {
            StringWriter swriter = new StringWriter();
            PrintWriter pwriter = new PrintWriter(swriter);
            t.printStackTrace(pwriter);
            pwriter.flush();
            buf.append(swriter.toString());
         }

         return buf.toString();
      }

      private long getOffset() {
         return System.currentTimeMillis() - LogFactoryImpl.this.initializationMillis;
      }

      public void setChannel(String val) {
         this._channel = val;
      }

      public String getChannel() {
         return this._channel;
      }

      public void setLevel(short val) {
         this._level = val;
      }

      public short getLevel() {
         return this._level;
      }
   }
}
