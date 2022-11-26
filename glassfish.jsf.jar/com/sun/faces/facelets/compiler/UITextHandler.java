package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.facelets.util.FastWriter;
import java.io.IOException;
import java.io.Writer;
import javax.el.ELException;
import javax.faces.component.UIComponent;
import javax.faces.component.UniqueIdVendor;
import javax.faces.view.facelets.FaceletContext;

final class UITextHandler extends AbstractUIHandler {
   private final ELText txt;
   private final String alias;
   private final int length;

   public UITextHandler(String alias, ELText txt) {
      this.alias = alias;
      this.txt = txt;
      this.length = txt.toString().length();
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent != null) {
         try {
            ELText nt = this.txt.apply(ctx.getExpressionFactory(), ctx);
            UIComponent c = new UIText(this.alias, nt);
            UIComponent ancestorNamingContainer = parent.getNamingContainer();
            String uid;
            if (null != ancestorNamingContainer && ancestorNamingContainer instanceof UniqueIdVendor) {
               uid = ((UniqueIdVendor)ancestorNamingContainer).createUniqueId(ctx.getFacesContext(), (String)null);
            } else {
               uid = ComponentSupport.getViewRoot(ctx, parent).createUniqueId();
            }

            c.setId(uid);
            this.addComponent(ctx, parent, c);
         } catch (Exception var7) {
            throw new ELException(this.alias + ": " + var7.getMessage(), var7.getCause());
         }
      }

   }

   public String toString() {
      return this.txt.toString();
   }

   public String getText() {
      return this.txt.toString();
   }

   public String getText(FaceletContext ctx) {
      Writer writer = new FastWriter(this.length);

      try {
         this.txt.apply(ctx.getExpressionFactory(), ctx).write(writer, ctx);
      } catch (IOException var4) {
         throw new ELException(this.alias + ": " + var4.getMessage(), var4.getCause());
      }

      return writer.toString();
   }
}
