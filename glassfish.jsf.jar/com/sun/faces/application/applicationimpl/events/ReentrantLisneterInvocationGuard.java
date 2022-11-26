package com.sun.faces.application.applicationimpl.events;

import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;

public class ReentrantLisneterInvocationGuard {
   public boolean isGuardSet(FacesContext ctx, Class systemEventClass) {
      Map data = this.getDataStructure(ctx);
      Boolean result = (Boolean)data.get(systemEventClass);
      return null == result ? false : result;
   }

   public void setGuard(FacesContext ctx, Class systemEventClass) {
      Map data = this.getDataStructure(ctx);
      data.put(systemEventClass, Boolean.TRUE);
   }

   public void clearGuard(FacesContext ctx, Class systemEventClass) {
      Map data = this.getDataStructure(ctx);
      data.put(systemEventClass, Boolean.FALSE);
   }

   private Map getDataStructure(FacesContext ctx) {
      Map result = null;
      Map ctxMap = ctx.getAttributes();
      String IS_PROCESSING_LISTENERS_KEY = "com.sun.faces.application.ApplicationImpl.IS_PROCESSING_LISTENERS";
      if (null == (result = (Map)ctxMap.get("com.sun.faces.application.ApplicationImpl.IS_PROCESSING_LISTENERS"))) {
         result = new HashMap(12);
         ctxMap.put("com.sun.faces.application.ApplicationImpl.IS_PROCESSING_LISTENERS", result);
      }

      return (Map)result;
   }
}
