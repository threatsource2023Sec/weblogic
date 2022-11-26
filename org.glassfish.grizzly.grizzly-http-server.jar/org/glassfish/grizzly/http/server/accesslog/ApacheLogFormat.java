package org.glassfish.grizzly.http.server.accesslog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.MimeHeaders;

public class ApacheLogFormat implements AccessLogFormat {
   private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
   public static final String COMMON_FORMAT = "%h - %u %t \"%r\" %s %b";
   public static final String COMBINED_FORMAT = "%h - %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-agent}i\"";
   public static final String VHOST_COMMON_FORMAT = "%v %h - %u %t \"%r\" %s %b";
   public static final String VHOST_COMBINED_FORMAT = "%v %h - %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-agent}i\"";
   public static final String REFERER_FORMAT = "%{Referer}i -> %U";
   public static final String AGENT_FORMAT = "%{User-agent}i";
   public static final ApacheLogFormat COMMON = new ApacheLogFormat("%h - %u %t \"%r\" %s %b");
   public static final ApacheLogFormat COMBINED = new ApacheLogFormat("%h - %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-agent}i\"");
   public static final ApacheLogFormat VHOST_COMMON = new ApacheLogFormat("%v %h - %u %t \"%r\" %s %b");
   public static final ApacheLogFormat VHOST_COMBINED = new ApacheLogFormat("%v %h - %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-agent}i\"");
   public static final ApacheLogFormat REFERER = new ApacheLogFormat("%{Referer}i -> %U");
   public static final ApacheLogFormat AGENT = new ApacheLogFormat("%{User-agent}i");
   public static final ApacheLogFormat COMMON_UTC;
   public static final ApacheLogFormat COMBINED_UTC;
   public static final ApacheLogFormat VHOST_COMMON_UTC;
   public static final ApacheLogFormat VHOST_COMBINED_UTC;
   public static final ApacheLogFormat REFERER_UTC;
   public static final ApacheLogFormat AGENT_UTC;
   private static final Logger LOGGER;
   private final List fields;
   private final TimeZone timeZone;

   public ApacheLogFormat(String format) {
      this(TimeZone.getDefault(), format);
   }

   public ApacheLogFormat(TimeZone timeZone, String format) {
      if (timeZone == null) {
         throw new NullPointerException("Null time zone");
      } else {
         this.fields = new ArrayList();
         this.timeZone = timeZone;
         this.parse(format);
      }
   }

   public String format(Response response, Date timeStamp, long responseNanos) {
      StringBuilder builder = new StringBuilder();
      Request request = response.getRequest();
      Iterator var7 = this.fields.iterator();

      while(var7.hasNext()) {
         Field field = (Field)var7.next();

         try {
            field.format(builder, request, response, timeStamp, responseNanos);
         } catch (Exception var10) {
            LOGGER.log(Level.WARNING, "Exception formatting access log entry", var10);
            builder.append('-');
         }
      }

      return builder.toString();
   }

   String unsafeFormat(Response response, Date timeStamp, long responseNanos) {
      StringBuilder builder = new StringBuilder();
      Request request = response.getRequest();
      Iterator var7 = this.fields.iterator();

      while(var7.hasNext()) {
         Field field = (Field)var7.next();
         field.format(builder, request, response, timeStamp, responseNanos);
      }

      return builder.toString();
   }

   public String getFormat() {
      StringBuilder builder = new StringBuilder();
      Iterator var2 = this.fields.iterator();

      while(var2.hasNext()) {
         Field field = (Field)var2.next();
         builder.append(field.toString());
      }

      return builder.toString();
   }

   private void parse(String format) {
      for(int x = 0; x < format.length(); ++x) {
         switch (format.charAt(x)) {
            case '%':
               x = this.parseFormat(format, (String)null, x);
               break;
            case '\\':
               x = this.parseEscape(format, x);
               break;
            default:
               this.addLiteral(format.charAt(x));
         }
      }

   }

   private int parseFormat(String format, String parameter, int position) {
      ++position;
      if (position < format.length()) {
         char field = format.charAt(position);
         if (parameter != null) {
            switch (field) {
               case 'C':
               case 'T':
               case 'h':
               case 'i':
               case 'o':
               case 'p':
               case 't':
                  break;
               default:
                  throw new IllegalArgumentException("Unsupported parameter \"" + parameter + "\" for field '" + field + "' in [" + format + "] at character " + position);
            }
         }

         switch (field) {
            case '%':
               this.addLiteral('%');
               break;
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'E':
            case 'F':
            case 'G':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'j':
            case 'k':
            case 'l':
            case 'n':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            default:
               throw new IllegalArgumentException("Unsupported field '" + field + "' in [" + format + "] at character " + position);
            case 'A':
               this.fields.add(new LocalAddressField());
               break;
            case 'B':
               this.fields.add(new ResponseSizeField(true));
               break;
            case 'C':
               this.fields.add(new RequestCookieField(parameter));
               break;
            case 'D':
               this.fields.add(new ResponseTimeField("micro", format, position));
               break;
            case 'H':
               this.fields.add(new RequestProtocolField());
               break;
            case 'T':
               this.fields.add(new ResponseTimeField(parameter, format, position));
               break;
            case 'U':
               this.fields.add(new RequestURIField());
               break;
            case 'a':
               this.fields.add(new RemoteAddressField());
               break;
            case 'b':
               this.fields.add(new ResponseSizeField(false));
               break;
            case 'h':
               this.fields.add(this.parseLocal(parameter, false, field, format, position) ? new LocalHostField() : new RemoteHostField());
               break;
            case 'i':
               this.fields.add(new RequestHeaderField(parameter));
               break;
            case 'm':
               this.fields.add(new RequestMethodField());
               break;
            case 'o':
               this.fields.add(new ResponseHeaderField(parameter));
               break;
            case 'p':
               this.fields.add(this.parseLocal(parameter, true, field, format, position) ? new LocalPortField() : new RemotePortField());
               break;
            case 'q':
               this.fields.add(new RequestQueryField());
               break;
            case 'r':
               this.fields.add(new RequestMethodField());
               this.addLiteral(' ');
               this.fields.add(new RequestURIField());
               this.fields.add(new RequestQueryField());
               this.addLiteral(' ');
               this.fields.add(new RequestProtocolField());
               break;
            case 's':
               this.fields.add(new ResponseStatusField());
               break;
            case 't':
               this.fields.add(new RequestTimeField(parameter, this.timeZone));
               break;
            case 'u':
               this.fields.add(new RequestUserField());
               break;
            case 'v':
               this.fields.add(new ServerNameField());
               break;
            case '{':
               return this.parseParameter(format, position);
         }

         return position;
      } else {
         throw new IllegalArgumentException("Unterminated field declaration in [" + format + "] at character " + position);
      }
   }

   private boolean parseLocal(String parameter, boolean defaultValue, char field, String format, int position) {
      if (parameter == null) {
         return defaultValue;
      } else {
         String p = parameter.trim().toLowerCase();
         if (p.equals("local")) {
            return true;
         } else if (p.equals("remote")) {
            return false;
         } else {
            throw new IllegalArgumentException("Unsupported parameter \"" + parameter + "\" for field '" + field + "' in [" + format + "] at character " + position);
         }
      }
   }

   private int parseParameter(String format, int position) {
      ++position;
      if (position < format.length()) {
         int end = format.indexOf(125, position);
         if (end == position) {
            return this.parseFormat(format, (String)null, end);
         }

         if (end > position) {
            return this.parseFormat(format, format.substring(position, end), end);
         }
      }

      throw new IllegalArgumentException("Unterminated format parameter in [" + format + "] at character " + position);
   }

   private int parseEscape(String format, int position) {
      ++position;
      if (position < format.length()) {
         char escaped = format.charAt(position);
         switch (escaped) {
            case 'b':
               this.addLiteral('\b');
               break;
            case 'f':
               this.addLiteral('\f');
               break;
            case 'n':
               this.addLiteral('\n');
               break;
            case 'r':
               this.addLiteral('\r');
               break;
            case 't':
               this.addLiteral('\t');
               break;
            default:
               this.addLiteral(escaped);
         }

         return position;
      } else {
         throw new IllegalArgumentException("Unterminated escape sequence in [" + format + "] at character " + position);
      }
   }

   private void addLiteral(char c) {
      if (!this.fields.isEmpty()) {
         Field last = (Field)this.fields.get(this.fields.size() - 1);
         if (last instanceof LiteralField) {
            ((LiteralField)last).append(c);
            return;
         }
      }

      this.fields.add(new LiteralField(c));
   }

   static {
      COMMON_UTC = new ApacheLogFormat(UTC, "%h - %u %t \"%r\" %s %b");
      COMBINED_UTC = new ApacheLogFormat(UTC, "%h - %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-agent}i\"");
      VHOST_COMMON_UTC = new ApacheLogFormat(UTC, "%v %h - %u %t \"%r\" %s %b");
      VHOST_COMBINED_UTC = new ApacheLogFormat(UTC, "%v %h - %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-agent}i\"");
      REFERER_UTC = new ApacheLogFormat(UTC, "%{Referer}i -> %U");
      AGENT_UTC = new ApacheLogFormat(UTC, "%{User-agent}i");
      LOGGER = Grizzly.logger(HttpServer.class);
   }

   private static class ResponseHeaderField extends HeaderField {
      ResponseHeaderField(String name) {
         super('o', name);
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         return this.format(builder, response.getResponse().getHeaders());
      }
   }

   private static class ResponseTimeField extends Field {
      private final long scale;

      ResponseTimeField(String unit, String format, int position) {
         super(null);
         if (unit == null) {
            this.scale = 1000000000L;
         } else {
            String s = unit.trim().toLowerCase();
            if (!s.equals("n") && !s.equals("nano") && !s.equals("nanos") && !s.equals("nanosec") && !s.equals("nanosecs") && !s.equals("nanosecond") && !s.equals("nanoseconds")) {
               if (!s.equals("micro") && !s.equals("micros") && !s.equals("microsec") && !s.equals("microsecs") && !s.equals("microsecond") && !s.equals("microseconds")) {
                  if (!s.equals("m") && !s.equals("milli") && !s.equals("millis") && !s.equals("millisec") && !s.equals("millisecs") && !s.equals("millisecond") && !s.equals("milliseconds")) {
                     if (!s.equals("s") && !s.equals("sec") && !s.equals("secs") && !s.equals("second") && !s.equals("seconds")) {
                        throw new IllegalArgumentException("Unsupported time unit \"" + unit + "\" for field 'T' in [" + format + "] at character " + position);
                     }

                     this.scale = 1000000000L;
                  } else {
                     this.scale = 1000000L;
                  }
               } else {
                  this.scale = 1000L;
               }
            } else {
               this.scale = 1L;
            }

         }
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         return responseNanos < 0L ? builder.append('-') : builder.append(responseNanos / this.scale);
      }

      public String toString() {
         StringBuilder string = (new StringBuilder()).append('%');
         if (this.scale == 1L) {
            string.append("{n}T");
         } else if (this.scale == 1000L) {
            string.append('D');
         } else if (this.scale == 1000000L) {
            string.append("{m}T");
         } else if (this.scale == 1000000000L) {
            string.append('T');
         } else {
            string.append('{').append(this.scale).append("}T");
         }

         return string.toString();
      }
   }

   private static class ResponseSizeField extends AbstractField {
      final String zero;

      ResponseSizeField(boolean zero) {
         super((char)(zero ? 'B' : 'b'));
         this.zero = zero ? "0" : "-";
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         long size = response.getContentLengthLong();
         return builder.append(size < 1L ? this.zero : Long.toString(size));
      }
   }

   private static class ResponseStatusField extends AbstractField {
      ResponseStatusField() {
         super('s');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         int status = response.getStatus();
         if (status < 10) {
            builder.append('0');
         }

         if (status < 100) {
            builder.append('0');
         }

         return builder.append(status);
      }
   }

   private static class RequestCookieField extends AbstractField {
      final String name;

      RequestCookieField(String name) {
         super('C', name.trim().toLowerCase());
         this.name = name.trim().toLowerCase();
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         Cookie[] cookies = request.getCookies();
         if (cookies != null) {
            Cookie[] var8 = cookies;
            int var9 = cookies.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Cookie cookie = var8[var10];
               if (this.name.equals(cookie.getName().toLowerCase())) {
                  return builder.append(cookie.getValue());
               }
            }
         }

         return builder;
      }
   }

   private static class RequestHeaderField extends HeaderField {
      RequestHeaderField(String name) {
         super('i', name);
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         return this.format(builder, request.getRequest().getHeaders());
      }
   }

   private static class RequestProtocolField extends AbstractField {
      RequestProtocolField() {
         super('H');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         Protocol protocol = request.getProtocol();
         if (protocol == null) {
            return builder.append("-");
         } else {
            switch (protocol) {
               case HTTP_0_9:
                  return builder.append("HTTP/0.9");
               case HTTP_1_0:
                  return builder.append("HTTP/1.0");
               case HTTP_1_1:
                  return builder.append("HTTP/1.1");
               default:
                  return builder.append("-");
            }
         }
      }
   }

   private static class RequestQueryField extends AbstractField {
      RequestQueryField() {
         super('q');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         String query = request.getQueryString();
         if (query != null) {
            builder.append('?').append(query);
         }

         return builder;
      }
   }

   private static class RequestURIField extends AbstractField {
      RequestURIField() {
         super('U');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         String uri = request.getRequestURI();
         return builder.append(uri == null ? "-" : uri);
      }
   }

   private static class RequestUserField extends AbstractField {
      RequestUserField() {
         super('u');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         String user = request.getRemoteUser();
         return builder.append(user == null ? "-" : user);
      }
   }

   private static class RequestMethodField extends AbstractField {
      RequestMethodField() {
         super('m');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         Method method = request.getMethod();
         return builder.append(method == null ? "-" : method.toString());
      }
   }

   private static class RequestTimeField extends Field {
      private static final String DEFAULT_PATTERN = "[yyyy/MMM/dd:HH:mm:ss Z]";
      private final SimpleDateFormatThreadLocal simpleDateFormat;
      private final TimeZone timeZone;
      private final String pattern;
      private final String format;

      RequestTimeField(String format, TimeZone zone) {
         super(null);
         this.format = format;
         if (format == null) {
            this.pattern = "[yyyy/MMM/dd:HH:mm:ss Z]";
            this.timeZone = zone;
         } else {
            int pos = format.lastIndexOf(64);
            if (pos >= 0 && (pos <= 0 || format.charAt(pos - 1) != '@')) {
               if (pos == 0) {
                  this.pattern = "[yyyy/MMM/dd:HH:mm:ss Z]";
                  this.timeZone = TimeZone.getTimeZone(format.substring(1));
               } else {
                  this.pattern = format.substring(0, pos).replace("@@", "@");
                  this.timeZone = TimeZone.getTimeZone(format.substring(pos + 1));
               }
            } else {
               this.pattern = format.replace("@@", "@");
               this.timeZone = zone;
            }
         }

         this.simpleDateFormat = new SimpleDateFormatThreadLocal(this.pattern);
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         if (timeStamp == null) {
            return builder.append('-');
         } else {
            SimpleDateFormat format = (SimpleDateFormat)this.simpleDateFormat.get();
            format.setTimeZone(this.timeZone);
            return builder.append(format.format(timeStamp));
         }
      }

      public String toString() {
         return this.format == null ? "%t" : "%{" + this.format + "}t";
      }
   }

   private static class RemotePortField extends AbstractField {
      RemotePortField() {
         super('p', "remote");
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         int port = request.getRemotePort();
         return builder.append(port < 1 ? "-" : port);
      }
   }

   private static class RemoteAddressField extends AbstractField {
      RemoteAddressField() {
         super('a');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         String address = request.getRemoteAddr();
         return builder.append(address == null ? "-" : address);
      }
   }

   private static class RemoteHostField extends AbstractField {
      RemoteHostField() {
         super('h');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         String host = request.getRemoteHost();
         return builder.append(host == null ? "-" : host);
      }
   }

   private static class LocalPortField extends AbstractField {
      LocalPortField() {
         super('p');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         int port = request.getLocalPort();
         return builder.append(port < 1 ? "-" : port);
      }
   }

   private static class LocalAddressField extends AbstractField {
      LocalAddressField() {
         super('A');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         String address = request.getLocalAddr();
         return builder.append(address == null ? "-" : address);
      }
   }

   private static class LocalHostField extends AbstractField {
      LocalHostField() {
         super('h', "local");
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         String host = request.getLocalName();
         return builder.append(host == null ? "-" : host);
      }
   }

   private static class ServerNameField extends AbstractField {
      ServerNameField() {
         super('v');
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         String name = request.getServerName();
         return builder.append(name == null ? "-" : name);
      }
   }

   private static class LiteralField extends Field {
      final StringBuilder contents;

      LiteralField(char character) {
         super(null);
         this.contents = (new StringBuilder()).append(character);
      }

      void append(char character) {
         this.contents.append(character);
      }

      StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
         return builder.append(this.contents);
      }

      public String toString() {
         StringBuilder builder = new StringBuilder();

         for(int x = 0; x < this.contents.length(); ++x) {
            char character = this.contents.charAt(x);
            switch (character) {
               case '%':
                  builder.append('%');
                  break;
               case 'b':
               case 'f':
               case 'n':
               case 'r':
               case 't':
                  builder.append('\\');
            }

            builder.append(character);
         }

         return builder.toString();
      }
   }

   private abstract static class HeaderField extends AbstractField {
      final String name;

      HeaderField(char format, String name) {
         super(format, name.trim().toLowerCase());
         this.name = name.trim().toLowerCase();
      }

      StringBuilder format(StringBuilder builder, MimeHeaders headers) {
         Iterator iterator = headers.values(this.name).iterator();
         if (iterator.hasNext()) {
            builder.append((String)iterator.next());
         }

         while(iterator.hasNext()) {
            builder.append("; ").append((String)iterator.next());
         }

         return builder;
      }
   }

   private abstract static class AbstractField extends Field {
      private final char format;
      private final String parameter;

      protected AbstractField(char format) {
         this(format, (String)null);
      }

      protected AbstractField(char format, String parameter) {
         super(null);
         this.format = format;
         this.parameter = parameter;
      }

      public final String toString() {
         StringBuilder builder = (new StringBuilder()).append('%');
         if (this.parameter != null) {
            builder.append('{').append(this.parameter).append('}');
         }

         return builder.append(this.format).toString();
      }
   }

   private abstract static class Field {
      private Field() {
      }

      abstract StringBuilder format(StringBuilder var1, Request var2, Response var3, Date var4, long var5);

      public abstract String toString();

      // $FF: synthetic method
      Field(Object x0) {
         this();
      }
   }
}
