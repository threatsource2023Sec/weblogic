package javax.faces.lifecycle;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

public abstract class Lifecycle {
   public abstract void addPhaseListener(PhaseListener var1);

   public abstract void execute(FacesContext var1) throws FacesException;

   public void attachWindow(FacesContext context) {
   }

   public abstract PhaseListener[] getPhaseListeners();

   public abstract void removePhaseListener(PhaseListener var1);

   public abstract void render(FacesContext var1) throws FacesException;
}
