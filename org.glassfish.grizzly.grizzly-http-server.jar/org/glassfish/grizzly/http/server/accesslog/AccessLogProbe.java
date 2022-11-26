package org.glassfish.grizzly.http.server.accesslog;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.HttpServerProbe;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class AccessLogProbe extends HttpServerProbe.Adapter {
   public static final int DEFAULT_STATUS_THRESHOLD = Integer.MIN_VALUE;
   private static final String ATTRIBUTE_TIME_STAMP = AccessLogProbe.class.getName() + ".timeStamp";
   private static final Logger LOGGER = Grizzly.logger(HttpServer.class);
   private final AccessLogAppender appender;
   private final AccessLogFormat format;
   private final int statusThreshold;

   public AccessLogProbe(AccessLogAppender appender, AccessLogFormat format) {
      this(appender, format, Integer.MIN_VALUE);
   }

   public AccessLogProbe(AccessLogAppender appender, AccessLogFormat format, int statusThreshold) {
      if (appender == null) {
         throw new NullPointerException("Null access log appender");
      } else if (format == null) {
         throw new NullPointerException("Null format");
      } else {
         this.appender = appender;
         this.format = format;
         this.statusThreshold = statusThreshold;
      }
   }

   public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
      request.setAttribute(ATTRIBUTE_TIME_STAMP, System.nanoTime());
      connection.getLocalAddress();
      connection.getPeerAddress();
   }

   public void onRequestCompleteEvent(HttpServerFilter filter, Connection connection, Response response) {
      if (response.getStatus() >= this.statusThreshold) {
         Long requestNanos = (Long)response.getRequest().getAttribute(ATTRIBUTE_TIME_STAMP);
         long timeStamp = System.currentTimeMillis();
         long nanoStamp = System.nanoTime();
         long responseNanos = requestNanos == null ? -1L : nanoStamp - requestNanos;
         Date requestMillis = new Date(timeStamp - responseNanos / 1000000L);

         try {
            this.appender.append(this.format.format(response, requestMillis, responseNanos));
         } catch (Throwable var13) {
            LOGGER.log(Level.WARNING, "Exception caught appending to access log", var13);
         }

      }
   }
}
