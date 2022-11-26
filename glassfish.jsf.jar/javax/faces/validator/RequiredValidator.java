package javax.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

public class RequiredValidator implements Validator {
   public static final String VALIDATOR_ID = "javax.faces.Required";

   public void validate(FacesContext context, UIComponent component, Object value) {
      if (UIInput.isEmpty(value)) {
         String requiredMessageStr = null;
         if (component instanceof UIInput) {
            requiredMessageStr = ((UIInput)component).getRequiredMessage();
         }

         FacesMessage msg;
         if (requiredMessageStr != null) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessageStr, requiredMessageStr);
         } else {
            msg = MessageFactory.getMessage(context, "javax.faces.component.UIInput.REQUIRED", MessageFactory.getLabel(context, component));
         }

         throw new ValidatorException(msg);
      }
   }
}
