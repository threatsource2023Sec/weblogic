package org.glassfish.admin.rest.utils;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.RestTextTextFormatter;

public class MessageUtil {
   public static RestTextTextFormatter formatter(HttpServletRequest request) {
      return formatter(getLocale(request));
   }

   public static RestTextTextFormatter formatter(Locale locale) {
      return locale != null ? RestTextTextFormatter.getInstance(locale) : RestTextTextFormatter.getInstance();
   }

   protected static Locale getLocale(HttpServletRequest request) {
      return request != null ? request.getLocale() : null;
   }
}
