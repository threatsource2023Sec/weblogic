package com.sun.faces.component.validator;

import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.util.RequestStateManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.ValidatorHandler;

public class ComponentValidators {
   private static final String COMPONENT_VALIDATORS = "javax.faces.component.ComponentValidators";
   private LinkedList validatorStack = null;

   public ComponentValidators() {
      this.validatorStack = new LinkedList();
   }

   public static ComponentValidators getValidators(FacesContext context, boolean createIfNull) {
      Map attrs = context.getAttributes();
      ComponentValidators componentValidators = (ComponentValidators)attrs.get("javax.faces.component.ComponentValidators");
      if (componentValidators == null && createIfNull) {
         componentValidators = new ComponentValidators();
         attrs.put("javax.faces.component.ComponentValidators", componentValidators);
      }

      return componentValidators;
   }

   public static void addDefaultValidatorsToComponent(FacesContext ctx, EditableValueHolder editableValueHolder) {
      if (ComponentSupport.isBuildingNewComponentTree(ctx)) {
         Set keySet = ctx.getApplication().getDefaultValidatorInfo().keySet();
         List validatorIds = new ArrayList(keySet.size());
         Set disabledValidatorIds = (Set)RequestStateManager.remove(ctx, "com.sun.faces.DISABLED_VALIDATORS");
         Iterator var5 = keySet.iterator();

         while(true) {
            String key;
            do {
               if (!var5.hasNext()) {
                  addValidatorsToComponent(ctx, validatorIds, editableValueHolder, (LinkedList)null);
                  return;
               }

               key = (String)var5.next();
            } while(disabledValidatorIds != null && disabledValidatorIds.contains(key));

            validatorIds.add(key);
         }
      }
   }

   public void addValidators(FacesContext ctx, EditableValueHolder editableValueHolder) {
      if (this.validatorStack != null && !this.validatorStack.isEmpty()) {
         Application application = ctx.getApplication();
         Map defaultValidatorInfo = application.getDefaultValidatorInfo();
         Set keySet = defaultValidatorInfo.keySet();
         List validatorIds = new ArrayList(keySet.size());
         Iterator var7 = keySet.iterator();

         while(var7.hasNext()) {
            String key = (String)var7.next();
            validatorIds.add(key);
         }

         Set disabledIds = (Set)RequestStateManager.remove(ctx, "com.sun.faces.DISABLED_VALIDATORS");
         int count = this.validatorStack.size();

         for(int i = count - 1; i >= 0; --i) {
            ValidatorInfo info = (ValidatorInfo)this.validatorStack.get(i);
            if (!info.isEnabled() || disabledIds != null && disabledIds.contains(info.getValidatorId())) {
               if (validatorIds.contains(info.getValidatorId())) {
                  validatorIds.remove(info.getValidatorId());
               }
            } else if (!validatorIds.contains(info.getValidatorId())) {
               validatorIds.add(info.getValidatorId());
            }
         }

         addValidatorsToComponent(ctx, validatorIds, editableValueHolder, this.validatorStack != null && !this.validatorStack.isEmpty() ? this.validatorStack : null);
      } else {
         addDefaultValidatorsToComponent(ctx, editableValueHolder);
      }
   }

   public void pushValidatorInfo(ValidatorInfo info) {
      this.validatorStack.add(info);
   }

   public void popValidatorInfo() {
      if (this.validatorStack.size() > 0) {
         this.validatorStack.removeLast();
      }

   }

   private static void addValidatorsToComponent(FacesContext ctx, Collection validatorIds, EditableValueHolder editableValueHolder, LinkedList validatorStack) {
      if (validatorIds != null && !validatorIds.isEmpty()) {
         Application application = ctx.getApplication();
         Map defaultValidatorInfo = application.getDefaultValidatorInfo();
         Validator[] validators = editableValueHolder.getValidators();
         Iterator var7 = defaultValidatorInfo.entrySet().iterator();

         while(true) {
            int i;
            while(var7.hasNext()) {
               Map.Entry defaultValidator = (Map.Entry)var7.next();
               Validator[] var9 = validators;
               i = validators.length;

               for(int var11 = 0; var11 < i; ++var11) {
                  Validator validator = var9[var11];
                  if (((String)defaultValidator.getValue()).equals(validator.getClass().getName())) {
                     validatorIds.remove(defaultValidator.getKey());
                     break;
                  }
               }
            }

            Validator v;
            for(var7 = validatorIds.iterator(); var7.hasNext(); editableValueHolder.addValidator(v)) {
               String id = (String)var7.next();
               v = application.createValidator(id);
               if (validatorStack != null) {
                  for(i = validatorStack.size() - 1; i >= 0; --i) {
                     ValidatorInfo info = (ValidatorInfo)validatorStack.get(i);
                     if (id.equals(info.getValidatorId())) {
                        info.applyAttributes(v);
                        break;
                     }
                  }
               }
            }

            return;
         }
      }
   }

   public static class ValidatorInfo {
      private String validatorId;
      private boolean enabled;
      private ValidatorHandler owner;
      private FaceletContext ctx;

      public ValidatorInfo(FaceletContext ctx, ValidatorHandler owner) {
         this.owner = owner;
         this.ctx = ctx;
         this.validatorId = owner.getValidatorId(ctx);
         this.enabled = !owner.isDisabled(ctx);
      }

      public String getValidatorId() {
         return this.validatorId;
      }

      public boolean isEnabled() {
         return this.enabled;
      }

      public void applyAttributes(Validator v) {
         this.owner.setAttributes(this.ctx, v);
      }
   }
}
