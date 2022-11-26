package javax.faces.flow;

import javax.faces.context.FacesContext;

public abstract class SwitchCase {
   public abstract String getFromOutcome();

   public abstract Boolean getCondition(FacesContext var1);
}
