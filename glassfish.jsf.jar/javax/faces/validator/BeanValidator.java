package javax.faces.validator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

public class BeanValidator implements Validator, PartialStateHolder {
   private static final Logger LOGGER = Logger.getLogger("javax.faces.validator", "javax.faces.LogStrings");
   private String validationGroups;
   private transient Class[] cachedValidationGroups;
   public static final String VALIDATOR_ID = "javax.faces.Bean";
   public static final String MESSAGE_ID = "javax.faces.validator.BeanValidator.MESSAGE";
   public static final String VALIDATOR_FACTORY_KEY = "javax.faces.validator.beanValidator.ValidatorFactory";
   public static final String VALIDATION_GROUPS_DELIMITER = ",";
   public static final String EMPTY_VALIDATION_GROUPS_PATTERN = "^[\\W,]*$";
   public static final String DISABLE_DEFAULT_BEAN_VALIDATOR_PARAM_NAME = "javax.faces.validator.DISABLE_DEFAULT_BEAN_VALIDATOR";
   public static final String ENABLE_VALIDATE_WHOLE_BEAN_PARAM_NAME = "javax.faces.validator.ENABLE_VALIDATE_WHOLE_BEAN";
   private boolean initialState;
   private boolean transientValue;

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

   public void validate(FacesContext context, UIComponent component, Object value) {
      if (context == null) {
         throw new NullPointerException();
      } else if (component == null) {
         throw new NullPointerException();
      } else {
         ValueExpression valueExpression = component.getValueExpression("value");
         if (valueExpression != null) {
            javax.validation.Validator beanValidator = getBeanValidator(context);
            Class[] validationGroupsArray = this.parseValidationGroups(this.getValidationGroups());
            ValueExpressionAnalyzer expressionAnalyzer = new ValueExpressionAnalyzer(valueExpression);
            ValueReference valueReference = expressionAnalyzer.getReference(context.getELContext());
            if (valueReference != null) {
               if (this.isResolvable(valueReference, valueExpression)) {
                  Set violationsRaw = null;

                  try {
                     violationsRaw = beanValidator.validateValue(valueReference.getBaseClass(), valueReference.getProperty(), value, validationGroupsArray);
                  } catch (IllegalArgumentException var15) {
                     LOGGER.fine("Unable to validate expression " + valueExpression.getExpressionString() + " using Bean Validation.  Unable to get value of expression.  Message from Bean Validation: " + var15.getMessage());
                  }

                  if (violationsRaw != null && !violationsRaw.isEmpty()) {
                     ValidatorException toThrow;
                     if (violationsRaw.size() == 1) {
                        ConstraintViolation violation = (ConstraintViolation)violationsRaw.iterator().next();
                        toThrow = new ValidatorException(MessageFactory.getMessage(context, "javax.faces.validator.BeanValidator.MESSAGE", violation.getMessage(), MessageFactory.getLabel(context, component)));
                     } else {
                        Set messages = new LinkedHashSet(violationsRaw.size());
                        Iterator var13 = violationsRaw.iterator();

                        while(var13.hasNext()) {
                           ConstraintViolation violation = (ConstraintViolation)var13.next();
                           messages.add(MessageFactory.getMessage(context, "javax.faces.validator.BeanValidator.MESSAGE", violation.getMessage(), MessageFactory.getLabel(context, component)));
                        }

                        toThrow = new ValidatorException(messages);
                     }

                     if (MultiFieldValidationUtils.wholeBeanValidationEnabled(context, validationGroupsArray)) {
                        this.recordValidationResult(context, component, valueReference.getBase(), valueReference.getProperty(), "javax.faces.Bean.FAILED_FIELD_LEVEL_VALIDATION");
                     }

                     throw toThrow;
                  }
               }

               if (MultiFieldValidationUtils.wholeBeanValidationEnabled(context, validationGroupsArray)) {
                  this.recordValidationResult(context, component, valueReference.getBase(), valueReference.getProperty(), value);
               }

            }
         }
      }
   }

   private boolean isResolvable(ValueReference valueReference, ValueExpression valueExpression) {
      Boolean resolvable = null;
      String failureMessage = null;
      if (valueExpression == null) {
         failureMessage = "Unable to validate expression using Bean Validation.  Expression must not be null.";
         resolvable = false;
      } else if (valueReference == null) {
         failureMessage = "Unable to validate expression " + valueExpression.getExpressionString() + " using Bean Validation.  Unable to get value of expression.";
         resolvable = false;
      } else {
         Class baseClass = valueReference.getBaseClass();
         if (baseClass != null && (Map.class.isAssignableFrom(baseClass) || Collection.class.isAssignableFrom(baseClass) || Array.class.isAssignableFrom(baseClass))) {
            failureMessage = "Unable to validate expression " + valueExpression.getExpressionString() + " using Bean Validation.  Expression evaluates to a Map, List or array.";
            resolvable = false;
         }
      }

      resolvable = null != resolvable ? resolvable : true;
      if (!resolvable) {
         LOGGER.fine(failureMessage);
      }

      return resolvable;
   }

   private Class[] parseValidationGroups(String validationGroupsStr) {
      if (this.cachedValidationGroups != null) {
         return this.cachedValidationGroups;
      } else if (validationGroupsStr == null) {
         this.cachedValidationGroups = new Class[]{Default.class};
         return this.cachedValidationGroups;
      } else {
         List validationGroupsList = new ArrayList();
         String[] classNames = validationGroupsStr.split(",");
         String[] var4 = classNames;
         int var5 = classNames.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String className = var4[var6];
            className = className.trim();
            if (className.length() != 0) {
               if (className.equals(Default.class.getName())) {
                  validationGroupsList.add(Default.class);
               } else {
                  try {
                     validationGroupsList.add(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
                  } catch (ClassNotFoundException var11) {
                     try {
                        validationGroupsList.add(Class.forName(className));
                     } catch (ClassNotFoundException var10) {
                        throw new FacesException("Validation group not found: " + className);
                     }
                  }
               }
            }
         }

         this.cachedValidationGroups = (Class[])validationGroupsList.toArray(new Class[validationGroupsList.size()]);
         return this.cachedValidationGroups;
      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (!this.initialStateMarked()) {
         Object[] values = new Object[]{this.validationGroups};
         return values;
      } else {
         return null;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (state != null) {
            Object[] values = (Object[])((Object[])state);
            this.validationGroups = (String)values[0];
         }

      }
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

   private static javax.validation.Validator getBeanValidator(FacesContext context) {
      ValidatorFactory validatorFactory = getValidatorFactory(context);
      ValidatorContext validatorContext = validatorFactory.usingContext();
      MessageInterpolator jsfMessageInterpolator = new JsfAwareMessageInterpolator(context, validatorFactory.getMessageInterpolator());
      validatorContext.messageInterpolator(jsfMessageInterpolator);
      return validatorContext.getValidator();
   }

   private static ValidatorFactory getValidatorFactory(FacesContext context) {
      ValidatorFactory validatorFactory = null;
      Object cachedObject = context.getExternalContext().getApplicationMap().get("javax.faces.validator.beanValidator.ValidatorFactory");
      if (cachedObject instanceof ValidatorFactory) {
         validatorFactory = (ValidatorFactory)cachedObject;
      } else {
         try {
            validatorFactory = Validation.buildDefaultValidatorFactory();
         } catch (ValidationException var4) {
            throw new FacesException("Could not build a default Bean Validator factory", var4);
         }

         context.getExternalContext().getApplicationMap().put("javax.faces.validator.beanValidator.ValidatorFactory", validatorFactory);
      }

      return validatorFactory;
   }

   private void recordValidationResult(FacesContext context, UIComponent component, Object wholeBean, String propertyName, Object propertyValue) {
      Map multiFieldCandidates = MultiFieldValidationUtils.getMultiFieldValidationCandidates(context, true);
      Map candidate = (Map)multiFieldCandidates.getOrDefault(wholeBean, new HashMap());
      Map tuple = new HashMap();
      tuple.put("component", component);
      tuple.put("value", propertyValue);
      candidate.put(propertyName, tuple);
      multiFieldCandidates.putIfAbsent(wholeBean, candidate);
   }

   private static class JsfAwareMessageInterpolator implements MessageInterpolator {
      private FacesContext context;
      private MessageInterpolator delegate;

      public JsfAwareMessageInterpolator(FacesContext context, MessageInterpolator delegate) {
         this.context = context;
         this.delegate = delegate;
      }

      public String interpolate(String message, MessageInterpolator.Context context) {
         Locale locale = this.context.getViewRoot().getLocale();
         if (locale == null) {
            locale = Locale.getDefault();
         }

         return this.delegate.interpolate(message, context, locale);
      }

      public String interpolate(String message, MessageInterpolator.Context context, Locale locale) {
         return this.delegate.interpolate(message, context, locale);
      }
   }
}
