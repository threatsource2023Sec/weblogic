package com.oracle.tyrus.fallback;

import javax.servlet.http.HttpServletRequest;

class FallbackUtil {
   static String getHeaderValue(HttpServletRequest req, String name) {
      String value = req.getHeader(name);
      if (value == null) {
         value = req.getParameter(name);
      }

      return value;
   }
}
