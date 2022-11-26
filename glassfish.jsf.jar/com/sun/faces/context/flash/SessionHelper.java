package com.sun.faces.context.flash;

import java.io.Serializable;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

class SessionHelper implements Serializable, HttpSessionActivationListener {
   private static final long serialVersionUID = -4146679754778263071L;
   static final String FLASH_SESSIONACTIVATIONLISTENER_ATTRIBUTE_NAME = "csfcffFSAL";
   private static final String FLASH_INNER_MAP_KEY = "csfcffFIM";
   private boolean didPassivate;

   static SessionHelper getInstance(ExternalContext extContext) {
      return (SessionHelper)extContext.getSessionMap().get("csfcffFSAL");
   }

   void update(ExternalContext extContext, ELFlash flash) {
      Map sessionMap = extContext.getSessionMap();
      if (this.didPassivate) {
         Map flashInnerMap = (Map)sessionMap.get("csfcffFIM");
         flash.setFlashInnerMap(flashInnerMap);
         this.didPassivate = false;
      } else {
         sessionMap.put("csfcffFSAL", this);
         sessionMap.put("csfcffFIM", flash.getFlashInnerMap());
      }

   }

   void remove(ExternalContext extContext) {
      Map sessionMap = extContext.getSessionMap();
      sessionMap.remove("csfcffFSAL");
      sessionMap.remove("csfcffFIM");
   }

   public void sessionDidActivate(HttpSessionEvent hse) {
      this.didPassivate = true;
   }

   public void sessionWillPassivate(HttpSessionEvent hse) {
      this.didPassivate = true;
   }
}
