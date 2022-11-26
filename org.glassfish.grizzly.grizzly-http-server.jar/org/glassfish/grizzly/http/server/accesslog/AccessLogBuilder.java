package org.glassfish.grizzly.http.server.accesslog;

import java.io.File;
import java.io.IOException;
import java.util.TimeZone;
import org.glassfish.grizzly.http.server.HttpServerProbe;
import org.glassfish.grizzly.http.server.ServerConfiguration;

public class AccessLogBuilder {
   private AccessLogFormat format;
   private int statusThreshold;
   private String rotationPattern;
   private boolean synchronous;
   private final File file;

   public AccessLogBuilder(String file) {
      this.format = ApacheLogFormat.COMBINED;
      this.statusThreshold = Integer.MIN_VALUE;
      if (file == null) {
         throw new NullPointerException("Null file");
      } else {
         this.file = (new File(file)).getAbsoluteFile();
      }
   }

   public AccessLogBuilder(File file) {
      this.format = ApacheLogFormat.COMBINED;
      this.statusThreshold = Integer.MIN_VALUE;
      if (file == null) {
         throw new NullPointerException("Null file");
      } else {
         this.file = file;
      }
   }

   public AccessLogProbe build() {
      Object appender;
      try {
         if (this.rotationPattern == null) {
            appender = new FileAppender(this.file.getCanonicalFile());
         } else {
            File directory = this.file.getCanonicalFile().getParentFile();
            String name = this.file.getName();
            int position = name.lastIndexOf(".");
            String base;
            String extension;
            if (position < 0) {
               base = name.replace("'", "''");
               extension = "";
            } else {
               base = name.substring(0, position).replace("'", "''");
               extension = name.substring(position).replace("'", "''");
            }

            String archive = '\'' + base + "'-" + this.rotationPattern + '\'' + extension + '\'';
            appender = new RotatingFileAppender(directory, name, archive);
         }
      } catch (IOException var8) {
         throw new IllegalStateException("I/O error creating acces log", var8);
      }

      if (!this.synchronous) {
         appender = new QueueingAppender((AccessLogAppender)appender);
      }

      return new AccessLogProbe((AccessLogAppender)appender, this.format, this.statusThreshold);
   }

   public ServerConfiguration instrument(ServerConfiguration serverConfiguration) {
      serverConfiguration.getMonitoringConfig().getWebServerConfig().addProbes(new HttpServerProbe[]{this.build()});
      return serverConfiguration;
   }

   public AccessLogBuilder format(AccessLogFormat format) {
      if (format == null) {
         throw new NullPointerException("Null format");
      } else {
         this.format = format;
         return this;
      }
   }

   public AccessLogBuilder format(String format) {
      if (format == null) {
         throw new NullPointerException("Null format");
      } else {
         return this.format((AccessLogFormat)(new ApacheLogFormat(format)));
      }
   }

   public AccessLogBuilder timeZone(TimeZone timeZone) {
      if (timeZone == null) {
         throw new NullPointerException("Null time zone");
      } else if (this.format instanceof ApacheLogFormat) {
         ApacheLogFormat apacheFormat = (ApacheLogFormat)this.format;
         this.format = new ApacheLogFormat(timeZone, apacheFormat.getFormat());
         return this;
      } else {
         throw new IllegalStateException("TimeZone can not be set for " + this.format.getClass().getName());
      }
   }

   public AccessLogBuilder timeZone(String timeZone) {
      if (timeZone == null) {
         throw new NullPointerException("Null time zone");
      } else {
         return this.timeZone(TimeZone.getTimeZone(timeZone));
      }
   }

   public AccessLogBuilder statusThreshold(int statusThreshold) {
      this.statusThreshold = statusThreshold;
      return this;
   }

   public AccessLogBuilder rotatedHourly() {
      return this.rotationPattern("yyyyMMDDhh");
   }

   public AccessLogBuilder rotatedDaily() {
      return this.rotationPattern("yyyyMMDD");
   }

   public AccessLogBuilder rotationPattern(String rotationPattern) {
      if (rotationPattern == null) {
         throw new NullPointerException("Null rotation pattern");
      } else {
         this.rotationPattern = rotationPattern;
         return this;
      }
   }

   public AccessLogBuilder synchronous(boolean synchronous) {
      this.synchronous = synchronous;
      return this;
   }
}
