package com.sun.faces.lifecycle;

import com.sun.faces.renderkit.RenderKitUtils;
import java.util.Map;
import javax.faces.component.UINamingContainer;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.ClientWindow;

public class ClientWindowImpl extends ClientWindow {
   String id;

   public Map getQueryURLParameters(FacesContext context) {
      return null;
   }

   public void decode(FacesContext context) {
      Map requestParamMap = context.getExternalContext().getRequestParameterMap();
      if (this.isClientWindowRenderModeEnabled(context)) {
         this.id = (String)requestParamMap.get("jfwid");
      }

      String paramName = RenderKitUtils.PredefinedPostbackParameter.CLIENT_WINDOW_PARAM.getName(context);
      if (requestParamMap.containsKey(paramName)) {
         this.id = (String)requestParamMap.get(paramName);
      }

      if (null == this.id) {
         this.id = this.calculateClientWindow(context);
      }

   }

   private String calculateClientWindow(FacesContext context) {
      synchronized(context.getExternalContext().getSession(true)) {
         String clientWindowCounterKey = "com.sun.faces.lifecycle.ClientWindowCounterKey";
         ExternalContext extContext = context.getExternalContext();
         Map sessionAttrs = extContext.getSessionMap();
         Integer counter = (Integer)sessionAttrs.get("com.sun.faces.lifecycle.ClientWindowCounterKey");
         if (null == counter) {
            counter = 0;
         }

         char sep = UINamingContainer.getSeparatorChar(context);
         this.id = extContext.getSessionId(true) + sep + counter;
         sessionAttrs.put("com.sun.faces.lifecycle.ClientWindowCounterKey", counter + 1);
      }

      return this.id;
   }

   public String getId() {
      return this.id;
   }
}
