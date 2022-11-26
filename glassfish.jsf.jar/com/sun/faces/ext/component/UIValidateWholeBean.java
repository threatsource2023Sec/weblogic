package com.sun.faces.ext.component;

import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.Validator;
import javax.validation.groups.Default;

public class UIValidateWholeBean extends UIInput implements PartialStateHolder {
   private static final String ERROR_MISSING_FORM = "f:validateWholeBean must be nested directly in an UIForm.";
   private static final String ERROR_MISPLACED_COMPONENT = "f:validateWholeBean must be placed at the end of UIForm.";
   public static final String FAMILY = "com.sun.faces.ext.validateWholeBean";
   private transient Class[] cachedValidationGroups;
   private transient String validationGroups = "";
   private boolean transientValue;
   private boolean initialState;

   public String getFamily() {
      return "com.sun.faces.ext.validateWholeBean";
   }

   public Object getSubmittedValue() {
      return this.getFamily();
   }

   public void setConverter(Converter converter) {
   }

   public final void addValidator(Validator validator) {
      if (validator instanceof WholeBeanValidator) {
         super.addValidator(validator);
         this.setValidatorInstalled(true);
      }

   }

   public void setValidationGroups(String validationGroups) {
      this.clearInitialState();
      String newValidationGroups = validationGroups;
      if (validationGroups != null && validationGroups.matches("^[\\W,]*$")) {
         newValidationGroups = null;
      }

      if (newValidationGroups == null && validationGroups != null) {
         this.cachedValidationGroups = null;
      }

      if (newValidationGroups != null && validationGroups != null && !newValidationGroups.equals(validationGroups)) {
         this.cachedValidationGroups = null;
      }

      if (newValidationGroups != null && validationGroups == null) {
         this.cachedValidationGroups = null;
      }

      this.validationGroups = newValidationGroups;
   }

   public String getValidationGroups() {
      return this.validationGroups;
   }

   public void validate(FacesContext context) {
      if (this.wholeBeanValidationEnabled(context)) {
         if (!this.isValidatorInstalled()) {
            WholeBeanValidator validator = new WholeBeanValidator();
            this.addValidator(validator);
         }

         super.validate(context);
      }
   }

   public void updateModel(FacesContext context) {
   }

   public void encodeBegin(FacesContext context) throws IOException {
      UIForm parent = (UIForm)getClosestParent(this, UIForm.class);
      if (parent == null) {
         throw new IllegalArgumentException("f:validateWholeBean must be nested directly in an UIForm.");
      } else {
         misplacedComponentCheck(parent, this.getClientId());
      }
   }

   private static void misplacedComponentCheck(UIComponent parentComponent, String clientId) throws IllegalArgumentException {
      try {
         Util.reverse(parentComponent.getChildren()).stream().forEach((childComponent) -> {
            if (childComponent.isRendered()) {
               if (childComponent instanceof EditableValueHolder && !(childComponent instanceof UIValidateWholeBean)) {
                  throw new IllegalArgumentException("f:validateWholeBean must be placed at the end of UIForm.");
               }

               if (childComponent.getClientId().equals(clientId)) {
                  throw new BreakException();
               }

               misplacedComponentCheck(childComponent, clientId);
            }

         });
      } catch (BreakException var3) {
      }

   }

   public static UIComponent getClosestParent(UIComponent component, Class parentType) {
      UIComponent parent;
      for(parent = component.getParent(); parent != null && !parentType.isInstance(parent); parent = parent.getParent()) {
      }

      return (UIComponent)parentType.cast(parent);
   }

   private boolean isValidatorInstalled() {
      return (Boolean)this.getStateHelper().eval(UIValidateWholeBean.PropertyKeys.ValidatorInstalled, false);
   }

   private void setValidatorInstalled(boolean newValue) {
      this.getStateHelper().put(UIValidateWholeBean.PropertyKeys.ValidatorInstalled, newValue);
   }

   Class[] getValidationGroupsArray() {
      if (this.cachedValidationGroups != null) {
         return this.cachedValidationGroups;
      } else {
         String validationGroupsStr = this.getValidationGroups();
         List validationGroupsList = new ArrayList();
         String[] var3 = validationGroupsStr.split(",");
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String className = var3[var5];
            className = className.trim();
            if (className.length() != 0) {
               if (className.equals(Default.class.getName())) {
                  validationGroupsList.add(Default.class);
               } else {
                  validationGroupsList.add(this.classForName(className));
               }
            }
         }

         this.cachedValidationGroups = (Class[])validationGroupsList.toArray(new Class[validationGroupsList.size()]);
         return this.cachedValidationGroups;
      }
   }

   private boolean wholeBeanValidationEnabled(FacesContext context) {
      return Boolean.TRUE.equals(context.getAttributes().get("javax.faces.validator.ENABLE_VALIDATE_WHOLE_BEAN"));
   }

   public void markInitialState() {
      this.initialState = true;
   }

   public boolean initialStateMarked() {
      return this.initialState;
   }

   public void clearInitialState() {
      this.initialState = false;
   }

   public boolean isTransient() {
      return this.transientValue;
   }

   public void setTransient(boolean transientValue) {
      this.transientValue = transientValue;
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object[] result = null;
         if (!this.initialStateMarked()) {
            Object[] values = new Object[]{this.validationGroups, super.saveState(context)};
            return values;
         } else {
            return result;
         }
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (state != null) {
            Object[] values = (Object[])((Object[])state);
            this.validationGroups = (String)values[0];
            Object parentState = values[1];
            super.restoreState(context, parentState);
         }

      }
   }

   private Class classForName(String className) {
      try {
         return Class.forName(className, false, Thread.currentThread().getContextClassLoader());
      } catch (ClassNotFoundException var5) {
         try {
            return Class.forName(className);
         } catch (ClassNotFoundException var4) {
            throw new FacesException("Validation group not found: " + className);
         }
      }
   }

   private static class BreakException extends RuntimeException {
      private static final long serialVersionUID = 1L;

      private BreakException() {
      }

      // $FF: synthetic method
      BreakException(Object x0) {
         this();
      }
   }

   private static enum PropertyKeys {
      ValidatorInstalled;
   }
}
