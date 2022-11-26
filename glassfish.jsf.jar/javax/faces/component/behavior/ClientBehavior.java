package javax.faces.component.behavior;

import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface ClientBehavior extends Behavior {
   String getScript(ClientBehaviorContext var1);

   void decode(FacesContext var1, UIComponent var2);

   Set getHints();
}
