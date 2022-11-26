package javax.faces.validator;

import java.util.EventListener;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface Validator extends EventListener {
   /** @deprecated */
   String NOT_IN_RANGE_MESSAGE_ID = "javax.faces.validator.NOT_IN_RANGE";

   void validate(FacesContext var1, UIComponent var2, Object var3) throws ValidatorException;
}
