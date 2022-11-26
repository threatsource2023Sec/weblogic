package org.glassfish.soteria.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.soteria.Utils;

public final class RequestCopier {
   private RequestCopier() {
   }

   public static RequestData copy(HttpServletRequest request) {
      RequestData requestData = new RequestData();
      requestData.setCookies(copyCookies(request.getCookies()));
      requestData.setHeaders(copyHeaders(request));
      requestData.setParameters(copyParameters(request.getParameterMap()));
      requestData.setLocales(Collections.list(request.getLocales()));
      requestData.setMethod(request.getMethod());
      requestData.setRequestURL(request.getRequestURL().toString());
      requestData.setQueryString(request.getQueryString());
      return requestData;
   }

   private static Cookie[] copyCookies(Cookie[] cookies) {
      if (Utils.isEmpty((Object[])cookies)) {
         return cookies;
      } else {
         ArrayList copiedCookies = new ArrayList();
         Cookie[] var2 = cookies;
         int var3 = cookies.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Cookie cookie = var2[var4];
            copiedCookies.add((Cookie)cookie.clone());
         }

         return (Cookie[])copiedCookies.toArray(new Cookie[copiedCookies.size()]);
      }
   }

   private static Map copyHeaders(HttpServletRequest request) {
      Map copiedHeaders = new HashMap();
      Iterator var2 = Collections.list(request.getHeaderNames()).iterator();

      while(var2.hasNext()) {
         String headerName = (String)var2.next();
         copiedHeaders.put(headerName, Collections.list(request.getHeaders(headerName)));
      }

      return copiedHeaders;
   }

   private static Map copyParameters(Map parameters) {
      if (isEmptyMap(parameters)) {
         return Collections.emptyMap();
      } else {
         Map copiedParameters = new HashMap();
         Iterator var2 = parameters.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry parameter = (Map.Entry)var2.next();
            copiedParameters.put(parameter.getKey(), Arrays.copyOf((Object[])parameter.getValue(), ((String[])parameter.getValue()).length));
         }

         return copiedParameters;
      }
   }

   private static boolean isEmptyMap(Map map) {
      return map == null || map.isEmpty();
   }
}
