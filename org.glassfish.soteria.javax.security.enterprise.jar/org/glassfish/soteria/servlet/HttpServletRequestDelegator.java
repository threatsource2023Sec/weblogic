package org.glassfish.soteria.servlet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.glassfish.soteria.Utils;

public class HttpServletRequestDelegator extends HttpServletRequestWrapper {
   private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
   private static final String[] datePatterns = new String[]{"EE, dd MMM yyyy HH:mm:ss zz", "EEEE, dd-MMM-yy HH:mm:ss zz", "EE MMM  d HH:mm:ss yyyy"};
   private final RequestData requestData;
   private List dateFormats;

   public HttpServletRequestDelegator(HttpServletRequest request, RequestData requestData) {
      super(request);
      this.requestData = requestData;
   }

   public Cookie[] getCookies() {
      return this.requestData.getCookies();
   }

   public Enumeration getHeaderNames() {
      return Collections.enumeration(this.requestData.getHeaders().keySet());
   }

   public String getHeader(String name) {
      Iterator var2 = this.requestData.getHeaders().entrySet().iterator();

      Map.Entry header;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         header = (Map.Entry)var2.next();
      } while(!((String)header.getKey()).equalsIgnoreCase(name) || ((List)header.getValue()).isEmpty());

      return (String)((List)header.getValue()).get(0);
   }

   public Enumeration getHeaders(String name) {
      List headers = new ArrayList();
      Iterator var3 = this.requestData.getHeaders().entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry header = (Map.Entry)var3.next();
         if (((String)header.getKey()).equalsIgnoreCase(name)) {
            headers.addAll((Collection)header.getValue());
         }
      }

      return Collections.enumeration(headers);
   }

   public int getIntHeader(String name) {
      String header = this.getHeader(name);
      return header == null ? -1 : Integer.parseInt(header);
   }

   public long getDateHeader(String name) {
      String header = this.getHeader(name);
      if (header == null) {
         return -1L;
      } else {
         if (this.dateFormats == null) {
            this.dateFormats = new ArrayList(datePatterns.length);
            String[] var3 = datePatterns;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String datePattern = var3[var5];
               this.dateFormats.add(this.createDateFormat(datePattern));
            }
         }

         Iterator var8 = this.dateFormats.iterator();

         while(var8.hasNext()) {
            DateFormat dateFormat = (DateFormat)var8.next();

            try {
               return dateFormat.parse(header).getTime();
            } catch (ParseException var7) {
            }
         }

         throw new IllegalArgumentException("Can't convert " + header + " to a date");
      }
   }

   public Map getParameterMap() {
      return this.requestData.getParameters();
   }

   public String getParameter(String name) {
      String[] values = (String[])this.requestData.getParameters().get(name);
      return Utils.isEmpty((Object[])values) ? null : values[0];
   }

   public Enumeration getParameterNames() {
      return Collections.enumeration(this.getParameterMap().keySet());
   }

   public String[] getParameterValues(String name) {
      return (String[])this.getParameterMap().get(name);
   }

   public Enumeration getLocales() {
      return Collections.enumeration(this.requestData.getLocales());
   }

   public Locale getLocale() {
      return this.requestData.getLocales().isEmpty() ? Locale.getDefault() : (Locale)this.requestData.getLocales().get(0);
   }

   public String getMethod() {
      return this.requestData.getMethod();
   }

   private DateFormat createDateFormat(String pattern) {
      DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
      dateFormat.setTimeZone(GMT);
      return dateFormat;
   }
}
