package com.sun.faces.facelets.compiler;

import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.MessageUtils;
import java.io.IOException;
import java.util.List;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

final class EndElementInstruction implements Instruction {
   private static final String HEAD_ELEMENT = "head";
   private static final String BODY_ELEMENT = "body";
   private final String element;

   public EndElementInstruction(String element) {
      this.element = element;
   }

   public void write(FacesContext context) throws IOException {
      if ("head".equalsIgnoreCase(this.element)) {
         this.warnUnhandledResources(context, "head");
      }

      if ("body".equalsIgnoreCase(this.element)) {
         this.warnUnhandledResources(context, "body");
         RenderKitUtils.renderUnhandledMessages(context);
      }

      context.getResponseWriter().endElement(this.element);
   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      return this;
   }

   public boolean isLiteral() {
      return true;
   }

   private void warnUnhandledResources(FacesContext ctx, String target) {
      UIViewRoot root = ctx.getViewRoot();
      if (root != null) {
         List headResources = root.getComponentResources(ctx, target);
         if (headResources != null && !headResources.isEmpty()) {
            FacesMessage m = MessageUtils.getExceptionMessage("com.sun.faces.RESOURCE_TARGET_NOT_AVAILABLE", target);
            ctx.addMessage((String)null, m);
         }
      }

   }
}
