package javax.faces.render;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;

public abstract class ClientBehaviorRenderer {
   public String getScript(ClientBehaviorContext behaviorContext, ClientBehavior behavior) {
      return null;
   }

   public void decode(FacesContext context, UIComponent component, ClientBehavior behavior) {
      if (null == context || null == component || behavior == null) {
         throw new NullPointerException();
      }
   }
}
