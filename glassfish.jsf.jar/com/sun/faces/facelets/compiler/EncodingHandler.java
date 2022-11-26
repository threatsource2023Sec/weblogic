package com.sun.faces.facelets.compiler;

import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletHandler;

public class EncodingHandler implements FaceletHandler {
   private final FaceletHandler next;
   private final String encoding;
   private final CompilationMessageHolder messageHolder;

   public EncodingHandler(FaceletHandler next, String encoding, CompilationMessageHolder messageHolder) {
      this.next = next;
      this.encoding = encoding;
      this.messageHolder = messageHolder;
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      FacesContext context = ctx.getFacesContext();
      Map ctxAttributes = context.getAttributes();
      ctxAttributes.put("facelets.compilationMessages", this.messageHolder);
      this.next.apply(ctx, parent);
      ctxAttributes.remove("facelets.compilationMessages");
      this.messageHolder.processCompilationMessages(ctx.getFacesContext());
      if (!ctxAttributes.containsKey("facelets.Encoding")) {
         ctx.getFacesContext().getAttributes().put("facelets.Encoding", this.encoding);
      }

   }

   public static CompilationMessageHolder getCompilationMessageHolder(FaceletContext ctx) {
      return (CompilationMessageHolder)ctx.getFacesContext().getAttributes().get("facelets.compilationMessages");
   }
}
