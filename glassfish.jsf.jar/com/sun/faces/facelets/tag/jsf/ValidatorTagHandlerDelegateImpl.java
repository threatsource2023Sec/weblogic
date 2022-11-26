package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.cdi.CdiValidator;
import com.sun.faces.component.validator.ComponentValidators;
import com.sun.faces.facelets.tag.MetaRulesetImpl;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.view.AttachedObjectHandler;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.CompositeFaceletHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TagHandler;
import javax.faces.view.facelets.TagHandlerDelegate;
import javax.faces.view.facelets.ValidatorHandler;

public class ValidatorTagHandlerDelegateImpl extends TagHandlerDelegate implements AttachedObjectHandler {
   protected final ValidatorHandler owner;
   private final boolean wrapping;

   public ValidatorTagHandlerDelegateImpl(ValidatorHandler owner) {
      this.owner = owner;
      this.wrapping = this.isWrapping();
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      ComponentSupport.copyPassthroughAttributes(ctx, parent, this.owner.getTag());
      if (this.wrapping) {
         this.applyWrapping(ctx, parent);
      } else {
         this.applyNested(ctx, parent);
      }

   }

   public MetaRuleset createMetaRuleset(Class type) {
      Util.notNull("type", type);
      MetaRuleset m = new MetaRulesetImpl(this.owner.getTag(), type);
      return m.ignore("binding").ignore("disabled").ignore("for");
   }

   public void applyAttachedObject(FacesContext context, UIComponent parent) {
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      EditableValueHolder evh = (EditableValueHolder)parent;
      if (this.owner.isDisabled(ctx)) {
         Set disabledIds = (Set)RequestStateManager.get(context, "com.sun.faces.DISABLED_VALIDATORS");
         if (disabledIds == null) {
            disabledIds = new HashSet(3);
            RequestStateManager.set(context, "com.sun.faces.DISABLED_VALIDATORS", disabledIds);
         }

         ((Set)disabledIds).add(this.owner.getValidatorId(ctx));
      } else {
         ValueExpression ve = null;
         Validator v = null;
         if (this.owner.getBinding() != null) {
            ve = this.owner.getBinding().getValueExpression(ctx, Validator.class);
            v = (Validator)ve.getValue(ctx);
         }

         if (v == null) {
            v = this.createValidator(ctx);
            if (ve != null) {
               ve.setValue(ctx, v);
            }
         }

         if (v == null) {
            throw new TagException(this.owner.getTag(), "No Validator was created");
         } else {
            this.owner.setAttributes(ctx, v);
            Validator[] validators = evh.getValidators();
            boolean found = false;
            Validator[] var9 = validators;
            int var10 = validators.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               Validator validator = var9[var11];
               if (validator.getClass().equals(v.getClass()) && !(v instanceof CdiValidator)) {
                  found = true;
                  break;
               }
            }

            if (!found) {
               evh.addValidator(v);
            }

         }
      }
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

   protected ComponentValidators.ValidatorInfo createValidatorInfo(FaceletContext ctx) {
      return new ComponentValidators.ValidatorInfo(ctx, this.owner);
   }

   private boolean isWrapping() {
      return this.owner.getValidatorConfig().getNextHandler() instanceof TagHandler || this.owner.getValidatorConfig().getNextHandler() instanceof CompositeFaceletHandler;
   }

   private void applyWrapping(FaceletContext ctx, UIComponent parent) throws IOException {
      ComponentValidators validators = ComponentValidators.getValidators(ctx.getFacesContext(), true);
      validators.pushValidatorInfo(this.createValidatorInfo(ctx));
      this.owner.getValidatorConfig().getNextHandler().apply(ctx, parent);
      validators.popValidatorInfo();
   }

   private void applyNested(FaceletContext ctx, UIComponent parent) {
      if (ComponentHandler.isNew(parent)) {
         if (parent instanceof EditableValueHolder) {
            this.applyAttachedObject(ctx.getFacesContext(), parent);
         } else {
            if (!UIComponent.isCompositeComponent(parent)) {
               throw new TagException(this.owner.getTag(), "Parent not an instance of EditableValueHolder: " + parent);
            }

            if (null == this.owner.getFor()) {
               throw new TagException(this.owner.getTag(), "validator tags nested within composite components must have a non-null \"for\" attribute");
            }

            CompositeComponentTagHandler.getAttachedObjectHandlers(parent).add(this.owner);
         }

      }
   }

   private Validator createValidator(FaceletContext ctx) {
      String id = this.owner.getValidatorId(ctx);
      if (id == null) {
         throw new TagException(this.owner.getTag(), "A validator id was not specified. Typically the validator id is set in the constructor ValidateHandler(ValidatorConfig)");
      } else {
         return ctx.getFacesContext().getApplication().createValidator(id);
      }
   }
}
