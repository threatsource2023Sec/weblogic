package org.glassfish.soteria.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.Utils;

public class CookieHandler {
   public static void saveCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, boolean secureOnly, boolean HttpOnly) {
      Cookie cookie = new Cookie(name, value);
      if (maxAge != null) {
         cookie.setMaxAge(maxAge);
      }

      cookie.setSecure(secureOnly);
      cookie.setHttpOnly(HttpOnly);
      cookie.setPath(Utils.isEmpty(request.getContextPath()) ? "/" : request.getContextPath());
      response.addCookie(cookie);
   }

   public static Cookie getCookie(HttpServletRequest request, String name) {
      if (request.getCookies() != null) {
         Cookie[] var2 = request.getCookies();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Cookie cookie = var2[var4];
            if (name.equals(cookie.getName()) && !isEmpty(cookie)) {
               return cookie;
            }
         }
      }

      return null;
   }

   public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
      Cookie cookie = new Cookie(name, (String)null);
      cookie.setMaxAge(0);
      cookie.setPath(Utils.isEmpty(request.getContextPath()) ? "/" : request.getContextPath());
      response.addCookie(cookie);
   }

   private static boolean isEmpty(Cookie cookie) {
      return cookie.getValue() == null || cookie.getValue().trim().isEmpty();
   }
}
