package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.tag.MetaRulesetImpl;
import com.sun.faces.util.Util;
import java.io.IOException;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.AttachedObjectHandler;
import javax.faces.view.facelets.ConverterHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TagHandlerDelegate;

public class ConverterTagHandlerDelegateImpl extends TagHandlerDelegate implements AttachedObjectHandler {
   private ConverterHandler owner;

   public ConverterTagHandlerDelegateImpl(ConverterHandler owner) {
      this.owner = owner;
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent != null && parent.getParent() == null) {
         ComponentSupport.copyPassthroughAttributes(ctx, parent, this.owner.getTag());
         if (parent instanceof ValueHolder) {
            this.owner.applyAttachedObject(ctx.getFacesContext(), parent);
         } else {
            if (!UIComponent.isCompositeComponent(parent)) {
               throw new TagException(this.owner.getTag(), "Parent not an instance of ValueHolder: " + parent);
            }

            if (null == this.owner.getFor()) {
               throw new TagException(this.owner.getTag(), "converter tags nested within composite components must have a non-null \"for\" attribute");
            }

            CompositeComponentTagHandler.getAttachedObjectHandlers(parent).add(this.owner);
         }

      }
   }

   public MetaRuleset createMetaRuleset(Class type) {
      Util.notNull("type", type);
      MetaRuleset m = new MetaRulesetImpl(this.owner.getTag(), type);
      return m.ignore("binding").ignore("for");
   }

   public String getFor() {
      String result = null;
      TagAttribute attr = this.owner.getTagAttribute("for");
      if (null != attr) {
         if (attr.isLiteral()) {
            result = attr.getValue();
         } else {
            FacesContext context = FacesContext.getCurrentInstance();
            FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
            result = (String)attr.getValueExpression(ctx, String.class).getValue(ctx);
         }
      }

      return result;
   }

   public void applyAttachedObject(FacesContext context, UIComponent parent) {
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      ValueHolder vh = (ValueHolder)parent;
      ValueExpression ve = null;
      Converter c = null;
      if (this.owner.getBinding() != null) {
         ve = this.owner.getBinding().getValueExpression(ctx, Converter.class);
         c = (Converter)ve.getValue(ctx);
      }

      if (c == null) {
         c = this.createConverter(ctx);
         if (ve != null) {
            ve.setValue(ctx, c);
         }
      }

      if (c == null) {
         throw new TagException(this.owner.getTag(), "No Converter was created");
      } else {
         this.owner.setAttributes(ctx, c);
         vh.setConverter(c);
         Object lv = vh.getLocalValue();
         FacesContext faces = ctx.getFacesContext();
         if (lv instanceof String) {
            vh.setValue(c.getAsObject(faces, parent, (String)lv));
         }

      }
   }

   private Converter createConverter(FaceletContext ctx) {
      if (this.owner.getConverterId(ctx) == null) {
         throw new TagException(this.owner.getTag(), "Default behavior invoked of requiring a converter-id passed in the constructor, must override ConvertHandler(ConverterConfig)");
      } else {
         return ctx.getFacesContext().getApplication().createConverter(this.owner.getConverterId(ctx));
      }
   }
}
