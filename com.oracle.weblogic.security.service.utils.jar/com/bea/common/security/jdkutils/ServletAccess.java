package com.bea.common.security.jdkutils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServletAccess {
   private Map servletInfos = null;
   private static ServletAccess SINGLETON = null;

   private ServletAccess() {
      this.servletInfos = new ConcurrentHashMap();
   }

   public static synchronized ServletAccess getInstance() {
      if (SINGLETON == null) {
         SINGLETON = new ServletAccess();
      }

      return SINGLETON;
   }

   public boolean registerServletInfo(String key, ServletInfoSpi servletInfo) {
      if (key == null) {
         return false;
      } else {
         this.servletInfos.put(key, servletInfo);
         return true;
      }
   }

   public void unRegisterServletInfo(String key) {
      if (key != null && this.servletInfos.containsKey(key)) {
         this.servletInfos.remove(key);
      }
   }

   public ServletInfoSpi getServletInfo(String key) {
      return key != null && this.servletInfos.containsKey(key) ? (ServletInfoSpi)this.servletInfos.get(key) : null;
   }
}
