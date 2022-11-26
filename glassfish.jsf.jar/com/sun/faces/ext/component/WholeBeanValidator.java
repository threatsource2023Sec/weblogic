package com.sun.faces.ext.component;

import com.sun.faces.util.BeanValidation;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.Util;
import com.sun.faces.util.copier.CopierUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.validation.ConstraintViolation;

class WholeBeanValidator implements Validator {
   private static final Logger LOGGER = Logger.getLogger("javax.faces.validator", "javax.faces.LogStrings");
   private static final String ERROR_MISSING_FORM = "f:validateWholeBean must be nested directly in an UIForm.";

   public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
      this.validate(context, (UIValidateWholeBean)component, value);
   }

   public void validate(FacesContext context, UIValidateWholeBean component, Object value) throws ValidatorException {
      UIForm form = this.getParentForm(component);
      ValueExpression wholeBeanVE = Util.getValueExpressionNullSafe(component, "value");
      Object wholeBean = wholeBeanVE.getValue(context.getELContext());
      String copierType = (String)component.getAttributes().get("copierType");
      if (!this.hasAnyBeanPropertyFailedValidation(context, wholeBean)) {
         AddRemainingCandidateFieldsCallback addRemainingCandidateFieldsCallback = new AddRemainingCandidateFieldsCallback(context, wholeBean);
         form.visitTree(VisitContext.createVisitContext(context), addRemainingCandidateFieldsCallback);
         Map validationCandidate = addRemainingCandidateFieldsCallback.getCandidate();
         if (!validationCandidate.isEmpty()) {
            Set violations = this.doBeanValidation(BeanValidation.getBeanValidator(context), this.copyBeanAndPopulateWithCandidateValues(context, wholeBeanVE, wholeBean, copierType, validationCandidate), component.getValidationGroupsArray(), wholeBeanVE);
            if (violations != null && !violations.isEmpty()) {
               ValidatorException toThrow;
               if (violations.size() == 1) {
                  ConstraintViolation violation = (ConstraintViolation)violations.iterator().next();
                  toThrow = new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.BeanValidator.MESSAGE", violation.getMessage(), MessageFactory.getLabel(context, component)));
               } else {
                  Set messages = new LinkedHashSet(violations.size());
                  Iterator var13 = violations.iterator();

                  while(var13.hasNext()) {
                     ConstraintViolation violation = (ConstraintViolation)var13.next();
                     messages.add(MessageFactory.getMessage(context, "javax.faces.validator.BeanValidator.MESSAGE", violation.getMessage(), MessageFactory.getLabel(context, component)));
                  }

                  toThrow = new ValidatorException(messages);
               }

               Iterator var16 = validationCandidate.entrySet().iterator();

               while(var16.hasNext()) {
                  Map.Entry validationCandidateEntry = (Map.Entry)var16.next();
                  this.invalidateComponent(validationCandidateEntry);
               }

               throw toThrow;
            }
         }
      }
   }

   private UIForm getParentForm(UIComponent component) {
      UIComponent parent = component.getParent();
      if (!(parent instanceof UIForm)) {
         throw new IllegalArgumentException("f:validateWholeBean must be nested directly in an UIForm.");
      } else {
         return (UIForm)parent;
      }
   }

   private boolean isFailedFieldLevelValidation(Map.Entry wholeBeanPropertyEntry) {
      return "javax.faces.Bean.FAILED_FIELD_LEVEL_VALIDATION".equals(((Map)wholeBeanPropertyEntry.getValue()).get("value"));
   }

   private void invalidateComponent(Map.Entry wholeBeanPropertyEntry) {
      ((EditableValueHolder)((Map)wholeBeanPropertyEntry.getValue()).get("component")).setValid(false);
   }

   private boolean hasAnyBeanPropertyFailedValidation(FacesContext context, Object wholeBean) {
      Map validationCandidates = MultiFieldValidationUtils.getMultiFieldValidationCandidates(context, false);
      if (context.isValidationFailed()) {
         return true;
      } else {
         if (!validationCandidates.isEmpty() && validationCandidates.containsKey(wholeBean)) {
            Iterator var4 = ((Map)validationCandidates.get(wholeBean)).entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry wholeBeanPropertyEntry = (Map.Entry)var4.next();
               if (this.isFailedFieldLevelValidation(wholeBeanPropertyEntry)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private Object copyBeanAndPopulateWithCandidateValues(FacesContext context, ValueExpression wholeBeanVE, Object wholeBean, String copierType, Map candidate) {
      Map propertiesToSet = new HashMap();
      Iterator var7 = candidate.entrySet().iterator();

      while(var7.hasNext()) {
         Map.Entry propertyEntry = (Map.Entry)var7.next();
         propertiesToSet.put(propertyEntry.getKey(), ((Map)propertyEntry.getValue()).get("value"));
      }

      Object wholeBeanCopy = CopierUtils.getCopier(context, copierType).copy(wholeBean);
      if (wholeBeanCopy == null) {
         throw new FacesException("Unable to copy bean from " + wholeBeanVE.getExpressionString());
      } else {
         ReflectionUtils.setProperties(wholeBeanCopy, propertiesToSet);
         return wholeBeanCopy;
      }
   }

   private Set doBeanValidation(javax.validation.Validator beanValidator, Object wholeBeanCopy, Class[] validationGroupArray, ValueExpression wholeBeanVE) {
      Set violationsRaw = null;

      try {
         violationsRaw = beanValidator.validate(wholeBeanCopy, validationGroupArray);
      } catch (IllegalArgumentException var7) {
         LOGGER.fine("Unable to validate expression " + wholeBeanVE.getExpressionString() + " using Bean Validation.  Unable to get value of expression.  Message from Bean Validation: " + var7.getMessage());
      }

      return violationsRaw;
   }

   private static class AddRemainingCandidateFieldsCallback implements VisitCallback {
      private final FacesContext context;
      private final Object base;
      private final Map candidate = new HashMap();

      public AddRemainingCandidateFieldsCallback(FacesContext context, Object base) {
         this.context = context;
         this.base = base;
      }

      final Map getCandidate() {
         return this.candidate;
      }

      public VisitResult visit(VisitContext visitContext, UIComponent component) {
         if (component instanceof EditableValueHolder && component.isRendered() && !(component instanceof UIValidateWholeBean)) {
            ValueExpression valueExpression = component.getValueExpression("value");
            if (valueExpression != null) {
               ValueReference valueReference = (new ValueExpressionAnalyzer(valueExpression)).getReference(this.context.getELContext());
               if (valueReference != null && valueReference.getBase().equals(this.base)) {
                  Map tuple = new HashMap();
                  tuple.put("component", component);
                  tuple.put("value", getComponentValue(component));
                  this.candidate.put(valueReference.getProperty(), tuple);
               }
            }
         }

         return VisitResult.ACCEPT;
      }

      private static Object getComponentValue(UIComponent component) {
         UIInput inputComponent = (UIInput)component;
         return inputComponent.getSubmittedValue() != null ? inputComponent.getSubmittedValue() : inputComponent.getLocalValue();
      }
   }
}
