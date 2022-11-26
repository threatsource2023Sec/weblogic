package javax.faces.component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.MethodBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class UIInput extends UIOutput implements EditableValueHolder {
   public static final String COMPONENT_TYPE = "javax.faces.Input";
   public static final String COMPONENT_FAMILY = "javax.faces.Input";
   public static final String CONVERSION_MESSAGE_ID = "javax.faces.component.UIInput.CONVERSION";
   public static final String REQUIRED_MESSAGE_ID = "javax.faces.component.UIInput.REQUIRED";
   public static final String UPDATE_MESSAGE_ID = "javax.faces.component.UIInput.UPDATE";
   private static final Validator[] EMPTY_VALIDATOR = new Validator[0];
   private transient Boolean isSetAlwaysValidateRequired;
   private static final Logger LOGGER = Logger.getLogger("javax.faces.component", "javax.faces.LogStrings");
   private Object submittedValue = null;
   private boolean localValueSet;
   private Boolean required;
   private String requiredMessage;
   private String converterMessage;
   private String validatorMessage;
   private boolean valid = true;
   private Boolean immediate;
   List validators = null;
   private Object[] values;

   public UIInput() {
      this.setRendererType("javax.faces.Text");
   }

   public String getFamily() {
      return "javax.faces.Input";
   }

   public Object getSubmittedValue() {
      return this.submittedValue;
   }

   public void setSubmittedValue(Object submittedValue) {
      this.submittedValue = submittedValue;
   }

   public void setValue(Object value) {
      super.setValue(value);
      this.setLocalValueSet(true);
   }

   public void resetValue() {
      this.setValue((Object)null);
      this.setSubmittedValue((Object)null);
      this.setLocalValueSet(false);
      this.setValid(true);
   }

   public boolean isLocalValueSet() {
      return this.localValueSet;
   }

   public void setLocalValueSet(boolean localValueSet) {
      this.localValueSet = localValueSet;
   }

   public boolean isRequired() {
      if (this.required != null) {
         return this.required;
      } else {
         ValueExpression ve = this.getValueExpression("required");
         if (ve != null) {
            try {
               return Boolean.TRUE.equals(ve.getValue(this.getFacesContext().getELContext()));
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return false;
         }
      }
   }

   public String getRequiredMessage() {
      if (this.requiredMessage != null) {
         return this.requiredMessage;
      } else {
         ValueExpression ve = this.getValueExpression("requiredMessage");
         if (ve != null) {
            try {
               return (String)ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setRequiredMessage(String message) {
      this.requiredMessage = message;
   }

   public String getConverterMessage() {
      if (this.converterMessage != null) {
         return this.converterMessage;
      } else {
         ValueExpression ve = this.getValueExpression("converterMessage");
         if (ve != null) {
            try {
               return (String)ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setConverterMessage(String message) {
      this.converterMessage = message;
   }

   public String getValidatorMessage() {
      if (this.validatorMessage != null) {
         return this.validatorMessage;
      } else {
         ValueExpression ve = this.getValueExpression("validatorMessage");
         if (ve != null) {
            try {
               return (String)ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setValidatorMessage(String message) {
      this.validatorMessage = message;
   }

   public boolean isValid() {
      return this.valid;
   }

   public void setValid(boolean valid) {
      this.valid = valid;
   }

   public void setRequired(boolean required) {
      this.required = required;
   }

   public boolean isImmediate() {
      if (this.immediate != null) {
         return this.immediate;
      } else {
         ValueExpression ve = this.getValueExpression("immediate");
         if (ve != null) {
            try {
               return Boolean.TRUE.equals(ve.getValue(this.getFacesContext().getELContext()));
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return false;
         }
      }
   }

   public void setImmediate(boolean immediate) {
      this.immediate = immediate;
   }

   /** @deprecated */
   public MethodBinding getValidator() {
      MethodBinding result = null;
      Validator[] curValidators = this.getValidators();
      if (null != curValidators) {
         for(int i = 0; i < curValidators.length; ++i) {
            if (MethodBindingValidator.class == curValidators[i].getClass()) {
               result = ((MethodBindingValidator)curValidators[i]).getWrapped();
               break;
            }
         }
      }

      return result;
   }

   /** @deprecated */
   public void setValidator(MethodBinding validatorBinding) {
      Validator[] curValidators = this.getValidators();
      if (null != curValidators) {
         for(int i = 0; i < curValidators.length; ++i) {
            if (null == validatorBinding) {
               if (MethodBindingValidator.class == curValidators[i].getClass()) {
                  this.removeValidator(curValidators[i]);
                  return;
               }
            } else if (validatorBinding == curValidators[i]) {
               this.removeValidator(curValidators[i]);
               break;
            }
         }
      }

      this.addValidator(new MethodBindingValidator(validatorBinding));
   }

   public MethodBinding getValueChangeListener() {
      MethodBinding result = null;
      ValueChangeListener[] curListeners = this.getValueChangeListeners();
      if (null != curListeners) {
         for(int i = 0; i < curListeners.length; ++i) {
            if (MethodBindingValueChangeListener.class == curListeners[i].getClass()) {
               result = ((MethodBindingValueChangeListener)curListeners[i]).getWrapped();
               break;
            }
         }
      }

      return result;
   }

   /** @deprecated */
   public void setValueChangeListener(MethodBinding valueChangeListener) {
      ValueChangeListener[] curListeners = this.getValueChangeListeners();
      if (null != curListeners) {
         for(int i = 0; i < curListeners.length; ++i) {
            if (null == valueChangeListener) {
               if (MethodBindingValueChangeListener.class == curListeners[i].getClass()) {
                  this.removeFacesListener(curListeners[i]);
                  return;
               }
            } else if (valueChangeListener == curListeners[i]) {
               this.removeFacesListener(curListeners[i]);
               break;
            }
         }
      }

      this.addValueChangeListener(new MethodBindingValueChangeListener(valueChangeListener));
   }

   public void processDecodes(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         super.processDecodes(context);
         if (this.isImmediate()) {
            this.executeValidate(context);
         }

      }
   }

   public void processValidators(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         super.processValidators(context);
         if (!this.isImmediate()) {
            this.executeValidate(context);
         }

      }
   }

   public void processUpdates(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         super.processUpdates(context);

         try {
            this.updateModel(context);
         } catch (RuntimeException var3) {
            context.renderResponse();
            throw var3;
         }

         if (!this.isValid()) {
            context.renderResponse();
         }

      }
   }

   public void decode(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         this.setValid(true);
         super.decode(context);
      }
   }

   public void updateModel(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isValid() && this.isLocalValueSet()) {
         ValueExpression ve = this.getValueExpression("value");
         if (ve != null) {
            FacesMessage message;
            try {
               ve.setValue(context.getELContext(), this.getLocalValue());
               this.setValue((Object)null);
               this.setLocalValueSet(false);
            } catch (ELException var7) {
               String messageStr = var7.getMessage();

               Throwable result;
               for(result = var7.getCause(); null != result && result.getClass().isAssignableFrom(ELException.class); result = result.getCause()) {
                  messageStr = result.getMessage();
               }

               FacesMessage message;
               if (null == messageStr) {
                  message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.UPDATE", MessageFactory.getLabel(context, this));
               } else {
                  message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageStr, messageStr);
               }

               LOGGER.log(Level.SEVERE, message.getSummary(), result);
               context.addMessage(this.getClientId(context), message);
               this.setValid(false);
            } catch (IllegalArgumentException var8) {
               message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.UPDATE", MessageFactory.getLabel(context, this));
               LOGGER.log(Level.SEVERE, message.getSummary(), var8);
               context.addMessage(this.getClientId(context), message);
               this.setValid(false);
            } catch (Exception var9) {
               message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.UPDATE", MessageFactory.getLabel(context, this));
               LOGGER.log(Level.SEVERE, message.getSummary(), var9);
               context.addMessage(this.getClientId(context), message);
               this.setValid(false);
            }
         }

      }
   }

   public void validate(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object submittedValue = this.getSubmittedValue();
         if (submittedValue != null || this.isRequired() && this.isSetAlwaysValidateRequired(context)) {
            Object newValue = null;

            try {
               newValue = this.getConvertedValue(context, submittedValue);
            } catch (ConverterException var5) {
               this.addConversionErrorMessage(context, var5, submittedValue);
               this.setValid(false);
            }

            this.validateValue(context, newValue);
            if (this.isValid()) {
               Object previous = this.getValue();
               this.setValue(newValue);
               this.setSubmittedValue((Object)null);
               if (this.compareValues(previous, newValue)) {
                  this.queueEvent(new ValueChangeEvent(this, previous, newValue));
               }
            }

         }
      }
   }

   private boolean isSetAlwaysValidateRequired(FacesContext context) {
      if (null != this.isSetAlwaysValidateRequired) {
         return this.isSetAlwaysValidateRequired;
      } else {
         Boolean bool = (Boolean)context.getExternalContext().getRequestMap().get("javax.faces.ALWAYS_PERFORM_VALIDATION_WHEN_REQUIRED_IS_TRUE");
         if (null != bool) {
            this.isSetAlwaysValidateRequired = bool;
         } else {
            String val = context.getExternalContext().getInitParameter("javax.faces.ALWAYS_PERFORM_VALIDATION_WHEN_REQUIRED_IS_TRUE");
            this.isSetAlwaysValidateRequired = Boolean.valueOf(val);
         }

         return this.isSetAlwaysValidateRequired;
      }
   }

   protected Object getConvertedValue(FacesContext context, Object newSubmittedValue) throws ConverterException {
      Renderer renderer = this.getRenderer(context);
      Object newValue;
      if (renderer != null) {
         newValue = renderer.getConvertedValue(context, this, newSubmittedValue);
      } else if (newSubmittedValue instanceof String) {
         Converter converter = this.getConverterWithType(context);
         if (converter != null) {
            newValue = converter.getAsObject(context, this, (String)newSubmittedValue);
         } else {
            newValue = newSubmittedValue;
         }
      } else {
         newValue = newSubmittedValue;
      }

      return newValue;
   }

   protected void validateValue(FacesContext context, Object newValue) {
      if (this.isValid() && this.isRequired() && isEmpty(newValue)) {
         String requiredMessageStr = this.getRequiredMessage();
         FacesMessage message;
         if (null != requiredMessageStr) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessageStr, requiredMessageStr);
         } else {
            message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.REQUIRED", MessageFactory.getLabel(context, this));
         }

         context.addMessage(this.getClientId(context), message);
         this.setValid(false);
      }

      if (this.isValid() && !isEmpty(newValue) && this.validators != null) {
         Iterator var9 = this.validators.iterator();

         while(var9.hasNext()) {
            Validator validator = (Validator)var9.next();

            try {
               validator.validate(context, this, newValue);
            } catch (ValidatorException var8) {
               this.setValid(false);
               String validatorMessageString = this.getValidatorMessage();
               FacesMessage message;
               if (null != validatorMessageString) {
                  message = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessageString, validatorMessageString);
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
               } else {
                  message = var8.getFacesMessage();
               }

               if (message != null) {
                  context.addMessage(this.getClientId(context), message);
               }
            }
         }
      }

   }

   protected boolean compareValues(Object previous, Object value) {
      if (previous == null) {
         return value != null;
      } else if (value == null) {
         return true;
      } else {
         return !previous.equals(value);
      }
   }

   private void executeValidate(FacesContext context) {
      try {
         this.validate(context);
      } catch (RuntimeException var3) {
         context.renderResponse();
         throw var3;
      }

      if (!this.isValid()) {
         context.renderResponse();
      }

   }

   private static boolean isEmpty(Object value) {
      if (value == null) {
         return true;
      } else if (value instanceof String && ((String)value).length() < 1) {
         return true;
      } else {
         if (value.getClass().isArray()) {
            if (0 == Array.getLength(value)) {
               return true;
            }
         } else if (value instanceof List && ((List)value).isEmpty()) {
            return true;
         }

         return false;
      }
   }

   public void addValidator(Validator validator) {
      if (validator == null) {
         throw new NullPointerException();
      } else {
         if (this.validators == null) {
            this.validators = new ArrayList();
         }

         this.validators.add(validator);
      }
   }

   public Validator[] getValidators() {
      return this.validators == null ? EMPTY_VALIDATOR : (Validator[])this.validators.toArray(new Validator[this.validators.size()]);
   }

   public void removeValidator(Validator validator) {
      if (this.validators != null) {
         this.validators.remove(validator);
      }

   }

   public void addValueChangeListener(ValueChangeListener listener) {
      this.addFacesListener(listener);
   }

   public ValueChangeListener[] getValueChangeListeners() {
      return (ValueChangeListener[])((ValueChangeListener[])this.getFacesListeners(ValueChangeListener.class));
   }

   public void removeValueChangeListener(ValueChangeListener listener) {
      this.removeFacesListener(listener);
   }

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[9];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.localValueSet;
      this.values[2] = this.required;
      this.values[3] = this.requiredMessage;
      this.values[4] = this.converterMessage;
      this.values[5] = this.validatorMessage;
      this.values[6] = this.valid;
      this.values[7] = this.immediate;
      this.values[8] = saveAttachedState(context, this.validators);
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.localValueSet = (Boolean)this.values[1];
      this.required = (Boolean)this.values[2];
      this.requiredMessage = (String)this.values[3];
      this.converterMessage = (String)this.values[4];
      this.validatorMessage = (String)this.values[5];
      this.valid = (Boolean)this.values[6];
      this.immediate = (Boolean)this.values[7];
      List restoredValidators;
      if (null != (restoredValidators = TypedCollections.dynamicallyCastList((List)restoreAttachedState(context, this.values[8]), Validator.class))) {
         if (null != this.validators) {
            Iterator iter = restoredValidators.iterator();

            while(iter.hasNext()) {
               this.validators.add(iter.next());
            }
         } else {
            this.validators = restoredValidators;
         }
      }

   }

   private Converter getConverterWithType(FacesContext context) {
      Converter converter = this.getConverter();
      if (converter != null) {
         return converter;
      } else {
         ValueExpression valueExpression = this.getValueExpression("value");
         if (valueExpression == null) {
            return null;
         } else {
            Class converterType;
            try {
               converterType = valueExpression.getType(context.getELContext());
            } catch (ELException var7) {
               throw new FacesException(var7);
            }

            if (converterType != null && converterType != String.class && converterType != Object.class) {
               try {
                  Application application = context.getApplication();
                  return application.createConverter(converterType);
               } catch (Exception var6) {
                  return null;
               }
            } else {
               return null;
            }
         }
      }
   }

   private void addConversionErrorMessage(FacesContext context, ConverterException ce, Object value) {
      String converterMessageString = this.getConverterMessage();
      FacesMessage message;
      if (null != converterMessageString) {
         message = new FacesMessage(FacesMessage.SEVERITY_ERROR, converterMessageString, converterMessageString);
      } else {
         message = ce.getFacesMessage();
         if (message == null) {
            message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.CONVERSION");
            if (message.getDetail() == null) {
               message.setDetail(ce.getMessage());
            }
         }
      }

      context.addMessage(this.getClientId(context), message);
   }
}
