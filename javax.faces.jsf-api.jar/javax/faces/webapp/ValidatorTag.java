package javax.faces.webapp;

import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/** @deprecated */
public class ValidatorTag extends TagSupport {
   private static final long serialVersionUID = -5562623615418158868L;
   private String validatorId = null;
   private String binding = null;

   public void setValidatorId(String validatorId) {
      this.validatorId = validatorId;
   }

   public void setBinding(String binding) throws JspException {
      if (binding != null && !UIComponentTag.isValueReference(binding)) {
         throw new JspException("Invalid Expression:" + binding);
      } else {
         this.binding = binding;
      }
   }

   public int doStartTag() throws JspException {
      UIComponentClassicTagBase tag = UIComponentClassicTagBase.getParentUIComponentClassicTagBase(this.pageContext);
      if (tag == null) {
         throw new JspException("Not nested in a UIComponentTag Error for tag with handler class:" + this.getClass().getName());
      } else if (!tag.getCreated()) {
         return 0;
      } else {
         UIComponent component = tag.getComponentInstance();
         if (component == null) {
            throw new JspException("Can't create Component from tag.");
         } else if (!(component instanceof EditableValueHolder)) {
            throw new JspException("Not nested in a tag of proper type. Error for tag with handler class:" + this.getClass().getName());
         } else {
            Validator validator = this.createValidator();
            if (validator == null) {
               String validateError = null;
               if (this.binding != null) {
                  validateError = this.binding;
               }

               if (this.validatorId != null) {
                  if (validateError != null) {
                     validateError = validateError + " or " + this.validatorId;
                  } else {
                     validateError = this.validatorId;
                  }
               }

               throw new JspException("Can't create class of type:javax.faces.validator.Validator from:" + validateError);
            } else {
               ((EditableValueHolder)component).addValidator(validator);
               return 0;
            }
         }
      }
   }

   public void release() {
      this.id = null;
   }

   protected Validator createValidator() throws JspException {
      FacesContext context = FacesContext.getCurrentInstance();
      Validator validator = null;
      ValueExpression vb = null;
      if (this.binding != null) {
         try {
            vb = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), this.binding, Object.class);
            if (vb != null) {
               validator = (Validator)vb.getValue(context.getELContext());
               if (validator != null) {
                  return validator;
               }
            }
         } catch (Exception var7) {
            throw new JspException(var7);
         }
      }

      if (this.validatorId != null) {
         try {
            String validatorIdVal = this.validatorId;
            if (UIComponentTag.isValueReference(this.validatorId)) {
               ValueExpression idBinding = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), this.validatorId, Object.class);
               validatorIdVal = (String)idBinding.getValue(context.getELContext());
            }

            validator = context.getApplication().createValidator(validatorIdVal);
            if (validator != null && vb != null) {
               vb.setValue(context.getELContext(), validator);
            }
         } catch (Exception var6) {
            throw new JspException(var6);
         }
      }

      return validator;
   }
}
