package com.sun.faces.facelets.compiler;

import com.sun.faces.el.ELContextImpl;
import com.sun.faces.facelets.el.CompositeFunctionMapper;
import com.sun.faces.facelets.tag.TagLibrary;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.el.ELContext;
import javax.el.FunctionMapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletHandler;

final class NamespaceHandler extends FunctionMapper implements FaceletHandler {
   private final TagLibrary library;
   private final Map ns;
   private FaceletHandler next;

   public NamespaceHandler(FaceletHandler next, TagLibrary library, Map ns) {
      this.library = library;
      this.ns = ns;
      this.next = next;
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      FunctionMapper orig = ctx.getFunctionMapper();
      this.pushMapper(ctx.getFacesContext(), this);
      ctx.setFunctionMapper(new CompositeFunctionMapper(this, orig));

      try {
         this.next.apply(ctx, parent);
      } finally {
         ctx.setFunctionMapper(orig);
      }

   }

   public Method resolveFunction(String prefix, String localName) {
      String uri = (String)this.ns.get(prefix);
      return uri != null ? this.library.createFunction(uri, localName) : null;
   }

   private void pushMapper(FacesContext ctx, FunctionMapper mapper) {
      ELContext elContext = ctx.getELContext();
      if (elContext instanceof ELContextImpl) {
         ((ELContextImpl)elContext).setFunctionMapper(mapper);
      }

   }
}
