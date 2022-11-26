package javax.faces.webapp;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class ValidatorELTag extends TagSupport {
   private static final long serialVersionUID = -4373376368829344328L;

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
               throw new JspException("Can't create class of type: javax.faces.validator.Validator.  Validator is null");
            } else {
               ((EditableValueHolder)component).addValidator(validator);
               return 0;
            }
         }
      }
   }

   protected abstract Validator createValidator() throws JspException;
}
