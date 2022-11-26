package javax.faces.view;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class StateManagementStrategy {
   public abstract Object saveView(FacesContext var1);

   public abstract UIViewRoot restoreView(FacesContext var1, String var2, String var3);
}
