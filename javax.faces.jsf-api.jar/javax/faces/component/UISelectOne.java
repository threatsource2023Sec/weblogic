package javax.faces.component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class UISelectOne extends UIInput {
   public static final String COMPONENT_TYPE = "javax.faces.SelectOne";
   public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";
   public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectOne.INVALID";

   public UISelectOne() {
      this.setRendererType("javax.faces.Menu");
   }

   public String getFamily() {
      return "javax.faces.SelectOne";
   }

   protected void validateValue(FacesContext context, Object value) {
      super.validateValue(context, value);
      if (this.isValid() && value != null) {
         boolean found = SelectUtils.matchValue(this.getFacesContext(), this, value, new SelectItemsIterator(this), this.getConverter());
         if (!found) {
            FacesMessage message = MessageFactory.getMessage(context, "javax.faces.component.UISelectOne.INVALID", MessageFactory.getLabel(context, this));
            context.addMessage(this.getClientId(context), message);
            this.setValid(false);
         }

      }
   }
}
