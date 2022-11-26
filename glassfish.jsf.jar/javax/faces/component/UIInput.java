package javax.faces.component;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.MethodBinding;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.PhaseId;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreValidateEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.render.Renderer;
import javax.faces.validator.BeanValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class UIInput extends UIOutput implements EditableValueHolder {
   private static final String BEANS_VALIDATION_AVAILABLE = "javax.faces.private.BEANS_VALIDATION_AVAILABLE";
   public static final String COMPONENT_TYPE = "javax.faces.Input";
   public static final String COMPONENT_FAMILY = "javax.faces.Input";
   public static final String CONVERSION_MESSAGE_ID = "javax.faces.component.UIInput.CONVERSION";
   public static final String REQUIRED_MESSAGE_ID = "javax.faces.component.UIInput.REQUIRED";
   public static final String UPDATE_MESSAGE_ID = "javax.faces.component.UIInput.UPDATE";
   public static final String VALIDATE_EMPTY_FIELDS_PARAM_NAME = "javax.faces.VALIDATE_EMPTY_FIELDS";
   public static final String EMPTY_STRING_AS_NULL_PARAM_NAME = "javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL";
   public static final String ALWAYS_PERFORM_VALIDATION_WHEN_REQUIRED_IS_TRUE = "javax.faces.ALWAYS_PERFORM_VALIDATION_WHEN_REQUIRED_IS_TRUE";
   private static final Validator[] EMPTY_VALIDATOR = new Validator[0];
   private transient Boolean emptyStringIsNull;
   private transient Boolean validateEmptyFields;
   private transient Boolean isSetAlwaysValidateRequired;
   private transient Object submittedValue = null;
   AttachedObjectListHolder validators;

   public UIInput() {
      this.setRendererType("javax.faces.Text");
   }

   public String getFamily() {
      return "javax.faces.Input";
   }

   public Object getSubmittedValue() {
      return this.submittedValue == null && !this.isValid() && this.considerEmptyStringNull(FacesContext.getCurrentInstance()) ? "" : this.submittedValue;
   }

   public void setSubmittedValue(Object submittedValue) {
      this.submittedValue = submittedValue;
   }

   public Object getValue() {
      return this.isLocalValueSet() ? this.getLocalValue() : super.getValue();
   }

   public void setValue(Object value) {
      super.setValue(value);
      this.setLocalValueSet(true);
   }

   public void resetValue() {
      super.resetValue();
      this.setSubmittedValue((Object)null);
      this.getStateHelper().remove(UIInput.PropertyKeys.localValueSet);
      this.getStateHelper().remove(UIInput.PropertyKeys.valid);
   }

   public boolean isLocalValueSet() {
      return (Boolean)this.getStateHelper().eval(UIInput.PropertyKeys.localValueSet, false);
   }

   public void setLocalValueSet(boolean localValueSet) {
      this.getStateHelper().put(UIInput.PropertyKeys.localValueSet, localValueSet);
   }

   public boolean isRequired() {
      return (Boolean)this.getStateHelper().eval(UIInput.PropertyKeys.required, false);
   }

   public String getRequiredMessage() {
      return (String)this.getStateHelper().eval(UIInput.PropertyKeys.requiredMessage);
   }

   public void setRequiredMessage(String message) {
      this.getStateHelper().put(UIInput.PropertyKeys.requiredMessage, message);
   }

   public String getConverterMessage() {
      return (String)this.getStateHelper().eval(UIInput.PropertyKeys.converterMessage);
   }

   public void setConverterMessage(String message) {
      this.getStateHelper().put(UIInput.PropertyKeys.converterMessage, message);
   }

   public String getValidatorMessage() {
      return (String)this.getStateHelper().eval(UIInput.PropertyKeys.validatorMessage);
   }

   public void setValidatorMessage(String message) {
      this.getStateHelper().put(UIInput.PropertyKeys.validatorMessage, message);
   }

   public boolean isValid() {
      return (Boolean)this.getStateHelper().eval(UIInput.PropertyKeys.valid, true);
   }

   public void setValid(boolean valid) {
      this.getStateHelper().put(UIInput.PropertyKeys.valid, valid);
   }

   public void setRequired(boolean required) {
      this.getStateHelper().put(UIInput.PropertyKeys.required, required);
   }

   public boolean isImmediate() {
      return (Boolean)this.getStateHelper().eval(UIInput.PropertyKeys.immediate, false);
   }

   public void setImmediate(boolean immediate) {
      this.getStateHelper().put(UIInput.PropertyKeys.immediate, immediate);
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

   public void markInitialState() {
      super.markInitialState();
      if (this.validators != null) {
         this.validators.markInitialState();
      }

   }

   public void clearInitialState() {
      if (this.initialStateMarked()) {
         super.clearInitialState();
         if (this.validators != null) {
            this.validators.clearInitialState();
         }
      }

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
         this.pushComponentToEL(context, this);
         if (!this.isImmediate()) {
            Application application = context.getApplication();
            application.publishEvent(context, PreValidateEvent.class, this);
            this.executeValidate(context);
            application.publishEvent(context, PostValidateEvent.class, this);
         }

         Iterator i = this.getFacetsAndChildren();

         while(i.hasNext()) {
            ((UIComponent)i.next()).processValidators(context);
         }

         this.popComponentFromEL(context);
      }
   }

   public void processUpdates(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         super.processUpdates(context);
         this.pushComponentToEL(context, this);

         try {
            this.updateModel(context);
         } catch (RuntimeException var6) {
            context.renderResponse();
            throw var6;
         } finally {
            this.popComponentFromEL(context);
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
            Throwable caught = null;
            FacesMessage message = null;

            try {
               ve.setValue(context.getELContext(), this.getLocalValue());
               this.resetValue();
            } catch (ELException var8) {
               caught = var8;
               String messageStr = var8.getMessage();

               for(Throwable result = var8.getCause(); null != result && result.getClass().isAssignableFrom(ELException.class); result = result.getCause()) {
                  messageStr = result.getMessage();
               }

               if (null == messageStr) {
                  message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.UPDATE", MessageFactory.getLabel(context, this));
               } else {
                  message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageStr, messageStr);
               }

               this.setValid(false);
            } catch (Exception var9) {
               caught = var9;
               message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.UPDATE", MessageFactory.getLabel(context, this));
               this.setValid(false);
            }

            if (caught != null) {
               assert message != null;

               UpdateModelException toQueue = new UpdateModelException(message, (Throwable)caught);
               ExceptionQueuedEventContext eventContext = new ExceptionQueuedEventContext(context, toQueue, this, PhaseId.UPDATE_MODEL_VALUES);
               context.getApplication().publishEvent(context, ExceptionQueuedEvent.class, eventContext);
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
            if (this.considerEmptyStringNull(context) && submittedValue instanceof String && ((String)submittedValue).length() == 0) {
               this.setSubmittedValue((Object)null);
               submittedValue = null;
            }

            Object newValue = null;

            try {
               newValue = this.getConvertedValue(context, submittedValue);
            } catch (ConverterException var5) {
               this.addConversionErrorMessage(context, var5);
               this.setValid(false);
               if (submittedValue == null) {
                  this.setSubmittedValue("");
               }
            }

            this.validateValue(context, newValue);
            if (this.isValid()) {
               Object previous = this.getValue();
               this.setValue(newValue);
               this.setSubmittedValue((Object)null);
               if (this.compareValues(previous, newValue)) {
                  this.queueEvent(new ValueChangeEvent(context, this, previous, newValue));
               }
            } else if (submittedValue == null) {
               this.setSubmittedValue("");
            }

         }
      }
   }

   private boolean isSetAlwaysValidateRequired(FacesContext context) {
      if (null != this.isSetAlwaysValidateRequired) {
         return this.isSetAlwaysValidateRequired;
      } else {
         Boolean bool = (Boolean)context.getAttributes().get("javax.faces.ALWAYS_PERFORM_VALIDATION_WHEN_REQUIRED_IS_TRUE");
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

      if (this.isValid() && (!isEmpty(newValue) || this.validateEmptyFields(context)) && this.validators != null) {
         Validator[] validators = (Validator[])this.validators.asArray(Validator.class);
         Validator[] var17 = validators;
         int var5 = validators.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Validator validator = var17[var6];

            try {
               validator.validate(context, this, newValue);
            } catch (ValidatorException var15) {
               this.setValid(false);
               String validatorMessageString = this.getValidatorMessage();
               FacesMessage message;
               if (null != validatorMessageString) {
                  message = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessageString, validatorMessageString);
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
               } else {
                  Collection messages = var15.getFacesMessages();
                  if (null != messages) {
                     message = null;
                     String cid = this.getClientId(context);
                     Iterator var13 = messages.iterator();

                     while(var13.hasNext()) {
                        FacesMessage m = (FacesMessage)var13.next();
                        context.addMessage(cid, m);
                     }
                  } else {
                     message = var15.getFacesMessage();
                  }
               }

               if (message != null) {
                  context.addMessage(this.getClientId(context), message);
               }
            }
         }
      }

   }

   protected boolean compareValues(Object previous, Object value) {
      boolean result = true;
      if (previous == null) {
         result = value != null;
      } else if (value == null) {
         result = true;
      } else {
         boolean previousEqualsValue = previous.equals(value);
         if (!previousEqualsValue && previous instanceof Comparable && value instanceof Comparable) {
            try {
               result = 0 != ((Comparable)previous).compareTo((Comparable)value);
            } catch (ClassCastException var6) {
               result = true;
            }
         } else {
            result = !previousEqualsValue;
         }
      }

      return result;
   }

   private void executeValidate(FacesContext context) {
      try {
         this.validate(context);
      } catch (RuntimeException var3) {
         context.renderResponse();
         throw var3;
      }

      if (!this.isValid()) {
         context.validationFailed();
         context.renderResponse();
      }

   }

   public static boolean isEmpty(Object value) {
      if (value == null) {
         return true;
      } else if (value instanceof String && ((String)value).length() < 1) {
         return true;
      } else {
         if (value.getClass().isArray()) {
            if (0 == Array.getLength(value)) {
               return true;
            }
         } else if (value instanceof List) {
            if (((List)value).isEmpty()) {
               return true;
            }
         } else if (value instanceof Collection) {
            if (((Collection)value).isEmpty()) {
               return true;
            }
         } else if (value instanceof Map && ((Map)value).isEmpty()) {
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
            this.validators = new AttachedObjectListHolder();
         }

         this.validators.add(validator);
      }
   }

   public Validator[] getValidators() {
      return this.validators != null ? (Validator[])this.validators.asArray(Validator.class) : EMPTY_VALIDATOR;
   }

   public void removeValidator(Validator validator) {
      if (validator != null) {
         if (this.validators != null) {
            this.validators.remove(validator);
         }

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
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object[] result = null;
         Object superState = super.saveState(context);
         Object validatorsState = this.validators != null ? this.validators.saveState(context) : null;
         if (superState != null || validatorsState != null) {
            result = new Object[]{superState, validatorsState};
         }

         return result;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         Object[] values = (Object[])((Object[])state);
         super.restoreState(context, values[0]);
         if (values[1] != null) {
            if (this.validators == null) {
               this.validators = new AttachedObjectListHolder();
            }

            this.validators.restoreState(context, values[1]);
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

   private void addConversionErrorMessage(FacesContext context, ConverterException ce) {
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

   private boolean considerEmptyStringNull(FacesContext ctx) {
      if (this.emptyStringIsNull == null) {
         String val = ctx.getExternalContext().getInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL");
         this.emptyStringIsNull = Boolean.valueOf(val);
      }

      return this.emptyStringIsNull;
   }

   private boolean validateEmptyFields(FacesContext ctx) {
      if (this.validateEmptyFields == null) {
         ExternalContext extCtx = ctx.getExternalContext();
         String val = extCtx.getInitParameter("javax.faces.VALIDATE_EMPTY_FIELDS");
         if (null == val) {
            val = (String)extCtx.getApplicationMap().get("javax.faces.VALIDATE_EMPTY_FIELDS");
         }

         if (val != null && !"auto".equals(val)) {
            this.validateEmptyFields = Boolean.valueOf(val);
         } else {
            this.validateEmptyFields = this.isBeansValidationAvailable(ctx);
         }
      }

      return this.validateEmptyFields;
   }

   private boolean isBeansValidationAvailable(FacesContext context) {
      boolean result = false;
      Map appMap = context.getExternalContext().getApplicationMap();
      if (appMap.containsKey("javax.faces.private.BEANS_VALIDATION_AVAILABLE")) {
         result = (Boolean)appMap.get("javax.faces.private.BEANS_VALIDATION_AVAILABLE");
      } else {
         try {
            new BeanValidator();
            result = true;
            appMap.put("javax.faces.private.BEANS_VALIDATION_AVAILABLE", true);
         } catch (Throwable var5) {
            appMap.put("javax.faces.private.BEANS_VALIDATION_AVAILABLE", Boolean.FALSE);
         }
      }

      return result;
   }

   static enum PropertyKeys {
      localValueSet,
      required,
      requiredMessage,
      converterMessage,
      validatorMessage,
      valid,
      immediate;
   }
}
