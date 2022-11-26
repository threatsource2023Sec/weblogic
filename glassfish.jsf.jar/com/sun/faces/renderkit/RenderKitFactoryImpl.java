package com.sun.faces.renderkit;

import com.sun.faces.util.MessageUtils;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

public class RenderKitFactoryImpl extends RenderKitFactory {
   protected String renderKitId;
   protected String className;
   protected ConcurrentHashMap renderKits = new ConcurrentHashMap();

   public RenderKitFactoryImpl() {
      super((RenderKitFactory)null);
      this.addRenderKit("HTML_BASIC", new RenderKitImpl());
   }

   public void addRenderKit(String renderKitId, RenderKit renderKit) {
      String message;
      if (renderKitId == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "renderKitId");
         throw new NullPointerException(message);
      } else if (renderKit == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "renderKit");
         throw new NullPointerException(message);
      } else {
         this.renderKits.put(renderKitId, renderKit);
      }
   }

   public RenderKit getRenderKit(FacesContext context, String renderKitId) {
      if (renderKitId == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "renderKitId");
         throw new NullPointerException(message);
      } else {
         return (RenderKit)this.renderKits.get(renderKitId);
      }
   }

   public Iterator getRenderKitIds() {
      return this.renderKits.keySet().iterator();
   }
}
