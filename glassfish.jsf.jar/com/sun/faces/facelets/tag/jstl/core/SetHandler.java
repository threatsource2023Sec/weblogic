package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import java.util.Iterator;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TextHandler;

public class SetHandler extends TagHandlerImpl {
   private final TagAttribute var = this.getAttribute("var");
   private final TagAttribute value = this.getAttribute("value");
   private final TagAttribute target = this.getAttribute("target");
   private final TagAttribute property = this.getAttribute("property");
   private final TagAttribute scope = this.getAttribute("scope");

   public SetHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      StringBuilder bodyValue = new StringBuilder();
      Iterator iter = TagHandlerImpl.findNextByType(this.nextHandler, TextHandler.class);

      while(iter.hasNext()) {
         TextHandler text = (TextHandler)iter.next();
         bodyValue.append(text.getText(ctx));
      }

      boolean valSet = bodyValue.length() > 0 || this.value != null && this.value.getValue().length() > 0;
      ValueExpression veObj;
      if (this.value != null) {
         veObj = this.value.getValueExpression(ctx, Object.class);
      } else {
         veObj = ctx.getExpressionFactory().createValueExpression(ctx.getFacesContext().getELContext(), bodyValue.toString(), Object.class);
      }

      String scopeStr;
      if (this.var != null) {
         String varStr = this.var.getValue(ctx);
         if (null != this.scope) {
            if (0 == this.scope.getValue().length()) {
               throw new TagException(this.tag, "zero length scope attribute set");
            }

            if (this.scope.isLiteral()) {
               scopeStr = this.scope.getValue();
            } else {
               scopeStr = this.scope.getValue(ctx);
            }

            if (scopeStr.equals("page")) {
               throw new TagException(this.tag, "page scope does not exist in JSF, consider using view scope instead.");
            }

            if (scopeStr.equals("request") || scopeStr.equals("session") || scopeStr.equals("application") || scopeStr.equals("view")) {
               scopeStr = scopeStr + "Scope";
            }

            String expr = "#{" + scopeStr + "." + varStr + "}";
            ValueExpression lhs = ctx.getExpressionFactory().createValueExpression(ctx, expr, Object.class);
            lhs.setValue(ctx, veObj.getValue(ctx));
         } else {
            ctx.getVariableMapper().setVariable(varStr, veObj);
         }
      } else {
         if (null == this.target || null == this.target.getValue() || this.target.getValue().length() <= 0 || null == this.property || null == this.property.getValue() || this.property.getValue().length() <= 0 || !valSet) {
            throw new TagException(this.tag, "when using this tag either one of var and value, or (target, property, value) must be set.");
         }

         if (this.target.isLiteral()) {
            throw new TagException(this.tag, "value of target attribute must be an expression");
         }

         scopeStr = null;
         if (this.property.isLiteral()) {
            scopeStr = this.property.getValue();
         } else {
            scopeStr = this.property.getValue(ctx);
         }

         ValueExpression targetVe = this.target.getValueExpression(ctx, Object.class);
         Object targetValue = targetVe.getValue(ctx);
         ctx.getFacesContext().getELContext().getELResolver().setValue(ctx, targetValue, scopeStr, veObj.getValue(ctx));
      }

   }

   protected void applyNextHandler(FaceletContext ctx, UIComponent c) {
   }
}
