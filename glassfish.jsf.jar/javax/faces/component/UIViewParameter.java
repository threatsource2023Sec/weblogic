package javax.faces.component;

import java.io.IOException;
import java.util.Iterator;
import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.faces.validator.RequiredValidator;

public class UIViewParameter extends UIInput {
   public static final String COMPONENT_TYPE = "javax.faces.ViewParameter";
   public static final String COMPONENT_FAMILY = "javax.faces.ViewParameter";
   private Renderer inputTextRenderer = null;
   private transient Boolean emptyStringIsNull;
   private String rawValue;

   public UIViewParameter() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.ViewParameter";
   }

   public String getName() {
      return (String)this.getStateHelper().eval(UIViewParameter.PropertyKeys.name);
   }

   public void setName(String name) {
      this.getStateHelper().put(UIViewParameter.PropertyKeys.name, name);
   }

   public boolean isImmediate() {
      return false;
   }

   public Object getSubmittedValue() {
      return this.getStateHelper().get(UIViewParameter.PropertyKeys.submittedValue);
   }

   public void setSubmittedValue(Object submittedValue) {
      this.getStateHelper().put(UIViewParameter.PropertyKeys.submittedValue, submittedValue);
   }

   public void decode(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         String paramValue = (String)context.getExternalContext().getRequestParameterMap().get(this.getName());
         if (paramValue != null) {
            this.setSubmittedValue(paramValue);
         }

         this.rawValue = (String)this.getSubmittedValue();
         this.setValid(true);
      }
   }

   public void processValidators(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         Object submittedValue = this.getSubmittedValue();
         if (submittedValue == null && this.myIsRequired()) {
            String requiredMessageStr = this.getRequiredMessage();
            FacesMessage message;
            if (null != requiredMessageStr) {
               message = new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessageStr, requiredMessageStr);
            } else {
               message = MessageFactory.getMessage(context, "javax.faces.component.UIInput.REQUIRED", MessageFactory.getLabel(context, this));
            }

            context.addMessage(this.getClientId(context), message);
            this.setValid(false);
            context.validationFailed();
            context.renderResponse();
         } else {
            if (this.myConsiderEmptyStringNull(context) && null == submittedValue) {
               this.setSubmittedValue("");
            }

            super.processValidators(context);
         }

      }
   }

   private boolean myConsiderEmptyStringNull(FacesContext ctx) {
      if (this.emptyStringIsNull == null) {
         String val = ctx.getExternalContext().getInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL");
         this.emptyStringIsNull = Boolean.valueOf(val);
      }

      return this.emptyStringIsNull;
   }

   private boolean myIsRequired() {
      return super.isRequired() || this.isRequiredViaNestedRequiredValidator();
   }

   private boolean isRequiredViaNestedRequiredValidator() {
      boolean result = false;
      if (null == this.validators) {
         return result;
      } else {
         Iterator iter = this.validators.iterator();

         while(iter.hasNext()) {
            if (iter.next() instanceof RequiredValidator) {
               result = true;
               Object submittedValue = this.getSubmittedValue();
               if (submittedValue == null) {
                  this.setSubmittedValue("");
               }
               break;
            }
         }

         return result;
      }
   }

   public void updateModel(FacesContext context) {
      super.updateModel(context);
      if (!this.hasValueExpression() && this.isValid() && this.isLocalValueSet()) {
         context.getExternalContext().getRequestMap().put(this.getName(), this.getLocalValue());
      }

   }

   public void encodeAll(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else {
         this.setSubmittedValue(this.getStringValue(context));
      }
   }

   public String getStringValue(FacesContext context) {
      String result = null;
      if (this.hasValueExpression()) {
         result = this.getStringValueFromModel(context);
      } else {
         result = null != this.rawValue ? this.rawValue : (String)this.getValue();
      }

      return result;
   }

   public String getStringValueFromModel(FacesContext context) throws ConverterException {
      ValueExpression ve = this.getValueExpression("value");
      if (ve == null) {
         return null;
      } else {
         Object currentValue = ve.getValue(context.getELContext());
         Converter c = this.getConverter();
         if (c == null) {
            if (currentValue == null) {
               return null;
            }

            if (currentValue instanceof String) {
               return (String)currentValue;
            }

            Class converterType = currentValue.getClass();
            c = context.getApplication().createConverter(converterType);
            if (c == null) {
               return currentValue.toString();
            }
         }

         return c.getAsString(context, this, currentValue);
      }
   }

   protected Object getConvertedValue(FacesContext context, Object submittedValue) throws ConverterException {
      return this.getInputTextRenderer(context).getConvertedValue(context, this, submittedValue);
   }

   private Renderer getInputTextRenderer(FacesContext context) {
      if (null == this.inputTextRenderer) {
         RenderKitFactory rkf = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");
         RenderKit standard = rkf.getRenderKit(context, "HTML_BASIC");
         this.inputTextRenderer = standard.getRenderer("javax.faces.Input", "javax.faces.Text");
      }

      assert null != this.inputTextRenderer;

      return this.inputTextRenderer;
   }

   private boolean hasValueExpression() {
      return null != this.getValueExpression("value");
   }

   public static class Reference {
      private StateHolderSaver saver;
      private int indexInParent;
      private String viewIdAtTimeOfConstruction;

      public Reference(FacesContext context, UIViewParameter param, int indexInParent, String viewIdAtTimeOfConstruction) {
         this.saver = new StateHolderSaver(context, param);
         this.indexInParent = indexInParent;
         this.viewIdAtTimeOfConstruction = viewIdAtTimeOfConstruction;
      }

      public UIViewParameter getUIViewParameter(FacesContext context) {
         UIViewParameter result = null;
         UIViewRoot root = context.getViewRoot();
         if (this.viewIdAtTimeOfConstruction.equals(root.getViewId())) {
            UIComponent metadataFacet = root.getFacet("javax_faces_metadata");
            result = (UIViewParameter)metadataFacet.getChildren().get(this.indexInParent);
         } else {
            result = (UIViewParameter)this.saver.restore(context);
         }

         return result;
      }
   }

   static enum PropertyKeys {
      name,
      submittedValue;
   }
}
